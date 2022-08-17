package comp1110.ass2;


public class BoardValidation {

    public String board;

    /**
     * Checks whether the position of each piece on a board conforms to the game's special restrictions.
     * check the network between the Settlements or city is connect
     * See if the rules are met by traversing each point
     *
     * @param board_state: The string representation of the board state.
     * @return true iff the current state  under the rule of the Building Constraints
     * false otherwise.
     */


    public static boolean checkBoardValidation(String board_state) {
        return false;
    }


    /**
     * Check for city or settlements or roads around knights
     *
     * @param board_state: The string representation of the board state.
     * @return true iff the current postion of knight have any city or settlements or roads next to knight
     * false otherwise.
     */

    public static boolean checkKnights(String board_state) {
        return false;
    }


    /**
     * Check network from start postion to furthest building is connect by road
     *
     * @param board_state: The string representation of the board state.
     * @return true iff  there is no breakpoint
     * false otherwise.
     */
    public static boolean checkNetwork(String board_state) {
        return false;
    }


    /**
     * Check the  spacing of road between settlements or towns is 2.
     *
     * @param board_state: The string representation of the board state.
     * @return true iff  the spacing of the completed buildings is 2 .
     * false otherwise.
     */

    public static boolean checkSpacing(String board_state) {
        return false;
    }

    /**
     * The maximum of building in this game is 33 and count the number of range is
     * between 0 to 33.
     *
     * @param board_state: The string representation of the board state.
     * @return true iff  the count number in the range  .
     * false otherwise.
     */

    public static boolean checkBuildingnumber(String board_state) {
        return false;
    }


}
