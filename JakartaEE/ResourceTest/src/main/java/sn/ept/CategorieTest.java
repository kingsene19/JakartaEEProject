package sn.ept;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class CategorieTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI =  "http://localhost:8080/VentesVelos-1.0-SNAPSHOT/api";
    }

    @Test
    public void testGetCategorieListJSON() {
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/categorie")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", hasSize(7));
    }

    @Test
    public void testGetProduitListJSON() {
        int categorieId = 1;
        given()
            .pathParam("id", categorieId)
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/categorie/{id}")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", hasSize(59));
    }

    @Test
    public void testGetProduitListNotFound() {
        int categorieId = 8;
        given()
            .pathParam("id", categorieId)
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/categorie/{id}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_JSON);
    }
}
