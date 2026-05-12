<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Movie" %>
<%
    request.setAttribute("pageTitle", "Edit Movie");
    Movie movie = (Movie) request.getAttribute("movie");
    if (movie == null) {
        movie = new Movie();
    }
%>
<%@ include file="../common/header.jspf" %>
<div class="row justify-content-center">
    <div class="col-12 col-lg-8 col-xl-7">
        <div class="card app-card">
            <div class="card-body p-4 p-lg-5">
                <div class="d-flex justify-content-between align-items-start mb-4">
                    <div>
                        <h2 class="fw-bold mb-1">Edit Movie</h2>
                        <p class="text-secondary mb-0">Update movie details.</p>
                    </div>
                    <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/movies?action=list">Back</a>
                </div>
                <form class="needs-validation" novalidate action="<%= request.getContextPath() %>/movies" method="post">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="<%= movie.getId() == null ? "" : movie.getId() %>">
                    <div class="row g-3">
                        <div class="col-md-6"><label class="form-label">Movie Name</label><input type="text" class="form-control" name="name" value="<%= movie.getName() == null ? "" : movie.getName() %>" required></div>
                        <div class="col-md-6"><label class="form-label">Genre</label><input type="text" class="form-control" name="genre" value="<%= movie.getGenre() == null ? "" : movie.getGenre() %>" required></div>
                        <div class="col-md-6"><label class="form-label">Duration</label><input type="text" class="form-control" name="duration" value="<%= movie.getDuration() == null ? "" : movie.getDuration() %>" required></div>
                        <div class="col-md-6"><label class="form-label">Language</label><input type="text" class="form-control" name="language" value="<%= movie.getLanguage() == null ? "" : movie.getLanguage() %>" required></div>
                        <div class="col-md-6"><label class="form-label">Rating</label><input type="text" class="form-control" name="rating" value="<%= movie.getRating() == null ? "" : movie.getRating() %>" required></div>
                    </div>
                    <div class="d-flex gap-2 mt-4">
                        <button type="submit" class="btn btn-warning fw-bold">Update Movie</button>
                        <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/movies?action=list">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>