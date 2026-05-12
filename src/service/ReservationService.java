package service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import model.Reservation;
import model.Seat;

public class ReservationService extends BaseTextService<Reservation> {

    @Override
    protected String getFileName() {
        return "reservations.txt";
    }

    @Override
    protected String getId(Reservation entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Reservation entity, String id) {
        entity.setId(id);
    }

    @Override
    protected String serialize(Reservation entity) {
        return entity.getId() + "|" + safe(entity.getUserId()) + "|" + safe(entity.getUserName()) + "|" + safe(entity.getShowtimeId()) + "|" + safe(entity.getMovieName()) + "|" + safe(entity.getSeatId()) + "|" + safe(entity.getSeatNumber()) + "|" + safe(entity.getStatus()) + "|" + safe(entity.getBookingDate());
    }

    @Override
    protected Reservation deserialize(String line) {
        String[] values = line.split("\\|", -1);
        if (values.length < 9) {
            return null;
        }
        return new Reservation(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]);
    }

    @Override
    protected String getIdPrefix() {
        return "R";
    }

    public List<Reservation> findByUserId(ServletContext context, String userId) {
        List<Reservation> filtered = new ArrayList<Reservation>();
        for (Reservation reservation : findAll(context)) {
            if (reservation.getUserId() != null && reservation.getUserId().equalsIgnoreCase(userId)) {
                filtered.add(reservation);
            }
        }
        return filtered;
    }

    public List<Reservation> findByShowtimeId(ServletContext context, String showtimeId) {
        List<Reservation> filtered = new ArrayList<Reservation>();
        for (Reservation reservation : findAll(context)) {
            if (reservation.getShowtimeId() != null && reservation.getShowtimeId().equalsIgnoreCase(showtimeId)) {
                filtered.add(reservation);
            }
        }
        return filtered;
    }

    public Reservation bookTicket(ServletContext context, Reservation reservation, SeatService seatService) {
        Seat seat = seatService.findById(context, reservation.getSeatId());
        if (seat == null || (seat.getStatus() != null && seat.getStatus().equalsIgnoreCase("Booked"))) {
            return null;
        }
        seat.setStatus("Booked");
        seatService.update(context, seat);
        reservation.setStatus("Booked");
        add(context, reservation);
        return reservation;
    }

    public boolean cancelReservation(ServletContext context, String reservationId, SeatService seatService) {
        Reservation reservation = findById(context, reservationId);
        if (reservation == null) {
            return false;
        }
        if (reservation.getSeatId() != null && !reservation.getSeatId().trim().isEmpty()) {
            seatService.updateStatus(context, reservation.getSeatId(), "Available");
        }
        reservation.setStatus("Cancelled");
        return update(context, reservation);
    }

    private String safe(String value) {
        return value == null ? "" : value.replace("|", "/");
    }
}