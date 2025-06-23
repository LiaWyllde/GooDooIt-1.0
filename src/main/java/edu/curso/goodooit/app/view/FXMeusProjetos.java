package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.AutenticacaoController;
import edu.curso.goodooit.app.controller.ControllerRegistry;
import edu.curso.goodooit.app.controller.MeusProjetosController;
import edu.curso.goodooit.app.model.Projeto;
import edu.curso.goodooit.app.model.Usuario;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class FXMeusProjetos extends Application {
    //Listagem de projeto que sou dono

    //TODO: Implementar convites do banco, barra lateral clicável e redirecionando

    private VBox areaPrincipal;
    private StackPane modalProjeto;
    private StackPane modalConvites;
    private TextField tfNome;
    private TextArea taDescricao;
    private TextField tfInicio;
    private TextField tfFim;

    private static MeusProjetosController meusProjetosController;

    public static void setMeusProjetosController(MeusProjetosController mpc) {
        meusProjetosController = mpc;
    }

    @Override
    public void start(Stage primaryStage) {
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        Usuario autenticado = AutenticacaoController.getAutenticado();
        String nomeCompleto = autenticado.getNome() + " " + autenticado.getSobrenome();
        primaryStage.setTitle("Meus Projetos - " + nomeCompleto);

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

        Label email = new Label("✉️ 1");
        email.setCursor(Cursor.HAND);

        email.setOnMouseClicked(e -> modalConvites.setVisible(true));

        email.setStyle("-fx-font-size: 16px;");
        notificacoes.getChildren().addAll(email);

        sidebar.getChildren().addAll(avatarView, nome, notificacoes);

        Map<String,Runnable> botoes = new LinkedHashMap<>();
        botoes.put("Projetos",    () -> telaProjetoDono(primaryStage));
        botoes.put("Colaborando", () -> telaProjetoColaborador(primaryStage));
        botoes.put("Equipes",     () -> telaEquipes(primaryStage));
        botoes.put("Tarefas",     () -> telaTarefas(primaryStage));
        botoes.put("Editar Perfil", () -> telaEditarPerfil(primaryStage));
        botoes.put("Sair",        () -> telaSair(primaryStage));

        AtomicBoolean roxoFlag = new AtomicBoolean(true);

        botoes.forEach((label, action) -> {
            boolean roxo = roxoFlag.get();

            Button btn = botaoMenu(label, roxo, false);
            btn.setCursor(Cursor.HAND);
            btn.setOnAction(e -> action.run());
            sidebar.getChildren().add(btn);
            roxoFlag.set(!roxo);
        });

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


        // Listando todos os projetos que o usuario eh dono
        ObservableList<Projeto> projetos = FXCollections.observableArrayList();
        projetos = meusProjetosController.getProjetos();
        for (Projeto p : projetos) {
            Integer concluidas = meusProjetosController.contarTarefasConcluidas(p.getID());
            Integer outras = meusProjetosController.contarTarefasNaoConcluidas(p.getID());
            projetosContainer.getChildren().add(criarBlocoProjeto(p.getNome(), p.getStatus().toString(), concluidas, outras, true));
        }

        ScrollPane scroll = new ScrollPane(projetosContainer);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        Button btnCriar = new Button("Criar novo projeto");
        estilizarBotao(btnCriar);
        btnCriar.setOnAction(e -> abrirModalProjeto("", "", "", ""));

        areaPrincipal.setAlignment(Pos.CENTER);
        areaPrincipal.getChildren().addAll(titulo, scroll, btnCriar);

        StackPane painelCinza = new StackPane(areaPrincipal);
        painelCinza.setStyle("-fx-background-color: #dddddd; -fx-background-radius: 20px;");
        painelCinza.setPadding(new Insets(15));
        HBox.setHgrow(painelCinza, Priority.ALWAYS);

        modalProjeto = criarModalProjeto(() -> System.out.println("Projeto salvo."));
        modalConvites = criarModalVisualizarConvites(() -> System.out.println("Saindo do Visualizar Convites"));


        StackPane root = new StackPane(new HBox(sidebar, painelCinza), modalProjeto, modalConvites);
        Scene scene = new Scene(root, larguraTela, alturaTela * 0.9);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox criarBlocoProjeto(String nome, String statusTxt, Integer concluidas, Integer outras, boolean podeEditar) {
        VBox bloco = new VBox(10);
        bloco.setCursor(Cursor.HAND);
        bloco.setPadding(new Insets(20));
        bloco.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-color: black; -fx-border-radius: 10px;");
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
        Button btn = botaoMenu(texto, roxo, false);
        btn.setCursor(Cursor.HAND);
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

    private StackPane criarModalVisualizarConvites(Runnable acaoConvite) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudoModal = new VBox(15);
        conteudoModal.setPadding(new Insets(30));
        conteudoModal.setAlignment(Pos.CENTER);
        conteudoModal.setMaxWidth(largura * 0.35);
        conteudoModal.setMaxHeight(altura * 0.4);
        conteudoModal.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");

        // Imagem do fantasminha
        Image avatarImage = new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true);
        ImageView avatarView = new ImageView(avatarImage);
        ImageView ghost = new ImageView(new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true)); // você deve ter esta imagem no recurso
        ghost.setFitHeight(80);
        ghost.setFitWidth(80);

        Label nomeCompleto = new Label("Gustavo Henrique");
        nomeCompleto.setStyle("-fx-font-size: 24px;");
        nomeCompleto.setAlignment(Pos.CENTER);

        Label usuario = new Label("Usuário: GustavoSilva");
        usuario.setStyle("-fx-font-size: 20px;");
        usuario.setAlignment(Pos.CENTER);

        Label mensagem = new Label("Te convidou para participar do projeto");
        mensagem.setStyle("-fx-font-size: 15px;");
        mensagem.setAlignment(Pos.CENTER);

        Label projeto = new Label("Projeto furão mecânico");
        projeto.setStyle("-fx-font-size: 20px;");
        projeto.setAlignment(Pos.CENTER);

        Button btnReatribuir = new Button("Aceitar");
        Button btnCancelar = new Button("Recusar");

        btnReatribuir.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 20;");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 20;");

        HBox botoes = new HBox(15, btnReatribuir, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudoModal.getChildren().addAll(ghost, nomeCompleto, usuario, mensagem, projeto, botoes);

        StackPane fundo = new StackPane(conteudoModal);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        btnCancelar.setOnAction(e -> fundo.setVisible(false));
        btnReatribuir.setOnAction(e -> {
            acaoConvite.run();
            fundo.setVisible(false);
        });

        return fundo;
    }

    private void telaProjetoDono(Stage primaryStage) {
        System.out.println("Tela Projeto");
    }

    private void telaSair(Stage primaryStage) {
        System.out.println("Tela Sair");
    }

    private void telaEditarPerfil(Stage primaryStage) {
        System.out.println("Tela EditarPerfil");
    }

    private void telaTarefas(Stage primaryStage) {
        System.out.println("Tela Tarefas");
    }

    private void telaEquipes(Stage primaryStage) {
        System.out.println("Tela Equipes");
    }

    private void telaProjetoColaborador(Stage primaryStage) {
        System.out.println("Tela ProjetoColaborador");
    }
}
