<%
if (session != null && session.getAttribute("username") != null) {
    response.sendRedirect("profile.jsp");
    return;
}
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Register | MangaZone</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>

	<div class="form-container">
		<div class="logo"  style="margin:0 auto">
		<img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="logo" style=" width:350px">
		</div>
        <% 
        String error = (String) request.getAttribute("error");
        Boolean success = (Boolean) request.getAttribute("success"); 
        %>

        <%-- Error handling --%>
        <% if (error != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error:</strong>
                <% if ("password_mismatch".equals(error)) { %>
                    Password and Confirm Password do not match.
                <% } else if ("fail".equals(error)) { %>
                    Registration failed. Please try again.
                <% } else if ("user_exists".equals(error)) { %>
                    This email is already registered. Please use a different email.
                <% } %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        <% } %>

        <%-- Success handling --%>
        <% if (success != null && success) { %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <strong>Success:</strong> Registration completed successfully. You can now log in!
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        <% } %>
	

		<form method="POST" action="../RegistrationController">
			<div class="mb-3">
				<label for="name" class="form-label">Username</label> <input
					type="text" class="form-control" id="name" name="username" required />
			</div>

			<div class="mb-3">
				<label for="email" class="form-label">Email address</label> <input
					type="email" class="form-control" id="email" name="email" required />
			</div>

			<div class="mb-3">
				<label for="password" class="form-label">Password</label> <input
					type="password" class="form-control" id="password" name="password"
					required />
			</div>

			<div class="mb-4">
				<label for="confirmPassword" class="form-label">Confirm
					Password</label> <input type="password" class="form-control"
					id="confirmPassword" name="confirmPassword" required />
			</div>

			<button type="submit" class="btn btn-primary w-100">Create
				Account</button>
		</form>

		<p class="text-center mt-3">
			Already have an account? <a href="login.jsp">Log in</a>
		</p>
	</div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js" integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq" crossorigin="anonymous"></script>

</body>
</html>