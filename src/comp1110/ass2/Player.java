package comp1110.ass2;

import java.util.ArrayList;
import java.util.List;

public class Player {
    /**
     * This class add a player into the Catan Dice game and offer methods for player updating their
     * score in each turn
     */


    /**
     * Board for player
     */

    private Board board;

    /**
     * Resource state
     */

    private ResourceState resources;

    /**
     * Array of score
     */

    private ArrayList<Integer> scores = new ArrayList<>();

    /**
     * Count of turn
     */

    private int turnCount = 0;


    /**
     * The name of the player
     */
    private String name;


    /**
     * constructor of initialising the field
     *
     * @param name
     */

    public Player(String name) {
        this.name = name;
        this.board = new Board();
        this.scores = scores; //Zero inserted no builds made
        this.turnCount = turnCount;
        this.resources = new ResourceState();
    }

    /**
     * This method update the player's score in each turn
     *
     * @param score no return
     */
    public void update_score(int score) {

    }

    public ArrayList<Integer> getScores() {
        return this.scores;
    }

    public Board getCurrentBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getTurnCount() {
        return this.turnCount;
    }

    public ResourceState getCurrentResources() {
        return this.resources;
    }

    public void setResources(ResourceState resources) {
        this.resources = resources;
    }

    public void setScores(ArrayList<Integer> score) {
        this.scores = score;
    }

    public int sumScores() {
        ArrayList<Integer> scores = this.scores;
        int sum = 0;
        int zeroCount = 0;
        for (Integer score : scores) {
            if (score == 0) {
                zeroCount += 1;
            }
            sum += score;
        }

        sum = sum - (2 * zeroCount);

        return sum;
    }

}




