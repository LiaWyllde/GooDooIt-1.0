package edu.curso.goodooit.app.controller;

import edu.curso.goodooit.app.model.Tarefa;
import edu.curso.goodooit.app.persistence.implementations.*;

public class AllControllerRegistry {
    private final DataBaseConnection dbConn;

    //Instancia estatica para ser usada em outros lugares
    private static AllControllerRegistry instance;
    //DAOs
    private final UsuarioDAO usuarioDAO;
    private final ConviteDAO conviteDAO;
    private final EquipeDAO equipeDAO;
    private final StatusDAO statusDAO;
    private final TarefaDAO tarefaDAO;
    private final ProjetoDAO projetoDAO;


    //Controllers
    private final MeusProjetosController meusProjetosController;
    private final LoginController loginController;
    private final AlterarSenhaController alterarSenhaController;
    private final AlterarDadosUsuarioController alterarDadosUsuarioController;
    private final ConviteController conviteController;
    private final TarefaController tarefaController;
    private final VisualizarProjetoController visualizarProjetoController;
    private final CadastroController cadastroController;


    public AllControllerRegistry(DataBaseConnection dbConn) {
        this.dbConn = dbConn;
        this.usuarioDAO = new UsuarioDAO(dbConn);
        this.conviteDAO = new ConviteDAO(dbConn);
        this.equipeDAO = new EquipeDAO(dbConn);
        this.statusDAO = new StatusDAO(dbConn);
        this.tarefaDAO = new TarefaDAO(dbConn);
        this.projetoDAO = new ProjetoDAO(dbConn);
        this.meusProjetosController = new MeusProjetosController(usuarioDAO, projetoDAO, statusDAO, tarefaDAO, equipeDAO);
        this.loginController = new LoginController(usuarioDAO);
        this.alterarSenhaController = new AlterarSenhaController(usuarioDAO);
        this.alterarDadosUsuarioController = new AlterarDadosUsuarioController(usuarioDAO);
        this.conviteController = new ConviteController(conviteDAO, usuarioDAO, projetoDAO, equipeDAO);
        this.tarefaController = new TarefaController(tarefaDAO, statusDAO, usuarioDAO, projetoDAO);
        this.visualizarProjetoController = new VisualizarProjetoController(tarefaDAO, statusDAO, equipeDAO);
        this.cadastroController = new CadastroController(usuarioDAO);
    }

    public static void initialize(DataBaseConnection dbConn) {
        if (instance == null) {
            instance = new AllControllerRegistry(dbConn);
        }
    }

    public static AllControllerRegistry getInstance() {
        return instance;
    }

    public MeusProjetosController getMeusProjetosController() {
        return meusProjetosController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public AlterarSenhaController getAlterarSenhaController() {
        return alterarSenhaController;
    }

    public AlterarDadosUsuarioController getAlterarDadosUsuarioController() {
        return alterarDadosUsuarioController;
    }

    public ConviteController getConviteController() {
        return conviteController;
    }

    public TarefaController getTarefaController() {
        return tarefaController;
    }

    public VisualizarProjetoController getVisualizarProjetoController() {
        return visualizarProjetoController;
    }

    public CadastroController getCadastroController() {
        return cadastroController;
    }
}
