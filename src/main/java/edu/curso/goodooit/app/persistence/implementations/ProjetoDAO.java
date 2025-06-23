package edu.curso.goodooit.app.persistence.implementations;

import edu.curso.goodooit.app.model.Projeto;
import edu.curso.goodooit.app.persistence.interfaces.IProjetoDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO implements IProjetoDAO {
    private final DataBaseConnection dbConn;

    public ProjetoDAO(DataBaseConnection dbConn) {
        this.dbConn = dbConn;
    }

    private Projeto construirProjeto(ResultSet rs) throws SQLException {
        return new Projeto(
                rs.getInt("ID"),
                rs.getString("nome"),
                rs.getString("descricao"),
                rs.getDate("data_inicio").toLocalDate(),
                rs.getDate("data_fim").toLocalDate(),
                rs.getDate("dataCriacao").toLocalDate(),
                rs.getInt("LiderID"),
                rs.getInt("StatusID")
                );
    }

    @Override
    public List<Projeto> buscarTodosProjetos() throws SQLException {
        String sql = "SELECT * FROM Projeto";
        List<Projeto> lista = new ArrayList<>();
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(construirProjeto(rs));
            }
        }
        return lista;
    }

    @Override
    public Projeto buscarProjetoId(Integer id) throws SQLException {
        String sql = "SELECT * FROM Projeto WHERE ID = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirProjeto(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Projeto> buscarProjetoUsuarioLider(Integer idUsuario) throws SQLException {
        String sql = """
                SELECT p.* 
                FROM Projeto p
                INNER JOIN Usuario u
                ON u.ID = p.LiderID
                WHERE u.ID = ?;
                """;
        List<Projeto> lista = new ArrayList<>();
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(construirProjeto(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public List<Projeto> buscarProjetoUsuarioColaborador(Integer idUsuario) throws SQLException {
        String sql = """
                    SELECT p.*
                      FROM Projeto p
                      INNER JOIN Usuario_Projeto up 
                        ON p.ID = up.projetoID
                     WHERE up.usuarioID = ?
                """;
        List<Projeto> lista = new ArrayList<>();
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(construirProjeto(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public Projeto buscarProjetoNome(String nome) throws SQLException {
        String sql = "SELECT * FROM Projeto WHERE nome = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirProjeto(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Integer registrarProjeto(Projeto projeto) throws SQLException {
        String sql = """
                    INSERT INTO Projeto (
                        nome,
                        descricao,
                        data_inicio,
                        data_fim,
                        dataCriacao,
                        LiderID,
                        StatusID
                    ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setDate(3, Date.valueOf(projeto.getDataInicio()));
            stmt.setDate(4, Date.valueOf(projeto.getDataFim()));
            stmt.setDate(5, Date.valueOf(projeto.getDataCriacao()));
            stmt.setInt(6, projeto.getStatusProjetoID());
            stmt.setInt(7, projeto.getLiderID());

            int linhas = stmt.executeUpdate();
            System.out.println("Linhas afetadas: " + linhas);
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Novo ID não encontrado");
                }
            }
        }
    }

    @Override
    public void atualizarProjeto(Projeto projeto) throws SQLException {
        String sql = """
                    UPDATE Projeto
                       SET nome,
                        descricao,
                        data_inicio,
                        data_fim,
                        dataCricao,
                        StatusID
                     WHERE ID = ?
                """;
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setDate(3, Date.valueOf(projeto.getDataInicio()));
            stmt.setDate(4, Date.valueOf(projeto.getDataFim()));
            stmt.setDate(5, Date.valueOf(projeto.getDataCriacao()));
            stmt.setInt(6, projeto.getStatusProjetoID());
            stmt.setInt(7, projeto.getID());

            int linhas = stmt.executeUpdate();
            System.out.println("Linhas afetadas: " + linhas);
        }
    }

    @Override
    public void excluirProjeto(Projeto projeto) throws SQLException {
        String sql = "DELETE FROM Projeto WHERE ID = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projeto.getID());
            int linhas = stmt.executeUpdate();
            System.out.println("Linhas afetadas: " + linhas);
        }
    }
}
