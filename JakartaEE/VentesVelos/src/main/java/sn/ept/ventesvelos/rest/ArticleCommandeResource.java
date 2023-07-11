package sn.ept.ventesvelos.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sn.ept.ventesvelos.entites.ArticleCommande;
import sn.ept.ventesvelos.facades.ArticleCommandeFacade;

@Path("article")
public class ArticleCommandeResource {

    @EJB
    private ArticleCommandeFacade articleCommandeFacade;

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Ajout d'un article à une commande",
            description = "Cet endpoint est utilisé pour ajouter les articles commandes à la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "L'article commande a été enregistré à la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de l'enregistrement de l'article commande"
                    )
            }
    )
    public Response addArticleCommande(
            @Parameter(
                    name = "Article Commande",
                    description = "L'article commande que vous souhaitez ajouter",
                    required = true
            )
            ArticleCommande a
    ) {
        ArticleCommande tmp = (ArticleCommande) articleCommandeFacade.find(a.getArticleCommandePK());
        if (tmp != null) {
            articleCommandeFacade.edit(a);
        } else {
            articleCommandeFacade.create(a);
        }
        return Response
                .status(Response.Status.OK)
                .entity(a)
                .build();
    }

}
