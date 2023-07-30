package sn.ept;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class EmployeTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/VentesVelos-1.0-SNAPSHOT/api";
    }

    @Test
    public void testDeleteEmployeNotFound() {
        int id=1601;
        given()
                .header("Accept", MediaType.APPLICATION_XML)
                .pathParam("id", id)
                .when()
                .delete("/employe/{id}")
                .then()
                .statusCode(404)
                .contentType(MediaType.APPLICATION_XML);
    }

    @Test
    public void testDeleteEmploye() {
        int id = 1458;
        given()
            .header("Accept", MediaType.APPLICATION_JSON)
            .pathParam("id", id)
        .when()
            .delete("/employe/{id}")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testAddEmploye() {
        String requestBody = "{\"prenom\": \"John\", \"nom\": \"Doe\", \"telephone\": \"780901020\", \"email\": \"test@example.com\",\"actif\":1,\"magasin_id\":1}";

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .body(requestBody)
                .when()
                .put("/employe")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("id", equalTo(1458))
                .body("nom", is("Doe"))
                .body("prenom", is("John"));
    }


    @Test
    public void testGetEmployeList() {
        given()
                .header("Accept", MediaType.APPLICATION_JSON)
                .when()
                .get("/employe")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }
}
