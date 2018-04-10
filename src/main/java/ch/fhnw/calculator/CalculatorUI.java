package ch.fhnw.calculator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.util.Duration;

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
    private boolean dotSet;
    private ImageView mario;

    public CalculatorUI() {
        initializeStylesheet();
        initializeControls();
        layoutControls();
        this.dotSet = false;
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
            int finalI = i;
            numberButtons.get(i).setOnAction(event -> addNumber(finalI));
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
        help.setOnAction(e -> showAbout());

        close = createButton("");
        close.setId("close");
        close.getStyleClass().add("windowactions");
        close.relocate(15, -29);
        close.setOnAction(e -> closeApplication());

        minimize = createButton("");
        minimize.setId("minimize");
        minimize.getStyleClass().add("windowactions");
        minimize.relocate(35, -29);

        pane = new Pane();
        pane.getChildren().addAll(close, minimize, help);

        plus = createButton("+");
        plus.setId("plus");
        plus.getStyleClass().add("operationbuttons");
        plus.setOnAction(event -> playSound("smb_bump.wav"));

        minus = createButton("-");
        minus.setId("minus");
        minus.getStyleClass().add("operationbuttons");
        minus.setOnAction(event -> playSound("smb_bump.wav"));

        multiply = createButton("*");
        multiply.setId("multiply");
        multiply.getStyleClass().add("operationbuttons");
        multiply.setOnAction(event -> playSound("smb_bump.wav"));

        divide = createButton("/");
        divide.setId("divide");
        divide.getStyleClass().add("operationbuttons");
        divide.setOnAction(event -> playSound("smb_bump.wav"));

        dot = createButton(".");
        dot.setId("dot");
        dot.setOnAction(event -> addDot());

        plusminus = createButton("+/-");
        plusminus.setId("plusminus");
        plusminus.setOnAction(event -> playSound("smw_message_block.wav"));

        equal = createButton("");
        equal.setId("equal");
        equal.setOnAction(event -> playSound("smw_break_block.wav"));

        ac = createButton("");
        ac.setId("ac");
        ac.setOnAction(event -> clearDisplay());

        display = new Label("0");
        display.setId("display");
        display.setMaxWidth(Double.MAX_VALUE);
        display.setAlignment(Pos.BASELINE_RIGHT);

        Image marioimage = new Image(CalculatorUI.class.getResourceAsStream("assets/img/mariowalk2.gif"));
        mario = new ImageView(marioimage);
        mario.setFitHeight(89);
        mario.setFitWidth(35);
    }

    private void jumpMario() {
        Image mariojump = new Image(CalculatorUI.class.getResourceAsStream("assets/img/mariojump2.gif"));
        this.mario.setImage(mariojump);
    }

    private void walkMario() {
        Image mariowalk = new Image(CalculatorUI.class.getResourceAsStream("assets/img/mariowalk2.gif"));
        this.mario.setImage(mariowalk);
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
            clip.play(.5);
            Thread.sleep(4050);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void playSound(String filename) {
        final URL resource = getClass().getResource("assets/sounds/" + filename);
        final AudioClip clip = new AudioClip(resource.toString());

        if (filename.equals("smb_coin.wav")) {

            Timeline marioanim = new Timeline(
                    new KeyFrame(Duration.ZERO, event -> this.jumpMario()),
                    new KeyFrame(Duration.seconds(0.2), event -> clip.play(1.0)),
                    new KeyFrame(Duration.seconds(0.5), event -> this.walkMario())
            );
            marioanim.setAutoReverse(false);
            marioanim.setCycleCount(1);
            marioanim.play();
        } else {
            clip.play(1.0);
        }
    }

    private void addNumber(int number) {

        //if the entered number is !0 then play the coin sound
        if (((number != 0))) {
            this.playSound("smb_coin.wav");
        } else {
            this.playSound("smw_swimming.wav");
        }

        StringBuilder sb = new StringBuilder();
        String display;

        if (this.display.getText().equals("0")) {
            display = "" + number;
        } else {
            display = sb.append(this.display.getText()).append(number).toString();
        }

        this.display.setText(display);


    }

    private void addDot() {
        if (!this.dotSet) {
            StringBuilder sb = new StringBuilder();
            this.display.setText(sb.append(this.display.getText()).append(".").toString());
            this.dotSet = true;
            this.playSound("smb_coin.wav");
        }
    }

    private void clearDisplay() {
        this.display.setText("0");
        this.dotSet = false;
        this.playSound("smw_pipe.wav");
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

        add(mario, 1, 7, 1, 1);
    }

}
