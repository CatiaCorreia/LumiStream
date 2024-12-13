package com.lumistream.jersey.movies;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieOperations {
    private static String SQL =  "jdbc:sqlite:/home/fanineto1/LumiStream/src/main/databases/movies.db";

	
    public static Movie getMovie(String title){
        Movie movie = null;
	    String query = "SELECT movieId, title, description, genre, releaseYear, videoUrlMp4, videoUrl360p, videoUrl1080p, duration, rating FROM movies WHERE title = ?";
        
        try (Connection conn = DriverManager.getConnection(SQL)) {
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, title); 
            ResultSet res = pstm.executeQuery();
                
            if (res.next()) { 
            
                System.out.println("Movie found: " + title);

                int movieId = res.getInt("movieId");
                String description = res.getString("description");
                if(description == null) description = "";

                String genre = res.getString("genre");
                if(genre == null) genre = "Unknown";

                Integer releaseYear = res.getInt("releaseYear");
                if (res.wasNull()) releaseYear = 0; 
                
                String videoUrlMp4 = res.getString("videoUrlMp4");
                if (videoUrlMp4 == null) videoUrlMp4 = "defaultVideoUrlMp4";

                String videoUrl360p = res.getString("videoUrl360p");
                if(videoUrl360p == null) videoUrl360p = "defaultVideoUrl360p";
                
                String videoUrl1080p = res.getString("videoUrl1080p");
                if(videoUrl1080p == null) videoUrl1080p =  "defaultVideoUrl1080p";

                Double duration = res.getDouble("duration");
                if (res.wasNull()) duration = 0.0; 
                
                Double rating = res.getDouble("rating");
                if (res.wasNull()) rating = 0.0; 


                movie = new Movie(movieId, title, description, genre, releaseYear, videoUrlMp4, videoUrl360p, videoUrl1080p, duration, rating);
            } else {
                System.out.println("No movie found with title: " + title);
            }
                
            } catch (SQLException e) {
                System.out.println("Error fetching movie: " + e.getMessage());
            }
            return movie;
        }

    public static List<Movie> getListMovies(int limit){
        List<Movie> movies = new ArrayList<>();
        
        String query = "SELECT movieId, title, description, genre, releaseYear, videoUrlMp4, videoUrl360p, videoUrl1080p, duration, rating FROM movies LIMIT ?";
        
        try (Connection conn = DriverManager.getConnection(SQL)) {
            PreparedStatement pstm = conn.prepareStatement(query);
            
            pstm.setInt(1, limit);
            ResultSet res = pstm.executeQuery();
                
            while (res.next()) { 
            
                int movieId = res.getInt("movieId");
                String title = res.getString("title");
                String description = res.getString("description");
                if(description == null) description = "";

                String genre = res.getString("genre");
                if(genre == null) genre = "Unknown";

                Integer releaseYear = res.getInt("releaseYear");
                if (res.wasNull()) releaseYear = 0; 

		String videoUrlMp4 = res.getString("videoUrlMp4");
                if (videoUrlMp4 == null) videoUrlMp4 = "defaultVideoUrlMp4";

                String videoUrl360p = res.getString("videoUrl360p");
                if(videoUrl360p == null) videoUrl360p = "defaultVideoUrl360p";
                
                String videoUrl1080p = res.getString("videoUrl1080p");
                if(videoUrl1080p == null) videoUrl1080p =  "defaultVideoUrl1080p";

                Double duration = res.getDouble("duration");
                if (res.wasNull()) duration = 0.0; 
                
                Double rating = res.getDouble("rating");
                if (res.wasNull()) rating = 0.0; 


                movies.add(new Movie(movieId, title, description, genre, releaseYear, videoUrlMp4, videoUrl360p, videoUrl1080p, duration, rating));
            }
                
        } catch (SQLException e) {
            System.out.println("Error fetching movie: " + e.getMessage());
        }
        return movies;
    }    

    public static void addMovie(Movie movie, String filename, String directory){
        String mp4 = directory + "movies1080p/" + filename + ".mp4";
        String hls360 = directory + "movies_hls_360p/" + filename + ".m3u8";
        String hls = directory + "movies_hls_1080p/" + filename + ".m3u8";
        String sql = "INSERT INTO movies(title, description, genre, releaseYear, videoUrlMp4, videoUrl360p, videoUrl1080p, duration, rating ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(SQL)) {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, movie.getTitle());
            pstm.setString(2, movie.getDescription());
            pstm.setString(3, movie.getGenre());
            pstm.setString(4, movie.getReleaseYear().toString());
            pstm.setString(5, mp4);
            pstm.setString(6, hls360);
            pstm.setString(7, hls);
            pstm.setString(8, movie.getDuration().toString());
            pstm.setString(9, movie.getRating().toString());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void convertMovie(String filename, String outputDir)
    {
        
        String[] commandArr360hls =
        {
            "/bin/bash", "-c",
            "cd "+ outputDir +"movies_hls_360p/ && ffmpeg -i " + filename + " -vf scale=-480:360 -codec: copy -start_number 0 -hls_time 10 -hls_list_size 0 -f hls " + filename.replace(".mp4", "") + ".m3u8"
        };

        execCommand(commandArr360hls);

        String[] commandArrhls =
        {
            "/bin/bash", "-c",
            "cd " + outputDir +"movies_hls_1080p/ && ffmpeg -i " + filename + " -codec: copy -start_number 0 -hls_time 10 -hls_list_size 0 -f hls " + filename.replace(".mp4", "") + ".m3u8"
        };

        execCommand(commandArrhls);
                
    }

    public static void deleteMovie(Movie movie){
        String sql = "DELETE FROM credential WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(SQL)) {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, movie.getTitle());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String[] command = 
        {
            "/bin/bash", "-c",
            "rm " + movie.getVideoUrl1080p()
        };
        
        execCommand(command);

        String[] command360 = 
        {
            "/bin/bash", "-c",
            "rm " + movie.getVideoUrl360p()
        };

        execCommand(command360);

        String[] commandhls = 
        {
            "/bin/bash", "-c",
            "rm " + movie.getVideoUrlMp4()
        };

        execCommand(commandhls);

    }

    public static void execCommand(String[] commandArr){
        String line;
        try
            {
            Process p = Runtime.getRuntime().exec(commandArr);
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = stdoutReader.readLine()) != null) {
                System.out.println(" .. stdout: "+line);
                }
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = stderrReader.readLine()) != null) {
                System.err.println(" .. stderr: "+line);
                }
            int retValue = p.waitFor();
            System.out.println(" .. exit code:"+Integer.toString(retValue));
            }
        catch(Exception e)
            { System.err.println(e.toString()); }
    }
}
