package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.sql.*;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Checkin_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("        <title>Check-in_Book</title>\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">  \n");
      out.write("    </head>\n");
      out.write("    <body style = \"background-image: url(lib2.jpg)\">\n");
      out.write("        <table border=\"0\" width=\"5\" cellspacing=\"1\">\n");
      out.write("            <thead><tr><th><h2>Check in your Books Here</h2></th></tr>\n");
      out.write("<th></th>\n");
      out.write("<th></th>\n");
      out.write("<th></th>\n");
      out.write("            </thead>\n");
      out.write("\n");
      out.write("        </table>\n");
      out.write("        \n");
      out.write("          <form action=\"Checkin.jsp\">\n");
      out.write("            <table border=\"0\" width=\"5\" cellspacing=\"1\">\n");
      out.write("                <tbody>\n");
      out.write("                    \n");
      out.write("                        <td>Book ID</td>\n");
      out.write("                        <td><input type=\"text\" name=\"Book_id\" value=\"\" /></td>\n");
      out.write("                        <td>Card No</td>\n");
      out.write("                        <td><input type=\"text\" name=\"Card_no\" value=\"\" /></td>\n");
      out.write("                        <td>Borrower First Name</td>\n");
      out.write("                        <td><input type=\"text\" name=\"Borrower FName\" value=\"\" /></td>\n");
      out.write("                        <td>Borrower Last Name</td>\n");
      out.write("                        <td><input type=\"text\" name=\"Borrower LName\" value=\"\" /></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td></td>\n");
      out.write("                        <td></td>\n");
      out.write("                        <td></td>\n");
      out.write("                        <td></td>\n");
      out.write("                        <td></td>\n");
      out.write("                        <td><input type=\"submit\" value=\"Search Borrowals\" /></td>\n");
      out.write("                    </tr>                \n");
      out.write("                </tbody>\n");
      out.write("            </table>\n");
      out.write("       \n");
      out.write("        ");

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
            if (bookid != null || Card_no != null || Fname != null || Lname != null) {
                PreparedStatement pst = null;
                ResultSet result = null;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lbms_db?zeroDateTimeBehavior=convertToNull", "root", "admin12");
                    if ( (bookid != null || Card_no != null || Fname != null || Lname != null) && chkLoanID == null) {
                       //Selecting all the books which are out now  by checking date_in field is empty
                        String selSql = "SELECT b.book_id, b.title,l.loan_id, l.branch_id, l.card_no, bo.Fname, l.due_date, l.date_out "
                                + "FROM book b, book_loans l, borrower bo "
                                + "WHERE b.book_id = l.book_id AND "
                                + "l.card_no = bo.card_no AND "                               
                                + " (l.date_in = 0000-00-00  OR l.date_in IS NULL) ";
                        
                        if(bookid != "")
                        {
                            selBook = " AND b.book_id = '"+ bookid +"'";
                            selSql = selSql.concat(selBook);
                        }
                        if(Card_no != "")
                        {
                            selCard = " AND l.card_no = "+Card_no+"";
                            selSql = selSql.concat(selCard);
                        }
                        
                        if(Fname != "")
                        {
                            selFname = " AND bo.Fname = '"+Fname+"'";
                            selSql = selSql.concat(selFname);
                        }
                        if(Lname != "")
                        {
                            selLname = " AND bo.Lname = '"+Lname+"'";
                            selSql = selSql.concat(selLname);
                        }
                        pst = con.prepareStatement(selSql);
                        result = pst.executeQuery();
                        out.println("<h1></h1>");
                        out.println("<table>");
                        out.println("<tr>");
                        out.println("<td>");
                        out.println("<h3>Select Books for Check In</h3>");
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("<h1></h1>");
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
                            out.println("<td>" + result.getString("loan_id") + "</td><td>" + result.getString("book_id") + "</td><td>" + result.getString("title") + "</td><td>" +bookAuth+ "</td><td>" + result.getString("branch_id") + "</td><td>" + result.getString("card_no") + "</td><td>" + result.getString("Fname") + "</td><td>" + result.getString("due_date") + "</td><td>" + result.getString("date_out") + "</td>");
                            out.print("<td>");
                            box = "<input name='chk' value = " + result.getString("loan_id") + " type='checkbox'>";
                            out.print(box);
                            out.print("</td>");
                            out.print("</tr>");

                        }
                        if (check = true) {

                            out.println("<tr>");
                            out.print("<td>");
                            button = "<input type='submit' value='Check In' name='checkin'>";
                            out.print(button);
                            out.print("</td>");
                            out.println("</tr>");

                        }
                        out.println("</form>");
                        out.println("</table>");
                    }
               //Check IN updating the Date_in field in the Book_loans table.
                  if( chkLoanID != null && chkLoanID.length >0)
                  {
                    for (int i = 0; i < chkLoanID.length; i++) {                        
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            Date dt = new Date();  // Set Todays date
                            String retDate = sdf.format(dt);
                            String updateLoan = "UPDATE book_loans SET date_in = ? WHERE loan_id = "+chkLoanID[i]+"";
                            pstUpdate = con.prepareStatement(updateLoan);
                            pstUpdate.setString(1,retDate);
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
        
      out.write("         \n");
      out.write("    </form>\n");
      out.write("    </body>\n");
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
