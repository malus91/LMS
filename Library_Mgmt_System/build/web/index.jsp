<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : Welcome page of the Library Management Poratl
    Created on : Sep 30, 2015, 12:16:10 AM
    Author     : Malini Bhaskaran
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LBMS_Welcome</title>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body style = "background-image: url(lib2.jpg)">
    <center>
        <table border="1" width="1" cellspacing="5" >
        <thead>
            <tr><th><h2><center>Manage Your Library</center></h2></th></tr>             
            </thead>
        </table>
        <h1></h1>
        <table border="1" width="1" cellspacing="5" >
            
            <tbody>
                
                <tr>
                    <td>
                        <form action="Create_New_User.jsp">
                            <center>
                            <input type="submit" value="CREATE NEW USER" name="CREATE NEW USER" />
                            </center>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form action="search.jsp">
                            <center>
                            <input type="submit" value="SEARCH FOR BOOK" name="SEARCH FOR BOOK" />
                            </center>
                        </form>
                        
                    </td>
                </tr>
                <tr>
                    <td>
                        <form action="Checkout.jsp">      
                        <center>
                        <input type="submit" value="CHECK-OUT BOOK" name="CHECKOUT BOOK" />
                        </center>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form action="Checkin.jsp">  
                        <center>
                        <input type="submit" value="CHECK-IN BOOK" name="CHECKIN BOOK" />
                        </center>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form action="Fines.jsp">  
                        <center>
                        <input type="submit" value="FINES" name="FINES" width ="1" />
                        </center>
                        </form>
                    </td>
                </tr>
            </tbody>
        </table>
    </center>
    </body>
</html>
