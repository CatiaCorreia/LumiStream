package com.lumistream.jersey.movies;

public class Movie {
    Integer movie_id;
    String title;
    String description;
    String genre;
    Integer release_year;
    String video_url_mp4;
    String video_url_360p;
    String video_url_1080p;
    Double duration;
    Double rating;

    public Movie(Integer movie_id, String title, String description, String genre, Integer release_year,
                 String video_url_mp4, String video_url_360p, String video_url_1080p,
                 Double duration, Double rating) {
        this.movie_id = movie_id != null ? movie_id : 0;
	    this.title = title != null ? title : "";
        this.description = description != null ? description : "";
        this.genre = genre != null ? genre : "Unknown";
        this.release_year = release_year != null ? release_year : 0;
        this.video_url_mp4 = video_url_mp4 != null ? video_url_mp4 : "default_video_url_mp4";
        this.video_url_360p = video_url_360p != null ? video_url_360p : "default_video_360p_url";
        this.video_url_1080p = video_url_1080p != null ? video_url_1080p : "default_video_1080p_url";
        this.duration = duration != null ? duration : 0.0;
        this.rating = rating != null ? rating : 0.0;
    }

    public Integer getMovieId() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getReleaseYear() {
        return release_year;
    }

    public String getVideoUrlMp4() {
        return video_url_mp4;
    }

    public String getVideoUrl360p() {
        return video_url_360p;
    }

    public String getVideoUrl1080p() {
        return video_url_1080p;
    }

    public Double getDuration() {
        return duration;
    }

    public Double getRating() {
        return rating;
    }
}
