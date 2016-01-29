<%-- 
    Document   : Fines_upd
    Created on : Oct 22, 2015, 10:00:58 AM
    Author     : Malini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*,java.util.*,java.text.SimpleDateFormat,java.util.Date" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fine Update</title>
        <link rel="stylesheet" type="text/css" href="style.css">    

    </head>
    <body style = "background-image: url(lib2.jpg)">
    <center>
        <table>
            <tr>
                <th></th>
                <th><h2>Updated Fines Details</h2></th>
            <th></th>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td>
                    <form name="Fines" action="Fines.jsp">
                        <input type="submit" value="Go Back to Fines" name="Fines" />                 
                    </form>
                </td>
            </tr>
        </table>  
        <h1></h1>

        <%
            try {

                Connection con = null;
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lbms_db?zeroDateTimeBehavior=convertToNull", "root", "admin12");
                Date dt = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String current_date = sdf.format(dt);
                String selUpd = "SELECT * FROM book_loans WHERE (due_date < current_date AND (date_in = '0000-00-00' OR date_in IS NULL)) OR "
                        + " date_in > due_date ";

                PreparedStatement pstSel = null;
                pstSel = con.prepareStatement(selUpd);
                ResultSet rslt = null;
                ResultSet loanChk = null;
                rslt = pstSel.executeQuery();
                String sqlIns = null;
                String sqlUpd = null;
                String sqlChk = null;
                double fine_amnt = 0.0;
                int due_days;
                long diff;
                char loan_exist = 'N';

                while (rslt.next()) {
                    sqlChk = "SELECT COUNT(*) AS loans FROM fines WHERE loan_id = " + rslt.getInt("loan_id") + "";
                    pstSel = con.prepareStatement(sqlChk);
                    loanChk = pstSel.executeQuery();
                    while (loanChk.next()) {
                        loan_exist = 'N';
                        if (loanChk.getInt("loans") > 0) {
                            loan_exist = 'X';
                        }
                    }
                    Date date_in = rslt.getDate("date_in");
                    char datePresent = 'Y';
                    if (date_in == null) {
                        datePresent = 'N';
                    }
                    Date dueDate = rslt.getDate("due_date");

                    // If date_in field is blank then calculate fine for difference between current date and due date
                    if (datePresent == 'N') {
                        diff = dt.getTime() - dueDate.getTime();
                    } else {
                        diff = date_in.getTime() - dueDate.getTime();
                    }
                    due_days = (int) (diff / (1000 * 60 * 60 * 24));
                    fine_amnt = due_days * .25;
                    //Check whether fine details are already present in table for the particular loan id .
                    if (loan_exist == 'N') {
                        sqlIns = "INSERT INTO fines VALUES(?,?,?)";
                        pstSel = con.prepareStatement(sqlIns);
                        pstSel.setInt(1, rslt.getInt("loan_id"));
                        pstSel.setDouble(2, fine_amnt);
                        pstSel.setBoolean(3, false);
                        pstSel.executeUpdate();

                    } //If Row already exist 
                    else if (loan_exist == 'X') {
                        sqlUpd = "UPDATE fines SET fine_amt = " + fine_amnt + " WHERE loan_id = " + rslt.getInt("loan_id") + " AND paid = false";
                        pstSel = con.prepareStatement(sqlUpd);
                        pstSel.executeUpdate();

                    }

                }

                String sqlSelFine = null;
                ResultSet selFine = null;
                String paid;
                sqlSelFine = "SELECT l.loan_id,l.card_no,l.due_date,l.date_in,f.fine_amt, f.paid FROM book_loans l JOIN fines f on l.loan_id = f.loan_id ;";
                pstSel = con.prepareStatement(sqlSelFine);
                selFine = pstSel.executeQuery();

                out.println("<table border='1' width='1' cellspacing='5'>");
                out.println("<tr>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("</tr>");
                out.println("<th>Loan Id</th>");
                out.println("<th>Card No</th>");
                out.println("<th>Due_date</th>");
                out.println("<th>Date_in</th>");
                out.println("<th>Fine_Amt in $</th>");
                out.println("<th>Paid OR Not</th>");
                out.println("</tr>");
                while (selFine.next()) {
                    paid = "No";
                    if (selFine.getBoolean("paid")) {
                        paid = "Yes";
                    }

                    out.println("<tr>");
                    out.println("<td>" + selFine.getInt("l.loan_id") + "</td><td>" + selFine.getInt("l.card_no") + "</td><td>" + selFine.getDate("l.due_date") + "</td><td>" + selFine.getDate("l.date_in") + "</td><td>" + selFine.getString("f.fine_amt") + "</td><td>" + paid + "</td>");
                    out.print("</tr>");
                }
                out.println("</form>");

            } catch (NullPointerException e) {
                out.println("<b>Null Pointer Exception: </b>" + e.getMessage());

            }

        %>      
    </center>
</body>
</html>
