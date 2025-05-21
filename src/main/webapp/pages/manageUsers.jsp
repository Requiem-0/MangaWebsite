<%@ page import="java.util.List" %>
<%@ page import="com.manga.models.User" %>
<%
    // Retrieve user object from session
    User loggedInUser = (User) session.getAttribute("user");

    // Redirect if not logged in
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }

    // Check for role 
    if (!"admin".equals(loggedInUser.getRole())) {
        response.sendRedirect(request.getContextPath() + "/pages/unauthorized.jsp");
        return;

    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>User Admin Panel</title>
  <!-- Bootstrap 5 CDN -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
  <!-- External Custom CSS -->
  <link href="<%= request.getContextPath() %>/css/manage_users.css" rel="stylesheet" />
</head>
<body>
  <div class="container-fluid">
    <div class="row">
      <!-- Sidebar -->
      <nav class="col-md-3 col-lg-2 sidebar d-md-block">
      <h2>Admin Panel</h2>
      <a href="<%= request.getContextPath() %>/DashboardController">Dashboard</a>
      <a href="<%= request.getContextPath() %>/ManageMangaController?action=list">Manage Manga</a>
      <a href="<%= request.getContextPath() %>/ManageVolumeController?action=list">Manage Volume</a>
      <a href="<%= request.getContextPath() %>/ManageChapterController?action=list" class="active">Manage Chapter</a>
	  <a href="<%= request.getContextPath() %>/ManageUsersServlet">Manage Users</a>
      <a href="#">Logout</a>
      </nav>

      <!-- Main Content -->
      <main class="col-md-9 col-lg-10 main-content">
        <div class="header">
          <h1 class="h4">Manage Users</h1>
        </div>

        <div class="content-box">
          <h2 class="h5" style="margin-bottom:20px">User Management</h2>
          <table class="table table-dark table-striped">
            <thead>
              <tr>
                <th>User ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Password</th>
                <th>Role</th>
                <th>Actions</th>
                
              </tr>
            </thead>
            <tbody>
            <%
          	List<User> userList = (List<User>) request.getAttribute("userList");
          	if (userList != null && !userList.isEmpty()) {
            	for (User user : userList) {
        	%>
              <tr>
	          <td><%= user.getUserId() %></td>
	   		  <td><%= user.getUsername() %></td>
	          <td><%= user.getEmail() %></td>
	          <td><%= user.getPassword() %></td>
	          <td><%= user.getRole() %></td>	          
	          
                <td>
  <form action="ManageUsersServlet" method="post" style="display:inline;">
    <input type="hidden" name="action" value="changeRole">
    <input type="hidden" name="userId" value="<%= user.getUserId() %>">

    <select name="newRole" style="display:inline-block; width: 100px;">
      <option value="user" <%= "user".equals(user.getRole()) ? "selected" : "" %>>User</option>
      <option value="admin" <%= "admin".equals(user.getRole()) ? "selected" : "" %>>Admin</option>
    </select>
    <button type="submit" style="display:inline-block; margin-left: 5px;">Change Role</button>
  </form>

  <button class="btn btn-sm btn-danger" 
          style="margin-left:10px;"
          onclick="if(confirm('Are you sure?')) { window.location='ManageUsersServlet?action=deleteUser&userId=<%=user.getUserId()%>'; }">
    Delete
  </button>
</td>
                
              </tr>
                      <%
              } // end for
          } else {
        %>
        <tr>
          <td colspan="5" class="text-center">No users found.</td>
        </tr>
        <%
          } // end if-else
        %>
            </tbody>
          </table>
        </div>
      </main>
    </div>
  </div>

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
