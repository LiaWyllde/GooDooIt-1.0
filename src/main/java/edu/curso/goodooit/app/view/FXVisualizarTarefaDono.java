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

public class FXVisualizarTarefaDono extends Application {

    private boolean menuVisivel = false;
    private Button botaoMenu;
    private Rectangle fundoEscurecido;

    @Override
    public void start(Stage stage) {
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        StackPane root = new StackPane();

        // Conteúdo principal com scroll
        VBox conteudo = criarConteudoPrincipal();
        ScrollPane scrollPane = new ScrollPane(conteudo);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent;");

        // Menu lateral escondido inicialmente
        VBox menuLateral = criarMenuLateral();
        menuLateral.setPrefWidth(250);
        menuLateral.setTranslateX(-260);
        StackPane.setAlignment(menuLateral, Pos.TOP_LEFT);

        // Fundo escurecido
        fundoEscurecido = new Rectangle(larguraTela, alturaTela);
        fundoEscurecido.setFill(new Color(0, 0, 0, 0.5));
        fundoEscurecido.setVisible(false);
        fundoEscurecido.setOnMouseClicked(e -> alternarMenu(menuLateral));
        StackPane.setAlignment(fundoEscurecido, Pos.CENTER);




        VBox layoutPrincipal = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().addAll(layoutPrincipal, fundoEscurecido, menuLateral);

        Scene scene = new Scene(root, larguraTela, alturaTela * 0.9);
        stage.setScene(scene);
        stage.setTitle("Organizador de Tarefas");
        stage.show();
    }


    private VBox criarConteudoPrincipal() {
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #b39ddb; -fx-font-family: monospace;");
        mainContent.setFillWidth(true);

        // Header
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        botaoMenu = new Button("≡");
        botaoMenu.setStyle("-fx-font-size: 18px; -fx-background-color: transparent;");
        botaoMenu.setOnAction(e -> alternarMenu((VBox) ((StackPane) botaoMenu.getScene().getRoot()).getChildren().get(2)));

        Label title = new Label("Organizar novas tarefas ✏");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Aguardando P.O.", "Em andamento", "Concluído");
        statusCombo.setValue("Aguardando P.O.");

        header.getChildren().addAll(botaoMenu, title, spacer, statusCombo);

        TextField descricaoField = new TextField("Organizar novas tarefas para a lista de desenvolvimento");
        descricaoField.setStyle("-fx-font-size: 14px;");
        descricaoField.setMaxWidth(Double.MAX_VALUE);

        HBox datas = new HBox(20);
        datas.setAlignment(Pos.CENTER_LEFT);
        Button inicioBtn = new Button("Início 20/04/2025");
        Button fimBtn = new Button("Fim 22/10/2027");
        estiloBotaoRoxo(inicioBtn);
        estiloBotaoRoxo(fimBtn);
        datas.getChildren().addAll(inicioBtn, fimBtn);

        ComboBox<String> prioridadeCombo = new ComboBox<>();
        prioridadeCombo.getItems().addAll("Alta", "Média", "Baixa");
        prioridadeCombo.setValue("Alta");

        HBox prioridadeBox = new HBox(10, new Label("Prioridade:"), prioridadeCombo);
        prioridadeBox.setAlignment(Pos.CENTER_LEFT);

        Label criadoPor = new Label("Criado em 11/03/2025 às 19h34 por Julia Fernandes (você)");
        criadoPor.setStyle("-fx-font-size: 12px;");

        HBox responsavelBox = new HBox(10);
        responsavelBox.setAlignment(Pos.CENTER_LEFT);
        Label responsavel = new Label("Responsável: João Francisco Alves (JoaoADS)");
        Button editarBtn = new Button("✎");
        responsavelBox.getChildren().addAll(responsavel, editarBtn);

        VBox comentariosArea = new VBox(10);
        comentariosArea.setPadding(new Insets(15));
        comentariosArea.setStyle("-fx-background-color: #d3d3d3; -fx-background-radius: 10;");
        Label comentariosTitulo = new Label("Comentários");
        comentariosTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        TextArea novoComentario = new TextArea();
        novoComentario.setPromptText("Adicionar um novo comentário...");
        novoComentario.setPrefRowCount(3);

        VBox listaComentarios = new VBox(10,
                comentario("Julia Fernandes", "17/04/2025 às 22:37", "Estou aguardando"),
                comentario("Nicolas Domingos", "17/04/2025 às 21:37", "Estou aguardando"),
                comentario("Gustavo Henrique", "17/04/2025 às 21:37", "Estou aguardando")
        );

        comentariosArea.getChildren().addAll(comentariosTitulo, novoComentario, listaComentarios);

        mainContent.getChildren().addAll(header, descricaoField, datas, prioridadeBox, criadoPor, responsavelBox, comentariosArea);
        return mainContent;
    }

    private VBox comentario(String autor, String data, String texto) {
        VBox box = new VBox(5);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-background-radius: 8px;");

        Label header = new Label(autor + " " + data);
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: black;");

        Label corpo = new Label(texto);
        corpo.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");

        Button excluir = new Button("🗑 Excluir");
        excluir.setStyle("-fx-font-size: 11px;");

        if(true) {
        	box.getChildren().addAll(header, corpo, excluir);
        }
        
        return box;
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

    private void estiloBotaoRoxo(Button btn) {
        btn.setStyle("-fx-background-color: #7e57c2; -fx-text-fill: white; -fx-background-radius: 10;");
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


    public static void main(String[] args) {
        launch(args);
    }
}
