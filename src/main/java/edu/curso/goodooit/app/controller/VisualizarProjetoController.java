package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.*;
import edu.curso.goodooit.app.persistence.implementations.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class VisualizarProjetoController {

    private final TarefaDAO tarefaDAO;
    private final StatusDAO statusDAO;
    private final EquipeDAO equipeDAO;

    public VisualizarProjetoController(TarefaDAO tarefaDAO, StatusDAO statusDAO, EquipeDAO equipeDAO) {
        this.tarefaDAO = tarefaDAO;
        this.statusDAO = statusDAO;
        this.equipeDAO = equipeDAO;
    }

    public boolean VerificarDonoDoProjeto(Projeto projeto) {
        Usuario autenticado = AutenticacaoController.getAutenticado();
        if (!Objects.equals(projeto.getLiderID(), autenticado.getID())) {
            System.out.println("O usuario autenticado não é dono do projeto.");
            return false;
        }
        return true;
    }

    public ObservableList<Tarefa> listarTarefasdoProjeto(Projeto projeto) {

        ObservableList<Tarefa> observableTarefas = FXCollections.observableArrayList();
        List<Tarefa> tarefas;

        try {
            tarefas = tarefaDAO.buscarTarefasProjetoId(projeto.getID());
            tarefas.forEach(tarefa -> {
                try {
                    tarefa.setStatus(statusDAO.buscarStatusId(tarefa.getStatusTarefaID()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            observableTarefas.addAll(tarefas);

        } catch (Exception ex) {
            System.out.println("Impossivel buscar tarefas por projeto, SQL quis assim");
        }
        return observableTarefas;
    }

    public ObservableList<Status> getAllStatus(){
        ObservableList<Status> observableStatuses = FXCollections.observableArrayList();
        List<Status> statuses;

        try{
            statuses = statusDAO.buscarTodosStatus();
            observableStatuses.addAll(statuses);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return observableStatuses;
    }

    public Status buscarStatus (Projeto projeto) {

        Status status = null;

        try {
            status = statusDAO.buscarStatusId(projeto.getStatus().getID());

        } catch (Exception e) {
            System.out.println("Impossivel buscar status do projeto, SQL quis assim.");
        }

        if (status != null) {
            return status;
        }

        System.out.println("Se alguma coisa aconteceu e voce cheogu em um erro aqui: " +
                "\n 1 - linha 64 de visualizar projeto controller" +
                "\n 2 - eu honestamente nao sei como resolver" +
                "\n 3 - acho que deus nos abandonou");
        ;

        return null;
    }

    public ObservableList<Usuario> listarMembrosDoProjeto(Projeto projeto) {

        ObservableList<Usuario> observableMembros = FXCollections.observableArrayList();
        List<Usuario> membros = null;

        try {
            membros = equipeDAO.buscarUsuariosPorProjeto(projeto.getID());
            observableMembros.addAll(membros);

        } catch (Exception e) {
            System.out.println("Impossivel buscar membros do projeto, SQL quis assim.");
        }

        if (membros != null) {
            return observableMembros;
        }

        System.out.println("Se alguma coisa aconteceu e voce cheogu em um erro aqui: " +
                "\n 1 - linha 88 de visualizar projeto controller" +
                "\n 2 - eu honestamente nao sei como resolver" +
                "\n 3 - acho que deus nos abandonou");

        return null;
    }

    public Integer contarTarefaProjeto(Projeto projeto) {

        Integer contarTarefa = null;

        try {

            contarTarefa = tarefaDAO.contarTarefaProjetoId(projeto.getID());

        }   catch (Exception e) {
            System.out.println("Impossivel buscar tarefas do projeto, SQL quis assim.");
        }

        if (contarTarefa != null) {
            return contarTarefa;
        }

        System.out.println("Se alguma coisa aconteceu e voce cheogu em um erro aqui: " +
                "\n 1 - linha 113 de visualizar projeto controller" +
                "\n 2 - eu honestamente nao sei como resolver" +
                "\n 3 - acho que deus nos abandonou");

        return contarTarefa;
    }

    public Integer contarMembrosProjeto(Projeto projeto) {
        try{
            return equipeDAO.contarMembrosProjeto(projeto.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removerMembroDoProjeto(Usuario usuario, Projeto projeto) {
        Usuario autenticado = AutenticacaoController.getAutenticado();
        if (Objects.equals(projeto.getLiderID(), autenticado.getID())) {
            try {
                equipeDAO.removerUsuarioProjeto(projeto.getID(), usuario.getID());
            } catch (SQLException e) {
                System.out.println("Erro ao remover membro do projeto: " + e.getMessage());
            }
        } else {
            System.out.println("Usuário não é o líder do projeto.");
        }
    }

    // TODO: Alteração de dados do projeto
    // TODO: Uusário sair do projeto
    //Reutilizar os botões da tela de meus projetos


}
