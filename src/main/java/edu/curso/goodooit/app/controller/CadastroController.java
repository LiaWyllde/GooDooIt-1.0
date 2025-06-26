package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.implementations.UsuarioDAO;

public class CadastroController {

    private final UsuarioDAO usuarioDAO ;

    public CadastroController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /*
        0 - Passou pela validação de um sub metodo
        1 - Campos vazios
        2 - Usuário já utilizado
        3 - Senha não confere
        4 - Sucesso em cadastrar
     */

    public Integer cadastrar (String login, String nome, String sobrenome, String email, String senha, String confirmaSenha) {

        if (nome.isBlank() || sobrenome.isBlank() || email.isBlank() || login.isBlank() || senha.isBlank() || confirmaSenha.isBlank()) {
            return 1;
        }

        try{
            Usuario buscar = usuarioDAO.buscarUsuarioLogin(login);
            if (buscar != null) {
                return 2;
            }
        } catch (Exception e) {
            System.out.println("Usuário não existe na database " + e.getMessage());
        }

        if (!senha.equals(confirmaSenha)) {
            return 3;
        }

        Usuario usuario = new Usuario(nome, sobrenome, login, senha, email);

        try {
            Integer id = usuarioDAO.registrarUsuario(usuario);
            usuario.setID(id);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar, SQL quis assim: " + e.getMessage());
        }
        return 4;
    }

    //Cntrole de visibilidade de mensagem de "cadastro concluído" e "campos obrigatórios"

    public boolean excluirConta(){
        Usuario autenticado = AutenticacaoController.getAutenticado();
        if (autenticado != null) {
            try {
                usuarioDAO.excluirUsuario(autenticado);
                AutenticacaoController.setAutenticado(null);
                System.out.println("Conta excluída com sucesso.");
                return true;
            } catch (Exception e) {
                System.out.println("Erro ao excluir conta: " + e.getMessage());
            }
        }
        return false;
    }
}
