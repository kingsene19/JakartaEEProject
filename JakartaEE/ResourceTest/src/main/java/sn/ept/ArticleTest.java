package sn.ept;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class ArticleTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/VentesVelos-1.0-SNAPSHOT/api";
    }

    @Test
    public void testAddArticleJSON() {
        String requestBody = "{\"articleCommandePK\": {\"numeroCommande\": 1, \"ligne\": 6}, \"quantite\":1, \"remise\": 0, " +
                "\"prixDepart\": 870, \"produitId\": {\"id\": 1} }";

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .header("Accept", MediaType.APPLICATION_JSON)
            .body(requestBody)
        .when()
            .put("/article")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("articleCommandePK.numeroCommande", is(1))
            .body("articleCommandePK.ligne", is(6));
    }

    @Test
    public void testDeleteArticleJSON() {
        int numero = 1;
        int ligne = 2;
        given()
                .header("Accept", MediaType.APPLICATION_JSON)
                .pathParam("numero", numero)
                .pathParam("ligne", ligne)
                .when()
                .delete("/article/{numero}/{ligne}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }
    @Test
    public void testAddArticleXML() {
        String requestBody = "<article><articleCommandePK><numeroCommande>1</numeroCommande><ligne>7</ligne></articleCommandePK>" +
                "<quantite>2</quantite><prixDepart>870</prixDepart><remise>0</remise><produit><id>1</id></produit></article>";

        given()
            .contentType(MediaType.APPLICATION_XML)
            .header("Accept", MediaType.APPLICATION_XML)
            .body(requestBody)
        .when()
            .put("/article")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_XML)
            .body("article.articleCommandePK.numeroCommande", is("1"))
            .body("article.articleCommandePK.ligne", is("7"));
    }

    @Test
    public void testDeleteArticleXML() {
        int numero = 1;
        int ligne = 1;
        given()
            .header("Accept", MediaType.APPLICATION_XML)
            .pathParam("numero", numero)
            .pathParam("ligne", ligne)
        .when()
            .delete("/article/{numero}/{ligne}")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testDeleteArticleNotFoundJSON() {
        int numero = 4;
        int ligne = 322;
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
            .pathParam("numero", numero)
            .pathParam("ligne", ligne)
        .when()
            .delete("/article/{numero}/{ligne}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testDeleteArticleNotFoundXML() {
        int numero = 4;
        int ligne = 322;
        given()
            .header("Accept", MediaType.APPLICATION_XML)
            .pathParam("numero", numero)
            .pathParam("ligne", ligne)
        .when()
            .delete("/article/{numero}/{ligne}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testGetArticleList() {
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/article")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", hasSize(4721));
    }
}
