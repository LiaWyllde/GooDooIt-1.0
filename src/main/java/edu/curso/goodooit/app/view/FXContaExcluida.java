package edu.curso.goodooit.app.view;

import edu.curso.goodooit.app.controller.AllControllerRegistry;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FXContaExcluida extends Application {

    @Override
    public void start(Stage stage) {
        double largura = Screen.getPrimary().getBounds().getWidth();
        double altura = Screen.getPrimary().getBounds().getHeight();

        // Logo
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/images/LogoComTexto.png")));
        logo.setPreserveRatio(true);
        logo.setFitHeight(120);

        // Mensagem
        Text mensagem = new Text("Sua conta foi\nexcluída, sinta-se\nlivre para voltar\nquando quiser!");
        mensagem.setFont(Font.font("Courier New", 22));
        mensagem.setFill(Color.BLACK);
        mensagem.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // Botão
        Button btnInscrever = new Button("Inscrever-se novamente");
        btnInscrever.setPrefHeight(40);
        btnInscrever.setPrefWidth(300);
        btnInscrever.setStyle("-fx-background-color: #8000C9; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-family: monospace; -fx-background-radius: 12px;");
        btnInscrever.setCursor(Cursor.HAND);

        btnInscrever.setOnAction(e -> {
            FXTelaCadastro telaCadastro = new FXTelaCadastro();
            FXTelaCadastro.setCadastroController(AllControllerRegistry.getInstance().getCadastroController());
            telaCadastro.start(stage);
        });

        // Caixa central
        VBox caixaRoxa = new VBox(30, mensagem, btnInscrever);
        caixaRoxa.setAlignment(Pos.CENTER);
        caixaRoxa.setPadding(new Insets(50));
        caixaRoxa.setStyle("-fx-background-color: #c9a4ec; -fx-background-radius: 10px;");
        caixaRoxa.setMaxWidth(450);

        // Layout principal
        VBox layout = new VBox(40, logo, caixaRoxa);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f0f0f0;");
        layout.setPrefSize(largura * 0.5, altura * 0.6);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.setTitle("Conta Excluída - GooDoolt");
        stage.show();
    }
}
