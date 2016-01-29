<%-- 
    Document   : Fines
    Created on : Oct 21, 2015, 10:35:32 PM
    Author     : Malini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.sql.*"%>
<%@ page language="java" session="true" import="java.lang.*,java.net.*,java.text.*,javax.servlet.http.*" %>
<%@ page import="java.sql.*,java.util.*,java.text.SimpleDateFormat,java.util.Date" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fine</title>
        <link rel="stylesheet" type="text/css" href="style.css">    

    </head>
    <body style = "background-image: url(lib2.jpg)">         
    <center>
        <%-- <h1>Update Fines information</h1>--%>
        <form name="Update" action="Fines_upd.jsp">
            <table border="0" width="3" cellspacing="2">
                <thead>
                    <tr>
                        <th><h2>Update Fines Information</h2></th>
                <th></th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Update Fine table with todays Data</td>
                        <td><input type="submit" value="Update / View Fines" name="SUBMIT"/></td>
                    </tr>
                </tbody>
            </table>           
        </form>
        <h1></h1>
        <form name="Fines" action="Fines.jsp">
            <table border="0" width="3" cellspacing="2">
                <thead>
                    <tr>
                        <th>Get Fine Details</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Card No</td>
                        <td><input type="text" name="Card_no" value=""/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value="Get Fines" name="SUBMIT" /></td>
                    </tr>
                </tbody>
            </table>        
            <%
                Connection con = null;
                String[] selected_Checkboxes = request.getParameterValues("chk");
                PreparedStatement pst = null;
                ResultSet result = null;
                ResultSet resUpd = null;
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lbms_db?zeroDateTimeBehavior=convertToNull", "root", "admin12");
                String Card_no = request.getParameter("Card_no");
                String button = null;
                Date dt = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String current_date = sdf.format(dt);
                if (Card_no != null && selected_Checkboxes == null) {
                    String selSql = "SELECT  l.card_no, SUM(f.fine_amt) AS total_fine, f.paid  "
                            + "FROM book_loans l, fines f  "
                            + "WHERE l.loan_id = f.loan_id AND "
                            + "f.paid = false AND "
                            + "l.card_no = " + Card_no + " "
                            + "GROUP BY l.card_no";
                    pst = con.prepareStatement(selSql);
                    result = pst.executeQuery();
                    String box = null;
                    String paid;
                    String pay;
                    Boolean chk = false;
                    out.println("<table>");
                    pay = "<form action='Fines.jsp'>";
                    out.println(pay);
                    out.println("<tr>");
                    out.println("<th>Card No</th>");
                    out.println("<th>Fine_Amt in $</th>");
                    out.println("<th>Paid OR Not</th>");
                    out.println("</tr>");
                    while (result.next()) {
                        chk = true;
                        paid = "No";
                        if (result.getBoolean("f.paid")) {
                            paid = "Yes";
                        }

                        out.println("<tr>");
                        out.println("<td>" + result.getInt("l.card_no") + "</td><td>" + result.getString("total_fine") + "</td><td>" + paid + "</td>");
                        out.print("<td>");
                        box = "<input name='chk' value=" + result.getInt("l.card_no") + " type='checkbox'>";
                        out.print(box);
                        out.print("</td>");
                        out.print("</tr>");
                    }

                    if (chk == true) {
                        out.println("<tr>");
                        out.print("<td>");
                        button = "<input type='submit' value='Pay Fine' name='Pay'>";
                        out.print(button);
                        out.print("</td>");
                        out.println("</tr>");
                    } else {
            %><dialog open> <font color = 'green'>No Fine information. You owe nothing! Thank You</font> </dialog><%
                }
                out.println("</form>");
                out.println("</table>");
            } else if (selected_Checkboxes != null) {
                String sqlLoan = null;
                ResultSet resultLoan = null;
                String sqlUpdFine = null;
                PreparedStatement pstUpd = null;
                String sqlBook = null;
                ResultSet rsltBook = null;
                char chkouts = 'N';

                int length_chk = selected_Checkboxes.length;
                for (int i = 0; i < length_chk; i++) {
                    // Check whether the Book is returned before paying the fine.
                    sqlBook = "SELECT COUNT(loan_id) AS no_chkouts FROM book_loans WHERE card_no = " + selected_Checkboxes[i] + " AND (date_in = 0000-00-00 OR date_in IS NULL) AND due_date < current_date";
                    pst = con.prepareStatement(sqlBook);
                    rsltBook = pst.executeQuery();
                    while (rsltBook.next()) {
                        if (rsltBook.getInt("no_chkouts") > 0) {
                            chkouts = 'Y';
                        }
                    }
                    if (chkouts == 'Y') {
                %><dialog open> <font color = 'red'>You have outstanding due checkouts!. Please return the books and then Pay the fine</font> </dialog><%
                                 }
                     //Get the corresponding loan_Ids for each customer from Fines table

                                 sqlLoan = "SELECT loan_id FROM book_loans WHERE card_no = " + selected_Checkboxes[i] + " AND (date_in IS NOT NULL OR date_in != 0000-00-00) AND due_date < date_in";
                                 pst = con.prepareStatement(sqlLoan);
                                 resultLoan = pst.executeQuery();
                                 while (resultLoan.next()) {
                                     sqlUpdFine = "UPDATE fines SET paid = true WHERE loan_id = " + resultLoan.getInt("loan_id") + " AND paid = false";
                                     pstUpd = con.prepareStatement(sqlUpdFine);
                                     pstUpd.executeUpdate();
                                     out.println("<h1></h1>");
                                     out.println("<table>");
                                     out.println("<tr>");
                                     out.println("<td>");
                                     out.println("<b><h3><font color = 'green'>Payment Updated Successfully!</font></h3></b>");
                                     out.println("</td>");
                                     out.println("</tr>");
                                     out.println("</table>");
                                 }
                             }
                         }

                %>
        </form>        
    </center>
</body>
</html>
