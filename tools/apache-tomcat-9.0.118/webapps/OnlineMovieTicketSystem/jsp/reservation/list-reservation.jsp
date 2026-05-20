<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Reservation" %>
<%
    request.setAttribute("pageTitle", "Reservation Management");
    List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
    if (reservations == null) {
        reservations = java.util.Collections.emptyList();
    }
%>
<%@ include file="../common/header.jspf" %>
<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Ticket Reservation Management</h2>
        <p class="text-secondary mb-0">Book, update, and cancel reservations.</p>
    </div>
    <a class="btn btn-warning fw-bold" href="<%= request.getContextPath() %>/reservations?action=add">Book Ticket</a>
</div>
<div class="card app-card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                <tr>
                    <th>ID</th><th>User</th><th>Movie</th><th>Showtime</th><th>Seat</th><th>Status</th><th>Date</th><th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (Reservation reservation : reservations) { %>
                <tr>
                    <td><%= reservation.getId() %></td>
                    <td><%= reservation.getUserName() %></td>
                    <td><%= reservation.getMovieName() %></td>
                    <td><%= reservation.getShowtimeId() %></td>
                    <td><%= reservation.getSeatNumber() %></td>
                    <td><span class="badge <%= "Cancelled".equalsIgnoreCase(reservation.getStatus()) ? "text-bg-secondary" : "text-bg-success" %>"><%= reservation.getStatus() %></span></td>
                    <td><%= reservation.getBookingDate() %></td>
                    <td class="text-end">
                        <a class="btn btn-sm btn-outline-primary" href="<%= request.getContextPath() %>/reservations?action=edit&id=<%= reservation.getId() %>">Edit</a>
                        <a class="btn btn-sm btn-outline-danger" href="<%= request.getContextPath() %>/reservations?action=delete&id=<%= reservation.getId() %>" onclick="return confirm('Cancel this reservation?')">Cancel</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>