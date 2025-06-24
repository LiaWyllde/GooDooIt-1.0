package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Convite;
import edu.curso.goodooit.app.model.Projeto;
import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.implementations.ConviteDAO;
import edu.curso.goodooit.app.persistence.implementations.EquipeDAO;
import edu.curso.goodooit.app.persistence.implementations.ProjetoDAO;
import edu.curso.goodooit.app.persistence.implementations.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConviteController {

    private final ConviteDAO conviteDAO;
    private final UsuarioDAO usuarioDAO;
    private final ProjetoDAO projetoDAO;
    private final EquipeDAO equipeDAO;

    private final Usuario autenticado = AutenticacaoController.getAutenticado();

    public ConviteController(ConviteDAO conviteDAO, UsuarioDAO usuarioDAO, ProjetoDAO projetoDAO, EquipeDAO equipeDAO) {
        this.conviteDAO = conviteDAO;
        this.usuarioDAO = usuarioDAO;
        this.projetoDAO = projetoDAO;
        this.equipeDAO = equipeDAO;
    }

    public ObservableList<Convite> convitesRecebidos() {
        ObservableList<Convite> convitesObservable = FXCollections.observableArrayList();
        try {
            List<Convite> convites = conviteDAO.buscarConviteIdDestinatario(autenticado.getID());
            convitesObservable.addAll(convites);
        } catch (Exception e) {
            System.out.println("Erro ao buscar convites: " + e.getMessage());
        }
        return convitesObservable;
    }

    public Usuario buscarRemetente(Convite convite) {
        try {
            return usuarioDAO.buscarUsuarioID(convite.getRemetenteID());
        } catch (Exception e) {
            System.out.println("Erro ao buscar remetente: " + e.getMessage());
            return null;
        }
    }

    public Projeto buscarProjeto(Convite convite) {
        try {
            return projetoDAO.buscarProjetoId(convite.getProjetoID());
        } catch (Exception e) {
            System.out.println("Erro ao buscar projeto: " + e.getMessage());
            return null;
        }
    }

    private boolean usuarioEhColaborador(Usuario usuario, Projeto projeto) {
        try {
            List<Usuario> usuariosProjeto = equipeDAO.buscarUsuariosPorProjeto(projeto.getID());
            return usuariosProjeto.contains(usuario);
        } catch (Exception e) {
            System.out.println("Erro ao verificar equipe: " + e.getMessage());
            return false;
        }
    }

    public Integer convidarUsuarioParaProjeto(String usernameDestinatario, Integer IDProjeto) {
        try {
            Projeto projeto = projetoDAO.buscarProjetoId(IDProjeto);
            if (projeto == null) return 2;
            if (!Objects.equals(projeto.getLiderID(), autenticado.getID())) return 1;

            Usuario destinatario = usuarioDAO.buscarUsuarioLogin(usernameDestinatario);
            if (destinatario == null) return 3;

            boolean colaborador = usuarioEhColaborador(destinatario, projeto);
            if (!colaborador) {
                Convite convite = new Convite();
                convite.setDestinatarioID(destinatario.getID());
                convite.setProjetoID(IDProjeto);
                convite.setRemetenteID(autenticado.getID());
                conviteDAO.registrarConvite(convite);
            }

        } catch (Exception e) {
            System.out.println("Erro ao convidar usuário: " + e.getMessage());
            return 4;
        }

        return 5;
    }

    public void recusarConvite(Integer idProjeto, Integer idRemetente, Integer idDestinatario) {
        try {
            Convite c = conviteDAO.buscarConvite(idProjeto, idRemetente, idDestinatario);
            conviteDAO.excluirConvite(c);
        } catch (Exception e) {
            System.out.println("Erro ao recusar convite: " + e.getMessage());
        }
    }

    public void aceitarConvite(Integer idProjeto, Integer idRemetente, Integer idDestinatario) {
        try {
            Convite c = conviteDAO.buscarConvite(idProjeto, idRemetente, idDestinatario);
            conviteDAO.excluirConvite(c);
            equipeDAO.adicionarMembroProjeto(idProjeto, idDestinatario);
        } catch (Exception e) {
            System.out.println("Erro ao aceitar convite: " + e.getMessage());
        }
    }
}
