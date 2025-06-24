package edu.curso.goodooit.app.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class FXTelaConvite extends Application {

    private StackPane root;

    @Override
    public void start(Stage primaryStage) {
        double larguraTela = 1600;
        double alturaTela = 1600;

        VBox sidebar = criarSidebar(primaryStage, "Julia Fernandes");

        Label titulo = new Label("Convites");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-family: monospace;");

        VBox convitesContainer = new VBox(20);
        convitesContainer.setPadding(new Insets(10));
        convitesContainer.setAlignment(Pos.TOP_CENTER);

        // Simulando convites
        for (int i = 0; i < 10; i++) {
            convitesContainer.getChildren().add(criarCartaoConvite(
                    "17/06/2025", "Gustavo Henrique", "GustavoHenrique01", "Furão Biônico"
            ));
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

    private VBox criarSidebar(Stage primaryStage, String nomeCompleto) {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #eeeeee;");
        sidebar.setPrefWidth(320);
        sidebar.setAlignment(Pos.TOP_CENTER);

        ImageView avatarView = new ImageView(new Image(getClass().getResourceAsStream("/images/LogoComTexto.png"), 100, 100, true, true));
        Label nome = new Label(nomeCompleto);
        nome.setStyle("-fx-font-size: 18px; -fx-font-family: monospace;");

        HBox notificacoes = new HBox(20);
        notificacoes.setAlignment(Pos.CENTER);

        ImageView iconeConvite = new ImageView(new Image(getClass().getResourceAsStream("/images/envelope.jpg"), 18, 18, true, true));
        Label email = new Label("1");
        email.setCursor(Cursor.HAND);
        email.setStyle("-fx-font-size: 16px;");

        notificacoes.getChildren().addAll(iconeConvite, email);
        sidebar.getChildren().addAll(avatarView, nome, notificacoes);

        Map<String, Runnable> botoes = new LinkedHashMap<>();
        botoes.put("Meus Projetos", () -> telaProjetoDono(primaryStage));
        botoes.put("Colaborando", () -> telaProjetoColaborador(primaryStage));
        botoes.put("Tarefas", () -> telaTarefas(primaryStage));
        botoes.put("Editar Perfil", () -> telaEditarPerfil(primaryStage));
        botoes.put("Sair", () -> telaSair(primaryStage));

        AtomicBoolean roxoFlag = new AtomicBoolean(true);
        botoes.forEach((label, action) -> {
            Button btn = new Button(label);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setPrefHeight(40);
            btn.setStyle("-fx-font-family: monospace; -fx-font-size: 14px; -fx-background-radius: 15;" +
                    (roxoFlag.get() ? "-fx-background-color: #d681f0; -fx-text-fill: black;" : "-fx-background-color: #cccccc;"));
            btn.setOnAction(e -> action.run());
            sidebar.getChildren().add(btn);
            roxoFlag.set(!roxoFlag.get());
        });

        return sidebar;
    }

    private VBox criarCartaoConvite(String data, String nome, String username, String projeto) {
        VBox cartao = new VBox(10);
        cartao.setPadding(new Insets(15));
        cartao.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        cartao.setMaxWidth(600);

        Label lblData = new Label(data);
        lblData.setStyle("-fx-font-family: monospace; -fx-font-size: 14px;");

        Label lblTexto = new Label(nome + "\n" + username + "\nconvidou você para participar do projeto \"" + projeto + "\"");
        lblTexto.setStyle("-fx-font-family: monospace; -fx-font-size: 16px;");

        Button btnAceitar = new Button("Aceitar");
        Button btnRecusar = new Button("Recusar");

        btnAceitar.setStyle("-fx-background-color: #8fd18f; -fx-border-color: black; -fx-border-radius: 8; -fx-background-radius: 8;" +
                "-fx-font-family: monospace; -fx-padding: 5 15;");
        btnRecusar.setStyle("-fx-background-color: #f28f8f; -fx-border-color: black; -fx-border-radius: 8; -fx-background-radius: 8;" +
                "-fx-font-family: monospace; -fx-padding: 5 15;");

        HBox botoes = new HBox(10, btnAceitar, btnRecusar);
        botoes.setAlignment(Pos.CENTER_RIGHT);

        cartao.getChildren().addAll(lblData, lblTexto, botoes);
        return cartao;
    }

    private void telaProjetoDono(Stage stage) {}
    private void telaProjetoColaborador(Stage stage) {}
    private void telaTarefas(Stage stage) {}
    private void telaEditarPerfil(Stage stage) {}
    private void telaSair(Stage stage) {}

    public static void main(String[] args) {
        launch(args);
    }
}
