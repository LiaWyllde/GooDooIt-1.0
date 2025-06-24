package edu.curso.goodooit.app.view;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
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

    private boolean menuVisivel = false;

    StackPane root;

    private boolean usuarioEhDono = true; // ← controle de permissão

    @Override
    public void start(Stage stage) {
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        VBox conteudo = criarConteudoPrincipal();
        ScrollPane scrollPane = new ScrollPane(conteudo);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox layoutPrincipal = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);


        root = new StackPane(layoutPrincipal);

        Scene scene = new Scene(root, larguraTela, alturaTela * 0.9);
        stage.setScene(scene);
        stage.setTitle("Visualizar Membros do Projeto");
        stage.show();
    }

    private VBox criarConteudoPrincipal() {
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #b39ddb; -fx-font-family: monospace;");

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);


        Label title = new Label("Projeto galo eletrônico");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label status = new Label("Em andamento");
        status.setStyle("-fx-background-color: #dadada; -fx-padding: 5 10 5 10; -fx-background-radius: 8; -fx-font-size: 12px;");

        header.getChildren().addAll(title, spacer, status);

        VBox painelMembros = new VBox();
        painelMembros.setAlignment(Pos.CENTER);
        painelMembros.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        painelMembros.setPadding(new Insets(15));

        Label titulo = new Label("Total de membros no projeto");
        titulo.setStyle("-fx-text-fill: black; -fx-font-size: 13px;");

        Label quantidade = new Label("5");
        quantidade.setStyle("-fx-text-fill: black; -fx-font-size: 26px; -fx-font-weight: bold;");

        Label rodape = new Label("Membros");
        rodape.setStyle("-fx-text-fill: black; -fx-font-size: 13px;");

        painelMembros.getChildren().addAll(titulo, quantidade, rodape);

        VBox blocoMembros = new VBox(10);
        blocoMembros.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        blocoMembros.setPadding(new Insets(15));

        Label subtitulo = new Label("Exibindo todos os membros");
        subtitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: black;");
        blocoMembros.getChildren().add(subtitulo);

        blocoMembros.getChildren().addAll(
            criarMembro("Gustavo Henrique da Silva", "GustavoHenrique01"),
            criarMembro("João Francisco Alves", "JoaoM05"),
            criarMembro("Julia Victoria da Silva", "Juliavds"),
            criarMembro("Kauan Oliveira", "Kauan01"),
            criarMembro("Nicolas Domingos da Silva", "NicolasDomingos89")
        );


        // Botão "Convidar novo membro" (somente para o dono)
        Button convidar = new Button("Convidar novo membro");
        convidar.setMaxWidth(Double.MAX_VALUE);
        convidar.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-font-weight: bold;");
        convidar.setOnAction(e -> System.out.println("Convidar novo membro..."));

        Button voltar = new Button("Voltar");
        voltar.setMaxWidth(Double.MAX_VALUE);
        estiloBotaoRoxo(voltar);

        mainContent.getChildren().addAll(header, painelMembros, blocoMembros);

        if (usuarioEhDono) {
            mainContent.getChildren().add(convidar);
        }

        mainContent.getChildren().add(voltar);

        return mainContent;
    }

    private HBox criarMembro(String nome, String username) {
        HBox membroBox = new HBox(10);
        membroBox.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 10;");
        membroBox.setPadding(new Insets(10));
        membroBox.setAlignment(Pos.CENTER_LEFT);

        ImageView avatar = new ImageView(new Image("/images/Goo.png"));
        avatar.setFitHeight(40);
        avatar.setFitWidth(40);

        VBox infos = new VBox(5);
        Label nomeLabel = new Label(nome);
        nomeLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: black;");

        Label userLabel = new Label("Username: " + username);
        userLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");

        infos.getChildren().addAll(nomeLabel, userLabel);

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        HBox acoes = new HBox(5);
        acoes.setAlignment(Pos.CENTER_RIGHT);

        if (usuarioEhDono) {
            Button remover = new Button("Remover");
            remover.setStyle("-fx-font-size: 12px; -fx-color:red");
            remover.setOnAction(e -> System.out.println("Remover: " + username));
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

        Button btnReatribuir = new Button("Reatribuir");
        Button btnCancelar = new Button("Cancelar");

        campoUsuario.setStyle("-fx-background-radius: 20; -fx-border-radius: 20;");
        btnReatribuir.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 20;");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 20;");

        HBox botoes = new HBox(15, btnReatribuir, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudo.getChildren().addAll(titulo, instrucao, campoUsuario, botoes);

        StackPane fundo = new StackPane(conteudo);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        btnCancelar.setOnAction(e -> fundo.setVisible(false));
        btnReatribuir.setOnAction(e -> {
            fundo.setVisible(false);
        });

        return fundo;
    }


    private Button botaoMenuLateral(String texto, String cor) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: " + cor + "; -fx-font-family: monospace; -fx-font-size: 14px; -fx-background-radius: 10px;");
        return btn;
    }


    private void estiloBotaoRoxo(Button btn) {
        btn.setStyle("-fx-background-color: #8744c2; -fx-text-fill: white; -fx-background-radius: 10px; -fx-font-family: monospace;");
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
