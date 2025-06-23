package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.CadastroController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXTelaCadastro extends Application {


    private static CadastroController cadastroController;
    private Text mensagem;

    public static void setCadastroController(CadastroController cadastroController) {
        FXTelaCadastro.cadastroController = cadastroController;
    }

    @Override
    public void start(Stage stage) {

        // Logo do sistema
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/images/LogoComTexto.png")));
        logo.setFitHeight(300);
        logo.setPreserveRatio(true);

        // Título
        Label titulo = new Label("Cadastro de novos usuários");
        titulo.setFont(Font.font("Courier New", 18));
        titulo.setTextFill(Color.BLACK);

        // Campos de cadastro
        TextField usernameField = criarCampoTexto("Username:");
        TextField nomeField = criarCampoTexto("Nome:");
        TextField sobrenomeField = criarCampoTexto("Sobrenome:");
        TextField emailField = criarCampoTexto("Email:");
        PasswordField senhaField = criarCampoSenha("Senha:");
        PasswordField confirmarField = criarCampoSenha("Confirmar senha:");

        // Mensagem de erro/sucesso
        mensagem = new Text();
        mensagem.setFont(Font.font("Courier New", 16));
        mensagem.setFill(Color.FIREBRICK);
        mensagem.setVisible(false);

        // Botões
        Button btnCadastrar = new Button("Inscrever-se");
        Button btnSair = new Button("Sair");
        estilizarBotao(btnCadastrar);
        estilizarBotao(btnSair);

        btnCadastrar.setOnAction(e -> {
            int resultado =
                    cadastroController.cadastrar(
                    usernameField.getText(),
                    nomeField.getText(),
                    sobrenomeField.getText(),
                    emailField.getText(),
                    senhaField.getText(),
                    confirmarField.getText()
            );
            System.out.println(resultado);
            exibirMensagem(resultado);
        });

        btnSair.setOnAction(e -> stage.close());

        HBox botoes = new HBox(20, btnCadastrar, btnSair);
        botoes.setAlignment(Pos.CENTER);

        // Telinha lilás
        VBox painelCentral = new VBox(15,
                titulo,
                usernameField, nomeField, sobrenomeField, emailField,
                senhaField, confirmarField,
                mensagem,
                botoes
        );
        painelCentral.setPadding(new Insets(30));
        painelCentral.setAlignment(Pos.CENTER);
        painelCentral.setStyle("-fx-background-color: #c6addb; -fx-background-radius: 20px;");
        painelCentral.setMaxWidth(800);

        VBox root = new VBox(30, logo, painelCentral);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #d9d9d9;");
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 1200, 1000);
        stage.setTitle("Cadastro - GooDoolt");
        stage.setScene(scene);
        stage.show();
    }

    private TextField criarCampoTexto(String prompt) {
        TextField campo = new TextField();
        campo.setPromptText(prompt);
        campo.setMaxWidth(500);
        campo.setStyle(
                "-fx-font-family: 'Courier New';" +
                        "-fx-font-size: 16px;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-background-color: white;" +
                        "-fx-prompt-text-fill: #555;" +
                        "-fx-padding: 10px;"
        );
        return campo;
    }

    private PasswordField criarCampoSenha(String prompt) {
        PasswordField campo = new PasswordField();
        campo.setPromptText(prompt);
        campo.setMaxWidth(500);
        campo.setStyle(
                "-fx-font-family: 'Courier New';" +
                        "-fx-font-size: 16px;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-background-color: white;" +
                        "-fx-prompt-text-fill: #555;" +
                        "-fx-padding: 10px;"
        );
        return campo;
    }

    private void estilizarBotao(Button botao) {
        botao.setPrefWidth(200);
        botao.setPrefHeight(45);
        botao.setStyle(
                "-fx-background-color: #6A0DAD;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-family: 'Courier New';" +
                        "-fx-background-radius: 15px;" +
                        "-fx-cursor: hand;"
        );
    }

    private void exibirMensagem(int resultado) {
        mensagem.setVisible(true);
        switch (resultado) {
            case 1 -> {
                mensagem.setText("Todos os campos são obrigatórios");
                mensagem.setFill(Color.FIREBRICK);
            }
            case 2 -> {
                mensagem.setText("O usuário já está sendo utilizado");
                mensagem.setFill(Color.FIREBRICK);
            }
            case 3 -> {
                mensagem.setText("As senhas não condizem");
                mensagem.setFill(Color.FIREBRICK);
            }
            case 4 -> {
                mensagem.setText("Usuário cadastrado com sucesso");
                mensagem.setFill(Color.FORESTGREEN);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
