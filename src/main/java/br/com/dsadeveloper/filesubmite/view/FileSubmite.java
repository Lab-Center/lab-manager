package br.com.dsadeveloper.filesubmite.view;

import br.com.dsadeveloper.filesubmite.entity.Aluno;
import br.com.dsadeveloper.filesubmite.util.StringHelper;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author danilo
 */
@ManagedBean
@SessionScoped
public class FileSubmite implements Serializable{
    
    private String nome;
    private String baseDirectory;
    
    @ManagedProperty(value = "#{loginView}")
    private LoginView loginView;

    @PostConstruct
    public void inicializar(){
//        try {
//            Configuracao.prepararBanco();
//            Statement ps = ConnectionFactory.getConnection().createStatement();
//            ResultSet rs = ps.executeQuery("select diretorio from configuracao where id = 1");
//            if(rs.next()){
//                baseDirectory = rs.getString("diretorio");
//            } else {
                baseDirectory = System.getProperty("user.home") +"/atividade_ib_2016_04_29";
//            }
//            ConnectionFactory.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(FileSubmite.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    public void uploadImagem(FileUploadEvent event) {
//        nome = loginView.getAlunoLogado().getLogin();
        if(nome == null || nome.isEmpty() ){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe primeiro seu nome", "Informe primeiro seu nome"));
            return;
        }
        try {
            System.out.println(baseDirectory);
            File diretorioBase = new File(baseDirectory);
            if(!diretorioBase.exists()){
                diretorioBase.mkdir();
            }
            File diretorio = new File(baseDirectory+"/"+nome);
            if(!diretorio.exists()){
                diretorio.mkdir();
            }
            File file = new File(diretorio.getAbsolutePath()+"/"+event.getFile().getFileName());
            if(file.exists()){
                int i = 1;
                while(file.exists()){
                    if(event.getFile().getFileName().lastIndexOf(".") > 1){
                        file = new File(diretorio.getAbsolutePath()+"/"+event.getFile().getFileName().substring(0, event.getFile().getFileName().lastIndexOf(".")) +i + event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf(".")));
                    } else {
                        file = new File(diretorio.getAbsolutePath()+"/"+event.getFile().getFileName() +i);
                    }
                    i++;
                }
            }
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(event.getFile().getContents());
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(FileSubmite.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Seus arquivos foram enviados com sucesso!"));
    }  
    
    public String[] getListaDeArquivosEnviados(){
//        Aluno aluno = loginView.getAlunoLogado();
//        if(aluno == null){
//            return new String[] {};
//        }
//        nome = loginView.getAlunoLogado().getLogin();
        if(nome == null || nome.isEmpty() ){
            return new String[]{};
        }
        System.out.println(baseDirectory);
        File diretorioBase = new File(baseDirectory);
        if(!diretorioBase.exists()){
            diretorioBase.mkdir();
        }
        File diretorio = new File(baseDirectory+"/"+nome);
        if(!diretorio.exists()){
            diretorio.mkdir();
        }
        return diretorio.list();
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        nome = StringHelper.replaceAllSpecialCharacters(nome).toUpperCase();
        this.nome = nome;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }
    
}
