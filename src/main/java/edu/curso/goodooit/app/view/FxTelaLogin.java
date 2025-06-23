package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.AllControllerRegistry;
import edu.curso.goodooit.app.controller.LoginController;
import edu.curso.goodooit.app.model.Usuario;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class FxTelaLogin extends Application {

    private static LoginController loginController;

    public static void setLoginController(LoginController lc) {
        loginController = lc;
    }

    @Override
    public void start(Stage stage) {
        // Logo
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/images/LogoComTexto.png")));
        logo.setFitHeight(180);
        logo.setPreserveRatio(true);

        // Campos
        TextField usernameField = new TextField();
        usernameField.setPromptText("nome de usuário");
        estilizarCampo(usernameField);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("senha");
        estilizarCampo(passwordField);

        // Mensagem de erro
        Text mensagemErro = new Text("Usuário ou senha inválido!");
        mensagemErro.setFill(Color.FIREBRICK);
        mensagemErro.setFont(Font.font("Courier New", 18));
        mensagemErro.setVisible(false);

        // Botões
        Button btnEntrar = new Button("Entrar");
        Button btnCadastrar = new Button("Cadastrar");
        estilizarBotao(btnEntrar);
        estilizarBotao(btnCadastrar);

        HBox hboxBotoes = new HBox(20, btnEntrar, btnCadastrar);
        hboxBotoes.setAlignment(Pos.CENTER);

        btnEntrar.setOnAction(event -> {
            try {
                Usuario u = loginController.efetuarLogin(
                        usernameField.getText().trim().toLowerCase(),
                        passwordField.getText().trim()
                );
                if (u == null) {
                    mensagemErro.setVisible(true);
                } else {
                    proximaTela(stage);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        btnCadastrar.setOnAction(event -> {
            FXTelaCadastro cadastro = new FXTelaCadastro();
            FXTelaCadastro.setCadastroController(AllControllerRegistry.getInstance().getCadastroController());
            cadastro.start(stage);
        });

        VBox vbox = new VBox(20, logo, usernameField, passwordField, mensagemErro, hboxBotoes);
        vbox.setPadding(new Insets(50));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #d9d9d9;");

        Scene scene = new Scene(vbox, 1200, 650, Color.LIGHTGRAY);
        stage.setTitle("Login - GooDoolt");
        stage.setScene(scene);
        stage.show();
    }

    private void estilizarCampo(TextField campo) {
        campo.setMaxWidth(500);
        campo.setStyle(
                "-fx-font-family: 'Courier New';" +
                        "-fx-font-size: 18px;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-background-color: white;" +
                        "-fx-prompt-text-fill: #999999;" +
                        "-fx-padding: 10px;"
        );
    }

    private void estilizarBotao(Button botao) {
        botao.setPrefWidth(200);
        botao.setPrefHeight(45);
        botao.setStyle(
                "-fx-background-color: #a88ddb;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-family: 'Courier New';" +
                        "-fx-background-radius: 15px;" +
                        "-fx-cursor: hand;"
        );
    }

    private void proximaTela(Stage primaryStage) {
        FXMeusProjetos projetos = new FXMeusProjetos();
        FXMeusProjetos.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        projetos.start(primaryStage);
    }
}
