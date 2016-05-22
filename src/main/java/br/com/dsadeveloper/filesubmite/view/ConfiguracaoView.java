package br.com.dsadeveloper.filesubmite.view;

import br.com.dsadeveloper.filesubmite.util.Configuracao;
import br.com.dsadeveloper.filesubmite.util.ConnectionFactory;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author danilo
 */
@ManagedBean
@ViewScoped
public class ConfiguracaoView implements Serializable{
    
    private String diretorio;
    
    @PostConstruct
    public void inicializar(){
        try {
            Configuracao.prepararBanco();
            Statement ps = ConnectionFactory.getConnection().createStatement();
            ResultSet rs = ps.executeQuery("select diretorio from configuracao where id = 1");
            ConnectionFactory.close();
            if(rs.next()){
                diretorio = rs.getString("diretorio");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConfiguracaoView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void salvar(){
        try {
            File file = new File(diretorio);
            if(file == null || !file.isDirectory()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Diretório inválido!!")); 
            }
            PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement("update from configuracao values(1, ?)");
            ps.setString(1, diretorio);
            ConnectionFactory.close();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Salvo com sucesso!!")); 
        } catch (SQLException ex) {
            Logger.getLogger(ConfiguracaoView.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Desculpe! Erro na aplicação!")); 
        }
    }
    
    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }
    
}
