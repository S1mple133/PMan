package me.s1mple133;

import me.s1mple133.controller.UserController;
import me.s1mple133.model.User;
import me.s1mple133.model.UserModel;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/auth")
public class Authentication {

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response login(UserModel user) {
        User actUser = UserController.getInstance().login(user.username, user.password);

        if (actUser == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok().entity(actUser.getSessionID().toString()).build();
    }

    @POST
    @Path("/register")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response register(UserModel user) {
        if (!UserController.getInstance().register(user.username, user.password, user.email)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        return Response.ok().build();
    }
}