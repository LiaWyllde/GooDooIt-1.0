package edu.curso.goodooit.app.model;

import java.time.LocalDate;
import java.util.List;

public class Projeto {
    Integer ID;
    String nome;
    String descricao;
    LocalDate dataInicio;
    LocalDate dataFim;
    LocalDate dataCriacao;
    Integer StatusProjetoID;
    Integer LiderID;

    List<Usuario> usuarios;
    List<Tarefa> tarefas;

    public Projeto() {}

    public Projeto(Integer ID, String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, LocalDate dataCriacao, Integer statusProjetoID, Integer liderID) {
        this.ID = ID;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataCriacao = dataCriacao;
        StatusProjetoID = statusProjetoID;
        LiderID = liderID;
    }

    public Projeto(String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, LocalDate dataCriacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataCriacao = dataCriacao;
    }

    public Projeto(String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, LocalDate dataCriacao, Integer statusProjetoID, Integer liderID) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataCriacao = dataCriacao;
        StatusProjetoID = statusProjetoID;
        LiderID = liderID;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Integer getStatusProjetoID() {
        return StatusProjetoID;
    }

    public void setStatusProjetoID(Integer statusProjetoID) {
        StatusProjetoID = statusProjetoID;
    }

    public Integer getLiderID() {
        return LiderID;
    }

    public void setLiderID(Integer liderID) {
        LiderID = liderID;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "ID=" + ID +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", dataCriacao=" + dataCriacao +
                ", StatusProjetoID=" + StatusProjetoID +
                ", LiderID=" + LiderID +
                '}';
    }
}
