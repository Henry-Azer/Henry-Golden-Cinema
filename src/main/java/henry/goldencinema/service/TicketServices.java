package henry.goldencinema.service;

import henry.goldencinema.entity.Ticket;

import java.util.Collection;

public interface TicketServices {

    Ticket addTicket(Ticket ticket);

    Ticket updateTicket(Ticket ticket) throws Exception;

    void deleteTicketById(String id) throws Exception;

    Ticket getTicketById(String id) throws Exception;

    Collection<Ticket> getAllTickets() throws Exception;

    Collection<Ticket> getTicketsByHall(String hall) throws Exception;

    Collection<Ticket> getTicketsByUsername(String username) throws Exception;

    Collection<Ticket> getTicketsByMovieTitle(String movieTitle) throws Exception;

}
