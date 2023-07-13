package com.example.kadai9.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class MovieForm {
    @NotBlank
    private String movieTitle;

    @Positive
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
