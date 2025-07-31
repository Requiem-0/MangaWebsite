<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.manga.models.Volume, com.manga.models.Manga, com.manga.models.User" %>

<%
    // TEMPORARY: simulate logged-in user for rating test
    if (session.getAttribute("userId") == null) {
        session.setAttribute("userId", 1); // Replace 1 with a real user ID from your database
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manga Rating</title>
    <link rel="stylesheet" href="styles.css"> <!-- Optional: for styling -->
</head>
<body>
    <%
        Manga manga = (Manga) request.getAttribute("manga");
        if (manga != null) {
    %>
        <h1><%= manga.getTitle() %></h1>
        <p><strong>Description:</strong> <%= manga.getDescription() %></p>
    <%
        } else {
    %>
        <h1>Manga Title</h1>
        <p><strong>Genre:</strong> Unknown</p>
        <p><strong>Description:</strong> No Description Available</p>
    <%
        }
    %>

    <!-- Display the average rating -->
    <h2>Average Rating: 
        <span>
            <%= request.getAttribute("avgRating") != null ? request.getAttribute("avgRating") : "N/A" %>
        </span>
    </h2>

    <!-- If the user has rated, display their rating -->
    <%
        Integer userRating = (Integer) request.getAttribute("userRating");
        if (userRating != null) {
    %>
        <p>Your Rating: <%= userRating %></p>
    <%
        } else {
    %>
        <p>You haven't rated this manga yet.</p>
    <%
        }
    %>

    <!-- Rating Form (only shown if the user is logged in) -->
    <%
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
    %>
        <h3>Rate this Manga:</h3>
        <form action="RatingController" method="post">
            <input type="hidden" name="mangaId" value="<%= manga != null ? manga.getMangaId() : "" %>">
            <input type="hidden" name="userId" value="<%= userId %>">
            <label for="rating">Rating (1-5): </label>
            <input type="number" id="rating" name="rating" min="1" max="5" required>
            <button type="submit">Submit Rating</button>
        </form>
    <%
        } else {
    %>
        <p>You need to <a href="login.jsp">log in</a> to rate this manga.</p>
    <%
        }
    %>

    <!-- Show any messages or errors -->
    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
        <div class="message success"><%= message %></div>
    <%
        }

        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div class="message error"><%= error %></div>
    <%
        }
    %>

    <footer>
        <p>&copy; 2025 MangaSite. All rights reserved.</p>
    </footer>
</body>
</html>
