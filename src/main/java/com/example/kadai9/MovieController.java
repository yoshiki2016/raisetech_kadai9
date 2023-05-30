package com.example.kadai9;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public List<Movie> searchMovies(@RequestParam(name = "published_year", required = false) Optional<Integer> publishedYear){
        return movieService.searchMovies(publishedYear);
    }
}
