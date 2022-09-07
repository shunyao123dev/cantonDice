package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private static final int HEX_HEIGHT = 200;

    private final Group root = new Group();
    private final Group controls = new Group();

    private final Group hexagons = new Group();

    private final Group structures = new Group();


    private TextField playerTextField;
    private TextField boardTextField;


    /**
     * Show the state of a (single player's) board in the window.
     *
     * @param: The string representation of the board state.
     */
    void displayState(String board_state) {
        // FIXME Task 5: implement the state viewer

    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Board State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(500);
        Button button = new Button("Show");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel, boardTextField, button);
        hb.setSpacing(10);
        controls.getChildren().add(hb);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Board State Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT, Color.GREEN);

        //Setting ocean image background

        Image ocean = new Image(new FileInputStream("assets/Ocean.png"));
        ImageView oceanView = new ImageView(ocean);
        oceanView.setX(600);
        oceanView.setY(85);
        oceanView.setFitHeight(1200);
        oceanView.setFitWidth(550);
        oceanView.setPreserveRatio(true);

        //Creating the hexagons for the island
        Polyline hexagon1 = makeHexagon(150, 740, 285);
        hexagons.getChildren().add(hexagon1);
        Polyline hexagon2 = makeHexagon(150, 875, 207);
        hexagons.getChildren().add(hexagon2);
        Polyline hexagon3 = makeHexagon(150, 1010, 285);
        hexagons.getChildren().add(hexagon3);
        Polyline hexagon4 = makeHexagon(150, 740, 440);
        hexagons.getChildren().add(hexagon4);
        Polyline hexagon5 = makeHexagon(150, 875, 518);
        hexagons.getChildren().add(hexagon5);
        Polyline hexagon6 = makeHexagon(150, 1010, 440);
        hexagons.getChildren().add(hexagon6);

        //Setting terrain on each island

        Image ore = new Image(new FileInputStream("assets/ORE.jpg"));
        Polyline oreHexagon = makeHexagon(125, 740, 285);
        oreHexagon.setFill(new ImagePattern(ore, 0, 0, 1, 1, true));
        hexagons.getChildren().add(oreHexagon);

        Image grain = new Image(new FileInputStream("assets/GRAIN.png"));
        Polyline grainHexagon = makeHexagon(125, 740, 440);
        grainHexagon.setFill(new ImagePattern(grain, 0, 0, 1, 1, true));
        hexagons.getChildren().add(grainHexagon);

        Image wool = new Image(new FileInputStream("assets/WOOL.jpg"));
        Polyline woolHexagon = makeHexagon(125, 875, 518);
        woolHexagon.setFill(new ImagePattern(wool, 0, 0, 1, 1, true));
        hexagons.getChildren().add(woolHexagon);

        Image timber = new Image(new FileInputStream("assets/TIMBER.png"));
        Polyline timberHexagon = makeHexagon(125, 1010, 440);
        timberHexagon.setFill(new ImagePattern(timber, 0, 0, 1, 1, true));
        hexagons.getChildren().add(timberHexagon);

        Image brick = new Image(new FileInputStream("assets/BRICK.png"));
        Polyline brickHexagon = makeHexagon(125, 1010, 285);
        brickHexagon.setFill(new ImagePattern(brick, 0, 0, 1, 1, true));
        hexagons.getChildren().add(brickHexagon);

        Image mystery = new Image(new FileInputStream("assets/MYSTERY.jpg"));
        Polyline mysteryHexagon = makeHexagon(125, 875, 207);
        mysteryHexagon.setFill(new ImagePattern(mystery, 0, 0, 1, 1, true));
        hexagons.getChildren().add(mysteryHexagon);


        //Small circles with resource and knights

        makeCircle("ORE", 30, 710, 255);
        makeCircle("GRAIN", 30, 710, 410);
        makeCircle("WOOL", 30, 845, 490);
        makeCircle("TIMBER", 30, 982, 410);
        makeCircle("BRICK", 30, 982, 255);
        makeCircle("MYSTERY", 30, 845, 175);

        makeKnight("1", 731, 240); //K1
        makeKnight("2", 731, 395); //K2
        makeKnight("3", 866, 475); //K3
        makeKnight("4", 1003, 395); //K4
        makeKnight("5", 1003, 240); //K5
        makeKnight("6", 866, 160); //K6



        //Roads

        Rectangle startRoad = new Rectangle();
        startRoad.setHeight(45);
        startRoad.setWidth(15);
        startRoad.setX(600);
        startRoad.setY(85);
        startRoad.setFill(Color.PURPLE);
        startRoad.setStroke(Color.BLACK);
        Image arrow = new Image(new FileInputStream("assets/ARROW.png"));
        ImageView arrowView = new ImageView(arrow);
        arrowView.setX(600);
        arrowView.setY(85);
        arrowView.setFitHeight(1200);
        arrowView.setFitWidth(550);
        arrowView.setPreserveRatio(true);
        structures.getChildren().addAll(arrowView, startRoad);




        makeRoad("1", 45, 15, 805,300, 30); //R0
        makeRoad("1", 45, 15, 720,370, -90); //R1
        makeRoad("1", 45, 15, 778,382, -30); //R2
        makeRoad("1", 45, 15, 815,450, 30); //R3
        makeRoad("1", 45, 15, 720,518, -90); //R4
        makeRoad("1", 45, 15, 790,530, -30); //R5
        makeRoad("1", 45, 15, 853,596, -90); //R6
        makeRoad("1", 45, 15, 937,535, 30); //R7
        makeRoad("1", 45, 15, 922,458, -30); //R8
        makeRoad("1", 45, 15, 953,380, 30); //R9
        makeRoad("1", 45, 15, 928,300, -30); //R10
        makeRoad("1", 45, 15, 945,225, 30); //R11
        makeRoad("1", 45, 15, 989,518, -90); //R12
        makeRoad("1", 45, 15, 1070,460, 30); //R13
        makeRoad("1", 45, 15, 1044,375, -30); //R14
        makeRoad("1", 45, 15, 1073,300, 30); //R15


        //Towns


        //Settlements


        //Resource key


        //Scoreboard


        //Titles

        root.getChildren().add(controls);
        root.getChildren().add(oceanView);
        root.getChildren().add(hexagons);
        root.getChildren().add(structures);


        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    //This code for creating the hexagon has been created
    public Polyline makeHexagon(int length, double xCoord, double yCoord) {
        double sideLength = length * 3 / 5;
        ArrayList<Double> points = new ArrayList<>();
        double bearing = 0;
        for (int i = 0; i < 6; i++) {
            double x = Math.cos(Math.PI / 18 * bearing) * sideLength;
            double y = Math.sin(Math.PI / 18 * bearing) * sideLength;
            points.add(x);
            points.add(y);
            bearing += 6;
        }

        Polyline hexagon = new Polyline();
        hexagon.getPoints().addAll(points);
        hexagon.setFill(Color.BLANCHEDALMOND);
        hexagon.setStroke(Color.BLACK);
        hexagon.setStrokeWidth(0);
        hexagon.setLayoutX(xCoord);
        hexagon.setLayoutY(yCoord);

        return hexagon;

    }

    //method for making circles
    public void makeCircle(String text, int radius, double xCoord, double yCoord) {
        Circle circle = new Circle(radius);
        circle.setFill(Color.BLANCHEDALMOND);
        circle.setStroke(Color.BLACK);
        Text newText = new Text(text);
        newText.setFont(Font.font("Verdana", 12));
        StackPane stack = new StackPane();
        stack.getChildren().addAll(circle, newText);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        hexagons.getChildren().add(stack);

    }

    public void makeRoad(String text, int height, int width, double xCoord, double yCoord, int rotation) {
        Rectangle rect = new Rectangle();
        rect.setHeight(height);
        rect.setWidth(width);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);
        Text newText = new Text(text);
        newText.setFont(Font.font("Verdana", 12));
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rect, newText);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        stack.getTransforms().add(new Rotate(rotation));
        structures.getChildren().add(stack);
    }
    public void makeKnight(String text, double xCoord, double yCoord) {
        Ellipse ellp = new Ellipse();
        ellp.setCenterX(0);
        ellp.setCenterY(0);
        ellp.setRadiusX(10);
        ellp.setRadiusY(15);
        ellp.setFill(Color.WHITE);
        ellp.setStroke(Color.BLACK);

        Circle circle = new Circle(7.5);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);

        Text newText = new Text(text);
        newText.setFont(Font.font("Verdana", 12));
        StackPane stack = new StackPane();
        stack.getChildren().addAll(ellp, newText);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        circle.setCenterX(xCoord+10);
        circle.setCenterY(yCoord-5);


        structures.getChildren().add(stack);
        structures.getChildren().add(circle);
    }
}
