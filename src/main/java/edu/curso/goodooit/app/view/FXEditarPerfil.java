package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.AlterarDadosUsuarioController;
import edu.curso.goodooit.app.controller.AlterarSenhaController;
import edu.curso.goodooit.app.controller.AutenticacaoController;
import edu.curso.goodooit.app.controller.AllControllerRegistry;
import edu.curso.goodooit.app.model.Usuario;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class FXEditarPerfil extends Application {

    private VBox areaPrincipal;
    private StackPane modalSenhaGlobal;
    private StackPane modalEsqueciSenha;
    private StackPane root;

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

        Usuario usuario = AutenticacaoController.getAutenticado();
        VBox sidebar = criarSideBar(primaryStage, usuario.getNome() + " " + usuario.getSobrenome());

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

        root = new StackPane(conteudoPrincipal);

        Scene scene = new Scene(root, larguraTela, alturaTela * 0.9);
        primaryStage.setScene(scene);
        primaryStage.show();

        mostrarTelaEditarPerfil();
    }

    private VBox criarSideBar(Stage primaryStage, String nomeCompleto) {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #eeeeee;");
        sidebar.setPrefWidth(220);
        sidebar.setAlignment(Pos.TOP_CENTER);

        Image avatarImage = new Image(getClass().getResourceAsStream("/images/LogoComTexto.png"), 100, 100, true, true);
        ImageView avatarView = new ImageView(avatarImage);

        Label nome = new Label(nomeCompleto);
        nome.setStyle("-fx-font-size: 18px; -fx-font-family: monospace;");

        HBox notificacoes = new HBox(20);
        notificacoes.setAlignment(Pos.CENTER);

        ImageView iconeConvite = formatarIcone("/images/envelope.jpg");
        Label email = new Label("1");
        email.setCursor(Cursor.HAND);
        email.setOnMouseClicked(e -> telaConvites(primaryStage));
        iconeConvite.setOnMouseClicked(e -> telaConvites(primaryStage));

        email.setStyle("-fx-font-size: 16px;");
        notificacoes.getChildren().addAll(iconeConvite, email);

        sidebar.getChildren().addAll(avatarView, nome, notificacoes);

        Map<String, Runnable> botoes = new LinkedHashMap<>();
        botoes.put("Meus Projetos", () -> telaProjetoDono(primaryStage));
        botoes.put("Colaborando", () -> telaProjetoColaborador(primaryStage));
        botoes.put("Tarefas", () -> telaTarefas(primaryStage));
        botoes.put("Editar Perfil", () -> telaEditarPerfil(primaryStage));
        botoes.put("Sair", () -> abrirModalSair(primaryStage));

        AtomicBoolean roxoFlag = new AtomicBoolean(true);

        botoes.forEach((label, action) -> {
            boolean roxo = roxoFlag.get();
            Button btn = botaoMenu(label, roxo, label.equals("Editar Perfil"));
            btn.setCursor(Cursor.HAND);
            btn.setOnAction(e -> action.run());
            sidebar.getChildren().add(btn);
            roxoFlag.set(!roxo);
        });

        return sidebar;
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
        campoSenha.setText("************");
        campoSenha.setDisable(true);

        Label mensagem = new Label();
        mensagem.setStyle("-fx-font-family: monospace; -fx-font-size: 14px;");

        VBox form = new VBox(18);
        form.setPadding(new Insets(10));
        form.setStyle("-fx-font-family: monospace;");

        form.getChildren().addAll(
                criarCampoComIcone("Username", campoUsername, ""),
                criarCampoComIcone("Nome", campoNome, ""),
                criarCampoComIcone("Sobrenome", campoSobrenome, ""),
                criarCampoComIcone("Email", campoEmail, ""),
                criarCampoComIcone("Senha", campoSenha, "")
        );

        HBox botoes = new HBox(10);
        botoes.setPadding(new Insets(20, 0, 0, 0));
        botoes.setAlignment(Pos.CENTER);

        Button salvarBtn = new Button("Salvar");

        salvarBtn.setPrefHeight(40);
        salvarBtn.setPrefWidth(180);
        salvarBtn.setStyle("-fx-background-color: #8000C9; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-family: monospace; -fx-background-radius: 12px;");
        salvarBtn.setCursor(Cursor.HAND);

        salvarBtn.setOnAction(e -> {
            String nome = campoNome.getText().trim();
            String sobrenome = campoSobrenome.getText().trim();
            String email = campoEmail.getText().trim();

            if (nome.isEmpty() || sobrenome.isEmpty() || email.isEmpty()) {
                mensagem.setText("Os campos não podem estar vazios.");
                mensagem.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            } else {
                boolean sucesso = alterarDadosUsuarioController.alterarDadosUsuario(nome, sobrenome, email);
                if (sucesso) {
                    mensagem.setText("Dados atualizados com sucesso!");
                    mensagem.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                } else {
                    mensagem.setText("Erro ao atualizar dados.");
                    mensagem.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }
            }
        });

        Region espacoEntre = new Region();
        HBox.setHgrow(espacoEntre, Priority.ALWAYS);

        botoes.getChildren().addAll(salvarBtn, espacoEntre);
        areaPrincipal.getChildren().addAll(titulo, form, botoes, mensagem);
    }

    public StackPane criarModalAlterarSenha() {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        // Conteúdo interno do modal
        VBox conteudo = new VBox(15);
        conteudo.setPadding(new Insets(30));
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setMaxWidth(largura * 0.4);
        conteudo.setMaxHeight(altura * 0.45);
        conteudo.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");

        Label titulo = new Label("Alterar Senha");
        titulo.setStyle("-fx-font-size: 24px; -fx-text-fill: #6A0DAD; -fx-font-weight: bold;");

        PasswordField campoAtual = new PasswordField();
        campoAtual.setPromptText("Senha atual");

        PasswordField campoNova = new PasswordField();
        campoNova.setPromptText("Nova senha");

        PasswordField campoConfirmar = new PasswordField();
        campoConfirmar.setPromptText("Confirmar nova senha");

        for (TextField campo : new TextField[]{campoAtual, campoNova, campoConfirmar}) {
            campo.setMaxWidth(400);
            campo.setStyle("-fx-background-radius: 30; -fx-border-radius: 16;");
        }

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 18;");

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 18;");

        HBox botoes = new HBox(15, btnCancelar, btnSalvar);
        botoes.setAlignment(Pos.CENTER);

        Button btnEsqueci = new Button("Esqueci minha senha");
        btnEsqueci.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white; -fx-background-radius: 18;");
        btnEsqueci.setMaxWidth(220);

        conteudo.getChildren().addAll(titulo, campoAtual, campoNova, campoConfirmar, botoes, btnEsqueci);

        // Overlay escurecido
        StackPane fundo = new StackPane(conteudo);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        btnSalvar.setOnAction(e -> {
            //Salver alteracao
            fundo.setVisible(false);
        });

        btnCancelar.setOnAction(e -> fundo.setVisible(false));
        btnEsqueci.setOnAction(e -> {
            System.out.println("Fluxo de recuperação de senha iniciado.");
            fundo.setVisible(false);
        });

        return fundo;

    }

    private void abrirTelaAlterarSenha() {
        StackPane pane =  criarModalAlterarSenha();
        root.getChildren().add(pane);
        pane.setVisible(true);
        pane.toFront();
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


        StackPane campoComIcone = new StackPane(campo);
            Button iconeEdit = new Button("");

            if (labelTexto.equalsIgnoreCase("Username") || labelTexto.equalsIgnoreCase("Senha")) {
                if(labelTexto.equalsIgnoreCase("Username")) {
                    iconeEdit = new Button("Bloqueado");
                } else if (labelTexto.equalsIgnoreCase("Senha")) {
                    iconeEdit = new Button("Editar senha");
                    iconeEdit.setOnAction(e -> {
                        abrirTelaAlterarSenha();
                    });
                }

                iconeEdit.setStyle("-fx-font-family: monospace;");
                StackPane.setAlignment(iconeEdit, Pos.CENTER_RIGHT);
                StackPane.setMargin(iconeEdit, new Insets(0, 10, 0, 0));
                campo.setEditable(false);
                campoComIcone.getChildren().add(iconeEdit);
            }

        return new VBox(5, label, campoComIcone);
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

    private void telaProjetoDono(Stage stage) {
        FXMeusProjetos tela = new FXMeusProjetos();
        FXMeusProjetos.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        tela.start(stage);
    }

    private void telaConvites(Stage stage) {
        FXTelaConvite convites = new FXTelaConvite();
        convites.start(stage);
    }

    private void telaEditarPerfil(Stage stage) {
        start(stage);
    }

    private void telaTarefas(Stage stage) {
        FXMinhasTarefas tarefas = new FXMinhasTarefas();
        tarefas.start(stage);
    }

    private void telaProjetoColaborador(Stage stage) {
        FXProjetosColaborando projetos = new FXProjetosColaborando();
        FXProjetosColaborando.setMeusProjetosController(AllControllerRegistry.getInstance().getMeusProjetosController());
        projetos.start(stage);
    }

    private void telaSair(Stage stage) {
        FxTelaLogin login = new FxTelaLogin();
        FxTelaLogin.setLoginController(AllControllerRegistry.getInstance().getLoginController());
        login.start(stage);
    }

    private void abrirModalSair(Stage stage) {
        StackPane fundo = criarModalSair(stage);
        root.getChildren().add(fundo);
        fundo.setVisible(true);
        fundo.toFront();
    }

    private StackPane criarModalSair(Stage primaryStage) {
        VBox conteudo = new VBox(15);
        conteudo.setPadding(new Insets(30));
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");
        conteudo.setMaxWidth(400);
        conteudo.setMaxHeight(300);

        ImageView ghost = new ImageView(new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true));
        Label titulo = new Label("GooDoolt");
        titulo.setStyle("-fx-font-size: 28px; -fx-text-fill: #6A0DAD; -fx-font-weight: bold;");

        Label pergunta = new Label("Deseja realmente sair da sua conta?");
        pergunta.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");

        Button btnSair = new Button("Sair");
        Button btnCancelar = new Button("Cancelar");

        btnSair.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCancelar.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        btnSair.setOnAction(e -> {
            telaSair(primaryStage);
        });

        btnCancelar.setOnAction(e -> root.getChildren().remove(root.getChildren().size() - 1));

        HBox botoes = new HBox(15, btnSair, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudo.getChildren().addAll(ghost, titulo, pergunta, botoes);

        StackPane fundo = new StackPane(conteudo);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        fundo.setAlignment(Pos.CENTER);
        return fundo;
    }

    private ImageView formatarIcone(String path) {
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
