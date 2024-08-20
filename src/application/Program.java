package application;

import db.DB;
import db.DbException;
import db.DbIntegrityException;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Program {
   public static void main(String[] args) {
      DB db = new DB();
      Connection connection = null;
      Statement st = null;

      try {
        connection = db.getConnection();
        connection.setAutoCommit(false);
        st = connection.createStatement();

        int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
//        int x = 1;
//
//        if(x < 2) {
//           throw new SQLException("Fake error");
//        }

        int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");

        connection.commit();
        System.out.println("rows1 " + rows1);
        System.out.println("rows2 " + rows2);


      } catch (SQLException e) {
         try {
            connection.rollback();
            throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
         } catch (SQLException e1) {
            throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage());
         }
      } finally {
         db.closeStatement(st);
         db.closeConnection();
         System.out.println("Closed!");
      }

   }
}
