package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.ControllerRegistry;
import edu.curso.goodooit.app.controller.LoginController;
import edu.curso.goodooit.app.persistence.implementations.DataBaseConnection;
import edu.curso.goodooit.app.persistence.implementations.UsuarioDAO;
import javafx.application.Application;

public class Main {
    public static void main(String[] args){
        String password = "Nick159642@";
        DataBaseConnection dbConn = new DataBaseConnection("GooDooIt01", "sa", password, "localhost", 1433);
        UsuarioDAO uDAO = new UsuarioDAO(dbConn);

        try{
            ControllerRegistry.initialize(dbConn);
            LoginController lc = new LoginController(uDAO);
            FxTelaLogin telaLogin = new FxTelaLogin();
            FxTelaLogin.setLoginController(lc);
            Application.launch(telaLogin.getClass(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
