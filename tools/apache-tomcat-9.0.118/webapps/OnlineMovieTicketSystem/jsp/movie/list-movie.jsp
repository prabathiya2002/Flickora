<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Movie" %>
<%
    request.setAttribute("pageTitle", "Movie Management");
    List<Movie> movies = (List<Movie>) request.getAttribute("movies");
    if (movies == null) {
        movies = java.util.Collections.emptyList();
    }
%>
<%@ include file="../common/header.jspf" %>
<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Movie Management</h2>
        <p class="text-secondary mb-0">Maintain the movie catalogue.</p>
    </div>
    <a class="btn btn-warning fw-bold" href="<%= request.getContextPath() %>/movies?action=add">Add Movie</a>
</div>
<div class="card app-card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                <tr>
                    <th>ID</th><th>Name</th><th>Genre</th><th>Duration</th><th>Language</th><th>Rating</th><th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (Movie movie : movies) { %>
                <tr>
                    <td><%= movie.getId() %></td>
                    <td><%= movie.getName() %></td>
                    <td><%= movie.getGenre() %></td>
                    <td><%= movie.getDuration() %></td>
                    <td><%= movie.getLanguage() %></td>
                    <td><span class="badge text-bg-warning text-dark"><%= movie.getRating() %></span></td>
                    <td class="text-end">
                        <a class="btn btn-sm btn-outline-primary" href="<%= request.getContextPath() %>/movies?action=edit&id=<%= movie.getId() %>">Edit</a>
                        <a class="btn btn-sm btn-outline-danger" href="<%= request.getContextPath() %>/movies?action=delete&id=<%= movie.getId() %>" onclick="return confirm('Delete this movie?')">Delete</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>