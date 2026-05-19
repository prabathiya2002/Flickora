<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Movie" %>
<%@ page import="model.User" %>
<%
    request.setAttribute("pageTitle", "Add Review");
    List<Movie> movies = (List<Movie>) request.getAttribute("movies");
    if (movies == null) {
        movies = java.util.Collections.emptyList();
    }
    List<User> users = (List<User>) request.getAttribute("users");
    if (users == null) {
        users = java.util.Collections.emptyList();
    }
%>
<%@ include file="../common/header.jspf" %>
<div class="row justify-content-center">
    <div class="col-12 col-xl-8">
        <div class="card app-card">
            <div class="card-body p-4 p-lg-5">
                <div class="d-flex justify-content-between align-items-start mb-4">
                    <div>
                        <h2 class="fw-bold mb-1">Add Review</h2>
                        <p class="text-secondary mb-0">Capture a movie rating from a customer.</p>
                    </div>
                    <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/reviews?action=list">Back</a>
                </div>
                <form class="needs-validation" novalidate action="<%= request.getContextPath() %>/reviews" method="post">
                    <input type="hidden" name="action" value="save">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">User</label>
                            <select class="form-select" name="userId" required>
                                <option value="">Select user</option>
                                <% for (User user : users) { %>
                                <option value="<%= user.getId() %>"><%= user.getName() %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Movie</label>
                            <select class="form-select" name="movieId" required>
                                <option value="">Select movie</option>
                                <% for (Movie movie : movies) { %>
                                <option value="<%= movie.getId() %>"><%= movie.getName() %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Rating</label>
                            <select class="form-select" name="rating" required>
                                <option value="">Choose rating</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>
                        </div>
                        <div class="col-md-8">
                            <label class="form-label">Comment</label>
                            <input type="text" class="form-control" name="comment" required>
                        </div>
                    </div>
                    <div class="d-flex gap-2 mt-4">
                        <button type="submit" class="btn btn-warning fw-bold">Save Review</button>
                        <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/reviews?action=list">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>