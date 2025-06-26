package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.AllControllerRegistry;
import edu.curso.goodooit.app.controller.AutenticacaoController;
import edu.curso.goodooit.app.controller.ConviteController;
import edu.curso.goodooit.app.model.Convite;
import javafx.application.Application;
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

public class FXTelaConvite extends Application {

    private StackPane root;

    private static ConviteController conviteController;

    public static void setConviteController(ConviteController conviteController) {
        FXTelaConvite.conviteController = conviteController;
    }

    @Override
    public void start(Stage primaryStage) {
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        String nomeUsuario = AutenticacaoController.getAutenticado().getNome();
        VBox sidebar = criarSideBar(primaryStage, nomeUsuario);

        Label titulo = new Label("Convites");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-family: monospace;");

        VBox convitesContainer = new VBox(20);
        convitesContainer.setPadding(new Insets(10));
        convitesContainer.setAlignment(Pos.TOP_CENTER);

        ObservableList<Convite> convites = conviteController.convitesRecebidos();

        if (convites == null || convites.isEmpty()) {
            Label vazio = new Label("Você não possui convites no momento.");
            vazio.setStyle("-fx-font-size: 16px; -fx-font-family: monospace;");
            convitesContainer.getChildren().add(vazio);
        } else {
            for (Convite convite : convites) {
                convitesContainer.getChildren().add(criarCartaoConvite(convite, convitesContainer));
            }
        }

        ScrollPane scroll = new ScrollPane(convitesContainer);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle("-fx-background-color: transparent;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        VBox conteudo = new VBox(20, titulo, scroll);
        conteudo.setPadding(new Insets(20));
        conteudo.setAlignment(Pos.TOP_CENTER);
        conteudo.setStyle("-fx-background-color: #b39ddb; -fx-background-radius: 15px;");
        conteudo.setPrefWidth(10);

        StackPane painelCinza = new StackPane(conteudo);
        painelCinza.setStyle("-fx-background-color: #dddddd; -fx-background-radius: 20px;");
        painelCinza.setPadding(new Insets(15));
        HBox.setHgrow(painelCinza, Priority.ALWAYS);

        root = new StackPane(new HBox(sidebar, painelCinza));
        Scene scene = new Scene(root, larguraTela, alturaTela * 0.9);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Convites - GooDoolt");
        primaryStage.show();
    }

    private VBox criarCartaoConvite(Convite convite, VBox container) {
        VBox cartao = new VBox(10);
        cartao.setPadding(new Insets(15));
        cartao.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        cartao.setMaxWidth(600);

        var controller = AllControllerRegistry.getInstance().getConviteController();
        var remetente = controller.buscarRemetente(convite);
        var projeto = controller.buscarProjeto(convite);

        String nomeRemetente = (remetente != null) ? remetente.getNome() + " " + remetente.getSobrenome() : "Remetente desconhecido";
        String username = (remetente != null) ? remetente.getLogin() : "login inválido";
        String nomeProjeto = (projeto != null) ? projeto.getNome() : "projeto inválido";

        Label lblTexto = new Label(nomeRemetente + "\n" + username + "\nconvidou você para participar do projeto \"" + nomeProjeto + "\"");
        lblTexto.setStyle("-fx-font-family: monospace; -fx-font-size: 16px;");

        Button btnAceitar = new Button("Aceitar");
        Button btnRecusar = new Button("Recusar");

        btnAceitar.setStyle("-fx-background-color: #8fd18f; -fx-border-color: black; -fx-border-radius: 8; -fx-background-radius: 8;" +
                "-fx-font-family: monospace; -fx-padding: 5 15;");
        btnRecusar.setStyle("-fx-background-color: #f28f8f; -fx-border-color: black; -fx-border-radius: 8; -fx-background-radius: 8;" +
                "-fx-font-family: monospace; -fx-padding: 5 15;");

        btnAceitar.setOnAction(e -> {
            controller.aceitarConvite(convite.getProjetoID(), convite.getRemetenteID(), convite.getDestinatarioID());
            container.getChildren().remove(cartao);
        });

        btnRecusar.setOnAction(e -> {
            controller.recusarConvite(convite.getProjetoID(), convite.getRemetenteID(), convite.getDestinatarioID());
            container.getChildren().remove(cartao);
        });

        HBox botoes = new HBox(10, btnAceitar, btnRecusar);
        botoes.setAlignment(Pos.CENTER_RIGHT);

        cartao.getChildren().addAll(lblTexto, botoes);
        return cartao;
    }


    // Sidebar e Navegação

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
        Label email = new Label(conviteController.contarNovosConvites().toString());
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

    private void telaProjetoDono(Stage primaryStage) {
        FXMeusProjetos fxMeusProjetos = new FXMeusProjetos();
        FXMeusProjetos.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        FXMeusProjetos.setConviteController(AllControllerRegistry.getInstance().getConviteController());
        fxMeusProjetos.start(primaryStage);
    }

    private void telaConvites(Stage primaryStage) {
        start(primaryStage);
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
        FXEditarPerfil.setConviteController(AllControllerRegistry.getInstance().getConviteController());
        FXEditarPerfil.setCadastroController(AllControllerRegistry.getInstance().getCadastroController());
        editarPerfil.start(primaryStage);
    }

    private void telaTarefas(Stage primaryStage) {
        FXMinhasTarefas minhasTarefas = new FXMinhasTarefas();
        FXMinhasTarefas.setConviteController(AllControllerRegistry.getInstance().getConviteController());
        FXMinhasTarefas.setTarefaController(AllControllerRegistry.getInstance().getTarefaController());
        minhasTarefas.start(primaryStage);
    }

    private void telaProjetoColaborador(Stage primaryStage) {
        FXProjetosColaborando projetoColaborador = new FXProjetosColaborando();
        FXProjetosColaborando.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        FXProjetosColaborando.setConviteController(AllControllerRegistry.getInstance().getConviteController());
        projetoColaborador.start(primaryStage);
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

        ImageView ghost = new ImageView(new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true));
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
        ImageView icone = new ImageView(new Image(getClass().getResourceAsStream(path)));
        icone.setFitWidth(18);
        icone.setFitHeight(18);
        StackPane.setAlignment(icone, Pos.CENTER_RIGHT);
        StackPane.setMargin(icone, new Insets(0, 10, 0, 0));
        return icone;
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
}
