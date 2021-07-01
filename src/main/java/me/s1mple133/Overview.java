package me.s1mple133;

import me.s1mple133.controller.PasswordController;
import me.s1mple133.controller.UserController;
import me.s1mple133.model.PasswordOverview;
import me.s1mple133.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;

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
}
