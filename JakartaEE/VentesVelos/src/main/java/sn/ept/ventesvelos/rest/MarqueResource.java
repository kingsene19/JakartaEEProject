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
import sn.ept.ventesvelos.entites.Marque;
import sn.ept.ventesvelos.entites.Produit;
import sn.ept.ventesvelos.facades.MarqueFacade;

import java.util.List;

@Path("/marque")
public class MarqueResource {

    @EJB
    private MarqueFacade marqueFacade;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Liste des marques",
            description = "Cet endpoint est utilisé pour obtenir l'ensemble des marques de produits disponible",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "L'ensemble des marques ont été retourné"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produite lors de la récupération des marques"
                    )
            }
    )
    public Response getMarqueList() {
        List<Marque> marques = marqueFacade.findAll();
        if (marques == null ) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("Un problème s'est produit lors du chargement")).build();
        }
        return Response.status(Response.Status.OK)
                .entity(marques)
                .build();

    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Trouver les produits",
            description = "Cet endpoint est utilisé pour obtenir l'ensemble des produits appartenant à une marque",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "L'ensemble des produits ont été retourné"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Marque correspondant à l'id indiqué est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Marque introuvable")
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
            @Parameter(description = "L'identifiant de la marque", required = true)
            int id
    ) {
        Marque tmp = (Marque) marqueFacade.find(id);
        if (tmp == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new Reponse("La marque d'id "+ id + " n'a pas pu être trouvé"))
                    .build();
        }
        List<Produit> produits = (List<Produit>) tmp.getProduitCollection();
        return Response
                .status(Response.Status.OK)
                .entity(produits)
                .build();
    }

}
