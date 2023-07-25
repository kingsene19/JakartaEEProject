package sn.ept;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StockTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/VentesVelos-1.0-SNAPSHOT/api";
    }

    @Test
    public void testGetStockListJSON() {
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/stock")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", hasSize(937));
    }

    @Test
    public void testGetStockJSON() {
        int magasinId = 1;
        int produitId = 1;
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
            .pathParam("magasin_id", magasinId)
            .pathParam("produit_id", produitId)
        .when()
            .get("/stock/{magasin_id}/{produit_id}")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("magasinId.id", equalTo(1))
            .body("produitId.id", equalTo(1));
    }

    @Test
    public void testGetStockNotFound() {
        int magasinId = 4;
        int produitId = 322;
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
            .pathParam("magasin_id", magasinId)
            .pathParam("produit_id", produitId)
        .when()
            .get("/stock/{magasin_id}/{produit_id}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testAddStockJSON() {
        String requestBody = "{\"stockPK\": {\"magasinId\":1,\"produitId\":1},\"quantite\":30,\"produitId\":{\"id\":1},\"magasinId\":{\"id\":1}}";

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .header("Accept", MediaType.APPLICATION_JSON)
            .body(requestBody)
        .when()
            .put("/stock")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("stockPK.magasinId", equalTo(1))
            .body("stockPK.produitId", equalTo(1));
    }
    @Test
    public void testDeleteStockJSON() {
        int magasinId = 1;
        int produitId = 1;
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
            .pathParam("magasin_id", magasinId)
            .pathParam("produit_id", produitId)
        .when()
            .delete("/stock/{magasin_id}/{produit_id}")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testDeleteStockXML() {
        int magasinId = 2;
        int produitId = 2;
        given()
            .header("Accept", MediaType.APPLICATION_XML)
            .pathParam("magasin_id", magasinId)
            .pathParam("produit_id", produitId)
        .when()
            .delete("/stock/{magasin_id}/{produit_id}")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testDeleteStockNotFoundJSON() {
        int magasinId = 4;
        int produitId = 322;
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
            .pathParam("magasin_id", magasinId)
            .pathParam("produit_id", produitId)
        .when()
            .delete("/stock/{magasin_id}/{produit_id}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testDeleteStockNotFoundXML() {
        int magasinId = 4;
        int produitId = 322;
        given()
            .header("Accept", MediaType.APPLICATION_XML)
            .pathParam("magasin_id", magasinId)
            .pathParam("produit_id", produitId)
        .when()
            .delete("/stock/{magasin_id}/{produit_id}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_XML);
    }
}
