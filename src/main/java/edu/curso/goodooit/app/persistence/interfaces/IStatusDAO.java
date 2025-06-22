package edu.curso.goodooit.app.persistence.interfaces;

import edu.curso.goodooit.app.model.Status;

import java.sql.SQLException;
import java.util.List;

public interface IStatusDAO {
    public List<Status> buscarTodosStatus() throws SQLException;

    public Status buscarStatusId(Integer idStatus) throws SQLException;
}
