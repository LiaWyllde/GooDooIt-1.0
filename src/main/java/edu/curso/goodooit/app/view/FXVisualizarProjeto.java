package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.VisualizarProjetoController;
import edu.curso.goodooit.app.model.Projeto;
import edu.curso.goodooit.app.model.Status;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FXVisualizarProjeto extends Application {
    //ToDo: botar pra funcionar de verdade

    private static VisualizarProjetoController visualizarProjetoController;
    private Projeto projeto;

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public static void setVisualizarProjetoController(VisualizarProjetoController visualizarProjetoController) {
        FXVisualizarProjeto.visualizarProjetoController = visualizarProjetoController;
    }

    @Override
    public void start(Stage stage) {
        boolean isDono = visualizarProjetoController.VerificarDonoDoProjeto(projeto);

        // Header com logo e status
        HBox header = new HBox(20);
        header.setPadding(new Insets(20, 20, 0, 20));
        header.setAlignment(Pos.CENTER_LEFT);

        Label nomeProjeto = new Label("Projeto galo eletrônico");
        nomeProjeto.setFont(Font.font("Courier New", 20));

        ComboBox<Status> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(visualizarProjetoController.getAllStatus());
        statusComboBox.setValue(statusComboBox.getItems().get(projeto.getStatusProjetoID()-1));
        statusComboBox.setStyle("-fx-font-family: 'Courier New'; -fx-background-radius: 10px;");
        statusComboBox.setVisible(isDono);

        Label statusTexto = new Label(projeto.getStatus().toString());
        statusTexto.setFont(Font.font("Courier New", 16));
        statusTexto.setVisible(!isDono);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(nomeProjeto, spacer, isDono ? statusComboBox : statusTexto);

        // Descrição
        TextField descricao = new TextField("Projeto inicial para confecção dos modelos do Galotron3000");
        descricao.setMaxWidth(700);
        descricao.setFont(Font.font("Courier New", 16));
        descricao.setEditable(false);
        descricao.setStyle("-fx-background-radius: 10px; -fx-background-color: white;");

        // Datas
        HBox datas = new HBox(40);
        datas.setAlignment(Pos.CENTER);
        datas.setPadding(new Insets(10));

        Label inicio = new Label("Início  20/04/2025");
        Label fim = new Label("Fim  22/10/2027");
        for (Label l : new Label[]{inicio, fim}) {
            l.setFont(Font.font("Courier New", 16));
            l.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-padding: 5 15;");
        }
        datas.getChildren().addAll(inicio, fim);

        // Informações do projeto
        VBox infoBox = new VBox(10);
        infoBox.setPadding(new Insets(15));
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        infoBox.setMaxWidth(500);

        Label tituloInfo = new Label("Informações do projeto");
        tituloInfo.setFont(Font.font("Courier New", 18));

        VBox infoMembros = new VBox(5);
        infoMembros.setAlignment(Pos.CENTER);

        VBox infoTarefas = new VBox(5);
        infoTarefas.setAlignment(Pos.CENTER);

        HBox infos = new HBox(40);
        infos.setAlignment(Pos.CENTER);

        Label numMembros = new Label("10");
        Label membros = new Label("Membros do projeto");
        Label numConcluidas = new Label("5");
        Label concluidas = new Label("Tarefas concluídas");
        for (Label l : new Label[]{membros, concluidas, numMembros, numConcluidas}) {
            l.setFont(Font.font("Courier New", 16));
            l.setAlignment(Pos.CENTER);
        }

        infoMembros.getChildren().addAll(numMembros, membros);
        infoTarefas.getChildren().addAll(numConcluidas, concluidas);
        infos.getChildren().addAll(infoMembros, infoTarefas);
        infoBox.getChildren().addAll(tituloInfo, infos);

        // Botão "Criar nova tarefa"
        Button btnNovaTarefa = new Button("Criar nova tarefa");
        btnNovaTarefa.setStyle("-fx-background-color: #d8d8d8; -fx-font-family: 'Courier New'; -fx-background-radius: 10px;");
        btnNovaTarefa.setMaxWidth(300);

        // Área de tarefas
        VBox tarefasContainer = new VBox(10);
        tarefasContainer.setPadding(new Insets(20));
        tarefasContainer.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        tarefasContainer.setMaxWidth(700);
        tarefasContainer.setMinHeight(400);
        tarefasContainer.setAlignment(Pos.TOP_CENTER);

        Label tituloTarefas = new Label("Tarefas");
        tituloTarefas.setFont(Font.font("Courier New", 18));
        tarefasContainer.getChildren().add(tituloTarefas);

        // Botões de edição/exclusão
        HBox botoesAcoes = new HBox(40);
        botoesAcoes.setAlignment(Pos.CENTER);
        botoesAcoes.setPadding(new Insets(10));

        Button btnEditar = new Button("Editar projeto");
        Button btnExcluir = new Button("Excluir projeto");
        Button btnVoltar = new  Button("Voltar");
        estilizarBotaoRoxo(btnVoltar);
        estilizarBotaoRoxo(btnEditar);
        estilizarBotaoRoxo(btnExcluir);
        btnEditar.setVisible(isDono);
        btnExcluir.setVisible(isDono);

        botoesAcoes.getChildren().addAll(btnVoltar, btnEditar, btnExcluir);

        // Container principal
        VBox root = new VBox(20,
                header, descricao, datas, infoBox, btnNovaTarefa, tarefasContainer, botoesAcoes
        );
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #c8afd6;");
        root.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(root, 1600, 900);
        stage.setTitle("Visualizar Projeto");
        stage.setScene(scene);
        stage.show();
    }

    private void estilizarBotaoRoxo(Button btn) {
        btn.setFont(Font.font("Courier New", 16));
        btn.setStyle(
                "-fx-background-color: #7e39d6;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 8 20;"
        );
    }

    public static void main(String[] args) {
        launch();
    }
}
