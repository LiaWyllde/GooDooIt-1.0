package edu.curso.goodooit.app.model;

public class Status {
    Integer ID;
    String titulo;
    String descricao;

    public Status() {}

    public Status(Integer ID, String titulo, String descricao) {
        this.ID = ID;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Status(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
