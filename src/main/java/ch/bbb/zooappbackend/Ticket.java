package ch.bbb.zooappbackend;

import java.util.ArrayList;

public class Ticket extends ResponseEntity{
    private String date;
    private int numAdults;
    private int numChildren;
    private double price = 0;

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

    public double calculatePrice() {
        this.price = (numAdults * 15) + (numChildren * 7.5);
        return this.price;
    }

    public Ticket(String date, int numAdults, int numChildren) {
        this.date = date;
        this.numAdults = numAdults;
        this.numChildren = numChildren;
    }

    public Ticket() {
        this.date = "";
        this.numAdults = 0;
        this.numChildren = 0;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumAdults() {
        return numAdults;
    }

    public void setNumAdults(int numAdults) {
        this.numAdults = numAdults;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
