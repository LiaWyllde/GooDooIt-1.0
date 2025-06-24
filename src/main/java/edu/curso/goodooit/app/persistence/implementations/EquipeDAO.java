package edu.curso.goodooit.app.persistence.implementations;

import edu.curso.goodooit.app.model.Equipe;
import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.interfaces.IEquipeDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeDAO implements IEquipeDAO {
    private final DataBaseConnection dbConn;

    public EquipeDAO(DataBaseConnection dbConn) {
        this.dbConn = dbConn;
    }

    private Usuario construirUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("ID"),
                rs.getString("nome"),
                rs.getString("sobrenome"),
                rs.getString("login"),
                rs.getString("senha"),
                rs.getString("email")
        );
    }

    @Override
    public List<Usuario> buscarUsuariosPorLista(Integer idLista) throws SQLException {
        String sql = """
                    SELECT u.*
                      FROM usuario u
                      INNER JOIN Usuario_Lista lc ON u.ID = lc.usuario_id
                     WHERE lc.lista_id = ?
                """;
        return comporLista(idLista, sql);
    }

    @Override
    public List<Equipe> buscarEquipesPorUsuario(Integer idUsuario) throws SQLException {
        // Primeiro busca todos os IDs de lista em que o usuário está
        String sql = "SELECT listaID FROM Usuario_Lista WHERE UsuarioID = ?";
        List<Equipe> equipes = new ArrayList<>();

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer idLista = rs.getInt("lista_id");
                    // Para cada lista, busca seus membros e monta a Equipe
                    List<Usuario> membros = buscarUsuariosPorLista(idLista);
                    equipes.add(new Equipe(membros));
                }
            }
        }
        return equipes;
    }

    @Override
    public Integer contarMembrosProjeto(Integer idProjeto) throws SQLException {
        String sql = """
                    SELECT COUNT(up.UsuarioID)
                    FROM Usuario_Projeto up
                    INNER JOIN Projeto p
                    ON up.ProjetoID = p.ID
                    WHERE p.ID = ?
                """;
        try (Connection conn = dbConn.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProjeto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    @Override
    public List<Usuario> buscarUsuariosPorProjeto(Integer idProjeto) throws SQLException {
        String sql =
                """
                        SELECT u.*
                        FROM usuario u
                        INNER JOIN usuario_projeto up ON up.usuarioID = u.id
                        WHERE up.projetoID = ?
                        """;
        return comporLista(idProjeto, sql);
    }

    @Override
    public List<Usuario> removerUsuarioProjeto(Integer idProjeto, Integer idUsuario) throws SQLException {
        String sql = "DELETE FROM Usuario_Projeto WHERE ProjetoID = ? AND UsuarioID = ?";
        List<Usuario> lista = new ArrayList<>();
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProjeto);
            ps.setInt(2, idUsuario);
            int linhas = ps.executeUpdate();
            System.out.println("Linhas afetadas: " + linhas);
            lista = buscarUsuariosPorProjeto(idUsuario);
        }
        return lista;
    }

    @Override
    public List<Usuario> adicionarMembroProjeto(Integer idProjeto, Integer idUsuario) throws SQLException {
        String sql = "INSERT INTO Usuario_Projeto VALUES(?,?)";
        List<Usuario> lista = new ArrayList<>();
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idProjeto);
            int linhas = ps.executeUpdate();
            System.out.println("Linhas afetadas: " + linhas);
            lista = buscarUsuariosPorProjeto(idUsuario);
        }
        return lista;
    }

    private List<Usuario> comporLista(Integer idProjeto, String sql) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProjeto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(construirUsuario(rs));
                }
            }
        }
        return usuarios;
    }


}

