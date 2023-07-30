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
import sn.ept.ventesvelos.entites.*;
import sn.ept.ventesvelos.facades.CommandeFacade;

import java.util.Collection;
import java.util.List;

@Path("/commande")
public class CommandeResource {

    @EJB
    private CommandeFacade commandeFacade;

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Ajout d'une commande",
            description = "Cet endpoint est utilisé pour ajouter des commandes à la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "La commande a été enregistrée à la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de l'enregistrement de la commande"
                    )
            }
    )
    public Response addCommande(
            @Parameter(
                    name = "Commande",
                    description = "La commande que vous souhaitez ajouter",
                    required = true
            )
            Commande c
    ) {
        if (c.getNumero() != null) {
            Commande tmp = (Commande) commandeFacade.find(c.getNumero());
            if (tmp != null) {
                commandeFacade.edit(c);
            } else {
                commandeFacade.create(c);
            }
        } else {
            commandeFacade.create(c);
        }
        return Response
                .status(Response.Status.OK)
                .entity(c)
                .build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Liste des clients",
            description = "Cet endpoint est utilisé pour obtenir l'ensemble des commandes disponibles"
    )
    public Response getCommandeList() {
        List<Client> clients = commandeFacade.findAll();
        if (clients == null ) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("Un problème s'est produit lors de la récupération")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(clients)
                .build();
    }

    @Path("/{numero}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Suppression de commande",
            description = "Cet endpoint est utilisé pour supprimer les commandes de la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "La commande a été supprimée de la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de la suppression de la commande"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "La commande correspondant au numéro est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Commande introuvable")
                                            }
                                    )
                            }
                    )
            }
    )
    public Response deleteClient(
            @PathParam("numero")
            @Parameter(description = "Le numéro de la commande", required = true)
            int numero
    ) {
        Commande tmp = (Commande) commandeFacade.find(numero);
        if (tmp == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("La commande de numero "+ numero +" n'a pas pu être trouvée")).build();
        }
        commandeFacade.remove(tmp);
        return Response.status(Response.Status.OK).entity(new Reponse("La commande de numero "+ numero +" n'a pas pu être trouvée")).build();
    }

    @Path("/{numero}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Trouver les articles d'une commande",
            description = "Cet endpoint est utilisé pour obtenir l'ensemble des articles commandes",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "L'ensemble des articles commandes ont été retourné"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produite lors de la récupération des articles commandes"
                    )
            }
    )
    public Response getArticleCommandeList(
            @PathParam("numero")
            @Parameter(description = "L'identifiant de la commande", required = true)
            int id
    ) {
        Commande tmp = (Commande) commandeFacade.find(id);
        if (tmp == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("Le produit d'id "+ id + " n'a pas pu être trouvé")).build();
        }
        Collection<ArticleCommande> articles = tmp.getArticleCommandeCollection();
        return Response.status(Response.Status.OK).entity(articles).build();
    }
}
