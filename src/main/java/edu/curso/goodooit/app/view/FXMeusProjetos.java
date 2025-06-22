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

public class FXMeusProjetos extends Application {

    private VBox areaPrincipal;
    private StackPane modalProjeto;
    private TextField tfNome;
    private TextArea taDescricao;
    private TextField tfInicio;
    private TextField tfFim;

    @Override
    public void start(Stage primaryStage) {
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        primaryStage.setTitle("Meus Projetos - Julia Fernandes");

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

        sidebar.getChildren().addAll(avatarView, nome, notificacoes);
        sidebar.getChildren().addAll(
                botaoMenu("Meus projetos", true),
                botaoMenu("Colaborando", false),
                botaoMenu("Equipes", true),
                botaoMenu("Tarefas", false),
                botaoMenu("Editar perfil", true),
                botaoMenu("Sair", false)
        );

        areaPrincipal = new VBox(30);
        areaPrincipal.setPadding(new Insets(20));
        areaPrincipal.setStyle("-fx-background-color: #b39ddb; -fx-background-radius: 15px;");
        areaPrincipal.setPrefWidth(700);
        VBox.setVgrow(areaPrincipal, Priority.ALWAYS);
        areaPrincipal.setMaxWidth(Double.MAX_VALUE);

        Label titulo = new Label("Meus projetos");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-family: monospace; -fx-text-fill: black;");

        VBox projetosContainer = new VBox(15);
        projetosContainer.setFillWidth(true);
        projetosContainer.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(projetosContainer, Priority.ALWAYS);

        
        // for(Projeto p: ObservableList<Projeto>)
        projetosContainer.getChildren().add(criarBlocoProjeto("Projeto galo eletrônico", "em andamento", 25, 95, true));
        projetosContainer.getChildren().add(criarBlocoProjeto("Projeto visitante", "concluído", 10, 0, false));
        // booleano para confirmar se o Usuário tem poderes de edição ao projeto ou não

        Button btnCriar = new Button("Criar novo projeto");
        btnCriar.setStyle("-fx-font-family: monospace; -fx-font-size: 14px; -fx-background-color: #dddddd; -fx-background-radius: 10;");
        btnCriar.setMaxWidth(Double.MAX_VALUE);
        btnCriar.setPrefHeight(35);
        btnCriar.setOnAction(e -> abrirModalProjeto("", "", "", ""));

        areaPrincipal.getChildren().addAll(titulo, projetosContainer, btnCriar);

        StackPane painelCinza = new StackPane(areaPrincipal);
        painelCinza.setStyle("-fx-background-color: #dddddd; -fx-background-radius: 20px;");
        painelCinza.setPadding(new Insets(15));
        HBox.setHgrow(painelCinza, Priority.ALWAYS);

        modalProjeto = criarModalProjeto(() -> System.out.println("Projeto salvo."));

        StackPane root = new StackPane(new HBox(sidebar, painelCinza), modalProjeto);
        Scene scene = new Scene(root, larguraTela, alturaTela * 0.9);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox criarBlocoProjeto(String nome, String statusTxt, int concluidas, int outras, boolean podeEditar) {
        VBox bloco = new VBox(10);
        bloco.setPadding(new Insets(20));
        bloco.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        bloco.setMaxWidth(Double.MAX_VALUE);

        Label nomeProjeto = new Label(nome);
        Label status = new Label("Status: " + statusTxt);
        Label lblConcluidas = new Label("Tarefas concluídas: " + concluidas);
        Label lblOutras = new Label("Outras tarefas: " + outras);

        for (Label lbl : new Label[]{nomeProjeto, status, lblConcluidas, lblOutras}) {
            lbl.setStyle("-fx-font-family: monospace; -fx-font-size: 14px; -fx-text-fill: black;");
        }

        Label icone = new Label(podeEditar ? "✎" : "👁");
        icone.setStyle("-fx-font-size: 18px; -fx-text-fill: gray; -fx-font-family: monospace;");

        if (podeEditar) {
            icone.setOnMouseClicked(e -> abrirModalProjeto(nome, statusTxt, "01/01/2024", "31/12/2024"));
        }

        StackPane stack = new StackPane(bloco, icone);
        StackPane.setAlignment(icone, Pos.TOP_RIGHT);
        StackPane.setMargin(icone, new Insets(10));

        bloco.getChildren().addAll(nomeProjeto, status, lblConcluidas, lblOutras);
        return new VBox(stack);
    }

    private void abrirModalProjeto(String nome, String descricao, String inicio, String fim) {
        tfNome.setText(nome);
        taDescricao.setText(descricao);
        tfInicio.setText(inicio);
        tfFim.setText(fim);
        modalProjeto.setVisible(true);
    }

    public StackPane criarModalProjeto(Runnable acaoSalvar) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudoModal = new VBox(10);
        conteudoModal.setPadding(new Insets(20));
        conteudoModal.setAlignment(Pos.CENTER_LEFT);
        conteudoModal.setMaxWidth(largura * 0.5);
        conteudoModal.setMaxHeight(altura * 0.5);
        conteudoModal.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");

        tfNome = new TextField();
        taDescricao = new TextArea();
        tfInicio = new TextField();
        tfFim = new TextField();

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        HBox botoes = new HBox(20, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudoModal.getChildren().addAll(
            new Label("Nome do Projeto"), tfNome,
            new Label("Descrição"), taDescricao,
            new Label("Prazo previsto para início"), tfInicio,
            new Label("Prazo previsto para finalização"), tfFim,
            botoes
        );

        StackPane fundo = new StackPane(conteudoModal);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        // Actions para atualizar o projeto
        btnSalvar.setOnAction(e -> {
            acaoSalvar.run();
            fundo.setVisible(false);
        });

        btnCancelar.setOnAction(e -> fundo.setVisible(false));

        return fundo;
    }

    private Button botaoMenu(String texto, boolean roxo) {
        return botaoMenu(texto, roxo, false);
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

    public static void main(String[] args) {
        launch(args);
    }
}
