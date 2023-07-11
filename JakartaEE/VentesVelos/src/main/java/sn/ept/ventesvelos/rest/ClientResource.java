package sn.ept.ventesvelos.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sn.ept.ventesvelos.entites.Client;
import sn.ept.ventesvelos.entites.Commande;
import sn.ept.ventesvelos.facades.ClientFacade;

import java.util.Collection;


@Path("/client")
public class ClientResource {

    @EJB
    private ClientFacade clientFacade;

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
        summary = "Inscription ou Modification des clients",
        description = "Cet endpoint est utilisé pour enregistrer les clients dans la base de données après leur inscription ou lorsque des modifications sont apportées à un client",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Le client a été enregistré à la base de données"
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Une erreur s'est produit lors de l'enregistrement du client"
                )
        }
    )
    public Response addClient(
            @Parameter(
                    name = "Client",
                    description = "Le client que vous souhaiter enregistrer ou modifier",
                    required = true
            )
            Client c
    ) {
        Client tmp = (Client) clientFacade.find(c.getId());
        if (tmp != null) {
            clientFacade.edit(c);
        } else {
            clientFacade.create(c);
        }
        return Response
                .status(Response.Status.OK)
                .entity(c)
                .build();
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Trouver les commandes",
            description = "Cet endpoint est utilisé pour obtenir les commandes du client",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "L'ensemble des commandes ont été retourné"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Client correspondant à l'id indiqué est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Client introuvable")
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produite lors de la récupération des commandes"
                    )
            }
    )
    public Response getCommandeList(
            @PathParam("id")
            @Parameter(description = "L'identifiant du client", required = true)
            int id
    ) {
        Client tmp = (Client) clientFacade.find(id);
        if (tmp == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new Reponse("Le client d'id "+ id + " n'a pas pu être trouvé"))
                    .build();
        }
        Collection<Commande> commandes = tmp.getCommandeCollection();
        return Response
                .status(Response.Status.OK)
                .entity(commandes)
                .build();
    }
}
