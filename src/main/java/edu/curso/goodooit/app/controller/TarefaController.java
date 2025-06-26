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

    private final TarefaDAO tarefaDAO;
    private final StatusDAO statusDAO;
    private final UsuarioDAO usuarioDAO;
    private final ProjetoDAO projetoDAO;

    public TarefaController(TarefaDAO tarefaDAO, StatusDAO statusDAO, UsuarioDAO usuarioDAO, ProjetoDAO projetoDAO) {
        this.tarefaDAO = tarefaDAO;
        this.statusDAO = statusDAO;
        this.usuarioDAO = usuarioDAO;
        this.projetoDAO = projetoDAO;
    }

    public boolean VerificarCriadorDaTarefa(Usuario usuario) {
        Usuario autenticado = AutenticacaoController.getAutenticado();
        if (!Objects.equals(usuario.getID(), autenticado.getID())) {
            System.out.println("O usuario autenticado não é criador da tarefa.");
            return false;
        }
        return true;
    }

    public Projeto getProjeto(Integer idTarefa) {
        Projeto projeto = null;
        try {
            Tarefa tarefa = tarefaDAO.buscarTarefaId(idTarefa);
            if (tarefa != null) {
                projeto = projetoDAO.buscarProjetoId(tarefa.getProjetoID());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o projeto da tarefa: " + e.getMessage());
        }
        return projeto;
    }

    public ObservableList<Tarefa> getTarefas(Integer idUsuario) {
        ObservableList<Tarefa> tarefasObservable = FXCollections.observableArrayList();
        try {
            List<Tarefa> tarefas = tarefaDAO.buscarTarefaIdResponsavel(idUsuario);
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

    public void editarTarefa(Tarefa tarefaEditada) {
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

    public void excluirTarefa(Integer idTarefa) {

        try {
            Tarefa tarefa = tarefaDAO.buscarTarefaId(idTarefa);
            tarefaDAO.excluirTarefa(tarefa);
        } catch (Exception e) {
            System.out.println("Erro ao excluir tarefas, SQL quis assim." + e.getMessage());
        }

    }

    public void adicionarTarefa (String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, int prioridade, String responsavel, Integer projetoID)  {
        Usuario autenticado = AutenticacaoController.getAutenticado();
        Integer responsavelID;
        try {
            if(responsavel.isBlank()) {
                responsavelID = null;

            } else{
                responsavelID = usuarioDAO.buscarUsuarioLogin(responsavel).getID();
            }
            Tarefa tarefa = new Tarefa(nome, descricao, dataInicio, dataFim, LocalDate.now(), prioridade, 1, autenticado.getID(), responsavelID, projetoID);
            System.out.printf("Tarefa %s de id %d adicionada com sucesso!", tarefa.getNome(), tarefa.getID());
            tarefaDAO.registrarTarefa(tarefa);
        } catch (Exception e) {
            System.out.println("Erro ao adicionar nova tarefa, SQL quis assim." + e.getMessage());
        }
    }

    public Integer buscarIdResponsavel (String loginResponsavel){
        Integer responsavelID = null;
        try{
            if(loginResponsavel.isBlank()) {
                return null;

            } else{
                responsavelID = usuarioDAO.buscarUsuarioLogin(loginResponsavel).getID();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responsavelID;
    }

    public String buscarResposavel(Integer responsavelID) {
        try{
            if(responsavelID != null){
                return usuarioDAO.buscarUsuarioID(responsavelID).getNome();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
