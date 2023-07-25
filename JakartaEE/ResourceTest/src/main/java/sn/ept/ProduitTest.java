package sn.ept;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class ProduitTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/VentesVelos-1.0-SNAPSHOT/api";
    }

    @Test
    public void testGetProduitListJSON() {
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/produit")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", hasSize(321));

    }

    @Test
    public void testGetProduitJSON() {
        int produitId = 3;
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
            .pathParam("id", produitId)
        .when()
            .get("/produit/{id}")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("produit.id", is(produitId))
            .body("stocks", hasSize(3));

    }

    @Test
    public void testGetStockListNotFoundJSON() {
        int produitId = 322;
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
            .pathParam("id", produitId)
        .when()
            .get("/produit/{id}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testAddProduit() {
        String requestBody = "{\"id\": 1500,\"nom\": \"Bikes\",\"annee_model\":2016,\"prix_depart\":399.99,\"categorie_id\":7,\"marque_id\":9}";

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .body(requestBody)
                .when()
                .put("/produit")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("id", is(1500))
                .body("nom", is("Bikes"));
    }


    @Test
    public void testDeleteProduitNotFoundJSON() {
        int id = 1700;
        given()
                .header("Accept", MediaType.APPLICATION_JSON)
                .pathParam("id", id)
                .when()
                .delete("/produit/{id}")
                .then()
                .statusCode(404)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testDeleteProduitNotFoundXML() {
        int id=1700;
        given()
                .header("Accept", MediaType.APPLICATION_XML)
                .pathParam("id", id)
                .when()
                .delete("/produit/{id}")
                .then()
                .statusCode(404)
                .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testDeleteProduitJSON() {
        int id = 2;
        given()
                .header("Accept", MediaType.APPLICATION_JSON)
                .pathParam("id", id)
                .when()
                .delete("/produit/{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testDeleteProduitXML() {
        int id = 1;
        given()
                .header("Accept", MediaType.APPLICATION_XML)
                .pathParam("id", id)
                .when()
                .delete("/produit/{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML);
    }

}
