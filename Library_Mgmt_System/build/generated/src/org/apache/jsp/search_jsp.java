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

public final class search_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_set_var_value_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_c_set_var_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_set_var_value_nobody.release();
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

      out.write('\n');
      out.write('\n');
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      if (_jspx_meth_c_set_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Search_Book</title>\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">\n");
      out.write("    </head>\n");
      out.write("    <body style = \"background-image: url(lib2.jpg)\">\n");
      out.write("\n");
      out.write("\n");
      out.write("    <center>\n");
      out.write("        <table\n");
      out.write("            <tr>\n");
      out.write("                <th><h2>Find Your Books</h2></th>\n");
      out.write("            </tr>\n");
      out.write("        </table>\n");
      out.write("        <form action=\"search.jsp\">      \n");
      out.write("            <table border=\"0\" width=\"6\" cellspacing=\"1\">\n");
      out.write("\n");
      out.write("                <tbody>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>Book Id</td>\n");
      out.write("                        <td><input id = \"book_id\" type=\"text\" name=\"book_id\"/></td>\n");
      out.write("                        <td>Title</td>  \n");
      out.write("                        <td><input type=\"text\" name=\"title\"/></td></td>              \n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>Author</td> \n");
      out.write("                        <td><input type=\"text\" name=\"Txt_auth\"/></td></td>    \n");
      out.write("                        <td><input type=\"radio\" id = \"r1\" name=\"Rg1\" value=\"Exactly\" checked=\"checked\" />\n");
      out.write("                            Exactly\n");
      out.write("                        </td>                          \n");
      out.write("                        <td><input type=\"radio\" id = \"r1\" name=\"Rg1\" value=\"Contains\" /> Contains</td>                                             \n");
      out.write("                    </tr> \n");
      out.write("                    <tr>\n");
      out.write("                        <td></td>\n");
      out.write("                        <td></td>\n");
      out.write("                        <td>\n");
      out.write("                            <input type=\"submit\" value=\"SUBMIT\" name=\"SUBMIT\" />\n");
      out.write("                        </td>\n");
      out.write("                    </tr>\n");
      out.write("                </tbody>\n");
      out.write("            </table> \n");
      out.write("        </form>\n");
      out.write("\n");
      out.write("        ");

            String bookid = request.getParameter("book_id");
            String title = request.getParameter("title");
            String author = request.getParameter("Txt_auth");
            String radio = request.getParameter("Rg1");

            String selAuthCont = null;
            String selBookid = null;
            String selAuthExact = null;
            String selTitle = null;
            String selGroup = null;

            int id = 0;
            String box = null;
            String button = null;
            String checkout = null;
            Boolean check = false;

            if (bookid == "" && title == "" && author == "") {
                bookid = null;
                title = null;
                author = null;
            }
            if (bookid != null || title != null || author != null) {
                Connection con = null;
                PreparedStatement pst = null;
                ResultSet result = null;
                PreparedStatement pst_auth = null;
                ResultSet rslt_auth = null;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lbms_db?zeroDateTimeBehavior=convertToNull", "root", "admin12");
                    /* 
                     pst = con.prepareStatement("SELECT b.book_id, b.title, a.author_name, c.branch_id, c.no_of_copies "
                     + "FROM book b, book_authors a, book_copies c "
                     + "WHERE b.book_id = a.book_id AND "                
                     + "b.book_id = c.book_id AND "
                     + " ( b.book_id = "+bookid+") ");
                     */
                    /*
                    String selSql = "SELECT b.book_id, b.title, c.branch_id, c.no_of_copies, (no_of_copies-count(loan_id)) as Availability"
                            + " FROM book b, book_authors a, (book_copies c LEFT JOIN book_loans l  ON c.book_id = l.book_id AND c.branch_id = l.branch_id) "
                            + " WHERE b.book_id = a.book_id AND "
                            + " b.book_id = c.book_id  AND l.date_in IS NULL";
                     */
                    String selSql = "SELECT b.book_id, b.title, c.branch_id, c.no_of_copies "
                            + " FROM book b, book_authors a, book_copies c "
                            + " WHERE b.book_id = a.book_id AND "
                            + " b.book_id = c.book_id";
                   

                    //Populating WHERE conditions dynamically checking the USER inputs
                    if (radio.equals(new String("Contains")) && author != "") {
                        selAuthCont = " AND a.author_name LIKE '%" + author + "%'";
                        selSql = selSql.concat(selAuthCont);
                    }
                    if (radio.equals(new String("Exactly")) && author != "") {
                        selAuthExact = " AND a.author_name = '" + author + "'";
                        selSql = selSql.concat(selAuthExact);
                    }
                    if (bookid != "") {
                        selBookid = " AND b.book_id = " + bookid + "";
                        selSql = selSql.concat(selBookid);
                    }
                    if (title != "") {
                        selTitle = " AND b.title LIKE '%"+ title +"%'";
                        selSql = selSql.concat(selTitle);
                    }

                    //To add the group by clause to get the correct avaialability
                    selGroup = " GROUP BY c.branch_id, c.book_id";
                    selSql = selSql.concat(selGroup);

                    pst = con.prepareStatement(selSql);

                    result = pst.executeQuery();

                    out.println("<table>");
                    checkout = "<form action='Checkout.jsp'>";
                    out.println(checkout);
                    out.println("<tr>");
                    out.println("<th>Book_id</th>");
                    out.println("<th>Title</th>");
                    out.println("<th>Author</th>");
                    out.println("<th>Branch_id</th>");
                    out.println("<th>No of Copies</th>");
                    out.println("<th>Availability</th>");
                    out.println("</tr>");
                    String selAuth = null;
                    String bookAuth = null;
                    String selAvailability = null;
                    ResultSet rslt_availble = null;
                    int available = 0;
                    int available_copies = 0;
                    int chkdouts = 0; 
                    while (result.next()) {
                        // To concatenate author names if multiple authors exist
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
                        selAvailability = "SELECT count(*) AS available FROM book_loans WHERE book_id = '"+result.getString("b.book_id")+"' AND branch_id = "+result.getInt("c.branch_id")+" AND date_in = 0000-00-00";
                        pst_auth = con.prepareStatement(selAvailability);
                        rslt_availble = pst_auth.executeQuery();
                        available = 0;
                        available_copies = result.getInt("c.no_of_copies");
                        while(rslt_availble.next())
                        {
                            chkdouts = rslt_availble.getInt("available");
                            available =  available_copies - chkdouts;
                            
                        }
                        check = true;
                        out.println("<tr>");
                        out.println("<td>" + result.getString("b.book_id") + "</td><td>" + result.getString("b.title") + "</td><td>" + bookAuth + "</td><td>" + result.getString("c.branch_id") + "</td><td>" + result.getString("c.no_of_copies") + "</td><td>"+available+"</td>");
                        // out.print("<td>");
                        // box = "<input name='chk' value=r" + result.getString("book_id") + " type='checkbox'>";
                        // out.print(box);                        
                        // out.print("</td>");
                        out.print("</tr>");

                    }
                    if (check == true) {

                        out.println("<tr>");
                        out.print("<td>");
                        button = "<input type='submit' value='Checkout' name='checkout'>";
                        out.print(button);
                        out.print("</td>");
                        out.println("</tr>");

                    } else {
                        out.println("<tr>");
                        out.print("<td>");
        
      out.write("<dialog open> <font color = 'red'>No Books Found for your search. Sorry!</font> </dialog>");

                        out.print("</td>");
                        out.println("</tr>");
                    }
                    out.println("</form>");
                    out.println("</table>");

                } catch (ClassNotFoundException e) {
                    out.println("<b>Driver Class not found Exception: </b>" + e.getMessage());

                }
            }
            
      out.write(" \n");
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

  private boolean _jspx_meth_c_set_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_0.setPageContext(_jspx_page_context);
    _jspx_th_c_set_0.setParent(null);
    _jspx_th_c_set_0.setVar("book_id");
    _jspx_th_c_set_0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${book_id}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    int _jspx_eval_c_set_0 = _jspx_th_c_set_0.doStartTag();
    if (_jspx_th_c_set_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
      return true;
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
    return false;
  }
}
