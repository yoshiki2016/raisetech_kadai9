package com.example.kadai9;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MovieMapper {

    List<Movie> findAll();

    List<Movie> findMovies(int publishedYear);

    Optional<Movie> findMovieById(int id);

    void createMovie(Movie movie);

    void updateMovie(Movie movie);


    void deleteMovie(int id);
}
