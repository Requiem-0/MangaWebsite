<%
// Disable caching for login page
response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);

// Check if the user is already logged in
if (session != null && session.getAttribute("username") != null) {
    response.sendRedirect("profile.jsp");
    return;
}
%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Login | MangaZone</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">  
</head>
<body>

  <div class="form-container">
    <form method="POST" action="${pageContext.request.contextPath}/LoginController">
    <div class="logo"  style="margin:0 auto">
		<img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="logo" style=" width:350px">
		</div>
		<%
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
%>

<% if (error != null) { %>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <strong>Error:</strong>
        <% if ("invalid_credentials".equals(error)) { %>
            Invalid email or password.
        <% } else if ("unknown_role".equals(error)) { %>
            Unknown user role. Please contact support.
        <% } %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
<% } %>


		
      <div class="mb-3">
        <label for="email" class="form-label">Email address</label>
        <input type="email" class="form-control" id="email" name="email" required />
      </div>

      <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input type="password" class="form-control" id="password" name="password" required />
      </div>

      <button type="submit" class="btn btn-primary w-100">Log In</button> 
    </form>

    <p class="text-center mt-3">Don't have an account? <a href="${pageContext.request.contextPath}/pages/registerUser.jsp">Register</a></p>
  </div>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js" integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq" crossorigin="anonymous"></script>

</body>
</html>