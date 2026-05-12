package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Movie;
import service.MovieService;

@WebServlet("/movies")
public class MovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = value(request, "action", "list");
        if ("delete".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            if (id != null) {
                movieService.delete(getServletContext(), id);
            }
            redirect(response, request, "/movies?action=list&message=Movie deleted successfully");
            return;
        }
        if ("add".equalsIgnoreCase(action)) {
            forward(request, response, "/jsp/movie/add-movie.jsp");
            return;
        }
        if ("edit".equalsIgnoreCase(action)) {
            request.setAttribute("movie", movieService.findById(getServletContext(), request.getParameter("id")));
            forward(request, response, "/jsp/movie/edit-movie.jsp");
            return;
        }
        request.setAttribute("movies", movieService.findAll(getServletContext()));
        request.setAttribute("message", request.getParameter("message"));
        forward(request, response, "/jsp/movie/list-movie.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = value(request, "action", "save");
        Movie movie = new Movie(request.getParameter("id"), request.getParameter("name"), request.getParameter("genre"), request.getParameter("duration"), request.getParameter("language"), request.getParameter("rating"));
        if ("update".equalsIgnoreCase(action)) {
            movieService.update(getServletContext(), movie);
        } else {
            movieService.add(getServletContext(), movie);
        }
        redirect(response, request, "/movies?action=list&message=Movie saved successfully");
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