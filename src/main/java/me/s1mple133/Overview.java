package me.s1mple133;

import me.s1mple133.controller.PasswordController;
import me.s1mple133.controller.UserController;
import me.s1mple133.model.PasswordOverview;
import me.s1mple133.model.PasswordOverviewModel;
import me.s1mple133.model.User;

import java.util.UUID;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/overview")
public class Overview {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnOverview(@HeaderParam("Token") String token) {
        User actUser = UserController.getInstance().getUserBySessionID(token);

        if(actUser == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok().entity(PasswordController.getInstance().getUserOverview(actUser).toArray(PasswordOverview[]::new)).build();
    }

    @POST
    @Path("/add")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response addUser(@HeaderParam("Token") String token, PasswordOverviewModel overviewModel) {
        User actUser = UserController.getInstance().getUserBySessionID(token);

        if(actUser == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        PasswordOverview overview = new PasswordOverview(overviewModel.user, overviewModel.application, UUID.randomUUID().toString());
        if (!PasswordController.getInstance().insertUserOverview(actUser, overview)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        return Response.ok().build();
    }
}