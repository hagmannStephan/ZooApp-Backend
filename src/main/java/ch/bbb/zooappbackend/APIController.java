package ch.bbb.zooappbackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class APIController {

    ArrayList<Ticket> tickets = new ArrayList<>();

    @GetMapping("/tickets")
    public ResponseEntity<ArrayList<Ticket>> getTickets() {
        return ResponseEntity.ok(DBConnector.getTickets());
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable int id) {
        Ticket t = DBConnector.getTicketById(id);
        if (t != null) {
            return ResponseEntity.ok(t);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/tickets")
    public ResponseEntity<Ticket> postTickets(@RequestBody Ticket ticket) {
        ticket.setPrice(ticket.calculatePrice());
        ticket.calculateId(DBConnector.getTickets());
        DBConnector.addTicket(ticket);
        return ResponseEntity.status(201).body(ticket);
    }

    @PutMapping("/tickets/{id}")
    public ResponseEntity<Ticket> putTickets(@PathVariable int id, @RequestBody Ticket newTicket) {
        Ticket existingTicket = DBConnector.getTicketById(id);

        if (existingTicket != null) {
            // User can't update these values
            newTicket.setId(id);
            newTicket.setPrice(newTicket.calculatePrice());

            DBConnector.updateTicket(newTicket);

            return ResponseEntity.ok(newTicket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/tickets/{id}")
    public ResponseEntity<Ticket> patchTicket(@PathVariable int id, @RequestBody Ticket updatedFields) {
        Ticket existingTicket = DBConnector.getTicketById(id);

        if (existingTicket != null) {
            // Update fields only if they are not null or 0
            if (updatedFields.getDate() != null) {
                existingTicket.setDate(updatedFields.getDate());
            }
            if (updatedFields.getNumAdults() != 0) {
                existingTicket.setNumAdults(updatedFields.getNumAdults());
            }
            if (updatedFields.getNumChildren() != 0) {
                existingTicket.setNumChildren(updatedFields.getNumChildren());
            }

            existingTicket.calculatePrice();

            // Update the ticket in the database
            DBConnector.updateTicketFields(existingTicket);

            return ResponseEntity.ok(existingTicket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<Ticket> deleteTickets(@PathVariable int id) {
        boolean deleted = DBConnector.deleteTicket(id);

        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/parking")
    public ResponseEntity<List<ParkingSpace>> getParking() {
        Random random = new Random();
        Set<Integer> uniqueNumbers = new HashSet<>();
        List<ParkingSpace> parkingSpaces = new ArrayList<>();

        int numberOfEntries = random.nextInt(41) + 10;

        for (int i = 0; i < numberOfEntries; i++) {
            int randomNumber;
            do {
                randomNumber = random.nextInt(150) + 1;
            } while (!uniqueNumbers.add(randomNumber));

            ParkingSpace parkingSpace = new ParkingSpace(randomNumber);
            parkingSpaces.add(parkingSpace);
        }

        parkingSpaces.sort(Comparator.comparingInt(ParkingSpace::getId));

        return ResponseEntity.ok(parkingSpaces);
    }
}
