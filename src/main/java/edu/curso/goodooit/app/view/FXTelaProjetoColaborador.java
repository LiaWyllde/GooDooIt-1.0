package edu.curso.goodooit.ui.view;

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

public class FXTelaProjetoColaborador extends Application {

    private boolean menuVisivel = false;
    private Button botaoMenu;
    private Rectangle fundoEscurecido;

    @Override
    public void start(Stage stage) {
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        StackPane mainLayout = new StackPane();

        VBox conteudoPrincipal = new VBox(15);
        conteudoPrincipal.setPadding(new Insets(20));
        conteudoPrincipal.setStyle("-fx-background-color: #c7b8d8;");

        ScrollPane scrollPane = new ScrollPane(conteudoPrincipal);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(alturaTela);
        scrollPane.setStyle("-fx-background-color: transparent;");

        VBox menuLateral = criarMenuLateral();
        menuLateral.setPrefHeight(alturaTela);
        menuLateral.setTranslateX(-260);
        menuLateral.setStyle(menuLateral.getStyle() + " -fx-effect: dropshadow(gaussian, gray, 10, 0, 2, 0);");
        StackPane.setAlignment(menuLateral, Pos.TOP_LEFT);

        fundoEscurecido = new Rectangle(larguraTela, alturaTela);
        fundoEscurecido.setFill(new Color(0, 0, 0, 0.5));
        fundoEscurecido.setVisible(false);
        fundoEscurecido.setOnMouseClicked(e -> alternarMenu(menuLateral));

        botaoMenu = new Button("\u2630");
        botaoMenu.setStyle("-fx-font-size: 18px; -fx-background-color: transparent;");
        botaoMenu.setOnAction(e -> alternarMenu(menuLateral));

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        Label titulo = new Label("Projeto galo eletrônico ✏");
        titulo.setStyle("-fx-font-family: monospace; -fx-font-size: 18px;");
        ComboBox<String> statusProjeto = new ComboBox<>();
        statusProjeto.getItems().addAll("Planejado", "Em andamento", "Concluído");
        statusProjeto.setValue("Em andamento");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(botaoMenu, titulo, spacer, statusProjeto);

        TextArea descricao = new TextArea("Projeto inicial para confecção dos modelos do Galotron3000");
        descricao.setWrapText(true);
        descricao.setEditable(false);
        descricao.setStyle("-fx-font-family: monospace; -fx-background-radius: 10; -fx-background-color: #f5f5f5;");

        HBox datas = new HBox(10);
        datas.setAlignment(Pos.CENTER);
        Button inicio = new Button("Início  20/04/2025");
        Button fim = new Button("Fim  22/10/2027");
        estiloBotaoRoxo(inicio);
        estiloBotaoRoxo(fim);
        datas.getChildren().addAll(inicio, fim);

        GridPane infos = new GridPane();
        infos.setHgap(20);
        infos.setVgap(10);
        infos.setAlignment(Pos.CENTER);
        infos.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-padding: 20;");

        infos.add(new Label("2"), 0, 0);
        infos.add(new Label("Listas ativas"), 0, 1);
        infos.add(new Label("10"), 1, 0);
        infos.add(new Label("Tarefas ativas"), 1, 1);
        infos.add(new Label("5"), 2, 0);
        infos.add(new Label("Tarefas concluídas"), 2, 1);
        infos.add(new Label("3"), 3, 0);
        infos.add(new Label("Atuam no projeto"), 3, 1);

        HBox criarBotoes = new HBox(10);
        criarBotoes.setAlignment(Pos.CENTER);
        Button criarTarefa = new Button("Criar nova tarefa");
        Button criarLista = new Button("Criar nova lista");
        estiloBotaoCinza(criarTarefa);
        estiloBotaoCinza(criarLista);
        criarBotoes.getChildren().addAll(criarTarefa, criarLista);

        HBox quadros = new HBox(20);
        quadros.setAlignment(Pos.CENTER);
        VBox naoIniciado = criarQuadro("Não iniciado");
        VBox andamento = criarQuadro("Em andamento");
        VBox concluidas = criarQuadro("Concluídas");
        quadros.getChildren().addAll(naoIniciado, andamento, concluidas);

        Button moverTarefa = new Button("Mover tarefa entre quadros");
        estiloBotaoCinza(moverTarefa);

        Label criado = new Label("Criado em 11/03/2025");
        criado.setStyle("-fx-font-family: monospace; -fx-font-size: 11px;");
        criado.setAlignment(Pos.CENTER_RIGHT);
        HBox rodape = new HBox(criado);
        rodape.setAlignment(Pos.CENTER_RIGHT);

        conteudoPrincipal.getChildren().addAll(header, descricao, datas, infos, criarBotoes, quadros, moverTarefa, rodape);

        mainLayout.getChildren().addAll(scrollPane, fundoEscurecido, menuLateral);

        Scene scene = new Scene(mainLayout, larguraTela, alturaTela * 0.9);
        stage.setScene(scene);
        stage.setTitle("Projeto Galo Eletrônico");
        stage.setMaximized(true);
        stage.show();
    }

