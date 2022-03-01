package henry.goldencinema.service.implementation;

import henry.goldencinema.entity.Movie;
import henry.goldencinema.repository.MovieRepository;
import henry.goldencinema.service.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MovieServicesImpl implements MovieServices {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Movie movie) throws Exception {
        isMovieExist(movie.getId(), null);

        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovieById(String id) throws Exception {
        isMovieExist(id, null);

        movieRepository.deleteById(id);
    }

    @Override
    public Movie getMovieById(String id) throws Exception {
        isMovieExist(id, null);

        return movieRepository.findMovieById(id);
    }

    @Override
    public Collection<Movie> getAllMovies() throws Exception {
        isMovieExist(null, null);

        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieByTitle(String title) throws Exception {
        isMovieExist(null, title);

        return movieRepository.findMovieByTitle(title);
    }

    @Override
    public Collection<Movie> getMoviesNowPlaying() throws Exception {
        isMovieExist(null, null);

        return movieRepository.getMoviesByNowPlaying(true);
    }

    private void isMovieExist(String id, String title) throws Exception {
        if (movieRepository.findAll() == null)
            throw new Exception("There are no movies");

        if (id != null) {
            if (movieRepository.findMovieById(id) == null)
                throw new Exception("Movie not found for id: " + id);
        } else if (title != null) {
            if (movieRepository.findMovieByTitle(title) == null)
                throw new Exception("Movie not found for title: " + title);
        }
    }
}
