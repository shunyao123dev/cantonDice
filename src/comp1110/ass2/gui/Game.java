package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Game extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private static final int HEX_HEIGHT = 200;

    private static final String testBoardState=
            "R0,R1,R2,R3,R4,R5,R6,R7,R8," +
                    "R9,R10,R11,R12,R13,R14,R15,S3," +
                    "S4,S5,S7,S9,S11,C7,C12,C20," +
                    "C30,J1,J2,J3,J4,J5,J6,K1,K2,K3,K4,K5,K6";

    private final Group root = new Group();
    private final Group objects = new Group();
    private final Group controls = new Group();

    private final Group menu = new Group();

    private final Group dieRoll = new Group();

    private final Group hexagons = new Group();

    private final Group startMenu = new Group();
    private final Group structures = new Group();

    private final Group rollCounter = new Group();

    private final Group instructions = new Group();


    private TextField playerTextField;
    private TextField boardTextField;

    private Group currentPlayersBoardDisplay = new Group();

    private Board currentPlayersBoard = new Board();

    private Board boardAfterMove = new Board();
    private ResourceState currentPlayersResourceState = new ResourceState();

    private Structure[] builtStructures = new Structure[33];

    private Structure[] currentStructures = new Structure[33];

    private int rollCount = 1;

    private int[] currentDie = new int[6];

    private final Group controlsForPlayerTurn = new Group();

    private int playerNumber = 0;

    private boolean gameStarted = false;

    private boolean gameOver = false;

    //Players

    private Player player1 = new Player("Player 1");
    private Player player2 = new Player("Player 2");
    private Player player3 = new Player("Player 3");
    private Player player4 = new Player("Player 4");
    private Player playerAI = new Player("AI");

    private Player currentPlayer = new Player("current");

    /**
     * Creates the launch menu
     */

    void launchStartMenu () {

        startMenu.getChildren().clear();
        controls.getChildren().clear();
        currentPlayersBoardDisplay.getChildren().clear();
        currentPlayersBoardDisplay.setVisible(false);

        //Creates the start menu

        MenuBar start = new MenuBar();

        Menu startBut = new Menu("Start");
        MenuItem onePlayer = new MenuItem("One player with AI");
        MenuItem twoPlayer = new MenuItem("Two players");
        MenuItem threePlayer = new MenuItem("Three players");
        MenuItem fourPlayer = new MenuItem("Four players");
        startBut.getItems().addAll(onePlayer, twoPlayer, threePlayer, fourPlayer);

        Menu endBut  = new Menu ("Exit");
        MenuItem close = new MenuItem("Close"); //Closes the app
        endBut.getItems().add(close);

        Font f = new Font("Verdana", 15);

        start.getMenus().addAll(startBut, endBut);

        VBox menu = new VBox();
        menu.getChildren().add(start);
        menu.setLayoutX(10);
        menu.setLayoutY(100);

        Rectangle textBox = new Rectangle();
        textBox.setHeight(40);
        textBox.setWidth(400);
        textBox.setX(10);
        textBox.setY(50);
        textBox.setFill(Color.WHITE);
        textBox.setStroke(Color.BLACK);
        Text text = new Text("Welcome to Catan! Select from the menu to start");
        text.setFont(Font.font("Verdana", 12));
        text.setX(15);
        text.setY(75);

        startMenu.getChildren().addAll(menu, textBox, text);

        onePlayer.setOnAction(new EventHandler<ActionEvent>() { //When the player selects an options the menu will disappear and start the game
            @Override
            public void handle(ActionEvent actionEvent) {
                launchControls();
                gameStarted = true;
                playerNumber = 1;
                currentPlayer = player1;
                menu.setVisible(false);
                text.setVisible(false);
            }
        });

        twoPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                launchControls();
                gameStarted = true;
                playerNumber = 2;
                currentPlayer = player1;
                menu.setVisible(false);
                text.setVisible(false);
            }
        });

        threePlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                launchControls();
                gameStarted = true;
                playerNumber = 3;
                currentPlayer = player1;
                menu.setVisible(false);
                text.setVisible(false);
            }
        });

        fourPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                launchControls();
                gameStarted = true;
                playerNumber = 4;
                currentPlayer = player1;
                menu.setVisible(false);
                text.setVisible(false);
            }
        });

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });


    }

    /**
     * Launches the control menu after the player has selected a game mode.
     */

    public void launchControls() {

        if (!gameOver) {

            //Reset game button

            Button resetGame = new Button("Reset game");
            HBox resetGameBox = new HBox();
            resetGameBox.getChildren().addAll(resetGame);
            resetGameBox.setLayoutX(10);
            resetGameBox.setLayoutY(12);

            resetGame.setOnAction(actionEvent -> {
                controls.getChildren().clear();
                rollCounter.getChildren().clear();
                dieRoll.getChildren().clear();
                currentPlayersBoardDisplay.getChildren().clear();
                currentPlayersResourceState = new ResourceState();
                currentDie = new int[6];
                builtStructures = new Structure[33];
                currentStructures = new Structure[33];
                playerNumber = 0;
                gameStarted = false;
                player1 = new Player("Player 1");
                player2 = new Player("Player 2");
                player3 = new Player("Player 3");
                player4 = new Player("Player 4");
                playerAI = new Player("AI");
                launchStartMenu();
            });

            //Launches the control menu

            Rectangle controlMenu = new Rectangle();
            controlMenu.setHeight(260);
            controlMenu.setWidth(150);
            controlMenu.setX(430);
            controlMenu.setY(140);
            controlMenu.setFill(Color.WHITE);
            controlMenu.setStroke(Color.BLACK);
            Rectangle controlHeader = new Rectangle();
            controlHeader.setHeight(30);
            controlHeader.setWidth(150);
            controlHeader.setX(430);
            controlHeader.setY(140);
            controlHeader.setFill(Color.LIGHTGRAY);
            controlHeader.setStroke(Color.BLACK);
            Text header = textBox("Controls");
            header.setY(159);
            header.setX(480);

            //Action bar

            boardTextField = new TextField();
            boardTextField.setPrefWidth(80);
            Button button = new Button("Enter");
            HBox hb = new HBox();
            hb.getChildren().addAll(boardTextField, button);
            hb.setSpacing(10);
            hb.setLayoutX(438);
            hb.setLayoutY(365);
            Text actionBarText = textBox("Action Bar");
            actionBarText.setX(475.5);
            actionBarText.setY(350);

            //Roll dice button and counter

            Button rollDice = new Button("Roll!");
            Rectangle rollCounterRect = new Rectangle();
            rollCounterRect.setHeight(75);
            rollCounterRect.setWidth(62.5);
            rollCounterRect.setFill(Color.WHITE);
            rollCounterRect.setStroke(Color.BLACK);
            rollCounterRect.setX(475);
            rollCounterRect.setY(243);
            HBox hb2 = new HBox();
            hb2.getChildren().addAll(rollDice);
            hb2.setLayoutX(485);
            hb2.setLayoutY(200);

            //Action bar action

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    String input = boardTextField.getText();
                    String pos = input.substring(Math.max(input.length() - 2, 0));
                    String boardString = boardToString(currentPlayersBoard);
                    currentStructures = currentPlayersBoard.structures;
                    Boolean moveComplete = false;

                    while (!moveComplete) {

                        if (CatanDice.isActionWellFormed(input)) {
                            if (CatanDice.checkResources(pos, currentDie)) {
                                if (CatanDice.checkBuildConstraints(pos, boardString)) {
                                    for (var structure : currentStructures) {
                                        if (input.equals(structure.getPosition())) {
                                            structure.setBuilt();
                                            moveComplete = true;
                                        }
                                    }

                                } else {
                                    instructions.getChildren().clear();
                                    instructions.getChildren().add(textBox("Insufficient prerequisites"));

                                }

                            } else {
                                instructions.getChildren().clear();
                                instructions.getChildren().add(textBox("Insufficient resources. Please select different move"));
                            }


                        } else {
                            instructions.getChildren().clear();
                            instructions.getChildren().add(textBox("Invalid input. Please type correctly"));
                        }


                    }
                }
            });

            //Roll dice action
            rollDice.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (rollCount < 4) {
                        if (rollCount == 1) {
                            int[] list = new int[]{0, 0, 0, 0, 0, 0};
                            MoveControls.rollDice(6, list);
                            Text rollCountText = textBox(String.valueOf(rollCount));
                            Font font = new Font("Verdana", 20);
                            rollCountText.setFont(font);
                            rollCountText.setX(500);
                            rollCountText.setY(287.5);
                            rollCounter.getChildren().add(rollCountText);
                            rollCount += 1;
                            try {
                                displayDice(list);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            rollCounter.getChildren().clear();
                            int[] list = new int[]{0, 0, 0, 0, 0, 0};
                            MoveControls.rollDice(6, list);
                            Text rollCountText = textBox(String.valueOf(rollCount));
                            Font font = new Font("Verdana", 20);
                            rollCountText.setFont(font);
                            rollCountText.setX(500);
                            rollCountText.setY(287.5);
                            rollCounter.getChildren().add(rollCountText);
                            rollCount += 1;
                            if (rollCount == 3) {
                                currentDie = list;
                            }
                            try {
                                displayDice(list);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            });

            //Change current board to current players board



            //display state of current player and prompt them to roll



            //get them to roll three times



            //change their current resource state and current die



            //prompt them to make a move



            //check if move is valid



            //update current displayed state



            //update scores



            //display scores



            //update turn count



            //update board state



            //change player to next player



            //check if game is over


            //recursively call launchControls


            controls.getChildren().addAll(controlMenu, controlHeader, header, hb, actionBarText, hb2, rollCounterRect, resetGameBox);


        } else { //the game is over


            //display final scores and winner




        }






    }

    /**
     * Creates the action bar where players will input strings of what they want to do
     */

    //needs to check if input valid, check if move valid (prerequisites, resources), change currentBoard




    /**
     * Starts game with one player and AI
     */
    public void gameOnePlayer() {

    }

    /**
     * Starts game with two players
     */

    public void gameTwoPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        Boolean gameOver = false;
        int playerTurn = 1; //initiates as player 1's turn

        if ((player1.getScores()).size() == 15 && (player2.getScores().size() == 15)) { //If both players have had 15 turns, game is over
            gameOver = true;
        }

