package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Movie;
import model.Showtime;
import service.MovieService;
import service.ShowtimeService;

@WebServlet("/showtimes")
public class ShowtimeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ShowtimeService showtimeService = new ShowtimeService();
    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = value(request, "action", "list");
        if ("delete".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            if (id != null) {
                showtimeService.delete(getServletContext(), id);
            }
            redirect(response, request, "/showtimes?action=list&message=Showtime deleted successfully");
            return;
        }
        if ("add".equalsIgnoreCase(action)) {
            request.setAttribute("movies", movieService.findAll(getServletContext()));
            forward(request, response, "/jsp/showtime/add-showtime.jsp");
            return;
        }
        if ("edit".equalsIgnoreCase(action)) {
            request.setAttribute("showtime", showtimeService.findById(getServletContext(), request.getParameter("id")));
            request.setAttribute("movies", movieService.findAll(getServletContext()));
            forward(request, response, "/jsp/showtime/edit-showtime.jsp");
            return;
        }
        request.setAttribute("showtimes", showtimeService.findAll(getServletContext()));
        request.setAttribute("message", request.getParameter("message"));
        forward(request, response, "/jsp/showtime/list-showtime.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = value(request, "action", "save");
        String movieId = request.getParameter("movieId");
        Movie movie = movieService.findById(getServletContext(), movieId);
        Showtime showtime = new Showtime(request.getParameter("id"), movieId, movie == null ? request.getParameter("movieName") : movie.getName(), request.getParameter("theater"), request.getParameter("showDate"), request.getParameter("showTime"), request.getParameter("hall"));
        if ("update".equalsIgnoreCase(action)) {
            showtimeService.update(getServletContext(), showtime);
        } else {
            showtimeService.add(getServletContext(), showtime);
        }
        redirect(response, request, "/showtimes?action=list&message=Showtime saved successfully");
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