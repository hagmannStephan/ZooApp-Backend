package ch.bbb.zooappbackend;

public class ParkingSpace extends ResponseEntity{
    private double length = 12.2;
    private double width = 3.7;

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public ParkingSpace(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ParkingSpace{" +
                "length=" + length +
                ", width=" + width +
                ", id=" + id +
                '}';
    }
}
