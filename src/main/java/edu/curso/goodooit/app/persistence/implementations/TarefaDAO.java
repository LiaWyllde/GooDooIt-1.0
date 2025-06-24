
package edu.curso.goodooit.app.persistence.implementations;

import edu.curso.goodooit.app.model.Tarefa;
import edu.curso.goodooit.app.persistence.interfaces.ITarefaDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {
    private final DataBaseConnection dbConn;

    public TarefaDAO(DataBaseConnection dbConn) {
        this.dbConn = dbConn;
    }


    private Tarefa construirTarefa(ResultSet rs) throws SQLException {
        return new Tarefa(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("descricao"),
                rs.getDate("data_inicio").toLocalDate(),
                rs.getDate("data_fim").toLocalDate(),
                rs.getDate("dataCriacao").toLocalDate(),
                rs.getInt("prioridade"),
                rs.getInt("StatusID"),
                rs.getInt("CriadorID"),
                rs.getObject("ResponsavelID") != null ? rs.getInt("ResponsavelID") : null,
                rs.getInt("ProjetoID")
        );
    }

    @Override
    public List<Tarefa> buscarTodosTarefa() throws SQLException {
        String sql = "SELECT * FROM tarefa";
        List<Tarefa> lista = new ArrayList<>();
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(construirTarefa(rs));
            }
        }
        return lista;
    }

    @Override
    public Tarefa buscarTarefaId(Integer id) throws SQLException {
        String sql = "SELECT * FROM tarefa WHERE id = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirTarefa(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Tarefa buscarTarefaNome(String nome) throws SQLException {
        String sql = "SELECT * FROM tarefa WHERE nome LIKE ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirTarefa(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Tarefa> buscarTarefaIdResponsavel(Integer idResponsavel) throws SQLException {
        String sql = """
                    SELECT t.*
                    FROM Tarefa t
                    INNER JOIN Usuario u
                    ON u.ID = t.ResponsavelID
                    WHERE u.ID = ?
                """;
        return comporLista(idResponsavel, sql);
    }

    @Override
    public List<Tarefa> buscarTarefaIdResponsavelProjeto(Integer idResponsavel, Integer idProjeto) throws SQLException {
        String sql = """
                    SELECT t.*
                    FROM Tarefa t
                    INNER JOIN Usuario u
                    ON u.ID = t.ResponsavelID
                    WHERE u.ID = ? AND t.ProjetoID = ?
                """;
        List<Tarefa> lista = new ArrayList<>();
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idResponsavel);
            stmt.setInt(2, idProjeto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(construirTarefa(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public List<Tarefa> buscarTarefaIdCriador(Integer idCriador) throws SQLException {
        String sql = "SELECT * FROM tarefa WHERE CriadorID = ?";
        return comporLista(idCriador, sql);
    }

    @Override
    public List<Tarefa> buscarTarefaPrioridade(int prioridade) throws SQLException {
        String sql = "SELECT * FROM tarefa WHERE prioridade = ?";
        return comporLista(prioridade, sql);
    }

    @Override
    public Integer contarTarefasCompletasProjetoId(Integer idProjeto) throws SQLException {
        String sql = """
                SELECT COUNT(t.StatusID)
                FROM Tarefa t
                WHERE t.StatusID = 3 AND t.ProjetoID = ?;
                """;
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProjeto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return null;
    }

    @Override
    public Integer contarTarefasIncompletasProjetoId(Integer idProjeto) throws SQLException {
        String sql = """
                SELECT COUNT(t.StatusID)
                FROM Tarefa t
                WHERE t.StatusID != 3 AND t.ProjetoID = ?;
                """;
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProjeto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return null;
    }

    @Override
    public Integer registrarTarefa(Tarefa tarefa) throws SQLException {
        String sql = """
                            INSERT INTO tarefa (
                                    nome,
                                    descricao,
                                    data_inicio,
                                    data_fim,
                                    dataCriacao,
                                    prioridade,
                                    CriadorID,
                                    StatusID,
                                    ResponsavelID,
                                    ProjetoID)
                            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tarefa.getNome());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setDate(3, Date.valueOf(tarefa.getDataInicio()));
            stmt.setDate(4, Date.valueOf(tarefa.getDataFim()));
            stmt.setDate(5, Date.valueOf(tarefa.getDataCriacao()));
            stmt.setInt(6, tarefa.getPrioridade());
            stmt.setInt(7, tarefa.getCriadorID());
            stmt.setInt(8, tarefa.getStatusTarefaID());

            if (tarefa.getResponsavelID() != null)
                stmt.setInt(9, tarefa.getResponsavelID());
            else
                stmt.setNull(9, Types.INTEGER);

            stmt.setInt(10, tarefa.getProjetoID());

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
    public void excluirTarefa(Tarefa tarefa) throws SQLException {
        String sql = "DELETE FROM tarefa WHERE id = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tarefa.getID());
            int linhas = stmt.executeUpdate();
            System.out.println("Linhas afetadas: " + linhas);
        }
    }

    @Override
    public void atualizarTarefa(Tarefa tarefa) throws SQLException {
        String sql = """
                UPDATE tarefa SET
                        nome = ?,
                        descricao = ?,
                        data_inicio = ?,data_fim = ?,
                        dataCriacao = ?,
                        prioridade = ?,
                        CriadorID = ?,
                        StatusID = ?,
                        ResponsavelID = ?,
                        ProjetoID = ?
                        WHERE id = ?
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tarefa.getNome());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setDate(3, Date.valueOf(tarefa.getDataInicio()));
            stmt.setDate(4, Date.valueOf(tarefa.getDataFim()));
            stmt.setDate(5, Date.valueOf(tarefa.getDataCriacao()));
            stmt.setInt(6, tarefa.getPrioridade());
            stmt.setInt(7, tarefa.getCriadorID());
            stmt.setInt(8, tarefa.getStatusTarefaID());

            if (tarefa.getResponsavelID() != null)
                stmt.setInt(9, tarefa.getResponsavelID());
            else
                stmt.setNull(9, Types.INTEGER);

            stmt.setInt(10, tarefa.getProjetoID());
            stmt.setInt(11, tarefa.getID());

            int linhas = stmt.executeUpdate();
            System.out.println("Linhas afetadas: " + linhas);
        }
    }

    @Override
    public List<Tarefa> buscarTarefasProjetoId(Integer idProjeto) throws SQLException {
        String sql = "SELECT * FROM  tarefa WHERE projetoID = ?";
        return comporLista(idProjeto, sql);
    }

    private List<Tarefa> comporLista(Integer id, String sql) throws SQLException {
        List<Tarefa> lista = new ArrayList<>();
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(construirTarefa(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public Integer contarTarefaProjetoId(Integer idProjeto) throws SQLException {
        String sql = """
                SELECT COUNT(t.ID)
                FROM Tarefa t
                WHERE t.ProjetoID = ?;
                """;
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProjeto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return null;
    }


}
