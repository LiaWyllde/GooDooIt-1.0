package edu.curso.goodooit.app.view;

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

    private Text mensagem;

    @Override
    public void start(Stage stage) {

        // Logo do sistema
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/images/LogoComTexto.png")));
        logo.setFitHeight(120);
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
        estilizarBotaoCinza(btnCadastrar);
        estilizarBotaoCinza(btnSair);

        btnCadastrar.setOnAction(e -> {
            int resultado = cadastrarUsuario(
                    usernameField.getText(),
                    nomeField.getText(),
                    sobrenomeField.getText(),
                    emailField.getText(),
                    senhaField.getText(),
                    confirmarField.getText()
            );
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
        painelCentral.setMaxWidth(600);

        VBox root = new VBox(30, logo, painelCentral);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #d9d9d9;");
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 900, 700);
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

    private void estilizarBotaoCinza(Button btn) {
        btn.setFont(Font.font("Courier New", 16));
        btn.setStyle(
                "-fx-background-color: #d8d8d8;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 8 20;"
        );
    }

    // Simulação de método principal só para a tela rodar, precisa integrar com a controller
    private int cadastrarUsuario(String username, String nome, String sobrenome, String email, String senha, String confirmar) {
        if (username.isBlank() || nome.isBlank() || sobrenome.isBlank() ||
                email.isBlank() || senha.isBlank() || confirmar.isBlank()) {
            return 1; // campos vazios
        }
        if (username.equalsIgnoreCase("LiaFernandes")) {
            return 2; // usuário em uso
        }
        if (!senha.equals(confirmar)) {
            return 3; // senha não confere
        }
        return 4; // sucesso
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
