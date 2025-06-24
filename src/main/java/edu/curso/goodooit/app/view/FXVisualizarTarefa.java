package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.AllControllerRegistry;
import edu.curso.goodooit.app.controller.MeusProjetosController;
import edu.curso.goodooit.app.controller.VisualizarProjetoController;
import edu.curso.goodooit.app.model.Projeto;
import edu.curso.goodooit.app.model.Status;
import edu.curso.goodooit.app.model.Tarefa;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FXVisualizarTarefa extends Application {

    private static VisualizarProjetoController visualizarProjetoController;
    private static MeusProjetosController meusProjetosController;
    private Projeto projeto;
    private Tarefa tarefa;

    private StackPane root;

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public static void setVisualizarProjetoController(VisualizarProjetoController visualizarProjetoController) {
        FXVisualizarTarefa.visualizarProjetoController = visualizarProjetoController;
    }

    public static void setMeusProjetosController(MeusProjetosController meusProjetosController) {
        FXVisualizarTarefa.meusProjetosController = meusProjetosController;
    }

    @Override
    public void start(Stage primaryStage) {
        boolean isDono = visualizarProjetoController.VerificarDonoDoProjeto(projeto);

        // Header com logo e status
        HBox header = new HBox(20);
        header.setPadding(new Insets(20, 20, 0, 20));
        header.setAlignment(Pos.CENTER_LEFT);

        Label lblProjeto = new Label("Projeto: ");
        lblProjeto.setStyle("-fx-font-size: 20px; -fx-font-family: 'Monospace';");
        Label nomeProjeto = new Label(projeto.getNome());
        nomeProjeto.setStyle("-fx-font-size: 20px; -fx-font-family: 'Monospace';");

        ComboBox<Status> statusComboBox = new ComboBox<>();
        statusComboBox.setPrefWidth(200);
        statusComboBox.setStyle("-fx-font-size: 18px");
        statusComboBox.getItems().addAll(visualizarProjetoController.getAllStatus());
        //Aqui eu coloco o status para mostrar baseado no status do projeto, porém eu preciso usar um -1
        // para que esteja de acordo com o índice da Collection já que ela começa de 0 e o ID começa de 1
        statusComboBox.setValue(statusComboBox.getItems().get(tarefa.getStatusTarefaID() - 1));
        statusComboBox.setStyle("-fx-font-family: 'monospace'; -fx-background-radius: 10px;");
        statusComboBox.setVisible(true);

        statusComboBox.setOnAction(event -> {
            tarefa.setStatus(statusComboBox.getSelectionModel().getSelectedItem());
            tarefa.setStatusTarefaID(statusComboBox.getSelectionModel().getSelectedItem().getID());
            meusProjetosController.editarProjeto(projeto);
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(lblProjeto, nomeProjeto, spacer, statusComboBox);

        // Descrição
        Label lblDescricao = new Label("Descrição");
        lblDescricao.setStyle("-fx-font-size: 20px; -fx-font-family: 'Monospace';");
        TextField descricao = new TextField(tarefa.getDescricao());
        descricao.setMaxWidth(700);
        descricao.setStyle("-fx-font-size: 18px");
        descricao.setEditable(false);
        descricao.setStyle("-fx-background-radius: 10px; -fx-background-color: white;");

        VBox desc = new VBox(lblDescricao, descricao);
        VBox.setVgrow(desc, Priority.ALWAYS);
        desc.setAlignment(Pos.CENTER);

        // Datas
        HBox datas = new HBox(40);
        datas.setAlignment(Pos.CENTER);
        datas.setPadding(new Insets(10));

        Label inicio = new Label("Início " + tarefa.getDataInicio().toString());
        Label fim = new Label("Fim " + tarefa.getDataFim().toString());
        for (Label l : new Label[]{inicio, fim}) {
            l.setFont(Font.font("Courier New", 16));
            l.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-padding: 5 15;");
        }
        datas.getChildren().addAll(inicio, fim);

        // Área de tarefas
        VBox tarefasContainer = new VBox(10);
        tarefasContainer.setPadding(new Insets(20));
        tarefasContainer.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        tarefasContainer.setMaxWidth(Double.MAX_VALUE);
        tarefasContainer.setFillWidth(true);
        tarefasContainer.setMinHeight(500);
        tarefasContainer.setAlignment(Pos.CENTER);

        Label tituloTarefa = new Label("Tarefa");
        tituloTarefa.setFont(Font.font("Courier New", 18));
        tarefasContainer.getChildren().add(tituloTarefa);

        tarefasContainer.getChildren().add(criarBlocoTarefa(tarefa, primaryStage));

        // Botões de edição/exclusão
        HBox botoesAcoes = new HBox(40);
        botoesAcoes.setAlignment(Pos.CENTER);
        botoesAcoes.setPadding(new Insets(10));

        Button btnEditar = new Button("Editar tarefa");
        Button btnExcluir = new Button("Excluir tarefa");
        Button btnVoltar = new Button("Voltar");
        estilizarBotaoRoxo(btnVoltar);
        estilizarBotaoRoxo(btnEditar);
        estilizarBotaoRoxo(btnExcluir);
        btnEditar.setVisible(isDono);
        btnExcluir.setVisible(isDono);

        //Eventos
        btnEditar.setOnMouseClicked(e -> {
            StackPane modal = criarModalEditarProjeto(projeto);
            root.getChildren().add(modal);
            abrirModalProjeto(projeto, modal);
        });

        btnVoltar.setOnMouseClicked(e -> {
            if (isDono) {
                telaProjetoDono(primaryStage);
            } else {
                telaProjetoColaborador(primaryStage);
            }
        });

        btnExcluir.setOnMouseClicked(e -> {
            //ToDo: Confirmação de delete
            meusProjetosController.excluirProjeto(projeto);
            telaProjetoDono(primaryStage);
        });

        if (isDono) {
            botoesAcoes.getChildren().addAll(btnVoltar, btnEditar, btnExcluir);
            botoesAcoes.setAlignment(Pos.CENTER);
        } else {
            //Exibir apenas o voltar quando o usuário não é dono e deixar o botão centralizado
            botoesAcoes.getChildren().addAll(btnVoltar);
            HBox.setHgrow(btnVoltar, Priority.ALWAYS);
            btnVoltar.setMaxWidth(200);
            botoesAcoes.setAlignment(Pos.CENTER);
        }

        VBox conteudo = new VBox(20);
        conteudo.setAlignment(Pos.TOP_CENTER);
        conteudo.setPadding(new Insets(20));
        conteudo.getChildren().addAll(header, desc, datas, botoesAcoes, tarefasContainer);

        // Container principal
        root = new StackPane(conteudo);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #c8afd6;");
        root.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(root, 1600, 900);
        primaryStage.setTitle("Visualizar Tarefa");
        primaryStage.setScene(scene);
        primaryStage.show();
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

    private StackPane criarModalEditarProjeto(Projeto p) {
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
            p.setNome(tfNome.getText());
            p.setDescricao(taDescricao.getText());
            p.setDataInicio(dpInicio.getValue());
            p.setDataFim(dpFim.getValue());
            meusProjetosController.editarProjeto(p);
            fundo.setVisible(false);
        });

        btnCancelar.setOnAction(e -> fundo.setVisible(false));
        return fundo;
    }

    private VBox criarBlocoTarefa(Tarefa tarefa, Stage primaryStage) {
        VBox bloco = new VBox(10);
        bloco.setCursor(Cursor.HAND);
        bloco.setPadding(new Insets(20));
        bloco.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-color: black; -fx-border-radius: 10px;");
        bloco.setMaxWidth(Double.MAX_VALUE);

        Label nomeTarefa = new Label(tarefa.getNome());
        Label status = new Label("Status: " + tarefa.getStatus().toString());
        Label prioridade = new Label("Prioridade: " + tarefa.getPrioridade());
        Label prazo = new Label("Prazo de Conclusão " + tarefa.getDataFim().toString());

        for (Label lbl : new Label[]{nomeTarefa, status, prioridade, prazo}) {
            lbl.setStyle("-fx-font-family: monospace; -fx-font-size: 18px; -fx-text-fill: black;");
        }

        StackPane stack = new StackPane(bloco);
        bloco.getChildren().addAll(nomeTarefa, status, prioridade, prazo);
        return new VBox(stack);
    }

    private void abrirModalProjeto(Projeto p, StackPane modal) {
        TextField tfNome = new TextField(p.getNome());
        TextArea taDescricao = new TextArea(p.getDescricao());
        DatePicker dpInicio = new DatePicker(p.getDataInicio());
        DatePicker dpFim = new DatePicker(p.getDataFim());
        modal.setVisible(true);
    }

    //Métodos para redirecionamento de telas
    private void telaProjetoColaborador(Stage primaryStage) {
        FXProjetosColaborando projetoColaborador = new FXProjetosColaborando();
        FXProjetosColaborando.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        projetoColaborador.start(primaryStage);
    }

    private void telaProjetoDono(Stage primaryStage) {
        FXMeusProjetos meusProjetos = new FXMeusProjetos();
        FXMeusProjetos.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        meusProjetos.start(primaryStage);
    }
}
