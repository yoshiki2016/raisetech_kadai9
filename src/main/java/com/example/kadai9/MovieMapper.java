package com.example.kadai9;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MovieMapper {

    List<Movie> findAll();

    List<Movie> findMovies(int publishedYear);

    void createMovie(Movie movie);

    void updateMovie(Movie movie);


    void deleteMovie(int id);
}
