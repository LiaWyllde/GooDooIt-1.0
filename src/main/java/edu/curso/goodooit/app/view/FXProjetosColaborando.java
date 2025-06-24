package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.AllControllerRegistry;
import edu.curso.goodooit.app.controller.AutenticacaoController;
import edu.curso.goodooit.app.controller.MeusProjetosController;
import edu.curso.goodooit.app.model.Projeto;
import edu.curso.goodooit.app.model.Usuario;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class FXProjetosColaborando extends Application {

    private VBox areaPrincipal;
    private StackPane root;

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
        primaryStage.setTitle("Projeto que participo - " + nomeCompleto);

        VBox sidebar = criarSideBar(primaryStage, nomeCompleto);

        areaPrincipal = new VBox(30);
        areaPrincipal.setPadding(new Insets(20));
        areaPrincipal.setStyle("-fx-background-color: #b39ddb; -fx-background-radius: 15px;");
        areaPrincipal.setPrefWidth(700);
        VBox.setVgrow(areaPrincipal, Priority.ALWAYS);
        areaPrincipal.setMaxWidth(Double.MAX_VALUE);

        Label titulo = new Label("Projetos que participo - " + meusProjetosController.contarProjetosColaborador());
        titulo.setStyle("-fx-font-size: 22px; -fx-font-family: monospace; -fx-text-fill: black;");

        VBox projetosContainer = new VBox(15);
        projetosContainer.setFillWidth(true);
        projetosContainer.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(projetosContainer, Priority.ALWAYS);

        // Listando todos os projetos que o usuario eh dono
        ObservableList<Projeto> projetos = FXCollections.observableArrayList();
        projetos = meusProjetosController.getProjetosColaborador();
        for (Projeto p : projetos) {
            Integer concluidas = meusProjetosController.contarTarefasConcluidas(p.getID());
            Integer outras = meusProjetosController.contarTarefasNaoConcluidas(p.getID());
            projetosContainer.getChildren().add(criarBlocoProjeto(p, concluidas, outras, primaryStage));
        }
        //Scroll para poder visualizar a tela inteira com muitos projetos
        ScrollPane scroll = new ScrollPane(projetosContainer);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(scroll, Priority.ALWAYS);


        areaPrincipal.setAlignment(Pos.CENTER);
        areaPrincipal.getChildren().addAll(titulo, scroll);

        StackPane painelCinza = new StackPane(areaPrincipal);
        painelCinza.setStyle("-fx-background-color: #dddddd; -fx-background-radius: 20px;");
        painelCinza.setPadding(new Insets(15));
        HBox.setHgrow(painelCinza, Priority.ALWAYS);

        root = new StackPane(new HBox(sidebar, painelCinza));
        Scene scene = new Scene(root, larguraTela, alturaTela * 0.9);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox criarSideBar(Stage primaryStage, String nomeCompleto) {
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


    private VBox criarBlocoProjeto(Projeto p, Integer concluidas, Integer outras, Stage primaryStage) {
        VBox bloco = new VBox(10);
        bloco.setCursor(Cursor.HAND);
        bloco.setPadding(new Insets(20));
        bloco.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-color: black; -fx-border-radius: 10px;");
        bloco.setMaxWidth(Double.MAX_VALUE);

        Label nomeProjeto = new Label(p.getNome());
        Label status = new Label("Status: " + p.getStatus().toString());
        Label lblConcluidas = new Label("Tarefas concluídas: " + concluidas);
        Label lblOutras = new Label("Outras tarefas: " + outras);

        for (Label lbl : new Label[]{nomeProjeto, status, lblConcluidas, lblOutras}) {
            lbl.setStyle("-fx-font-family: monospace; -fx-font-size: 18px; -fx-text-fill: black;");
        }

        Button btnDeixarProjeto = new Button("Abandonar Projeto");
        Button visualizar = new Button("Visualizar");
        btnDeixarProjeto.setStyle("-fx-font-size: 18px; -fx-text-fill: gray; -fx-font-family: monospace;");
        visualizar.setStyle("-fx-font-size: 18px; -fx-text-fill: gray; -fx-font-family: monospace;");

        btnDeixarProjeto.setOnMouseClicked(e -> {
            //Todo: Se der tempo fazer a confirmação de saida
            meusProjetosController.deixarProjeto(p);
            telaProjetoColaborador(primaryStage);
        });

        visualizar.setOnMouseClicked(e -> {
            telaVisualizarProjeto(primaryStage, p);
        });

        HBox botoes = new HBox(10, btnDeixarProjeto, visualizar);
        botoes.setAlignment(Pos.TOP_RIGHT);
        StackPane.setAlignment(botoes, Pos.TOP_RIGHT);
        StackPane.setMargin(botoes, new Insets(10));

        StackPane stack = new StackPane(bloco, botoes);
        bloco.getChildren().addAll(nomeProjeto, status, lblConcluidas, lblOutras);
        return new VBox(stack);
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

    private void abrirModalSair(Stage primaryStage) {
        StackPane fundo = criarModalSair(primaryStage);
        root.getChildren().add(fundo);
        fundo.setVisible(true);
        fundo.toFront();
    }

    private ImageView formatarIcone(String path) {
        ImageView iconeEdit = new ImageView(new Image(getClass().getResourceAsStream(path)));
        iconeEdit.setFitWidth(18);
        iconeEdit.setFitHeight(18);
        StackPane.setAlignment(iconeEdit, Pos.CENTER_RIGHT);
        StackPane.setMargin(iconeEdit, new Insets(0, 10, 0, 0));
        return iconeEdit;
    }

    private void telaProjetoDono(Stage primaryStage) {
        FXMeusProjetos fxMeusProjetos = new FXMeusProjetos();
        FXMeusProjetos.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        fxMeusProjetos.start(primaryStage);
    }

    private void telaConvites(Stage primaryStage) {
        FXTelaConvite convites = new FXTelaConvite();
        convites.start(primaryStage);
    }

    private void telaSair(Stage primaryStage) {
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
        FXMinhasTarefas minhasTarefas = new FXMinhasTarefas();
        minhasTarefas.start(primaryStage);
    }

    private void telaProjetoColaborador(Stage primaryStage) {
        start(primaryStage);
    }


    private void telaVisualizarProjeto(Stage primaryStage, Projeto p) {
        FXVisualizarProjeto visualizarProjeto = new FXVisualizarProjeto();
        visualizarProjeto.setProjeto(p);
        FXVisualizarProjeto.setVisualizarProjetoController(AllControllerRegistry.getInstance().getVisualizarProjetoController());
        FXVisualizarProjeto.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        FXVisualizarProjeto.setTarefaController(AllControllerRegistry.getInstance().getTarefaController());
        visualizarProjeto.start(primaryStage);
    }
}