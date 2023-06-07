package com.example.kadai9;

import java.util.Objects;

public class Movie {
    private int id;
    private String movieTitle;
    private int publishedYear;

    public Movie(int id, String movieTitle, int publishedYear) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.publishedYear = publishedYear;
    }

    public Movie(String movieTitle, int publishedYear) {
        this.movieTitle = movieTitle;
        this.publishedYear = publishedYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id && publishedYear == movie.publishedYear && Objects.equals(movieTitle, movie.movieTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieTitle, publishedYear);
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
