<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : search
    Created on : Sep 30, 2015, 12:40:17 AM
    Author     : Malini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.sql.*"%>
<%@ page language="java" session="true" import="java.lang.*,java.net.*,java.text.*,javax.servlet.http.*" %>

<c:set var="book_id" value="${book_id}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search_Book</title>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body style = "background-image: url(lib2.jpg)">


    <center>
        <table
            <tr>
                <th><h2>Find Your Books</h2></th>
            </tr>
        </table>
        <form action="search.jsp">      
            <table border="0" width="6" cellspacing="1">

                <tbody>
                    <tr>
                        <td>Book Id</td>
                        <td><input id = "book_id" type="text" name="book_id"/></td>
                        <td>Title</td>  
                        <td><input type="text" name="title"/></td></td>              
                    </tr>
                    <tr>
                        <td>Author</td> 
                        <td><input type="text" name="Txt_auth"/></td></td>    
                        <td><input type="radio" id = "r1" name="Rg1" value="Exactly" checked="checked" />
                            Exactly
                        </td>                          
                        <td><input type="radio" id = "r1" name="Rg1" value="Contains" /> Contains</td>                                             
                    </tr> 
                    <tr>
                        <td></td>
                        <td></td>
                        <td>
                            <input type="submit" value="SUBMIT" name="SUBMIT" />
                        </td>
                    </tr>
                </tbody>
            </table> 
        </form>

        <%
            String bookid = request.getParameter("book_id");
            String title = request.getParameter("title");
            String author = request.getParameter("Txt_auth");
            String radio = request.getParameter("Rg1");

            String selAuthCont = null;
            String selBookid = null;
            String selAuthExact = null;
            String selTitle = null;
            String selGroup = null;

            int id = 0;
            String box = null;
            String button = null;
            String checkout = null;
            Boolean check = false;

            if (bookid == "" && title == "" && author == "") {
                bookid = null;
                title = null;
                author = null;
            }
            if (bookid != null || title != null || author != null) {
                Connection con = null;
                PreparedStatement pst = null;
                ResultSet result = null;
                PreparedStatement pst_auth = null;
                ResultSet rslt_auth = null;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lbms_db?zeroDateTimeBehavior=convertToNull", "root", "admin12");
                    /* 
                     pst = con.prepareStatement("SELECT b.book_id, b.title, a.author_name, c.branch_id, c.no_of_copies "
                     + "FROM book b, book_authors a, book_copies c "
                     + "WHERE b.book_id = a.book_id AND "                
                     + "b.book_id = c.book_id AND "
                     + " ( b.book_id = "+bookid+") ");
                     */
                    /*
                     String selSql = "SELECT b.book_id, b.title, c.branch_id, c.no_of_copies, (no_of_copies-count(loan_id)) as Availability"
                     + " FROM book b, book_authors a, (book_copies c LEFT JOIN book_loans l  ON c.book_id = l.book_id AND c.branch_id = l.branch_id) "
                     + " WHERE b.book_id = a.book_id AND "
                     + " b.book_id = c.book_id  AND l.date_in IS NULL";
                     */
                    String selSql = "SELECT b.book_id, b.title, c.branch_id, c.no_of_copies "
                            + " FROM book b, book_authors a, book_copies c "
                            + " WHERE b.book_id = a.book_id AND "
                            + " b.book_id = c.book_id";

                    //Populating WHERE conditions dynamically checking the USER inputs
                    if (radio.equals(new String("Contains")) && author != "") {
                        selAuthCont = " AND a.author_name LIKE '%" + author + "%'";
                        selSql = selSql.concat(selAuthCont);
                    }
                    if (radio.equals(new String("Exactly")) && author != "") {
                        selAuthExact = " AND a.author_name = '" + author + "'";
                        selSql = selSql.concat(selAuthExact);
                    }
                    if (bookid != "") {
                        selBookid = " AND b.book_id = " + bookid + "";
                        selSql = selSql.concat(selBookid);
                    }
                    if (title != "") {
                        selTitle = " AND b.title LIKE '%" + title + "%'";
                        selSql = selSql.concat(selTitle);
                    }

                    //To add the group by clause to get the correct avaialability
                    selGroup = " GROUP BY c.branch_id, c.book_id";
                    selSql = selSql.concat(selGroup);

                    pst = con.prepareStatement(selSql);

                    result = pst.executeQuery();

                    out.println("<table>");
                    checkout = "<form action='Checkout.jsp'>";
                    out.println(checkout);
                    out.println("<tr>");
                    out.println("<th>Book_id</th>");
                    out.println("<th>Title</th>");
                    out.println("<th>Author</th>");
                    out.println("<th>Branch_id</th>");
                    out.println("<th>No of Copies</th>");
                    out.println("<th>Availability</th>");
                    out.println("</tr>");
                    String selAuth = null;
                    String bookAuth = null;
                    String selAvailability = null;
                    ResultSet rslt_availble = null;
                    int available = 0;
                    int available_copies = 0;
                    int chkdouts = 0;
                    while (result.next()) {
                        // To concatenate author names if multiple authors exist
                        selAuth = "SELECT author_name FROM book_authors WHERE book_id = ?";
                        pst_auth = con.prepareStatement(selAuth);
                        pst_auth.setString(1, result.getString("b.book_id"));
                        rslt_auth = pst_auth.executeQuery();
                        bookAuth = "";
                        int i = 0;
                        while (rslt_auth.next()) {
                            // To give comma separation
                            i = i + 1;
                            if (i != 1) {
                                bookAuth = bookAuth.concat(new String(","));
                            }
                            bookAuth = bookAuth.concat(rslt_auth.getString("author_name"));
                        }
                        selAvailability = "SELECT count(*) AS available FROM book_loans WHERE book_id = '" + result.getString("b.book_id") + "' AND branch_id = " + result.getInt("c.branch_id") + " AND date_in IS NULL";
                        pst_auth = con.prepareStatement(selAvailability);
                        rslt_availble = pst_auth.executeQuery();
                        available = 0;
                        available_copies = result.getInt("c.no_of_copies");
                        while (rslt_availble.next()) {
                            chkdouts = rslt_availble.getInt("available");
                            available = available_copies - chkdouts;

                        }
                        check = true;
                        out.println("<tr>");
                        out.println("<td>" + result.getString("b.book_id") + "</td><td>" + result.getString("b.title") + "</td><td>" + bookAuth + "</td><td>" + result.getString("c.branch_id") + "</td><td>" + result.getString("c.no_of_copies") + "</td><td>" + available + "</td>");
                        // out.print("<td>");
                        // box = "<input name='chk' value=r" + result.getString("book_id") + " type='checkbox'>";
                        // out.print(box);                        
                        // out.print("</td>");
                        out.print("</tr>");

                    }
                    if (check == true) {

                        out.println("<tr>");
                        out.print("<td>");
                        button = "<input type='submit' value='Checkout' name='checkout'>";
                        out.print(button);
                        out.print("</td>");
                        out.println("</tr>");

                    } else {
                        out.println("<tr>");
                        out.print("<td>");
        %><dialog open> <font color = 'red'>No Books Found for your search. Sorry!</font> </dialog><%
                        out.print("</td>");
                        out.println("</tr>");
                    }
                    out.println("</form>");
                    out.println("</table>");

                } catch (ClassNotFoundException e) {
                    out.println("<b>Driver Class not found Exception: </b>" + e.getMessage());

                }
            }
            %> 
    </center>
</body>
</html>
