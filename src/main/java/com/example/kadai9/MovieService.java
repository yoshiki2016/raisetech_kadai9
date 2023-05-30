package com.example.kadai9;

import java.util.List;

public interface MovieService {
    List<Movie> findAll();
    List<Movie> findMovie(int publishedYear);
}
