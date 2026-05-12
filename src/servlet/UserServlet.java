package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Admin;
import model.Customer;
import model.User;
import service.UserService;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = value(request, "action", "list");
        if ("delete".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            if (id != null) {
                userService.delete(getServletContext(), id);
            }
            redirect(response, request, "/users?action=list&message=User deleted successfully");
            return;
        }
        if ("add".equalsIgnoreCase(action)) {
            forward(request, response, "/jsp/user/add-user.jsp");
            return;
        }
        if ("logout".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            redirect(response, request, "/jsp/login.jsp?message=Logged out successfully");
            return;
        }
        if ("edit".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            request.setAttribute("user", userService.findById(getServletContext(), id));
            forward(request, response, "/jsp/user/edit-user.jsp");
            return;
        }
        request.setAttribute("users", userService.findAll(getServletContext()));
        request.setAttribute("message", request.getParameter("message"));
        forward(request, response, "/jsp/user/list-user.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = value(request, "action", "save");
        if ("login".equalsIgnoreCase(action)) {
            handleLogin(request, response);
            return;
        }

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = value(request, "role", "Customer");

        User user = createUser(id, name, email, username, password, role);

        if ("update".equalsIgnoreCase(action)) {
            userService.update(getServletContext(), user);
        } else {
            User existing = userService.findByUsername(getServletContext(), username);
            if (existing == null) {
                userService.add(getServletContext(), user);
            } else {
                request.setAttribute("message", "Username already exists");
                forward(request, response, "/jsp/user/add-user.jsp");
                return;
            }
        }
        redirect(response, request, "/users?action=list&message=User saved successfully");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.login(getServletContext(), username, password);
        if (user == null) {
            request.setAttribute("message", "Invalid username or password");
            forward(request, response, "/jsp/login.jsp");
            return;
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("loggedInUser", user);
        redirect(response, request, "/jsp/dashboard.jsp?message=Welcome+" + encode(user.getName()));
    }

    private User createUser(String id, String name, String email, String username, String password, String role) {
        if ("Admin".equalsIgnoreCase(role)) {
            return new Admin(id, name, email, username, password);
        }
        if ("Customer".equalsIgnoreCase(role)) {
            return new Customer(id, name, email, username, password);
        }
        return new User(id, name, email, username, password, role);
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

    private String encode(String value) {
        return value == null ? "" : value.replace(" ", "+");
    }
}