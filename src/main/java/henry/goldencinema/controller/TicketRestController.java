package henry.goldencinema.controller;

import henry.goldencinema.entity.Movie;
import henry.goldencinema.entity.Ticket;
import henry.goldencinema.service.TicketServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/api/ticket")
public class TicketRestController {

    @Autowired
    private TicketServices ticketServices;

    @GetMapping("/all")
    public Collection<Ticket> getAllTickets() throws Exception {
        return ticketServices.getAllTickets();
    }

    @GetMapping("/id/{id}")
    public Ticket getTicketById(@PathVariable String id) throws Exception {
        return ticketServices.getTicketById(id);
    }

    @GetMapping("/hall/{hall}")
    public Collection<Ticket> getTicketByHall(@PathVariable String hall) throws Exception {
        return ticketServices.getTicketsByHall(hall);
    }

    @GetMapping("/username/{username}")
    public Collection<Ticket> getTicketByUsername(@PathVariable String username) throws Exception {
        return ticketServices.getTicketsByUsername(username);
    }

    @GetMapping("/movie-title/{movieTitle}")
    public Collection<Ticket> getTicketByMovieTitle(@PathVariable String movieTitle) throws Exception {
        return ticketServices.getTicketsByMovieTitle(movieTitle);
    }

    @PostMapping
    public ResponseEntity<Ticket> addTicket(@RequestBody Ticket ticket) throws URISyntaxException {
        Ticket savedTicket = ticketServices.addTicket(ticket);

        return ResponseEntity.created(new URI("/ticket" + savedTicket.getId())).body(ticket);
    }

    @PutMapping()
    @PreAuthorize("ticket.username = authentication.principal.username")
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket) throws Exception {
        Ticket updatedTicket = ticketServices.updateTicket(ticket);

        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("ticketServicesImpl.getTicketById(id).username = authentication.principal.username")
    public ResponseEntity<Movie> deleteTicket(@PathVariable String id) throws Exception {
        ticketServices.deleteTicketById(id);

        return ResponseEntity.ok().build();
    }
}
