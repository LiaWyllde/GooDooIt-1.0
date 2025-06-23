package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Projeto;
import edu.curso.goodooit.app.model.Status;
import edu.curso.goodooit.app.model.Tarefa;
import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.implementations.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class TarefaController {

    private final AutenticacaoController autenticacaoController;
    private final TarefaDAO tarefaDAO;
    private final StatusDAO statusDAO;

    public TarefaController(AutenticacaoController autenticacaoController, TarefaDAO tarefaDAO, StatusDAO statusDAO) {
        this.autenticacaoController = autenticacaoController;
        this.tarefaDAO = tarefaDAO;
        this.statusDAO = statusDAO;
    }

    Usuario autenticado = AutenticacaoController.getAutenticado();

    public boolean VerificarCriadorDaTarefa(Usuario usuario) {
        if (!Objects.equals(usuario.getID(), autenticado.getID())) {
            System.out.println("O usuario autenticado não é criador da tarefa.");
            return false;
        }
        return true;
    }

    public ObservableList<Tarefa> getTarefas(Integer IDProjeto) {
        ObservableList<Tarefa> tarefasObservable = FXCollections.observableArrayList();
        try {
            List<Tarefa> tarefas = tarefaDAO.buscarTarefasProjetoId(IDProjeto);
            tarefas.forEach(tarefa -> {
                try {
                    tarefa.setStatus(statusDAO.buscarStatusId(tarefa.getStatusTarefaID()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            tarefasObservable.addAll(tarefas);
        } catch (Exception e) {
            System.out.println("Erro de consulta das tarefas, SQL quis assim." + e.getMessage());
        }
        return tarefasObservable;
    }

    public void editarTarefa(Tarefa tarefaEditada) throws SQLException {
        try {
            if (tarefaEditada != null){
                tarefaDAO.atualizarTarefa(tarefaEditada);
            } else {
                System.out.println("Tarefa não encontrada");
            }
        } catch (Exception e) {
            System.out.println("Erro ao editar tarefas, SQL quis assim." + e.getMessage());
        }
    }

    public void excluirTarefa(Integer idTarefa) throws SQLException {

        try {
            Tarefa tarefa = tarefaDAO.buscarTarefaId(idTarefa);
            tarefaDAO.excluirTarefa(tarefa);
        } catch (Exception e) {
            System.out.println("Erro ao excluir tarefas, SQL quis assim." + e.getMessage());
        }

    }

    public void adicionarTarefa (Integer ID, String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, LocalDate dataCriacao, int prioridade, Integer statusTarefaID, Integer criadorID, Integer responsavelID, Integer projetoID)  {
        Tarefa tarefa = new Tarefa(ID, nome, descricao, dataInicio, dataFim, dataCriacao, prioridade, criadorID, responsavelID, statusTarefaID, projetoID);
        System.out.printf("Tarefa %s de id %d adicionada com sucesso!", tarefa.getNome(), tarefa.getID());
        try {
            tarefaDAO.registrarTarefa(tarefa);
        } catch (Exception e) {
            System.out.println("Erro ao adicionar nova tarefa, SQL quis assim." + e.getMessage());
        }
    }
}
