<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    request.setAttribute("pageTitle", "Dashboard");
%>
<%@ include file="common/header.jspf" %>
<div class="hero-banner p-4 p-lg-5 mb-4 app-card">
    <div class="row align-items-center g-4">
        <div class="col-lg-8">
            <div class="badge brand-badge rounded-pill mb-3 px-3 py-2">Online Movie Ticket Reservation System</div>
            <h1 class="display-6 fw-bold">Manage users, movies, showtimes, bookings, seats, and reviews from one dashboard.</h1>
            <p class="lead text-white-50 mb-0">Built with Java, JSP, Servlets, Bootstrap, and text-file persistence for a full SE1020 MVC submission.</p>
        </div>
        <div class="col-lg-4">
            <div class="bg-white text-dark rounded-4 p-4 shadow-sm">
                <h5 class="fw-bold mb-3">Quick Actions</h5>
                <div class="d-grid gap-2">
                    <a class="btn btn-dark" href="<%= request.getContextPath() %>/users?action=list">User Management</a>
                    <a class="btn btn-outline-dark" href="<%= request.getContextPath() %>/movies?action=list">Movie Management</a>
                    <a class="btn btn-warning" href="<%= request.getContextPath() %>/reservations?action=add">Book Ticket</a>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row g-4">
    <div class="col-md-4"><div class="card app-card h-100"><div class="card-body"><h5 class="fw-bold">User Management</h5><p class="text-secondary">Register, login, update profiles, and manage roles.</p><a class="btn btn-sm btn-dark" href="<%= request.getContextPath() %>/users?action=list">Open</a></div></div></div>
    <div class="col-md-4"><div class="card app-card h-100"><div class="card-body"><h5 class="fw-bold">Movie Management</h5><p class="text-secondary">Maintain movie details, language, duration, and ratings.</p><a class="btn btn-sm btn-dark" href="<%= request.getContextPath() %>/movies?action=list">Open</a></div></div></div>
    <div class="col-md-4"><div class="card app-card h-100"><div class="card-body"><h5 class="fw-bold">Showtime Management</h5><p class="text-secondary">Publish schedules by movie, hall, theater, date, and time.</p><a class="btn btn-sm btn-dark" href="<%= request.getContextPath() %>/showtimes?action=list">Open</a></div></div></div>
    <div class="col-md-4"><div class="card app-card h-100"><div class="card-body"><h5 class="fw-bold">Ticket Reservation</h5><p class="text-secondary">Book, update, and cancel reservations with seat selection.</p><a class="btn btn-sm btn-dark" href="<%= request.getContextPath() %>/reservations?action=list">Open</a></div></div></div>
    <div class="col-md-4"><div class="card app-card h-100"><div class="card-body"><h5 class="fw-bold">Seat Management</h5><p class="text-secondary">Manage availability and seat types for each showtime.</p><a class="btn btn-sm btn-dark" href="<%= request.getContextPath() %>/seats?action=list">Open</a></div></div></div>
    <div class="col-md-4"><div class="card app-card h-100"><div class="card-body"><h5 class="fw-bold">Reviews & Ratings</h5><p class="text-secondary">Capture customer feedback and movie ratings.</p><a class="btn btn-sm btn-dark" href="<%= request.getContextPath() %>/reviews?action=list">Open</a></div></div></div>
</div>
<%@ include file="common/footer.jspf" %>