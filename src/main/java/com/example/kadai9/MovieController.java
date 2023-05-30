package com.example.kadai9;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies/all")
    public List<Movie> movies() {
        return movieService.findAll();
    }

    @GetMapping("/movies")
    public List<Movie> findMovie(@RequestParam("published_year") int publishedYear){
        return movieService.findMovie(publishedYear);
    }

}
