package com.example.kadai9.service;

import com.example.kadai9.entity.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> findMovies(Integer publishedYear);
    Movie findMovieById(int id);
    Movie createMovie(String movieTitle, int publishedYear);
    void updateMovie(int id, String movieTitle,int publishedYear);
    void deleteMovie(int id);
}
