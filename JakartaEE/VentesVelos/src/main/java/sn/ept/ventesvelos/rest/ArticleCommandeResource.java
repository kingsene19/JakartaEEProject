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
import sn.ept.ventesvelos.facades.ArticleCommandeFacade;

import java.util.List;

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


    @Path("/{numero}/{ligne}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Suppression d'article commande",
            description = "Cet endpoint est utilisé pour supprimer les articles commandes de la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "L'article commande a été supprimé de la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de la suppression de l'article commande"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Article commande correspondant à la clé indiquée est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Article commande introuvable")
                                            }
                                    )
                            }
                    )
            }
    )
    public Response deleteArticleCommande(
            @PathParam("numero")
            @Parameter(description = "Le numero de commande", required = true)
            int numero,
            @PathParam("ligne")
            @Parameter(description = "La ligne de l'article commande", required = true)
            int ligne
    ) {
        ArticleCommande tmp = (ArticleCommande) articleCommandeFacade.find(new ArticleCommandePK(numero, ligne));
        if (tmp == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("L'article commande de clé ("+ numero +", "+ ligne +") n'a pas pu être trouvé")).build();
        }
        articleCommandeFacade.remove(tmp);
        return Response.status(Response.Status.OK).entity(new Reponse("L'article commande de clé ("+ numero +", "+ ligne + ") a été supprimé avec succès")).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Récupération des articles commandes",
            description = "Cet endpoint est utilisé pour récupérer tous les articles commande de la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Les articles commandes ont été supprimées à la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Une erreur s'est produite lors de la récupération de l'article commande"
                    )
            }
    )
    public Response getArticlesCommandes() {
        List<ArticleCommande> categories = articleCommandeFacade.findAll();
        if (categories == null ) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("Un problème s'est produit lors de la récupération")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(categories)
                .build();
    }

}
