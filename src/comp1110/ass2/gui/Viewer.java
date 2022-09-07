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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

        Image ocean = new Image(new FileInputStream("C:\\Users\\hhean\\" +
                "IdeaProjects\\comp1110-ass2\\assets\\Ocean.png"));
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

        Image ore = new Image(new FileInputStream("C:\\Users\\hhean\\" +
                "IdeaProjects\\comp1110-ass2\\assets\\ORE.jpg"));
        Polyline oreHexagon = makeHexagon(125, 740, 285);
        oreHexagon.setFill(new ImagePattern(ore, 0, 0, 1, 1, true));
        hexagons.getChildren().add(oreHexagon);

        Image grain = new Image(new FileInputStream("C:\\Users\\hhean\\" +
                "IdeaProjects\\comp1110-ass2\\assets\\GRAIN.png"));
        Polyline grainHexagon = makeHexagon(125, 740, 440);
        grainHexagon.setFill(new ImagePattern(grain, 0, 0, 1, 1, true));
        hexagons.getChildren().add(grainHexagon);

        Image wool = new Image(new FileInputStream("C:\\Users\\hhean\\" +
                "IdeaProjects\\comp1110-ass2\\assets\\WOOL.jpg"));
        Polyline woolHexagon = makeHexagon(125, 875, 518);
        woolHexagon.setFill(new ImagePattern(wool, 0, 0, 1, 1, true));
        hexagons.getChildren().add(woolHexagon);

        Image timber = new Image(new FileInputStream("C:\\Users\\hhean\\" +
                "IdeaProjects\\comp1110-ass2\\assets\\TIMBER.png"));
        Polyline timberHexagon = makeHexagon(125, 1010, 440);
        timberHexagon.setFill(new ImagePattern(timber, 0, 0, 1, 1, true));
        hexagons.getChildren().add(timberHexagon);

        Image brick = new Image(new FileInputStream("C:\\Users\\hhean\\" +
                "IdeaProjects\\comp1110-ass2\\assets\\BRICK.png"));
        Polyline brickHexagon = makeHexagon(125, 1010, 285);
        brickHexagon.setFill(new ImagePattern(brick, 0, 0, 1, 1, true));
        hexagons.getChildren().add(brickHexagon);

        Image mystery = new Image(new FileInputStream("C:\\Users\\hhean\\" +
                "IdeaProjects\\comp1110-ass2\\assets\\MYSTERY.jpg"));
        Polyline mysteryHexagon = makeHexagon(125, 875, 207);
        mysteryHexagon.setFill(new ImagePattern(mystery, 0, 0, 1, 1, true));
        hexagons.getChildren().add(mysteryHexagon);


        //Small circles with resource

        Circle oreCircle = new Circle(30);
        oreCircle.setFill(Color.BLANCHEDALMOND);
        oreCircle.setStroke(Color.BLACK);
        Text oreText = new Text("ORE");
        oreText.setFont(Font.font ("Verdana", 12));
        StackPane oreStack = new StackPane();
        oreStack.getChildren().addAll(oreCircle, oreText);
        oreStack.setLayoutX(710);
        oreStack.setLayoutY(255);
        hexagons.getChildren().add(oreStack);

        Circle grainCircle = new Circle(30);
        grainCircle.setFill(Color.BLANCHEDALMOND);
        grainCircle.setStroke(Color.BLACK);
        Text grainText = new Text("GRAIN");
        grainText.setFont(Font.font ("Verdana", 12));
        StackPane grainStack = new StackPane();
        grainStack.getChildren().addAll(oreCircle, Text);
        grainStack.setLayoutX(710);
        grainStack.setLayoutY(255);
        hexagons.getChildren().add(oreStack);






        //Roads


        //Towns


        //Settlements


        //Resource key


        //Scoreboard


        //Titles

        root.getChildren().add(controls);
        root.getChildren().add(oceanView);
        root.getChildren().add(hexagons);


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
}