package henry.goldencinema.controller;

import henry.goldencinema.entity.Movie;
import henry.goldencinema.service.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/api/movie")
public class MovieRestController {

    @Autowired
    private MovieServices movieServices;

    @GetMapping("/all")
    public Collection<Movie> getAllMovies() throws Exception {
        return movieServices.getAllMovies();
    }

    @GetMapping("/now-play")
    public Collection<Movie> getAllMoviesNowPlaying() throws Exception {
        return movieServices.getMoviesNowPlaying();
    }

    @GetMapping("/id/{id}")
    public Movie getMovieById(@PathVariable String id) throws Exception {
        return movieServices.getMovieById(id);
    }

    @GetMapping("/title/{title}")
    public Movie getMovieByTitle(@PathVariable String title) throws Exception {
        return movieServices.getMovieByTitle(title);
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) throws URISyntaxException {
        Movie savedMovie = movieServices.addMovie(movie);

        return ResponseEntity.created(new URI("/movie" + savedMovie.getId())).body(savedMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) throws Exception {
        Movie updatedMovie = movieServices.updateMovie(movie);

        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable String id) throws Exception {
        movieServices.deleteMovieById(id);

        return ResponseEntity.ok().build();
    }
}
