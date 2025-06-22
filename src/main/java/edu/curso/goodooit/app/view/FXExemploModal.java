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

public class FXExemploModal extends Application {

    @Override
    public void start(Stage primaryStage) {
        double larguraTela = Screen.getPrimary().getBounds().getWidth();
        double alturaTela = Screen.getPrimary().getBounds().getHeight();

        Button btnAbrirModalProjeto = new Button("Abrir Modal Projeto");
        Button btnMostrarGoo = new Button("Mostrar GooDoolt");
        Button btnReatribuir = new Button("Reatribuir Tarefa");
        Button btnAlterarSenha = new Button("Alterar Senha");
        Button btnNovaTarefa = new Button("Nova Tarefa");
        Button btnEsqueciSenha = new Button("Esqueci a senha");
        Button btnConfirmarAcesso = new Button("Confirmar Acesso");
        Button btnVisualizarConvites = new Button("Visualizar Convites");
        Button btnNovaLista = new Button("Nova Lista");
        
        for (Button btn : new Button[]{btnAbrirModalProjeto, btnMostrarGoo, btnReatribuir, btnAlterarSenha, btnNovaTarefa, btnEsqueciSenha, btnConfirmarAcesso, btnVisualizarConvites, btnNovaLista}) {
            btn.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white; -fx-background-radius: 10;");
            btn.setPrefWidth(250);
        }

        VBox conteudoPrincipal = new VBox(20, btnAbrirModalProjeto, btnMostrarGoo, btnReatribuir, btnAlterarSenha, btnNovaTarefa, btnEsqueciSenha, btnConfirmarAcesso, btnVisualizarConvites, btnNovaLista);
        conteudoPrincipal.setAlignment(Pos.CENTER);

        StackPane modalProjeto = criarModalProjeto(() -> System.out.println("Projeto salvo."));
        StackPane modalGooDoolt = criarModalGooDoolt(() -> System.out.println("Saindo da conta."));
        StackPane modalReatribuir = criarModalReatribuir(() -> System.out.println("Tarefa reatribuída."));
        StackPane modalSenha = criarModalAlterarSenha(() -> System.out.println("Senha alterada com sucesso."));
        StackPane modalNovaTarefa = criarModalNovaTarefa(() -> System.out.println("Tarefa criada com sucesso!"));
        StackPane modalEsqueciSenha = criarModalEsqueciSenha(() -> System.out.println("Saindo do esqueci Senha"));
        StackPane modalConfirmarAcesso = criarModalConfirmarAcesso(() -> System.out.println("Saindo do Confirmar Acesso"));
        StackPane modalVisualizarConvites = criarModalVisualizarConvites(() -> System.out.println("Saindo do Visualizar Convites"));
        StackPane modalNovaLista = criarModalNovaLista(() -> System.out.println("Saindo do nova lista"));

        StackPane root = new StackPane(conteudoPrincipal, modalProjeto, modalGooDoolt, modalReatribuir, modalSenha, modalNovaTarefa, modalEsqueciSenha, modalConfirmarAcesso, modalVisualizarConvites, modalNovaLista);

        btnAbrirModalProjeto.setOnAction(e -> modalProjeto.setVisible(true));
        btnMostrarGoo.setOnAction(e -> modalGooDoolt.setVisible(true));
        btnReatribuir.setOnAction(e -> modalReatribuir.setVisible(true));
        btnAlterarSenha.setOnAction(e -> modalSenha.setVisible(true));
        btnNovaTarefa.setOnAction(e -> modalNovaTarefa.setVisible(true));
        btnEsqueciSenha.setOnAction(e -> modalEsqueciSenha.setVisible(true));
        btnConfirmarAcesso.setOnAction(e -> modalConfirmarAcesso.setVisible(true));
        btnVisualizarConvites.setOnAction(e -> modalVisualizarConvites.setVisible(true));
        btnNovaLista.setOnAction(e -> modalNovaLista.setVisible(true));
        

        Scene scene = new Scene(root, larguraTela * 0.8, alturaTela * 0.8);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Exemplo de Múltiplos Modais");
        primaryStage.show();
    }

    public StackPane criarModalProjeto(Runnable acaoSalvar) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudoModal = new VBox(10);
        conteudoModal.setPadding(new Insets(20));
        conteudoModal.setAlignment(Pos.CENTER_LEFT);
        conteudoModal.setMaxWidth(largura * 0.5);
        conteudoModal.setMaxHeight(altura * 0.5);
        conteudoModal.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");

