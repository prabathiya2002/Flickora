<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Seat" %>
<%@ page import="model.Showtime" %>
<%@ page import="model.User" %>
<%
    request.setAttribute("pageTitle", "Book Ticket");
    List<User> users = (List<User>) request.getAttribute("users");
    if (users == null) {
        users = java.util.Collections.emptyList();
    }
    List<Showtime> showtimes = (List<Showtime>) request.getAttribute("showtimes");
    if (showtimes == null) {
        showtimes = java.util.Collections.emptyList();
    }
    List<Seat> seats = (List<Seat>) request.getAttribute("seats");
    if (seats == null) {
        seats = java.util.Collections.emptyList();
    }
%>
<%@ include file="../common/header.jspf" %>
<div class="row justify-content-center">
    <div class="col-12 col-xl-9">
        <div class="card app-card">
            <div class="card-body p-4 p-lg-5">
                <div class="d-flex justify-content-between align-items-start mb-4">
                    <div>
                        <h2 class="fw-bold mb-1">Book Ticket</h2>
                        <p class="text-secondary mb-0">Select a user, showtime, and available seat.</p>
                    </div>
                    <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/reservations?action=list">Back</a>
                </div>
                <form class="needs-validation" novalidate action="<%= request.getContextPath() %>/reservations" method="post">
                    <input type="hidden" name="action" value="save">
                    <input type="hidden" name="status" value="Booked">
                    <div class="row g-3">
                        <div class="col-md-4">
                            <label class="form-label">User</label>
                            <select class="form-select" name="userId" required>
                                <option value="">Select user</option>
                                <% for (User user : users) { %>
                                <option value="<%= user.getId() %>"><%= user.getName() %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Showtime</label>
                            <select class="form-select" name="showtimeId" required>
                                <option value="">Select showtime</option>
                                <% for (Showtime showtime : showtimes) { %>
                                <option value="<%= showtime.getId() %>"><%= showtime.getMovieName() %> - <%= showtime.getShowDate() %> <%= showtime.getShowTime() %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Seat</label>
                            <select class="form-select" name="seatId" required>
                                <option value="">Select seat</option>
                                <% for (Seat seat : seats) { %>
                                <option value="<%= seat.getId() %>"><%= seat.getSeatNumber() %> (<%= seat.getType() %>)</option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="d-flex gap-2 mt-4">
                        <button type="submit" class="btn btn-warning fw-bold">Confirm Booking</button>
                        <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/reservations?action=list">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>