package sn.ept.ventesvelos.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sn.ept.ventesvelos.entites.ArticleCommande;
import sn.ept.ventesvelos.entites.Commande;
import sn.ept.ventesvelos.facades.CommandeFacade;

import java.util.Collection;

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
        Commande tmp = (Commande) commandeFacade.find(c.getNumero());
        if (tmp != null) {
            commandeFacade.edit(c);
        } else {
            commandeFacade.create(c);
        }
        return Response
                .status(Response.Status.OK)
                .entity(c)
                .build();
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
