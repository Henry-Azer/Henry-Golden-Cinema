package henry.goldencinema.repository;

import henry.goldencinema.entity.cinema.Movie;
import henry.goldencinema.entity.cinema.Show;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ShowRepository extends MongoRepository<Show, String> {

    List<Show> findAll();

    Show findShowById(String id);

    Show findAllByShowTime(LocalTime time);

    Collection<Show> findAllByMovie(Movie movie);

    Collection<Show> findAllByShowDate(LocalDate date);

}
