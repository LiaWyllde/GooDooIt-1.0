package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.implementations.*;

import java.sql.SQLException;

public class TarefaController {

    private final AutenticacaoController autenticacaoController;
    private final UsuarioDAO usuarioDAO;
    private final TarefaDAO tarefaDAO;
    private final StatusDAO statusDAO;
    private final EquipeDAO equipeDAO;

    public TarefaController(AutenticacaoController autenticacaoController, UsuarioDAO usuarioDAO, TarefaDAO tarefaDAO, StatusDAO statusDAO, EquipeDAO equipeDAO) {
        this.autenticacaoController = autenticacaoController;
        this.usuarioDAO = usuarioDAO;
        this.tarefaDAO = tarefaDAO;
        this.statusDAO = statusDAO;
        this.equipeDAO = equipeDAO;
    }

    Usuario autenticado = AutenticacaoController.getAutenticado();

    public boolean donoDaTarefa(Usuario usuario) {
        return false;
    }

    public void buscarStatusTarefa(Integer idTarefa) throws SQLException {

    }

    public void editarTarefa(Integer idTarefa) throws SQLException {

    }




}
