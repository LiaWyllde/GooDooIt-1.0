package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.implementations.UsuarioDAO;

import java.sql.SQLException;

public class AlterarSenhaController {

    private final UsuarioDAO usuarioDAO;

    public AlterarSenhaController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /*
        1 - Senha antiga está incorreta
        2 - Senhas não coincidem
        3 - Alterado com sucesso
     */

    public Integer validarSenha(String senhaAntiga, String novaSenha, String confirmaSenha) {

        Usuario usuario = AutenticacaoController.getAutenticado();

        if (usuario.getSenha().equals(senhaAntiga)) {

            if (novaSenha.equals(confirmaSenha)) {
                usuario.setSenha(novaSenha);

                try {
                    usuarioDAO.atualizarUsuario(usuario);
                    return 3;
                }catch (Exception e){
                    System.out.println("Erro ao atualizar senha: " + e.getMessage());
                }

            } else {
                return 2;
            }

            /*
               Ao criar a tela de alterar senha, setar uma mensagem de "senhas coincidem"
               e alterar sua visibilidade pelo retorno dessa função aqui
             */

        }
        return 1;
    }

}
