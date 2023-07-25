package sn.ept;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CommandeTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/VentesVelos-1.0-SNAPSHOT/api";
    }

    @Test
    public void testAddCommandeJSON() {
        String requestBody = "{\"numero\": 1616,\"date_commande\": \"2023-06-09\", \"date_livraison_voulue\": \"2023-06-30\", \"statut\": 3," +
                " \"clientId\": {\"id\": 136}, \"vendeurId\": {\"id\": 1453}, \"magasinId\": {\"id\": 3}}";

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .header("Accept", MediaType.APPLICATION_JSON)
            .body(requestBody)
        .when()
            .put("/commande")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("numero", equalTo(1616));
    }

    @Test
    public void testGetArticleCommandeListJSON() {
        int numero = 10;
        given()
            .pathParam("numero", numero)
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/commande/{numero}")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", hasSize(1));
    }

    @Test
    public void testGetArticleCommandeListNotFoundJSON() {
        int numero = 1618;
        given()
            .pathParam("numero", numero)
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/commande/{numero}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testGetArticleCommandeListNotFoundXML() {
        int numero = 1618;
        given()
            .pathParam("numero", numero)
            .header("Accept", MediaType.APPLICATION_XML)
        .when()
            .get("/commande/{numero}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testDeleteCommandeXML() {
        int id = 1616;
        given()
                .header("Accept", MediaType.APPLICATION_XML)
                .pathParam("id", id)
                .when()
                .delete("/commande/{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testDeleteCommandeNotFoundJSON() {
        int id = 1700;
        given()
                .header("Accept", MediaType.APPLICATION_JSON)
                .pathParam("id", id)
                .when()
                .delete("/commande/{id}")
                .then()
                .statusCode(404)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testDeleteCommandeNotFoundXML() {
        int id=1700;
        given()
                .header("Accept", MediaType.APPLICATION_XML)
                .pathParam("id", id)
                .when()
                .delete("/commande/{id}")
                .then()
                .statusCode(404)
                .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testDeleteCommandeJSON() {
        int id = 1600;
        given()
                .header("Accept", MediaType.APPLICATION_JSON)
                .pathParam("id", id)
                .when()
                .delete("/commande/{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }
    @Test
    public void testGetCommandeList() {
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/commande")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", hasSize(1614));
    }
}
