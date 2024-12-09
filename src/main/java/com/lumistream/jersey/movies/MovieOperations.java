package com.lumistream.jersey.movies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieOperations {
   	//private static String SQL = "jdbc:sqlite:LumiStream/src/main/databases/movies.db";
	private static String SQL = "jdbc:sqlite:src/main/databases/movies.db";
	
    public static Movie getMovie(String title){
        Movie movie = null;
        //String query = "SELECT (description, genre, release_year, poster_url, video_url_360p, video_url_1080p, duration, rating) from movies where title = " + title;
	    String query = "SELECT movie_id, title, description, genre, release_year, poster_url, video_url_360p, video_url_1080p, duration, rating FROM movies WHERE title = ?";
        
        try (Connection conn = DriverManager.getConnection(SQL)) {
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, title); // parameterized query to prevent SQL injection
            ResultSet res = pstm.executeQuery();
                
            if (res.next()) { 
            
                System.out.println("Movie found: " + title);

                int movie_id = res.getInt("movie_id");
                String description = res.getString("description");
                if(description == null) description = "";

                String genre = res.getString("genre");
                if(genre == null) genre = "Unknown";

                Integer release_year = res.getInt("release_year");
                if (res.wasNull()) release_year = 0; 
                
                String poster_url = res.getString("poster_url");
                if (poster_url == null) poster_url = "default_poster_url";

                String video_url_360p = res.getString("video_url_360p");
                if(video_url_360p == null) video_url_360p = "default_video_360p_url";
                
                String video_url_1080p = res.getString("video_url_1080p");
                if(video_url_1080p == null) video_url_1080p =  "default_video_1080p_url";

                Double duration = res.getDouble("duration");
                if (res.wasNull()) duration = 0.0; 
                
                Double rating = res.getDouble("rating");
                if (res.wasNull()) rating = 0.0; 


                movie = new Movie(movie_id, title, description, genre, release_year, poster_url, video_url_360p, video_url_1080p, duration, rating);
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
        
        String query = "SELECT movie_id, title, description, genre, release_year, poster_url, video_url_360p, video_url_1080p, duration, rating FROM movies LIMIT ?";
        
        try (Connection conn = DriverManager.getConnection(SQL)) {
            PreparedStatement pstm = conn.prepareStatement(query);
            
            pstm.setInt(1, limit); // parameterized query to prevent SQL injection
            ResultSet res = pstm.executeQuery();
                
            while (res.next()) { 
            
                int movie_id = res.getInt("movie_id");
                String title = res.getString("title");
                String description = res.getString("description");
                if(description == null) description = "";

                String genre = res.getString("genre");
                if(genre == null) genre = "Unknown";

                Integer release_year = res.getInt("release_year");
                if (res.wasNull()) release_year = 0; 
                
                String poster_url = res.getString("poster_url");
                if (poster_url == null) poster_url = "default_poster_url";

                String video_url_360p = res.getString("video_url_360p");
                if(video_url_360p == null) video_url_360p = "default_video_360p_url";
                
                String video_url_1080p = res.getString("video_url_1080p");
                if(video_url_1080p == null) video_url_1080p =  "default_video_1080p_url";

                Double duration = res.getDouble("duration");
                if (res.wasNull()) duration = 0.0; 
                
                Double rating = res.getDouble("rating");
                if (res.wasNull()) rating = 0.0; 


                movies.add(new Movie(movie_id, title, description, genre, release_year, poster_url, video_url_360p, video_url_1080p, duration, rating));
            }
                
        } catch (SQLException e) {
            System.out.println("Error fetching movie: " + e.getMessage());
        }
        return movies;
    }    

    public void addMovie(Integer movie_id, String title, String description, String genre, Integer release_year,
                        String poster_url, String video_url_360p, String video_url_1080p,
                        Double duration, Double rating){
        
        //add movie entry into the database, the file into the ningx, and convert it into low resolution
    }

    public void deleteMovie(){
        //remove movie from the database and delete the files
    }
}
