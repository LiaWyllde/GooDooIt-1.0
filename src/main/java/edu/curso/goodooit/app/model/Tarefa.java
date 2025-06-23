package edu.curso.goodooit.app.model;

import java.time.LocalDate;

public class Tarefa {
    private Integer ID;
    private String nome;
    private String descricao;
    private int posicao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private LocalDate dataCriacao;
    private int prioridade;
    private Integer StatusTarefaID;
    private Integer QuadroID;
    private Integer ListaID;
    private Integer CriadorID;
    private Integer ResponsavelID;
    private Integer ProjetoID;

    private Status status;


    public Tarefa() {}

    public Tarefa(Integer ID, String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, LocalDate dataCriacao, int prioridade, Integer statusTarefaID, Integer criadorID, Integer responsavelID, Integer projetoID) {
        this.ID = ID;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataCriacao = dataCriacao;
        this.prioridade = prioridade;
        CriadorID = criadorID;
        ResponsavelID = responsavelID;
        StatusTarefaID = statusTarefaID;
        ProjetoID = projetoID;
    }

    public Tarefa(String nome, String descricao, int posicao, LocalDate dataInicio, LocalDate dataFim, LocalDate dataCriacao, int prioridade) {
        this.nome = nome;
        this.descricao = descricao;
        this.posicao = posicao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataCriacao = dataCriacao;
        this.prioridade = prioridade;
    }

    public Tarefa(String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, LocalDate dataCriacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataCriacao = dataCriacao;
        this.prioridade = 3;
        this.posicao = 1;
    }

    public Tarefa(String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, LocalDate dataCriacao, int prioridade, Integer statusTarefaID, Integer criadorID, Integer responsavelID, Integer projetoID) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataCriacao = dataCriacao;
        this.prioridade = prioridade;
        CriadorID = criadorID;
        ResponsavelID = responsavelID;
        StatusTarefaID = statusTarefaID;
        ProjetoID = projetoID;
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

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
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

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public Integer getStatusTarefaID() {
        return StatusTarefaID;
    }

    public void setStatusTarefaID(Integer statusTarefaID) {
        StatusTarefaID = statusTarefaID;
    }

    public Integer getQuadroID() {
        return QuadroID;
    }

    public void setQuadroID(Integer quadroID) {
        QuadroID = quadroID;
    }

    public Integer getListaID() {
        return ListaID;
    }

    public void setListaID(Integer listaID) {
        ListaID = listaID;
    }

    public Integer getCriadorID() {
        return CriadorID;
    }

    public void setCriadorID(Integer criadorID) {
        CriadorID = criadorID;
    }

    public Integer getResponsavelID() {
        return ResponsavelID;
    }

    public void setResponsavelID(Integer responsavelID) {
        ResponsavelID = responsavelID;
    }

    public Integer getProjetoID() {
        return ProjetoID;
    }

    public void setProjetoID(Integer projetoID) {
        ProjetoID = projetoID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "ID=" + ID +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", posicao=" + posicao +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", dataCriacao=" + dataCriacao +
                ", prioridade=" + prioridade +
                ", StatusTarefaID=" + StatusTarefaID +
                ", QuadroID=" + QuadroID +
                ", ListaID=" + ListaID +
                ", CriadorID=" + CriadorID +
                ", ResponsavelID=" + ResponsavelID +
                '}';
    }
}
