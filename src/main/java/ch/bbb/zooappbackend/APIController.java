package ch.bbb.zooappbackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
public class APIController {

    ArrayList<Ticket> tickets = new ArrayList<>();

    @GetMapping("/tickets")
    public ResponseEntity<ArrayList<Ticket>> getTickets() {
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable int id) {
        for (Ticket ticket : tickets) {
            int currentId = ticket.getId();
            if (currentId == id) {
                return ResponseEntity.ok(ticket);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/tickets")
    public ResponseEntity<Ticket> postTickets(@RequestBody Ticket ticket) {
        ticket.setPrice(ticket.calculatePrice());
        ticket.calculateId(tickets);
        tickets.add(ticket);
        return ResponseEntity.status(201).body(ticket);
    }

    @PutMapping("/tickets/{id}")
    public ResponseEntity<Ticket> putTickets(@PathVariable int id, @RequestBody Ticket newTicket){
        for (Ticket ticket : tickets) {
            int currentId = ticket.getId();
            if (currentId == id) {
                newTicket.setId(id);        // User can't update these values
                newTicket.setPrice(ticket.getPrice());
                ticket = newTicket;
                return ResponseEntity.ok(ticket);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/tickets/{id}")
    public ResponseEntity<Ticket> patchTicket(@PathVariable int id, @RequestBody Ticket updatedFields) {
        for (Ticket ticket : tickets) {
            int currentId = ticket.getId();
            if (currentId == id) {
                if (updatedFields.getDate() != null) {
                    ticket.setDate(updatedFields.getDate());
                }
                if (updatedFields.getNumAdults() != 0) {
                    ticket.setNumAdults(updatedFields.getNumAdults());
                }
                if(updatedFields.getNumChildren() != 0){
                    ticket.setNumChildren(updatedFields.getNumChildren());
                }
                return ResponseEntity.ok(ticket);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<Ticket> deleteTickets(@PathVariable int id) {
        for (Ticket ticket : tickets) {
            int currentId = ticket.getId();
            if (currentId == id) {
                tickets.remove(ticket);
                return ResponseEntity.ok(ticket);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
