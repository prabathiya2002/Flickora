package servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Reservation;
import model.Seat;
import model.Showtime;
import model.User;
import service.ReservationService;
import service.SeatService;
import service.ShowtimeService;
import service.UserService;

@WebServlet("/reservations")
public class ReservationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ReservationService reservationService = new ReservationService();
    private final SeatService seatService = new SeatService();
    private final ShowtimeService showtimeService = new ShowtimeService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = value(request, "action", "list");
        if ("delete".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            if (id != null) {
                reservationService.cancelReservation(getServletContext(), id, seatService);
            }
            redirect(response, request, "/reservations?action=list&message=Reservation cancelled successfully");
            return;
        }
        if ("add".equalsIgnoreCase(action)) {
            request.setAttribute("users", userService.findAll(getServletContext()));
            request.setAttribute("showtimes", showtimeService.findAll(getServletContext()));
            request.setAttribute("seats", seatService.findAvailable(getServletContext()));
            forward(request, response, "/jsp/reservation/book-ticket.jsp");
            return;
        }
        if ("edit".equalsIgnoreCase(action)) {
            request.setAttribute("reservation", reservationService.findById(getServletContext(), request.getParameter("id")));
            request.setAttribute("users", userService.findAll(getServletContext()));
            request.setAttribute("showtimes", showtimeService.findAll(getServletContext()));
            request.setAttribute("seats", seatService.findAll(getServletContext()));
            forward(request, response, "/jsp/reservation/edit-reservation.jsp");
            return;
        }
        request.setAttribute("reservations", reservationService.findAll(getServletContext()));
        request.setAttribute("message", request.getParameter("message"));
        forward(request, response, "/jsp/reservation/list-reservation.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = value(request, "action", "save");
        String userId = request.getParameter("userId");
        String showtimeId = request.getParameter("showtimeId");
        String seatId = request.getParameter("seatId");
        User user = userService.findById(getServletContext(), userId);
        Showtime showtime = showtimeService.findById(getServletContext(), showtimeId);
        Seat seat = seatService.findById(getServletContext(), seatId);

        Reservation reservation = new Reservation(request.getParameter("id"), userId, user == null ? request.getParameter("userName") : user.getName(), showtimeId, showtime == null ? request.getParameter("movieName") : showtime.getMovieName(), seatId, seat == null ? request.getParameter("seatNumber") : seat.getSeatNumber(), request.getParameter("status"), LocalDate.now().toString());

        if ("update".equalsIgnoreCase(action)) {
            Reservation existingReservation = reservationService.findById(getServletContext(), reservation.getId());
            String previousSeatId = existingReservation == null ? "" : existingReservation.getSeatId();
            if (seat != null && seat.getStatus() != null && seat.getStatus().equalsIgnoreCase("Booked") && (previousSeatId == null || !previousSeatId.equalsIgnoreCase(seatId))) {
                redirect(response, request, "/reservations?action=list&message=Selected seat is already booked");
                return;
            }
            reservationService.update(getServletContext(), reservation);
            if (previousSeatId != null && !previousSeatId.trim().isEmpty() && !previousSeatId.equalsIgnoreCase(seatId)) {
                seatService.updateStatus(getServletContext(), previousSeatId, "Available");
            }
            if (seat != null) {
                seatService.updateStatus(getServletContext(), seat.getId(), "Booked");
            }
        } else {
            Reservation bookedReservation = reservationService.bookTicket(getServletContext(), reservation, seatService);
            if (bookedReservation == null) {
                redirect(response, request, "/reservations?action=list&message=Unable to book the selected seat");
                return;
            }
        }
        redirect(response, request, "/reservations?action=list&message=Reservation saved successfully");
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    private void redirect(HttpServletResponse response, HttpServletRequest request, String path) throws IOException {
        response.sendRedirect(request.getContextPath() + path);
    }

    private String value(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
    }
}