package ch.bbb.zooappbackend;

public class Ticket {
    private int id = 0; // TODO: Generate new id
    private String date;
    private int numAdults;
    private int numChildren;
    private double price = 0;

    public double calculatePrice() {
        this.price = (numAdults * 15) + (numChildren * 7.5);
        return this.price;
    }

    public Ticket(String date, int numAdults, int numChildren) {
        this.date = date;
        this.numAdults = numAdults;
        this.numChildren = numChildren;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
