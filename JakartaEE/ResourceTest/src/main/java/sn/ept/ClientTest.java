package sn.ept;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ClientTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/VentesVelos-1.0-SNAPSHOT/api";
    }

    @Test
    public void testDeleteClientXML() {
        int id = 1411;
        given()
                .header("Accept", MediaType.APPLICATION_XML)
                .pathParam("id", id)
                .when()
                .delete("/client/{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testDeleteClientNotFoundJSON() {
        int id = 1600;
        given()
                .header("Accept", MediaType.APPLICATION_JSON)
                .pathParam("id", id)
                .when()
                .delete("/client/{id}")
                .then()
                .statusCode(404)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testDeleteClientNotFoundXML() {
        int id=1601;
        given()
                .header("Accept", MediaType.APPLICATION_XML)
                .pathParam("id", id)
                .when()
                .delete("/client/{id}")
                .then()
                .statusCode(404)
                .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testDeleteClientJSON() {
        int id = 1410;
        given()
                .header("Accept", MediaType.APPLICATION_JSON)
                .pathParam("id", id)
                .when()
                .delete("/client/{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testAddClientJSON() {
        String requestBody = "{\"prenom\": \"John\", \"nom\": \"Doe\", \"telephone\": \"780901020\", \"email\": \"test@example.com\"}";

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .header("Accept", MediaType.APPLICATION_JSON)
            .body(requestBody)
        .when()
            .put("/client")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("id", equalTo(1458))
            .body("nom", is("Doe"))
            .body("prenom", is("John"));
    }

    @Test
    public void testAddClientXML() {
        String requestBody = "<client><nom>Doe</nom><prenom>John</prenom><telephone>767810912</telephone><email>test@example.com</email></client>";

        given()
            .contentType(MediaType.APPLICATION_XML)
            .header("Accept", MediaType.APPLICATION_XML)
            .body(requestBody)
        .when()
            .put("/client")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_XML)
            .body("client.id", is("1459"))
            .body("client.nom", is("Doe"))
            .body("client.prenom", is("John"));
    }
    @Test
    public void testGetCommandeList() {
        int clientId = 159;
        given()
            .pathParam("id", clientId)
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/client/{id}")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", hasSize(1));
    }
    @Test
    public void testGetCommandeListNotFoundJSON() {
        int clientId = 1500;
        given()
            .pathParam("id", clientId)
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/client/{id}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testGetCommandeListNotFoundXML() {
        int clientId = 1500;
        given()
            .pathParam("id", clientId)
            .header("Accept", MediaType.APPLICATION_XML)
        .when()
            .get("/client/{id}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testGetClientList() {
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
        .when()
            .get("/client")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", hasSize(1455));
    }
}

