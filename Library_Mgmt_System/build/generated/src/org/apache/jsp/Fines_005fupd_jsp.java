package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Fines_005fupd_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("        <title>Fine Update</title>\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">    \n");
      out.write("\n");
      out.write("    </head>\n");
      out.write("    <body style = \"background-image: url(lib2.jpg)\">\n");
      out.write("    <center>\n");
      out.write("        <table>\n");
      out.write("            <tr>\n");
      out.write("                <th></th>\n");
      out.write("                <th><h2>Updated Fines Details</h2></th>\n");
      out.write("                <th></th>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td></td>\n");
      out.write("                <td></td>\n");
      out.write("                <td>\n");
      out.write("                <form name=\"Fines\" action=\"Fines.jsp\">\n");
      out.write("                <input type=\"submit\" value=\"Go Back to Fines\" name=\"Fines\" />                 \n");
      out.write("                </form>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("        </table>  \n");
      out.write("        <h1></h1>\n");
      out.write("\n");
      out.write("        ");

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
                if (!rslt.next()) {
                    out.println("<b><font color = 'red'>No new Fine Information found for update</font></b>");
                }
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
                out.println("<th>Fine_Amt</th>");
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

        
      out.write("      \n");
      out.write("    </center>\n");
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
