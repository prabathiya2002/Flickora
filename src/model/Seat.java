package model;

public class Seat {
    private String id;
    private String showtimeId;
    private String seatNumber;
    private String status;
    private String type;
    private String price;

    public Seat() {
    }

    public Seat(String id, String showtimeId, String seatNumber, String status, String type, String price) {
        this.id = id;
        this.showtimeId = showtimeId;
        this.seatNumber = seatNumber;
        this.status = status;
        this.type = type;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return type;
    }
}