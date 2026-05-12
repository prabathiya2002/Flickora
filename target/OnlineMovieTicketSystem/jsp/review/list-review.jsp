<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Review" %>
<%
    request.setAttribute("pageTitle", "Review Management");
    List<Review> reviews = (List<Review>) request.getAttribute("reviews");
    if (reviews == null) {
        reviews = java.util.Collections.emptyList();
    }
%>
<%@ include file="../common/header.jspf" %>
<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Review & Rating System</h2>
        <p class="text-secondary mb-0">Track customer feedback for movies.</p>
    </div>
    <a class="btn btn-warning fw-bold" href="<%= request.getContextPath() %>/reviews?action=add">Add Review</a>
</div>
<div class="card app-card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                <tr>
                    <th>ID</th><th>User</th><th>Movie</th><th>Rating</th><th>Comment</th><th>Date</th><th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (Review review : reviews) { %>
                <tr>
                    <td><%= review.getId() %></td>
                    <td><%= review.getUserName() %></td>
                    <td><%= review.getMovieName() %></td>
                    <td><span class="badge text-bg-warning text-dark"><%= review.getRating() %></span></td>
                    <td><%= review.getComment() %></td>
                    <td><%= review.getReviewDate() %></td>
                    <td class="text-end">
                        <a class="btn btn-sm btn-outline-primary" href="<%= request.getContextPath() %>/reviews?action=edit&id=<%= review.getId() %>">Edit</a>
                        <a class="btn btn-sm btn-outline-danger" href="<%= request.getContextPath() %>/reviews?action=delete&id=<%= review.getId() %>" onclick="return confirm('Delete this review?')">Delete</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>