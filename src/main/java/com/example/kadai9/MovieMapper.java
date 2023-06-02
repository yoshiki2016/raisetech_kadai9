package com.example.kadai9;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MovieMapper {

    List<Movie> findAll();

    List<Movie> findMovies(int publishedYear);

    void createMovies(Movie movie);

    void updateMovies(Movie movie);

    @Delete("DELETE FROM movies WHERE id = #{id}")
    void deleteMovies(int id);
}
