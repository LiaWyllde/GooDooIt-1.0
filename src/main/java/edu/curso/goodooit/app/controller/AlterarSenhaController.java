package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.implementations.UsuarioDAO;

import java.sql.SQLException;

public class AlterarSenhaController {

    private final UsuarioDAO usuarioDAO;

    public AlterarSenhaController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }


    public boolean validarSenha(String senhaAntiga, String novaSenha, String confirmaSenha) {

        Usuario usuario = AutenticacaoController.getAutenticado();

        if (usuario.getSenha().equals(senhaAntiga)) {

            if (novaSenha.equals(confirmaSenha)) {
                usuario.setSenha(novaSenha);

                try {
                    usuarioDAO.atualizarUsuario(usuario);
                }catch (Exception e){
                    System.out.println("Erro ao atualizar senha: " + e.getMessage());
                }

            } else {
                return false;
            }

            /*
               Ao criar a tela de alterar senha, setar uma mensagem de "senhas coincidem"
               e alterar sua visibilidade pelo retorno dessa função aqui
             */

        }
        return true;
    }

}
