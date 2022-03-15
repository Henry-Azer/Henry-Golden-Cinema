package henry.goldencinema.service.implementation;

import henry.goldencinema.entity.cinema.Movie;
import henry.goldencinema.repository.MovieRepository;
import henry.goldencinema.service.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class MovieServicesImpl implements MovieServices {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Optional<Collection<Movie>> getAllMovies() {
        return Optional.of(movieRepository.findAll());
    }

    @Override
    public Optional<Collection<Movie>> getMoviesNowPlaying() {
        return Optional.ofNullable(movieRepository.getMoviesByNowPlaying(true));
    }

    @Override
    public Optional<Movie> getMovieById(String id) {
        return Optional.ofNullable(movieRepository.findMovieById(id));
    }

    @Override
    public Optional<Movie> getMovieByTitle(String title) {
        return Optional.ofNullable(movieRepository.findMovieByTitle(title));
    }

    @Override
    public Optional<Movie> addMovie(Movie movie) {
        return Optional.of(movieRepository.save(movie));
    }

    @Override
    public Optional<Movie> updateMovie(Movie movie) {
        return Optional.of(movieRepository.save(movie));
    }

    @Override
    public void deleteMovieById(String id) {
        movieRepository.deleteById(id);
    }
}
