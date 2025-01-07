<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>File Records</title>
    <link rel="stylesheet" href="/static/styles.css"> <!-- Optional: For custom styling -->
</head>
<body>
<h1>File Records</h1>

<!-- Display File Records in a Table -->
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Filename</th>
        <th>File Path</th>
        <th>Timestamp Received</th>
        <th>Timestamp Processed</th>
        <th>Processing Time (ms)</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="record" items="${fileRecords}">
        <tr>
            <td>${record.id}</td>
            <td>${record.filename}</td>
            <td>${record.filePath}</td>
            <td>${record.timestampReceived}</td>
            <td>${record.timestampProcessed}</td>
            <td>${record.processingTimeInMillis}</td>
            <td>
                <form action="/files/${record.id}" method="POST" style="display:inline;">
                    <input type="hidden" name="_method" value="DELETE"/>
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Form to Add a New Record -->
<h2>Add a New File Record</h2>
<form action="/files" method="POST">
    <label for="filename">Filename:</label>
    <input type="text" id="filename" name="filename" required><br/>

    <label for="filePath">File Path:</label>
    <input type="text" id="filePath" name="filePath" required><br/>

    <label for="timestampReceived">Timestamp Received:</label>
    <input type="datetime-local" id="timestampReceived" name="timestampReceived" required><br/>

    <label for="timestampProcessed">Timestamp Processed:</label>
    <input type="datetime-local" id="timestampProcessed" name="timestampProcessed" required><br/>

    <label for="processingTimeInMillis">Processing Time (ms):</label>
    <input type="number" id="processingTimeInMillis" name="processingTimeInMillis" required><br/>

    <button type="submit">Add Record</button>
</form>
</body>
</html>
