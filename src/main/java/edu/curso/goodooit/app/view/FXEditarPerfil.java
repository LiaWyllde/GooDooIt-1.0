package edu.curso.goodooit.ui.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FXEditarPerfil extends Application {

    private VBox areaPrincipal;
    private StackPane modalSenhaGlobal;
    private StackPane modalEsqueciSenha;
    
    @Override
    public void start(Stage primaryStage) {
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        primaryStage.setTitle("Tarefas - Julia Fernandes");

        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #eeeeee;");
        sidebar.setPrefWidth(220);
        sidebar.setAlignment(Pos.TOP_CENTER);

        Image avatarImage = new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true);
        ImageView avatarView = new ImageView(avatarImage);

        Label nome = new Label("Julia Fernandes");
        nome.setStyle("-fx-font-size: 18px; -fx-font-family: monospace;");

        HBox notificacoes = new HBox(20);
        notificacoes.setAlignment(Pos.CENTER);
        Label sino = new Label("🔔 5"); // Property notificação
        Label email = new Label("✉️ 1"); // Property email
        sino.setStyle("-fx-font-size: 16px;");
        email.setStyle("-fx-font-size: 16px;");
        notificacoes.getChildren().addAll(sino, email);

        sidebar.getChildren().addAll(avatarView, nome, notificacoes,
                botaoMenu("Meus projetos", true),
                botaoMenu("Colaborando", false),
                botaoMenu("Equipes", true),
                botaoMenu("Tarefas", false),
                botaoMenu("Editar perfil", true, true),
                botaoMenu("Sair", false)
        );

        StackPane painelCinza = new StackPane();
        painelCinza.setStyle("-fx-background-color: #dddddd; -fx-background-radius: 20px;");
        painelCinza.setPadding(new Insets(15));
        painelCinza.setMaxWidth(Double.MAX_VALUE);

        areaPrincipal = new VBox();
        areaPrincipal.setSpacing(20);
        areaPrincipal.setPadding(new Insets(20));
        areaPrincipal.setStyle("-fx-background-color: #b39ddb; -fx-background-radius: 15px;");
        areaPrincipal.setPrefWidth(700);
        painelCinza.getChildren().add(areaPrincipal);

        HBox conteudoPrincipal = new HBox();
        conteudoPrincipal.setPadding(new Insets(10));
        conteudoPrincipal.getChildren().addAll(sidebar, painelCinza);
        HBox.setHgrow(painelCinza, Priority.ALWAYS);

        // StackPane principal
        StackPane rootStack = new StackPane(conteudoPrincipal);
        modalSenhaGlobal = modalAlterarSenha(() -> System.out.println("Senha alterada com sucesso."));
        modalEsqueciSenha = criarModalEsqueciSenha(() -> modalSenhaGlobal.setVisible(true));

        rootStack.getChildren().addAll(modalSenhaGlobal, modalEsqueciSenha);
        modalSenhaGlobal.setVisible(false);
        modalEsqueciSenha.setVisible(false);

        Scene scene = new Scene(rootStack, larguraTela, alturaTela * 0.9);
        primaryStage.setScene(scene);
        primaryStage.show();

        mostrarTelaEditarPerfil();
    }

    private Button botaoMenu(String texto, boolean roxo) {
        return botaoMenu(texto, roxo, false);
    }

    private Button botaoMenu(String texto, boolean roxo, boolean ativaEditarPerfil) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-font-family: monospace; -fx-font-size: 14px; -fx-background-radius: 15;" +
                (roxo ? "-fx-background-color: #d681f0; -fx-text-fill: black;" : "-fx-background-color: #cccccc;"));

        if (ativaEditarPerfil) {
            btn.setOnAction(e -> mostrarTelaEditarPerfil());
        }

        return btn;
    }

    private void mostrarTelaEditarPerfil() {
        areaPrincipal.getChildren().clear();

        Label titulo = new Label("Editar perfil");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-family: monospace; -fx-text-fill: black;");

        VBox form = new VBox(18);
        form.setPadding(new Insets(10));
        form.setStyle("-fx-font-family: monospace;");

        form.getChildren().addAll(
                criarCampoComIcone("Username", new TextField("jfernandes"), "⛔"), // Property Username
                criarCampoComIcone("Nome", new TextField("Julia"), "✏️"), // Property Nome
                criarCampoComIcone("Sobrenome", new TextField("Fernandes"), "✏️"), // Property Sobrenome
                criarCampoComIcone("Email", new TextField("julia@example.com"), "✏️"), // Property Email
                criarCampoComIcone("Senha", new PasswordField(), "✏️")
        );

        HBox botoes = new HBox(10);
        botoes.setPadding(new Insets(20, 0, 0, 0));
        botoes.setAlignment(Pos.CENTER);

        Button salvarBtn = new Button("Salvar");
        Button cancelarBtn = new Button("Cancelar");

        salvarBtn.setPrefHeight(35);
        cancelarBtn.setPrefHeight(35);
        salvarBtn.setStyle("-fx-background-color: #d681f0; -fx-font-family: monospace;");
        cancelarBtn.setStyle("-fx-background-color: #cccccc; -fx-font-family: monospace;");

        Region espacoEntre = new Region();
        HBox.setHgrow(espacoEntre, Priority.ALWAYS);

        botoes.getChildren().addAll(salvarBtn, espacoEntre, cancelarBtn);
        areaPrincipal.getChildren().addAll(titulo, form, botoes);
    }

    private VBox criarCampoComIcone(String labelTexto, TextField campo, String icone) {
        Label label = new Label(labelTexto);
        label.setStyle("-fx-font-size: 14px; -fx-font-family: monospace; -fx-text-fill: black;");

        campo.setPrefHeight(35);
        campo.setStyle("-fx-background-radius: 10; -fx-font-size: 14px;");

        if (labelTexto.equalsIgnoreCase("Username") || labelTexto.equalsIgnoreCase("Senha")) {
            campo.setDisable(true);
            campo.setStyle("-fx-background-radius: 10; -fx-font-size: 14px; -fx-opacity: 1.0; -fx-background-color: white; -fx-text-fill: black;");
        }

        Label iconeLabel = new Label(icone);
        iconeLabel.setStyle("-fx-font-size: 16px;");
        StackPane.setAlignment(iconeLabel, Pos.CENTER_RIGHT);
        StackPane.setMargin(iconeLabel, new Insets(0, 10, 0, 0));

        if (labelTexto.equalsIgnoreCase("Senha")) {
            campo.setEditable(false);
            iconeLabel.setOnMouseClicked(e -> modalSenhaGlobal.setVisible(true));
        }

        StackPane campoComIcone = new StackPane(campo, iconeLabel);
        return new VBox(5, label, campoComIcone);
    }

    // Modal de alterar senha
    public StackPane modalAlterarSenha(Runnable acaoSalvarSenha) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudo = new VBox(15);
        conteudo.setPadding(new Insets(30));
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setMaxWidth(largura * 0.35);
        conteudo.setMaxHeight(altura * 0.4);
        conteudo.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");

        Label titulo = new Label("🔐 Alterar Senha");
        titulo.setStyle("-fx-font-size: 24px; -fx-text-fill: #6A0DAD; -fx-font-weight: bold;");

        PasswordField campoAtual = new PasswordField();
        campoAtual.setPromptText("Senha atual"); // Senha Atual

        PasswordField campoNova = new PasswordField();
        campoNova.setPromptText("Nova senha"); // Nova Atual

        PasswordField campoConfirmar = new PasswordField();
        campoConfirmar.setPromptText("Confirmar nova senha");

        for (TextField campo : new TextField[]{campoAtual, campoNova, campoConfirmar}) {
            campo.setMaxWidth(250);
            campo.setStyle("-fx-background-radius: 16; -fx-border-radius: 16;");
        }

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 18;");

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 18;");

        Button btnEsqueci = new Button("Esqueci minha senha");
        btnEsqueci.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white; -fx-background-radius: 18;");
        btnEsqueci.setMaxWidth(220);

        btnSalvar.setOnAction(e -> {
        	// Listener da alteração de senha
            acaoSalvarSenha.run();
            modalSenhaGlobal.setVisible(false);
        });

        btnCancelar.setOnAction(e -> modalSenhaGlobal.setVisible(false));
        btnEsqueci.setOnAction(e -> {
            modalSenhaGlobal.setVisible(false);
            modalEsqueciSenha.setVisible(true);
        });

        HBox botoes = new HBox(15, btnCancelar, btnSalvar);
        botoes.setAlignment(Pos.CENTER);

        conteudo.getChildren().addAll(titulo, campoAtual, campoNova, campoConfirmar, botoes, btnEsqueci);

        StackPane fundo = new StackPane(conteudo);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        fundo.setAlignment(Pos.CENTER);
        return fundo;
    }

    public StackPane criarModalEsqueciSenha(Runnable acaoVoltar) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudoModal = new VBox(15);
        conteudoModal.setPadding(new Insets(30));
        conteudoModal.setAlignment(Pos.CENTER);
        conteudoModal.setMaxWidth(largura * 0.35);
        conteudoModal.setMaxHeight(altura * 0.4);
        conteudoModal.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");

        StackPane fundo = new StackPane(conteudoModal);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        Label titulo = new Label("Esqueci minha senha");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-family: monospace; -fx-text-fill: black;");

        ImageView ghost = new ImageView(new Image(getClass().getResourceAsStream("/images/Goo.png"), 80, 80, true, true));

        Label cuidado = new Label("Mais cuidado na próxima");
        cuidado.setStyle("-fx-font-size: 16px; -fx-font-family: monospace; -fx-text-fill: black;");

        Label instrucao = new Label("Envie um email para:");
        instrucao.setStyle("-fx-font-size: 14px; -fx-font-family: monospace; -fx-text-fill: black;");

        Label email = new Label("businesgoodooit@gmail.com");
        email.setStyle("-fx-font-size: 14px; -fx-font-family: monospace; -fx-text-fill: black;");

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle("-fx-background-color: #8000C9; -fx-text-fill: white; -fx-font-family: monospace; -fx-background-radius: 12px;");
        btnVoltar.setPrefWidth(150);
        btnVoltar.setOnAction(e -> {
            fundo.setVisible(false); // ← apenas fecha
        });

        VBox.setMargin(btnVoltar, new Insets(10, 0, 0, 0));

        conteudoModal.getChildren().addAll(titulo, ghost, cuidado, instrucao, email, btnVoltar);
        return fundo;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
