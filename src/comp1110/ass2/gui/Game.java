package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import comp1110.ass2.gui.Viewer;

public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    /**
     * Might prompts some user
     *
     */

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Board State Viewer");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.GREEN);

        //try start viewer in the game file
        //initialise game in the game file

        stage.setScene(scene);
        stage.show();
        //Task 10 here
    }
}

