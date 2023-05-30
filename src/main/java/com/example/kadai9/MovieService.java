package com.example.kadai9;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> findAll();
    List<Movie> findMovies(int publishedYear);
    List<Movie> searchMovies(Optional<Integer> publishedYear);
}
