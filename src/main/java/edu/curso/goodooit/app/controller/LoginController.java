package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.implementations.UsuarioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.naming.AuthenticationNotSupportedException;
import java.sql.SQLException;

//A controller de login pega as informações na ui.vew e requisita na DAO

public class LoginController {
    private UsuarioDAO usuarioDAO;

    public LoginController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario efetuarLogin(String login, String senha) throws SQLException{
        Integer id = usuarioDAO.validarSenha(login,senha);
        if(id != null){
            Usuario u = usuarioDAO.buscarUsuarioID(id);
            AutenticacaoController.setAutenticado(u);
            return u;
        }
        return null;
    }
}
