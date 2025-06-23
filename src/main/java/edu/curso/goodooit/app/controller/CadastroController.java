package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.implementations.UsuarioDAO;

public class CadastroController {

    private final UsuarioDAO usuarioDAO ;

    public CadastroController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public boolean cadastrar(String nome, String sobrenome, String login, String senha, String email) {

        if (nome == null || sobrenome == null || login == null || senha == null || email == null) {
            return false;
        }

        Usuario usuario = new Usuario(nome, sobrenome, login, senha, email);

        try {
            Integer id = usuarioDAO.registrarUsuario(usuario);
            usuario.setID(id);
            usuarioDAO.atualizarUsuario(usuario);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar, SQL quis assim: " + e.getMessage());
        }
        return true;
    }

    //Cntrole de visibilidade de mensagem de "cadastro concluído" e "campos obrigatórios"

}
