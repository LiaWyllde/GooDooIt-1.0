# Anotações

## Dados semelhantes em várias telas (o menu)

// Label nome = new Label("Julia Fernandes"); // Property nome completo do Usuário (nome e sobrenome)

// Label sino = new Label("🔔 5"); // Property quantidade de notificação do Usuário

// Label email = new Label("✉️ 1"); // Property quantidade de convites do Usuário 

## FXEditarPerfil
117 - 123

<code>
form.getChildren().addAll(
    criarCampoComIcone("Username", new TextField("jfernandes"), "⛔"), // Property Username do Usuário
    criarCampoComIcone("Nome", new TextField("Julia"), "✏️"), // Property Nome do Usuário
    criarCampoComIcone("Sobrenome", new TextField("Fernandes"), "✏️"), // Property Sobrenome do Usuário
    criarCampoComIcone("Email", new TextField("julia@example.com"), "✏️"), // Property Email do Usuário
    criarCampoComIcone("Senha", new PasswordField(), "✏️") # Property Senha do Usuário (fica sem texto)
);
</code>

185 - 189


// Dados do modal de alterar senha


<code>
PasswordField campoAtual = new PasswordField();
campoAtual.setPromptText("Senha atual"); // Texto de "Senha Atual"
PasswordField campoNova = new PasswordField();
campoNova.setPromptText("Nova senha"); // Texto da "Nova Senha"
</code>
## FXMeusProjetos

//  76-79
<code>
        // for(Projeto p: ObservableList<Projeto>)
        projetosContainer.getChildren().add(criarBlocoProjeto("Projeto galo eletrônico", "em andamento", 25, 95, true));
        projetosContainer.getChildren().add(criarBlocoProjeto("Projeto visitante", "concluído", 10, 0, false));
        // booleano para confirmar se o Usuário tem poderes de edição ao projeto ou não
</code>

// 175-179

// Actions para atualizar o projeto
<code>        
        btnSalvar.setOnAction(e -> {
            acaoSalvar.run();
            fundo.setVisible(false);
        });
</code>

## FXMinhasTarefas

<code>
39 - Label sino = new Label("🔔 5"); // Property quantidade de notificação do Usuário
40 - Label email = new Label("✉️ 1"); // Property quantidade de convites do Usuário 
</code>


78 - 82

<code>
        // for(Projeto p: ObservableList<Tarefas>)
        tarefas.getChildren().addAll(
                blocoTarefa("Desenvolver o som do cocoricó", "aguardando dispositivo", "Alta", "24/06/2025"),
                blocoTarefa("Iniciar testes", "aguardando furão", "Muito alta", "24/06/2025"),
                blocoTarefa("Iniciar testes", "aguardando galo", "Alta", "24/06/2025")
        );
</code>
            
## FXTelaProjetoDono

<code>
22 - private boolean usuarioEhDono = true; // <- controle de acesso
37 - Label titulo = new Label("Projeto galo eletrônico");
</code>
    
42 - 45

<code>
        if (usuarioEhDono) {
            iconeEdicao.setOnMouseClicked(e -> abrirModalProjeto("Projeto galo eletrônico", "Projeto inicial para confecção dos modelos do Galotron3000", "28/04/2025", "22/10/2027")); // dados do objeto Projeto
        }
</code>

50 -   status.getItems().addAll("Planejado", "Em andamento", "Concluído"); // padrão dos status

56 -       TextArea descricao = new TextArea("Projeto inicial para confecção dos modelos do Galotron3000"); // Descrição projeto

100 - 107

<code>
        adicionarTarefaAoQuadro(quadroNaoIniciado, "Documentar base", "Alta 25/06/25");
        adicionarTarefaAoQuadro(quadroNaoIniciado, "Pesquisar componentes", "Média 27/06/25");
        adicionarTarefaAoQuadro(quadroAndamento, "Montar protótipo", "Alta 20/06/25");
        adicionarTarefaAoQuadro(quadroConcluidas, "Planejamento inicial", "Baixa 15/06/25");
        quadros.getChildren().addAll(quadroNaoIniciado, quadroAndamento, quadroConcluidas);
</code>

101 - 104

<code>
// Event Listener do modal de alterar o projeto
        salvar.setOnAction(e -> {
            acaoSalvar.run();
            modalProjeto.setVisible(false);
        });
</code>

## FXVisualizarMembroProjeto

26 -  private boolean usuarioEhDono = false; // ← controle de permissão

75 -         Label title = new Label("Projeto galo eletrônico"); // Nome projeto

94 -         Label quantidade = new Label("5"); // total membros no projeto

110      
<code>
        // for(Usuario u: ObservableList<Membros> do projeto)
blocoMembros.getChildren().addAll(
            criarMembro("", ""), // nome, username
        );
</code>

## FXVisualizarNotificacoes

21 -  primaryStage.setTitle("Notificações - Julia Fernandes"); // Nome usuário


63 - 71

<code>
    
// Notificações do Usuário
        VBox listaNotificacoes = new VBox(20);
        listaNotificacoes.getChildren().addAll(
                blocoNotificacao("17/06/2025  18:03", "Atualização de status", 
                        "O status do projeto “furão biônico” foi alterado para “em andamento”"),
                blocoNotificacao("17/06/2025  18:03", "Novo participante na lista", 
                        "Nicolas Domingos agora faz parte da lista “olho laser”"),
                blocoNotificacao("16/06/2025  18:03", "Atribuição de tarefa", 
                        "Gustavo atribuiu uma tarefa a você “Desenvolver olho biônico”")
        );
</code>

## FXVisualizarTelasProjeto

75 - Label title = new Label("Projeto galo eletrônico"); // nome projeto

98 -         Label quantidade = new Label("7"); // total tarefas no projeto


118 - 126

<code>
        // Listagem de tarefas
        for (int i = 0; i < 7; i++) {
            blocoTarefas.getChildren().add(criarTarefaItem(
                "Documentar criação de dispositivos",
                "Alta",
                "24/06/2025",
                "Lista de tarefas: Lista de engenharia",
                !usuarioEhDono
            ));
        }
</code>        
