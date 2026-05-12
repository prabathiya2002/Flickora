<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Seat" %>
<%
    request.setAttribute("pageTitle", "Seat Management");
    List<Seat> seats = (List<Seat>) request.getAttribute("seats");
    if (seats == null) {
        seats = java.util.Collections.emptyList();
    }
%>
<%@ include file="../common/header.jspf" %>
<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Seat Management</h2>
        <p class="text-secondary mb-0">Monitor seat availability by showtime.</p>
    </div>
    <a class="btn btn-warning fw-bold" href="<%= request.getContextPath() %>/seats?action=add">Add Seat</a>
</div>
<div class="card app-card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                <tr>
                    <th>ID</th><th>Showtime</th><th>Seat No.</th><th>Status</th><th>Type</th><th>Price</th><th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (Seat seat : seats) { %>
                <tr>
                    <td><%= seat.getId() %></td>
                    <td><%= seat.getShowtimeId() %></td>
                    <td><%= seat.getSeatNumber() %></td>
                    <td><span class="badge <%= "Booked".equalsIgnoreCase(seat.getStatus()) ? "text-bg-danger" : "text-bg-success" %>"><%= seat.getStatus() %></span></td>
                    <td><%= seat.getType() %></td>
                    <td><%= seat.getPrice() %></td>
                    <td class="text-end">
                        <a class="btn btn-sm btn-outline-primary" href="<%= request.getContextPath() %>/seats?action=edit&id=<%= seat.getId() %>">Edit</a>
                        <a class="btn btn-sm btn-outline-danger" href="<%= request.getContextPath() %>/seats?action=delete&id=<%= seat.getId() %>" onclick="return confirm('Delete this seat?')">Delete</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>