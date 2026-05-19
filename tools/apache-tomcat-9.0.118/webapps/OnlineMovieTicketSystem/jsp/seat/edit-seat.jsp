<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Seat" %>
<%@ page import="model.Showtime" %>
<%
    request.setAttribute("pageTitle", "Edit Seat");
    Seat seat = (Seat) request.getAttribute("seat");
    if (seat == null) {
        seat = new Seat();
    }
    List<Showtime> showtimes = (List<Showtime>) request.getAttribute("showtimes");
    if (showtimes == null) {
        showtimes = java.util.Collections.emptyList();
    }
%>
<%@ include file="../common/header.jspf" %>
<div class="row justify-content-center">
    <div class="col-12 col-xl-8">
        <div class="card app-card">
            <div class="card-body p-4 p-lg-5">
                <div class="d-flex justify-content-between align-items-start mb-4">
                    <div>
                        <h2 class="fw-bold mb-1">Edit Seat</h2>
                        <p class="text-secondary mb-0">Update seat type, status, and showtime assignment.</p>
                    </div>
                    <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/seats?action=list">Back</a>
                </div>
                <form class="needs-validation" novalidate action="<%= request.getContextPath() %>/seats" method="post">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="<%= seat.getId() == null ? "" : seat.getId() %>">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Showtime</label>
                            <select class="form-select" name="showtimeId" required>
                                <option value="">Select showtime</option>
                                <% for (Showtime showtime : showtimes) { %>
                                <option value="<%= showtime.getId() %>" <%= showtime.getId() != null && showtime.getId().equalsIgnoreCase(seat.getShowtimeId()) ? "selected" : "" %>><%= showtime.getMovieName() %> - <%= showtime.getShowDate() %> <%= showtime.getShowTime() %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="col-md-6"><label class="form-label">Seat Number</label><input type="text" class="form-control" name="seatNumber" value="<%= seat.getSeatNumber() == null ? "" : seat.getSeatNumber() %>" required></div>
                        <div class="col-md-6">
                            <label class="form-label">Type</label>
                            <select class="form-select" name="type" required>
                                <option value="Regular" <%= "Regular".equalsIgnoreCase(seat.getType()) ? "selected" : "" %>>Regular</option>
                                <option value="VIP" <%= "VIP".equalsIgnoreCase(seat.getType()) ? "selected" : "" %>>VIP</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Status</label>
                            <select class="form-select" name="status" required>
                                <option value="Available" <%= "Available".equalsIgnoreCase(seat.getStatus()) ? "selected" : "" %>>Available</option>
                                <option value="Booked" <%= "Booked".equalsIgnoreCase(seat.getStatus()) ? "selected" : "" %>>Booked</option>
                            </select>
                        </div>
                    </div>
                    <div class="d-flex gap-2 mt-4">
                        <button type="submit" class="btn btn-warning fw-bold">Update Seat</button>
                        <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/seats?action=list">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>