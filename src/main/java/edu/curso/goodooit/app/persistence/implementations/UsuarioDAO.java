package edu.curso.goodooit.app.persistence.implementations;

import edu.curso.goodooit.app.model.Usuario;
import edu.curso.goodooit.app.persistence.interfaces.IUsuarioDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IUsuarioDAO {
    private DataBaseConnection dbConn;

    public UsuarioDAO(DataBaseConnection dbConn) {
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
    public List<Usuario> buscarTodosUsuarios() throws SQLException {
        String sql = "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(construirUsuario(rs));
            }
        }
        return usuarios;
    }

    @Override
    public Integer registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, sobrenome, login, senha, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSobrenome());
            stmt.setString(3, usuario.getLogin());
            stmt.setString(4, usuario.getSenha());
            stmt.setString(5, usuario.getEmail());
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
    public void atualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, sobrenome = ?, senha = ?, email = ? WHERE ID = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSobrenome());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getEmail());
            stmt.setInt(5,usuario.getID());
            int linhas = stmt.executeUpdate();
            System.out.println("Linhas afetadas: " + linhas);
        }
    }

    @Override
    public void excluirUsuario(Usuario usuario) throws SQLException {
        String sql = "DELETE FROM usuario WHERE ID = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, usuario.getID());
            int linhas = stmt.executeUpdate();
            System.out.println("Linhas afetadas: " + linhas);
        }
    }

    @Override
    public Usuario buscarUsuarioID(Integer id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE ID = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construirUsuario(rs);
            }
        }
        return null;
    }

    @Override
    public Usuario buscarUsuarioLogin(String login) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE login = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construirUsuario(rs);
            }
        }
        return null;
    }

    @Override
    public Usuario buscarUsuarioNomeCompleto(String nomeCompleto) throws SQLException {
        String[] nomeSplit = nomeCompleto.split(" ");
        String sql = "SELECT * FROM usuario WHERE nome LIKE ? AND sobrenome LIKE ?" ;
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, "%" + nomeSplit[0] + "%");
            stmt.setString(2, "%" + nomeSplit[1] + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construirUsuario(rs);
            }
        }
        return null;
    }


    @Override
    public Integer validarSenha(String login, String senha) throws SQLException {
        //metodo retorna um ID de usuario caso o login e senha estejam corretos
        //usado no service de autenticacao
        String sql = """
                    SELECT
                    	CASE WHEN u.senha = ?
                            THEN ID
                    		ELSE NULL
                    	END AS ID
                    FROM usuario u
                    WHERE u.login = ?
                """;
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, senha);
            stmt.setString(2, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        }
        return null;
    }

}
