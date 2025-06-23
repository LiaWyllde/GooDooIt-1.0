package edu.curso.goodooit.app.view;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FXTelaConvites extends Application {

    @Override
    public void start(Stage stage) {
        // ===== SIDEBAR =====
        VBox sidebar = criarSidebar();

        // ===== CONTEÚDO PRINCIPAL =====
        VBox conteudo = new VBox(20);
        conteudo.setPadding(new Insets(30));
        conteudo.setStyle("-fx-background-color: #d2bfe6;");
        conteudo.setPrefWidth(800);

        Label titulo = new Label("Convites");
        titulo.setFont(Font.font("Courier New", 28));
        conteudo.getChildren().add(titulo);

        // Adiciona 3 convites fictícios
        for (int i = 0; i < 3; i++) {
            conteudo.getChildren().add(criarCartaoConvite(
                    "17/06/2025",
                    "Gustavo Henrique",
                    "GustavoHenrique01",
                    "Furão Biónico"
            ));
        }

        HBox root = new HBox(sidebar, conteudo);

        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("Convites - GooDoolt");
        stage.show();
    }

    private VBox criarSidebar() {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(30));
        sidebar.setAlignment(Pos.TOP_CENTER);
        sidebar.setStyle("-fx-background-color: #ffffff;");
        sidebar.setPrefWidth(250);

        // Avatar
        ImageView avatar = new ImageView(new Image(getClass().getResourceAsStream("/images/Goo.png")));
        avatar.setFitHeight(100);
        avatar.setPreserveRatio(true);

        Label nome = new Label("Julia Fernandes");
        nome.setFont(Font.font("Courier New", 18));

        Label iconeMensagem = new Label("✉️\n1");
        iconeMensagem.setFont(Font.font("Courier New", 16));
        iconeMensagem.setAlignment(Pos.CENTER);

        VBox menu = new VBox(10,
                criarBotaoMenu("Meus projetos", true),
                criarBotaoMenu("Colaborando", false),
                criarBotaoMenu("Equipes", false),
                criarBotaoMenu("Tarefas", false),
                criarBotaoMenu("Editar perfil", true),
                criarBotaoMenu("Sair", false)
        );
        menu.setAlignment(Pos.CENTER);

        sidebar.getChildren().addAll(avatar, nome, iconeMensagem, menu);
        return sidebar;
    }

    private Button criarBotaoMenu(String texto, boolean roxo) {
        Button btn = new Button(texto);
        btn.setFont(Font.font("Courier New", 14));
        btn.setPrefWidth(180);
        btn.setStyle(
                "-fx-background-color: " + (roxo ? "#d39ef2;" : "#d8d8d8;") +
                        "-fx-background-radius: 12px;" +
                        "-fx-font-family: 'Courier New';" +
                        "-fx-cursor: hand;"
        );
        return btn;
    }

    private VBox criarCartaoConvite(String data, String nome, String username, String projeto) {
        VBox cartao = new VBox(10);
        cartao.setPadding(new Insets(15));
        cartao.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        cartao.setMaxWidth(700);

        Label lblData = new Label(data);
        Label lblTexto = new Label(nome + "\n" + username + "\nconvidou você para participar do projeto \"" + projeto + "\"");
        lblData.setFont(Font.font("Courier New", 14));
        lblTexto.setFont(Font.font("Courier New", 16));

        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER_RIGHT);

        Button btnAceitar = new Button("Aceitar");
        Button btnRecusar = new Button("Recusar");
        estilizarBotaoConvite(btnAceitar, "green");
        estilizarBotaoConvite(btnRecusar, "red");

        botoes.getChildren().addAll(btnAceitar, btnRecusar);
        cartao.getChildren().addAll(lblData, lblTexto, botoes);

        return cartao;
    }

    private void estilizarBotaoConvite(Button btn, String cor) {
        String backgroundColor = switch (cor.toLowerCase()) {
            case "green" -> "#8fd18f";
            case "red" -> "#f28f8f";
            default -> "#dddddd";
        };

        btn.setFont(Font.font("Courier New", 14));
        btn.setStyle(
                "-fx-background-color: " + backgroundColor + ";" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 5 15;" +
                        "-fx-cursor: hand;"
        );
    }

    public static void main(String[] args) {
        launch();
    }
}
