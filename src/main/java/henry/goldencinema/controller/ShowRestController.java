package henry.goldencinema.controller;


import henry.goldencinema.entity.cinema.Movie;
import henry.goldencinema.entity.cinema.Show;
import henry.goldencinema.entity.responses.ApiResponse;
import henry.goldencinema.service.MovieServices;
import henry.goldencinema.service.ShowServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/show")
public class ShowRestController {

    @Autowired
    private ShowServices showServices;

    @Autowired
    private MovieServices movieServices;

    @GetMapping("/movie-id/{movieId}")
    public ResponseEntity<?> getShowsByMovieId(@PathVariable String movieId) {
        Optional<Movie> movie = movieServices.getMovieById(movieId);

        assert movie.isPresent();
        Optional<Collection<Show>> shows = showServices.getAllShowsByMovie(movie.get());

        assert shows.isPresent();
        if (shows.get().isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Empty shows list for movie id: " + movieId, ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Shows list for movie id: " + movieId, shows));
    }
}
