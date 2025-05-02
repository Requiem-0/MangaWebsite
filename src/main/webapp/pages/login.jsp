<%
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
  <link href="../css/style.css" rel="stylesheet">  
</head>
<body>

  <div class="form-container">
    <div class="logo"  style="margin:0 auto">
		<img src="../resources/images/logo.png" alt="logo" style=" width:350px">
		</div>
    <form method="POST" action="../LoginController">
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

    <p class="text-center mt-3">Don't have an account? <a href="registerUser.jsp">Register</a></p>
  </div>

</body>
</html>