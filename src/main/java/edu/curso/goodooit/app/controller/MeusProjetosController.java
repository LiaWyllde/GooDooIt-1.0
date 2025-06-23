package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Projeto;
import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.implementations.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MeusProjetosController {
    private final UsuarioDAO usuarioDAO;
    private final ProjetoDAO projetoDAO;
    private final StatusDAO statusDAO;
    private final TarefaDAO tarefaDAO;
    private final EquipeDAO equipeDAO;

    public MeusProjetosController(UsuarioDAO usuarioDAO, ProjetoDAO projetoDAO, StatusDAO statusDAO, TarefaDAO tarefaDAO, EquipeDAO equipeDAO) {
        this.usuarioDAO = usuarioDAO;
        this.projetoDAO = projetoDAO;
        this.statusDAO = statusDAO;
        this.tarefaDAO = tarefaDAO;
        this.equipeDAO = equipeDAO;
    }

    public ObservableList<Projeto> getProjetos() {
        Usuario autenticado = AutenticacaoController.getAutenticado();
        ObservableList<Projeto> projetosObservable = FXCollections.observableArrayList();
        try {
            List<Projeto> projetos = projetoDAO.buscarProjetoUsuarioLider(autenticado.getID());
            projetos.forEach(projeto -> {
                try {
                    projeto.setStatus(statusDAO.buscarStatusId(projeto.getStatusProjetoID()));
                    projeto.setTarefas(tarefaDAO.buscarTarefasProjetoId(projeto.getID()));
                    projeto.setUsuarios(equipeDAO.buscarUsuariosPorProjeto(projeto.getID()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            projetosObservable.addAll(projetos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projetosObservable;
    }

    public Integer contarTarefasConcluidas(Integer idProjeto){
        try{
            return tarefaDAO.contarTarefasCompletasProjetoId(idProjeto);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Integer contarTarefasNaoConcluidas(Integer idProjeto){
        try{
            return tarefaDAO.contarTarefasIncompletasProjetoId(idProjeto);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Projeto buscarProjeto(Integer idProjeto){
        try {
            return projetoDAO.buscarProjetoId(idProjeto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
        Implementar na fronteira tratativa para projeto nulo
     */

    public Projeto buscarProjeto(String nomeProjeto){
        try {
            return projetoDAO.buscarProjetoNome(nomeProjeto);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void salvarProjeto(Projeto projetoEditado){
        try{
            if (projetoEditado != null){
                System.out.println(projetoEditado.toString());
                projetoDAO.atualizarProjeto(projetoEditado);
            } else {
                System.out.println("Projeto não encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void criarProjeto(String nome, String descricao, LocalDate dataInicio, LocalDate dataFim) {
        //TODO: Implementar
        System.out.printf("Criando Projeto %s%n Descricao %s%n Data Inicio %s%n Data Final %s%n", nome,descricao,dataInicio.toString(),dataFim.toString());
        Projeto novoProjeto = new Projeto(nome, descricao, dataInicio, dataFim, LocalDate.now(), 1, AutenticacaoController.getAutenticado().getID());
        try {
            projetoDAO.registrarProjeto(novoProjeto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
