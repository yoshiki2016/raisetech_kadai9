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
    public List<Movie> findAll() {
        return movieMapper.findAll();
    }

    @Override
    public List<Movie> findMovies(int publishedYear){
        return movieMapper.findMovies(publishedYear);
    }

    @Override
    public List<Movie> searchMovies(Optional<Integer> publishedYear){
        return publishedYear.isPresent() ? findMovies(publishedYear.get()) : findAll();
    }

}
