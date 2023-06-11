package com.example.kadai9;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // 検索メソッド作成
    @GetMapping("/movies")
    public List<Movie> findMovies(@RequestParam(name = "publishedYear", required = false) Integer publishedYear){
        return movieService.findMovies(publishedYear);
    }

    // idによる検索
    @GetMapping("/movies/{id}")
    public Movie findMovieById(@PathVariable("id") int id) {
        return movieService.findMovieById(id);
    }

    @PostMapping("/movies")
    public ResponseEntity<Map<String, String>> createMovie(@RequestBody MovieForm movieForm, UriComponentsBuilder uriBuilder) {
        Movie movie = movieService.createMovie(movieForm.getMovieTitle(), movieForm.getPublishedYear());
        URI url = uriBuilder
                .path("/movies/" + movie.getId())
                .build()
                .toUri();
        return ResponseEntity.created(url).body(Map.of("message", "the movie successfully created"));
    }

    @PatchMapping("/movies/{id}")
    public ResponseEntity<Map<String, String>> updateMovie(@PathVariable("id") int id, @RequestBody MovieForm movieForm) {
        movieService.updateMovie(id, movieForm.getMovieTitle(), movieForm.getPublishedYear());
        return ResponseEntity.ok(Map.of("message", "the movie successfully updated"));
    }
    // 削除メソッド作成
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Map<String, String>> deleteMovie(@PathVariable("id") int id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok(Map.of("message", "the movie successfully deleted"));
    }
}
