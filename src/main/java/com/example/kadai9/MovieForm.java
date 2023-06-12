package com.example.kadai9;

import jakarta.validation.constraints.NotBlank;

public class MovieForm {
    @NotBlank
    private String movieTitle;

    @NotBlank
    private int publishedYear;

    public MovieForm(String movieTitle, int publishedYear) {
        this.movieTitle = movieTitle;
        this.publishedYear = publishedYear;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public int getPublishedYear() {
        return publishedYear;
    }
}
