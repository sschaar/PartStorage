<%@ page import="java.util.*, at.htlklu.entities.Parts" %>
<!DOCTYPE html>
<html>
<head>
    <title>Parts Storage</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h1>HTL Parts Storage</h1>
<form action="search" method="get">
    <input type="text" name="search" placeholder="Enter serial number or part name">
    <button type="submit">Find</button>
</form>

<%
    List<Parts> partsList = (List<Parts>) request.getAttribute("parts");
    if (partsList != null && !partsList.isEmpty()) {
%>
<table border="1">
    <thead>
    <tr>
        <th>Serial Number</th>
        <th>Part Name</th>
        <th>Box</th>
        <th>Count</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Parts part : partsList) {
    %>
    <tr>
        <td><%= part.getSerialnr() %></td>
        <td><%= part.getPartname() %></td>
        <td><%= part.getBox() %></td>
        <td class="<%= part.getCount() == 0 ? "empty" : "" %>">
            <%= part.getCount() %>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<%
    }
%>
</body>
</html>
