package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.*;
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

import java.time.LocalDate;

public class FXVisualizarProjeto extends Application {

    private static VisualizarProjetoController visualizarProjetoController;
    private static MeusProjetosController meusProjetosController;
    private static TarefaController tarefaController;
    private Projeto projeto;

    private StackPane root;

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public static void setVisualizarProjetoController(VisualizarProjetoController visualizarProjetoController) {
        FXVisualizarProjeto.visualizarProjetoController = visualizarProjetoController;
    }

    public static void setMeusProjetosController(MeusProjetosController meusProjetosController) {
        FXVisualizarProjeto.meusProjetosController = meusProjetosController;
    }

    public static void setTarefaController(TarefaController tarefaController) {
        FXVisualizarProjeto.tarefaController = tarefaController;
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
        statusComboBox.setValue(statusComboBox.getItems().get(projeto.getStatusProjetoID() - 1));
        statusComboBox.setStyle("-fx-font-family: 'monospace'; -fx-background-radius: 10px;");
        statusComboBox.setVisible(isDono);

        statusComboBox.setOnAction(event -> {
            projeto.setStatus(statusComboBox.getSelectionModel().getSelectedItem());
            projeto.setStatusProjetoID(statusComboBox.getSelectionModel().getSelectedItem().getID());
            meusProjetosController.editarProjeto(projeto);
        });

        Label statusTexto = new Label(projeto.getStatus().toString());
        statusTexto.setStyle("-fx-font-size: 18px");
        statusTexto.setVisible(!isDono);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(lblProjeto, nomeProjeto, spacer, isDono ? statusComboBox : statusTexto);

        // Descrição
        Label lblDescricao = new Label("Descricao");
        lblDescricao.setStyle("-fx-font-size: 20px; -fx-font-family: 'Monospace';");
        TextField descricao = new TextField(projeto.getDescricao());
        descricao.setMaxWidth(700);
        descricao.setStyle("-fx-font-size: 18px");
        descricao.setEditable(false);
        descricao.setStyle("-fx-background-radius: 10px;-fx-background-color: white;");

        VBox desc = new VBox(lblDescricao, descricao);
        VBox.setVgrow(desc, Priority.ALWAYS);
        desc.setAlignment(Pos.CENTER);

        // Datas
        HBox datas = new HBox(40);
        datas.setAlignment(Pos.CENTER);
        datas.setPadding(new Insets(10));

        Label inicio = new Label("Início " + projeto.getDataInicio().toString());
        Label fim = new Label("Fim " + projeto.getDataFim().toString());
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

        Label numMembros = new Label((visualizarProjetoController.contarMembrosProjeto(projeto)).toString());
        numMembros.setStyle("-fx-font-size: 36px");
        Label membros = new Label("Membros do projeto");

        Label numConcluidas = new Label(visualizarProjetoController.contarTarefaProjeto(projeto).toString());
        numConcluidas.setStyle("-fx-font-size: 36px");
        Label concluidas = new Label("Tarefas totais");

        for (Label l : new Label[]{membros, concluidas, numMembros, numConcluidas}) {
            l.setStyle("-fx-font-size: 16px");
            l.setAlignment(Pos.CENTER);
        }

        infoMembros.getChildren().addAll(numMembros, membros);
//                    infoMembros.setStyle("-fx-font-size: 36px");
        infoTarefas.getChildren().addAll(numConcluidas, concluidas);
        infos.getChildren().addAll(infoMembros, infoTarefas);
        infoBox.getChildren().addAll(tituloInfo, infos);

        Button btnNovaTarefa = new Button("Criar nova tarefa");
        btnNovaTarefa.setStyle("-fx-background-color: #d8d8d8; -fx-font-family: 'Courier New'; -fx-background-radius: 10px;");
        btnNovaTarefa.setMaxWidth(300);

        // Área de tarefas
        VBox tarefasContainer = new VBox(10);
        tarefasContainer.setPadding(new Insets(20));
        tarefasContainer.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        tarefasContainer.setMaxWidth(Double.MAX_VALUE);
        tarefasContainer.setFillWidth(true);
        tarefasContainer.setMinHeight(500);
        tarefasContainer.setAlignment(Pos.CENTER);

        Label tituloTarefas = new Label("Tarefas");
        tituloTarefas.setFont(Font.font("Courier New", 18));
        tarefasContainer.getChildren().add(tituloTarefas);

        ObservableList<Tarefa> tarefas = visualizarProjetoController.listarTarefasdoProjeto(projeto);
        tarefas.forEach(tarefa -> {
            tarefasContainer.getChildren().add(criarBlocoTarefa(tarefa, primaryStage));
        });

        ScrollPane scroll = new ScrollPane(tarefasContainer);
        scroll.setFitToWidth(true);
        scroll.setMaxWidth(800);
        scroll.setPrefViewportWidth(800);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setTranslateX(0);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        // Botões de edição/exclusão
        HBox botoesAcoes = new HBox(40);
        botoesAcoes.setAlignment(Pos.CENTER);
        botoesAcoes.setPadding(new Insets(10));

        Button btnEditar = new Button("Editar projeto");
        Button btnExcluir = new Button("Excluir projeto");
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

        btnNovaTarefa.setOnMouseClicked(e -> {
            abrirModalTarefa(primaryStage);
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

        infoMembros.setOnMouseClicked(event -> {
            telaMembros(primaryStage);
        });

        VBox conteudo = new VBox(20);
        conteudo.setAlignment(Pos.TOP_CENTER);
        conteudo.setPadding(new Insets(20));
        conteudo.getChildren().addAll(header, desc, datas, infoBox, btnNovaTarefa, scroll, botoesAcoes);

        // Container principal
        root = new StackPane(conteudo);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #c8afd6;");
        root.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(root, 1600, 900);
        primaryStage.setTitle("Visualizar Projeto");
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
        Label prioridade = new Label("Prioridade: " + tarefa.getPrioridadeString());
        Label responsavel = new Label(tarefaController.buscarResposavel(tarefa.getResponsavelID()) == null
                                        ? "Responsável: Sem responsável atribuído"
                                            : "Responsável: " +tarefaController.buscarResposavel(tarefa.getResponsavelID()));
        Label prazo = new Label("Prazo de Conclusão " + tarefa.getDataFim().toString());

        for (Label lbl : new Label[]{nomeTarefa, status, prioridade, responsavel, prazo}) {
            lbl.setStyle("-fx-font-family: monospace; -fx-font-size: 18px; -fx-text-fill: black;");
        }

        Button visualizar = new Button("Visualizar");
        visualizar.setStyle("-fx-font-size: 18px; -fx-text-fill: gray; -fx-font-family: monospace;");

        visualizar.setOnMouseClicked(e -> {
            telaVisualizarTarefa(tarefa, primaryStage);
        });

        HBox botoes = new HBox(10, visualizar);
        botoes.setAlignment(Pos.TOP_RIGHT);
        StackPane.setAlignment(botoes, Pos.TOP_RIGHT);
        StackPane.setMargin(botoes, new Insets(10));

        StackPane stack = new StackPane(bloco, botoes);
        bloco.getChildren().addAll(nomeTarefa, status, prioridade, responsavel, prazo);
        return new VBox(stack);
    }

    private void telaVisualizarTarefa(Tarefa tarefa, Stage primaryStage) {
        FXVisualizarTarefa visualizarTarefa = new FXVisualizarTarefa();
        FXVisualizarTarefa.setVisualizarProjetoController(AllControllerRegistry.getInstance().getVisualizarProjetoController());
        FXVisualizarTarefa.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        FXVisualizarTarefa.setTarefaController(AllControllerRegistry.getInstance().getTarefaController());
        visualizarTarefa.setTarefa(tarefa);
        visualizarTarefa.setProjeto(projeto);
        visualizarTarefa.start(primaryStage);
    }

    public StackPane criarModalNovaTarefa(Stage primaryStage) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudo = new VBox(15);
        conteudo.setPadding(new Insets(30));
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setMaxWidth(largura * 0.35);
        conteudo.setMaxHeight(altura * 0.4);
        conteudo.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");
        Label titulo = new Label("Nova Tarefa");
        titulo.setStyle("-fx-font-size: 24px; -fx-text-fill: #6A0DAD; -fx-font-weight: bold;");

        TextField tfTitulo = new TextField();
        tfTitulo.setPromptText("Título da Tarefa");
        tfTitulo.setMaxWidth(280);
        tfTitulo.setStyle("-fx-background-radius: 16; -fx-border-radius: 16;");

        TextArea taDescricao = new TextArea();
        taDescricao.setPromptText("Descrição");
        taDescricao.setPrefRowCount(2);
        taDescricao.setMaxWidth(280);
        taDescricao.setStyle("-fx-background-radius: 16; -fx-border-radius: 16;");

        DatePicker dpInicio = new DatePicker();
        dpInicio.setPromptText("Início");
        DatePicker dpPrazo = new DatePicker();
        dpPrazo.setPromptText("Prazo de Entrega");

        for (DatePicker data : new DatePicker[]{dpInicio, dpPrazo}) {
            data.setMaxWidth(Double.MAX_VALUE);
            data.setStyle("-fx-background-radius: 16; -fx-border-radius: 16;");
        }

        HBox datas = new HBox(15, dpInicio, dpPrazo);
        datas.setAlignment(Pos.CENTER);
        datas.setMaxWidth(280);
        HBox.setHgrow(dpInicio, Priority.ALWAYS);
        HBox.setHgrow(dpPrazo, Priority.ALWAYS);

        ComboBox<String> cbPrioridade = new ComboBox<>();
        cbPrioridade.getItems().addAll("Muito Baixa", "Baixa", "Média", "Alta", "Muito Alta");
        cbPrioridade.setPromptText("Prioridade");
        cbPrioridade.setMaxWidth(Double.MAX_VALUE);
        cbPrioridade.setStyle("-fx-background-radius: 16; -fx-border-radius: 16;");

        HBox seletores = new HBox(15, cbPrioridade);
        seletores.setAlignment(Pos.CENTER);
        seletores.setMaxWidth(280);
        HBox.setHgrow(cbPrioridade, Priority.ALWAYS);

        TextField tfResponsavel = new TextField();
        tfResponsavel.setPromptText("Responsável");
        tfResponsavel.setMaxWidth(280);
        tfResponsavel.setStyle("-fx-background-radius: 16; -fx-border-radius: 16;");

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 18;");

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 18;");

        HBox botoes = new HBox(15, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudo.getChildren().addAll(
                titulo,
                tfTitulo,
                taDescricao,
                datas,
                seletores,
                tfResponsavel,
                botoes
        );

        StackPane fundo = new StackPane(conteudo);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        btnSalvar.setOnAction(e -> {
            String nome = tfTitulo.getText().trim();
            String descricao = taDescricao.getText().trim();
            LocalDate dataInicio = dpInicio.getValue();
            LocalDate dataFim = dpPrazo.getValue();
            int prioridade = (cbPrioridade.getSelectionModel().getSelectedIndex() + 1);
            String responsavel = tfResponsavel.getText().trim();

            tarefaController.adicionarTarefa(nome, descricao, dataInicio, dataFim, prioridade, responsavel, projeto.getID());
            start(primaryStage);
        });

        btnCancelar.setOnAction(e -> fundo.setVisible(false));

        return fundo;
    }

    private void abrirModalTarefa(Stage primaryStage) {
        StackPane fundo = criarModalNovaTarefa(primaryStage);
        root.getChildren().add(fundo);
        fundo.setVisible(true);
    }

    private void abrirModalProjeto(Projeto p, StackPane modal) {
        modal.setVisible(true);
    }

    //Métodos para redirecionamento de telas
    private void telaProjetoColaborador(Stage primaryStage) {
        FXProjetosColaborando projetoColaborador = new FXProjetosColaborando();
        FXProjetosColaborando.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        projetoColaborador.start(primaryStage);
    }

    private void telaMembros(Stage primaryStage) {
        FXVisualizarMembrosProjeto membros = new FXVisualizarMembrosProjeto();
        FXVisualizarMembrosProjeto.setVisualizarProjetoController(AllControllerRegistry.getInstance().getVisualizarProjetoController());
        FXVisualizarMembrosProjeto.setConviteController(AllControllerRegistry.getInstance().getConviteController());
        membros.setProjeto(projeto);
        membros.start(primaryStage);
    }

    private void telaProjetoDono(Stage primaryStage) {
        FXMeusProjetos meusProjetos = new FXMeusProjetos();
        FXMeusProjetos.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        meusProjetos.start(primaryStage);
    }
}
