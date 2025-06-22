package edu.curso.goodooit.ui.view;

import edu.curso.goodooit.app.controller.LoginController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class FxTelaLogin extends Application {

    private LoginController loginController;

    public FxTelaLogin(LoginController loginController) {
        this.loginController = loginController;
    }

    public FxTelaLogin() {

    }
    // Esse construtor vazio só existe para testes da instância da classe antes de implementar a service

    @Override
    public void start(Stage stage) {
        // Imagem do logo
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/images/LogoComTexto.png")));
        logo.setFitHeight(180);
        logo.setPreserveRatio(true);

        // Campo de nome de usuário
        TextField usernameField = new TextField();
        usernameField.setPromptText("nome de usuário");
        estilizarCampo(usernameField);

        // Campo de senha
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("senha");
        estilizarCampo(passwordField);

        // Texto de erro (inicialmente invisível)
        Text mensagemErro = new Text("Usuário ou senha inválido!");
        mensagemErro.setFill(Color.FIREBRICK);
        mensagemErro.setFont(Font.font("Courier New", 18));
        mensagemErro.setVisible(false); // começa invisível

        // Botão de entrar
        Button btnEntrar = new Button("Entrar");
        estilizarBotao(btnEntrar);

        // Se o usuário não estiver logado, mostrar mensagem
        btnEntrar.setOnAction(event -> {
            if (this.loginController == null) {
                mensagemErro.setVisible(true);
                System.out.println("É UM TESTE, ME APAGA!!!");
            } else {
                try {
                    if (!loginController.efetuarLogin(usernameField.textProperty(), passwordField.textProperty())) {
                        mensagemErro.setVisible(true);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Container vertical
        VBox vbox = new VBox(20, logo, usernameField, passwordField, mensagemErro, btnEntrar);
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

    public static void main(String[] args) {
        launch();
    }
}
