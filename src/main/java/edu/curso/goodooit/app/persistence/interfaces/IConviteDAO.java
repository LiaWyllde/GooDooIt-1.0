package edu.curso.goodooit.app.persistence.interfaces;

import edu.curso.goodooit.app.model.Convite;

import java.sql.SQLException;
import java.util.List;

public interface IConviteDAO {
    public List<Convite> buscarTodosConvites() throws SQLException;

    public List<Convite> buscarConviteIdRemetente(Integer idRemetente) throws SQLException;

    public List<Convite> buscarConviteIdDestinatario(Integer idDestinatario) throws SQLException;

    public List<Convite> buscarConviteIdProjeto(Integer idProjeto) throws SQLException;

    public Convite buscarConvite(Integer idProjeto, Integer idRemetente, Integer idDestinatario) throws SQLException;

    public void registrarConvite(Convite convite) throws SQLException;

    public void excluirConvite(Convite convite) throws SQLException;

    public void atualizarConvite(Convite convite) throws SQLException;
}
