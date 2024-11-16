package edu.upc.dsa.services;


import edu.upc.dsa.UserManager;
import edu.upc.dsa.UserManagerImpl;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import org.apache.log4j.Logger;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;




@Api(value = "/users", description = "Endpoint to user Service")
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class);
    private UserManager us;
    public UserService() {
        this.us = UserManagerImpl.getInstance();
        if (us.size()==0) {
            this.us.addUser("Admin", "admin", "admin");
            this.us.addUser("user1", "User1", "notadmin");
            this.us.addUser("user2", "User2", "notadmin" );
        }
    }

    @Provider
    @Priority(Priorities.AUTHENTICATION)
    public class AuthFilter implements ContainerRequestFilter {

        @Override
        public void filter(ContainerRequestContext requestContext) throws IOException {
            // Captura los encabezados personalizados
            String username = requestContext.getHeaderString("X-Username");
            String role = requestContext.getHeaderString("X-Role");

            if (username != null && role != null) {
                // Crear un contexto de seguridad personalizado basado en el encabezado "X-Role"
                SecurityContext securityContext = new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return () -> username;
                    }

                    @Override
                    public boolean isUserInRole(String r) {
                        // Si "role" es "admin", el usuario es administrador
                        return "admin".equalsIgnoreCase(role) && "admin".equalsIgnoreCase(r);
                    }

                    @Override
                    public boolean isSecure() {
                        return requestContext.getUriInfo().getAbsolutePath().toString().startsWith("https");
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return "CustomAuth";
                    }
                };
                // Establece el SecurityContext personalizado
                requestContext.setSecurityContext(securityContext);
            } else {
                // Si faltan los encabezados, aborta con una respuesta de Unauthorized
                requestContext.abortWith(Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Unauthorized: Missing authentication headers\"}")
                        .build());
            }
        }
    }

    @GET
    @ApiOperation(value = "get all User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
    })
    // Elimina @Path("/") porque ya tienes /tracks a nivel de clase
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {
        List<User> users = this.us.findAll();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.ok(entity).build();
    }

    @GET
    @ApiOperation(value = "get a User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username) {
        User u = this.us.getUserByUsername(username);
        if (u == null) return Response.status(404).build();
        else return Response.status(201).entity(u).build();
    }

    @DELETE
    @ApiOperation(value = "delete a User", notes = "Elimina un usuario específico si es un administrador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario eliminado exitosamente"),
            @ApiResponse(code = 401, message = "No autenticado"),
            @ApiResponse(code = 403, message = "No autorizado"),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("username") String username, @Context SecurityContext securityContext) {
        try {
            // Verificar si el usuario está autenticado
            if (securityContext.getUserPrincipal() == null) {
                logger.info("Usuario no autenticado intentó eliminar un usuario.");
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Usuario no autenticado\"}")
                        .build();
            }

            // Verificar si el usuario tiene permisos de administrador
            if (!securityContext.isUserInRole("admin")) {
                logger.info("Usuario sin permisos de admin: " + securityContext.getUserPrincipal().getName());
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"message\": \"No tienes permiso para realizar esta acción\"}")
                        .build();
            }

            // Procede con la eliminación del usuario
            User u = this.us.getUserByUsername(username);
            if (u == null) {
                logger.warn("Usuario no encontrado: " + username);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Usuario no encontrado\"}")
                        .build();
            }

            this.us.deleteUser(username);
            logger.info("Usuario eliminado: " + username);
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Usuario eliminado exitosamente\"}")
                    .build();

        } catch (Exception e) {
            logger.error("Error al eliminar usuario: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error interno del servidor\"}")
                    .build();
        }
    }


    @PUT
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "update a User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    public Response updateUser(@PathParam("username") String username, User user) {
        User existingUser = this.us.getUserByUsername(username);
        if (existingUser == null) {
            return Response.status(404).entity("Usuario no encontrado").build();
        }
        existingUser.setIsAdmin(user.getIsAdmin()); // Actualiza el estado de admin
        this.us.updateUser(existingUser);
        return Response.status(200).entity(existingUser).build();
    }

    @POST
    @ApiOperation(value = "create a new User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User user) {
        if (user.getPassword() == null || user.getUsername() == null) {
            return Response.status(500).entity(user).build();
        }
        this.us.addUser(user.getUsername(), user.getPassword(), user.getIsAdmin());
        return Response.status(201).entity(user).build();
    }

    @POST
    @ApiOperation(value = "login a User", notes = "Login a user with username and password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful login"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        try {
            User storedUser = this.us.getUserByUsername(user.getUsername());
            logger.info("Buscando usuario: " + user.getUsername());

            if (storedUser == null || !BCrypt.checkpw(user.getPassword(), storedUser.getPassword())) {
                logger.warn("Credenciales incorrectas para el usuario: " + user.getUsername());
                return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\": \"Credenciales incorrectas\"}").build();
            }

            String role = storedUser.getIsAdmin().equals("admin") ? "admin" : "user";

            return Response.ok()
                    .header("X-Username", user.getUsername())
                    .header("X-Role", role)
                    .entity("{\"message\": \"Login exitoso\", \"redirect\": \"" + (role.equals("admin") ? "admin.html" : "user.html") + "\"}")
                    .build();
        } catch (Exception e) {
            logger.error("Error al iniciar sesión: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\": \"Error interno del servidor\"}").build();
        }
    }



}