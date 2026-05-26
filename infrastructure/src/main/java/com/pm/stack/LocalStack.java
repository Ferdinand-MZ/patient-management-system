package com.pm.stack;

import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.*;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ecs.Protocol;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.logs.LogGroup;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.services.msk.CfnCluster;
import software.amazon.awscdk.services.rds.*;
import software.amazon.awscdk.services.route53.CfnHealthCheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LocalStack extends Stack {
    // membuat vpc
    private final Vpc vpc;

    // buat ecs
    private final Cluster ecsCluster;

    // constructor, boilerplate untuk pass stack ke scope
    public LocalStack(final App scope, final String id, final StackProps props) {
        super(scope,id,props);

        this.vpc = createVpc();

        DatabaseInstance authServiceDb = createDatabase("AuthServiceDB", "auth-service-db");

        DatabaseInstance patientServiceDb = createDatabase("PatientServiceDB", "patient-service-db");

        CfnHealthCheck authDBHealthCheck = createDbHealthCheck(authServiceDb, "AuthServiceDBHealthCheck");

        CfnHealthCheck patientDBHealthCheck = createDbHealthCheck(patientServiceDb, "PatientServiceDBHealthCheck");

        CfnCluster mskCluster = createMskCluster();

        // inisialisasi ecs cluster
        this.ecsCluster = createEcsCluster();

        // inisialisasi ecs service
        FargateService authService = createFargateService(
                "AuthService",
                "auth-service",

                // ini port ecs service
                List.of(4005),
                authServiceDb,
                Map.of("JWT_SECRET", "qGl1+ECC3WD3UyAD9mMLDknNr0RuGLL0CavaWxSOuGU="));

        // ngasih tau kalau authService kita ada dependency di authDBHealthCheck dan authServiceDB
        // mastiin kalau authServiceDb starts sebelum kita nyoba start authService
        authService.getNode().addDependency(authDBHealthCheck);
        authService.getNode().addDependency(authServiceDb);

        FargateService billingService = createFargateService(
            "BillingService",
            "billing-service",

            // 4001 itu port app nya, 9001 itu port grpc
            List.of(4001, 9001),

            // null ini artinya ga punya db
            null,

            // ga ada additional vars
            null
        );

        FargateService analyticsService = createFargateService(
            "AnalyticsService",
            "analytics-service",
            List.of(4002),
            null,
            null
        );

        // agar kafka jalan dulu sebelum analytics service (soalnya kita pakai kafka)
        analyticsService.getNode().addDependency(mskCluster);

        FargateService patientService = createFargateService(
                "PatientService",
                "patient-service",
                List.of(4000),
                patientServiceDb,

                // k1/k2 itu nama environment tambahan, dan v1/v2 itu nilainya
                // Format: "NAMA_VARIABLE", "NILAI_VARIABLE"
                Map.of(
                    "BILLING_SERVICE_ADDRESS", "host.docker.internal",
                    "BILLING_SERVICE_GRPC_PORT", "9001"
                )
        );

        patientService.getNode().addDependency(patientServiceDb);
        patientService.getNode().addDependency(patientDBHealthCheck);

        // dependency ke billing service buat grpc request,
        // setiap kali create patient bakal buat grpc request ke billing service
        patientService.getNode().addDependency(billingService);

        // karena patient service kirim patients created events,
        // kita pastikan kalau cluster itu jalan dulu sebelum patient service dimulai
        patientService.getNode().addDependency(mskCluster);

        // ngebuat ecs service untuk api gateway
        createApiGatewayService();
    }

    private Vpc createVpc() {
        return Vpc.Builder
            .create(this, "PatientManagementVPC")
            .vpcName("PatientManagementVPC")

            // max availability zone, ini maksudnya available di 2 different zones
            .maxAzs(2)
            .build();
    }

    // buat db rds (databases as service)
    private DatabaseInstance createDatabase(String id, String dbName) {
        return DatabaseInstance.Builder
            .create(this, id)
            .engine(DatabaseInstanceEngine.postgres(
                PostgresInstanceEngineProps.builder()
                    .version(PostgresEngineVersion.VER_17_2)
                        .build()))

            // Connect the db with the vpc
            .vpc(vpc)
            .instanceType(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO))

            // amount of storage yang mau di alokasiin ke database
            .allocatedStorage(20)
            .credentials(Credentials.fromGeneratedSecret("admin_user"))

            // dbName diambil dari variabel di parameter DatabaseInstance sebelumnya
            .databaseName(dbName)

            // setiap kali kita destroy stack(LocalStack), kita juga akan menghapus db storage
            .removalPolicy(RemovalPolicy.DESTROY)
            .build();
    }

    private CfnHealthCheck createDbHealthCheck(DatabaseInstance db, String id) {
        return CfnHealthCheck.Builder.create(this, id)
                .healthCheckConfig(CfnHealthCheck.HealthCheckConfigProperty.builder()
                    .type("TCP")
                    .port(Token.asNumber(db.getDbInstanceEndpointPort()))
                    .ipAddress(db.getDbInstanceEndpointAddress())
                    .requestInterval(30)
                    .failureThreshold(3)
                    .build())
                .build();
    }

    private CfnCluster createMskCluster() {
        return CfnCluster.Builder.create(this, "MskCluster")

                // Nama cluster Kafka yang akan dibuat di AWS MSK
                .clusterName("kafka-cluster")

                // Versi Apache Kafka yang digunakan oleh cluster
                .kafkaVersion("2.8.0")

                // Jumlah broker Kafka yang dijalankan dalam cluster
                .numberOfBrokerNodes(1)

                // Konfigurasi node broker Kafka
                .brokerNodeGroupInfo(CfnCluster.BrokerNodeGroupInfoProperty.builder()

                        // Tipe instance EC2 yang dipakai broker Kafka
                        .instanceType("kafka.m5.xlarge")

                        // Menempatkan broker di private subnet VPC
                        .clientSubnets(vpc.getPrivateSubnets().stream()
                                .map(ISubnet::getSubnetId)
                                .collect(Collectors.toList()))

                        // Distribusi broker otomatis ke Availability Zone
                        .brokerAzDistribution("DEFAULT")
                        .build())

                // Membuat resource cluster MSK
                .build();
    }

    // ECS Cluster
    private Cluster createEcsCluster() {
        return Cluster.Builder.create(this, "PatientManagementCluster")
            .vpc(vpc)
            .defaultCloudMapNamespace(CloudMapNamespaceOptions.builder()
                .name("patient-management.local")
                .build())
        .build();
    }

    // ECS Service
    // Fargate Service merupakan salah satu tipe ECS Service, ini yang paling common dipakai di enterprise
    private FargateService createFargateService(
        String id,
        String imageName,
        List<Integer> ports,
        DatabaseInstance db,
        Map<String, String> additionalEnvVars
    ) {
        // sebelum membuat ecs task, kita harus membuat ecs task definition
        // task definition adalah blueprint atau container dan digunakan untuk specify seperti amount of cpu,
        // amount of memory, image yang kita gunakan, env variables, etc.

        FargateTaskDefinition taskDefinition = FargateTaskDefinition.Builder.create(this, id + "Task")
            .cpu(256)
            .memoryLimitMiB(512)
            .build();

        // di container ini kita pakai builder di awal karena mau ditambahin more environment variables
        ContainerDefinitionOptions.Builder containerOptions = ContainerDefinitionOptions.builder()
            .image(ContainerImage.fromRegistry(imageName))

            // specify port mana yang akan dipakai, ports yang digunakan dari argument parameter List<Integer> ports
            .portMappings(ports.stream()
                .map(port -> PortMapping.builder()

                    // port yang digunakan aplikasi untuk running didalam container
                    .containerPort(port)

                    // port yang kita inginkan agar container expose, agar other services bisa ngakses
                    .hostPort(port)

                    .protocol(Protocol.TCP)
                    .build())
                .toList())

            // specify logging dimana, dan bentuk log group nya
            .logging(LogDriver.awsLogs(AwsLogDriverProps.builder()
                        .logGroup(LogGroup.Builder.create(this, id + "LogGroup")
                            .logGroupName("/ecs/" + imageName)
                            .removalPolicy(RemovalPolicy.DESTROY)
                            .retention(RetentionDays.ONE_DAY)
                            .build())
                        .streamPrefix(imageName)
                    .build()));

        // define environment variables untuk service ini
        Map<String, String> envVars = new HashMap<>();

        // 3 address dimana localstack bisa set up kafka untuk di host
        envVars.put("SPRING_KAFKA_BOOTSTRAP_SERVERS", "localhost.localstack.cloud:4510, localhost.localstack.cloud:4511, localhost.localstack.cloud:4512");

        // Menggabungkan environment variable tambahan ke envVars utama
        if(additionalEnvVars != null) {
            envVars.putAll(additionalEnvVars);
        }

        // Jika database tersedia, tambahkan konfigurasi koneksi database Spring Boot
        if(db != null){

            // URL koneksi PostgreSQL untuk service Spring Boot
            envVars.put("SPRING_DATASOURCE_URL", "jdbc:postgresql://%s:%s/%s-db".formatted(
                db.getDbInstanceEndpointAddress(), // %s pertama || Host DB
                db.getDbInstanceEndpointPort(), // %s kedua || Port DB
                imageName // %s ketiga || Nama DB
            ));

            // Username database PostgreSQL
            envVars.put("SPRING_DATASOURCE_USERNAME", "admin_user");

            // Password db
            envVars.put("SPRING_DATASOURCE_PASSWORD", db.getSecret().secretValueFromJson("password").toString());

            // update tabel otomatis lewat hibernate
            envVars.put("SPRING_JPA_DATABASE_DDL_AUTO", "update");

            // inisialisasi sql otomatis saat aplikasi startup
            envVars.put("SPRING_SQL_INIT_MODE", "always");

            // waktu tunggu koneksi db (60 detik)
            envVars.put("SPRING_DATASOURCE_HIKARI_INITIALIZATION_FAIL_TIMEOUT", "60000");
        }

        // tambah semua envVars ke konfigurasi container
        containerOptions.environment(envVars);

        // Nambah Container ke task definition ECS
        taskDefinition.addContainer(imageName + "Container", containerOptions.build());

        return FargateService.Builder.create(this, id)

            // cluster ECS nya
            .cluster(ecsCluster)

            // Task Definition yang dipake (yang kita buat tadi di atas)
            .taskDefinition(taskDefinition)

            // Container ga dapet public IP demi keamanan
            .assignPublicIp(false)

            // Nama Service ECS nya (imageName)
            .serviceName(imageName)
            .build();
    }

    // ecs Service untuk api gateway (tidak memakai fargateservice karena mau di ekspos)
    private void createApiGatewayService() {
        FargateTaskDefinition taskDefinition = FargateTaskDefinition.Builder.create(this, "APIGatewayTaskDefinition")
                .cpu(256)
                .memoryLimitMiB(512)
                .build();

        // ga pake builder kayak di atas karena kita akan build semuanya dalam all in one go tanpa tambahan env var
        ContainerDefinitionOptions containerOptions = ContainerDefinitionOptions.builder()
                .image(ContainerImage.fromRegistry("api-gateway"))
                .environment(Map.of(
                    "SPRING_PROFILES_ACTIVE", "prod",
                    "AUTH_SERVICE_URL", "http://host.docker.internal:4005"
                ))

                // specify port mana yang akan dipakai
                .portMappings(List.of(4004).stream()
                        .map(port -> PortMapping.builder()

                                // port yang digunakan aplikasi untuk running didalam container
                                .containerPort(port)

                                // port yang kita inginkan agar container expose, agar other services bisa ngakses
                                .hostPort(port)

                                .protocol(Protocol.TCP)
                                .build())
                        .toList())

                // specify logging dimana, dan bentuk log group nya
                .logging(LogDriver.awsLogs(AwsLogDriverProps.builder()
                        // ga perlu dynamic variable id lagi
                        .logGroup(LogGroup.Builder.create(this, "ApiGatewayLogGroup")
                                .logGroupName("/ecs/api-gateway")
                                .removalPolicy(RemovalPolicy.DESTROY)
                                .retention(RetentionDays.ONE_DAY)
                                .build())
                        .streamPrefix("api-gateway")
                        .build()))
                .build();

        taskDefinition.addContainer("APIGatewayContainer", containerOptions);

        // yang ini bakal otomatis create application load balancer
        ApplicationLoadBalancedFargateService apiGateway = ApplicationLoadBalancedFargateService.Builder.create(
                this, "api-gateway")
                .cluster(ecsCluster)
                .serviceName("api-gateway")
                .taskDefinition(taskDefinition)
                .desiredCount(1)
                .healthCheckGracePeriod(Duration.seconds(60))
                .build();
    }

    public static void main(final String[] args) {
        App app = new App(AppProps.builder().outdir("./cdk.out").build());

        // defining additional properties yang ingin kita apply di stack
        // yang di specify disini itu synthesizer
        StackProps props = StackProps.builder()
            // synthesizer adalah abs term untuk convert kode yand define arsitektur kita
            // menjadi cloud formation template,
            // dan yang dimaksud BootstraplessSynthesizer, adalah menyuruh cdk code untuk
            // skip initial bootstrapping dari cdk environment karena kita ga perlu untuk local stack
            .synthesizer(new BootstraplessSynthesizer())
            .build();

        new LocalStack(app, "localstack", props );

        // app.synth akan bilang ke cdk yang telah kita buat disini kalau kita ingin take our stack tambah props apa aja
        // convert stack itu menjadi cloud formation template dan masukan semuanya ke cdk.out folder
        app.synth();
        System.out.println("App synthesizing in progress...");
    }
}
