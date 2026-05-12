<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Showtime" %>
<%
    request.setAttribute("pageTitle", "Showtime Management");
    List<Showtime> showtimes = (List<Showtime>) request.getAttribute("showtimes");
    if (showtimes == null) {
        showtimes = java.util.Collections.emptyList();
    }
%>
<%@ include file="../common/header.jspf" %>
<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Showtime Management</h2>
        <p class="text-secondary mb-0">Manage theatre schedules and hall assignments.</p>
    </div>
    <a class="btn btn-warning fw-bold" href="<%= request.getContextPath() %>/showtimes?action=add">Add Showtime</a>
</div>
<div class="card app-card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                <tr>
                    <th>ID</th><th>Movie</th><th>Theater</th><th>Date</th><th>Time</th><th>Hall</th><th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (Showtime showtime : showtimes) { %>
                <tr>
                    <td><%= showtime.getId() %></td>
                    <td><%= showtime.getMovieName() %></td>
                    <td><%= showtime.getTheater() %></td>
                    <td><%= showtime.getShowDate() %></td>
                    <td><%= showtime.getShowTime() %></td>
                    <td><%= showtime.getHall() %></td>
                    <td class="text-end">
                        <a class="btn btn-sm btn-outline-primary" href="<%= request.getContextPath() %>/showtimes?action=edit&id=<%= showtime.getId() %>">Edit</a>
                        <a class="btn btn-sm btn-outline-danger" href="<%= request.getContextPath() %>/showtimes?action=delete&id=<%= showtime.getId() %>" onclick="return confirm('Delete this showtime?')">Delete</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>