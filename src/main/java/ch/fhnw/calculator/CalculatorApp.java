package ch.fhnw.calculator;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CalculatorApp extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        Parent rootPanel = new CalculatorUI();

        Scene scene = new Scene(rootPanel);

        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setHeight(590);
        primaryStage.setWidth(320);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(CalculatorApp.class.getResourceAsStream("assets/img/supermario.png")));

        rootPanel.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        rootPanel.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
