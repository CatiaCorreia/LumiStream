
package com.lumistream.jersey.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import com.lumistream.jersey.movies.MovieOperations;
import com.lumistream.jersey.movies.Movie;

@Path("movie")
public class MovieResources {

    @Path("{title}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieInfo(@PathParam("title") String title) {
	System.out.println("Title received: " + title);
        Movie movie = MovieOperations.getMovie(title);
        if (movie == null) {
            // debbug  - return 404 if the movie is not found
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"error\": \"Movie not found\"}")
                           .build();
        }
        // debbug - return 200 OK with the movie object as JSON
        return Response.ok(movie).build();
    }

    @Path("movies")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movie> getMoviesList(@QueryParam("limit") int limit) {
        return MovieOperations.getListMovies(limit);
}

}
