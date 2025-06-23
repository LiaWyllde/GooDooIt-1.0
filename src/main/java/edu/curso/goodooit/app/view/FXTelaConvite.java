package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.AllControllerRegistry;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class FXTelaConvite extends Application {

    StackPane root;

    @Override
    public void start(Stage primaryStage) {
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        // ===== SIDEBAR =====
        VBox sidebar = criarSidebar(primaryStage, "Julia Fernandes");

        // ===== CONTEÚDO PRINCIPAL =====
        VBox areaPrincipal = new VBox(30);
        areaPrincipal.setPadding(new Insets(20));
        areaPrincipal.setStyle("-fx-background-color: #d2bfe6;");
        areaPrincipal.setPrefWidth(700);
        VBox.setVgrow(areaPrincipal, Priority.ALWAYS);
        areaPrincipal.setMaxWidth(Double.MAX_VALUE);

        /*
            Tentativa de fazer um container para corrigir a largura do fundo
            e também para criar um scroll em caso de existirem muitos convites
            FXMeusProjetos serve como modelo para fazer no mesmo padrao
            ToDo: Olhar aqui
         */
        VBox convitesContainer = new VBox(15);
        convitesContainer.setFillWidth(true);
        convitesContainer.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(convitesContainer, Priority.ALWAYS);

        ScrollPane scroll = new ScrollPane(convitesContainer);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        Label titulo = new Label("Convites");
        titulo.setFont(Font.font("Courier New", 28));
        areaPrincipal.getChildren().addAll(titulo, scroll);

        // Adiciona 3 convites fictícios
        for (int i = 0; i < 3; i++) {
            areaPrincipal.getChildren().add(criarCartaoConvite(
                    "17/06/2025",
                    "Gustavo Henrique",
                    "GustavoHenrique01",
                    "Furão Biónico"
            ));
        }

        root = new StackPane(new HBox(sidebar, areaPrincipal));
        Scene scene = new Scene(root, larguraTela, alturaTela * 0.9);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Convites - GooDoolt");
        primaryStage.show();
    }

    private VBox criarSidebar(Stage primaryStage, String nomeCompleto) {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #eeeeee;");
        sidebar.setPrefWidth(220);
        sidebar.setAlignment(Pos.TOP_CENTER);

        Image avatarImage = new Image(getClass().getResourceAsStream("/images/LogoComTexto.png"), 100, 100, true, true);
        ImageView avatarView = new ImageView(avatarImage);

        Label nome = new Label(nomeCompleto);
        nome.setStyle("-fx-font-size: 18px; -fx-font-family: monospace;");

        HBox notificacoes = new HBox(20);
        notificacoes.setAlignment(Pos.CENTER);

        ImageView iconeConvite = formatarIcone("/images/envelope.jpg");
        Label email = new Label("1");
        email.setCursor(Cursor.HAND);

        email.setOnMouseClicked(e -> telaConvites(primaryStage));
        iconeConvite.setOnMouseClicked(e -> telaConvites(primaryStage));

        email.setStyle("-fx-font-size: 16px;");
        notificacoes.getChildren().addAll(iconeConvite, email);

        sidebar.getChildren().addAll(avatarView, nome, notificacoes);

        Map<String, Runnable> botoes = new LinkedHashMap<>();
        botoes.put("Meus Projetos", () -> telaProjetoDono(primaryStage));
        botoes.put("Colaborando", () -> telaProjetoColaborador(primaryStage));
        botoes.put("Tarefas", () -> telaTarefas(primaryStage));
        botoes.put("Editar Perfil", () -> telaEditarPerfil(primaryStage));
        botoes.put("Sair", () -> abrirModalSair(primaryStage));

        AtomicBoolean roxoFlag = new AtomicBoolean(true);

        botoes.forEach((label, action) -> {
            boolean roxo = roxoFlag.get();

            Button btn = botaoMenu(label, roxo, false);
            btn.setCursor(Cursor.HAND);
            btn.setOnAction(e -> action.run());
            sidebar.getChildren().add(btn);
            roxoFlag.set(!roxo);
        });
        return sidebar;
    }

    private Button criarBotaoMenu(String texto, boolean roxo) {
        Button btn = new Button(texto);
        btn.setFont(Font.font("Courier New", 14));
        btn.setPrefWidth(180);
        btn.setStyle(
                "-fx-background-color: " + (roxo ? "#d39ef2;" : "#d8d8d8;") +
                        "-fx-background-radius: 12px;" +
                        "-fx-font-family: 'Courier New';" +
                        "-fx-cursor: hand;"
        );
        return btn;
    }

    private Button botaoMenu(String texto, boolean roxo, boolean selecionado) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-font-family: monospace; -fx-font-size: 14px; -fx-background-radius: 15;" +
                (selecionado ? "-fx-border-color: black; -fx-border-width: 2;" : "") +
                (roxo ? "-fx-background-color: #d681f0; -fx-text-fill: black;" : "-fx-background-color: #cccccc;"));
        return btn;
    }

    private VBox criarCartaoConvite(String data, String nome, String username, String projeto) {
        VBox cartao = new VBox(10);
        cartao.setPadding(new Insets(15));
        cartao.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        cartao.setMaxWidth(700);

        Label lblData = new Label(data);
        Label lblTexto = new Label(nome + "\n" + username + "\nconvidou você para participar do projeto \"" + projeto + "\"");
        lblData.setFont(Font.font("Courier New", 14));
        lblTexto.setFont(Font.font("Courier New", 16));

        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER_RIGHT);

        Button btnAceitar = new Button("Aceitar");
        Button btnRecusar = new Button("Recusar");
        estilizarBotaoConvite(btnAceitar, "green");
        estilizarBotaoConvite(btnRecusar, "red");

        botoes.getChildren().addAll(btnAceitar, btnRecusar);
        cartao.getChildren().addAll(lblData, lblTexto, botoes);

        return cartao;
    }

    private void estilizarBotaoConvite(Button btn, String cor) {
        String backgroundColor = switch (cor.toLowerCase()) {
            case "green" -> "#8fd18f";
            case "red" -> "#f28f8f";
            default -> "#dddddd";
        };

        btn.setFont(Font.font("Courier New", 14));
        btn.setStyle(
                "-fx-background-color: " + backgroundColor + ";" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 5 15;" +
                        "-fx-cursor: hand;"
        );
    }

    public ImageView formatarIcone(String path) {
        ImageView iconeEdit = new ImageView(new Image(getClass().getResourceAsStream(path)));
        iconeEdit.setFitWidth(18);
        iconeEdit.setFitHeight(18);
        StackPane.setAlignment(iconeEdit, Pos.CENTER_RIGHT);
        StackPane.setMargin(iconeEdit, new Insets(0, 10, 0, 0));
        return iconeEdit;
    }

    private void abrirModalSair(Stage primaryStage) {
        StackPane fundo = criarModalSair(primaryStage);
        root.getChildren().add(fundo);
        fundo.setVisible(true);
        fundo.toFront();
    }

    private StackPane criarModalSair(Stage primaryStage) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudo = new VBox(15);
        conteudo.setPadding(new Insets(30));
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setMaxWidth(largura * 0.35);
        conteudo.setMaxHeight(altura * 0.4);
        conteudo.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");

        Image avatarImage = new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true);
        ImageView avatarView = new ImageView(avatarImage);
        ImageView ghost = new ImageView(new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true)); // você deve ter esta imagem no recurso
        ghost.setFitHeight(80);
        ghost.setFitWidth(80);

        Label titulo = new Label("GooDoolt");
        titulo.setStyle("-fx-font-size: 28px; -fx-text-fill: #6A0DAD; -fx-font-weight: bold;");

        Label pergunta = new Label("Deseja realmente sair da sua conta?");
        pergunta.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");

        Button btnSair = new Button("Sair");
        Button btnCancelar = new Button("Cancelar");

        btnSair.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCancelar.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        HBox botoes = new HBox(15, btnSair, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudo.getChildren().addAll(ghost, titulo, pergunta, botoes);

        StackPane fundo = new StackPane(conteudo);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        btnSair.setOnAction(e -> {
            fundo.setVisible(false);
            telaSair(primaryStage);
        });

        btnCancelar.setOnAction(e -> fundo.setVisible(false));

        return fundo;
    }

    private void telaSair(Stage primaryStage) {
        System.out.println("Tela Sair");
        FxTelaLogin telaLogin = new FxTelaLogin();
        FxTelaLogin.setLoginController(AllControllerRegistry.getInstance().getLoginController());
        telaLogin.start(primaryStage);
    }


    private void telaEditarPerfil(Stage primaryStage) {
        FXEditarPerfil editarPerfil = new FXEditarPerfil();
        FXEditarPerfil.setAlterarDadosUsuarioController(AllControllerRegistry.getInstance().getAlterarDadosUsuarioController());
        FXEditarPerfil.setAlterarSenhaController(AllControllerRegistry.getInstance().getAlterarSenhaController());
        editarPerfil.start(primaryStage);
    }

    private void telaTarefas(Stage primaryStage) {
        //ToDo: Atualizar injeção na tela quando mexer nas tarefas
        FXMinhasTarefas minhasTarefas = new FXMinhasTarefas();
        minhasTarefas.start(primaryStage);
    }

    private void telaProjetoColaborador(Stage primaryStage) {

    }

    private void telaProjetoDono(Stage primaryStage) {
        FXMeusProjetos meusProjetos = new FXMeusProjetos();
        FXMeusProjetos.setConviteController(AllControllerRegistry.getInstance().getConviteController());
        meusProjetos.start(primaryStage);
    }

    private void telaConvites(Stage primaryStage) {
        start(primaryStage);
    }
    public static void main (String[]args){
        launch();
    }
}

