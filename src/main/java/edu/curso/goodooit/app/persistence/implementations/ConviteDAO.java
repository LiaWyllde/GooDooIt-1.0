package edu.curso.goodooit.app.persistence.implementations;

import edu.curso.goodooit.app.model.Convite;
import edu.curso.goodooit.app.persistence.interfaces.IConviteDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConviteDAO implements IConviteDAO {
    private final DataBaseConnection dbConn;

    public ConviteDAO(DataBaseConnection dbConn) {
        this.dbConn = dbConn;
    }

    private Convite construirConvite(ResultSet rs) throws SQLException {
        return new Convite(
                rs.getInt("remetenteID"),
                rs.getInt("projetoID"),
                rs.getInt("destinatarioID")
        );
    }

    @Override
    public List<Convite> buscarTodosConvites() throws SQLException {
        String sql = "SELECT * FROM convite";
        List<Convite> lista = new ArrayList<>();
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(construirConvite(rs));
            }
        }
        return lista;
    }

    @Override
    public List<Convite> buscarConviteIdRemetente(Integer idRemetente) throws SQLException {
        String sql = "SELECT * FROM convite WHERE remetenteID = ?";
        return comporLista(idRemetente, sql);
    }

    private List<Convite> comporLista(Integer id, String sql) throws SQLException {
        List<Convite> lista = new ArrayList<>();
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(construirConvite(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public List<Convite> buscarConviteIdDestinatario(Integer idDestinatario) throws SQLException {
        String sql = "SELECT * FROM convite WHERE destinatarioID = ?";
        return comporLista(idDestinatario, sql);
    }

    @Override
    public List<Convite> buscarConviteIdProjeto(Integer idProjeto) throws SQLException {
        String sql = "SELECT * FROM convite WHERE projetoID = ?";
        return comporLista(idProjeto, sql);
    }

    @Override
    public Convite buscarConvite(Integer idProjeto, Integer idRemetente, Integer idDestinatario) throws SQLException {
        String sql = "SELECT * FROM convite WHERE projetoID = ? AND remetenteID = ? AND destinatarioID = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProjeto);
            ps.setInt(2, idRemetente);
            ps.setInt(3, idDestinatario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirConvite(rs);
                }
            }
        }
        return null;
    }

    @Override
    public void registrarConvite(Convite convite) throws SQLException {
        String sql = "INSERT INTO convite (remetenteID, projetoID, destinatarioID) VALUES (?, ?, ?)";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, convite.getRemetenteID());
            ps.setInt(2, convite.getProjetoID());
            ps.setInt(3, convite.getDestinatarioID());
            int linhas = ps.executeUpdate();
            System.out.println("Linhas afetadas: " + linhas);
        }
    }

    @Override
    public void excluirConvite(Convite convite) throws SQLException {
        String sql = "DELETE FROM convite WHERE remetenteID = ? AND projetoID = ? AND destinatarioID = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, convite.getRemetenteID());
            ps.setInt(2, convite.getProjetoID());
            ps.setInt(3, convite.getDestinatarioID());
            int linhas = ps.executeUpdate();
            System.out.println("Linhas afetadas: " + linhas);
        }
    }

    @Override
    public void atualizarConvite(Convite convite) throws SQLException {
        String sql = "UPDATE convite SET projetoID = ?, destinatarioID = ? WHERE remetenteID = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, convite.getProjetoID());
            ps.setInt(2, convite.getDestinatarioID());
            ps.setInt(3, convite.getRemetenteID());
            int linhas = ps.executeUpdate();
            System.out.println("Linhas afetadas: " + linhas);
        }
    }
}
