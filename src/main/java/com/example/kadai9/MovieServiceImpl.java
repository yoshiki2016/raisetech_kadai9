package com.example.kadai9;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService{
    private final MovieMapper movieMapper;

    public MovieServiceImpl(MovieMapper movieMapper) {
        this.movieMapper = movieMapper;
    }

    @Override
    public List<Movie> findMovies(Integer publishedYear){
        if(publishedYear == null){
            return movieMapper.findAll();
        } else {
            return movieMapper.findMovies(publishedYear);
        }
    }

    @Override
    public void createMovies(Movie movie){
        movieMapper.createMovies(movie);
    }

    @Override
    public void updateMovies(Movie movie){
        movieMapper.updateMovies(movie);
    }

    @Override
    public void deleteMovies(int id){
        movieMapper.deleteMovies(id);
    };
}
