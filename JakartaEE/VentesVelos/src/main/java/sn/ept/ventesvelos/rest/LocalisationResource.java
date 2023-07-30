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
import sn.ept.ventesvelos.entites.Localisation;
import sn.ept.ventesvelos.facades.LocalisationFacade;

import java.util.List;

@Path("/localisation")
public class LocalisationResource {

    @EJB
    private LocalisationFacade localisationFacade;

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Ajout ou modification des localisations",
            description = "Cet endpoint est utilisé pour enregistrer les localisation dans la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Le localisation a été enregistré à la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de l'enregistrement de le localisation"
                    )
            }
    )
    public Response addLocalisation(
            @Parameter(
                    name = "Localisation",
                    description = "La localisation que vous souhaiter enregistrer ou modifier",
                    required = true
            )
            Localisation l
    ) {
        if (l.getId() != null) {
            Localisation tmp = (Localisation) localisationFacade.find(l.getId());
            if (tmp != null) {
                localisationFacade.edit(l);
            } else {
                localisationFacade.create(l);
            }
        } else {
            localisationFacade.create(l);
        }
        return Response
                .status(Response.Status.OK)
                .entity(l)
                .build();
    }

    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Suppression du localisation",
            description = "Cet endpoint est utilisé pour supprimer les localisations de la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Le localisation a été supprimée de la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produite lors de la suppression de le localisation"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Le localisation correspondant à l'id est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Localisation introuvable")
                                            }
                                    )
                            }
                    )
            }
    )
    public Response deleteLocalisation(
            @PathParam("id")
            @Parameter(description = "L'id de la localisation", required = true)
            int id
    ) {
        Localisation tmp = (Localisation) localisationFacade.find(id);
        if (tmp == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("La localisation d'id "+ id +" n'a pas pu être trouvée")).build();
        }
        localisationFacade.remove(tmp);
        return Response.status(Response.Status.OK).entity(new Reponse("La localisation d'id "+ id +" n'a pas pu être trouvée")).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Liste des localisations",
            description = "Cet endpoint est utilisé pour obtenir l'ensemble des localisations disponibles"
    )
    public Response getLocalisationList() {
        List<Localisation> locas = localisationFacade.findAll();
        if (locas == null ) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("Un problème s'est produit lors de la récupération")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(locas)
                .build();
    }
}
