package br.com.dsadeveloper.filesubmite.view;

import br.com.dsadeveloper.filesubmite.entity.Aluno;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LoginView {
    
    private Aluno alunoLogado;
    private List<Aluno> alunos = new ArrayList<Aluno>();

    private String login;
    private String senha;
    
    @PostConstruct
    public void inicializar() {
        try {
            File file = new File(getClass().getResource("/alunos").toURI());
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String linhaAluno = scanner.nextLine();
                Aluno aluno = new Aluno();
                String dadosAluno[] = linhaAluno.split(",");
                System.out.println(dadosAluno[0]);
                aluno.setNome(dadosAluno[0]);
                aluno.setLogin(dadosAluno[1]);
                aluno.setSenha(dadosAluno[2]);
                alunos.add(aluno);
            }
        } catch (Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar os alunos!", "Detalhe: "+ex.getMessage()));
//            ex.printStackTrace();
        }
    }
    
    public void logar(){
        if(alunos == null || alunos.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alunos vazios!", "Vazio!"));
        }
        Aluno aluno = new Aluno();
        aluno.setLogin(login);
        aluno.setSenha(senha);
        try {
        alunoLogado = alunos.get(alunos.indexOf(aluno));
        } catch (ArrayIndexOutOfBoundsException e){}
        if(alunoLogado == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dados inválidos!", "Login ou senha inválidos!"));
        }
    }
    
    public void deslogar(){
        alunoLogado = null;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    
    
    public Aluno getAlunoLogado() {
        return alunoLogado;
    }

    public void setAlunoLogado(Aluno alunoLogado) {
        this.alunoLogado = alunoLogado;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
    
}
