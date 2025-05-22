<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.manga.models.Chapter, com.manga.models.Volume" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Manage Chapters</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
  <link href="<%= request.getContextPath() %>/css/admin_manage_chapter.css" rel="stylesheet" />
</head>
<body>
<div class="container-fluid">
  <div class="row">
    <!-- Sidebar -->
    <nav class="col-md-3 col-lg-2 mngchapter-sidebar d-md-block">
      <h2>Admin Panel</h2>
      <a href="<%= request.getContextPath() %>/DashboardController">Dashboard</a>
      <a href="<%= request.getContextPath() %>/ManageMangaController?action=list">Manage Manga</a>
      <a href="<%= request.getContextPath() %>/ManageVolumeController?action=list">Manage Volume</a>
      <a href="<%= request.getContextPath() %>/ManageChapterController?action=list" class="active">Manage Chapter</a>
      <a href="<%= request.getContextPath() %>/ManageUsersServlet">Manage Users</a>
      <a href="#">Logout</a>
    </nav>

    <!-- Main Content -->
    <main class="col-md-9 col-lg-10 mngchapter-main-content">
      <div class="mngchapter-header">
        <h1 class="h4">Manage Chapters</h1>
        <h5>Volume ID: <%= request.getAttribute("volumeId") != null ? request.getAttribute("volumeId") : "N/A" %></h5>
      </div>

      <div class="mngchapter-content-box">
        <% 
          String message = (String) request.getAttribute("message");
          String error = (String) request.getAttribute("error");
          if (message != null) {
        %>
          <div class="alert alert-success"><%= message %></div>
        <% } 
           if (error != null) {
        %>
          <div class="alert alert-danger"><%= error %></div>
        <% } %>

        <form method="get" action="<%= request.getContextPath() %>/ManageChapterController" class="mb-4">
          <div class="input-group">
            <input type="hidden" name="action" value="list" />
            <input type="number" name="volumeId" class="form-control" placeholder="Enter Volume ID to view chapters" required />
            <button class="btn btn-secondary">Show Chapters</button>
          </div>
        </form>

        <%
          List<Chapter> chapterList = (List<Chapter>) request.getAttribute("chapterList");
          int volumeId = (request.getAttribute("volumeId") != null) ? (int) request.getAttribute("volumeId") : 0;
        %>

        <table class="table table-striped table-bordered table-hover">
          <thead class="table-dark">
            <tr>
              <th>Chapter No</th>
              <th>Title</th>
              <th>PDF</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
          <%
            if (chapterList == null || chapterList.isEmpty()) {
          %>
            <tr><td colspan="4" class="text-center">No chapters found.</td></tr>
          <%
            } else {
              for (Chapter chapter : chapterList) {
          %>
            <tr>
              <td><%= chapter.getChapterno() %></td>
              <td><%= chapter.getChaptertitle() %></td>
              <td>
                <% if (chapter.getChapter_pdf() != null) { %>
                  <a href="<%= request.getContextPath() + "/" + chapter.getChapter_pdf() %>" target="_blank">View PDF</a>
                <% } else { %>
                  No File
                <% } %>
              </td>
              <td>
                <form method="get" action="<%= request.getContextPath() %>/ManageChapterController" style="display:inline;">
                  <input type="hidden" name="action" value="deleteChapter" />
                  <input type="hidden" name="chapterId" value="<%= chapter.getChapterId() %>" />
                  <input type="hidden" name="volumeId" value="<%= volumeId %>" />
                  <button class="btn btn-sm btn-danger" onclick="return confirm('Delete this chapter?');">Delete</button>
                </form>
              </td>
            </tr>
          <%
              }
            }
          %>
          </tbody>
        </table>

        <h3 class="mt-4">Add New Chapter</h3>
        <form method="post" action="<%= request.getContextPath() %>/ManageChapterController" enctype="multipart/form-data" class="mb-5">
          <div class="row g-3 align-items-center">

            <div class="col-md-3">
              <label class="form-label">Volume</label>
              <select name="volumeId" class="form-select" required>
                <option value="">Select Volume</option>
                <%
                  List<Volume> volumeList = (List<Volume>) request.getAttribute("volumeList");
                  if (volumeList != null) {
                    for (Volume v : volumeList) {
                %>
                  <option value="<%= v.getVolumeId() %>" 
                    <%= (v.getVolumeId() == volumeId) ? "selected" : "" %>>
                    Volume <%= v.getVolumeNumber() %> (ID: <%= v.getVolumeId() %>)
                  </option>
                <%
                    }
                  }
                %>
              </select>
            </div>

            <div class="col-md-2">
              <label class="form-label">Chapter No</label>
              <input type="number" name="chapterno" class="form-control" required />
            </div>

            <div class="col-md-4">
              <label class="form-label">Chapter Title</label>
              <input type="text" name="chaptertitle" class="form-control" required />
            </div>

            <div class="col-md-3">
              <label class="form-label">Upload PDF</label>
              <input type="file" name="chapterPDF" accept="application/pdf" class="form-control" required />
            </div>
          </div>

          <div class="mt-3">
            <button type="submit" class="btn btn-primary">Add Chapter</button>
          </div>
        </form>
      </div>
    </main>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
