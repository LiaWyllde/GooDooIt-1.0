package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.implementations.UsuarioDAO;
import edu.curso.goodooit.app.persistence.interfaces.IUsuarioDAO;


public class AlterarDadosUsuarioController {

    private final UsuarioDAO usuarioDAO;

    public AlterarDadosUsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    private Usuario autenticado = AutenticacaoController.getAutenticado();

    public boolean alterarDadosUsuario(String nome, String sobrenome, String email) {

        if (nome == null || sobrenome == null || email == null) {
            return false;
        } else {

            autenticado.setNome(nome);
            autenticado.setSobrenome(sobrenome);
            autenticado.setEmail(email);

            try {
                usuarioDAO.atualizarUsuario(autenticado);
            } catch (Exception e) {
                System.out.println("Erro ao autenticar" + e.getMessage());
            }


        }
        return true;
    }

    /*
        Ao criar a tela de atualizar os dados, incluir uma mensagem de confirmação com a
        visibilidade setada como false para receber o retorno desse metodo e também
        uma mensagem de "os campos não podem ser vazios"
        TODO: trocar o retorno do método para boolean
     */

}
