package com.example.kadai9;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MovieMapper {
    @Select("SELECT * FROM movies")
    List<Movie> findAll();

    @Select("SELECT * FROM movies WHERE published_year = #{publishedYear}")
    List<Movie> findMovies(int publishedYear);

    @Delete("DELETE FROM movies WHERE id = #{id}")
    void deleteMovies(int id);
}
