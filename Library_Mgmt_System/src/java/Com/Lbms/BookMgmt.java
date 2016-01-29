/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Com.Lbms;

/**
 *
 * @author Malini
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookMgmt {
    static final String url = "jdbc:mysql://localhost:3306/lbms?zeroDateTimeBehavior=convertToNull";
    public static void CreateUser(String card_no, String Fname, 
          String Lname, String address, String Phone) {
      try {

          String insert = "INSERT INTO borrower(card_no, Fname, Lname, address,Phone)" +
                  "VALUES (?, ?, ?, ?, ?,  ?)";

          Class.forName("com.mysql.jdbc.Driver");
          Connection con = DriverManager.getConnection(url, "root", "admin12");
 
          PreparedStatement ps = con.prepareStatement(insert);

          ps.setString(1, card_no);
          ps.setString(2, Fname);
          ps.setString(3, Lname);
          ps.setString(4, address);
          ps.setString(5, Phone);
          ps.executeUpdate();
          con.close();

      } catch (Exception ex) {
          Logger.getLogger(BookMgmt.class.getName()).log(
                           Level.SEVERE, null, ex);
      }
  }
    
    public static List GetBooks( String Book_id, String Title, String Author, String Rg_contains) {

      List<String> list = new ArrayList<String>();

      try {

          Class.forName("com.mysql.jdbc.Driver");
          Connection con = DriverManager.getConnection(url, "root", "admin12");

          Statement stmt = con.createStatement();

          ResultSet result = stmt.executeQuery("SELECT book_id, title,year_pub, publisher,url_s "
                  + "                           FROM  books b, book_author a"
                  + "                           WHERE b.book_id = a.book_id AND"
                  + "                                 b.book_id = book_id AND"
                  + "                                 a.author_name LIKE('%Author%)"
                            + "");

          while(result.next())
          {
             list.add(result.getString("Book_id"));
             list.add(result.getString("author"));
             list.add(result.getString("title"));
             list.add(result.getString("year_pub"));
             list.add(result.getString("publisher"));
             list.add(result.getString("url_s"));
          } 

          con.close();

      } catch (Exception ex) {
          Logger.getLogger(BookMgmt.class.getName()).log( 
                           Level.SEVERE, null, ex);
      }
          return list;
  }
    public static void BookLoanUpdate(String LoanId) {
      try {
          String delete = "DELETE from books WHERE id = ?";

          Class.forName("com.mysql.jdbc.Driver");
          Connection con = DriverManager.getConnection(url, "root", "admin12");
          PreparedStatement ps = con.prepareStatement(delete);

          ps.setString(1, LoanId);
          ps.executeUpdate();
          con.close();

      } catch (Exception ex) {
          Logger.getLogger(BookMgmt.class.getName()).log( 
             Level.SEVERE, null, ex);
      }
  }
    
    

}
