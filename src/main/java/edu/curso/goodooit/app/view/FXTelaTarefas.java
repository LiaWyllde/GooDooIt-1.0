package edu.curso.goodooit.ui.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class FXTelaTarefas extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tarefas - Julia Fernandes");

        // --- Sidebar ---
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #eeeeee;");
        sidebar.setPrefWidth(200);
        sidebar.setAlignment(Pos.TOP_CENTER);

        // Avatar (simbolicamente com um emoji ou imagem genérica)
        Label avatar = new Label("👻");
        avatar.setStyle("-fx-font-size: 48px;");

        Label nome = new Label("Julia Fernandes");
        nome.setStyle("-fx-font-size: 16px; -fx-font-family: monospace;");

        // Ícones de notificação
        HBox notificacoes = new HBox(10);
        notificacoes.setAlignment(Pos.CENTER);
        Label sino = new Label("🔔 5");
        Label email = new Label("✉️ 1");
        notificacoes.getChildren().addAll(sino, email);

        // Botões de menu
        sidebar.getChildren().addAll(avatar, nome, notificacoes);
        sidebar.getChildren().addAll(
                botaoMenu("Meus projetos", true),
                botaoMenu("Colaborando", false),
                botaoMenu("Equipes", true),
                botaoMenu("Tarefas", false, true),
                botaoMenu("Editar perfil", true),
                botaoMenu("Sair", false)
        );

        // --- Área de conteúdo principal ---
        VBox areaPrincipal = new VBox(20);
        areaPrincipal.setPadding(new Insets(20));
        areaPrincipal.setStyle("-fx-background-color: #b39ddb;");
        areaPrincipal.setPrefWidth(600);

        Label titulo = new Label("Minhas tarefas");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-family: monospace;");

        TextField campoBusca = new TextField();
        campoBusca.setPromptText("buscar tarefa");
        campoBusca.setStyle("-fx-font-family: monospace; -fx-background-radius: 10;");
        campoBusca.setPrefHeight(35);
        campoBusca.setMaxWidth(Double.MAX_VALUE);

        // Scroll de tarefas
        VBox tarefas = new VBox(15);
        tarefas.setPadding(new Insets(10));
        tarefas.setFillWidth(true); // Garante expansão horizontal do conteúdo
        tarefas.setStyle("-fx-background-color: transparent;");

        tarefas.getChildren().addAll(
                blocoTarefa("Desenvolver o som do cocoricó", "aguardando dispositivo", "Alta", "24/06/2025"),
                blocoTarefa("Iniciar testes", "aguardando furão", "Muito alta", "24/06/2025"),
                blocoTarefa("Iniciar testes", "aguardando galo", "Alta", "24/06/2025")
        );

        ScrollPane scrollTarefas = new ScrollPane();
        scrollTarefas.setContent(tarefas);
        scrollTarefas.setFitToWidth(true);
        scrollTarefas.setPannable(true);
        scrollTarefas.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTarefas.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollTarefas.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        VBox.setVgrow(scrollTarefas, Priority.ALWAYS);


        areaPrincipal.getChildren().addAll(titulo, campoBusca, scrollTarefas);

        // Layout geral
        HBox root = new HBox();
        root.getChildren().addAll(sidebar, areaPrincipal);
        HBox.setHgrow(areaPrincipal, Priority.ALWAYS);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- Método para gerar os botões de menu lateral ---
    private Button botaoMenu(String texto, boolean roxo) {
        return botaoMenu(texto, roxo, false);
    }

    private Button botaoMenu(String texto, boolean roxo, boolean selecionado) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-font-family: monospace; -fx-font-size: 14px;" +
                (selecionado ? "-fx-border-color: black; -fx-border-width: 2;" : "") +
                (roxo ? "-fx-background-color: #d681f0; -fx-text-fill: black;" : "-fx-background-color: #cccccc;"));
        return btn;
    }

    // --- Método para gerar os blocos de tarefa ---
    private VBox blocoTarefa(String titulo, String status, String prioridade, String prazo) {
        VBox box = new VBox(5);
        box.setPadding(new Insets(12));
        box.setStyle("-fx-background-color: white; -fx-background-radius: 12px;");
        box.setMaxWidth(Double.MAX_VALUE);

        Label tituloLbl = new Label(titulo);
        tituloLbl.setStyle("-fx-font-family: monospace; -fx-font-size: 16px; -fx-font-weight: bold;");
        tituloLbl.setWrapText(true); // Permite quebra de linha

        Label statusLbl = new Label("Status: " + status);
        Label prioridadeLbl = new Label("Prioridade: " + prioridade);
        Label prazoLbl = new Label("Prazo de conclusão: " + prazo);

        for (Label l : new Label[]{statusLbl, prioridadeLbl, prazoLbl}) {
            l.setStyle("-fx-font-family: monospace;");
            l.setWrapText(true);
        }

        box.getChildren().addAll(tituloLbl, statusLbl, prioridadeLbl, prazoLbl);
        return box;
    }


    public static void main(String[] args) {
        launch(args);
    }
}

