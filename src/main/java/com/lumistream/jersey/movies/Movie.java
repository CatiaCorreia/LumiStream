package com.lumistream.jersey.movies;

public class Movie {
    Integer movieId;
    String title;
    String description;
    String genre;
    Integer releaseYear;
    String videoUrlMp4;
    String videoUrl360p;
    String videoUrl1080p;
    Double duration;
    Double rating;

    public Movie(Integer movieId, String title, String description, String genre, Integer releaseYear,
                 String videoUrlMp4, String videoUrl360p, String videoUrl1080p,
                 Double duration, Double rating) {
        this.movieId = movieId != null ? movieId : 0;
	    this.title = title != null ? title : "";
        this.description = description != null ? description : "";
        this.genre = genre != null ? genre : "Unknown";
        this.releaseYear = releaseYear != null ? releaseYear : 0;
        this.videoUrlMp4 = videoUrlMp4 != null ? videoUrlMp4 : "defaultVideoUrlMp4";
        this.videoUrl360p = videoUrl360p != null ? videoUrl360p : "defaultVideoUrl360p";
        this.videoUrl1080p = videoUrl1080p != null ? videoUrl1080p : "defaultVideoUrl1080p";
        this.duration = duration != null ? duration : 0.0;
        this.rating = rating != null ? rating : 0.0;
    }

    public Integer getMovieId() {
        return movieId;
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
        return releaseYear;
    }

    public String getVideoUrlMp4() {
        return videoUrlMp4;
    }

    public String getVideoUrl360p() {
        return videoUrl360p;
    }

    public String getVideoUrl1080p() {
        return videoUrl1080p;
    }

    public Double getDuration() {
        return duration;
    }

    public Double getRating() {
        return rating;
    }
}
