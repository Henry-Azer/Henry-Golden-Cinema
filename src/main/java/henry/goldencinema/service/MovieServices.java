package henry.goldencinema.service;

import henry.goldencinema.entity.Movie;

import java.util.Collection;
import java.util.Optional;

public interface MovieServices {

    Optional<Collection<Movie>> getAllMovies();

    Optional<Collection<Movie>> getMoviesNowPlaying();

    Optional<Movie> getMovieById(String id);

    Optional<Movie> getMovieByTitle(String title);

    Optional<Movie> addMovie(Movie movie);

    Optional<Movie> updateMovie(Movie movie);

    void deleteMovieById(String id);
}
