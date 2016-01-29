<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : Search_res
    Created on : Oct 8, 2015, 12:36:20 PM
    Author     : Malini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<jsp:useBean id="sample" scope="page" class="Com.Lbms.BookMgmt" />
<sql:query var="Book" dataSource="jdbc/LBMS">
    SELECT book_id, title  FROM book WHERE
    book_id = ? <sql:param value = "${param.book_id}"/>
</sql:query>
    
<table border="1">
    <!-- column headers -->
    <tr>
        <c:forEach var="columnName" items="${Book.columnNames}">
            <th><c:out value="${columnName}"/></th>
            </c:forEach>
    </tr>
    <!-- column data -->
    <c:forEach var="row" items="${Book.rowsByIndex}">
        <tr>
            <c:forEach var="column" items="${row}">
                <td><c:out value="${column}"/></td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
    
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css">  
    </head>
    <body>
        <h1>Available Books</h1>        
<table>
<tr>
<th>Author</th>
<th>Title</th>
<th>Year</th>
<th>Publisher</th>
<th>Image URL</th>
</tr> 

</table>
        
    </body>
</html>
