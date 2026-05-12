package model;

public class Reservation {
    private String id;
    private String userId;
    private String userName;
    private String showtimeId;
    private String movieName;
    private String seatId;
    private String seatNumber;
    private String status;
    private String bookingDate;

    public Reservation() {
    }

    public Reservation(String id, String userId, String userName, String showtimeId, String movieName, String seatId, String seatNumber, String status, String bookingDate) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.showtimeId = showtimeId;
        this.movieName = movieName;
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.status = status;
        this.bookingDate = bookingDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
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

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
}