package com.lumistream.jersey.resources;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.lumistream.jersey.movies.MovieOperations;
import com.lumistream.jersey.movies.Movie;

@Path("movie")

public class MovieResources {
    @Path("{title}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Movie getMovieInfo(@PathParam("title") String title){
        return MovieOperations.getMovie(title);
    }
}
