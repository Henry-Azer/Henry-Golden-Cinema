package henry.goldencinema.repository;

import henry.goldencinema.entity.cinema.Hall;
import henry.goldencinema.entity.cinema.Movie;
import henry.goldencinema.entity.cinema.Ticket;
import henry.goldencinema.entity.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

    List<Ticket> findAll();

    Ticket findTicketById(String id);

    Collection<Ticket> findTicketsByUser(User user);

    Collection<Ticket> findTicketsByHall(Hall hall);

    Collection<Ticket> findTicketsByMovie(Movie movie);

}
