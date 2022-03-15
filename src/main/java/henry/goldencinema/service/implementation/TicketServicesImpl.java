package henry.goldencinema.service.implementation;

import henry.goldencinema.entity.cinema.Hall;
import henry.goldencinema.entity.cinema.Movie;
import henry.goldencinema.entity.cinema.Ticket;
import henry.goldencinema.entity.user.User;
import henry.goldencinema.repository.TicketRepository;
import henry.goldencinema.service.TicketServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TicketServicesImpl implements TicketServices {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Optional<Collection<Ticket>> getAllTickets() {
        return Optional.of(ticketRepository.findAll());
    }

    @Override
    public Optional<Collection<Ticket>> getTicketsByUser(User user) {
        return Optional.ofNullable(ticketRepository.findTicketsByUser(user));
    }

    @Override
    public Optional<Collection<Ticket>> getTicketsByMovie(Movie movie) {
        return Optional.ofNullable(ticketRepository.findTicketsByMovie(movie));
    }

    @Override
    public Optional<Collection<Ticket>> getTicketsByHall(Hall hall) {
        return Optional.ofNullable(ticketRepository.findTicketsByHall(hall));
    }

    @Override
    public Optional<Ticket> getTicketById(String id) {

        return Optional.ofNullable(ticketRepository.findTicketById(id));
    }

    @Override
    public Optional<Ticket> addTicket(Ticket ticket) {
        return Optional.of(ticketRepository.save(ticket));

    }

    @Override
    public Optional<Ticket> updateTicket(Ticket ticket) {
        return Optional.of(ticketRepository.save(ticket));
    }

    @Override
    public void deleteTicketById(String id) {

        ticketRepository.deleteById(id);
    }

}