//        public Player (String name){
//            this.name = name;
//            this.board = new Board();
//            this.scores = scores;
//            this.turnCount = turnCount;
//            this.resources = new ResourceState();
//        }

        while (!gameOver) {
            if (playerTurn == 1) {
                currentPlayersBoard = player1.getCurrentBoard();
                currentPlayersBoardDisplay.getChildren().clear();
                controlsForPlayerTurn.getChildren().clear();

                displayStateCurrent(boardToString(currentPlayersBoard)); //displays the board of the current player

                Text playerOnePlaying = textBox("Player 1's turn. Please roll");
                playerOnePlaying.setFont(Font.font("Verdana", 12));
                playerOnePlaying.setX(15);
                playerOnePlaying.setY(75);
                instructions.getChildren().add(playerOnePlaying);

                //the player has a turn. Need to roll dice, complete move

                rollDiceButton();

                int oreCount = 0;
                int grainCount = 0;
                int woolCount = 0;
                int timberCount = 0;
                int brickCount = 0;
                int goldCount = 0;


                for (var i : currentDie) {
                    if (i == 0) {
                        oreCount +=1;
                    } else if (i == 1) {
                        grainCount +=1;
                    } else if (i == 2) {
                        woolCount +=1;
                    } else if (i == 3) {
                        timberCount +=1;
                    } else if (i == 4) {
                        brickCount +=1;
                    } else {
                        goldCount +=1;
                    }
                }

                currentPlayersResourceState.changeResource(ResourceType.ORE, oreCount);
                currentPlayersResourceState.changeResource(ResourceType.GRAIN, grainCount);
                currentPlayersResourceState.changeResource(ResourceType.WOOL, woolCount);
                currentPlayersResourceState.changeResource(ResourceType.TIMBER, timberCount);
                currentPlayersResourceState.changeResource(ResourceType.BRICK, brickCount);
                currentPlayersResourceState.changeResource(ResourceType.GOLD, goldCount);

                //updates player 1 resource state

                player1.setResources(currentPlayersResourceState);

                instructions.getChildren().clear();
                Text playerOneMoving = textBox("Player 1 please input a move into the action bar");
                playerOneMoving.setFont(Font.font("Verdana", 12));
                playerOneMoving.setX(15);
                playerOneMoving.setY(75);
                instructions.getChildren().add(playerOnePlaying);

                actionBar();

                rollCount = 0;
                playerTurn -= 1;
                currentPlayersBoardDisplay.getChildren().clear();
            } else {
                currentPlayersBoard = player2.getCurrentBoard();
                currentPlayersBoardDisplay.getChildren().clear();
                controlsForPlayerTurn.getChildren().clear();

                displayStateCurrent(boardToString(currentPlayersBoard)); //displays the board of the current player



                rollCount = 0;
                playerTurn +=1;
                currentPlayersBoardDisplay.getChildren().clear();
            }


        }
