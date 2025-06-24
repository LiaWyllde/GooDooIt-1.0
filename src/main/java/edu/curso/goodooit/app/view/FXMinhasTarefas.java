package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.AllControllerRegistry;
import edu.curso.goodooit.app.model.Projeto;
import javafx.application.Application;
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
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

public class FXMinhasTarefas extends Application {

    /*
        Essa tela é um placeholder temporário. A sidebar funciona, mas só isso também
        ToDo: terminar essa tela
     */


    private StackPane root;

    @Override
    public void start(Stage primaryStage) {

        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        primaryStage.setTitle("Tarefas - Julia Fernandes");

        // Sidebar (esquerda)
        VBox sidebar = criarSideBar(primaryStage, "Julia Fernandes");

        // Container cinza de fundo (envolve o painel roxo)
        StackPane painelCinza = new StackPane();
        painelCinza.setStyle("-fx-background-color: #dddddd; -fx-background-radius: 20px;");
        painelCinza.setPadding(new Insets(15));
        painelCinza.setMaxWidth(Double.MAX_VALUE);

        // Área roxa (conteúdo principal)
        VBox areaPrincipal = new VBox(20);
        areaPrincipal.setPadding(new Insets(20));
        areaPrincipal.setStyle("-fx-background-color: #b39ddb; -fx-background-radius: 15px;");
        areaPrincipal.setPrefWidth(700);

        Label titulo = new Label("Minhas tarefas");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-family: monospace; -fx-text-fill: black;");

        TextField campoBusca = new TextField();
        campoBusca.setPromptText("buscar tarefa");
        campoBusca.setStyle("-fx-font-family: monospace; -fx-font-size: 14px; -fx-background-radius: 10;");
        campoBusca.setPrefHeight(35);

        VBox tarefas = new VBox(15);
        tarefas.setPadding(new Insets(10));
        tarefas.setStyle("-fx-background-color: transparent;");
        tarefas.getChildren().addAll(
                blocoTarefa("Desenvolver o som do cocoricó", "aguardando dispositivo", "Alta", "24/06/2025"),
                blocoTarefa("Iniciar testes", "aguardando furão", "Muito alta", "24/06/2025"),
                blocoTarefa("Iniciar testes", "aguardando galo", "Alta", "24/06/2025")
        );

        ScrollPane scrollTarefas = new ScrollPane(tarefas);
        scrollTarefas.setFitToWidth(true);
        scrollTarefas.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollTarefas.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTarefas.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(scrollTarefas, Priority.ALWAYS);

        areaPrincipal.getChildren().addAll(titulo, campoBusca, scrollTarefas);
        painelCinza.getChildren().add(areaPrincipal);

        // Layout principal (esquerda + conteúdo)
        root = new StackPane(new HBox(sidebar, painelCinza));
        HBox.setHgrow(painelCinza, Priority.ALWAYS);

        Scene scene = new Scene(root, larguraTela, alturaTela * 0.9);
        primaryStage.setScene(scene);
        primaryStage.show();
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

    private VBox blocoTarefa(String titulo, String status, String prioridade, String prazo) {
        VBox box = new VBox(5);
        box.setPadding(new Insets(12));
        box.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        box.setMaxWidth(Double.MAX_VALUE);

        Label tituloLbl = new Label(titulo);
        tituloLbl.setStyle("-fx-font-family: monospace; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
        tituloLbl.setWrapText(true);

        Label statusLbl = new Label("Status: " + status);
        Label prioridadeLbl = new Label("Prioridade: " + prioridade);
        Label prazoLbl = new Label("Prazo de conclusão: " + prazo);

        for (Label l : new Label[]{statusLbl, prioridadeLbl, prazoLbl}) {
            l.setStyle("-fx-font-family: monospace; -fx-font-size: 14px; -fx-text-fill: black;");
            l.setWrapText(true);
        }

        box.getChildren().addAll(tituloLbl, statusLbl, prioridadeLbl, prazoLbl);
        return box;
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

    public ImageView formatarIcone(String path) {
        ImageView iconeEdit = new ImageView(new Image(getClass().getResourceAsStream(path)));
        iconeEdit.setFitWidth(18);
        iconeEdit.setFitHeight(18);
        StackPane.setAlignment(iconeEdit, Pos.CENTER_RIGHT);
        StackPane.setMargin(iconeEdit, new Insets(0, 10, 0, 0));
        return iconeEdit;
    }

    /*
        Métodos para redirecionamento de telas
     */
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
        start(primaryStage);
    }

    private void telaProjetoColaborador(Stage primaryStage) {
        FXProjetosColaborando projetoColaborador = new FXProjetosColaborando();
        FXProjetosColaborando.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        projetoColaborador.start(primaryStage);
    }

}
