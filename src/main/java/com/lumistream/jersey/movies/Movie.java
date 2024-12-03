package com.lumistream.jersey.movies;

public class Movie {
    String title;
    String description;
    String genre;
    Integer release_year;
    String poster;
    String video_url_360p;
    String video_url_1080p;
    Double duration;
    Double rating;

    public Movie(String title, String description, String genre, Integer release_year, String poster, String video_url_360p, String video_url_1080p, Double duration, Double rating){
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.release_year = release_year;
        this.poster = poster;
        this.video_url_360p = video_url_360p;
        this.video_url_1080p = video_url_1080p;
        this.duration = duration;
        this.rating = rating;
    }
}
