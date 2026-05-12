<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%
    request.setAttribute("pageTitle", "Edit User");
    User user = (User) request.getAttribute("user");
    if (user == null) {
        user = new User();
    }
%>
<%@ include file="../common/header.jspf" %>
<div class="row justify-content-center">
    <div class="col-12 col-lg-8 col-xl-7">
        <div class="card app-card">
            <div class="card-body p-4 p-lg-5">
                <div class="d-flex justify-content-between align-items-start mb-4">
                    <div>
                        <h2 class="fw-bold mb-1">Edit User</h2>
                        <p class="text-secondary mb-0">Update the stored user record.</p>
                    </div>
                    <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/users?action=list">Back</a>
                </div>
                <form class="needs-validation" novalidate action="<%= request.getContextPath() %>/users" method="post">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="<%= user.getId() == null ? "" : user.getId() %>">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Full Name</label>
                            <input type="text" class="form-control" name="name" value="<%= user.getName() == null ? "" : user.getName() %>" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Email</label>
                            <input type="email" class="form-control" name="email" value="<%= user.getEmail() == null ? "" : user.getEmail() %>" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Username</label>
                            <input type="text" class="form-control" name="username" value="<%= user.getUsername() == null ? "" : user.getUsername() %>" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Password</label>
                            <input type="text" class="form-control" name="password" value="<%= user.getPassword() == null ? "" : user.getPassword() %>" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Role</label>
                            <select class="form-select" name="role" required>
                                <option value="Customer" <%= "Customer".equalsIgnoreCase(user.getRole()) ? "selected" : "" %>>Customer</option>
                                <option value="Admin" <%= "Admin".equalsIgnoreCase(user.getRole()) ? "selected" : "" %>>Admin</option>
                            </select>
                        </div>
                    </div>
                    <div class="d-flex gap-2 mt-4">
                        <button type="submit" class="btn btn-warning fw-bold">Update User</button>
                        <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/users?action=list">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>