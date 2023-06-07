package com.example.kadai9;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> findMovies(Integer publishedYear);
    Movie findMovieById(int id);
    Movie createMovie(CreateMovieForm createMovieForm);
    void updateMovie(Movie movie);
    void deleteMovie(int id);
}
