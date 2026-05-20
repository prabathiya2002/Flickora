<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%
    String message = (String) request.getAttribute("message");
    if (message == null || message.trim().isEmpty()) {
        message = request.getParameter("message");
    }
    User currentUser = (User) session.getAttribute("loggedInUser");
    if (currentUser != null) {
        response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Login | Online Movie Ticket Reservation System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <style>
        body {
            min-height: 100vh;
            background: radial-gradient(circle at top left, rgba(245, 158, 11, 0.22), transparent 28%), linear-gradient(135deg, #111827 0%, #1f2937 45%, #0f172a 100%);
        }
        .login-card {
            border: 0;
            border-radius: 24px;
            box-shadow: 0 24px 60px rgba(0, 0, 0, 0.24);
        }
    </style>
</head>
<body class="d-flex align-items-center py-5">
<div class="container">
    <div class="row justify-content-center">
        <div class="col-12 col-md-10 col-lg-6 col-xl-5">
            <% if (message != null && !message.trim().isEmpty()) { %>
            <div class="alert alert-warning border-0 login-card mb-4"><%= message %></div>
            <% } %>
            <div class="card login-card">
                <div class="card-body p-4 p-md-5">
                    <div class="text-center mb-4">
                        <div class="badge rounded-pill text-bg-dark px-3 py-2 mb-3">Online Movie Ticket Reservation System</div>
                        <h2 class="fw-bold mb-2">Welcome back</h2>
                        <p class="text-secondary mb-0">Login to manage reservations and module records.</p>
                    </div>
                    <form action="<%= request.getContextPath() %>/users" method="post" class="needs-validation" novalidate>
                        <input type="hidden" name="action" value="login">
                        <div class="mb-3">
                            <label class="form-label">Username</label>
                            <input type="text" class="form-control form-control-lg" name="username" required>
                            <div class="invalid-feedback">Username is required.</div>
                        </div>
                        <div class="mb-4">
                            <label class="form-label">Password</label>
                            <input type="password" class="form-control form-control-lg" name="password" required>
                            <div class="invalid-feedback">Password is required.</div>
                        </div>
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-warning btn-lg fw-bold">Login</button>
                            <a href="<%= request.getContextPath() %>/users?action=add" class="btn btn-outline-secondary">Create a customer account</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
(() => {
  'use strict';
  const forms = document.querySelectorAll('.needs-validation');
  Array.from(forms).forEach(form => {
    form.addEventListener('submit', event => {
      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
      }
      form.classList.add('was-validated');
    }, false);
  });
})();
</script>
</body>
</html>