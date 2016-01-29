package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.sql.*;
import java.lang.*;
import java.net.*;
import java.text.*;
import javax.servlet.http.*;

public final class Create_005fNew_005fUser_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>New User</title>\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">  \n");
      out.write("    </head>\n");
      out.write("    <body style = \"background-image: url(lib2.jpg)\">\n");
      out.write("   ");
      out.write("\n");
      out.write("\n");
      out.write("    <center>\n");
      out.write("        <form action = \"Create_New_User.jsp\">\n");
      out.write("            <table><thead<tr>\n");
      out.write("                        <th><h2><center>Welcome on Board!</center></h2></th>\n");
      out.write("                        <th></th>\n");
      out.write("                        </tr> </thead></table>\n");
      out.write("            <table border=\"0\" width=\"3\" cellspacing=\"2\">\n");
      out.write("                \n");
      out.write("                <tbody>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>First Name</td>\n");
      out.write("                        <td><input type=\"text\" name=\"Fname\" value=\"\" required/></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>Last Name</td>\n");
      out.write("                        <td><input type=\"text\" name=\"Lname\" value=\"\" required/></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>Address</td>\n");
      out.write("                        <td><textarea name=\"Address\" rows=\"7\" cols=\"20\" required>\n");
      out.write("                            </textarea></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>City</td>\n");
      out.write("                        <td><input type=\"text\" name=\"City\" value=\"\" required/></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>State</td>\n");
      out.write("                        <td><input type=\"text\" name=\"State\" value=\"\" required/></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>Email</td>\n");
      out.write("                        <td><input type=\"text\" name=\"Email\" value=\"\" required/></td>\n");
      out.write("                    </tr>                    \n");
      out.write("                    <tr>\n");
      out.write("                        <td>Phone</td>\n");
      out.write("                        <td><input type=\"text\" name=\"Phone\" value=\"\" required/></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td></td>\n");
      out.write("                        <td><input type=\"submit\" value=\"SUBMIT\" name=\"SUBMIT\" /></td>\n");
      out.write("                    </tr>\n");
      out.write("                </tbody>\n");
      out.write("            </table>    \n");
      out.write("\n");
      out.write("    </center>\n");
      out.write("    ");

        String Fname = request.getParameter("Fname");
        String Lname = request.getParameter("Lname");
        String Address = request.getParameter("Address");
        String Phone = request.getParameter("Phone");
        String Email = request.getParameter("Email");
        String City  = request.getParameter("City");
        String State = request.getParameter("State");
        if (Fname != null && Lname != null && Address != null && Phone != null) {
            Connection con = null;
            PreparedStatement pst = null;
            ResultSet result = null;
            int nextCard = 0;
            int highCard = 0;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lbms_db?zeroDateTimeBehavior=convertToNull", "root", "admin12");
            String selMaxCard = "SELECT MAX(Card_no) as High_card FROM borrower";
            pst = con.prepareStatement(selMaxCard);
            result = pst.executeQuery();
            while (result.next()) {
                highCard = result.getInt("High_card");
                nextCard = highCard + 1;
            }

            ResultSet resCheck = null;
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

            if (existing_user < 0) {
                String userInsert = "INSERT INTO borrower (card_no, Fname, Lname,email, address,city,state,phone) values(?,?,?,?,?)";
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
                out.println("Successfully Created User. Your Card no is " + nextCard + "");
                
            
            } 
            else { 
                         
                
      out.write("<dialog open> <font color = 'red'>There is already a user with the same First Name, Last Name and Address. New account can not be created</font> </dialog>");

            }
        }                                 
    
      out.write("\n");
      out.write("</form>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
