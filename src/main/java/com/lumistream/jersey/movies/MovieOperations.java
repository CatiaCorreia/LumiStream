package com.lumistream.jersey.movies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieOperations {
    private static String SQL = "jdbc:sqlite:LumiStream/src/main/databases/movies.db";

    public static Movie getMovie(String title){
        Movie movie = null;
        String query = "SELECT (description, genre, release_year, poster_url, video_url_360p, video_url_1080p, duration, rating) from movies where title = " + title;

        try (Connection conn = DriverManager.getConnection(SQL)) {
            PreparedStatement pstm = conn.prepareStatement(query);
            ResultSet res = pstm.executeQuery();
            String description = res.getString("description");
            String genre = res.getString("genre");
            Integer release_year = res.getInt("release_year");
            String poster_url = res.getString("poster_url");
            String video_url_360p = res.getString("video_url_360p");
            String video_url_1080p = res.getString("video_url_1080p");
            Double duration = res.getDouble("duration");
            Double rating = res.getDouble("reting");
            movie = new Movie(title, description, genre, release_year, poster_url, video_url_360p, video_url_1080p, duration, rating);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return movie;
    }

    public void addMovie(String title, String description, String genre, Integer release_year, String poster, Double duration, Double rating){
        
        //add movie entry into the database, the file into the ningx, and convert it into low resolution
    }

    public void deleteMovie(){
        //remove movie from the database and delete the files
    }
}
