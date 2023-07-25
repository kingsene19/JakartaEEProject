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
import sn.ept.ventesvelos.entites.Employe;
import sn.ept.ventesvelos.entites.Magasin;
import sn.ept.ventesvelos.facades.MagasinFacade;

import java.util.Collection;
import java.util.List;


@Path("/magasin")
public class MagasinResource {

    @EJB
    private MagasinFacade magasinFacade;

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Inscription ou Modification des magasins",
            description = "Cet endpoint est utilisé pour enregistrer les magasin dans la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Le magasin a été enregistré à la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de l'enregistrement de le magasin"
                    )
            }
    )
    public Response addMagasin(
            @Parameter(
                    name = "Magasin",
                    description = "Le magasin que vous souhaiter enregistrer ou modifier",
                    required = true
            )
            Magasin m
    ) {
        Magasin tmp = (Magasin) magasinFacade.find(m.getId());
        if (tmp != null) {
            magasinFacade.edit(m);
        } else {
            magasinFacade.create(m);
        }
        return Response
                .status(Response.Status.OK)
                .entity(m)
                .build();
    }

    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Suppression du magasin",
            description = "Cet endpoint est utilisé pour supprimer les magasins de la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Le magasin a été supprimé de la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de la suppression de le magasin"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Le magasin correspondant à l'id est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Magasin introuvable")
                                            }
                                    )
                            }
                    )
            }
    )
    public Response deleteMagasin(
            @PathParam("id")
            @Parameter(description = "L'id du magasin", required = true)
            int id
    ) {
        Magasin tmp = (Magasin) magasinFacade.find(id);
        if (tmp == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("Le magasin d'id "+ id +" n'a pas pu être trouvé")).build();
        }
        magasinFacade.remove(tmp);
        return Response.status(Response.Status.OK).entity(new Reponse("Le magasin d'id "+ id +" n'a pas pu être trouvé")).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Liste des magasins",
            description = "Cet endpoint est utilisé pour obtenir l'ensemble des magasins disponibles"
    )
    public Response getMagasinList() {
        List<Magasin> mags = magasinFacade.findAll();
        if (mags == null ) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("Un problème s'est produit lors de la récupération")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(mags)
                .build();
    }

}
