package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.AlterarDadosUsuarioController;
import edu.curso.goodooit.app.controller.AlterarSenhaController;
import edu.curso.goodooit.app.controller.AutenticacaoController;
import edu.curso.goodooit.app.model.Usuario;
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

public class FXEditarPerfil extends Application {

    private VBox areaPrincipal;
    private StackPane modalSenhaGlobal;
    private StackPane modalEsqueciSenha;

    private static AlterarDadosUsuarioController alterarDadosUsuarioController;
    private static AlterarSenhaController alterarSenhaController;

    public static void setAlterarDadosUsuarioController(AlterarDadosUsuarioController controller) {
        alterarDadosUsuarioController = controller;
    }

    public static void setAlterarSenhaController(AlterarSenhaController controller) {
        alterarSenhaController = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        primaryStage.setTitle("Editar Perfil - GooDoolt");

        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #eeeeee;");
        sidebar.setPrefWidth(220);
        sidebar.setAlignment(Pos.TOP_CENTER);

        Image avatarImage = new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true);
        ImageView avatarView = new ImageView(avatarImage);

        Usuario usuario = AutenticacaoController.getAutenticado();
        Label nome = new Label(usuario.getNome() + " " + usuario.getSobrenome());
        nome.setStyle("-fx-font-size: 18px; -fx-font-family: monospace;");

        sidebar.getChildren().addAll(avatarView, nome,
                botaoMenu("Meus projetos", true),
                botaoMenu("Colaborando", false),
                botaoMenu("Equipes", true),
                botaoMenu("Tarefas", false),
                botaoMenu("Editar perfil", true, true),
                botaoMenu("Sair", false)
        );

        StackPane painelCinza = new StackPane();
        painelCinza.setStyle("-fx-background-color: #dddddd; -fx-background-radius: 20px;");
        painelCinza.setPadding(new Insets(15));
        painelCinza.setMaxWidth(Double.MAX_VALUE);

        areaPrincipal = new VBox();
        areaPrincipal.setSpacing(20);
        areaPrincipal.setPadding(new Insets(20));
        areaPrincipal.setStyle("-fx-background-color: #b39ddb; -fx-background-radius: 15px;");
        areaPrincipal.setPrefWidth(700);
        painelCinza.getChildren().add(areaPrincipal);

        HBox conteudoPrincipal = new HBox();
        conteudoPrincipal.setPadding(new Insets(10));
        conteudoPrincipal.getChildren().addAll(sidebar, painelCinza);
        HBox.setHgrow(painelCinza, Priority.ALWAYS);

        StackPane rootStack = new StackPane(conteudoPrincipal);
        modalSenhaGlobal = modalAlterarSenha(() -> System.out.println("Senha alterada com sucesso."));
        modalEsqueciSenha = criarModalEsqueciSenha(() -> modalSenhaGlobal.setVisible(true));

        rootStack.getChildren().addAll(modalSenhaGlobal, modalEsqueciSenha);
        modalSenhaGlobal.setVisible(false);
        modalEsqueciSenha.setVisible(false);

        Scene scene = new Scene(rootStack, larguraTela, alturaTela * 0.9);
        primaryStage.setScene(scene);
        primaryStage.show();

