package sn.ept;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

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

    @Test
    public void testDeleteMarqueXML() {
        int id = 1;
        given()
                .header("Accept", MediaType.APPLICATION_XML)
                .pathParam("id", id)
                .when()
                .delete("/marque/{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testDeleteMarqueNotFoundJSON() {
        int id = 1700;
        given()
                .header("Accept", MediaType.APPLICATION_JSON)
                .pathParam("id", id)
                .when()
                .delete("/marque/{id}")
                .then()
                .statusCode(404)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testDeleteMarqueNotFoundXML() {
        int id=1700;
        given()
                .header("Accept", MediaType.APPLICATION_XML)
                .pathParam("id", id)
                .when()
                .delete("/marque/{id}")
                .then()
                .statusCode(404)
                .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testDeleteMarqueJSON() {
        int id = 2;
        given()
                .header("Accept", MediaType.APPLICATION_JSON)
                .pathParam("id", id)
                .when()
                .delete("/marque/{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testAddMarqueJSON() {
        String requestBody = "{\"id\": 1500, \"nom\": \"Bikes\"}";

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .body(requestBody)
                .when()
                .put("/marque")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("id", is(1500))
                .body("nom", is("Bikes"));
    }
}
