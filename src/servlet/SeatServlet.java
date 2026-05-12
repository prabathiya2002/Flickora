package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Seat;
import service.SeatService;
import service.ShowtimeService;

@WebServlet("/seats")
public class SeatServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final SeatService seatService = new SeatService();
    private final ShowtimeService showtimeService = new ShowtimeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = value(request, "action", "list");
        if ("delete".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            if (id != null) {
                seatService.delete(getServletContext(), id);
            }
            redirect(response, request, "/seats?action=list&message=Seat deleted successfully");
            return;
        }
        if ("add".equalsIgnoreCase(action)) {
            request.setAttribute("showtimes", showtimeService.findAll(getServletContext()));
            forward(request, response, "/jsp/seat/add-seat.jsp");
            return;
        }
        if ("edit".equalsIgnoreCase(action)) {
            request.setAttribute("seat", seatService.findById(getServletContext(), request.getParameter("id")));
            request.setAttribute("showtimes", showtimeService.findAll(getServletContext()));
            forward(request, response, "/jsp/seat/edit-seat.jsp");
            return;
        }
        request.setAttribute("seats", seatService.findAll(getServletContext()));
        request.setAttribute("message", request.getParameter("message"));
        forward(request, response, "/jsp/seat/list-seat.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = value(request, "action", "save");
        Seat seat = seatService.createSeat(request.getParameter("type"), request.getParameter("showtimeId"), request.getParameter("seatNumber"), request.getParameter("status"));
        seat.setId(request.getParameter("id"));
        seat.setShowtimeId(request.getParameter("showtimeId"));
        seat.setSeatNumber(request.getParameter("seatNumber"));
        seat.setStatus(request.getParameter("status"));
        if ("update".equalsIgnoreCase(action)) {
            seatService.update(getServletContext(), seat);
        } else {
            seatService.add(getServletContext(), seat);
        }
        redirect(response, request, "/seats?action=list&message=Seat saved successfully");
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