package edu.upc.dsa.services;

import edu.upc.dsa.models.Question;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Api(value = "/question", description = "Endpoint to handle user questions")
@Path("/question")
public class QuestionService {
    private static final Logger logger = Logger.getLogger(QuestionService.class);

    @POST
    @ApiOperation(value = "submit a new question", notes = "Submit a new question to the application")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Question submitted successfully"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitQuestion(Question question, @Context SecurityContext securityContext) {
        logger.info("Received question:");
        logger.info("Date: " + question.getDate());
        logger.info("Title: " + question.getTitle());
        logger.info("Message: " + question.getMessage());
        logger.info("Sender: " + question.getSender());

        String jsonResponse = "{\"message\": \"Question submitted successfully\"}";
        return Response.status(201).entity(jsonResponse).build();
    }
}