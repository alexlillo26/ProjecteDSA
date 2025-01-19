package edu.upc.dsa.services;

import edu.upc.dsa.models.Question;
import edu.upc.dsa.orm.dao.QuestionDAO;
import edu.upc.dsa.orm.dao.QuestionDAOImpl;
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

@Api(value = "/question", description = "Endpoint to handle user questions")
@Path("/question")
public class QuestionService {
    private static final Logger logger = Logger.getLogger(QuestionService.class);
    private QuestionDAO questionDAO;

    public QuestionService() {
        Session session = FactorySession.openSession();
        this.questionDAO = new QuestionDAOImpl(session);
    }

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
        logger.info("Title: " + question.getTitle());
        logger.info("Message: " + question.getMessage());
        logger.info("Sender: " + question.getSender());

        String result = questionDAO.addQuestion(question.getTitle(), question.getMessage(), question.getSender());
        if ("Success".equals(result)) {
            String jsonResponse = "{\"message\": \"Question submitted successfully\"}";
            return Response.status(201).entity(jsonResponse).build();
        } else {
            return Response.status(500).entity("{\"message\": \"Internal Server Error\"}").build();
        }
    }

    @PUT
    @Path("/{id}")
    @ApiOperation(value = "update a question", notes = "Update an existing question")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Question updated successfully"),
            @ApiResponse(code = 404, message = "Question not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateQuestion(@PathParam("id") String id, Question question) {
        questionDAO.updateQuestion(id, question.getTitle(), question.getMessage());
        return Response.status(200).entity("{\"message\": \"Question updated successfully\"}").build();
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "delete a question", notes = "Delete an existing question")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Question deleted successfully"),
            @ApiResponse(code = 404, message = "Question not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteQuestion(@PathParam("id") String id) {
        questionDAO.deleteQuestionByID(id);
        return Response.status(200).entity("{\"message\": \"Question deleted successfully\"}").build();
    }

    @GET
    @ApiOperation(value = "get all questions", notes = "Get all questions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Questions retrieved successfully"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestions() {
        List<Question> questions = questionDAO.getQuestions();
        return Response.status(200).entity(questions).build();
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "get a question by ID", notes = "Get a question by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Question retrieved successfully"),
            @ApiResponse(code = 404, message = "Question not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestionByID(@PathParam("id") String id) {
        Question question = questionDAO.getQuestionByID(id);
        if (question != null) {
            return Response.status(200).entity(question).build();
        } else {
            return Response.status(404).entity("{\"message\": \"Question not found\"}").build();
        }
    }
}