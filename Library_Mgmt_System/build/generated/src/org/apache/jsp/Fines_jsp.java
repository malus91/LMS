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
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Fines_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Fine</title>\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">    \n");
      out.write("\n");
      out.write("    </head>\n");
      out.write("    <body style = \"background-image: url(lib2.jpg)\">         \n");
      out.write("    <center>\n");
      out.write("        <h1>Update Fines information</h1>\n");
      out.write("        <form name=\"Update\" action=\"Fines_upd.jsp\">\n");
      out.write("            <table border=\"0\" width=\"3\" cellspacing=\"2\">\n");
      out.write("                <thead>\n");
      out.write("                    <tr>\n");
      out.write("                        <th>Update Fines</th>\n");
      out.write("                        <th></th>\n");
      out.write("                    </tr>\n");
      out.write("                </thead>\n");
      out.write("                <tbody>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>Update Fine table with todays Data</td>\n");
      out.write("                        <td><input type=\"submit\" value=\"Update / View Fines\" name=\"SUBMIT\"/></td>\n");
      out.write("                    </tr>\n");
      out.write("                </tbody>\n");
      out.write("            </table>           \n");
      out.write("        </form>\n");
      out.write("        <h1>Check your Fines Here</h1>\n");
      out.write("        <form name=\"Fines\" action=\"Fines.jsp\">\n");
      out.write("            <table border=\"0\" width=\"3\" cellspacing=\"2\">\n");
      out.write("                <thead>\n");
      out.write("                    <tr>\n");
      out.write("                        <th>Get Fine Details</th>\n");
      out.write("                        <th></th>\n");
      out.write("                    </tr>\n");
      out.write("                </thead>\n");
      out.write("                <tbody>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>Card No</td>\n");
      out.write("                        <td><input type=\"text\" name=\"Card_no\" value=\"\"/></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td></td>\n");
      out.write("                        <td><input type=\"submit\" value=\"Get Fines\" name=\"SUBMIT\" /></td>\n");
      out.write("                    </tr>\n");
      out.write("                </tbody>\n");
      out.write("            </table>        \n");
      out.write("            ");
 
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
              if(Card_no != null && selected_Checkboxes == null) 
              {
                String selSql = "SELECT  l.card_no, SUM(f.fine_amt) AS total_fine, f.paid  "
                                + "FROM book_loans l, fines f  "
                                + "WHERE l.loan_id = f.loan_id AND "
                                + "l.card_no = "+Card_no+" "
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
                out.println("<th>Fine_Amt</th>");
                out.println("<th>Paid OR Not</th>");
                out.println("</tr>");
                while (result.next())
                {
                    chk = true; 
                    paid = "No";
                    if(result.getBoolean("f.paid")) 
                    {
                       paid= "Yes"; 
                    }
                
                    out.println("<tr>");
                    out.println("<td>" + result.getInt("l.card_no") + "</td><td>" + result.getString("total_fine") + "</td><td>" +paid+ "</td>");
                    out.print("<td>");
                    box = "<input name='chk' value=" + result.getInt("l.card_no") + " type='checkbox'>";
                    out.print(box);                        
                    out.print("</td>");
                    out.print("</tr>");
                }
                
                if(chk == true)
                {
                            out.println("<tr>");
                            out.print("<td>");
                            button = "<input type='submit' value='Pay Fine' name='Pay'>";
                            out.print(button);
                            out.print("</td>");
                            out.println("</tr>");
                }
                
                else
                {
            
      out.write("<dialog open> <font color = 'green'>No Fine information. You owe nothing! Thank You</font> </dialog>");

                }
               out.println("</form>");
               out.println("</table>");
              }
              else if(selected_Checkboxes != null)
              {
                  String sqlLoan = null;
                  ResultSet resultLoan = null; 
                  String sqlUpdFine = null;
                  PreparedStatement pstUpd = null;
                  String sqlBook = null;
                  ResultSet rsltBook = null; 
                  char chkouts = 'N'; 
                  
                 int length_chk = selected_Checkboxes.length;
                 for(int i=0;i<length_chk;i++)
                 {
                     // Check whether the Book is returned before paying the fine.
                     sqlBook = "SELECT COUNT(loan_id) AS no_chkouts FROM book_loans WHERE card_no = "+selected_Checkboxes[i]+" AND date_in = '0000-00-00' AND due_date < "+current_date+"";
                     pst = con.prepareStatement(sqlBook);
                     rsltBook = pst.executeQuery();
                     while(rsltBook.next())
                     {
                         if(rsltBook.getInt("no_chkouts")>0)
                         {     chkouts = 'Y';
                         }
                     }   
                     if(chkouts == 'Y')
                     {
                     
      out.write("<dialog open> <font color = 'red'>You have outstanding due checkouts!. Please return the books and then Pay the fine</font> </dialog>");

                     }                     
                     //Get the corresponding loan_Ids for each customer from Fines table
                     
                     sqlLoan = "SELECT loan_id FROM book_loans WHERE card_no = "+selected_Checkboxes[i]+" AND date_in IS NOT NULL AND due_date < date_in";  
                     pst = con.prepareStatement(sqlLoan);
                     resultLoan = pst.executeQuery();
                     while(resultLoan.next())
                     {
                        sqlUpdFine = "UPDATE fines SET paid = true WHERE loan_id = "+resultLoan.getInt("loan_id")+""; 
                        pstUpd = con.prepareStatement(sqlUpdFine);
                        pstUpd.executeUpdate();
                        out.println("Payment Updated Successfully");
                     }                     
                 }
              }
          
            
      out.write("\n");
      out.write("        </form>        \n");
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
