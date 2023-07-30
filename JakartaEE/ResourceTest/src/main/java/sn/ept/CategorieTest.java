package sn.ept;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class CategorieTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI =  "http://localhost:8080/VentesVelos-1.0-SNAPSHOT/api";
    }

    @Test
    public void testGetCategorieList() {
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
        int categorieId = 1500;
        given()
            .pathParam("id", categorieId)
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/categorie/{id}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testDeleteCategorieJSON() {
        int id = 2;
        given()
                .header("Accept", MediaType.APPLICATION_JSON)
                .pathParam("id", id)
                .when()
                .delete("/categorie/{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testDeleteCategorieXML() {
        int id = 8;
        given()
            .header("Accept", MediaType.APPLICATION_XML)
            .pathParam("id", id)
        .when()
            .delete("/categorie/{id}")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testDeleteCategorieNotFoundJSON() {
        int id = 1600;
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
                .pathParam("id", id)
            .when()
                .delete("/categorie/{id}")
            .then()
                .statusCode(404)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testDeleteCategorieNotFoundXML() {
        int id=1601;
        given()
            .header("Accept", MediaType.APPLICATION_XML)
            .pathParam("id", id)
        .when()
            .delete("/categorie/{id}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testAddCategorieJSON() {
        String requestBody = "{\"nom\": \"Bikes\"}";

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .header("Accept", MediaType.APPLICATION_JSON)
            .body(requestBody)
        .when()
            .put("/categorie")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("id", is(9))
            .body("nom", is("Bikes"));
    }
    @Test
    public void testAddCategorieXML() {
        String requestBody = "<categorie><nom>Bikes Old</nom></categorie>";

        given()
            .contentType(MediaType.APPLICATION_XML)
            .header("Accept", MediaType.APPLICATION_XML)
            .body(requestBody)
        .when()
            .put("/categorie")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_XML)
            .body("categorie.id", is("8"))
            .body("categorie.nom", is("Bikes Old"));
    }
}
