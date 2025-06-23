package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Convite;
import edu.curso.goodooit.app.model.Projeto;
import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.implementations.ConviteDAO;
import edu.curso.goodooit.app.persistence.implementations.EquipeDAO;
import edu.curso.goodooit.app.persistence.implementations.ProjetoDAO;
import edu.curso.goodooit.app.persistence.implementations.UsuarioDAO;
import edu.curso.goodooit.app.persistence.interfaces.IProjetoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConviteController {

    private final ConviteDAO conviteDAO;
    private final UsuarioDAO usuarioDAO;
    private final ProjetoDAO projetoDAO;
    private final EquipeDAO equipeDAO;

    public ConviteController(ConviteDAO conviteDAO, UsuarioDAO usuarioDAO, ProjetoDAO projetoDAO, EquipeDAO equipeDAO) {
        this.conviteDAO = conviteDAO;
        this.usuarioDAO = usuarioDAO;
        this.projetoDAO = projetoDAO;
        this.equipeDAO = equipeDAO;
    }

    private Usuario autenticado = AutenticacaoController.getAutenticado();

    public ObservableList<Convite> convitesRecebidos() {

        ObservableList<Convite> convitesObservable = FXCollections.observableArrayList();
        List<Convite> convites = new ArrayList<>();

        try {
            convites = conviteDAO.buscarConviteIdDestinatario(autenticado.getID());
        } catch (Exception e) {
            System.out.println("Erro ao buscar convites, SQL quis assim: " + e.getMessage());
        }

        convitesObservable.addAll(convites);

        return convitesObservable;

        /*
            Adicionar tratativa na tela de convites para caso seja null, exibir
            "não há convites no momento"
         */

    }

    private boolean usuarioEhColaborador(Usuario usuario, Projeto projeto) {

        try {
            List<Usuario> usuariosProjeto = equipeDAO.buscarUsuariosPorProjeto(projeto.getID());
            if (!usuariosProjeto.contains(usuario)) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuarios, o SQL quis assim: " + e.getMessage());
        }
        return true;
    }


    public Integer convidarUsuarioParaProjeto(String usernameDestinatario, Integer IDProjeto) {

        try {

            Projeto projeto = projetoDAO.buscarProjetoId(IDProjeto);

            if (projeto != null) {
                if (!Objects.equals(projeto.getLiderID(), autenticado.getID())) {
                    System.out.println("O usuario autenticado não é dono do projeto.");
                    return 1;
                }

            } else {
                System.out.println("Nenhum projeto encontrado.");
                return 2;
            }

            Usuario destinatario = usuarioDAO.buscarUsuarioLogin(usernameDestinatario);

            if (destinatario != null) {

                boolean colaborador = usuarioEhColaborador(destinatario, projeto);

                if (!colaborador) {
                    Convite convite = new Convite();
                    convite.setDestinatarioID(destinatario.getID());
                    convite.setProjetoID(IDProjeto);
                    convite.setRemetenteID(autenticado.getID());

                    conviteDAO.registrarConvite(convite);
                }

            } else {
                System.out.println("Nenhum usuario encontrado");
                return 3;
            }

        } catch (Exception e) {
            System.out.println("Erro ao criar convite porque o SQL quis assim: " + e.getMessage());
            return 4;
        }

        return 5;

    }

    // O retorno de Integer é utilizado para selecionar a mensagem que deve aparecer na view

    public void recusarConvite(Integer idProjeto, Integer idRemetente, Integer idDestinatario) {

        try {
            Convite c = conviteDAO.buscarConvite(idProjeto, idRemetente, idDestinatario);
            conviteDAO.excluirConvite(c);

        } catch (Exception e) {
            System.out.println("Erro ao excluir convite porque o SQL quis assim: " + e.getMessage());
        }
    }

    public void aceitarConvite(Integer idProjeto, Integer idRemetente, Integer idDestinatario) {

        try {
            Convite c = conviteDAO.buscarConvite(idProjeto, idRemetente, idDestinatario);
            conviteDAO.excluirConvite(c);
            equipeDAO.adicionarMembroProjeto(idProjeto, idDestinatario);
        } catch (Exception e) {
            System.out.println("Erro ao aceitar convite porque o SQL quis assim: " + e.getMessage());
        }

    }

}
