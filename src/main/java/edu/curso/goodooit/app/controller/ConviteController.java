package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Convite;
import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.implementations.ConviteDAO;
import edu.curso.goodooit.app.persistence.implementations.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConviteController {

    private final ConviteDAO conviteDAO;
    private final UsuarioDAO usuarioDAO;

    public ConviteController(ConviteDAO conviteDAO, UsuarioDAO usuarioDAO) {
        this.conviteDAO = conviteDAO;
        this.usuarioDAO = usuarioDAO;
    }

    private Usuario autenticado = AutenticacaoController.getAutenticado();

    public ObservableList<Convite> convitesRecebidos() {

        ObservableList<Convite> convitesObservable = FXCollections.observableArrayList();
        List<Convite> convites = new ArrayList<>();

        try {
            convites = conviteDAO.buscarConviteIdDestinatario(autenticado.getID());
        } catch (Exception e) {
            System.out.println("Erro ao buscar convites: " + e.getMessage());
        }

        convitesObservable.addAll(convites);

        return convitesObservable;

        /*
            Adicionar tratativa na tela de convites para caso seja null, exibir
            "não há convites no momento"
         */

    }

    public boolean convidarUsuarioParaProjeto(String usernameDestinatario, Integer IDProjeto) {

        try {

            Usuario destinatario = usuarioDAO.buscarUsuarioLogin(usernameDestinatario);

            if (destinatario != null) {
                Integer IDDestinatario = destinatario.getID();

                Convite convite = new Convite();
                convite.setDestinatarioID(IDDestinatario);
                convite.setProjetoID(IDProjeto);
                convite.setRemetenteID(autenticado.getID());

                conviteDAO.registrarConvite(convite);
            } else  {
                System.out.println("Nenhum usuario encontrado");
            }

        } catch (Exception e) {
            System.out.println("Erro ao criar convite: " + e.getMessage());
            return false;
        }

        return true;
    }

    //TODO: Finalizar respostas de convite

}
