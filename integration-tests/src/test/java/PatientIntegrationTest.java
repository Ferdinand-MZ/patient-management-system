import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientIntegrationTest {
    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnPatientsWithValidToken(){
        // 1. Arrange
        // 2. act
        // 3. assert

        // define/arrange property yang akan kita kirim di request test run
        String loginPayload = """
             {
                "email": "testuser@test.com",
                "password": "password123"
             }
            """;

        String token = given()
            .contentType("application/json")
            .body(loginPayload)
            .when()

            // act portion (karena trigger post request ke login endpoint)
            .post("/auth/login")

            // disini assert
            .then()
            .statusCode(200)

            // .extract bakal pull response dari request yang kita buat diatas
            .extract()
            .jsonPath()
            .get("token");

        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .get("/api/patients")
            .then()
            .statusCode(200)
            .body("patients", notNullValue());
    }
}
