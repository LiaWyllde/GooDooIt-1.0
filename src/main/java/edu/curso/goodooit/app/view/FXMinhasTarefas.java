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

public class FXMinhasTarefas extends Application {

    @Override
    public void start(Stage primaryStage) {
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();
        
        primaryStage.setTitle("Tarefas - Julia Fernandes");

        // Sidebar (esquerda)
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #eeeeee;");
        sidebar.setPrefWidth(220);
        sidebar.setAlignment(Pos.TOP_CENTER);

        // Avatar
        Image avatarImage = new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true);
        ImageView avatarView = new ImageView(avatarImage);

        Label nome = new Label("Julia Fernandes");
        nome.setStyle("-fx-font-size: 18px; -fx-font-family: monospace;");

        HBox notificacoes = new HBox(20);
        notificacoes.setAlignment(Pos.CENTER);
        Label sino = new Label("🔔 5");
        Label email = new Label("✉️ 1");
        sino.setStyle("-fx-font-size: 16px;");
        email.setStyle("-fx-font-size: 16px;");
        notificacoes.getChildren().addAll(sino, email);

        sidebar.getChildren().addAll(avatarView, nome, notificacoes);
        sidebar.getChildren().addAll(
                botaoMenu("Meus projetos", true),
                botaoMenu("Colaborando", false),
                botaoMenu("Equipes", true),
                botaoMenu("Tarefas", false, true),
                botaoMenu("Editar perfil", true),
                botaoMenu("Sair", false)
        );

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
        HBox root = new HBox();
        root.setPadding(new Insets(10));
        root.getChildren().addAll(sidebar, painelCinza);
        HBox.setHgrow(painelCinza, Priority.ALWAYS);

        Scene scene = new Scene(root, larguraTela, alturaTela * 0.9);
        primaryStage.setScene(scene);
        primaryStage.show();
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

    public static void main(String[] args) {
        launch(args);
    }
}
