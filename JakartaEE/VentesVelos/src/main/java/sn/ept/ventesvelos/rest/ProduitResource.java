package sn.ept.ventesvelos.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
}
