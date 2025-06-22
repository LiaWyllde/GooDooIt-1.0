package edu.curso.goodooit.app.model;

import java.util.List;

public class Usuario {
    Integer ID;
    String nome;
    String sobrenome;
    String login;
    String senha;
    String email;

    //lista de listas é responsabilidade da dao de lista
    List<Equipe> equipes;

    //As listas sao responsabilidade da dao de projeto
    List<Projeto> projetosColaborador;
    List<Projeto> projetosLider;

    public Usuario() {}

    public Usuario(String nome, String sobrenome, String login, String senha, String email) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.login = login;
        this.senha = senha;
        this.email = email;
    }

    public Usuario(Integer ID, String nome, String sobrenome, String login, String senha, String email) {
        this.ID = ID;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.login = login;
        this.senha = senha;
        this.email = email;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Projeto> getProjetosColaborador() {
        return projetosColaborador;
    }

    public void setProjetosColaborador(List<Projeto> projetosColaborador) {
        this.projetosColaborador = projetosColaborador;
    }

    public List<Projeto> getProjetosLider() {
        return projetosLider;
    }

    public void setProjetosLider(List<Projeto> projetosLider) {
        this.projetosLider = projetosLider;
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "ID=" + ID +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
