package edu.curso.goodooit.app.persistence.interfaces;

import edu.curso.goodooit.app.model.Projeto;

import java.sql.SQLException;
import java.util.List;

public interface IProjetoDAO {
    public List<Projeto> buscarTodosProjetos() throws SQLException;

    public Projeto buscarProjetoId(Integer id) throws SQLException;

    public List<Projeto> buscarProjetoUsuarioLider(Integer idUsuario) throws SQLException;

    public List<Projeto> buscarProjetoUsuarioColaborador(Integer idUsuario) throws SQLException;

    public Projeto buscarProjetoNome(String nome) throws SQLException;

    public Integer registrarProjeto(Projeto projeto) throws SQLException;

    public void excluirProjeto(Projeto projeto) throws SQLException;

    public void atualizarProjeto(Projeto projeto) throws SQLException;

}