//


    }

    /**
     * Starts game with three players
     */
    public void gameThreePlayer() {

    }
    /**
     * Starts game with four players
     */
    public void gameFourPlayer() {

    }

    public void actionBar() {

        boardTextField = new TextField();
        boardTextField.setPrefWidth(80);
        Button button = new Button("Enter");
        HBox hb = new HBox();
        hb.getChildren().addAll(boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(438);
        hb.setLayoutY(365);
        Text actionBarText = textBox("Action Bar");
        actionBarText.setX(475.5);
        actionBarText.setY(350);
        controlsForPlayerTurn.getChildren().addAll(hb, actionBarText);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String input = boardTextField.getText();
                String pos = input.substring(Math.max(input.length() - 2, 0));
                String boardString = boardToString(currentPlayersBoard);
                currentStructures = currentPlayersBoard.structures;
                Boolean moveComplete = false;

                while (!moveComplete) {

                    if (CatanDice.isActionWellFormed(input)) {
                        if (CatanDice.checkResources(pos, currentDie)) {
                            if (CatanDice.checkBuildConstraints(pos, boardString)) {
                                for (var structure : currentStructures) {
                                    if (input.equals(structure.getPosition())) {
                                        structure.setBuilt();
                                        moveComplete = true;
                                    }
                                }

                            } else {
                                instructions.getChildren().clear();
                                instructions.getChildren().add(textBox("Insufficient prerequisites"));

                            }

                        } else {
                            instructions.getChildren().clear();
                            instructions.getChildren().add(textBox("Insufficient resources. Please select different move"));
                        }


                    } else {
                        instructions.getChildren().clear();
                        instructions.getChildren().add(textBox("Invalid input. Please type correctly"));
                    }


                }
            }
        });

    }

    /**
     * Creates a button that rolls the dice.
     */
    private void rollDiceButton() {
        Button rollDice = new Button("Roll!");
        rollDice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (rollCount < 4) {
                    if (rollCount == 1) {
                        int[] list = new int[]{0, 0, 0, 0, 0, 0};
                        MoveControls.rollDice(6, list);
                        Text rollCountText = textBox(String.valueOf(rollCount));
                        Font font = new Font("Verdana", 20);
                        rollCountText.setFont(font);
                        rollCountText.setX(500);
                        rollCountText.setY(287.5);
                        rollCounter.getChildren().add(rollCountText);
                        rollCount += 1;
                        try {
                            displayDice(list);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        rollCounter.getChildren().clear();
                        int[] list = new int[]{0, 0, 0, 0, 0, 0};
                        MoveControls.rollDice(6, list);
                        Text rollCountText = textBox(String.valueOf(rollCount));
                        Font font = new Font("Verdana", 20);
                        rollCountText.setFont(font);
                        rollCountText.setX(500);
                        rollCountText.setY(287.5);
                        rollCounter.getChildren().add(rollCountText);
                        rollCount += 1;
                        if (rollCount == 3) {
                            currentDie = list;
                        }
                        try {
                            displayDice(list);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        Rectangle rollCounterRect = new Rectangle();
        rollCounterRect.setHeight(75);
        rollCounterRect.setWidth(62.5);
        rollCounterRect.setFill(Color.WHITE);
        rollCounterRect.setStroke(Color.BLACK);
        rollCounterRect.setX(475);
        rollCounterRect.setY(243);


        HBox hb2 = new HBox();
        hb2.getChildren().addAll(rollDice);
        hb2.setLayoutX(485);
        hb2.setLayoutY(200);
        controlsForPlayerTurn.getChildren().addAll(rollCounterRect, hb2);
    }

    //Things to do in start game

    //create player class for as many players

    //


    /**
     * Converts object orientated board into string.
     * @param board
     * @return
     */
    public String boardToString(Board board)  {
        String boardString = "";
        Structure[] currentStructures = board.getBoard();
        int count = 0;
        for (var structure : currentStructures) {
            if (count == 0  && structure.isBuilt()) {
                boardString = boardString + structure.getPosition();
                count += 1;
            } else if (structure.isBuilt()) {
                boardString = boardString + " " + structure.getPosition();
            }
        }

        return boardString;
    }

    //Need to get number of players and create player profiles for each of these
    //Player 1 to go first and display Player 1 board.


    //Players 1 goes (need to display who's turn it is)
    // Needs to roll. Need to change and display resource state each time.
    // Player must be able to select dice to put aside and roll up to three times.
    // After rolling then build, trade or swap. Need to check if action,
    // can be made. Need to then change board and update viewer.

    //Display next player. Repeat.

    //Once turn count is equal to 15, game ends for player.

    //Add players scores up and depict winner.




/** ----------------------------------VIEWER----------------------------------------------------------------------------/


    /**
     * Show the state of a (single player's) board in the window.
     * If any structure (road, settlement or town) is built, the
     * structure will appear grey. If a Knight is built but unused,
     * the figure will appear green. If a Knight has been built and
     * used, the figure will be black.
     * @param: The string representation of the board state.
     */
    void displayState(String board_state) {
        // FIXME Task 5: implement the state viewer
        String[] boardArr = board_state.split(",");

        for (var str : boardArr) {
            if (str.equals("R0")) {
                buildRoad(805, 300, 30);
            } else if (str.equals("R1")) {
                buildRoad(720,370,-90);
            } else if (str.equals("R2")) {
                buildRoad(778,382,-30);
            } else if (str.equals("R3")) {
                buildRoad(815,450,30);
            } else if (str.equals("R4")) {
                buildRoad(720,518,-90);
            } else if (str.equals("R5")) {
                buildRoad(790,530,-30);
            } else if (str.equals("R6")) {
                buildRoad(853,596,-90);
            } else if (str.equals("R7")) {
                buildRoad(937,535,30);
            } else if (str.equals("R8")) {
                buildRoad(922,458,-30);
            } else if (str.equals("R9")) {
                buildRoad(953,380,30);
            } else if (str.equals("R10")) {
                buildRoad(928,300,-30);
            } else if (str.equals("R11")) {
                buildRoad(945,225,30);
            } else if (str.equals("R12")) {
                buildRoad(989,518,-90);
            } else if (str.equals("R13")) {
                buildRoad(1070,460,30);
            } else if (str.equals("R14")) {
                buildRoad(1044,375,-30);
            } else if (str.equals("R15")) {
                buildRoad(1073,300,30);
            } else if (str.equals("S3")) {
                buildSettlement(825,290);
            } else if (str.equals("S4")) {
                buildSettlement(818,425);
            } else if (str.equals("S5")) {
                buildSettlement(820,580);
            } else if (str.equals("S7")) {
                buildSettlement(955,510);
            } else if (str.equals("S9")) {
                buildSettlement(960,355);
            } else if (str.equals("S11")) {
                buildSettlement(960,200);
            } else if (str.equals("C7")) {
                buildTown(670,340);
            } else if (str.equals("C12")) {
                buildTown(670,490);
            } else if (str.equals("C20")) {
                buildTown(1075,420);
            } else if (str.equals("C30")) {
                buildTown(1075,260);
            } else if (str.equals("J1")) {
                buildKnight(731,240);
            } else if (str.equals("J2")) {
                buildKnight(731,395);
            } else if (str.equals("J3")) {
                buildKnight(866,475);
            } else if (str.equals("J4")) {
                buildKnight(1003,395);
            } else if (str.equals("J5")) {
                buildKnight(1003,240);
            } else if (str.equals("J6")) {
                buildKnight(866,160);
            } else if (str.equals("K1")) {
                useKnight(731,240);
            } else if (str.equals("K2")) {
                useKnight(731,395);
            } else if (str.equals("K3")) {
                useKnight(866,475);
            } else if (str.equals("K4")) {
                useKnight(1003,395);
            } else if (str.equals("K5")) {
                useKnight(1003,240);
            } else { //K6
                useKnight(866,160);
            }

        }

    }

    void displayStateCurrent(String board_state) {
        String[] boardArr = board_state.split(",");

        for (var str : boardArr) {
            if (str.equals("R0")) {
                buildRoadCurrent(805, 300, 30);
            } else if (str.equals("R1")) {
                buildRoadCurrent(720,370,-90);
            } else if (str.equals("R2")) {
                buildRoadCurrent(778,382,-30);
            } else if (str.equals("R3")) {
                buildRoadCurrent(815,450,30);
            } else if (str.equals("R4")) {
                buildRoadCurrent(720,518,-90);
            } else if (str.equals("R5")) {
                buildRoadCurrent(790,530,-30);
            } else if (str.equals("R6")) {
                buildRoadCurrent(853,596,-90);
            } else if (str.equals("R7")) {
                buildRoadCurrent(937,535,30);
            } else if (str.equals("R8")) {
                buildRoadCurrent(922,458,-30);
            } else if (str.equals("R9")) {
                buildRoadCurrent(953,380,30);
            } else if (str.equals("R10")) {
                buildRoadCurrent(928,300,-30);
            } else if (str.equals("R11")) {
                buildRoadCurrent(945,225,30);
            } else if (str.equals("R12")) {
                buildRoadCurrent(989,518,-90);
            } else if (str.equals("R13")) {
                buildRoadCurrent(1070,460,30);
            } else if (str.equals("R14")) {
                buildRoadCurrent(1044,375,-30);
            } else if (str.equals("R15")) {
                buildRoadCurrent(1073,300,30);
            } else if (str.equals("S3")) {
                buildSettlementCurrent(825,290);
            } else if (str.equals("S4")) {
                buildSettlementCurrent(818,425);
            } else if (str.equals("S5")) {
                buildSettlementCurrent(820,580);
            } else if (str.equals("S7")) {
                buildSettlementCurrent(955,510);
            } else if (str.equals("S9")) {
                buildSettlementCurrent(960,355);
            } else if (str.equals("S11")) {
                buildSettlementCurrent(960,200);
            } else if (str.equals("C7")) {
                buildTownCurrent(670,340);
            } else if (str.equals("C12")) {
                buildTownCurrent(670,490);
            } else if (str.equals("C20")) {
                buildTownCurrent(1075,420);
            } else if (str.equals("C30")) {
                buildTownCurrent(1075,260);
            } else if (str.equals("J1")) {
                buildKnightCurrent(731,240);
            } else if (str.equals("J2")) {
                buildKnightCurrent(731,395);
            } else if (str.equals("J3")) {
                buildKnightCurrent(866,475);
            } else if (str.equals("J4")) {
                buildKnightCurrent(1003,395);
            } else if (str.equals("J5")) {
                buildKnightCurrent(1003,240);
            } else if (str.equals("J6")) {
                buildKnightCurrent(866,160);
            } else if (str.equals("K1")) {
                useKnightCurrent(731,240);
            } else if (str.equals("K2")) {
                useKnightCurrent(731,395);
            } else if (str.equals("K3")) {
                useKnightCurrent(866,475);
            } else if (str.equals("K4")) {
                useKnightCurrent(1003,395);
            } else if (str.equals("K5")) {
                useKnightCurrent(1003,240);
            } else { //K6
                useKnightCurrent(866,160);
            }

        }

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
        hb.setLayoutX(5);
        hb.setLayoutY(10);
        controls.getChildren().add(hb);


    }

    /**
     * Creates the base board for Catan Island 1
     * @throws FileNotFoundException
     */

    private void makeBaseBoard() throws FileNotFoundException {

        //Setting ocean image background

        Image ocean = new Image(new FileInputStream("assets/Ocean.png"));
        ImageView oceanView = new ImageView(ocean);
        oceanView.setX(600);
        oceanView.setY(85);
        oceanView.setFitHeight(1200);
        oceanView.setFitWidth(550);
        oceanView.setPreserveRatio(true);
        objects.getChildren().add(oceanView);

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

        makeStartRoad("S", 45, 15, 795,240, -30); //R0
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

        //Settlements

        makeSettlement("3", 825,290);
        makeSettlement("4", 818,425);
        makeSettlement("5", 820,580);
        makeSettlement("7", 955,510);
        makeSettlement("9", 960,355);
        makeSettlement("11", 960,200);

        //Towns

        makeTown("7", 670,340, 0);
        makeTown("12", 670,490,3);
        makeTown("20", 1075, 420, 3);
        makeTown("30", 1075, 260, 3);

        //Resource key and Scoreboard

        Image key = new Image(new FileInputStream("assets/KEY.png"));
        ImageView keyView = new ImageView(key);
        keyView.setX(25);
        keyView.setY(425);
        keyView.setFitHeight(1200);
        keyView.setFitWidth(550);
        keyView.setPreserveRatio(true);
        objects.getChildren().add(keyView);

        //Titles

        Image title = new Image(new FileInputStream("assets/TITLE.jpg"));
        ImageView titleView = new ImageView(title);
        titleView.setX(725);
        titleView.setY(15);
        titleView.setFitHeight(100);
        titleView.setFitWidth(300);
        titleView.setPreserveRatio(true);
        objects.getChildren().add(titleView);

        //Nodes

        root.getChildren().add(controls); //adds control bar
        root.getChildren().add(currentPlayersBoardDisplay); //adds current players board
        root.getChildren().add(startMenu); // adds start menu
        root.getChildren().add(objects); //adds resource key, scoreboard, title and ocean
        root.getChildren().add(hexagons); //adds all hexagons
        root.getChildren().add(structures); //adds all structures to the board
        root.getChildren().add(dieRoll); // adds dice to the board
        root.getChildren().add(rollCounter);
        root.getChildren().add(instructions);
        root.getChildren().add(controlsForPlayerTurn);

    }

    /**
     * Creates hexagons. Has adapted the hexagon code from Assignment 1.
     * @param length: The length of each side
     * @param xCoord: The x-coordinate of the hexagon
     * @param yCoord: The y-coordinate of the hexagon
     * @return: Returns a hexagon of type Polyline
     */
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

    /**
     * Creates circles
     * @param text: The number to be displayed in the circle
     * @param radius: The radius of the circle
     * @param xCoord: The x-coordinate of the circle
     * @param yCoord: The y-coordinate of the circle
     */
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

    /**
     * Creates the road shape
     * @param text: The number to be displayed on the road
     * @param height: The height of the road
     * @param width: The width of the road
     * @param xCoord: The x-coordinate of the road
     * @param yCoord: The y-coordinate of the road
     * @param rotation: The rotation of the road in degrees
     */

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

    /**
     * Creates the start road
     * @param height: The height of the road
     * @param width: The width of the road
     * @param xCoord: The x-coordinate of the road
     * @param yCoord: The y-coordinate of the road
     * @param rotation: The rotation of the road in degrees
     */
    public void makeStartRoad(String text, int height, int width, double xCoord, double yCoord, int rotation) {
        Rectangle rect = new Rectangle();
        rect.setHeight(height);
        rect.setWidth(width);
        rect.setFill(Color.PURPLE);
        rect.setStroke(Color.BLACK);
        Text newText = new Text(text);
        newText.setFont(Font.font("Verdana", 12));
        newText.setFill(Color.WHITE);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rect, newText);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        stack.getTransforms().add(new Rotate(rotation));
        structures.getChildren().add(stack);
    }

    /**
     * Creates the knight shape
     * @param text: The number to be displayed on the knight
     * @param xCoord: The x-coordinate of the knight
     * @param yCoord: The y-coordinate of the knight
     */

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

    /**
     * Creates the town structure
     * @param text: The number to be displayed on the town
     * @param xCoord: The x-coordinate of the town
     * @param yCoord: The y-coordinate of the town
     */

    public void makeSettlement(String text, double xCoord, double yCoord) {
        Rectangle rect = new Rectangle();
        rect.setX(0);
        rect.setY(0);
        rect.setHeight(20);
        rect.setWidth(20);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);

        Polygon tri = new Polygon(15, 0, 30, 15, 0, 15);
        tri.setFill(Color.WHITE);
        tri.setStroke(Color.BLACK);

        Text newText = new Text(text);
        newText.setFont(Font.font("Verdana", 12));
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rect, newText);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        tri.setLayoutX(xCoord-4);
        tri.setLayoutY(yCoord-13);

        structures.getChildren().add(stack);
        structures.getChildren().add(tri);
    }

    /**
     * Creates the settlement structure
     * @param text: The number to be displayed on the settlement
     * @param xCoord: The x-coordinate of the settlement
     * @param yCoord: The y-coordinate of the settlement
     * @param textShift: The text shift for the number
     */

    public void makeTown(String text, double xCoord, double yCoord, int textShift) {
        Polygon base = new Polygon(0, 10, 20, 10,20, 0, 40, 0, 40, 30, 0, 30);
        base.setFill(Color.WHITE);
        base.setStroke(Color.BLACK);

        Polygon tri = new Polygon(15, 0, 30, 15, 0, 15);
        tri.setFill(Color.WHITE);
        tri.setStroke(Color.BLACK);

        Text newText = new Text(text);
        newText.setFont(Font.font("Verdana", 12));
        StackPane stack = new StackPane();
        stack.getChildren().addAll(base);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        newText.setX(xCoord+26-textShift);
        newText.setY(yCoord+22);
        tri.setLayoutX(xCoord + 16);
        tri.setLayoutY(yCoord - 14);

        structures.getChildren().add(stack);
        structures.getChildren().add(newText);
        structures.getChildren().add(tri);
    }

    /**
     * Adds a built road to the board
     * @param xCoord: The x-coordinate of the used road
     * @param yCoord: The y-coordinate of the used road
     * @param rotation: The rotation of the used road
     */

    public void buildRoad(double xCoord, double yCoord, int rotation) {
        Rectangle rect = new Rectangle();
        rect.setHeight(45);
        rect.setWidth(15);
        rect.setFill(Color.BLACK);
        rect.setStroke(Color.BLACK);
        rect.setOpacity(0.5);
        StackPane stack = new StackPane();
        stack.getChildren().add(rect);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        stack.getTransforms().add(new Rotate(rotation));
        structures.getChildren().add(stack);
    }

    public void buildRoadCurrent(double xCoord, double yCoord, int rotation) {
        Rectangle rect = new Rectangle();
        rect.setHeight(45);
        rect.setWidth(15);
        rect.setFill(Color.BLACK);
        rect.setStroke(Color.BLACK);
        rect.setOpacity(0.5);
        StackPane stack = new StackPane();
        stack.getChildren().add(rect);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        stack.getTransforms().add(new Rotate(rotation));
        currentPlayersBoardDisplay.getChildren().add(stack);
    }


    /**
     * Adds a built settlement to the board
     * @param xCoord: The x-coordinate of the settlement
     * @param yCoord: The y-coordinate of the settlement
     */

    public void buildSettlement(double xCoord, double yCoord) {
        Rectangle rect = new Rectangle();
        rect.setX(0);
        rect.setY(0);
        rect.setHeight(20);
        rect.setWidth(20);
        rect.setFill(Color.BLACK);
        rect.setStroke(Color.BLACK);
        rect.setOpacity(0.5);

        Polygon tri = new Polygon(15, 0, 30, 15, 0, 15);
        tri.setFill(Color.BLACK);
        tri.setStroke(Color.BLACK);
        tri.setOpacity(0.5);

        StackPane stack = new StackPane();
        stack.getChildren().add(rect);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        tri.setLayoutX(xCoord - 4);
        tri.setLayoutY(yCoord - 13);

        structures.getChildren().add(stack);
        structures.getChildren().add(tri);
    }

    public void buildSettlementCurrent(double xCoord, double yCoord) {
        Rectangle rect = new Rectangle();
        rect.setX(0);
        rect.setY(0);
        rect.setHeight(20);
        rect.setWidth(20);
        rect.setFill(Color.BLACK);
        rect.setStroke(Color.BLACK);
        rect.setOpacity(0.5);

        Polygon tri = new Polygon(15, 0, 30, 15, 0, 15);
        tri.setFill(Color.BLACK);
        tri.setStroke(Color.BLACK);
        tri.setOpacity(0.5);

        StackPane stack = new StackPane();
        stack.getChildren().add(rect);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        tri.setLayoutX(xCoord - 4);
        tri.setLayoutY(yCoord - 13);

        currentPlayersBoardDisplay.getChildren().add(stack);
        currentPlayersBoardDisplay.getChildren().add(tri);
    }

    /**
     * Adds a built town to the board
     * @param xCoord: The x-coordinate of the town
     * @param yCoord: The y-coordinate of the town
     */

    public void buildTown(double xCoord, double yCoord) {
        Polygon base = new Polygon(0, 10, 20, 10, 20, 0, 40, 0, 40, 30, 0, 30);
        base.setFill(Color.BLACK);
        base.setStroke(Color.BLACK);
        base.setOpacity(0.5);

        Polygon tri = new Polygon(15, 0, 30, 15, 0, 15);
        tri.setFill(Color.BLACK);
        tri.setStroke(Color.BLACK);
        tri.setOpacity(0.5);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(base);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        tri.setLayoutX(xCoord + 16);
        tri.setLayoutY(yCoord - 14);

        structures.getChildren().add(stack);
        structures.getChildren().add(tri);
    }

    public void buildTownCurrent(double xCoord, double yCoord) {
        Polygon base = new Polygon(0, 10, 20, 10, 20, 0, 40, 0, 40, 30, 0, 30);
        base.setFill(Color.BLACK);
        base.setStroke(Color.BLACK);
        base.setOpacity(0.5);

        Polygon tri = new Polygon(15, 0, 30, 15, 0, 15);
        tri.setFill(Color.BLACK);
        tri.setStroke(Color.BLACK);
        tri.setOpacity(0.5);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(base);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        tri.setLayoutX(xCoord + 16);
        tri.setLayoutY(yCoord - 14);

        currentPlayersBoardDisplay.getChildren().add(stack);
        currentPlayersBoardDisplay.getChildren().add(tri);
    }

    /**
     * Adds a built knight to the board
     * @param xCoord: The x-coordinate of the built knight
     * @param yCoord: The y-coordinate of the built knight
     */

    public void buildKnight (double xCoord, double yCoord) {
        Ellipse ellp = new Ellipse();
        ellp.setCenterX(0);
        ellp.setCenterY(0);
        ellp.setRadiusX(10);
        ellp.setRadiusY(15);
        ellp.setFill(Color.LIGHTGREEN);
        ellp.setStroke(Color.BLACK);
        ellp.setOpacity(0.5);

        Circle circle = new Circle(7.5);
        circle.setFill(Color.LIGHTGREEN);
        circle.setStroke(Color.BLACK);
        circle.setOpacity(0.5);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(ellp);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        circle.setCenterX(xCoord + 10);
        circle.setCenterY(yCoord - 5);

        structures.getChildren().add(stack);
        structures.getChildren().add(circle);
    }

    public void buildKnightCurrent (double xCoord, double yCoord) {
        Ellipse ellp = new Ellipse();
        ellp.setCenterX(0);
        ellp.setCenterY(0);
        ellp.setRadiusX(10);
        ellp.setRadiusY(15);
        ellp.setFill(Color.LIGHTGREEN);
        ellp.setStroke(Color.BLACK);
        ellp.setOpacity(0.5);

        Circle circle = new Circle(7.5);
        circle.setFill(Color.LIGHTGREEN);
        circle.setStroke(Color.BLACK);
        circle.setOpacity(0.5);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(ellp);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        circle.setCenterX(xCoord + 10);
        circle.setCenterY(yCoord - 5);

        currentPlayersBoardDisplay.getChildren().add(stack);
        currentPlayersBoardDisplay.getChildren().add(circle);
    }

    /**
     * Adds a used knight to the board
     * @param xCoord: The x-coordinate of the used knight
     * @param yCoord: The y-coordinate of the used knight
     */

    public void useKnight (double xCoord, double yCoord) {
        Ellipse ellp = new Ellipse();
        ellp.setCenterX(0);
        ellp.setCenterY(0);
        ellp.setRadiusX(10);
        ellp.setRadiusY(15);
        ellp.setFill(Color.BLACK);
        ellp.setStroke(Color.BLACK);

        Circle circle = new Circle(7.5);
        circle.setFill(Color.BLACK);
        circle.setStroke(Color.BLACK);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(ellp);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        circle.setCenterX(xCoord + 10);
        circle.setCenterY(yCoord - 5);

        structures.getChildren().add(stack);
        structures.getChildren().add(circle);
    }

    public void useKnightCurrent (double xCoord, double yCoord) {
        Ellipse ellp = new Ellipse();
        ellp.setCenterX(0);
        ellp.setCenterY(0);
        ellp.setRadiusX(10);
        ellp.setRadiusY(15);
        ellp.setFill(Color.BLACK);
        ellp.setStroke(Color.BLACK);

        Circle circle = new Circle(7.5);
        circle.setFill(Color.BLACK);
        circle.setStroke(Color.BLACK);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(ellp);
        stack.setLayoutX(xCoord);
        stack.setLayoutY(yCoord);
        circle.setCenterX(xCoord + 10);
        circle.setCenterY(yCoord - 5);

        currentPlayersBoardDisplay.getChildren().add(stack);
        currentPlayersBoardDisplay.getChildren().add(circle);
    }


    /**
     * Generates the dice images and displays them
     * @param dice
     * @throws FileNotFoundException
     */

    public void displayDice(int[] dice) throws FileNotFoundException{

        dieRoll.getChildren().clear();

        Image oreDice = new Image(new FileInputStream("assets/Ore_dice.png"));
        Image grainDice = new Image(new FileInputStream("assets/Wheat_dice.png"));
        Image woolDice = new Image(new FileInputStream("assets/Wool_dice.png"));
        Image timberDice = new Image(new FileInputStream("assets/Timber_dice.png"));
        Image brickDice = new Image(new FileInputStream("assets/Brick_dice.png"));
        Image goldDice = new Image(new FileInputStream("assets/Gold_dice.png"));

        ArrayList<Image> diceImages = new ArrayList<>();

        for (int die : dice) {
            Image currentDie = new WritableImage(500,500);
            if (die == 0) {
                currentDie = oreDice;
            } else if (die == 1) {
                currentDie = grainDice;
            } else if (die == 2) {
                currentDie = woolDice;
            } else if (die == 3) {
                currentDie = timberDice;
            } else if (die == 4) {
                currentDie = brickDice;
            } else if (die == 5) {
                currentDie = goldDice;
            }
            diceImages.add(currentDie);
        }

        ImageView dice1 = new ImageView(diceImages.get(0));
        dice1.setFitHeight(100);
        dice1.setFitWidth(100);
        dice1.setX(50);
        dice1.setY(175);

        ImageView dice2 = new ImageView(diceImages.get(1));
        dice2.setFitHeight(100);
        dice2.setFitWidth(100);
        dice2.setX(175);
        dice2.setY(175);

        ImageView dice3 = new ImageView(diceImages.get(2));
        dice3.setFitHeight(100);
        dice3.setFitWidth(100);
        dice3.setX(300);
        dice3.setY(175);

        ImageView dice4 = new ImageView(diceImages.get(3));
        dice4.setFitHeight(100);
        dice4.setFitWidth(100);
        dice4.setX(50);
        dice4.setY(300);

        ImageView dice5 = new ImageView(diceImages.get(4));
        dice5.setFitHeight(100);
        dice5.setFitWidth(100);
        dice5.setX(175);
        dice5.setY(300);

        ImageView dice6 = new ImageView(diceImages.get(5));
        dice6.setFitHeight(100);
        dice6.setFitWidth(100);
        dice6.setX(300);
        dice6.setY(300);

        dieRoll.getChildren().addAll(dice1, dice2, dice3, dice4, dice5, dice6);


    }

    /**
     * Assumes move is valid
     * @param string
     * @param board
     * @param
     * @return
     */

    public Board stringMoveToBoard(String string, Board board) {
        Character firstLetter = string.charAt(0);
        String pos = string.substring(Math.max(string.length() - 2, 0));
        if (firstLetter.equals('b')) { //check if enough resources, check if prerequisites
            for (var structure : board.structures) {
                if (structure.getPosition().equals(pos) && !structure.isBuilt()){
                    structure.setBuilt();
                }
            }
        } else if (firstLetter.equals('t')) {


        } else {

        }

        return board;

    }


    /**
     * Generates a text box given a string
     * @param string
     * @return
     */
    public Text textBox(String string) {
        Rectangle textBox = new Rectangle();
        textBox.setHeight(40);
        textBox.setWidth(400);
        textBox.setX(10);
        textBox.setY(50);
        textBox.setFill(Color.WHITE);
        textBox.setStroke(Color.BLACK);
        Text text = new Text(string);
        text.setFont(Font.font("Verdana", 12));
        text.setX(15);
        text.setY(75);
        return text;
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Board State Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT, Color.GREEN);

        makeBaseBoard();
        launchStartMenu();

        stage.setScene(scene);
        stage.show();
        //Task 10 here
    }


}

