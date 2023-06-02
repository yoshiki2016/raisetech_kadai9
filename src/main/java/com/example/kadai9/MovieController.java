package com.example.kadai9;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // 検索メソッド作成
    @GetMapping("/movies")
    public List<Movie> findMovies(@RequestParam(name = "published_year", required = false) Integer publishedYear){
        return movieService.findMovies(publishedYear);
    }

    @PostMapping("/movies")
    public ResponseEntity<Map<String, String>> createMovie(@RequestBody CreateMovieForm createMovieForm, UriComponentsBuilder uriBuilder) {
        Movie movie = movieService.createMovie(createMovieForm);
        URI url = uriBuilder
                .path("/movies/" + movie.getId())
                .build()
                .toUri();
        return ResponseEntity.created(url).body(Map.of("message", "the movie successfully created"));
    }

    @PatchMapping("/movies")
    public ResponseEntity<Map<String, String>> updateMovie(@RequestBody Movie movie) {
        movieService.updateMovie(movie);
        return ResponseEntity.ok(Map.of("message", "the movie successfully updated"));
    }
    // 削除メソッド作成
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Map<String, String>> deleteMovie(@PathVariable("id") int id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok(Map.of("message", "the movie successfully deleted"));
    }
}
