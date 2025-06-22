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

public class FXTelaProjetoDono extends Application {

    private VBox areaPrincipal;
    private StackPane modalProjeto;
    private TextField tfNome;
    private TextArea taDescricao;
    private TextField tfInicio;
    private TextField tfFim;
    private boolean usuarioEhDono = true; // <- controle de acesso

    @Override
    public void start(Stage primaryStage) {
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        primaryStage.setTitle("Projeto - Galo Eletrônico");

        VBox conteudoPrincipal = new VBox(20);
        conteudoPrincipal.setPadding(new Insets(30));
        conteudoPrincipal.setStyle("-fx-background-color: #c7b8d8;");

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        Label titulo = new Label("Projeto galo eletrônico");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-family: monospace;");

        Label iconeEdicao = new Label(usuarioEhDono ? " ✎" : " 👁");
        iconeEdicao.setStyle("-fx-font-size: 16px; -fx-text-fill: gray;");
        if (usuarioEhDono) {
            iconeEdicao.setOnMouseClicked(e -> abrirModalProjeto("Projeto galo eletrônico", "Projeto inicial para confecção dos modelos do Galotron3000", "28/04/2025", "22/10/2027"));
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ComboBox<String> status = new ComboBox<>();
        status.getItems().addAll("Planejado", "Em andamento", "Concluído");
        status.setValue("Em andamento");
        status.setDisable(!usuarioEhDono);

        header.getChildren().addAll(titulo, iconeEdicao, spacer, status);

        TextArea descricao = new TextArea("Projeto inicial para confecção dos modelos do Galotron3000");
        descricao.setWrapText(true);
        descricao.setEditable(false);
        descricao.setStyle("-fx-background-radius: 10; -fx-font-family: monospace; -fx-background-color: #f5f5f5;");

        HBox datas = new HBox(10);
        datas.setAlignment(Pos.CENTER);
        Button inicio = new Button("Início  28/04/2025");
        Button fim = new Button("Fim  22/10/2027");
        estiloRoxo(inicio);
        estiloRoxo(fim);
        datas.getChildren().addAll(inicio, fim);

        GridPane resumo = new GridPane();
        resumo.setHgap(30);
        resumo.setVgap(10);
        resumo.setAlignment(Pos.CENTER);
        resumo.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-padding: 20;");
        resumo.add(new Label("2"), 0, 0);
        resumo.add(new Label("Listas ativas"), 0, 1);
        resumo.add(new Label("10"), 1, 0);
        resumo.add(new Label("Tarefas ativas"), 1, 1);
        resumo.add(new Label("5"), 2, 0);
        resumo.add(new Label("Tarefas concluídas"), 2, 1);
        resumo.add(new Label("3"), 3, 0);
        resumo.add(new Label("Atuam no projeto"), 3, 1);

        HBox botoesAcao = new HBox(10);
        botoesAcao.setAlignment(Pos.CENTER);
        Button novaTarefa = new Button("Criar nova tarefa");
        Button novaLista = new Button("Criar nova lista");
        novaLista.setDisable(!usuarioEhDono);
        estiloCinza(novaTarefa);
        estiloCinza(novaLista);
        botoesAcao.getChildren().addAll(novaTarefa, novaLista);

        HBox quadros = new HBox(20);
        quadros.setAlignment(Pos.CENTER);
        
        VBox quadroNaoIniciado = criarQuadro("Não iniciado");
        VBox quadroAndamento = criarQuadro("Em andamento");
        VBox quadroConcluidas = criarQuadro("Concluídas");

        // Adicionando tarefas simuladas:
        adicionarTarefaAoQuadro(quadroNaoIniciado, "Documentar base", "Alta 25/06/25");
        adicionarTarefaAoQuadro(quadroNaoIniciado, "Pesquisar componentes", "Média 27/06/25");

        adicionarTarefaAoQuadro(quadroAndamento, "Montar protótipo", "Alta 20/06/25");

        adicionarTarefaAoQuadro(quadroConcluidas, "Planejamento inicial", "Baixa 15/06/25");

        quadros.getChildren().addAll(quadroNaoIniciado, quadroAndamento, quadroConcluidas);

        Button moverTarefa = new Button("Mover tarefa entre quadros");
        estiloCinza(moverTarefa);

        GridPane config = new GridPane();
        config.setHgap(20);
        config.setVgap(10);
        config.setAlignment(Pos.CENTER);
        config.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-padding: 20;");
        config.setVisible(usuarioEhDono);
        config.add(new Label("✎ Gerenciar membros"), 0, 0);
        config.add(new Label("✎ Gerenciar listas"), 1, 0);
        config.add(new Label("✎ Gerenciar status"), 2, 0);
        config.add(new Label("✎ Gerenciar tarefas"), 3, 0);

        Label criado = new Label("Criado em 11/03/2025");
        criado.setStyle("-fx-font-size: 11px; -fx-font-family: monospace;");
        HBox rodape = new HBox(criado);
        rodape.setAlignment(Pos.CENTER_RIGHT);

        conteudoPrincipal.getChildren().addAll(header, descricao, datas, resumo, botoesAcao, quadros, moverTarefa, config, rodape);

        StackPane principal = new StackPane(conteudoPrincipal);
        StackPane.setMargin(conteudoPrincipal, new Insets(20));

        modalProjeto = criarModalProjeto(() -> System.out.println("Salvo."));
        modalProjeto.setVisible(false);

        Scene cena = new Scene(new StackPane(principal, modalProjeto), larguraTela, alturaTela * 0.90);
        primaryStage.setScene(cena);
        primaryStage.show();
    }

    private VBox criarQuadro(String titulo) {
        VBox quadro = new VBox(10);
        quadro.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-padding: 10;");
        Label label = new Label(titulo);
        label.setStyle("-fx-font-weight: bold; -fx-font-family: monospace;");
        quadro.getChildren().add(label);
        return quadro;
    }

    private void estiloRoxo(Button btn) {
        btn.setStyle("-fx-background-color: #8744c2; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-family: monospace;");
    }

    private void estiloCinza(Button btn) {
        btn.setStyle("-fx-background-color: #c7c7c7; -fx-text-fill: black; -fx-background-radius: 10; -fx-font-family: monospace;");
    }

    private void abrirModalProjeto(String nome, String descricao, String inicio, String fim) {
        tfNome.setText(nome);
        taDescricao.setText(descricao);
        tfInicio.setText(inicio);
        tfFim.setText(fim);
        modalProjeto.setVisible(true);
    }

    private StackPane criarModalProjeto(Runnable acaoSalvar) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudo = new VBox(10);
        conteudo.setPadding(new Insets(20));
        conteudo.setStyle("-fx-background-color: white; -fx-background-radius: 15;");

        tfNome = new TextField();
        taDescricao = new TextArea();
        tfInicio = new TextField();
        tfFim = new TextField();

        Button salvar = new Button("Salvar");
        Button cancelar = new Button("Cancelar");
        salvar.setOnAction(e -> {
            acaoSalvar.run();
            modalProjeto.setVisible(false);
        });
        cancelar.setOnAction(e -> modalProjeto.setVisible(false));

        conteudo.getChildren().addAll(
            new Label("Nome do Projeto"), tfNome,
            new Label("Descrição"), taDescricao,
            new Label("Início"), tfInicio,
            new Label("Fim"), tfFim,
            new HBox(10, salvar, cancelar)
        );

        StackPane fundo = new StackPane(conteudo);
        fundo.setStyle("-fx-background-color: rgba(0,0,0,0.5);");
        fundo.setAlignment(Pos.CENTER);

        return fundo;
    }
    
    private void adicionarTarefaAoQuadro(VBox quadro, String titulo, String prioridade) {
        VBox tarefa = new VBox(4);
        tarefa.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-padding: 6;");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-family: monospace; -fx-font-size: 11px;");

        Label lblPrioridade = new Label(prioridade);
        lblPrioridade.setStyle("-fx-font-family: monospace; -fx-font-size: 10px;");

        tarefa.getChildren().addAll(lblTitulo, lblPrioridade);
        quadro.getChildren().add(tarefa);
    }


    public static void main(String[] args) {
        launch();
    }
}
