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
    public void testAddClientJSON() {
        String requestBody = "{\"id\": 1460,\"prenom\": \"John\", \"nom\": \"Doe\", \"telephone\": \"780901020\", \"email\": \"test@example.com\"}";

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .header("Accept", MediaType.APPLICATION_JSON)
            .body(requestBody)
        .when()
            .put("/client")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("id", equalTo(1460))
            .body("nom", is("Doe"))
            .body("prenom", is("John"));
    }

    @Test
    public void testAddClientXML() {
        String requestBody = "<client><id>1461</id><nom>Doe</nom><prenom>John</prenom><telephone>767810912</telephone><email>test@example.com</email></client>";

        given()
            .contentType(MediaType.APPLICATION_XML)
            .header("Accept", MediaType.APPLICATION_XML)
            .body(requestBody)
        .when()
            .put("/client")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_XML)
            .body("client.id", is("1461"))
            .body("client.nom", is("Doe"))
            .body("client.prenom", is("John"));
    }
    @Test
    public void testGetCommandeListJSON() {
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
        int clientId = 1462;
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
        int clientId = 1462;
        given()
            .pathParam("id", clientId)
            .header("Accept", MediaType.APPLICATION_XML)
        .when()
            .get("/client/{id}")
        .then()
            .statusCode(404)
            .contentType(MediaType.APPLICATION_XML);
    }

}

