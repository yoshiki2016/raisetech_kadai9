package com.example.kadai9;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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

    // 削除メソッド作成
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Map<String, String>> deleteMovies(@PathVariable("id") int id) {
        movieService.deleteMovies(id);
        return ResponseEntity.ok(Map.of("message", "the movie successfully delete"));
    }
}
