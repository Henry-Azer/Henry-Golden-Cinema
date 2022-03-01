package henry.goldencinema.service.implementation;

import henry.goldencinema.entity.Ticket;
import henry.goldencinema.repository.TicketRepository;
import henry.goldencinema.service.TicketServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TicketServicesImpl implements TicketServices {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket addTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket updateTicket(Ticket ticket) throws Exception {
        isTicketExist(ticket.getId());

        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicketById(String id) throws Exception {
        isTicketExist(id);

        ticketRepository.deleteById(id);
    }

    @Override
    public Ticket getTicketById(String id) throws Exception {
        isTicketExist(id);

        return ticketRepository.findTicketById(id);
    }

    @Override
    public Collection<Ticket> getAllTickets() throws Exception {
        isTicketExist(null);

        return ticketRepository.findAll();
    }

    @Override
    public Collection<Ticket> getTicketsByUsername(String username) throws Exception {
        if (ticketRepository.findTicketsByUsername(username) == null)
            throw new Exception("Tickets not found for user: " + username);

        return ticketRepository.findTicketsByUsername(username);
    }

    @Override
    public Collection<Ticket> getTicketsByMovieTitle(String movieTitle) throws Exception {
        if (ticketRepository.findTicketsByMovieTitle(movieTitle) == null)
            throw new Exception("Tickets not found for movie: " + movieTitle);

        return ticketRepository.findTicketsByMovieTitle(movieTitle);
    }

    @Override
    public Collection<Ticket> getTicketsByHall(String hall) throws Exception {
        if (ticketRepository.findTicketsByHall(hall) == null)
            throw new Exception("Movie not found for hall: " + hall);

        return ticketRepository.findTicketsByHall(hall);
    }

    private void isTicketExist(String id) throws Exception {
        if (ticketRepository.findAll() == null)
            throw new Exception("There are no tickets");

        if (ticketRepository.findTicketById(id) == null)
            throw new Exception("Tickets not found for id: " + id);
    }
}
