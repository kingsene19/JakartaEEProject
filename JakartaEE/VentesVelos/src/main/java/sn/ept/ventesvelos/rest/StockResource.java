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
import sn.ept.ventesvelos.facades.StockFacade;

import java.util.List;

@Path("/stock")
public class StockResource {

    @EJB
    private StockFacade stockFacade;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Liste des stocks",
            description = "Cet endpoint est utilisé pour obtenir l'ensemble des stocks de la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "L'ensemble des stocks ont été retourné"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produite lors de la récupération des stocks"
                    )
            }
    )
    public Response getStockList() {
        return Response
                .status(Response.Status.OK)
                .entity(stockFacade.findAll())
                .build();
    }

    @Path("/{magasin_id}/{produit_id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Trouver un stock",
            description = "Cet endpoint est utilisé pour obtenir le stock dont l'id est passé en paramètre",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Le stock a été retourné"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Stock correspondant à l'id indiqué est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Stock introuvable")
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produite lors du stock"
                    )
            }
    )
    public Response getStock(
            @PathParam("magasin_id")
            @Parameter(description = "L'identifiant du magasin", required = true)
            int magasin_id,
            @PathParam("produit_id")
            @Parameter(description = "L'identificant du produit", required = true)
            int produit_id

    ) {
        Stock tmp = (Stock) stockFacade.find(new StockPK(magasin_id, produit_id));
        if (tmp == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("Le stock d'id ("+ magasin_id +", "+ produit_id +") n'a pas pu être trouvé")).build();
        }
        return Response.status(Response.Status.OK).entity(tmp).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Ajout d'un stock",
            description = "Cet endpoint est utilisé pour ajouter ou modifier les stocks dans la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Le stock a été enregistré à la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de l'enregistrement du stock"
                    )
            }
    )
    public Response addArticleCommande(
            @Parameter(
                    name = "Stock",
                    description = "Le stock commande que vous souhaitez ajouter",
                    required = true
            )
            Stock s
    ) {
        Stock tmp = (Stock) stockFacade.find(s.getStockPK());
        if (tmp != null) {
            stockFacade.edit(s);
        } else {
            stockFacade.create(s);
        }
        return Response.status(Response.Status.OK).entity(s).build();
    }


    @Path("/{magasin_id}/{produit_id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @DELETE
    @Operation(
            summary = "Suppression de stock",
            description = "Cet endpoint est utilisé pour supprimer les stocks de la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Le stock a été supprimé de la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de la suppression du stock"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Stock correspondant à l'id indiqué est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Stock introuvable")
                                            }
                                    )
                            }
                    )
            }
    )
    public Response delete(
            @PathParam("magasin_id")
            @Parameter(description = "L'identifiant du magasin", required = true)
            int magasin_id,
            @PathParam("produit_id")
            @Parameter(description = "L'identificant du produit", required = true)
            int produit_id
    ) {
        Stock tmp = (Stock) stockFacade.find(new StockPK(magasin_id, produit_id));
        if (tmp == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("Le stock d'id ("+ magasin_id +", "+ produit_id +") n'a pas pu être trouvé")).build();
        }
        stockFacade.remove(tmp);
        return Response.status(Response.Status.OK).entity(new Reponse("Le stock d'id ("+ magasin_id +", "+ produit_id + ") a été supprimé avec succès")).build();
    }
}
