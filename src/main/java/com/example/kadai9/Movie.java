package com.example.kadai9;

public class Movie {
    private int id;
    private String movieName;
    private int publishedYear;

    public Movie(int id, String movieName, int publishedYear) {
        this.id = id;
        this.movieName = movieName;
        this.publishedYear = publishedYear;
    }

    public int getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public int getPublishedYear() {
        return publishedYear;
    }
}
