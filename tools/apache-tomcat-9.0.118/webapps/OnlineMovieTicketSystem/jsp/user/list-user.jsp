<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%
    request.setAttribute("pageTitle", "User Management");
    List<User> users = (List<User>) request.getAttribute("users");
    if (users == null) {
        users = java.util.Collections.emptyList();
    }
%>
<%@ include file="../common/header.jspf" %>
<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">User Management</h2>
        <p class="text-secondary mb-0">Create, view, update, and delete users.</p>
    </div>
    <a class="btn btn-warning fw-bold" href="<%= request.getContextPath() %>/users?action=add">Add User</a>
</div>
<div class="card app-card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                <tr>
                    <th>ID</th><th>Name</th><th>Email</th><th>Username</th><th>Role</th><th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (User user : users) { %>
                <tr>
                    <td><%= user.getId() %></td>
                    <td><%= user.getName() %></td>
                    <td><%= user.getEmail() %></td>
                    <td><%= user.getUsername() %></td>
                    <td><span class="badge text-bg-dark"><%= user.getRole() %></span></td>
                    <td class="text-end">
                        <a class="btn btn-sm btn-outline-primary" href="<%= request.getContextPath() %>/users?action=edit&id=<%= user.getId() %>">Edit</a>
                        <a class="btn btn-sm btn-outline-danger" href="<%= request.getContextPath() %>/users?action=delete&id=<%= user.getId() %>" onclick="return confirm('Delete this user?')">Delete</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>