package edu.curso.goodooit.app.persistence.interfaces;

import edu.curso.goodooit.app.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface IUsuarioDAO {

    public List<Usuario> buscarTodosUsuarios() throws SQLException;

    public Integer registrarUsuario(Usuario usuario) throws SQLException;

    public void atualizarUsuario(Usuario usuario) throws SQLException;

    public void excluirUsuario(Usuario usuario) throws SQLException;

    public Usuario buscarUsuarioID(Integer id) throws SQLException;

    public Usuario buscarUsuarioLogin(String login) throws SQLException;

    public Usuario buscarUsuarioNomeCompleto(String nomeCompleto) throws SQLException;

    public Integer validarSenha(String login, String senha) throws SQLException;

}
