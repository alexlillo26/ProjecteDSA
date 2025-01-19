package edu.upc.dsa.services;

import edu.upc.dsa.models.Denuncia;
import edu.upc.dsa.orm.dao.DenunciaDAO;
import edu.upc.dsa.orm.dao.DenunciaDAOImpl;
import edu.upc.dsa.orm.FactorySession;
import edu.upc.dsa.orm.Session;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Api(value = "/denuncia", description = "Endpoint to handle user denuncias")
@Path("/denuncia")
public class DenunciaService {
    private static final Logger logger = Logger.getLogger(DenunciaService.class);
    private DenunciaDAO denunciaDAO;

    public DenunciaService() {
        Session session = FactorySession.openSession();
        this.denunciaDAO = new DenunciaDAOImpl(session);
    }

    @POST
    @ApiOperation(value = "submit a new denuncia", notes = "Submit a new denuncia to the application")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Denuncia submitted successfully"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitDenuncia(Denuncia denuncia, @Context SecurityContext securityContext) {
        logger.info("Received denuncia:");
        logger.info("Title: " + denuncia.getTitle());
        logger.info("Message: " + denuncia.getMessage());
        logger.info("Sender: " + denuncia.getSender());

        String result = denunciaDAO.addDenuncia(denuncia.getTitle(), denuncia.getMessage(), denuncia.getSender());
        if ("Success".equals(result)) {
            String jsonResponse = "{\"message\": \"Denuncia submitted successfully\"}";
            return Response.status(201).entity(jsonResponse).build();
        } else {
            return Response.status(500).entity("{\"message\": \"Internal Server Error\"}").build();
        }
    }

    @PUT
    @Path("/{id}")
    @ApiOperation(value = "update a denuncia", notes = "Update an existing denuncia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Denuncia updated successfully"),
            @ApiResponse(code = 404, message = "Denuncia not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDenuncia(@PathParam("id") String id, Denuncia denuncia) {
        denunciaDAO.updateDenuncia(id, denuncia.getTitle(), denuncia.getMessage());
        return Response.status(200).entity("{\"message\": \"Denuncia updated successfully\"}").build();
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "delete a denuncia", notes = "Delete an existing denuncia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Denuncia deleted successfully"),
            @ApiResponse(code = 404, message = "Denuncia not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDenuncia(@PathParam("id") String id) {
        denunciaDAO.deleteDenunciaByID(id);
        return Response.status(200).entity("{\"message\": \"Denuncia deleted successfully\"}").build();
    }

    @GET
    @ApiOperation(value = "get all denuncias", notes = "Get all denuncias")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Denuncias retrieved successfully"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDenuncias() {
        List<Denuncia> denuncias = denunciaDAO.getDenuncias();
        return Response.status(200).entity(denuncias).build();
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "get a denuncia by ID", notes = "Get a denuncia by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Denuncia retrieved successfully"),
            @ApiResponse(code = 404, message = "Denuncia not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDenunciaByID(@PathParam("id") String id) {
        Denuncia denuncia = denunciaDAO.getDenunciaByID(id);
        if (denuncia != null) {
            return Response.status(200).entity(denuncia).build();
        } else {
            return Response.status(404).entity("{\"message\": \"Denuncia not found\"}").build();
        }
    }
}