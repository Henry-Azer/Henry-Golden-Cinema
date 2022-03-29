package henry.goldencinema.schedule;

import henry.goldencinema.entity.cinema.Hall;
import henry.goldencinema.entity.cinema.Movie;
import henry.goldencinema.entity.cinema.Show;
import henry.goldencinema.service.HallServices;
import henry.goldencinema.service.MovieServices;
import henry.goldencinema.service.ShowServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
@Configuration
@EnableScheduling
public class ShowsScheduler {

    @Autowired
    private HallServices hallServices;

    @Autowired
    private MovieServices movieServices;

    @Autowired
    private ShowServices showServices;

    @Scheduled(cron = "0 0 0 * * *")
    public void settingMovieShowsSchedulerForEachDay() {
        Optional<Collection<Movie>> allMoviesOptionalCollection = movieServices.getAllMovies();
        assert allMoviesOptionalCollection.isPresent();
        List<Movie> allMoviesList = (List<Movie>) allMoviesOptionalCollection.get();

        allMoviesList.forEach(movie -> movie.setNowPlaying(false));

        int movieShowsCounter = 0;
        while (movieShowsCounter != 6) {
            Collections.shuffle(allMoviesList);
            Movie movie = allMoviesList.get(0);

            if (!movie.getNowPlaying()) {
                movie.setNowPlaying(true);
                movieShowsCounter++;
            }
        }

        allMoviesList.forEach(movie -> movieServices.updateMovie(movie));

        Optional<Collection<Movie>> moviesToShowOptionalCollection = movieServices.getMoviesNowPlaying();
        assert moviesToShowOptionalCollection.isPresent();
        List<Movie> moviesToShowList = (List<Movie>) moviesToShowOptionalCollection.get();

        Optional<Collection<Hall>> hallsOptionalCollection = hallServices.getAllHalls();
        assert hallsOptionalCollection.isPresent();

        int moviesIndex = 0;
        for (Hall hall : hallsOptionalCollection.get()) {
            if (moviesIndex > 6) break;

            Movie movie = moviesToShowList.get(moviesIndex);
            Collection<Show> shows = new ArrayList<>();

            for (Show show : hall.getShows()) {
                show.getSeats().clear();
                show.setShowDate(LocalDate.now().plusDays(1));
                show.setMovie(movie);

                showServices.updateShow(show);
                shows.add(show);
            }

            hall.setShows(shows);
            hallServices.updateHall(hall);

            moviesIndex++;
        }
    }
}