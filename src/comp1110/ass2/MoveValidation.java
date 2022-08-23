package comp1110.ass2;

public class MoveValidation {


    /**
     * Check next step is valid ,if these step is building then next step is for building
     * is road.
     *
     * @param board_state: The string representation of the board state.
     * @return true iff the next step is valid, false otherwise.
     */
    public static boolean nextStep(String board_state) {
        return false;
    }

    /**
     * Check when build knight check Are there any buildings around the hexagon(The resource block)
     *
     * @param board_state: The string representation of the board state.
     * @return true iff have building , false otherwise.
     */
    public static boolean isKnightValid(String board_state) {
        return false;
    }
    /**
     *    Check if the next position has been placed
     *
     * @param board_state: The string representation of the board state.
     * @return true iff the next position has been no placed, false otherwise.
     */

    public static boolean isEmpty(String board_state) {
        return false;
    }

    public static boolean checkBuildConstraints(String structure,
                                                String board_state) {
        return false; // FIXME: Task #8
    }

    /**
     * Check if the available resources are sufficient to build the
     * specified structure, without considering trades or swaps.
     *
     * @param structure: The string representation of the structure to
     *        be built.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     *         resources, false otherwise.
     */
    public static boolean checkResources(String structure,
                                         int[] resource_state) {
        return false; // FIXME: Task #7
    }

    /**
     * Check if the available resources are sufficient to build the
     * specified structure, considering also trades and/or swaps.
     * This method needs access to the current board state because the
     * board state encodes which Knights are available to perform swaps.
     *
     * @param structure: The string representation of the structure to
     *        be built.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     *         resources, false otherwise.
     */
    public static boolean checkResourcesWithTradeAndSwap(String structure,
                                                         String board_state,
                                                         int[] resource_state) {
        return false; // FIXME: Task #12
    }

    /**
     * Check if a player action (build, trade or swap) is executable in the
     * given board and resource state.
     *
     * @param action: String representatiion of the action to check.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the action is applicable, false otherwise.
     */
    public static boolean canDoAction(String action,
                                      String board_state,
                                      int[] resource_state) {
        return false; // FIXME: Task #9
    }

    /**
     * Check if the specified sequence of player actions is executable
     * from the given board and resource state.
     *
     * @param actions: The sequence of (string representatins of) actions.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the action sequence is executable, false otherwise.
     */
    public static boolean canDoSequence(String[] actions,
                                        String board_state,
                                        int[] resource_state) {
        return false; // FIXME: Task #11
    }




}
