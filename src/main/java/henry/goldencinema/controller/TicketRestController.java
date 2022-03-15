package henry.goldencinema.controller;

import henry.goldencinema.entity.cinema.Hall;
import henry.goldencinema.entity.cinema.Movie;
import henry.goldencinema.entity.cinema.Ticket;
import henry.goldencinema.entity.responses.ApiResponse;
import henry.goldencinema.entity.user.User;
import henry.goldencinema.service.HallServices;
import henry.goldencinema.service.MovieServices;
import henry.goldencinema.service.TicketServices;
import henry.goldencinema.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/ticket")
public class TicketRestController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private HallServices hallServices;

    @Autowired
    private MovieServices movieServices;

    @Autowired
    private TicketServices ticketServices;

    @GetMapping("/all")
    public ResponseEntity<?> getAllTickets() {
        Optional<Collection<Ticket>> tickets = ticketServices.getAllTickets();

        assert tickets.isPresent();
        if (tickets.get().isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Empty tickets list", ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(), "Tickets list", tickets));
    }

    @GetMapping("/user-email/{email}")
    @PreAuthorize("#email == authentication.name")
    public ResponseEntity<?> getTicketByUser(@PathVariable String email) {
        Optional<Collection<Ticket>> tickets = Optional.empty();
        Optional<User> user = userServices.getUserByEmail(email);

        if (user.isPresent())
            tickets = ticketServices.getTicketsByUser(user.get());

        if (tickets.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Empty tickets list for email: " + email, ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Tickets list for email: " + email, tickets));
    }

    @GetMapping("/movie-title/{movieTitle}")
    public ResponseEntity<?> getTicketByMovieTitle(@PathVariable String movieTitle) {
        Optional<Collection<Ticket>> tickets = Optional.empty();
        Optional<Movie> movie = movieServices.getMovieByTitle(movieTitle);

        if (movie.isPresent())
            tickets = ticketServices.getTicketsByMovie(movie.get());

        if (tickets.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Empty tickets list for movie: " + movieTitle, ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Tickets list for movie: " + movieTitle, tickets));
    }

    @GetMapping("/hall-name/{hallName}")
    public ResponseEntity<?> getTicketByHall(@PathVariable String hallName) {
        Optional<Collection<Ticket>> tickets = Optional.empty();
        Optional<Hall> hall = hallServices.getHallByName(hallName);

        if (hall.isPresent())
            tickets = ticketServices.getTicketsByHall(hall.get());

        if (tickets.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Empty tickets list for hall: " + hallName, ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Tickets list for hall: " + hallName, tickets));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable String id) {
        Optional<Ticket> ticket = ticketServices.getTicketById(id);

        if (ticket.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Ticket not found for id: " + id, ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Ticket found for id: " + id, ticket));
    }

    @PostMapping
    @PreAuthorize("#ticket.user.email == authentication.name")
    public ResponseEntity<?> addTicket(@RequestBody Ticket ticket) {
        Optional<User> existedUser = userServices.getUserByEmail(ticket.getUser().getEmail());
        if (existedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "User not found for email: " + ticket.getUser().getEmail(), ""));

        Optional<Movie> existedMovie = movieServices.getMovieByTitle(ticket.getMovie().getTitle());
        if (existedMovie.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Movie not found for title: " + ticket.getMovie().getTitle(), ""));

        Optional<Hall> existedHall = hallServices.getHallByName(ticket.getHall().getName());
        if (existedHall.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Hall not found for name: " + ticket.getHall().getName(), ""));

        Optional<Ticket> existedTicket = ticketServices.getTicketById(ticket.getId());
        if (existedTicket.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Ticket already exist for id: " + ticket.getId(), ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Ticket Added successfully", ticketServices.addTicket(ticket)));
    }

    @PutMapping()
    @PreAuthorize("#ticket.getUser().email == authentication.name")
    public ResponseEntity<?> updateTicket(@RequestBody Ticket ticket) {
        Optional<Ticket> existedTicket = ticketServices.getTicketById(ticket.getId());

        if (existedTicket.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Ticket not found", ticket));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Ticket updated successfully", ticketServices.updateTicket(ticket)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable String id) {
        Optional<Ticket> existedTicket = ticketServices.getTicketById(id);

        if (existedTicket.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Ticket not found for id: ", id));

        ticketServices.deleteTicketById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Ticket deleted successfully", ""));
    }
}
