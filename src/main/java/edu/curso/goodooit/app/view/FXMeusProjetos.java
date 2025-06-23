package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.AutenticacaoController;
import edu.curso.goodooit.app.controller.ConviteController;
import edu.curso.goodooit.app.controller.MeusProjetosController;
import edu.curso.goodooit.app.model.Projeto;
import edu.curso.goodooit.app.model.Usuario;
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

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class FXMeusProjetos extends Application {

    private VBox areaPrincipal;
    private StackPane modalProjeto;
    private StackPane modalConvites;
    private StackPane root;


    private static MeusProjetosController meusProjetosController;
    private static ConviteController conviteController;

    public static void setMeusProjetosController(MeusProjetosController mpc) {
        meusProjetosController = mpc;
    }

    public static void setConviteController(ConviteController conviteController) {
        FXMeusProjetos.conviteController = conviteController;
    }

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
        
        ImageView iconeNotificacao = formatarIcone("/images/notification.png");
        Label sino = new Label("5"); // Property notificação
        
        ImageView iconeConvite = formatarIcone("/images/envelope.jpg");
        Label email = new Label("1"); // Property email
        
        sino.setStyle("-fx-font-size: 16px;");
        email.setStyle("-fx-font-size: 16px;");
        notificacoes.getChildren().addAll(iconeConvite, email);

        sidebar.getChildren().addAll(avatarView, nome, notificacoes);

        Map<String, Runnable> botoes = new LinkedHashMap<>();
        botoes.put("Projetos", () -> telaProjetoDono(primaryStage));
        botoes.put("Colaborando", () -> telaProjetoColaborador(primaryStage));
        botoes.put("Equipes", () -> telaEquipes(primaryStage));
        botoes.put("Tarefas", () -> telaTarefas(primaryStage));
        botoes.put("Editar Perfil", () -> telaEditarPerfil(primaryStage));
        botoes.put("Sair", () -> telaSair(primaryStage));

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
            projetosContainer.getChildren().add(criarBlocoProjeto(p, concluidas, outras));
        }
        //Scroll para poder visualizar a tela inteira com muitos projetos
        ScrollPane scroll = new ScrollPane(projetosContainer);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        Button btnCriar = new Button("Criar novo projeto");
        estilizarBotao(btnCriar);

        Projeto projetoVazio = new Projeto();
        btnCriar.setOnAction(e -> abrirModalProjeto(projetoVazio));

        areaPrincipal.getChildren().addAll(titulo, projetosContainer, btnCriar);

        StackPane painelCinza = new StackPane(areaPrincipal);
        painelCinza.setStyle("-fx-background-color: #dddddd; -fx-background-radius: 20px;");
        painelCinza.setPadding(new Insets(15));
        HBox.setHgrow(painelCinza, Priority.ALWAYS);

        modalConvites = criarModalVisualizarConvites(() -> System.out.println("Convite"));

        root = new StackPane(new HBox(sidebar, painelCinza), modalConvites);
        Scene scene = new Scene(root, larguraTela, alturaTela * 0.9);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox criarBlocoProjeto(Projeto p, Integer concluidas, Integer outras) {
        VBox bloco = new VBox(10);
        bloco.setPadding(new Insets(20));
        bloco.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        bloco.setMaxWidth(Double.MAX_VALUE);

        Label nomeProjeto = new Label(p.getNome());
        Label status = new Label("Status: " + p.getStatus().toString());
        Label lblConcluidas = new Label("Tarefas concluídas: " + concluidas);
        Label lblOutras = new Label("Outras tarefas: " + outras);

        for (Label lbl : new Label[]{nomeProjeto, status, lblConcluidas, lblOutras}) {
            lbl.setStyle("-fx-font-family: monospace; -fx-font-size: 18px; -fx-text-fill: black;");
        }

//        Label icone = new Label("✎");
        Button icone = new Button("Editar");
        ImageView iconeEdicao = formatarIcone("/images/edit.jpg");
        icone.setStyle("-fx-font-size: 18px; -fx-text-fill: gray; -fx-font-family: monospace;");

        icone.setOnMouseClicked(e -> {
            StackPane modal = criarModalProjeto(p);
            root.getChildren().add(modal);
            abrirModalProjeto(p,modal);
        });

        StackPane stack = new StackPane(bloco, icone);
        StackPane.setAlignment(icone, Pos.TOP_RIGHT);
        StackPane.setMargin(icone, new Insets(10));

        bloco.getChildren().addAll(nomeProjeto, status, lblConcluidas, lblOutras);
        return new VBox(stack);
    }

    private void abrirModalProjeto(Projeto p, StackPane modal) {
        TextField tfNome = new TextField(p.getNome());
        TextArea taDescricao = new TextArea(p.getDescricao());
        DatePicker dpInicio = new DatePicker(p.getDataInicio());
        DatePicker dpFim = new DatePicker(p.getDataFim());
        modal.setVisible(true);
    }

    private void abrirModalProjeto(Projeto p) {
        p.setID(1);
        modalProjeto = criarModalProjeto(p);
        root.getChildren().add(modalProjeto);
        modalProjeto.setVisible(true);
    }

    public StackPane criarModalProjeto(Projeto p) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudoModal = new VBox(10);
        conteudoModal.setPadding(new Insets(20));
        conteudoModal.setAlignment(Pos.CENTER_LEFT);
        conteudoModal.setMaxWidth(largura * 0.5);
        conteudoModal.setMaxHeight(altura * 0.5);
        conteudoModal.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");

        TextField tfNome = new TextField(p.getNome());
        TextArea taDescricao = new TextArea(p.getDescricao());
        DatePicker dpInicio = new DatePicker(p.getDataInicio());
        DatePicker dpFim = new DatePicker(p.getDataFim());

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        HBox botoes = new HBox(20, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudoModal.getChildren().addAll(
                new Label("Nome do Projeto"), tfNome,
                new Label("Descrição"), taDescricao,
                new Label("Prazo previsto para início"), dpInicio,
                new Label("Prazo previsto para finalização"), dpFim,
                botoes
        );

        StackPane fundo = new StackPane(conteudoModal);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        // Actions para atualizar o projeto
        btnSalvar.setOnAction(e -> {
            meusProjetosController.salvarProjeto(p.getID());
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

    private StackPane criarModalVisualizarConvites(Runnable acaoAceitar) {
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

        Button btnAceitar = new Button("Aceitar");
        Button btnRecusar = new Button("Recusar");

        btnAceitar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 20;");
        btnRecusar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 20;");

        HBox botoes = new HBox(15, btnAceitar, btnRecusar);
        botoes.setAlignment(Pos.CENTER);

        conteudoModal.getChildren().addAll(ghost, nomeCompleto, usuario, mensagem, projeto, botoes);

        StackPane fundo = new StackPane(conteudoModal);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        btnRecusar.setOnAction(e -> fundo.setVisible(false));
        btnAceitar.setOnAction(e -> {
            acaoAceitar.run();
            fundo.setVisible(false);
        });

        return fundo;
    }

    public ImageView formatarIcone(String path) {
        ImageView iconeEdit = new ImageView(new Image(getClass().getResourceAsStream(path)));
        iconeEdit.setFitWidth(18);
        iconeEdit.setFitHeight(18);
        StackPane.setAlignment(iconeEdit, Pos.CENTER_RIGHT);
        StackPane.setMargin(iconeEdit, new Insets(0, 10, 0, 0));
        return iconeEdit;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
