package com.example.kadai9;

public class MovieForm {
    private int id;
    private String movieTitle;
    private int publishedYear;

    public MovieForm(String movieTitle, int publishedYear) {
        this.movieTitle = movieTitle;
        this.publishedYear = publishedYear;
    }

    public int getId() {
        return id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public int getPublishedYear() {
        return publishedYear;
    }
}
