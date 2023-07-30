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

    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Suppression de marque",
            description = "Cet endpoint est utilisé pour supprimer les marques de la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "La marque a été supprimée de la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de la suppression de la marque"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "La marque correspondant au numéro est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Marque introuvable")
                                            }
                                    )
                            }
                    )
            }
    )
    public Response deleteMarque(
            @PathParam("id")
            @Parameter(description = "L'id de la marque", required = true)
            int id
    ) {
        Marque tmp = (Marque) marqueFacade.find(id);
        if (tmp == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("La marque d'id "+ id +" n'a pas pu être trouvée")).build();
        }
        marqueFacade.remove(tmp);
        return Response.status(Response.Status.OK).entity(new Reponse("La marque d'id "+ id +" n'a pas pu être trouvée")).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Ajout d'une marque",
            description = "Cet endpoint est utilisé pour ajouter les marque à la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "La marque a été enregistrée à la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de l'enregistrement de la marque"
                    )
            }
    )
    public Response addMarque(
            @Parameter(
                    name = "Marque",
                    description = "La marque que vous souhaitez ajouter",
                    required = true
            )
            Marque m
    ) {
        if (m.getId() != null) {
            Marque tmp = (Marque) marqueFacade.find(m.getId());
            if (tmp != null) {
                marqueFacade.edit(m);
            } else {
                marqueFacade.create(m);
            }
        } else {
            marqueFacade.create(m);
        }
        return Response
                .status(Response.Status.OK)
                .entity(m)
                .build();
    }

}
