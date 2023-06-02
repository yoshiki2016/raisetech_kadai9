package com.example.kadai9;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> findMovies(Integer publishedYear);
    void createMovie(Movie movie);
    void updateMovie(Movie movie);
    void deleteMovies(int id);
}
