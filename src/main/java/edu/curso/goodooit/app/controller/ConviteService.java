package edu.curso.goodooit.app.usecase;

import edu.curso.goodooit.domain.model.Convite;
import edu.curso.goodooit.infra.repository.ConviteDAO;
import edu.curso.goodooit.infra.repository.EquipeDAO;

public class ConviteService {
    private final ConviteDAO conviteDAO;
    private final EquipeDAO equipeDAO;

    public ConviteService(ConviteDAO conviteDAO, EquipeDAO equipeDAO) {
        this.conviteDAO = conviteDAO;
        this.equipeDAO = equipeDAO;
    }

    public Convite convidarUsuario(Integer idProjeto, Integer idRemetente, Integer idDestinatario) throws Exception {
        Convite c = new Convite(idRemetente, idProjeto, idDestinatario);
        conviteDAO.registrarConvite(c);

        return c;
    }

    public void recusarConvite(Integer idProjeto, Integer idRemetente, Integer idDestinatario) throws Exception {
        Convite c = conviteDAO.buscarConvite(idProjeto, idRemetente, idDestinatario);
        conviteDAO.excluirConvite(c);
    }

    public void aceitarConvite(Integer idProjeto, Integer idRemetente, Integer idDestinatario) throws Exception {
        Convite c = conviteDAO.buscarConvite(idProjeto, idRemetente, idDestinatario);
        conviteDAO.excluirConvite(c);
        equipeDAO.adicionarMembroProjeto(idProjeto, idDestinatario);
    }
}
