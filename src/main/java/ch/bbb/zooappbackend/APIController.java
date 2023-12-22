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

    @PostMapping("/tickets")
    public ResponseEntity<Ticket> postTickets(@RequestBody Ticket ticket) {
        ticket.setPrice(ticket.calculatePrice());
        return ResponseEntity.ok(ticket);
    }

}
