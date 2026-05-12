<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Movie" %>
<%
    request.setAttribute("pageTitle", "Add Showtime");
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
                        <h2 class="fw-bold mb-1">Add Showtime</h2>
                        <p class="text-secondary mb-0">Assign movie, theater, hall, and schedule details.</p>
                    </div>
                    <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/showtimes?action=list">Back</a>
                </div>
                <form class="needs-validation" novalidate action="<%= request.getContextPath() %>/showtimes" method="post">
                    <input type="hidden" name="action" value="save">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Movie</label>
                            <select class="form-select" name="movieId" required>
                                <option value="">Select movie</option>
                                <% for (Movie movie : movies) { %>
                                <option value="<%= movie.getId() %>"><%= movie.getName() %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="col-md-6"><label class="form-label">Theater</label><input type="text" class="form-control" name="theater" required></div>
                        <div class="col-md-4"><label class="form-label">Date</label><input type="date" class="form-control" name="showDate" required></div>
                        <div class="col-md-4"><label class="form-label">Time</label><input type="time" class="form-control" name="showTime" required></div>
                        <div class="col-md-4"><label class="form-label">Hall</label><input type="text" class="form-control" name="hall" required></div>
                    </div>
                    <div class="d-flex gap-2 mt-4">
                        <button type="submit" class="btn btn-warning fw-bold">Save Showtime</button>
                        <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/showtimes?action=list">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>