package com.pm.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    // endpoint
    private final WebClient webClient;

    // Constructor untuk JwtValidationGatewayFilter
    // auth.service.url diambil dari application.properties / application.yml
    // lalu digunakan sebagai base URL untuk WebClient
    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder, @Value("${auth.service.url}") String authServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    // Method apply() digunakan untuk membuat custom GatewayFilter
    // Filter ini dapat meng-intercept HTTP request,
    // menjalankan logic tertentu seperti validasi JWT,
    // lalu menentukan apakah request dilanjutkan atau ditolak
    public GatewayFilter apply(Object config) {
        return(exchange, chain) -> {
            // mendapatkan authorization header dari request dan di assign ke variabel bernama Token
            String token =
                exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            // exchange itu adalah variabel java Object yang merepresentasikan current request nya bagaimana
            if(token == null || !token.startsWith("Bearer ")){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return webClient.get()
                    // mengirim request GET ke auth-service
                    // endpoint ini digunakan untuk memvalidasi JWT token
                    .uri("/validate")

                    // menambahkan Authorization header ke request
                    // token JWT dari client diteruskan ke auth-service
                    // contoh:
                    // Authorization: Bearer eyJhbGciOi...
                    .header(HttpHeaders.AUTHORIZATION, token)

                    // mengirim request dan mengambil response
                    .retrieve()

                    // response body tidak digunakan
                    // kita hanya perlu mengetahui apakah request berhasil atau gagal
                    // 200 = token valid
                    // 401 = token tidak valid
                    .toBodilessEntity()

                    // jika validasi berhasil,
                    // request asli diteruskan ke service tujuan
                    // misalnya ke patient-service atau billing-service
                    .then(chain.filter(exchange));
        };
    }
}
