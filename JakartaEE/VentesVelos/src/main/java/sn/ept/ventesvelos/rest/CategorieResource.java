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
import sn.ept.ventesvelos.entites.Categorie;
import sn.ept.ventesvelos.entites.Produit;
import sn.ept.ventesvelos.facades.CategorieFacade;

import java.util.List;

@Path("/categorie")
public class CategorieResource {

    @EJB
    private CategorieFacade categorieFacade;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Liste des catégories",
            description = "Cet endpoint est utilisé pour obtenir l'ensemble des catégories de produits disponible"
    )
    public Response getCategorieList() {
        List<Categorie> categories = categorieFacade.findAll();
        if (categories == null ) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("Un problème s'est produit lors du chargement")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(categories)
                .build();
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Trouver les produits",
            description = "Cet endpoint est utilisé pour obtenir l'ensemble des produits appartenant à une catégorie",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "L'ensemble des produits ont été retourné"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Catégorie correspondant à l'id indiqué est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Catégorie introuvable")
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produite lors de la récupération des produits"
                    )
            }
    )
    public Response getProduitList(
            @PathParam("id")
            @Parameter(description = "L'identifiant de la catégorie", required = true)
            int id
    ) {
        Categorie tmp = (Categorie) categorieFacade.find(id);
        if (tmp == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new Reponse("La catégorie d'id "+ id + " n'a pas pu être trouvé"))
                    .build();
        }
        List<Produit> produits = (List<Produit>) tmp.getProduitCollection();
        return Response
                .status(Response.Status.OK)
                .entity(produits)
                .build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Ajout d'une catégorie",
            description = "Cet endpoint est utilisé pour ajouter les catégories à la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "La catégorie a été enregistrée à la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de l'enregistrement de la catégorie"
                    )
            }
    )
    public Response addCategorie(
            @Parameter(
                    name = "Catégorie",
                    description = "La catégorie que vous souhaitez ajouter",
                    required = true
            )
            Categorie c
    ) {
        if (c.getId() != null) {
            Categorie tmp = (Categorie) categorieFacade.find(c.getId());
            if (tmp != null) {
                categorieFacade.edit(c);
            } else {
                categorieFacade.create(c);
            }
        } else {
            categorieFacade.create(c);
        }
        return Response
                .status(Response.Status.OK)
                .entity(c)
                .build();
    }

    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Suppression de catégorie",
            description = "Cet endpoint est utilisé pour supprimer les catégorie de la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "La catégorie a été supprimée de la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de la suppression de la catégorie"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "La catégorie correspondant à l'id est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Categorie introuvable")
                                            }
                                    )
                            }
                    )
            }
    )
    public Response deleteCategorie(
            @PathParam("id")
            @Parameter(description = "L'id de la categorie", required = true)
            int id
    ) {
        Categorie tmp = (Categorie) categorieFacade.find(id);
        if (tmp == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("La categorie d'id "+ id +" n'a pas pu être trouvé")).build();
        }
        categorieFacade.remove(tmp);
        return Response.status(Response.Status.OK).entity(new Reponse("La categorie d'id "+ id +" n'a pas pu être trouvé")).build();
    }
}
