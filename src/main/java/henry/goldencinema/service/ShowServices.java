package henry.goldencinema.service;

import henry.goldencinema.entity.cinema.Movie;
import henry.goldencinema.entity.cinema.Show;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ShowServices {

    Optional<Collection<Show>> getAllShows();

    Optional<Collection<Show>> getAllShowsByMovie(Movie movie);

    Optional<Collection<Show>> getAllShowsByDate(LocalDate date);

    Optional<List<Show>> getShowsByDateAndTimeByMovie(Movie movie, LocalDate date, LocalTime time);

    Optional<Show> getShowById(String id);

    Optional<Show> addShow(Show show);

    Optional<Show> updateShow(Show show);

    void deleteShowById(String id);

}
