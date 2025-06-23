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
    private Button botaoMenu;
    private Rectangle fundoEscurecido;
    private VBox menuLateralRef;

    private boolean usuarioEhDono = true; // ← controle de permissão

    @Override
    public void start(Stage stage) {
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        StackPane root = new StackPane();

        VBox conteudo = criarConteudoPrincipal();
        ScrollPane scrollPane = new ScrollPane(conteudo);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        menuLateralRef = criarMenuLateral();
        menuLateralRef.setPrefWidth(250);
        menuLateralRef.setTranslateX(-260);
        StackPane.setAlignment(menuLateralRef, Pos.TOP_LEFT);

        fundoEscurecido = new Rectangle(larguraTela, alturaTela);
        fundoEscurecido.setFill(new Color(0, 0, 0, 0.5));
        fundoEscurecido.setVisible(false);
        fundoEscurecido.setOnMouseClicked(e -> alternarMenu(menuLateralRef));
        StackPane.setAlignment(fundoEscurecido, Pos.CENTER);

        VBox layoutPrincipal = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().addAll(layoutPrincipal, fundoEscurecido, menuLateralRef);

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

        botaoMenu = new Button("≡");
        botaoMenu.setStyle("-fx-font-size: 18px; -fx-background-color: transparent;");
        botaoMenu.setOnAction(e -> alternarMenu(menuLateralRef));

        Label title = new Label("Projeto galo eletrônico");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label status = new Label("Em andamento");
        status.setStyle("-fx-background-color: #dadada; -fx-padding: 5 10 5 10; -fx-background-radius: 8; -fx-font-size: 12px;");

        header.getChildren().addAll(botaoMenu, title, spacer, status);

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
            Button remover = new Button();
            remover.setGraphic(formatarIcone("/delete.jpg"));
            remover.setStyle("-fx-font-size: 12px;");
            remover.setOnAction(e -> System.out.println("Remover: " + username));
            acoes.getChildren().add(remover);
        }

        membroBox.getChildren().addAll(avatar, infos, espacador, acoes);
        return membroBox;
    }

    private VBox criarMenuLateral() {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(15));
        menu.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-min-width: 250px; -fx-max-width: 250px;");
        menu.setAlignment(Pos.TOP_CENTER);

        ImageView avatar = new ImageView(new Image("/images/Goo.png"));
        avatar.setFitHeight(80);
        avatar.setFitWidth(80);

        Label nome = new Label("Julia Fernandes");
        nome.setStyle("-fx-font-family: monospace; -fx-font-size: 16px;");

        Button fecharMenu = new Button("Fechar menu");
        fecharMenu.setMaxWidth(Double.MAX_VALUE);
        fecharMenu.setStyle("-fx-background-color: #ffaaaa; -fx-font-family: monospace;");
        fecharMenu.setOnAction(e -> alternarMenu(menu));

        menu.getChildren().addAll(avatar, nome,
                botaoMenuLateral("Projetos", "#d763f7"),
                botaoMenuLateral("Tarefas", "#c7c7c7"),
                botaoMenuLateral("Sair", "#d763f7"),
                fecharMenu
        );
        return menu;
    }

    private Button botaoMenuLateral(String texto, String cor) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: " + cor + "; -fx-font-family: monospace; -fx-font-size: 14px; -fx-background-radius: 10px;");
        return btn;
    }

    private void alternarMenu(VBox menuLateral) {
        TranslateTransition slide = new TranslateTransition(Duration.millis(300), menuLateral);
        FadeTransition fade = new FadeTransition(Duration.millis(300), fundoEscurecido);

        if (!menuVisivel) {
            slide.setToX(0);
            fade.setFromValue(0);
            fade.setToValue(1);
            fundoEscurecido.setVisible(true);
            botaoMenu.setText("X");
        } else {
            slide.setToX(-260);
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> fundoEscurecido.setVisible(false));
            botaoMenu.setText("≡");
        }

        slide.play();
        fade.play();
        menuVisivel = !menuVisivel;
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
