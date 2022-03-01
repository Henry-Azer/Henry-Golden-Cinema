package henry.goldencinema.repository;

import henry.goldencinema.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

    List<Ticket> findAll();

    Ticket findTicketById(String id);

    Collection<Ticket> findTicketsByHall(String hall);

    Collection<Ticket> findTicketsByUsername(String username);

    Collection<Ticket> findTicketsByMovieTitle(String movieTitle);


}
