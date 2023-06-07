package com.example.kadai9;

import java.util.List;

public interface MovieService {
    List<Movie> findMovies(Integer publishedYear);
    Movie findMovieById(int id);
    Movie createMovie(MovieForm movieForm);
    void updateMovie(Movie movie);
    void deleteMovie(int id);
}
