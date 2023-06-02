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

    public void setId(int id) {
        this.id = id;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }
}
