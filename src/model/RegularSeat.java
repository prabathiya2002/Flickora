package model;

public class RegularSeat extends Seat {
    public RegularSeat() {
        setType("Regular");
        setPrice("15.00");
    }

    public RegularSeat(String id, String showtimeId, String seatNumber, String status) {
        super(id, showtimeId, seatNumber, status, "Regular", "15.00");
    }

    @Override
    public String getCategory() {
        return "Regular";
    }
}