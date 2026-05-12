package model;

public class VIPSeat extends Seat {
    public VIPSeat() {
        setType("VIP");
        setPrice("25.00");
    }

    public VIPSeat(String id, String showtimeId, String seatNumber, String status) {
        super(id, showtimeId, seatNumber, status, "VIP", "25.00");
    }

    @Override
    public String getCategory() {
        return "VIP";
    }
}