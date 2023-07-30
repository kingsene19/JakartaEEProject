package sn.ept;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LocalisationTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/VentesVelos-1.0-SNAPSHOT/api";
    }

    @Test
    public void testDeleteLocalisationNotFound() {
        int id=10;
        given()
                .header("Accept", MediaType.APPLICATION_XML)
                .pathParam("id", id)
                .when()
                .delete("/localisation/{id}")
                .then()
                .statusCode(404)
                .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testAddLocalisation() {
        String requestBody = "{\"latitude\": 14.58, \"longitude\": 17.89}";

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .body(requestBody)
                .when()
                .put("/localisation")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("id", equalTo(1))
                .body("latitude", equalTo(14.58F))
                .body("longitude", equalTo(17.89F));
    }

    @Test
    public void testDeleteLocalisation() {
        int id = 1;
        given()
                .header("Accept", MediaType.APPLICATION_JSON)
                .pathParam("id", id)
                .when()
                .delete("/localisation/{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testGetLocalisationList() {
        given()
                .header("Accept", MediaType.APPLICATION_JSON)
                .when()
                .get("/localisation")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }
}
