package sn.ept;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;

public class MagasinTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/VentesVelos-1.0-SNAPSHOT/api";
    }

    @Test
    public void testDeleteMagasinNotFound() {
        int id=1601;
        given()
            .header("Accept", MediaType.APPLICATION_XML)
            .pathParam("id", id)
        .when()
            .delete("/magasin/{id}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testDeleteMagasin() {
        int id = 3;
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
            .pathParam("id", id)
        .when()
                .delete("/magasin/{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testAddMagasin() {
        String requestBody = "{\"id\":4,\"email\":\"bikeshop@shop.com\",\"nom\":\"BikeShop\",\"telephone\":\"780901020\"}";

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .header("Accept", MediaType.APPLICATION_JSON)
            .body(requestBody)
        .when()
            .put("/magasin")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("id", equalTo(4))
            .body("nom", is("BikeShop"));
    }


    @Test
    public void testGetMagasinList() {
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/magasin")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", hasSize(2));
    }
}
