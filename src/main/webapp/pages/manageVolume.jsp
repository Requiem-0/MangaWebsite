<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Manage Volumes</title>
    <style>
        table { border-collapse: collapse; width: 80%; margin-bottom: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #eee; }
        .message { color: green; }
        .error { color: red; }
        img { max-height: 100px; }
    </style>
</head>
<body>

<h2>Manage Volumes for Manga ID: <%= request.getAttribute("mangaId") != null ? request.getAttribute("mangaId") : "N/A" %></h2>

<%
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");

    if (message != null) {
%>
        <p class="message"><%= message %></p>
<%
    }

    if (error != null) {
%>
        <p class="error"><%= error %></p>
<%
    }

    java.util.List<com.manga.models.Volume> volumeList = (java.util.List<com.manga.models.Volume>) request.getAttribute("volumeList");
%>

<table>
    <thead>
        <tr>
            <th>Volume Number</th>
            <th>ISBN</th>
            <th>Image</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
<%
    if (volumeList != null && !volumeList.isEmpty()) {
        for (com.manga.models.Volume volume : volumeList) {
%>
        <tr>
            <td><%= volume.getVolumeNumber() %></td>
            <td><%= volume.getIsbn() %></td>
            <td>
<%
                if (volume.getVolume_img() != null && !volume.getVolume_img().isEmpty()) {
%>
                    <img src="<%= request.getContextPath() + volume.getVolume_img() %>" alt="Volume Image" />
<%
                } else {
%>
                    No image
<%
                }
%>
            </td>
            <td>
                <form method="get" action="<%= request.getContextPath() %>/ManageVolumeController?action=list&mangaId=2" style="display:inline;">
                    <input type="hidden" name="action" value="deleteVolume" />
                    <input type="hidden" name="volumeId" value="<%= volume.getVolumeId() %>" />
                    <input type="hidden" name="mangaId" value="<%= request.getAttribute("mangaId") %>" />
                    <button type="submit" onclick="return confirm('Delete this volume?');">Delete</button>
                </form>
            </td>
        </tr>
<%
        }
    } else {
%>
        <tr>
            <td colspan="4">No volumes found.</td>
        </tr>
<%
    }
%>
    </tbody>
</table>

<h3>Add New Volume</h3>
<form method="post" action="<%= request.getContextPath() %>/ManageVolumeController?action=list&mangaId=2" enctype="multipart/form-data">
    <input type="hidden" name="mangaId" value="<%= request.getAttribute("mangaId") %>" />
    <label>Volume Number: <input type="number" name="volumeNumber" required /></label><br/><br/>
    <label>ISBN: <input type="text" name="isbn" required /></label><br/><br/>
    <label>Volume Image: <input type="file" name="volumeImageFile" accept="image/*" /></label><br/><br/>
    <button type="submit">Add Volume</button>
</form>
<form method="get" action="<%= request.getContextPath() %>/ManageVolumeController">
    <label for="mangaIdInput">Enter Manga ID to view volumes:</label>
    <input type="number" id="mangaIdInput" name="mangaId" required />
    <input type="hidden" name="action" value="list" />
    <button type="submit">Show Volumes</button>
</form>


</body>
</html>
