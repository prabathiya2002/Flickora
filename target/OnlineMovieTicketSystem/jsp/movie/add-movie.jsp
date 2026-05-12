<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    request.setAttribute("pageTitle", "Add Movie");
%>
<%@ include file="../common/header.jspf" %>
<div class="row justify-content-center">
    <div class="col-12 col-lg-8 col-xl-7">
        <div class="card app-card">
            <div class="card-body p-4 p-lg-5">
                <div class="d-flex justify-content-between align-items-start mb-4">
                    <div>
                        <h2 class="fw-bold mb-1">Add Movie</h2>
                        <p class="text-secondary mb-0">Create a new movie record.</p>
                    </div>
                    <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/movies?action=list">Back</a>
                </div>
                <form class="needs-validation" novalidate action="<%= request.getContextPath() %>/movies" method="post">
                    <input type="hidden" name="action" value="save">
                    <div class="row g-3">
                        <div class="col-md-6"><label class="form-label">Movie Name</label><input type="text" class="form-control" name="name" required></div>
                        <div class="col-md-6"><label class="form-label">Genre</label><input type="text" class="form-control" name="genre" required></div>
                        <div class="col-md-6"><label class="form-label">Duration</label><input type="text" class="form-control" name="duration" placeholder="120 min" required></div>
                        <div class="col-md-6"><label class="form-label">Language</label><input type="text" class="form-control" name="language" required></div>
                        <div class="col-md-6"><label class="form-label">Rating</label><input type="text" class="form-control" name="rating" placeholder="U, PG, 18+" required></div>
                    </div>
                    <div class="d-flex gap-2 mt-4">
                        <button type="submit" class="btn btn-warning fw-bold">Save Movie</button>
                        <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/movies?action=list">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jspf" %>