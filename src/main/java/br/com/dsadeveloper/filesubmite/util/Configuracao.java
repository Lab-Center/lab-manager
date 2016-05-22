package br.com.dsadeveloper.filesubmite.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuracao {
    
    public static void prepararBanco(){
        try {
            Statement statement = ConnectionFactory.getConnection().createStatement();
            statement.executeUpdate("create table if not exists configuracao (id integer, diretorio string)");
            ResultSet rs = statement.executeQuery("select diretorio from configuracao where id = 1");
            if(!rs.next()){
                statement.executeUpdate("insert into configuracao values(1, '"+ System.getProperty("user.home") +"/arquivos')");
            }
            ConnectionFactory.close();
        } catch (SQLException ex) {
            Logger.getLogger(Configuracao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
