package com.example.kadai9;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public List<Movie> movies() {
        return movieService.findAll();
    }

    @GetMapping("/movies?published_year={published_year}")
    public List<Movie> findMovie(@PathVariable(name = "published_year") int published_year){
        return movieService.findMovie(published_year);
    }

}
