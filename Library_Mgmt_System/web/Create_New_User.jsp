<%-- 
    Document   : Create_New_User
    Created on : Sep 30, 2015, 12:40:58 AM
    Author     : Malini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.sql.*"%>
<%@ page language="java" session="true" import="java.lang.*,java.net.*,java.text.*,javax.servlet.http.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New User</title>
        <link rel="stylesheet" type="text/css" href="style.css">  
    </head>
    <body style = "background-image: url(lib2.jpg)">
        <%-- <center><h1>Welcome on Board</h1></center>--%>

    <center>
        <form action = "Create_New_User.jsp">
            <table><thead<tr>
                        <th><h2><center>Welcome on Board!</center></h2></th>
                <th></th>
                </tr> </thead></table>
            <table border="0" width="3" cellspacing="2">

                <tbody>
                    <tr>
                        <td>First Name</td>
                        <td><input type="text" name="Fname" value="" required/></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input type="text" name="Lname" value="" required/></td>
                    </tr>
                    <tr>
                        <td>Address</td>
                        <td><textarea name="Address" rows="7" cols="20" required>
                            </textarea></td>
                    </tr>
                    <tr>
                        <td>City</td>
                        <td><input type="text" name="City" value="" required/></td>
                    </tr>
                    <tr>
                        <td>State</td>
                        <td><input type="text" name="State" value="" required/></td>
                    </tr>
                    <tr>
                        <td>Email</td>
                        <td><input type="text" name="Email" value="" required/></td>
                    </tr>                    
                    <tr>
                        <td>Phone</td>
                        <td><input type="text" name="Phone" value="" /></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value="SUBMIT" name="SUBMIT" /></td>
                    </tr>
                </tbody>
            </table>    

    </center>
    <%
        String Fname = request.getParameter("Fname");
        String Lname = request.getParameter("Lname");
        String Address = request.getParameter("Address");
        String Phone = request.getParameter("Phone");
        String Email = request.getParameter("Email");
        String City = request.getParameter("City");
        String State = request.getParameter("State");
        if (Fname != null && Lname != null && Address != null && Phone != null) {
            Connection con = null;
            PreparedStatement pst = null;
            ResultSet result = null;
            int nextCard = 0;
            int highCard = 0;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lbms_db?zeroDateTimeBehavior=convertToNull", "root", "admin12");
            //Generating next Card number by getting the highest card number available
            String selMaxCard = "SELECT MAX(Card_no) as High_card FROM borrower";
            pst = con.prepareStatement(selMaxCard);
            result = pst.executeQuery();
            while (result.next()) {
                highCard = result.getInt("High_card");
                nextCard = highCard + 1;
            }

            ResultSet resCheck = null;
            //Checking if any user exist with the same Fname, Lname and address
            String chkUser = "SELECT count(*) AS no_of_user FROM borrower WHERE Fname = ? AND Lname = ? AND address = ?";
            pst = con.prepareStatement(chkUser);
            pst.setString(1, Fname);
            pst.setString(2, Lname);
            pst.setString(3, Address);
            resCheck = pst.executeQuery();
            int existing_user = 0;
            while (resCheck.next()) {
                existing_user = resCheck.getInt("no_of_user");
            }

            if (existing_user <= 0) {
                String userInsert = "INSERT INTO borrower (card_no, Fname, Lname,email, address,city,state,phone) values(?,?,?,?,?,?,?,?)";
                pst = con.prepareStatement(userInsert);

                pst.setInt(1, nextCard);
                pst.setString(2, Fname);
                pst.setString(3, Lname);
                pst.setString(4, Email);
                pst.setString(5, Address);
                pst.setString(6, City);
                pst.setString(7, State);
                pst.setString(8, Phone);
                pst.executeUpdate();

                out.println("<h2></h2>");
                out.println("<center>");
                out.println("<table>");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>");
                out.println("Successfully Created User. Your Card no is " + nextCard + "");
                out.println("</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("</center>");
                out.println("</table>");

            } else {

    %><dialog open> <font color = 'red'>There is already a user with the same First Name, Last Name and Address. New account can not be created</font> </dialog><%                        }
        }
        %>
</form>
</body>
</html>
