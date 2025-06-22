package edu.curso.goodooit.app.persistence.implementations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private String databaseName;
    private String user;
    private String password;
    private String host;
    private int port;

    public DataBaseConnection(String databaseName, String user, String password, String host, int port) {
        this.databaseName = databaseName;
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = DriverManager.getConnection(String.format("jdbc:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s;encrypt=false",
                                                                host, port, databaseName, user, password));
        return connection;
    }
}
