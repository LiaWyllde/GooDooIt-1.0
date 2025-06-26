package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.AllControllerRegistry;
import edu.curso.goodooit.app.controller.ConviteController;
import edu.curso.goodooit.app.controller.VisualizarProjetoController;
import edu.curso.goodooit.app.model.Projeto;
import edu.curso.goodooit.app.model.Usuario;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXVisualizarMembrosProjeto extends Application {

    private StackPane root;
    private Projeto projeto;

    private static VisualizarProjetoController visualizarProjetoController;
    private static ConviteController conviteController;

    private boolean isDono;

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public static void setVisualizarProjetoController(VisualizarProjetoController visualizarProjetoController) {
        FXVisualizarMembrosProjeto.visualizarProjetoController = visualizarProjetoController;
    }

    public static void setConviteController(ConviteController conviteController) {
        FXVisualizarMembrosProjeto.conviteController = conviteController;
    }

    @Override
    public void start(Stage primaryStage) {
        isDono = visualizarProjetoController.VerificarDonoDoProjeto(projeto);
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        VBox conteudo = criarConteudoPrincipal(primaryStage);
        ScrollPane scrollPane = new ScrollPane(conteudo);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox layoutPrincipal = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root = new StackPane(layoutPrincipal);

        Scene scene = new Scene(root, larguraTela, alturaTela * 0.9);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Visualizar Membros do Projeto");
        primaryStage.show();
    }

    private VBox criarConteudoPrincipal(Stage primaryStage) {
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #b39ddb; -fx-font-family: monospace;");

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label lblProjeto = new Label("Projeto: ");
        Label tituloProjeto = new Label(projeto.getNome());
        tituloProjeto.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label status = new Label(projeto.getStatus().getTitulo());
        status.setStyle("-fx-background-color: #dadada; -fx-padding: 5 10 5 10; -fx-background-radius: 8; -fx-font-size: 12px;");

        header.getChildren().addAll(lblProjeto, tituloProjeto, spacer, status);

        VBox painelMembros = new VBox();
        painelMembros.setAlignment(Pos.CENTER);
        painelMembros.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        painelMembros.setPadding(new Insets(15));

        Label totalMembros = new Label("Total de membros no projeto");
        totalMembros.setStyle("-fx-text-fill: black; -fx-font-size: 13px;");

        Label quantidade = new Label(visualizarProjetoController.contarMembrosProjeto(projeto).toString());
        quantidade.setStyle("-fx-text-fill: black; -fx-font-size: 26px; -fx-font-weight: bold;");

        Label rodape = new Label("Membros");
        rodape.setStyle("-fx-text-fill: black; -fx-font-size: 13px;");

        painelMembros.getChildren().addAll(totalMembros, quantidade, rodape);

        VBox blocoMembros = new VBox(10);
        blocoMembros.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        blocoMembros.setPadding(new Insets(15));

        Label subtitulo = new Label("Exibindo todos os membros");
        subtitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: black;");
        blocoMembros.getChildren().add(subtitulo);


        ObservableList<Usuario> membros = visualizarProjetoController.listarMembrosDoProjeto(projeto);
        membros.forEach(membro -> {
            blocoMembros.getChildren().add(criarMembro(membro, primaryStage));
        });


        // Botão "Convidar novo membro" (somente para o dono)
        Button convidar = new Button("Convidar novo membro");
        convidar.setMaxWidth(Double.MAX_VALUE);
        convidar.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-font-weight: bold;");
        convidar.setOnAction(e -> abrirModalConvite(primaryStage));

        Button voltar = new Button("Voltar");
        voltar.setMaxWidth(Double.MAX_VALUE);
        estiloBotaoRoxo(voltar);

        voltar.setOnAction(evento -> {
            telaVisualizarProjeto(projeto, primaryStage);
        });

        mainContent.getChildren().addAll(header, painelMembros, blocoMembros);

        if (isDono) {
            mainContent.getChildren().add(convidar);
        }

        mainContent.getChildren().add(voltar);

        return mainContent;
    }

    private HBox criarMembro(Usuario membro, Stage primaryStage) {
        HBox membroBox = new HBox(10);
        membroBox.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 10;");
        membroBox.setPadding(new Insets(10));
        membroBox.setAlignment(Pos.CENTER_LEFT);

        ImageView avatar = new ImageView(new Image("/images/Goo.png"));
        avatar.setFitHeight(40);
        avatar.setFitWidth(40);

        VBox infos = new VBox(5);
        Label nomeLabel = new Label(membro.getNome());
        nomeLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: black;");

        Label userLabel = new Label("Username: " + membro.getLogin());
        userLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");

        infos.getChildren().addAll(nomeLabel, userLabel);

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        HBox acoes = new HBox(5);
        acoes.setAlignment(Pos.CENTER_RIGHT);

        if (isDono) {
            Button remover = new Button("Remover");
            remover.setStyle("-fx-font-size: 12px; -fx-color:red");
            remover.setOnAction(e -> {
                visualizarProjetoController.removerMembroDoProjeto(membro, projeto);
                start(primaryStage);
            });
            acoes.getChildren().add(remover);
        }

        membroBox.getChildren().addAll(avatar, infos, espacador, acoes);
        return membroBox;
    }

    public StackPane criarModalConvite(Stage primaryStage) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudo = new VBox(15);
        conteudo.setPadding(new Insets(30));
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setMaxWidth(largura * 0.35);
        conteudo.setMaxHeight(altura * 0.4);
        conteudo.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");

        Label titulo = new Label("Convidar novo membro");
        titulo.setStyle("-fx-font-size: 22px; -fx-text-fill: #6A0DAD; -fx-font-weight: bold;");

        Label instrucao = new Label("Digite usuário do novo responsável pela tarefa:");
        instrucao.setStyle("-fx-font-size: 14px;");

        TextField campoUsuario = new TextField();

        Button btnConvidar = new Button("Convidar");
        Button btnCancelar = new Button("Cancelar");

        campoUsuario.setStyle("-fx-background-radius: 20; -fx-border-radius: 20;");
        btnConvidar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 20;");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 20;");

        HBox botoes = new HBox(15, btnConvidar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudo.getChildren().addAll(titulo, instrucao, campoUsuario, botoes);

        StackPane fundo = new StackPane(conteudo);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        btnCancelar.setOnAction(e -> fundo.setVisible(false));
        btnConvidar.setOnAction(e -> {
            conviteController.convidarUsuarioParaProjeto(campoUsuario.getText().trim(), projeto.getID());
            fundo.setVisible(false);
        });

        return fundo;
    }

    private void abrirModalConvite(Stage primaryStage) {
        StackPane fundo = criarModalConvite(primaryStage);
        root.getChildren().add(fundo);
        fundo.setVisible(true);
    }

    private void telaVisualizarProjeto(Projeto projeto, Stage primaryStage) {
        FXVisualizarProjeto visualizarProjeto = new FXVisualizarProjeto();
        visualizarProjeto.setProjeto(projeto);
        FXVisualizarProjeto.setVisualizarProjetoController(AllControllerRegistry.getInstance().getVisualizarProjetoController());
        FXVisualizarProjeto.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        FXVisualizarProjeto.setTarefaController(AllControllerRegistry.getInstance().getTarefaController());
        visualizarProjeto.start(primaryStage);
    }

    private void estiloBotaoRoxo(Button btn) {
        btn.setStyle("-fx-background-color: #8744c2; -fx-text-fill: white; -fx-background-radius: 10px; -fx-font-family: monospace;");
    }
}
