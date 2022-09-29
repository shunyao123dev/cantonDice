package comp1110.ass2;

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

    private int[] scores = new int[15];

    /**
     * Count of turn
     */

    private int turnCount = 0;


    /**
     * The name of the player
     */
    private String name;

    /**
     * The score of the player
     */
    private int score;

    /**
     * constructor of initialising the field
     * @param name
     */

    public Player (String name){
        this.name = name;
        this.board = new Board();
    }

    /**
     * This method update the player's score in each turn
     * @param score
     * no return
     */
    public void update_score (int score) {

    }


}
