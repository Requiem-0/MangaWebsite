<%
    // Retrieve the username and email from the session
    String username = (String) session.getAttribute("username");
    String email = (String) session.getAttribute("email");

    // If the user is not logged in, redirect them to the login page
    if (username == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <link rel="stylesheet" href="../css/profile.css">
  <title>Profile Settings</title>
</head>
<body>
  <div class="container">
    <!-- Profile Section -->
    <div class="profile-section">
      <img src="fang.jpg" alt="Profile" class="profile-image" />
      <div class="profile-details">
      <h1 class="welcome" style="text-align:center">Welcome: <%= username != null ? username : "Guest" %></h1>
        <div class="input-group">
            <input type="text" placeholder="<%= username != null ? username : "" %>"  />
          </div>
          <div class="input-group">
            <input type="text" placeholder="<%= email != null ? email : "" %>"  />
          </div>
        <div class="button-group">
          <button class="edit-btn">Edit Photo</button>
          <button class="delete-btn">Delete Photo</button>
          <button class="save-btn">Save Changes</button>
        </div>
      </div>
    </div>

    <!-- Password Section -->
    <div class="password-section">
      <h2>Change Password</h2>
      <div class="input-group">
        <input type="password" placeholder="Current password" />
      </div>
      <div class="input-group">
        <input type="password" placeholder="New Password" />
      </div>
      <div class="input-group">
        <input type="password" placeholder="Confirm Password" />
      </div>
      <button class="save-btn">Save Changes</button>
    </div>
  </div>
</body>
</html>