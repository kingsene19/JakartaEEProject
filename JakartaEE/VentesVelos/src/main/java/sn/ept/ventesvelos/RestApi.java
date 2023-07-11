package sn.ept.ventesvelos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("api")
@OpenAPIDefinition(
        info = @Info(
                title = "API POUR LA BOUTIQUE DE VENTES DE VELOS",
                description = "Ceci est une documentation Swagger définissant les endpoints pour les différentes ressources nécessaire aux application Web et Mobile pour la vente de vélos ",
                contact = @Contact(
                        name = "Mohamed Massamba Sene ",
                        email = "test@example.com",
                        url = "https://www.google.sn"
                ),
                version = "1.0.0",
                license = @License(name = "OpenSource")
        ),
        servers = {
                @Server(
                        url = "{urlDeploiement}",
                        variables = {
                                @ServerVariable(name="urlDeploiement", defaultValue = "http://localhost:8080/VentesVelos-1.0-SNAPSHOT/")
                        }
                )
        }
)
public class RestApi extends Application {

}
