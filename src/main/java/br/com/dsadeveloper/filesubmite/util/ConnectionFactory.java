package br.com.dsadeveloper.filesubmite.util;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

    public static Connection connection;
    
    public static Connection getConnection() {
        try {
            if(connection == null || connection.isClosed()){
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:filesubmite.db");
            }
            return connection;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    public static void close(){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
