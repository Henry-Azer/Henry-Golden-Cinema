package henry.goldencinema.service;

import henry.goldencinema.entity.Movie;

import java.util.Collection;

public interface MovieServices {

    Movie addMovie(Movie movie);

    Movie updateMovie(Movie movie) throws Exception;

    void deleteMovieById(String id) throws Exception;

    Movie getMovieById(String id) throws Exception;

    Movie getMovieByTitle(String title) throws Exception;

    Collection<Movie> getMoviesNowPlaying() throws Exception;

    Collection<Movie> getAllMovies() throws Exception;

}
