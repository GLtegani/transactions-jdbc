package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

   public DB() {
   }

   private Connection connection = null;

   private Properties loadProperties() {
      try (BufferedReader fs = new BufferedReader(new FileReader("db.properties"))) {
         Properties props = new Properties();
         props.load(fs);
         return props;
      } catch (IOException e) {
         throw new DbException(e.getMessage());
      }
   }

   public Connection getConnection() {
      if(this.connection == null) {
         try {
            Properties props = loadProperties();
            String url = props.getProperty("dburl");
            this.connection = DriverManager.getConnection(url, props);
         } catch (SQLException e) {
            throw new DbException(e.getMessage());
         }
      }

      return this.connection;
   }

   public void closeConnection() {
      if(this.connection != null) {
         try {
            this.connection.close();
         } catch (SQLException e) {
            throw new DbException(e.getMessage());
         }
      }
   }

   public void closeStatement(Statement st) {
      if(st != null) {
         try {
            st.close();
         } catch (SQLException e) {
            throw new DbException(e.getMessage());
         }
      }
   }
   public void closeResultSet(ResultSet rs) {
      if(rs != null) {
         try {
            rs.close();
         } catch (SQLException e) {
            throw new DbException(e.getMessage());
         }
      }
   }
}
