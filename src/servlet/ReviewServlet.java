package servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Movie;
import model.Review;
import model.User;
import service.MovieService;
import service.ReviewService;
import service.UserService;

@WebServlet("/reviews")
public class ReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ReviewService reviewService = new ReviewService();
    private final UserService userService = new UserService();
    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = value(request, "action", "list");
        if ("delete".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            if (id != null) {
                reviewService.delete(getServletContext(), id);
            }
            redirect(response, request, "/reviews?action=list&message=Review deleted successfully");
            return;
        }
        if ("add".equalsIgnoreCase(action)) {
            request.setAttribute("users", userService.findAll(getServletContext()));
            request.setAttribute("movies", movieService.findAll(getServletContext()));
            forward(request, response, "/jsp/review/add-review.jsp");
            return;
        }
        if ("edit".equalsIgnoreCase(action)) {
            request.setAttribute("review", reviewService.findById(getServletContext(), request.getParameter("id")));
            request.setAttribute("users", userService.findAll(getServletContext()));
            request.setAttribute("movies", movieService.findAll(getServletContext()));
            forward(request, response, "/jsp/review/edit-review.jsp");
            return;
        }
        request.setAttribute("reviews", reviewService.findAll(getServletContext()));
        request.setAttribute("message", request.getParameter("message"));
        forward(request, response, "/jsp/review/list-review.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = value(request, "action", "save");
        String userId = request.getParameter("userId");
        String movieId = request.getParameter("movieId");
        User user = userService.findById(getServletContext(), userId);
        Movie movie = movieService.findById(getServletContext(), movieId);
        Review review = new Review(request.getParameter("id"), userId, user == null ? request.getParameter("userName") : user.getName(), movieId, movie == null ? request.getParameter("movieName") : movie.getName(), request.getParameter("rating"), request.getParameter("comment"), LocalDate.now().toString());
        if ("update".equalsIgnoreCase(action)) {
            reviewService.update(getServletContext(), review);
        } else {
            reviewService.add(getServletContext(), review);
        }
        redirect(response, request, "/reviews?action=list&message=Review saved successfully");
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

//Pramudi