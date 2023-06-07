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
    public Movie findMovieById(int id){
        Optional<Movie> movie = movieMapper.findMovieById(id);
        if(movie.isPresent()){
            return movie.get();
        } else {
            throw new ResourceNotFoundException("resource not found");
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
    public void deleteMovie(int id){
        movieMapper.deleteMovie(id);
    };
}
