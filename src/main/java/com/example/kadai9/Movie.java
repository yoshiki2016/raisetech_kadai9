package com.example.kadai9;

public class Movie {
    private int id;
    private String movieTitle;
    private int publishedYear;

    public Movie(int id, String movieTitle, int publishedYear) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.publishedYear = publishedYear;
    }

    public int getId() {
        return id;
    }

    public String getmovieTitle() {
        return movieTitle;
    }

    public int getPublishedYear() {
        return publishedYear;
    }
}
