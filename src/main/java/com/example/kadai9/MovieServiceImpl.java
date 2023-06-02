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
    public Movie createMovie(CreateMovieForm createMovieForm){
        Movie movie = new Movie(createMovieForm.getId(), createMovieForm.getMovieTitle(), createMovieForm.getPublishedYear());
        movieMapper.createMovie(movie);
        return movie;
    }

    @Override
    public void updateMovie(Movie movie){
        movieMapper.updateMovie(movie);
    }

    @Override
    public void deleteMovies(int id){
        movieMapper.deleteMovies(id);
    };
}
