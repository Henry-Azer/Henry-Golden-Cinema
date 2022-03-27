package henry.goldencinema.service;

import henry.goldencinema.entity.cinema.Hall;
import henry.goldencinema.entity.cinema.Movie;
import henry.goldencinema.entity.cinema.Ticket;
import henry.goldencinema.entity.user.User;

import java.util.Collection;
import java.util.Optional;

public interface TicketServices {

    Optional<Collection<Ticket>> getAllTickets();

    Optional<Collection<Ticket>> getTicketsByHall(Hall hall);

    Optional<Collection<Ticket>> getTicketsByUser(User user);

    Optional<Collection<Ticket>> getTicketsByMovie(Movie movie);

    Optional<Ticket> getTicketById(String id);

    Optional<Ticket> addTicket(Ticket ticket, String movieId, String authenticatedUserId);

    Optional<Ticket> updateTicket(Ticket ticket);

    void deleteTicketById(String id);

}