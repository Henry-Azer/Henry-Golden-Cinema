package henry.goldencinema.service.implementation;

import henry.goldencinema.entity.cinema.Hall;
import henry.goldencinema.entity.cinema.Movie;
import henry.goldencinema.entity.cinema.Show;
import henry.goldencinema.entity.cinema.Ticket;
import henry.goldencinema.entity.user.User;
import henry.goldencinema.image.processing.TicketImageProcess;
import henry.goldencinema.repository.TicketRepository;
import henry.goldencinema.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServicesImpl implements TicketServices {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowServices showServices;

    @Autowired
    private MovieServices movieServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private HallServices hallServices;

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
    public Optional<Ticket> addTicket(Ticket ticket, String movieId, String authenticatedUserId) {
        Optional<Movie> movie = movieServices.getMovieById(movieId);
        assert movie.isPresent();
        ticket.setMovie(movie.get());

        Optional<List<Show>> shows = showServices.getShowsByDateAndTimeByMovie(
                movie.get(), ticket.getDate(), ticket.getTime());
        assert shows.isPresent();
        shows.get().get(0).getSeats().add(ticket.getSeat());
        showServices.updateShow(shows.get().get(0));

        Optional<User> user = userServices.getUserById(authenticatedUserId);
        assert user.isPresent();
        ticket.setUser(user.get());

        Optional<Hall> hall = hallServices.getHallByShow(shows.get().get(0));
        assert hall.isPresent();
        ticket.setHall(hall.get());

        ticket.setImage(TicketImageProcess.generateTicketImage(ticket));

        return Optional.of(ticketRepository.save(ticket));
    }

    @Override
    public Optional<Ticket> updateTicket(Ticket ticket) {
        Optional<List<Show>> shows =
                showServices.getShowsByDateAndTimeByMovie(ticket.getMovie(), ticket.getDate(), ticket.getTime());
        assert shows.isPresent();
        shows.get().get(0).getSeats().add(ticket.getSeat());
        showServices.updateShow(shows.get().get(0));

        ticket.setImage(TicketImageProcess.generateTicketImage(ticket));

        return Optional.of(ticketRepository.save(ticket));
    }

    @Override
    public void deleteTicketById(String id) {
        Optional<Ticket> ticket = getTicketById(id);
        assert ticket.isPresent();

        Optional<List<Show>> shows = showServices.getShowsByDateAndTimeByMovie(
                        ticket.get().getMovie(), ticket.get().getDate(), ticket.get().getTime());
        assert shows.isPresent();
        shows.get().get(0).getSeats().remove(ticket.get().getSeat());
        showServices.updateShow(shows.get().get(0));

        ticketRepository.deleteById(id);
    }

}