    private void alternarMenu(VBox menuLateral) {
        TranslateTransition transicao = new TranslateTransition(Duration.millis(300), menuLateral);
        FadeTransition fade = new FadeTransition(Duration.millis(300), fundoEscurecido);

        if (!menuVisivel) {
            transicao.setToX(0);
            fade.setFromValue(0);
            fade.setToValue(1);
            fundoEscurecido.setVisible(true);
            botaoMenu.setText("X");
        } else {
            transicao.setToX(-260);
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> fundoEscurecido.setVisible(false));
            botaoMenu.setText("\u2630");
        }

        transicao.play();
        fade.play();
        menuVisivel = !menuVisivel;
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
        nome.setStyle("-fx-font-family: 'monospace'; -fx-font-size: 16px;");

        HBox icones = new HBox(20);
        Label sino = new Label("\uD83D\uDD14\n5");
        Label email = new Label("\u2709\n1");
        icones.getChildren().addAll(sino, email);
        icones.setAlignment(Pos.CENTER);

        Button fecharMenu = new Button("Fechar menu");
        fecharMenu.setMaxWidth(Double.MAX_VALUE);
        fecharMenu.setStyle("-fx-background-color: #ffaaaa; -fx-font-family: monospace; -fx-font-size: 14px; -fx-background-radius: 10px;");
        fecharMenu.setOnAction(e -> alternarMenu(menu));

        menu.getChildren().addAll(avatar, nome, icones,
                botaoMenu("Meus projetos", "#d763f7"),
                botaoMenu("Colaborando", "#c7c7c7"),
                botaoMenu("Equipes", "#d763f7"),
                botaoMenu("Tarefas", "#c7c7c7"),
                botaoMenu("Editar perfil", "#d763f7"),
                botaoMenu("Sair", "#c7c7c7"),
                fecharMenu
        );

        return menu;
    }

    private Button botaoMenu(String texto, String cor) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: " + cor + "; -fx-font-family: monospace; -fx-font-size: 14px; -fx-background-radius: 10px;");
        return btn;
    }

    private VBox criarQuadro(String titulo) {
        VBox quadro = new VBox(10);
        quadro.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-padding: 10;");

        Label tituloLbl = new Label(titulo);
        tituloLbl.setStyle("-fx-font-weight: bold; -fx-font-family: monospace; -fx-font-size: 13px;");

        VBox lista1 = criarLista("Bico cibernético");
        VBox lista2 = criarLista("Olho laser");

        quadro.getChildren().addAll(tituloLbl, lista1, lista2);
        return quadro;
    }

    private VBox criarLista(String tituloLista) {
        VBox lista = new VBox(5);
        lista.setStyle("-fx-background-color: #ecd7fb; -fx-background-radius: 12px; -fx-padding: 8;");

        Label titulo = new Label(tituloLista);
        titulo.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");

        for (int i = 0; i < 3; i++) {
            VBox tarefa = new VBox();
            tarefa.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-padding: 6;");
            Label nome = new Label("Documentar b ...");
            nome.setStyle("-fx-font-family: monospace; -fx-font-size: 11px;");
            Label prioridade = new Label("Alta 28/06/25");
            prioridade.setStyle("-fx-font-family: monospace; -fx-font-size: 10px;");
            tarefa.getChildren().addAll(nome, prioridade);
            lista.getChildren().add(tarefa);
        }

        lista.getChildren().add(0, titulo);
        return lista;
    }

    private void estiloBotaoRoxo(Button btn) {
        btn.setStyle("-fx-background-color: #8744c2; -fx-text-fill: white; -fx-background-radius: 10px; -fx-font-family: monospace;");
    }

    private void estiloBotaoCinza(Button btn) {
        btn.setStyle("-fx-background-color: #c7c7c7; -fx-text-fill: black; -fx-background-radius: 10px; -fx-font-family: monospace;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
