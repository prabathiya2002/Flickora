package service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import model.RegularSeat;
import model.Seat;
import model.VIPSeat;

public class SeatService extends BaseTextService<Seat> {

    @Override
    protected String getFileName() {
        return "seats.txt";
    }

    @Override
    protected String getId(Seat entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Seat entity, String id) {
        entity.setId(id);
    }

    @Override
    protected String serialize(Seat entity) {
        return entity.getId() + "|" + safe(entity.getShowtimeId()) + "|" + safe(entity.getSeatNumber()) + "|" + safe(entity.getStatus()) + "|" + safe(entity.getType()) + "|" + safe(entity.getPrice());
    }

    @Override
    protected Seat deserialize(String line) {
        String[] values = line.split("\\|", -1);
        if (values.length < 6) {
            return null;
        }
        String type = values[4];
        if ("VIP".equalsIgnoreCase(type)) {
            return new VIPSeat(values[0], values[1], values[2], values[3]);
        }
        return new RegularSeat(values[0], values[1], values[2], values[3]);
    }

    @Override
    protected String getIdPrefix() {
        return "SE";
    }

    public List<Seat> findByShowtimeId(ServletContext context, String showtimeId) {
        List<Seat> filtered = new ArrayList<Seat>();
        for (Seat seat : findAll(context)) {
            if (seat.getShowtimeId() != null && seat.getShowtimeId().equalsIgnoreCase(showtimeId)) {
                filtered.add(seat);
            }
        }
        return filtered;
    }

    public List<Seat> findAvailable(ServletContext context) {
        List<Seat> filtered = new ArrayList<Seat>();
        for (Seat seat : findAll(context)) {
            if (seat.getStatus() != null && seat.getStatus().equalsIgnoreCase("Available")) {
                filtered.add(seat);
            }
        }
        return filtered;
    }

    public boolean updateStatus(ServletContext context, String seatId, String status) {
        Seat seat = findById(context, seatId);
        if (seat == null) {
            return false;
        }
        seat.setStatus(status);
        return update(context, seat);
    }

    public Seat createSeat(String seatType, String showtimeId, String seatNumber, String status) {
        if ("VIP".equalsIgnoreCase(seatType)) {
            return new VIPSeat("", showtimeId, seatNumber, status);
        }
        return new RegularSeat("", showtimeId, seatNumber, status);
    }

    private String safe(String value) {
        return value == null ? "" : value.replace("|", "/");
    }
}