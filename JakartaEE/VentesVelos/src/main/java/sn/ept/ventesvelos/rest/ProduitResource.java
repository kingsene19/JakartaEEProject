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
import sn.ept.ventesvelos.entites.Marque;
import sn.ept.ventesvelos.entites.Produit;
import sn.ept.ventesvelos.entites.Stock;
import sn.ept.ventesvelos.facades.ProduitFacade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/produit")
public class ProduitResource {

    @EJB
    private ProduitFacade produitFacade;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Liste des produits",
            description = "Cet endpoint est utilisé pour obtenir l'ensemble des produits disponible",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "L'ensemble des produits ont été retourné"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produite lors de la récupération des produits"
                    )
            }
    )
    public Response getProduitList() {
        List<Produit> tmp = produitFacade.findAll();
        if (tmp == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("Une erreur s'est produit lors de la récupération")).build();
        }
        return Response.status(Response.Status.OK)
                .entity(tmp)
                .build();
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Trouver un produit",
            description = "Cet endpoint est utilisé pour obtenir les informations sur un projet spécifique",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Le produit recherché a été retourné"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Produit correspondant à l'id indiqué est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Produit introuvable")
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produite lors de la récupération"
                    )
            }
    )
    public Response getProduit(
            @PathParam("id")
            @Parameter(description = "L'identifiant du produit", required = true)
            int id
    ) {
        Produit tmp = (Produit) produitFacade.find(id);
        if (tmp == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new Reponse("Le produit d'id "+ id + " n'a pas pu être trouvé"))
                    .build();
        }
        List<Stock> stocks = (List<Stock>) tmp.getStockCollection();
        Map<String, Object> responseEntity = new HashMap<>();
        responseEntity.put("produit", tmp);
        responseEntity.put("stocks", stocks);
        return Response
                .status(Response.Status.OK)
                .entity(responseEntity)
                .build();
    }

    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Suppression de produit",
            description = "Cet endpoint est utilisé pour supprimer les produit de la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Le produit a été supprimé de la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de la suppression du produit"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Le produit correspondant au numéro est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Produit introuvable")
                                            }
                                    )
                            }
                    )
            }
    )
    public Response deleteProduit(
            @PathParam("id")
            @Parameter(description = "L'id du produit", required = true)
            int id
    ) {
        Produit tmp = (Produit) produitFacade.find(id);
        if (tmp == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("La produit d'id "+ id +" n'a pas pu être trouvée")).build();
        }
        produitFacade.remove(tmp);
        return Response.status(Response.Status.OK).entity(new Reponse("La marque de produit "+ id +" n'a pas pu être trouvée")).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Ajout d'un produit",
            description = "Cet endpoint est utilisé pour ajouter les produits à la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Le produit a été enregistré à la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de l'enregistrement du produit"
                    )
            }
    )
    public Response addMarque(
            @Parameter(
                    name = "Produit",
                    description = "Le produit que vous souhaitez ajouter",
                    required = true
            )
            Categorie c
    ) {
        Produit tmp = (Produit) produitFacade.find(c.getId());
        if (tmp != null) {
            produitFacade.edit(c);
        } else {
            produitFacade.create(c);
        }
        return Response
                .status(Response.Status.OK)
                .entity(c)
                .build();
    }
}
