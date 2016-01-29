<%-- 
    Document   : Checkin
    Created on : Sep 30, 2015, 12:41:57 AM
    Author     : Malini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.sql.*"%>
<%@page import="java.sql.*,java.util.*,java.text.SimpleDateFormat,java.util.Date" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check-in_Book</title>
        <link rel="stylesheet" type="text/css" href="style.css">  
    </head>
    <body style = "background-image: url(lib2.jpg)">
        <table border="0" width="5" cellspacing="1">
            <thead><tr><th><h2>Check in your Books Here</h2></th></tr>
    <th></th>
    <th></th>
    <th></th>
</thead>

</table>

<form action="Checkin.jsp">
    <table border="0" width="5" cellspacing="1">
        <tbody>

        <td>Book ID</td>
        <td><input type="text" name="Book_id" value="" /></td>
        <td>Card No</td>
        <td><input type="text" name="Card_no" value="" /></td>
        <td>Borrower First Name</td>
        <td><input type="text" name="Borrower FName" value="" /></td>
        <td>Borrower Last Name</td>
        <td><input type="text" name="Borrower LName" value="" /></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td><input type="submit" value="Search Borrowals" /></td>
        </tr>                
        </tbody>
    </table>

    <%
        String bookid = request.getParameter("Book_id");
        String Card_no = request.getParameter("Card_no");
        String Fname = request.getParameter("Borrower FName");
        String Lname = request.getParameter("Borrower LName");
        String[] chkLoanID = request.getParameterValues("chk");
        Connection con = null;
        PreparedStatement pstUpdate = null;
        String selBook = null;
        String selCard = null;
        String selFname = null;
        String selLname = null;
        String box = null;
        String button = null;
        String checkout = null;
        Boolean check = false;
        if (bookid == "" && Card_no == "" && Fname == "" && Lname == "") {
            bookid = null;
            Fname = null;
            Lname = null;
            Card_no = null;
        }
        if (bookid != null || Card_no != null || Fname != null || Lname != null || chkLoanID != null) {
            PreparedStatement pst = null;
            ResultSet result = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lbms_db?zeroDateTimeBehavior=convertToNull", "root", "admin12");
                if ((bookid != null || Card_no != null || Fname != null || Lname != null) && chkLoanID == null) {
                    //Selecting all the books which are out now  by checking date_in field is empty
                    String selSql = "SELECT b.book_id, b.title,l.loan_id, l.branch_id, l.card_no, bo.Fname, l.due_date, l.date_out "
                            + "FROM book b, book_loans l, borrower bo "
                            + "WHERE b.book_id = l.book_id AND "
                            + "l.card_no = bo.card_no AND "
                            + " (l.date_in = 0000-00-00  OR l.date_in IS NULL) ";

                    if (bookid != "") {
                        selBook = " AND b.book_id = '" + bookid + "'";
                        selSql = selSql.concat(selBook);
                    }
                    if (Card_no != "") {
                        selCard = " AND l.card_no = " + Card_no + "";
                        selSql = selSql.concat(selCard);
                    }

                    if (Fname != "") {
                        selFname = " AND bo.Fname = '" + Fname + "'";
                        selSql = selSql.concat(selFname);
                    }
                    if (Lname != "") {
                        selLname = " AND bo.Lname = '" + Lname + "'";
                        selSql = selSql.concat(selLname);
                    }
                    pst = con.prepareStatement(selSql);
                    result = pst.executeQuery();

                    out.println("<h1></h1>");
                    out.println("<table>");
                    out.println("<tr>");
                    out.println("<th>");
                    out.println("<h3>Select Books for Check In</h3>");
                    out.println("</th>");
                    out.println("</tr>");
                    out.println("<th></th>");
                    out.println("</table>");
                    out.println("<table width='15' cellspacing='2'>");
                    checkout = "<form action='Checkin.jsp'>";
                    out.println(checkout);
                    out.println("<tr>");
                    out.println("<th>Loan ID</th>");
                    out.println("<th>Book ID</th>");
                    out.println("<th>Title</th>");
                    out.println("<th>Author</th>");
                    out.println("<th>Branch_id</th>");
                    out.println("<th>Card_no</th>");
                    out.println("<th>Borrower Fname</th>");
                    out.println("<th>Due date</th>");
                    out.println("<th>Date out</th>");
                    out.println("<th>Select</th>");
                    out.println("</tr>");
                    String selAuth = null;
                    String bookAuth = null;
                    PreparedStatement pst_auth = null;
                    ResultSet rslt_auth = null;

                    while (result.next()) {
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
                        check = true;
                        out.println("<tr>");
                        out.println("<td>" + result.getString("loan_id") + "</td><td>" + result.getString("book_id") + "</td><td>" + result.getString("title") + "</td><td>" + bookAuth + "</td><td>" + result.getString("branch_id") + "</td><td>" + result.getString("card_no") + "</td><td>" + result.getString("Fname") + "</td><td>" + result.getString("due_date") + "</td><td>" + result.getString("date_out") + "</td>");
                        out.print("<td>");
                        box = "<input name='chk' value = " + result.getString("loan_id") + " type='checkbox'>";
                        out.print(box);
                        out.print("</td>");
                        out.print("</tr>");

                    }
                    if (check == true) {

                        out.println("<tr>");
                        out.print("<td>");
                        button = "<input type='submit' value='Check In' name='checkin'>";
                        out.print(button);
                        out.print("</td>");
                        out.println("</tr>");
                    }
                    out.println("</form>");
                    out.println("</table>");
                    if (check != true) {
                        out.println("<h1></h1>");
                        out.println("<table>");
                        out.println("<tr>");
                        out.println("<td>");
                        out.println("<b><h3><font color = 'red'>No outstanding checkouts found!!!</font></h3></b>");
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</table>");
                    }
                }
                //Check IN updating the Date_in field in the Book_loans table.
                if (chkLoanID != null && chkLoanID.length > 0) {
                    for (int i = 0; i < chkLoanID.length; i++) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        Date dt = new Date();  // Set Todays date
                        String retDate = sdf.format(dt);
                        String updateLoan = "UPDATE book_loans SET date_in = ? WHERE loan_id = " + chkLoanID[i] + "";
                        pstUpdate = con.prepareStatement(updateLoan);
                        pstUpdate.setString(1, retDate);
                        pstUpdate.executeUpdate();
                        out.println("<h3></h3>");
                        out.println("<table>");
                        out.println("<tbody>");
                        out.println("<tr>");
                        out.println("<td>");
                        out.println("<h3>Successfully Checked IN. Thanks For visiting our Library</h3>");
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</tbody>");
                        out.println("</table>");
                    }
                }

            } catch (ClassNotFoundException e) {
                out.println("<b>Driver Class not found Exception: </b>" + e.getMessage());

            }
        }
    %>         
</form>
</body>
</html>
