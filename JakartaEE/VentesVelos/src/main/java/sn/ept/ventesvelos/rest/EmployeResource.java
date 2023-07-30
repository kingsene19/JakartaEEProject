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
import sn.ept.ventesvelos.facades.EmployeFacade;

import java.util.Collection;
import java.util.List;


@Path("/employe")
public class EmployeResource {

    @EJB
    private EmployeFacade employeFacade;

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Inscription ou Modification des employes",
            description = "Cet endpoint est utilisé pour enregistrer les employes dans la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "L'employe a été enregistré à la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de l'enregistrement de l'employe"
                    )
            }
    )
    public Response addEmploye(
            @Parameter(
                    name = "Employe",
                    description = "L'employé que vous souhaiter enregistrer ou modifier",
                    required = true
            )
            Employe e
    ) {
        if (e.getId() != null) {
            Employe tmp = (Employe) employeFacade.find(e.getId());
            if (tmp != null) {
                employeFacade.edit(e);
            } else {
                employeFacade.create(e);
            }
        } else {
            employeFacade.create(e);
        }
        return Response
                .status(Response.Status.OK)
                .entity(e)
                .build();
    }

    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Suppression de l'employe",
            description = "Cet endpoint est utilisé pour supprimer les employés de la base de données",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "L'employe a été supprimé de la base de données"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Une erreur s'est produit lors de la suppression de l'employe"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "L'employe correspondant à l'id est introuvable",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            examples =  {
                                                    @ExampleObject(name="Employe introuvable")
                                            }
                                    )
                            }
                    )
            }
    )
    public Response deleteEmploye(
            @PathParam("id")
            @Parameter(description = "L'id du client", required = true)
            int id
    ) {
        Employe tmp = (Employe) employeFacade.find(id);
        if (tmp == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("L'employe d'id "+ id +" n'a pas pu être trouvé")).build();
        }
        employeFacade.remove(tmp);
        return Response.status(Response.Status.OK).entity(new Reponse("L'employe d'id "+ id +" n'a pas pu être trouvé")).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Liste des employés",
            description = "Cet endpoint est utilisé pour obtenir l'ensemble des employés disponibles"
    )
    public Response getEmployeList() {
        List<Employe> emps = employeFacade.findAll();
        if (emps == null ) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Reponse("Un problème s'est produit lors de la récupération")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(emps)
                .build();
    }

}
