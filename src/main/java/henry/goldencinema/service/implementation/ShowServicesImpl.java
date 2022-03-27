package henry.goldencinema.service.implementation;

import henry.goldencinema.entity.cinema.Movie;
import henry.goldencinema.entity.cinema.Show;
import henry.goldencinema.repository.ShowRepository;
import henry.goldencinema.service.ShowServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ShowServicesImpl implements ShowServices {

    @Autowired
    private ShowRepository showRepository;

    @Override
    public Optional<Collection<Show>> getAllShows() {
        return Optional.ofNullable(showRepository.findAll());
    }

    @Override
    public Optional<Collection<Show>> getAllShowsByMovie(Movie movie) {
        return Optional.ofNullable(showRepository.findAllByMovie(movie));
    }

    @Override
    public Optional<Collection<Show>> getAllShowsByDate(LocalDate date) {
        return Optional.ofNullable(showRepository.findAllByShowDate(date));
    }

    @Override
    public Optional<List<Show>> getShowsByDateAndTimeByMovie(Movie movie, LocalDate date, LocalTime time) {
        Collection<Show> shows = showRepository.findAll();
        Predicate<Show> idleHallsPredicate = show -> show.getMovie().equals(movie)
                && show.getShowDate().equals(date) && show.getShowTime().equals(time);

        if (!shows.isEmpty())
            return Optional.of(shows.stream().filter(idleHallsPredicate).collect(Collectors.toList()));

        return Optional.empty();
    }

    @Override
    public Optional<Show> getShowById(String id) {
        return Optional.ofNullable(showRepository.findShowById(id));
    }

    @Override
    public Optional<Show> addShow(Show show) {
        return Optional.of(showRepository.save(show));
    }

    @Override
    public Optional<Show> updateShow(Show show) {
        return Optional.of(showRepository.save(show));
    }

    @Override
    public void deleteShowById(String id) {
        showRepository.deleteById(id);
    }
}
