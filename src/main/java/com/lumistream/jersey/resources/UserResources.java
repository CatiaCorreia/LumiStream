package com.lumistream.jersey.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.lumistream.jersey.user.User;

@Path("/{username}/{pass}")

public class UserResources {

    @Path("/addUser")
    @POST
    public void addUser(@PathParam("username") String username, @PathParam("pass") String userpass){
       User u = new User();
       u.addUser(username, userpass);
    }

    @Path("/login")
    @POST
    public void getUser(@PathParam("username") String username, @PathParam("pass") String userpass){

    }

    @Path("/delete")
    @DELETE
    public void deleteUser(@PathParam("username") String username){

    }

}