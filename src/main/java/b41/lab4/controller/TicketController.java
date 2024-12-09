package b41.lab4.controller;

import b41.lab4.data.Ticket;
import b41.lab4.exception.ResourceNotFoundException;
import b41.lab4.repository.TicketRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @TODO: повертати ResponseEntity зі статус кодами та відповідним Ticket як результат усіх запитів
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket updatedTicket) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        ticket.setDate(updatedTicket.getDate());
        ticket.setTime(updatedTicket.getTime());
        ticket.setSeatNumber(updatedTicket.getSeatNumber());
        ticket.setMovieName(updatedTicket.getMovieName());
        return ticketRepository.save(ticket);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketRepository.deleteById(id);
    }
}
