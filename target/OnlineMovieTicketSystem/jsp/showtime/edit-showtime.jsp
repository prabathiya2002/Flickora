<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Movie" %>
<%@ page import="model.Showtime" %>
<%
    request.setAttribute("pageTitle", "Edit Showtime");
    Showtime showtime = (Showtime) request.getAttribute("showtime");
    if (showtime == null) {
        showtime = new Showtime();
    }
    List<Movie> movies = (List<Movie>) request.getAttribute("movies");
    if (movies == null) {
        movies = java.util.Collections.emptyList();
    }
%>
<%@ include file="../common/header.jspf" %>
<div class="row justify-content-center">
    <div class="col-12 col-xl-8">
        <div class="card app-card">
            <div class="card-body p-4 p-lg-5">
                <div class="d-flex justify-content-between align-items-start mb-4">
                    <div>
                        <h2 class="fw-bold mb-1">Edit Showtime</h2>
                        <p class="text-secondary mb-0">Update the scheduled screening details.</p>
                    </div>
                    <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/showtimes?action=list">Back</a>
                </div>
                <form class="needs-validation" novalidate action="<%= request.getContextPath() %>/showtimes" method="post">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="<%= showtime.getId() == null ? "" : showtime.getId() %>">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Movie</label>
                            <select class="form-select" name="movieId" required>
                                <option value="">Select movie</option>
                                <% for (Movie movie : movies) { %>
                                <option value="<%= movie.getId() %>" <%= movie.getId() != null && movie.getId().equalsIgnoreCase(showtime.getMovieId()) ? "selected" : "" %>><%= movie.getName() %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="col-md-6"><label class="form-label">Theater</label><input type="text" class="form-control" name="theater" value="<%= showtime.getTheater() == null ? "" : showtime.getTheater() %>" required></div>
                        <div class="col-md-4"><label class="form-label">Date</label><input type="date" class="form-control" name="showDate" value="<%= showtime.getShowDate() == null ? "" : showtime.getShowDate() %>" required></div>
                        <div class="col-md-4"><label class="form-label">Time</label><input type="time" class="form-control" name="showTime" value="<%= showtime.getShowTime() == null ? "" : showtime.getShowTime() %>" required></div>
                        <div class="col-md-4"><label class="form-label">Hall</label><input type="text" class="form-control" name="hall" value="<%= showtime.getHall() == null ? "" : showtime.getHall() %>" required></div>
                    </div>
                    <div class="d-flex gap-2 mt-4">
                        <button type="submit" class="btn btn-warning fw-bold">Update Showtime</button>
                        <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/showtimes?action=list">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>