package comp1110.ass2.gui;

import comp1110.ass2.*;
import comp1110.ass2.AI;
import java.util.List;
import java.util.Map;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends Application {
    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;
    private final Group root = new Group();
    private final Group objects = new Group();
    private final Group controls = new Group();
    private final Group dieRoll = new Group();
    private final Group hexagons = new Group();
    private final Group startMenu = new Group();
    private final Group structures = new Group();
    private final Group rollCounter = new Group();
    private final Group instructions = new Group();
    private TextField boardTextField;
    private final Group currentPlayerDisplay = new Group();
    private ResourceState currentPlayersResourceState = new ResourceState();
    private Structure[] currentStructures = new Structure[32];
    private int rollCount = 1;
    private int[] currentDie = new int[6];
    private final Group redDie = new Group();
    private int[] dieSelected = new int[]{0,0,0,0,0,0};
    private int playerNumber = 0;
    private int playerTurn = 0;
    private boolean gameOver = false;
    private boolean somethingBuilt = false;
    private boolean gameJustStarted = true;
    private boolean AIGame = false;

    //Die
    private final Rectangle die1 = new Rectangle();
    private final Rectangle die2 = new Rectangle();
    private final Rectangle die3 = new Rectangle();
    private final Rectangle die4 = new Rectangle();
    private final Rectangle die5 = new Rectangle();
    private final Rectangle die6 = new Rectangle();

    //Players
    private final Player player1 = new Player("Player 1");
    private final Player player2 = new Player("Player 2");
    private final Player player3 = new Player("Player 3");
    private final Player player4 = new Player("Player 4");
    private final Player playerAI = new Player("AI");
    private final Player currentPlayer = new Player("current");


    /*
      Author of class: Hugo Heanly u7119555
     */


    /**
     * Creates the start menu for Catan
     * Author: Hugo Heanly u7119555
     */
    void launchStartMenu () {

        startMenu.getChildren().clear();
        controls.getChildren().clear();

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

        start.getMenus().addAll(startBut, endBut);

        VBox menu = new VBox();
        menu.getChildren().add(start);
        menu.setLayoutX(10);
        menu.setLayoutY(150);

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

        Rectangle intro = new Rectangle();
        intro.setHeight(40);
        intro.setWidth(400);
        intro.setX(10);
        intro.setY(100);
        intro.setFill(Color.WHITE);
        intro.setStroke(Color.BLACK);
        Text introText = new Text("Note: For action bar, enter actions as per the read me");
        introText.setFont(Font.font("Verdana", 12));
        introText.setX(15);
        introText.setY(125);

        startMenu.getChildren().addAll(menu, textBox, text, intro, introText);

        //When the player selects an options the menu will disappear and start the game

        onePlayer.setOnAction(actionEvent -> {
            // AI not ready yet.

            playerNumber = 1;
            playerTurn = 1;
            menu.setVisible(false);
            text.setVisible(false);
            introText.setVisible(false);
            intro.setVisible(false);
            AIGame = true;
            try {
                launchControls();
            } catch (FileNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        twoPlayer.setOnAction(actionEvent -> {
            playerNumber = 2;
            playerTurn = 1;
            menu.setVisible(false);
            text.setVisible(false);
            introText.setVisible(false);
            intro.setVisible(false);
            try {
                launchControls();
            } catch (FileNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        threePlayer.setOnAction(actionEvent -> {
            playerNumber = 3;
            playerTurn = 1;
            menu.setVisible(false);
            text.setVisible(false);
            introText.setVisible(false);
            intro.setVisible(false);
            try {
                launchControls();
            } catch (FileNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        fourPlayer.setOnAction(actionEvent -> {
            playerNumber = 4;
            playerTurn = 1;
            menu.setVisible(false);
            text.setVisible(false);
            introText.setVisible(false);
            intro.setVisible(false);
            try {
                launchControls();
            } catch (FileNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        close.setOnAction(actionEvent -> Platform.exit());


    }

    /**
     * Launches the control menu after the player has selected a game mode.
     * Author: Hugo Heanly u7119555
     */

    public void launchControls() throws FileNotFoundException, InterruptedException {

        controls.getChildren().clear();
        currentPlayerDisplay.getChildren().clear();
        redDie.getChildren().clear();
        dieRoll.getChildren().clear();
        instructions.getChildren().clear();
        rollCounter.getChildren().clear();

        //If the game is not over, continue to play

        if (!gameOver) {

            if (playerTurn == 1) {
                currentPlayer.copyFrom(player1);
            } else if (playerTurn == 2) {
                if (AIGame) {
                    currentPlayer.copyFrom(playerAI);
                } else {
                    currentPlayer.copyFrom(player2);
                }
            } else if (playerTurn == 3) {
                currentPlayer.copyFrom(player3);
            } else if (playerTurn == 4) {
                currentPlayer.copyFrom(player4);
            }

            //Reset game button

            Button resetGame = new Button("Reset game");
            HBox resetGameBox = new HBox();
            resetGameBox.getChildren().addAll(resetGame);
            resetGameBox.setLayoutX(10);
            resetGameBox.setLayoutY(12);

            resetGame.setOnAction(actionEvent -> {
                controls.getChildren().clear();
                rollCounter.getChildren().clear();
                rollCount = 1;
                dieSelected = new int[]{0,0,0,0,0,0};
                dieRoll.getChildren().clear();
                redDie.getChildren().clear();
                instructions.getChildren().clear();
                currentPlayerDisplay.getChildren().clear();
                currentPlayersResourceState = new ResourceState();
                currentDie = new int[6];
                currentStructures = new Structure[33];
                playerNumber = 0;
                AIGame = false;
                player1.resetPlayer("Player 1");
                player2.resetPlayer("Player 2");
                player3.resetPlayer("Player 3");
                player4.resetPlayer("Player 4");
                playerAI.resetPlayer("Player AI");
                currentPlayer.resetPlayer("current");
                try {
                    makeDieVisible(die1);
                    makeDieVisible(die2);
                    makeDieVisible(die3);
                    makeDieVisible(die4);
                    makeDieVisible(die5);
                    makeDieVisible(die6);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                launchStartMenu();
            });

            //Creates the control menu

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

            //End turn button

            Button endTurn = new Button("End turn");
            HBox hb3 = new HBox();
            hb3.getChildren().addAll(endTurn);
            hb3.setLayoutX(475);
            hb3.setLayoutY(100);
            hb3.toFront();

            //Set die as empty to start

            makeDie(die1, 1);
            makeDieTransparent(die1);
            final boolean[] selectedAtRoll1 = {false};
            makeDie(die2, 2);
            makeDieTransparent(die2);
            final boolean[] selectedAtRoll2 = {false};
            makeDie(die3, 3);
            makeDieTransparent(die3);
            final boolean[] selectedAtRoll3 = {false};
            makeDie(die4, 4);
            makeDieTransparent(die4);
            final boolean[] selectedAtRoll4 = {false};
            makeDie(die5, 5);
            makeDieTransparent(die5);
            final boolean[] selectedAtRoll5 = {false};
            makeDie(die6, 6);
            makeDieTransparent(die6);
            final boolean[] selectedAtRoll6 = {false};

            //Event handlers for each die to create selection

            EventHandler<javafx.scene.input.MouseEvent> eventHandlerDie1 =
                    mouseEvent -> {
                        if (dieSelected[0] == 0 && rollCount == 2) {
                            die1.setStroke(Color.RED);
                            die1.setStrokeWidth(2);
                            dieSelected[0] = 1;
                        } else if (dieSelected[0] == 1 && rollCount == 2) {
                            die1.setStroke(Color.BLACK);
                            die1.setStrokeWidth(1);
                            dieSelected[0] = 0;
                        } else if (rollCount == 3 && selectedAtRoll1[0]) {
                            if (dieSelected[0] == 0) {
                                die1.setStroke(Color.RED);
                                die1.setStrokeWidth(2);
                                dieSelected[0] = 1;
                            } else if (dieSelected[0] == 1) {
                                die1.setStroke(Color.BLACK);
                                die1.setStrokeWidth(1);
                                dieSelected[0] = 0;
                            }
                        }
                    };

            EventHandler<javafx.scene.input.MouseEvent> eventHandlerDie2 =
                    mouseEvent -> {
                        if (dieSelected[1] == 0 && rollCount == 2) {
                            die2.setStroke(Color.RED);
                            die2.setStrokeWidth(2);
                            dieSelected[1] = 1;
                        } else if (dieSelected[1] == 1 && rollCount == 2) {
                            die2.setStroke(Color.BLACK);
                            die2.setStrokeWidth(1);
                            dieSelected[1] = 0;
                        } else if (rollCount == 3 && selectedAtRoll2[0]) {
                            if (dieSelected[1] == 0) {
                                die2.setStroke(Color.RED);
                                die2.setStrokeWidth(2);
                                dieSelected[1] = 1;
                            } else if (dieSelected[1] == 1) {
                                die2.setStroke(Color.BLACK);
                                die2.setStrokeWidth(1);
                                dieSelected[1] = 0;
                            }
                        }
                    };

            EventHandler<javafx.scene.input.MouseEvent> eventHandlerDie3 =
                    mouseEvent -> {
                        if (dieSelected[2] == 0 && rollCount == 2) {
                            die3.setStroke(Color.RED);
                            die3.setStrokeWidth(2);
                            dieSelected[2] = 1;
                        } else if (dieSelected[2] == 1 && rollCount == 2) {
                            die3.setStroke(Color.BLACK);
                            die3.setStrokeWidth(1);
                            dieSelected[2] = 0;
                        } else if (rollCount == 3 && selectedAtRoll3[0]) {
                            if (dieSelected[2] == 0) {
                                die3.setStroke(Color.RED);
                                die3.setStrokeWidth(2);
                                dieSelected[2] = 1;
                            } else if (dieSelected[2] == 1) {
                                die3.setStroke(Color.BLACK);
                                die3.setStrokeWidth(1);
                                dieSelected[2] = 0;
                            }
                        }
                    };

            EventHandler<javafx.scene.input.MouseEvent> eventHandlerDie4 =
                    mouseEvent -> {
                        if (dieSelected[3] == 0 && rollCount == 2) {
                            die4.setStroke(Color.RED);
                            die4.setStrokeWidth(2);
                            dieSelected[3] = 1;
                        } else if (dieSelected[3] == 1 && rollCount == 2) {
                            die4.setStroke(Color.BLACK);
                            die4.setStrokeWidth(1);
                            dieSelected[3] = 0;
                        } else if (rollCount == 3 && selectedAtRoll4[0]) {
                            if (dieSelected[3] == 0) {
                                die4.setStroke(Color.RED);
                                die4.setStrokeWidth(2);
                                dieSelected[3] = 1;
                            } else if (dieSelected[3] == 1) {
                                die4.setStroke(Color.BLACK);
                                die4.setStrokeWidth(1);
                                dieSelected[3] = 0;
                            }
                        }
                    };

            EventHandler<javafx.scene.input.MouseEvent> eventHandlerDie5 =
                    mouseEvent -> {
                        if (dieSelected[4] == 0 && rollCount == 2) {
                            die5.setStroke(Color.RED);
                            die5.setStrokeWidth(2);
                            dieSelected[4] = 1;
                        } else if (dieSelected[4] == 1 && rollCount == 2) {
                            die5.setStroke(Color.BLACK);
                            die5.setStrokeWidth(1);
                            dieSelected[4] = 0;
                        } else if (rollCount == 3 && selectedAtRoll5[0]) {
                            if (dieSelected[4] == 0) {
                                die5.setStroke(Color.RED);
                                die5.setStrokeWidth(2);
                                dieSelected[4] = 1;
                            } else if (dieSelected[4] == 1) {
                                die5.setStroke(Color.BLACK);
                                die5.setStrokeWidth(1);
                                dieSelected[4] = 0;
                            }
                        }
                    };

            EventHandler<javafx.scene.input.MouseEvent> eventHandlerDie6 =
                    mouseEvent -> {
                        if (dieSelected[5] == 0 && rollCount == 2) {
                            die6.setStroke(Color.RED);
                            die6.setStrokeWidth(2);
                            dieSelected[5] = 1;
                        } else if (dieSelected[5] == 1 && rollCount == 2) {
                            die6.setStroke(Color.BLACK);
                            die6.setStrokeWidth(1);
                            dieSelected[5] = 0;
                        } else if (rollCount == 3 && selectedAtRoll6[0]) {
                            if (dieSelected[5] == 0) {
                                die6.setStroke(Color.RED);
                                die6.setStrokeWidth(2);
                                dieSelected[5] = 1;
                            } else if (dieSelected[5] == 1) {
                                die6.setStroke(Color.BLACK);
                                die6.setStrokeWidth(1);
                                dieSelected[5] = 0;
                            }
                        }
                    };

            if (gameJustStarted) {
                die1.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerDie1);
                die2.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerDie2);
                die3.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerDie3);
                die4.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerDie4);
                die5.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerDie5);
                die6.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerDie6);
                gameJustStarted = false;
            }


            dieRoll.getChildren().addAll(die1, die2, die3, die4, die5, die6);

            //Action bar handler

            button.setOnAction(e -> {
                if (rollCount == 4) {
                    dieSelected = new int[]{0, 0, 0, 0, 0, 0};
                    String input = boardTextField.getText();
                    completeAction(input);
                }
            });

            //Roll dice handler

            rollDice.setOnAction(actionEvent -> {
                if (rollCount == 1) {
                    die1.setVisible(true);
                    die2.setVisible(true);
                    die3.setVisible(true);
                    die4.setVisible(true);
                    die5.setVisible(true);
                    die6.setVisible(true);
                    int[] rolledDice = new int[]{0, 0, 0, 0, 0, 0};
                    CatanDice.rollDice(6, rolledDice);
                    Text rollCountText = textBox(String.valueOf(rollCount));
                    Font font = new Font("Verdana", 20);
                    rollCountText.setFont(font);
                    rollCountText.setX(500);
                    rollCountText.setY(287.5);
                    rollCounter.getChildren().add(rollCountText);
                    currentDie = rolledDice;
                    rollCount += 1;
                    int[] newState = resourceStateFromDice(currentDie);
                    currentPlayersResourceState.changeResourceState(newState);
                    currentPlayer.setResources(currentPlayersResourceState);
                    try {
                        displayDice(rolledDice);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else if (rollCount == 2) {
                    rollCounter.getChildren().clear();
                    int numberOfDieToRoll = countZeros(dieSelected);
                    int[] rolledDice = new int[numberOfDieToRoll];
                    int[] rolledDiceFin = rollDiceFinite(rolledDice);
                    Text rollCountText = textBox(String.valueOf(rollCount));
                    Font font = new Font("Verdana", 20);
                    rollCountText.setFont(font);
                    rollCountText.setX(500);
                    rollCountText.setY(287.5);
                    rollCounter.getChildren().add(rollCountText);
                    rollCount += 1;

                    int[] finalList = new int[6];
                    int dieChangedIndex = 0;

                    for (int i = 0; i < 6; i++) {
                        if (dieSelected[i] == 0) {
                            finalList[i] = rolledDiceFin[dieChangedIndex];
                            dieChangedIndex += 1;
                        } else if (dieSelected[i] == 1) {
                            finalList[i] = currentDie[i];
                        }
                    }
                    int dieCount = 0;

                    for (int i = 0; i < 6; i++) {
                        if (dieSelected[i] == 1 && dieCount == 0) {
                            selectedAtRoll1[0] = true;
                        } else if (dieSelected[i] == 1 && dieCount == 1) {
                            selectedAtRoll2[0] = true;
                        } else if (dieSelected[i] == 1 && dieCount == 2) {
                            selectedAtRoll3[0] = true;
                        } else if (dieSelected[i] == 1 && dieCount == 3) {
                            selectedAtRoll4[0] = true;
                        } else if (dieSelected[i] == 1 && dieCount == 4) {
                            selectedAtRoll5[0] = true;
                        } else if (dieSelected[i] == 1 && dieCount == 5) {
                            selectedAtRoll6[0] = true;
                        }
                        dieCount +=1;
                    }

                    currentDie = finalList;
                    int[] newState = resourceStateFromDice(currentDie);
                    currentPlayersResourceState.changeResourceState(newState);
                    currentPlayer.setResources(currentPlayersResourceState);
                    try {
                        displayDice(finalList);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else if (rollCount == 3) {
                    rollCounter.getChildren().clear();
                    int numberOfDieToRoll = countZeros(dieSelected);
                    int[] rolledDice = new int[numberOfDieToRoll];
                    int[] rolledDiceFin = rollDiceFinite(rolledDice);
                    Text rollCountText = textBox(String.valueOf(rollCount));
                    Font font = new Font("Verdana", 20);
                    rollCountText.setFont(font);
                    rollCountText.setX(500);
                    rollCountText.setY(287.5);
                    rollCounter.getChildren().add(rollCountText);
                    rollCount += 1;

                    int[] finalList = new int[6];
                    int dieChangedIndex = 0;

                    for (int i = 0; i < 6; i++) {
                        if (dieSelected[i] == 0) {
                            finalList[i] = rolledDiceFin[dieChangedIndex];
                            dieChangedIndex += 1;
                        } else if (dieSelected[i] == 1) {
                            finalList[i] = currentDie[i];
                        }
                    }
                    currentDie = finalList;
                    try {
                        displayDice(finalList);
                        die1.setStroke(Color.BLACK);
                        die2.setStroke(Color.BLACK);
                        die3.setStroke(Color.BLACK);
                        die4.setStroke(Color.BLACK);
                        die5.setStroke(Color.BLACK);
                        die6.setStroke(Color.BLACK);

                        selectedAtRoll1[0] = false;
                        selectedAtRoll2[0] = false;
                        selectedAtRoll3[0] = false;
                        selectedAtRoll4[0] = false;
                        selectedAtRoll5[0] = false;
                        selectedAtRoll6[0] = false;


                        int[] newState = resourceStateFromDice(currentDie);
                        currentPlayersResourceState.changeResourceState(newState);
                        currentPlayer.setResources(currentPlayersResourceState);

                        if(!AIGame) {
                            displayInstructions("Well done! Please now type a move into the action bar");
                        }

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            //automated AI move;

            if (AIGame && playerTurn == 2) {

                Timeline rollDiceT = new Timeline(
                        new KeyFrame(Duration.seconds(0), event -> {
                            displayInstructions("AI is thinking...");
                            rollDice.fire();}),
                        new KeyFrame(Duration.seconds(1.5), event -> {
                            displayInstructions("AI is thinking...");
                            rollDice.fire();}),
                        new KeyFrame(Duration.seconds(3.0), event -> {
                            displayInstructions("AI has finished rolling");
                            rollDice.fire();}),
                        new KeyFrame(Duration.seconds(4.5), event -> {
                            String highestPointMove = AI.highestPointMove(currentPlayer);
                            if (highestPointMove.equals("")) {
                                displayInstructions("AI was unable to find a move!");
                            } else {
                                displayInstructions("AI has decided to " + highestPointMove + "!");
                                completeAction(highestPointMove);
                            }}),
                        new KeyFrame(Duration.seconds(6.5), event -> endTurn.fire()
                        ));

                rollDiceT.setCycleCount(1);
                rollDiceT.play();


            }





            //End turn button

            endTurn.setOnAction(actionEvent -> {
                //Checks if the game is over. i.e. All players have had 15 turns
                if (playerNumber == 1) {
                    if (player1.getScores().size() == 15 && playerAI.getScores().size() == 15) {
                        gameOver = true;
                    }
                } else if (playerNumber == 2) {
                    if (player1.getScores().size() == 15 && player2.getScores().size() == 15) {
                        gameOver = true;
                    } else if (player1.getScores().size() == 15 && playerAI.getScores().size() == 15) {
                        gameOver = true;
                    }
                } else if (playerNumber == 3) {
                    if (player1.getScores().size() == 15 && player2.getScores().size() == 15 &&
                            player3.getScores().size() == 15) {
                        gameOver = true;
                    }
                } else if (playerNumber == 4) {
                    if (player1.getScores().size() == 15 && player2.getScores().size() == 15 &&
                            player3.getScores().size() == 15 && player4.getScores().size() == 15) {
                        gameOver = true;
                    }
                }

                currentPlayer.incrementTurnCount();

                //checks if the player built. If not add zero to score.

                if (!somethingBuilt) {
                    ArrayList<Integer> newScore = currentPlayer.getScores();
                    newScore.add(0);
                    currentPlayer.setScores(newScore);
                }

                somethingBuilt = false;

                //set die back to transparent

                try {
                    makeDieTransparent(die1);
                    makeDieTransparent(die2);
                    makeDieTransparent(die3);
                    makeDieTransparent(die4);
                    makeDieTransparent(die5);
                    makeDieTransparent(die6);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                //Changes the player turn count and copies changes made.

                dieSelected = new int[]{0, 0, 0, 0, 0, 0};
                rollCount = 1;

                if (playerNumber == 1) {
                    if (playerTurn == 1) {
                        player1.copyFrom(currentPlayer);
                        currentPlayer.copyFrom(playerAI);
                        playerTurn = 2;
                    } else if (playerTurn == 2) {
                        playerAI.copyFrom(currentPlayer);
                        currentPlayer.copyFrom(player1);
                        playerTurn = 1;
                    }
                }
                else if  (playerNumber == 2) {
                    if (playerTurn == 1) {
                        player1.copyFrom(currentPlayer);
                        currentPlayer.copyFrom(player2);
                        playerTurn = 2;
                    } else if (playerTurn == 2) {
                        player2.copyFrom(currentPlayer);
                        currentPlayer.copyFrom(player1);
                        playerTurn = 1;
                    }
                } else if (playerNumber == 3) {
                    if (playerTurn == 1) {
                        player1.copyFrom(currentPlayer);
                        currentPlayer.copyFrom(player2);
                        playerTurn = 2;
                    } else if (playerTurn == 2) {
                        player2.copyFrom(currentPlayer);
                        currentPlayer.copyFrom(player3);
                        playerTurn = 3;
                    } else if (playerTurn == 3) {
                        player3.copyFrom(currentPlayer);
                        currentPlayer.copyFrom(player1);
                        playerTurn = 1;
                    }
                } else if (playerNumber == 4) {
                    if (playerTurn == 1) {
                        player1.copyFrom(currentPlayer);
                        currentPlayer.copyFrom(player2);
                        playerTurn = 2;
                    } else if (playerTurn == 2) {
                        player2.copyFrom(currentPlayer);
                        currentPlayer.copyFrom(player3);
                        playerTurn = 3;
                    } else if (playerTurn == 3) {
                        player3.copyFrom(currentPlayer);
                        currentPlayer.copyFrom(player4);
                        playerTurn = 4;
                    } else if (playerTurn == 4) {
                        player4.copyFrom(currentPlayer);
                        currentPlayer.copyFrom(player1);
                        playerTurn = 1;
                    }
                }

                //recursively call launch controls to start for next player

                try {
                    launchControls();
                } catch (FileNotFoundException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });

            //display the current state of the player and instructions

            displayStateCurrent(currentPlayer);
            displayInstructions(currentPlayer.getName() + " is playing. Please roll!");
            controls.getChildren().addAll(controlMenu, controlHeader, header,
                    hb, actionBarText, hb2, rollCounterRect, resetGameBox, hb3);


        } else { //the game is over
            controls.getChildren().clear();
            currentPlayerDisplay.getChildren().clear();
            redDie.getChildren().clear();
            startMenu.getChildren().clear();
            objects.getChildren().clear();
            hexagons.getChildren().clear();
            structures.getChildren().clear();
            dieRoll.getChildren().clear();
            rollCounter.getChildren().clear();
            instructions.getChildren().clear();

            Rectangle winnerBackground = new Rectangle();
            winnerBackground.setWidth(500);
            winnerBackground.setHeight(400);
            winnerBackground.setX(340);
            winnerBackground.setY(230);
            winnerBackground.setStroke(Color.BLACK);
            Image trophy = new Image(new FileInputStream("assets/trophy.png"));
            winnerBackground.setFill(new ImagePattern(trophy));
            winnerBackground.toFront();

            Rectangle winnerBackground2 = new Rectangle();
            winnerBackground2.setWidth(500);
            winnerBackground2.setHeight(400);
            winnerBackground2.setX(340);
            winnerBackground2.setY(230);
            winnerBackground2.setStroke(Color.BLACK);
            winnerBackground2.setFill(Color.WHITE);


            Rectangle winnerTitle = new Rectangle();
            winnerTitle.setWidth(600);
            winnerTitle.setHeight(100);
            winnerTitle.setX(290);
            winnerTitle.setY(60);
            winnerTitle.setStroke(Color.BLACK);
            winnerTitle.setFill(Color.WHITE);

            String winnerName = "";
            int winnerScore = 0;

            if (playerNumber == 1) {
                int p1Score = player1.sumScores();
                int pAIScore = playerAI.sumScores();
                if (p1Score > pAIScore) {
                    winnerName = "Player 1";
                    winnerScore = p1Score;
                } else if (pAIScore > p1Score) {
                    winnerName = "Player AI";
                    winnerScore = pAIScore;
                }
            } else if (playerNumber == 2) {
                int p1Score = player1.sumScores();
                int p2Score = player2.sumScores();

                if (p1Score > p2Score) {
                    winnerName = "Player 1";
                    winnerScore = p1Score;
                } else if (p2Score > p1Score) {
                    winnerName = "Player 2";
                    winnerScore = p2Score;
                }

            } else if (playerNumber == 3) {
                int p1Score = player1.sumScores();
                int p2Score = player2.sumScores();
                int p3Score = player3.sumScores();

                if (p1Score > p2Score && p2Score > p3Score) {
                    winnerName = "Player 1";
                    winnerScore = p1Score;
                } else if (p2Score > p1Score && p1Score > p3Score) {
                    winnerName = "Player 2";
                    winnerScore = p2Score;
                } else if (p3Score > p1Score && p1Score > p2Score) {
                    winnerName = "Player 3";
                    winnerScore = p3Score;
                }

            } else if (playerNumber == 4) {
                int p1Score = player1.sumScores();
                int p2Score = player2.sumScores();
                int p3Score = player3.sumScores();
                int p4Score = player4.sumScores();

                if (p1Score > p2Score && p2Score > p3Score && p1Score > p4Score) {
                    winnerName = "Player 1";
                    winnerScore = p1Score;
                } else if (p2Score > p1Score && p1Score > p3Score && p2Score > p4Score) {
                    winnerName = "Player 2";
                    winnerScore = p2Score;
                } else if (p3Score > p1Score && p1Score > p2Score && p3Score > p4Score) {
                    winnerName = "Player 3";
                    winnerScore = p3Score;
                } else if (p4Score > p1Score && p4Score > p2Score && p4Score > p3Score) {
                    winnerName = "Player 4";
                    winnerScore = p4Score;
                }


            }

            String winnerText = "The winner is " + winnerName + " with a score of " + winnerScore;

            Text text = new Text(winnerText);
            text.setFont(Font.font("Verdana", 20));
            text.setX(375);
            text.setY(115);
            text.toFront();


            structures.getChildren().addAll(winnerBackground2, winnerBackground, winnerTitle, text);

        }


    }

    //-----------------------------------------------VIEWER-----------------------------------------------------------*/

    /**
     * Displays the current board, score and resource state of the player on the screen.
     * @param player: The current player
     * @throws FileNotFoundException when files are not able to be accessed from /assets.
     * Author: Hugo Heanly u7119555
     */
    public void displayStateCurrent(Player player) throws FileNotFoundException {

        currentPlayerDisplay.getChildren().clear();

        Board board = player.getCurrentBoard();
        String board_state = boardToString(board);
        int[] resourceState = currentPlayer.getCurrentResources().getResourceState();

        String[] boardArr = board_state.split(",");

        for (var str : boardArr) {
            switch (str) {
                case "R0" -> buildRoadCurrent(805, 300, 30);
                case "R1" -> buildRoadCurrent(720, 370, -90);
                case "R2" -> buildRoadCurrent(778, 382, -30);
                case "R3" -> buildRoadCurrent(815, 450, 30);
                case "R4" -> buildRoadCurrent(720, 518, -90);
                case "R5" -> buildRoadCurrent(790, 530, -30);
                case "R6" -> buildRoadCurrent(853, 596, -90);
                case "R7" -> buildRoadCurrent(937, 535, 30);
                case "R8" -> buildRoadCurrent(922, 458, -30);
                case "R9" -> buildRoadCurrent(953, 380, 30);
                case "R10" -> buildRoadCurrent(928, 300, -30);
                case "R11" -> buildRoadCurrent(945, 225, 30);
                case "R12" -> buildRoadCurrent(989, 518, -90);
                case "R13" -> buildRoadCurrent(1070, 460, 30);
                case "R14" -> buildRoadCurrent(1044, 375, -30);
                case "R15" -> buildRoadCurrent(1073, 300, 30);
                case "S3" -> buildSettlementCurrent(825, 290);
                case "S4" -> buildSettlementCurrent(818, 425);
                case "S5" -> buildSettlementCurrent(820, 580);
                case "S7" -> buildSettlementCurrent(955, 510);
                case "S9" -> buildSettlementCurrent(960, 355);
                case "S11" -> buildSettlementCurrent(960, 200);
                case "C7" -> buildTownCurrent(670, 340);
                case "C12" -> buildTownCurrent(670, 490);
                case "C20" -> buildTownCurrent(1075, 420);
                case "C30" -> buildTownCurrent(1075, 260);
                case "J1" -> buildKnightCurrent(731, 240);
                case "J2" -> buildKnightCurrent(731, 395);
                case "J3" -> buildKnightCurrent(866, 475);
                case "J4" -> buildKnightCurrent(1003, 395);
                case "J5" -> buildKnightCurrent(1003, 240);
                case "J6" -> buildKnightCurrent(866, 160);
                case "K1" -> useKnightCurrent(731, 240);
                case "K2" -> useKnightCurrent(731, 395);
                case "K3" -> useKnightCurrent(866, 475);
                case "K4" -> useKnightCurrent(1003, 395);
                case "K5" -> useKnightCurrent(1003, 240);
                case "K6" -> useKnightCurrent(866, 160);
            }

        }

        //display score

        ArrayList<Integer> scores = player.getScores();

        for (int i = 0; i < scores.size(); i ++) {
            String holder = "";
            if (scores.get(i) == 0) {
                holder = holder + "X";
            } else {
                holder = holder + scores.get(i);
            }
            Text score = textBox(holder);
            int lengthScore = 1;
            if (scores.get(i) > 9) {
                lengthScore = 2;
            }
            Font font = new Font("Verdana", 20);
            score.setFont(font);

            if (i == 0) {
                if (lengthScore == 1) {
                    score.setX(372.5);
                    score.setY(458);
                } else {
                    score.setX(366.5);
                    score.setY(458);
                }
                currentPlayerDisplay.getChildren().add(score);

            } else if (i == 1) {
                if (lengthScore == 1) {
                    score.setX(415.5);
                    score.setY(458);
                } else {
                    score.setX(409.25);
                    score.setY(458);
                }
                currentPlayerDisplay.getChildren().add(score);

            } else if (i == 2) {
                if (lengthScore == 1) {
                    score.setX(457.75);
                    score.setY(458);
                } else {
                    score.setX(451.5);
                    score.setY(458);
                }
                currentPlayerDisplay.getChildren().add(score);

            } else if (i == 3) {
                if (lengthScore == 1) {
                    score.setX(500.5);
                    score.setY(458);
                } else {
                    score.setX(494.25);
                    score.setY(458);
                }
                currentPlayerDisplay.getChildren().add(score);

            } else if (i == 4) {
                if (lengthScore == 1) {
                    score.setX(542.5);
                    score.setY(458);
                } else {
                    score.setX(536.5);
                    score.setY(458);
                }
                currentPlayerDisplay.getChildren().add(score);

            } else if (i == 5) {
                if (lengthScore == 1) {
                    score.setX(542.5);
                    score.setY(501);
                } else {
                    score.setX(536.5);
                    score.setY(501);
                }
                currentPlayerDisplay.getChildren().add(score);

            } else if (i == 6) {
                if (lengthScore == 1) {
                    score.setX(542.5);
                    score.setY(544);
                } else {
                    score.setX(536.5);
                    score.setY(544);
                }
                currentPlayerDisplay.getChildren().add(score);

            } else if (i == 7) {
                if (lengthScore == 1) {
                    score.setX(501.5);
                    score.setY(544);
                } else {
                    score.setX(494.25);
                    score.setY(544);
                }
                currentPlayerDisplay.getChildren().add(score);

            } else if (i == 8) {
                if (lengthScore == 1) {
                    score.setX(458.5);
                    score.setY(544);
                } else {
                    score.setX(451.5);
                    score.setY(544);
                }
                currentPlayerDisplay.getChildren().add(score);

            } else if (i == 9) {
                if (lengthScore == 1) {
                    score.setX(415.5);
                    score.setY(544);
                } else {
                    score.setX(409.25);
                    score.setY(544);
                }
                currentPlayerDisplay.getChildren().add(score);

            } else if (i == 10) {
                if (lengthScore == 1) {
                    score.setX(372.5);
                    score.setY(544);
                } else {
                    score.setX(365.5);
                    score.setY(544);
                }
                currentPlayerDisplay.getChildren().add(score);

            } else if (i == 11) {
                if (lengthScore == 1) {
                    score.setX(372.5);
                    score.setY(587);
                } else {
                    score.setX(365.5);
                    score.setY(587);
                }
                currentPlayerDisplay.getChildren().add(score);


            } else if (i == 12) {
                if (lengthScore == 1) {
                    score.setX(372.5);
                    score.setY(629);
                } else {
                    score.setX(365.5);
                    score.setY(629);
                }
                currentPlayerDisplay.getChildren().add(score);

            } else if (i == 13) {
                if (lengthScore == 1) {
                    score.setX(415.5);
                    score.setY(629);
                } else {
                    score.setX(409);
                    score.setY(629);
                }
                currentPlayerDisplay.getChildren().add(score);

            } else {
                if (lengthScore == 1) {
                    score.setX(458.5);
                    score.setY(629);
                } else {
                    score.setX(452);
                    score.setY(629);
                }
                currentPlayerDisplay.getChildren().add(score);

            }
        }

        Text sumScore = textBox(String.valueOf(currentPlayer.sumScores()));
        Font font = new Font("Verdana", 20);
        sumScore.setFont(font);

        if (currentPlayer.sumScores() < -9) {
            sumScore.setX(522.5);
            sumScore.setY(629);
        } else if (currentPlayer.sumScores() < 0) {
            sumScore.setX(528.5);
            sumScore.setY(629);
        } else if (currentPlayer.sumScores() < 10) {
            sumScore.setX(534.5);
            sumScore.setY(629);
        } else if (currentPlayer.sumScores() < 100) {
            sumScore.setX(528.5);
            sumScore.setY(629);
        } else {
            sumScore.setX(522.5);
            sumScore.setY(629);
        }

        currentPlayerDisplay.getChildren().add(sumScore);

        //display resource state

        Image oreDice = new Image(new FileInputStream("assets/Ore_dice.png"));
        Image grainDice = new Image(new FileInputStream("assets/Wheat_dice.png"));
        Image woolDice = new Image(new FileInputStream("assets/Wool_dice.png"));
        Image timberDice = new Image(new FileInputStream("assets/Timber_dice.png"));
        Image brickDice = new Image(new FileInputStream("assets/Brick_dice.png"));
        Image goldDice = new Image(new FileInputStream("assets/Gold_dice.png"));

        Map<String, Integer> resources = new HashMap<>();

        for (int i = 0; i < resourceState.length; i++) {
            if (i == 0) {
                resources.put("ore", resourceState[i]);
            } else if (i == 1) {
                resources.put("grain", resourceState[i]);
            } else if (i == 2) {
                resources.put("wool", resourceState[i]);
            } else if (i == 3) {
                resources.put("timber", resourceState[i]);
            } else if (i == 4) {
                resources.put("brick", resourceState[i]);
            } else if (i == 5) {
                resources.put("gold", resourceState[i]);
            }
        }

        ArrayList<String> listResources = new ArrayList<>();

        ArrayList<String> oreList = new ArrayList<>(Collections.nCopies(resources.get("ore"), "ore"));
        ArrayList<String> grainList = new ArrayList<>(Collections.nCopies(resources.get("grain"), "grain"));
        ArrayList<String> woolList = new ArrayList<>(Collections.nCopies(resources.get("wool"), "wool"));
        ArrayList<String> timberList = new ArrayList<>(Collections.nCopies(resources.get("timber"), "timber"));
        ArrayList<String> brickList = new ArrayList<>(Collections.nCopies(resources.get("brick"), "brick"));
        ArrayList<String> goldList = new ArrayList<>(Collections.nCopies(resources.get("gold"), "gold"));

        listResources.addAll(oreList);
        listResources.addAll(grainList);
        listResources.addAll(woolList);
        listResources.addAll(timberList);
        listResources.addAll(brickList);
        listResources.addAll(goldList);

        for (int i = 0; i < listResources.size(); i++) {
            if (i == 0) {
                switch(listResources.get(i)) {
                    case "ore" -> die1.setFill(new ImagePattern(oreDice));
                    case "grain" -> die1.setFill(new ImagePattern(grainDice));
                    case "wool" -> die1.setFill(new ImagePattern(woolDice));
                    case "timber" -> die1.setFill(new ImagePattern(timberDice));
                    case "brick" -> die1.setFill(new ImagePattern(brickDice));
                    case "gold" -> die1.setFill(new ImagePattern(goldDice));
                }
            } else if (i == 1) {
                switch(listResources.get(i)) {
                    case "ore" -> die2.setFill(new ImagePattern(oreDice));
                    case "grain" -> die2.setFill(new ImagePattern(grainDice));
                    case "wool" -> die2.setFill(new ImagePattern(woolDice));
                    case "timber" -> die2.setFill(new ImagePattern(timberDice));
                    case "brick" -> die2.setFill(new ImagePattern(brickDice));
                    case "gold" -> die2.setFill(new ImagePattern(goldDice));

                }
            } else if (i == 2) {
                switch(listResources.get(i)) {
                    case "ore" -> die3.setFill(new ImagePattern(oreDice));
                    case "grain" -> die3.setFill(new ImagePattern(grainDice));
                    case "wool" -> die3.setFill(new ImagePattern(woolDice));
                    case "timber" -> die3.setFill(new ImagePattern(timberDice));
                    case "brick" -> die3.setFill(new ImagePattern(brickDice));
                    case "gold" -> die3.setFill(new ImagePattern(goldDice));
                }

            } else if (i == 3) {
                switch(listResources.get(i)) {
                    case "ore" -> die4.setFill(new ImagePattern(oreDice));
                    case "grain" -> die4.setFill(new ImagePattern(grainDice));
                    case "wool" -> die4.setFill(new ImagePattern(woolDice));
                    case "timber" -> die4.setFill(new ImagePattern(timberDice));
                    case "brick" -> die4.setFill(new ImagePattern(brickDice));
                    case "gold" -> die4.setFill(new ImagePattern(goldDice));
                }

            } else if (i == 4) {
                switch(listResources.get(i)) {
                    case "ore" -> die5.setFill(new ImagePattern(oreDice));
                    case "grain" -> die5.setFill(new ImagePattern(grainDice));
                    case "wool" -> die5.setFill(new ImagePattern(woolDice));
                    case "timber" -> die5.setFill(new ImagePattern(timberDice));
                    case "brick" -> die5.setFill(new ImagePattern(brickDice));
                    case "gold" -> die5.setFill(new ImagePattern(goldDice));
                }

            } else if (i == 5) {
                switch(listResources.get(i)) {
                    case "ore" -> die6.setFill(new ImagePattern(oreDice));
                    case "grain" -> die6.setFill(new ImagePattern(grainDice));
                    case "wool" -> die6.setFill(new ImagePattern(woolDice));
                    case "timber" -> die6.setFill(new ImagePattern(timberDice));
                    case "brick" -> die6.setFill(new ImagePattern(brickDice));
                    case "gold" -> die6.setFill(new ImagePattern(goldDice));
                }

            }

        }

        if (listResources.size() == 5) {
            makeDieTransparent(die6);
        } else if (listResources.size() == 4) {
            makeDieTransparent(die5);
            makeDieTransparent(die6);
        } else if (listResources.size() == 3) {
            makeDieTransparent(die4);
            makeDieTransparent(die5);
            makeDieTransparent(die6);
        } else if (listResources.size() == 2) {
            makeDieTransparent(die3);
            makeDieTransparent(die4);
            makeDieTransparent(die5);
            makeDieTransparent(die6);
        } else if (listResources.size() == 1) {
            makeDieTransparent(die2);
            makeDieTransparent(die3);
            makeDieTransparent(die4);
            makeDieTransparent(die5);
            makeDieTransparent(die6);
        } else if (listResources.size() == 0) {
            makeDieTransparent(die1);
            makeDieTransparent(die2);
            makeDieTransparent(die3);
            makeDieTransparent(die4);
            makeDieTransparent(die5);
            makeDieTransparent(die6);
        }
        currentPlayerDisplay.toFront();

    }

    /**
     * Completes the action once the input is given from the action bar.
     * Separated so that the AI can give an input as well.
     * @param input: The action to be completed
     */
    public void completeAction(String input) {
        String[] act = input.split(" ");
        String boardString = boardToString(currentPlayer.getCurrentBoard());
        currentStructures = currentPlayer.getCurrentBoard().getStructures();
        ArrayList<Integer> currentPlayersScore = currentPlayer.getScores();
        int[] stringResourceState = currentPlayer.getCurrentResources().getResourceState();

        if (!CatanDice.isActionWellFormed(input)) { //not valid input
            displayInstructions("Invalid action input. Please type again");

        } else if (!AI.anyMovePossible(currentPlayer)) {
            displayInstructions("There are no possible moves left.");

        } else if (act[0].equals("build") && (currentPlayer.getCurrentBoard().getStructure(act[1], currentPlayer.getCurrentBoard().getStructures())).isBuilt()) {
            displayInstructions(act[1] + " is already built!");

        } else if (!CatanDice.canDoAction(input, boardString, stringResourceState)) {
            String returnString = "";
            switch (act[0]) {
                case "build":
                    if (act.length != 2) {
                        returnString = "Invalid build command entered";
                    } else if (!(CatanDice.checkBuildConstraints(act[1], boardString))) {
                        returnString = "Insufficient building prerequisites to build " + act[1];
                    } else if (!(CatanDice.checkResources(act[1], stringResourceState))) {
                        returnString = "Insufficient resources to build " + act[1];
                    }
                    break;
                case "trade":
                    if (act.length != 2) {
                        returnString = "Invalid trade command entered";
                    } else if (stringResourceState[5] < 2) {
                        returnString = "Insufficient gold to execute trade";
                    }
                    break;
                case "swap":
                    int idx1 = Integer.parseInt(act[1]);
                    int idx2 = Integer.parseInt(act[2]);
                    if (act.length != 3) {
                        returnString = "Invalid swap command entered";
                    } else if (!(CatanDice.canDoSwap(idx1, idx2, boardString, stringResourceState))) {
                        returnString = "Do not possess the built knight to execute swap";
                    }
                    break;
            }
            displayInstructions(returnString);

        } else {
            switch (act[0]) {
                case "build" -> {
                    String pos = input.substring(Math.max(input.length() - 2, 0));
                    for (Structure structure : currentStructures) {
                        if (structure.getPosition().equals(pos)) {
                            structure.setBuilt();
                            ArrayList<String> arrayBoard = stringToArrayList(boardString);
                            CatanDice.state_after_building(pos, stringResourceState, arrayBoard);
                            boardString = arrayListToString(arrayBoard);
                            updateResources(stringResourceState);

                            if (somethingBuilt) {
                                int currentScore = currentPlayersScore.get(currentPlayersScore.size() - 1);
                                int newScore = currentScore + structure.getValue();
                                currentPlayersScore.set(currentPlayersScore.size() - 1, newScore);
                            } else {
                                currentPlayersScore.add(structure.getValue());
                            }

                            currentPlayer.setScores(currentPlayersScore);


                        }
                    }
                    somethingBuilt = true;
                    currentPlayer.setBoard(stringToBoard(boardString));
                    try {
                        displayStateCurrent(currentPlayer);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    displayInstructions("Successfully built " + pos);
                }
                case "trade" -> {
                    CatanDice.state_after_trade(input, stringResourceState);
                    currentPlayersResourceState.changeResourceState(stringResourceState);
                    currentPlayer.setResources(currentPlayersResourceState);
                    displayInstructions("Successfully traded two gold for " + input.substring(input.length() - 1));
                    try {
                        displayStateCurrent(currentPlayer);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                case "swap" -> {
                    ArrayList<String> arrayBoard = stringToArrayList(boardString);
                    CatanDice.state_after_swap(stringResourceState, input, arrayBoard);
                    boardString = arrayListToString(arrayBoard);
                    currentPlayersResourceState.changeResourceState(stringResourceState);
                    currentPlayer.setResources(currentPlayersResourceState);
                    currentPlayer.setBoard(stringToBoard(boardString));
                    String received = resourceReturner(input.substring(input.length() - 1));
                    String traded = resourceReturner(Character.toString(input.charAt(5)));
                    displayInstructions("Successfully swapped one " + traded + " for one " + received);

                    try {
                        displayStateCurrent(currentPlayer);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }


    /**
     * Creates the base board for Catan Island 1
     * @throws FileNotFoundException when files are not able to be accessed from /assets.
     * Author: Hugo Heanly u7119555
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
        objects.toBack();

        //Nodes

        root.getChildren().add(controls); //adds control bar
        root.getChildren().add(currentPlayerDisplay); //adds current players board and scores
        root.getChildren().add(redDie); //Shows selected die
        root.getChildren().add(startMenu); // adds start menu
        root.getChildren().add(objects); //adds resource key, scoreboard, title and ocean
        root.getChildren().add(hexagons); //adds all hexagons
        root.getChildren().add(structures); //adds all structures to the board
        root.getChildren().add(dieRoll); // adds dice to the board
        root.getChildren().add(rollCounter);
        root.getChildren().add(instructions);

    }

    /**
     * Creates hexagons. Has been adapted the hexagon code from Assignment 1.
     * @param length: The length of each side
     * @param xCoord: The x-coordinate of the hexagon
     * @param yCoord: The y-coordinate of the hexagon
     * @return: Returns a hexagon of type Polyline
     * Author: Hugo Heanly u7119555
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
     * Author: Hugo Heanly u7119555
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
     * Author: Hugo Heanly u7119555
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
     * Author: Hugo Heanly u7119555
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
     * Author: Hugo Heanly u7119555
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
     * Author: Hugo Heanly u7119555
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
     * Author: Hugo Heanly u7119555
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
     * Builds roads for the current player display
     * @param xCoord: x-coordinate of the road
     * @param yCoord: y-coordinate of the road
     * @param rotation: rotation of the road
     * Author: Hugo Heanly u7119555
     */
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
        currentPlayerDisplay.getChildren().add(stack);
    }

    /**
     * Builds the settlements for the current players display
     * @param xCoord: x-coordinate of the settlement
     * @param yCoord: y-coordinate of the settlement
     * Author: Hugo Heanly u7119555
     */
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

        currentPlayerDisplay.getChildren().add(stack);
        currentPlayerDisplay.getChildren().add(tri);
    }

    /**
     * Builds the towns for the current players display
     * @param xCoord: x-coordinate of the town
     * @param yCoord: y-coordinate of the town
     * Author: Hugo Heanly u7119555
     */
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

        currentPlayerDisplay.getChildren().add(stack);
        currentPlayerDisplay.getChildren().add(tri);
    }

    /**
     * Builds the knights for the current players display
     * @param xCoord: x-coordinate for the knight
     * @param yCoord: y-coordinate for the knight
     * Author: Hugo Heanly u7119555
     */
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

        currentPlayerDisplay.getChildren().add(stack);
        currentPlayerDisplay.getChildren().add(circle);
    }

    /**
     * Builds the used knights for the current players display
     * @param xCoord: x-coordinate for the used knight
     * @param yCoord: y-coordinate for the used knight
     * Author: Hugo Heanly u7119555
     */
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

        currentPlayerDisplay.getChildren().add(stack);
        currentPlayerDisplay.getChildren().add(circle);
    }

    /**
     * Generates the dice images and displays them
     * @param dice is the die to be displayed
     * @throws FileNotFoundException when files are not able to be accessed from /assets.
     * Author: Hugo Heanly u7119555
     */
    public void displayDice(int[] dice) throws FileNotFoundException {

        Image oreDice = new Image(new FileInputStream("assets/Ore_dice.png"));
        Image grainDice = new Image(new FileInputStream("assets/Wheat_dice.png"));
        Image woolDice = new Image(new FileInputStream("assets/Wool_dice.png"));
        Image timberDice = new Image(new FileInputStream("assets/Timber_dice.png"));
        Image brickDice = new Image(new FileInputStream("assets/Brick_dice.png"));
        Image goldDice = new Image(new FileInputStream("assets/Gold_dice.png"));

        int position = 0;

        for (int die : dice) {
            if (position == 0) {
                position += 1;
                if (die == 0) {
                    die1.setFill(new ImagePattern(oreDice));
                } else if (die == 1) {
                    die1.setFill(new ImagePattern(grainDice));
                } else if (die == 2) {
                    die1.setFill(new ImagePattern(woolDice));
                } else if (die == 3) {
                    die1.setFill(new ImagePattern(timberDice));
                } else if (die == 4) {
                    die1.setFill(new ImagePattern(brickDice));
                } else if (die == 5) {
                    die1.setFill(new ImagePattern(goldDice));
                }
            } else if (position == 1) {
                position += 1;
                if (die == 0) {
                    die2.setFill(new ImagePattern(oreDice));
                } else if (die == 1) {
                    die2.setFill(new ImagePattern(grainDice));
                } else if (die == 2) {
                    die2.setFill(new ImagePattern(woolDice));
                } else if (die == 3) {
                    die2.setFill(new ImagePattern(timberDice));
                } else if (die == 4) {
                    die2.setFill(new ImagePattern(brickDice));
                } else if (die == 5) {
                    die2.setFill(new ImagePattern(goldDice));
                }
            } else if (position == 2) {
                position += 1;
                if (die == 0) {
                    die3.setFill(new ImagePattern(oreDice));
                } else if (die == 1) {
                    die3.setFill(new ImagePattern(grainDice));
                } else if (die == 2) {
                    die3.setFill(new ImagePattern(woolDice));
                } else if (die == 3) {
                    die3.setFill(new ImagePattern(timberDice));
                } else if (die == 4) {
                    die3.setFill(new ImagePattern(brickDice));
                } else if (die == 5) {
                    die3.setFill(new ImagePattern(goldDice));
                }
            } else if (position == 3) {
                position += 1;
                if (die == 0) {
                    die4.setFill(new ImagePattern(oreDice));
                } else if (die == 1) {
                    die4.setFill(new ImagePattern(grainDice));
                } else if (die == 2) {
                    die4.setFill(new ImagePattern(woolDice));
                } else if (die == 3) {
                    die4.setFill(new ImagePattern(timberDice));
                } else if (die == 4) {
                    die4.setFill(new ImagePattern(brickDice));
                } else if (die == 5) {
                    die4.setFill(new ImagePattern(goldDice));
                }
            } else if (position == 4) {
                position += 1;
                if (die == 0) {
                    die5.setFill(new ImagePattern(oreDice));
                } else if (die == 1) {
                    die5.setFill(new ImagePattern(grainDice));
                } else if (die == 2) {
                    die5.setFill(new ImagePattern(woolDice));
                } else if (die == 3) {
                    die5.setFill(new ImagePattern(timberDice));
                } else if (die == 4) {
                    die5.setFill(new ImagePattern(brickDice));
                } else if (die == 5) {
                    die5.setFill(new ImagePattern(goldDice));
                }
            } else if (position == 5) {
                position += 1;
                if (die == 0) {
                    die6.setFill(new ImagePattern(oreDice));
                } else if (die == 1) {
                    die6.setFill(new ImagePattern(grainDice));
                } else if (die == 2) {
                    die6.setFill(new ImagePattern(woolDice));
                } else if (die == 3) {
                    die6.setFill(new ImagePattern(timberDice));
                } else if (die == 4) {
                    die6.setFill(new ImagePattern(brickDice));
                } else if (die == 5) {
                    die6.setFill(new ImagePattern(goldDice));
                }
            }
        }
    }

    /**
     * Makes the die and sets their coordinates
     * @param shape the die to be created
     * @param die the die number (1-6)
     * Author: Hugo Heanly u7119555
     */
    public void makeDie(Rectangle shape, int die) {
        shape.setHeight(100);
        shape.setWidth(100);
        shape.setVisible(true);

        if (die == 1) {
            shape.setX(50);
            shape.setY(175);
        } else if (die == 2) {
            shape.setX(175);
            shape.setY(175);
        } else if (die == 3) {
            shape.setX(300);
            shape.setY(175);
        } else if (die == 4) {
            shape.setX(50);
            shape.setY(300);
        } else if (die == 5) {
            shape.setX(175);
            shape.setY(300);
        } else if (die == 6) {
            shape.setX(300);
            shape.setY(300);
        }
    }

    /**
     * Makes the die transparent
     * @param shape: The die to be made transparent
     * @throws FileNotFoundException if the file can not be found in /assets.
     * Author: Hugo Heanly u7119555
     */
    public void makeDieTransparent(Rectangle shape) throws FileNotFoundException {
        shape.setVisible(false);
    }

    /**
     * Makes the die visible
     * @param shape: The die to be made visible
     * @throws FileNotFoundException if the file can not be found in /assets.
     * Author: Hugo Heanly u7119555
     */
    public void makeDieVisible(Rectangle shape) throws FileNotFoundException {
        shape.setVisible(true);
    }

    /**
     * Updates the resource state for the current player
     * @param resourceState: The resource state
     * Author: Hugo Heanly u7119555
     */
    public void updateResources(int[] resourceState) {

        currentPlayersResourceState.changeResourceState(resourceState);
        currentPlayer.setResources(currentPlayersResourceState);

    }

    /**
     * Displays the instructions based on the string
     * @param string: The instruction to be displayed
     * Author: Hugo Heanly u7119555
     */
    public void displayInstructions(String string) {
        instructions.getChildren().clear();
        Text text = new Text(string);
        text.setFont(Font.font("Verdana", 12));
        text.setX(15);
        text.setY(75);
        instructions.getChildren().add(text);
    }

    /**
     * Given a string of a digit, returns the corresponding resource
     * @param string: The digit
     * @return: The corresponding resource as a string
     * Author: Hugo Heanly u7119555
     */
    public String resourceReturner(String string) {
        String wordResource = "";
        switch (string) {
            case "0" -> wordResource = "ore";
            case "1" -> wordResource = "grain";
            case "2" -> wordResource = "wool";
            case "3" -> wordResource = "timber";
            case "4" -> wordResource = "brick";
            case "5" -> wordResource = "gold";

        }
        return wordResource;
    }


    /**
     * Generates a text box given a string
     * @param string the text to be depicted as text on the screen
     * @return returns the JavaFX node text
     * Author: Hugo Heanly u7119555
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

    /*---------------------------------------------HELPER FUNCTIONS--------------------------------------------------*/


    /**
     * Given the string board state, returns the equivalent Board
     * @return board: OO Board
     * Author: Hugo Heanly u7119555
     */
    public Board stringToBoard(String boardState) {
        ArrayList<String> structureStrings = new ArrayList<>(List.of(boardState.split(",")));
        Board currentBoard = new Board();
        Structure[] playersStructures = currentBoard.getStructures();

        for (String builtString : structureStrings) {
            for (Structure unBuiltStructure : playersStructures) {
                if (builtString.equals(unBuiltStructure.getPosition()) && isKnight(builtString)) {
                    if (isUsedKnight(builtString)) {
                        unBuiltStructure.setBuilt();
                        unBuiltStructure.setKnightUsed();
                    } else {
                        unBuiltStructure.setBuilt();
                    }
                } else if (builtString.equals(unBuiltStructure.getPosition())) {
                    unBuiltStructure.setBuilt();
                }
            }
        }
        currentBoard.setStructures(playersStructures);
        return currentBoard;
    }

    /**
     * Converts object orientated board into string.
     * @param board the object orientated board
     * @return the board state as a string
     */
    public String boardToString(Board board)  {
        StringBuilder boardString = new StringBuilder();

        Structure[] currentStructures = board.getStructures();
        for (Structure structure : currentStructures) {
            if (structure.isBuilt()) {
                boardString.append(structure.getPosition()).append(",");
            }
        }
        if (boardString.toString().equals("")) {
            return boardString.toString();
        }
        boardString = new StringBuilder(boardString.substring(0, boardString.length() - 1));
        return boardString.toString();
    }

    /**
     * Determines if the knight is used
     * @param knight: The knight in string form
     * @return true if the knight is used, otherwise false.
     */
    public boolean isUsedKnight(String knight) {
        return knight.equals("K1") || knight.equals("K2") ||
                knight.equals("K3") || knight.equals("K4") ||
                knight.equals("K5") || knight.equals("K6");
    }

    public boolean isKnight(String knight) {
        String letter = knight.substring(0,1);
        return letter.equals("K") || letter.equals("J");
    }
    /**
     * Counts the amount of zeroes in an array of ints
     * @param ints: The array of ints
     * @return: The number of zeroes
     * Author: Hugo Heanly u7119555
     */
    public int countZeros(int[] ints) {
        int count = 0;
        for (int anInt : ints) {
            if (anInt == 0) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Rolls a finite amount of dice
     * @param dice: A list of the dice to be rolled
     * @return: Returns a list of the same dice now rolled
     * Author: Hugo Heanly u7119555
     */
    public int[] rollDiceFinite(int[] dice) {
        int[] rolledDice = new int[dice.length];
        for (int i = 0; i < dice.length; i++) {
            int rolledDie = ThreadLocalRandom.current().nextInt(0, 6);
            rolledDice[i] = rolledDie;
        }
        return rolledDice;
    }

    /**
     * Returns the resource state from a given dice roll
     * @param dice: The rolled dice
     * @return: The corresponding resource state
     * Author: Hugo Heanly u7119555
     */
    public int[] resourceStateFromDice(int[] dice) {
        int oreCount = 0;
        int grainCount = 0;
        int woolCount = 0;
        int timberCount = 0;
        int brickCount = 0;
        int goldCount = 0;

        for (int die : dice) {
            if (die == 0) {
                oreCount += 1;
            } else if (die == 1) {
                grainCount += 1;
            } else if (die == 2) {
                woolCount += 1;
            } else if (die == 3) {
                timberCount += 1;
            } else if (die == 4) {
                brickCount += 1;
            } else if (die == 5) {
                goldCount += 1;
            }
        }

        return new int[]{oreCount,grainCount,woolCount,timberCount,brickCount,goldCount};

    }

    /**
     * Changes the board state from ArrayList to String
     * @param boardList: The board state as an ArrayList
     * @return: The board state as a string
     * Author: Hugo Heanly u7119555
     */
    public String arrayListToString(ArrayList<String> boardList) {
        StringBuilder board = new StringBuilder();

        for (String pos : boardList) {
            board.append(pos).append(",");
        }

        return board.substring(0, board.length()-1);
    }

    /**
     * Changes the board state from String to ArrayList
     * @param boardState: The board state as a String
     * @return: The board state as an ArrayList
     * Author: Hugo Heanly u7119555
     */
    public ArrayList<String> stringToArrayList(String boardState) {

        if (boardState.equals("")) {
            return new ArrayList<>();
        }
        String[] inter = boardState.split(",");
        return new ArrayList<>(Arrays.asList(inter));

    }

    /**
     * Starts the stage
     * @param stage: Stage for the JavaFx
     * @throws Exception
     * Author: Hugo Heanly u7119555
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Board State Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT, Color.GREEN);

        makeBaseBoard();
        launchStartMenu();

        stage.setScene(scene);
        stage.show();
    }


}

