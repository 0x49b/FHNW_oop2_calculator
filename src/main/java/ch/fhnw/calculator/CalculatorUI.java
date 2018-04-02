package ch.fhnw.calculator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;

public class CalculatorUI extends GridPane {

    private Pane pane;
    private Button help;
    private Button close;
    private Button minimize;
    private ArrayList<Button> numberButtons = new ArrayList<>();
    private Button dot;
    private Button plus;
    private Button minus;
    private Button multiply;
    private Button divide;
    private Button plusminus;
    private Button equal;
    private Button ac;
    private Label display;

    public CalculatorUI() {
        initializeStylesheet();
        initializeControls();
        layoutControls();
    }

    private void initializeStylesheet() {
        String css = this.getClass().getResource("styles.css").toExternalForm();
        this.getStylesheets().clear();
        this.getStylesheets().add(css);

        Font.loadFont(this.getClass().getResource("assets/fonts/PixelEmulator.ttf").toExternalForm(), 10);

        this.getStyleClass().clear();
        this.getStyleClass().add("root");
    }

    private void initializeControls() {
        for (int i = 0; i < 10; i++) {
            numberButtons.add(createButton(String.valueOf(i)));
            numberButtons.get(i).setId("number" + i);
            numberButtons.get(i).setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            playSound("smb_coin.wav");
                        }
                    }
            );
            if (i == 0) {
                numberButtons.get(i).getStyleClass().add("numbers");
                numberButtons.get(i).getStyleClass().add("zerobutton");
            } else {
                numberButtons.get(i).getStyleClass().add("numbers");
                numberButtons.get(i).getStyleClass().add("numberbuttons");
            }

        }

        help = createButton("");
        help.setId("help");
        help.getStyleClass().add("windowactions");
        help.relocate(55, -29);
        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                showAbout();
            }
        });

        close = createButton("");
        close.setId("close");
        close.getStyleClass().add("windowactions");
        close.relocate(15, -29);
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                closeApplication();
            }
        });

        minimize = createButton("");
        minimize.setId("minimize");
        minimize.getStyleClass().add("windowactions");
        minimize.relocate(35, -29);

        pane = new Pane();
        pane.getChildren().addAll(close, minimize, help);

        plus = createButton("+");
        plus.setId("plus");
        plus.getStyleClass().add("operationbuttons");
        plus.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        playSound("smb_bump.wav");
                    }
                }
        );

        minus = createButton("-");
        minus.setId("minus");
        minus.getStyleClass().add("operationbuttons");
        minus.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        playSound("smb_bump.wav");
                    }
                }
        );

        multiply = createButton("*");
        multiply.setId("multiply");
        multiply.getStyleClass().add("operationbuttons");
        multiply.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        playSound("smb_bump.wav");
                    }
                }
        );

        divide = createButton("/");
        divide.setId("divide");
        divide.getStyleClass().add("operationbuttons");
        divide.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        playSound("smb_bump.wav");
                    }
                }
        );

        dot = createButton(".");
        dot.setId("dot");
        dot.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        playSound("smb_coin.wav");
                    }
                }
        );

        plusminus = createButton("+/-");
        plusminus.setId("plusminus");

        equal = createButton("");
        equal.setId("equal");

        ac = createButton("C");
        ac.setId("ac");

        display = new Label("42");
        display.setId("display");
        display.setMaxWidth(Double.MAX_VALUE);
        display.setAlignment(Pos.BASELINE_RIGHT);

    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);

        return button;
    }

    private void closeApplication() {
        final URL resource = getClass().getResource("assets/sounds/smb_gameover.wav");
        final AudioClip clip = new AudioClip(resource.toString());

        try {
            clip.play(1.0);
            Thread.sleep(4050);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void playSound(String filename) {
        final URL resource = getClass().getResource("assets/sounds/" + filename);
        final AudioClip clip = new AudioClip(resource.toString());
        clip.play(1.0);
    }

    private void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SuperMario Calculator \n\rby lichtwellenreiter");
        alert.setHeaderText("SuperMario Calculator by lichtwellenreiter");
        alert.setContentText("Credits to Nintendo, themushroomkingdom.net and spriters-resource.com");
        alert.showAndWait();
    }

    private void layoutControls() {
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(cc, cc, cc, cc);

        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
        getRowConstraints().addAll(new RowConstraints(), rc, rc, rc, rc, rc);

        add(pane, 0, 0, 4, 1);

        add(display, 0, 1, 4, 1);

        add(ac, 0, 2);
        add(plusminus, 1, 2);
        add(divide, 2, 2);
        add(multiply, 3, 2);

        add(numberButtons.get(7), 0, 3);
        add(numberButtons.get(8), 1, 3);
        add(numberButtons.get(9), 2, 3);
        add(minus, 3, 3);

        add(numberButtons.get(4), 0, 4);
        add(numberButtons.get(5), 1, 4);
        add(numberButtons.get(6), 2, 4);
        add(plus, 3, 4);

        add(numberButtons.get(1), 0, 5);
        add(numberButtons.get(2), 1, 5);
        add(numberButtons.get(3), 2, 5);
        add(equal, 3, 5, 1, 2);

        add(numberButtons.get(0), 0, 6, 2, 1);
        add(dot, 2, 6);
    }

}
