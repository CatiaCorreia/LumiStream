package com.lumistream.jersey.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.lumistream.jersey.user.User;
import com.lumistream.jersey.user.UserSupervisor;

@Path("media/{username}/{pass}")

public class UserResources {

    private final Integer APP1 = 1;
    private static String URL = "jdbc:sqlite:LumiStream/src/main/databases/user.db";

    @Path("/addUser")
    @POST
    public void addUser(@PathParam("username") String username, @PathParam("pass") String userpass) {
        User.addUser(username, userpass, URL);
    }

    @Path("/login")
    @POST
    public void login(@PathParam("username") String username, @PathParam("pass") String userpass) {

        UserSupervisor.loginUser(username, userpass, APP1, URL);

    }

    @Path("/logout")
    @POST
    public void logout(@PathParam("username") String username, @PathParam("pass") String userpass) {
        UserSupervisor.logoutUser(username, userpass, APP1);
    }

    @Path("/delete")
    @POST
    public void deleteUser(@PathParam("username") String username, @PathParam("pass") String userpass) {
        User.deleteUser(username, URL);
    } 

}