        mostrarTelaEditarPerfil();
    }

    private Button botaoMenu(String texto, boolean roxo) {
        return botaoMenu(texto, roxo, false);
    }

    private Button botaoMenu(String texto, boolean roxo, boolean ativaEditarPerfil) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-font-family: monospace; -fx-font-size: 14px; -fx-background-radius: 15;" +
                (roxo ? "-fx-background-color: #d681f0; -fx-text-fill: black;" : "-fx-background-color: #cccccc;"));

        if (ativaEditarPerfil) {
            btn.setOnAction(e -> mostrarTelaEditarPerfil());
        }

        return btn;
    }

    private void mostrarTelaEditarPerfil() {
        areaPrincipal.getChildren().clear();

        Label titulo = new Label("Editar perfil");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-family: monospace; -fx-text-fill: black;");

        Usuario usuario = AutenticacaoController.getAutenticado();

        TextField campoUsername = new TextField(usuario.getLogin());
        campoUsername.setDisable(true);

        TextField campoNome = new TextField(usuario.getNome());
        TextField campoSobrenome = new TextField(usuario.getSobrenome());
        TextField campoEmail = new TextField(usuario.getEmail());

        PasswordField campoSenha = new PasswordField();
        campoSenha.setText("********");
        campoSenha.setDisable(true);

        Label mensagem = new Label();
        mensagem.setStyle("-fx-font-family: monospace; -fx-font-size: 14px;");

        VBox form = new VBox(18);
        form.setPadding(new Insets(10));
        form.setStyle("-fx-font-family: monospace;");

        form.getChildren().addAll(
                criarCampoComIcone("Username", campoUsername, "⛔"),
                criarCampoComIcone("Nome", campoNome, "✏️"),
                criarCampoComIcone("Sobrenome", campoSobrenome, "✏️"),
                criarCampoComIcone("Email", campoEmail, "✏️"),
                criarCampoComIcone("Senha", campoSenha, "✏️")
        );

        HBox botoes = new HBox(10);
        botoes.setPadding(new Insets(20, 0, 0, 0));
        botoes.setAlignment(Pos.CENTER);

        Button salvarBtn = new Button("Salvar");
        Button cancelarBtn = new Button("Cancelar");

        salvarBtn.setPrefHeight(40);
        salvarBtn.setPrefWidth(180);
        salvarBtn.setStyle("-fx-background-color: #8000C9; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-family: monospace; -fx-background-radius: 12px;");

        cancelarBtn.setPrefHeight(40);
        cancelarBtn.setPrefWidth(180);
        cancelarBtn.setStyle("-fx-background-color: #999999; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-family: monospace; -fx-background-radius: 12px;");

        salvarBtn.setOnAction(e -> {
            String nome = campoNome.getText().trim();
            String sobrenome = campoSobrenome.getText().trim();
            String email = campoEmail.getText().trim();

            if (nome.isEmpty() || sobrenome.isEmpty() || email.isEmpty()) {
                mensagem.setText("⚠️ Os campos não podem estar vazios.");
                mensagem.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            } else {
                boolean sucesso = alterarDadosUsuarioController.alterarDadosUsuario(nome, sobrenome, email);
                if (sucesso) {
                    mensagem.setText("✅ Dados atualizados com sucesso!");
                    mensagem.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                } else {
                    mensagem.setText("❌ Erro ao atualizar dados.");
                    mensagem.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }
            }
        });

        cancelarBtn.setOnAction(e -> mostrarTelaEditarPerfil());

        Region espacoEntre = new Region();
        HBox.setHgrow(espacoEntre, Priority.ALWAYS);

        botoes.getChildren().addAll(salvarBtn, espacoEntre, cancelarBtn);
        areaPrincipal.getChildren().addAll(titulo, form, botoes, mensagem);
    }

    private VBox criarCampoComIcone(String labelTexto, TextField campo, String iconeTexto) {
        Label label = new Label(labelTexto);
        label.setStyle("-fx-font-size: 14px; -fx-font-family: monospace; -fx-text-fill: black;");

        campo.setPrefHeight(35);
        campo.setStyle("-fx-background-radius: 10; -fx-font-size: 14px;");

        if (labelTexto.equalsIgnoreCase("Username") || labelTexto.equalsIgnoreCase("Senha")) {
            campo.setDisable(true);
            campo.setStyle("-fx-background-radius: 10; -fx-font-size: 14px; -fx-opacity: 1.0; -fx-background-color: white; -fx-text-fill: black;");
        }

        StackPane campoComIcone;

        if (iconeTexto.equals("✏️")) {
            Button iconeEdit = new Button("Editar");
            iconeEdit.setStyle("-fx-font-family: monospace;");
            StackPane.setAlignment(iconeEdit, Pos.CENTER_RIGHT);
            StackPane.setMargin(iconeEdit, new Insets(0, 10, 0, 0));

            if (labelTexto.equalsIgnoreCase("Senha")) {
                campo.setEditable(false);
                iconeEdit.setOnMouseClicked(e -> modalSenhaGlobal.setVisible(true));
            }

            campoComIcone = new StackPane(campo, iconeEdit);
        } else {
            Button iconeEdit = new Button("Bloqueado");
            iconeEdit.setStyle("-fx-font-family: monospace;");
            StackPane.setAlignment(iconeEdit, Pos.CENTER_RIGHT);
            StackPane.setMargin(iconeEdit, new Insets(0, 10, 0, 0));
            campoComIcone = new StackPane(campo, iconeEdit);
        }

        return new VBox(5, label, campoComIcone);
    }

    public StackPane modalAlterarSenha(Runnable acaoSalvarSenha) {
        // código do modal senha (mantido como está)
        return new StackPane(); // substitua conforme necessário
    }

    public StackPane criarModalEsqueciSenha(Runnable acaoVoltar) {
        // código do modal esqueci senha (mantido como está)
        return new StackPane(); // substitua conforme necessário
    }

    public ImageView formatarIcone(String path) {
        ImageView iconeEdit = new ImageView(new Image(getClass().getResourceAsStream(path)));
        iconeEdit.setFitWidth(18);
        iconeEdit.setFitHeight(18);
        StackPane.setAlignment(iconeEdit, Pos.CENTER_RIGHT);
        StackPane.setMargin(iconeEdit, new Insets(0, 10, 0, 0));
        return iconeEdit;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
