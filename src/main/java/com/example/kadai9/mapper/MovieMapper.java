package com.example.kadai9.mapper;

import com.example.kadai9.entity.Movie;
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
