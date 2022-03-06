package henry.goldencinema.controller;

import henry.goldencinema.entity.Movie;
import henry.goldencinema.entity.responses.ApiResponse;
import henry.goldencinema.service.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/movie")
public class MovieRestController {

    @Autowired
    private MovieServices movieServices;

    @GetMapping("/all")
    public ResponseEntity<?> getAllMovies() {
        Optional<Collection<Movie>> movies = movieServices.getAllMovies();

        assert movies.isPresent();
        if (movies.get().isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(), "Empty movies list", ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(), "Movies list", movies));
    }

    @GetMapping("/now-play")
    public ResponseEntity<?> getAllMoviesNowPlaying() {
        Optional<Collection<Movie>> nowPlayingMovies = movieServices.getMoviesNowPlaying();

        assert nowPlayingMovies.isPresent();
        if (nowPlayingMovies.get().isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(), "Empty now playing movies list", ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(), "Now playing movies list", nowPlayingMovies));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable String id) {
        Optional<Movie> movie = movieServices.getMovieById(id);

        if (movie.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Movie not found for id: " + id, ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Movie found for id: " + id, movie));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getMovieByTitle(@PathVariable String title) {
        Optional<Movie> movie = movieServices.getMovieByTitle(title);

        if (movie.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Movie not found for title: " + title, ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Movie found for title: " + title, movie));
    }

    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
        Optional<Movie> existedMovie = movieServices.getMovieByTitle(movie.getTitle());

        if (existedMovie.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Movie already exist for title: " + movie.getTitle(), ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Movie Added successfully", movieServices.addMovie(movie)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@RequestBody Movie movie) {
        Optional<Movie> existedMovie = movieServices.getMovieById(movie.getId());

        if (existedMovie.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Movie not found", movie));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Movie updated successfully", movieServices.updateMovie(movie)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable String id) {
        Optional<Movie> existedMovie = movieServices.getMovieById(id);

        if (existedMovie.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Movie not found for id: " + id, ""));

        movieServices.deleteMovieById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Movie deleted successfully", ""));
    }
}
