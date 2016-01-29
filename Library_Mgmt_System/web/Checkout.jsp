<%-- 
    Document   : Checkout
    Created on : Sep 30, 2015, 12:42:33 AM
    Author     : Malini
--%>
<%@page import="java.sql.*,java.util.*,java.text.SimpleDateFormat,java.util.Date" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout_Books</title>
        <link rel="stylesheet" type="text/css" href="style.css">  
    </head>
    <body style = "background-image: url(lib2.jpg)">

    <center>
        <form name="checkout" action="Checkout.jsp">
            <table border="0" width="5" cellspacing="2">

                <thead>
                    <tr>                        
                        <th><h2>Fill Details to Checkout</h2></th>
                        <th></th>
                        
                    </tr>
                    
                </thead>
                <tbody>
                    <tr>
                        <td>Book ID</td>
                        <td><input type="text" name="ck_book_id" value="" required/></td>
                    </tr>
                    <tr>
                        <td>Branch_ID</td>
                        <td><input type="text" name="ck_branch_id" value="" required/></td>
                    </tr>
                    <tr>
                        <td>Borrower Card Number</td>
                        <td><input type="text" name="ck_card_no" value="" required/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value="CHECK OUT" name="CHECK OUT" /></td>
                    </tr>
                </tbody>
            </table>

            <%
                String chk_bookid = request.getParameter("ck_book_id");
                String chk_borrower = request.getParameter("ck_card_no");
                String chk_branchid = request.getParameter("ck_branch_id");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Calendar c = Calendar.getInstance();
                Date dt = new Date(); // Set Todays date
                int copies = 0;
                int copies_chkd = 0;
                String issue_date = sdf.format(dt);
                c.add(Calendar.DATE, 14); // Adding 14 days to get Due date
                String renual_date = sdf.format(c.getTime());

                if (chk_bookid != null && chk_borrower != null && chk_branchid != null) {
                    Connection con = null;
                    PreparedStatement pstInsertItem = null;
                    PreparedStatement pstSel = null;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lbms_db?zeroDateTimeBehavior=convertToNull", "root", "admin12");
                        //Select Query to get the number of existing Checkouts  
                        String sqlSel = "SELECT COUNT(card_no) AS no_of_chkouts FROM book_loans WHERE card_no = " + chk_borrower + " AND (date_in IS NULL OR date_in = 0000-00-00) ";
                        pstSel = con.prepareStatement(sqlSel);
                        ResultSet rslt = null;
                        rslt = pstSel.executeQuery();
                        //Select Query to get the book_copies available in Each branch for a specific book_id
                        String selCopies = "SELECT no_of_copies FROM book_copies WHERE book_id = " + chk_bookid + " AND branch_id = " + chk_branchid + "";
                        ResultSet rsltCopies = null;
                        pstSel = con.prepareStatement(selCopies);
                        rsltCopies = pstSel.executeQuery();
                        while (rsltCopies.next()) 
                        {
                            copies = rsltCopies.getInt("no_of_copies");
                        }
                        //Gets checked out copies for a particular book at particulat branch
                        String selchkCopies = "SELECT COUNT(book_id) AS no_of_books_chkd FROM book_loans WHERE book_id = " + chk_bookid + " AND branch_id = " + chk_branchid + " AND (date_in IS null OR date_in = 0000-00-00) ";
                        ResultSet rtChkdCopies = null;
                        pstSel = con.prepareStatement(selchkCopies);
                        rtChkdCopies = pstSel.executeQuery();
                        while (rtChkdCopies.next()) 
                        {
                            copies_chkd = rtChkdCopies.getInt("no_of_books_chkd");
                        }
                         out.println("<h3></h3>");
                         out.println("<table>");
                         out.println("<tbody>");
                         out.println("<tr>");
                         out.println("<td>");
                        //Check if book Copies are available for Checkout at Branches
                        if (copies <= copies_chkd) {
                            out.println("<h3>Sorry!, There are no more available copies of Book " + chk_bookid + " at Branch " + chk_branchid + "</h3>");
                        } else {

                            while (rslt.next()) {
                                if (rslt.getInt("no_of_chkouts") >= 3) {
                                    out.println("<h3>Sorry!Maximum Checkout limit reached. You have already checked out 3 books</h3>");
                                } else {
                                    if (chk_bookid != null && chk_branchid != null && chk_borrower != null) {
                                        String sqlInsertItem = "INSERT INTO book_loans (book_id,branch_id,card_no,date_out,due_date) VALUES (?,?,?,?,?)";
                                        pstInsertItem = con.prepareStatement(sqlInsertItem);
                                        pstInsertItem.setString(1, chk_bookid);
                                        pstInsertItem.setString(2, chk_branchid);
                                        pstInsertItem.setString(3, chk_borrower);
                                        pstInsertItem.setString(4, issue_date);
                                        pstInsertItem.setString(5, renual_date);
                                        pstInsertItem.executeUpdate();                                        
                                        out.println("<h3>Successfully Checked out. Please return the book on " + renual_date + "</h3>");
                                    }
                                }
                            }
                        }
                         out.println("</td>");
                         out.println("</tr>");
                         out.println("</tbody>");
                         out.println("</table>");              
                         
                        
                    } catch (ClassNotFoundException e) {
                        out.println("<b>Driver Class not found Exception: </b>" + e.getMessage());

                    } catch (SQLException e) {
                        out.println("<b>SQL EXception:</b>" + e.getMessage());
                    } finally {
                        try {
                            if (con != null) {
                                con.close();
                            }
                        } catch (SQLException e) {
                            out.println("<br>Connection Closing Exception: </b>" + e.getMessage());

                        }
                    }

                }
            %>
        </form>
    </center>
    </body>
</html>
