package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.implementations.UsuarioDAO;

import java.sql.SQLException;

//A controller de login pega as informações na ui.vew e requisita na service (use case)

public class LoginController {
    private UsuarioDAO usuarioDAO;

    public LoginController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario efetuarLogin(String login, String senha) throws SQLException{
        Integer id = usuarioDAO.validarSenha(login,senha);
        if(id != null){
            return usuarioDAO.buscarUsuarioID(id);
        }
        return null;
    }
}
