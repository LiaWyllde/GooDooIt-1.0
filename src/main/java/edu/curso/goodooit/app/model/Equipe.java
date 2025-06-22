package edu.curso.goodooit.app.model;

import java.util.List;

public class Equipe {
    List<Usuario> usuarios;

    public Equipe(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
