
package com.lumistream.jersey.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.InputStream;

import java.util.List;

import com.lumistream.jersey.movies.MovieOperations;
import com.lumistream.jersey.movies.Movie;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;


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

    @Path("/upload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadMovie(FormDataMultiPart multiPart    ){

        FormDataBodyPart filePart = multiPart.getField("file");
        InputStream uploadedInputStream = filePart.getValueAs(InputStream.class);
        FormDataContentDisposition fileDetail = filePart.getFormDataContentDisposition();

        FormDataBodyPart movieInfoPart = multiPart.getField("movieinfo");
        movieInfoPart.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        Movie movie = movieInfoPart.getValueAs(Movie.class);

        String uploadedFileName = fileDetail.getFileName();
        String directory = "/mnt/newdisk/moviesStorage/movies1080p/"; 
        File file = new File(directory + uploadedFileName);
        try{
            file.createNewFile();
        }
        catch(Exception e)
        {
             e.printStackTrace();
        }

        String outputDirectory = "/mnt/newdisk/moviesStorage/";
        MovieOperations.convertMovie(uploadedFileName, outputDirectory);
        MovieOperations.addMovie(movie, uploadedFileName.replace(".mp4", ""), outputDirectory);
        
        return Response.ok("uploaded").build();
    }

    @Path("/delete")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteMovie(Movie movie){
	MovieOperations.deleteMovie(movie);
	return Response.ok("Movie deleted").build();
    }
}

