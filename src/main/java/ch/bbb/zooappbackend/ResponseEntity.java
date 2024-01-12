package ch.bbb.zooappbackend;

import java.util.ArrayList;

public class ResponseEntity {
    protected int id = 0;

    public int calculateId(ArrayList<Ticket> tickets){
        int id = Integer.MIN_VALUE;
        int highestId = 0;

        for (Ticket ticket : tickets) {
            int currentId = ticket.getId();
            if (currentId > highestId) {
                highestId = currentId;
            }
        }

        this.id = highestId + 1;
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
