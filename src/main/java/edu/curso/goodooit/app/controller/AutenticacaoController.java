package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Usuario;

public class AutenticacaoController {

    private static Usuario autenticado;

    public static Usuario getAutenticado() {
        return autenticado;
    }

    public static void setAutenticado(Usuario autenticado) {
        AutenticacaoController.autenticado = autenticado;
    }
}