        TextField tfNome = new TextField();
        TextArea taDescricao = new TextArea();
        TextField tfInicio = new TextField();
        TextField tfFim = new TextField();

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        HBox botoes = new HBox(20, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudoModal.getChildren().addAll(
            new Label("Nome do Projeto"), tfNome,
            new Label("Descrição"), taDescricao,
            new Label("Prazo previsto para início"), tfInicio,
            new Label("Prazo previsto para finalização"), tfFim,
            botoes
        );

        StackPane fundo = new StackPane(conteudoModal);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        btnSalvar.setOnAction(e -> {
            acaoSalvar.run();
            fundo.setVisible(false);
        });

        btnCancelar.setOnAction(e -> fundo.setVisible(false));

        return fundo;
    }

    public StackPane criarModalGooDoolt(Runnable acaoSair) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudo = new VBox(15);
        conteudo.setPadding(new Insets(30));
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setMaxWidth(largura * 0.35);
        conteudo.setMaxHeight(altura * 0.4);
        conteudo.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");
        
        Image avatarImage = new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true);
        ImageView avatarView = new ImageView(avatarImage);
        ImageView ghost = new ImageView(new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true)); // você deve ter esta imagem no recurso
        ghost.setFitHeight(80);
        ghost.setFitWidth(80);
        
        Label titulo = new Label("GooDoolt");
        titulo.setStyle("-fx-font-size: 28px; -fx-text-fill: #6A0DAD; -fx-font-weight: bold;");

        Label pergunta = new Label("Deseja realmente sair da sua conta?");
        pergunta.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");

        Button btnSair = new Button("Sair");
        Button btnCancelar = new Button("Cancelar");

        btnSair.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCancelar.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        HBox botoes = new HBox(15, btnSair, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudo.getChildren().addAll(ghost, titulo, pergunta, botoes);

        StackPane fundo = new StackPane(conteudo);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        btnSair.setOnAction(e -> {
            fundo.setVisible(false);
            acaoSair.run();
        });

        btnCancelar.setOnAction(e -> fundo.setVisible(false));

        return fundo;
    }

    public StackPane criarModalReatribuir(Runnable acaoReatribuir) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudo = new VBox(15);
        conteudo.setPadding(new Insets(30));
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setMaxWidth(largura * 0.35);
        conteudo.setMaxHeight(altura * 0.4);
        conteudo.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");
        
        Label titulo = new Label("🔄 Reatribuir tarefa");
        titulo.setStyle("-fx-font-size: 22px; -fx-text-fill: #6A0DAD; -fx-font-weight: bold;");

        Label instrucao = new Label("Digite o nome ou usuário do novo responsável pela tarefa:");
        instrucao.setStyle("-fx-font-size: 14px;");

        TextField campoUsuario = new TextField();

        Button btnReatribuir = new Button("Reatribuir");
        Button btnCancelar = new Button("Cancelar");

        campoUsuario.setStyle("-fx-background-radius: 20; -fx-border-radius: 20;");
        btnReatribuir.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 20;");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 20;");

        HBox botoes = new HBox(15, btnReatribuir, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudo.getChildren().addAll(titulo, instrucao, campoUsuario, botoes);

        StackPane fundo = new StackPane(conteudo);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        btnCancelar.setOnAction(e -> fundo.setVisible(false));
        btnReatribuir.setOnAction(e -> {
            acaoReatribuir.run();
            fundo.setVisible(false);
        });

        return fundo;
    }
    
    public StackPane criarModalAlterarSenha(Runnable acaoSalvarSenha) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        // Conteúdo interno do modal
        VBox conteudo = new VBox(15);
        conteudo.setPadding(new Insets(30));
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setMaxWidth(largura * 0.35);
        conteudo.setMaxHeight(altura * 0.4);
        conteudo.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");
        
        Label titulo = new Label("🔐 Alterar Senha");
        titulo.setStyle("-fx-font-size: 24px; -fx-text-fill: #6A0DAD; -fx-font-weight: bold;");

        PasswordField campoAtual = new PasswordField();
        campoAtual.setPromptText("Senha atual");

        PasswordField campoNova = new PasswordField();
        campoNova.setPromptText("Nova senha");

        PasswordField campoConfirmar = new PasswordField();
        campoConfirmar.setPromptText("Confirmar nova senha");

        for (TextField campo : new TextField[]{campoAtual, campoNova, campoConfirmar}) {
            campo.setMaxWidth(250);
            campo.setStyle("-fx-background-radius: 16; -fx-border-radius: 16;");
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
            acaoSalvarSenha.run();
            fundo.setVisible(false);
        });

        btnCancelar.setOnAction(e -> fundo.setVisible(false));
        btnEsqueci.setOnAction(e -> {
            System.out.println("Fluxo de recuperação de senha iniciado.");
            fundo.setVisible(false);
        });

        return fundo;
    }

    public StackPane criarModalNovaTarefa(Runnable acaoSalvarTarefa) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();
        
        VBox conteudo = new VBox(15);
        conteudo.setPadding(new Insets(30));
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setMaxWidth(largura * 0.35);
        conteudo.setMaxHeight(altura * 0.4);
        conteudo.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");
        Label titulo = new Label("📋 Nova Tarefa");
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

        TextField tfInicio = new TextField();
        tfInicio.setPromptText("Início");
        TextField tfPrazo = new TextField();
        tfPrazo.setPromptText("Prazo de Entrega");

        for (TextField campo : new TextField[]{tfInicio, tfPrazo}) {
            campo.setMaxWidth(Double.MAX_VALUE);
            campo.setStyle("-fx-background-radius: 16; -fx-border-radius: 16;");
        }

        HBox datas = new HBox(15, tfInicio, tfPrazo);
        datas.setAlignment(Pos.CENTER);
        datas.setMaxWidth(280);
        HBox.setHgrow(tfInicio, Priority.ALWAYS);
        HBox.setHgrow(tfPrazo, Priority.ALWAYS);

        ComboBox<String> cbPrioridade = new ComboBox<>();
        cbPrioridade.getItems().addAll("Alta", "Média", "Baixa");
        cbPrioridade.setPromptText("Prioridade");
        cbPrioridade.setMaxWidth(Double.MAX_VALUE);
        cbPrioridade.setStyle("-fx-background-radius: 16; -fx-border-radius: 16;");

        ComboBox<String> cbLista = new ComboBox<>();
        cbLista.getItems().addAll("Design", "Desenvolvimento", "Marketing");
        cbLista.setPromptText("Lista de atuação");
        cbLista.setMaxWidth(Double.MAX_VALUE);
        cbLista.setStyle("-fx-background-radius: 16; -fx-border-radius: 16;");

        HBox seletores = new HBox(15, cbPrioridade, cbLista);
        seletores.setAlignment(Pos.CENTER);
        seletores.setMaxWidth(280);
        HBox.setHgrow(cbPrioridade, Priority.ALWAYS);
        HBox.setHgrow(cbLista, Priority.ALWAYS);

        TextField tfResponsavel = new TextField();
        tfResponsavel.setPromptText("Responsável");
        tfResponsavel.setMaxWidth(280);
        tfResponsavel.setStyle("-fx-background-radius: 16; -fx-border-radius: 16;");

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 18;");

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 18;");

        HBox botoes = new HBox(15, btnCancelar, btnSalvar);
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
            acaoSalvarTarefa.run();
            fundo.setVisible(false);
        });

        btnCancelar.setOnAction(e -> fundo.setVisible(false));

        return fundo;
    }
    
    public StackPane criarModalEsqueciSenha(Runnable acaoVoltar) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudoModal = new VBox(15);
        conteudoModal.setPadding(new Insets(30));
        conteudoModal.setAlignment(Pos.CENTER);
        conteudoModal.setMaxWidth(largura * 0.35);
        conteudoModal.setMaxHeight(altura * 0.4);
        conteudoModal.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");
        
        StackPane fundo = new StackPane(conteudoModal);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        Label titulo = new Label("Esqueci minha senha");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-family: monospace; -fx-text-fill: black;");

        // Imagem do fantasminha
        Image avatarImage = new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true);
        ImageView avatarView = new ImageView(avatarImage);
        ImageView ghost = new ImageView(new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true)); // você deve ter esta imagem no recurso
        ghost.setFitHeight(80);
        ghost.setFitWidth(80);

        Label cuidado = new Label("Mais cuidado na próxima");
        cuidado.setStyle("-fx-font-size: 16px; -fx-font-family: monospace; -fx-text-fill: black;");

        Label instrucao = new Label("Envie um email para:");
        instrucao.setStyle("-fx-font-size: 14px; -fx-font-family: monospace; -fx-text-fill: black;");

        Label email = new Label("businesgoodooit@gmail.com");
        email.setStyle("-fx-font-size: 14px; -fx-font-family: monospace; -fx-text-fill: black;");

        
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle("-fx-background-color: #8000C9; -fx-text-fill: white; -fx-font-family: monospace; -fx-background-radius: 12px;");
        btnVoltar.setPrefWidth(150);
        btnVoltar.setOnAction(e -> {
            fundo.setVisible(false);
            acaoVoltar.run();
        });

        VBox.setMargin(btnVoltar, new Insets(10, 0, 0, 0));

        conteudoModal.getChildren().addAll(titulo, ghost, cuidado, instrucao, email, btnVoltar);


        return fundo;
    }

    public StackPane criarModalConfirmarAcesso(Runnable acaoReatribuir) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudoModal = new VBox(15);
        conteudoModal.setPadding(new Insets(30));
        conteudoModal.setAlignment(Pos.CENTER);
        conteudoModal.setMaxWidth(largura * 0.35);
        conteudoModal.setMaxHeight(altura * 0.4);
        conteudoModal.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");

        Label instrucao = new Label("Deseja realmente aceitar o convite para");
        instrucao.setStyle("-fx-font-size: 14px;");
        instrucao.setAlignment(Pos.CENTER);

        Label titulo = new Label("Projeto Furão biônico?");
        titulo.setStyle("-fx-font-size: 22px; -fx-text-fill: #6A0DAD; -fx-font-weight: bold;");
        titulo.setAlignment(Pos.CENTER);

        Button btnReatribuir = new Button("Sim");
        Button btnCancelar = new Button("Não");

        btnReatribuir.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 20;");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 20;");

        HBox botoes = new HBox(15, btnReatribuir, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudoModal.getChildren().addAll(instrucao, titulo, botoes);

        StackPane fundo = new StackPane(conteudoModal);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        btnCancelar.setOnAction(e -> fundo.setVisible(false));
        btnReatribuir.setOnAction(e -> {
            acaoReatribuir.run();
            fundo.setVisible(false);
        });

        return fundo;
    }
    
    public StackPane criarModalVisualizarConvites(Runnable acaoReatribuir) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudoModal = new VBox(15);
        conteudoModal.setPadding(new Insets(30));
        conteudoModal.setAlignment(Pos.CENTER);
        conteudoModal.setMaxWidth(largura * 0.35);
        conteudoModal.setMaxHeight(altura * 0.4);
        conteudoModal.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");
        
        // Imagem do fantasminha
        Image avatarImage = new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true);
        ImageView avatarView = new ImageView(avatarImage);
        ImageView ghost = new ImageView(new Image(getClass().getResourceAsStream("/images/Goo.png"), 100, 100, true, true)); // você deve ter esta imagem no recurso
        ghost.setFitHeight(80);
        ghost.setFitWidth(80);
        
        Label nomeCompleto = new Label("Gustavo Henrique");
        nomeCompleto.setStyle("-fx-font-size: 24px;");
        nomeCompleto.setAlignment(Pos.CENTER);

        Label usuario = new Label("Usuário: GustavoSilva");
        usuario.setStyle("-fx-font-size: 20px;");
        usuario.setAlignment(Pos.CENTER);

        Label mensagem = new Label("Te convidou para participar do projeto");
        mensagem.setStyle("-fx-font-size: 15px;");
        mensagem.setAlignment(Pos.CENTER);
        
        Label projeto = new Label("Projeto furão mecânico");
        projeto.setStyle("-fx-font-size: 20px;");
        projeto.setAlignment(Pos.CENTER);
        
        Button btnReatribuir = new Button("Aceitar");
        Button btnCancelar = new Button("Recusar");

        btnReatribuir.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 20;");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 20;");

        HBox botoes = new HBox(15, btnReatribuir, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudoModal.getChildren().addAll(ghost, nomeCompleto, usuario, mensagem, projeto, botoes);

        StackPane fundo = new StackPane(conteudoModal);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        btnCancelar.setOnAction(e -> fundo.setVisible(false));
        btnReatribuir.setOnAction(e -> {
            acaoReatribuir.run();
            fundo.setVisible(false);
        });

        return fundo;
    }
    
    public StackPane criarModalNovaLista(Runnable acaoSalvar) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        VBox conteudoModal = new VBox(15);
        conteudoModal.setPadding(new Insets(30));
        conteudoModal.setAlignment(Pos.CENTER);
        conteudoModal.setMaxWidth(largura * 0.35);
        conteudoModal.setMaxHeight(altura * 0.4);
        conteudoModal.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 20;");

        Label adicionarLista = new Label("Adicionar Lista em Projeto galo eletrônico");
        adicionarLista.setStyle("-fx-font-size: 20px;");
        adicionarLista.setMaxWidth(Double.MAX_VALUE);
        adicionarLista.setAlignment(Pos.CENTER);
        adicionarLista.setWrapText(true);

        Label lista = new Label("Digite o nome da Lista");
        lista.setStyle("-fx-font-size: 16px;");
        lista.setMaxWidth(Double.MAX_VALUE);
        lista.setAlignment(Pos.CENTER);
        lista.setWrapText(true);

        TextField tfFim = new TextField();

        Button btnSalvar = new Button("Adicionar");
        Button btnCancelar = new Button("Cancelar");
        
        btnSalvar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 20;");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 20;");


        HBox botoes = new HBox(20, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        conteudoModal.getChildren().addAll(
            adicionarLista,
            lista,
            tfFim,
            botoes
        );

        StackPane fundo = new StackPane(conteudoModal);
        fundo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        fundo.setVisible(false);
        fundo.setAlignment(Pos.CENTER);

        btnSalvar.setOnAction(e -> {
            acaoSalvar.run();
            fundo.setVisible(false);
        });

        btnCancelar.setOnAction(e -> fundo.setVisible(false));

        return fundo;
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}