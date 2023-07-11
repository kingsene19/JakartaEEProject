package sn.ept;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class MarqueTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI =  "http://localhost:8080/VentesVelos-1.0-SNAPSHOT/api";
    }

    @Test
    public void testGetMarqueListJSON() {
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/marque")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", hasSize(9));
    }

    @Test
    public void testGetProduitListJSON() {
        int marqueId = 1;
        given()
            .pathParam("id", marqueId)
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/marque/{id}")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", hasSize(118));
    }

    @Test
    public void testGetProduitListNotFound() {
        int marqueId = 10;
        given()
            .pathParam("id", marqueId)
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/marque/{id}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_JSON);
    }
}
