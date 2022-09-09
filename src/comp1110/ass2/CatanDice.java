package comp1110.ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CatanDice {

    /**
     * Check if the string encoding of a board state is well formed.
     * Note that this does not mean checking if the state is valid
     * (represents a state that the player could get to in game play),
     * only that the string representation is syntactically well formed.
     *
     * @param board_state: The string representation of the board state.
     * @return true iff the string is a well-formed representation of
     *         a board state, false otherwise.
     */
    public static boolean isBoardStateWellFormed(String board_state) {
        if (board_state.isEmpty()) {
            return true;
        }
        for (int i = 0; i < board_state.length(); i++) {
            if (!(Character.isLetterOrDigit(board_state.charAt(i))) && board_state.charAt(i) != ',') {
                return false;
            }
        }
        String[] b_state = board_state.split(",");
        for (int j = 0; j < b_state.length; j++) {
            if (b_state[j].charAt(0) != 'R' && b_state[j].charAt(0) != 'K' && b_state[j].charAt(0) != 'S'
                    && b_state[j].charAt(0) != 'C' && b_state[j].charAt(0) != 'J') {
                return false;
            } else {
                if (b_state[j].charAt(0) == 'K' || b_state[j].charAt(0) == 'J') {
                    if (b_state[j].length() != 2) {
                        return false;
                    } else {
                        if (Character.getNumericValue(b_state[j].charAt(1)) != 1 && Character.getNumericValue(b_state[j].charAt(1)) != 2 &&
                                Character.getNumericValue(b_state[j].charAt(1)) != 3 && Character.getNumericValue(b_state[j].charAt(1)) != 5
                                && Character.getNumericValue(b_state[j].charAt(1)) != 6) {
                            return false;
                        }
                    }
                } else if (b_state[j].charAt(0) == 'C') {
                    if (b_state[j].length() == 2) {
                        if (Character.getNumericValue(b_state[j].charAt(1)) != 7) {
                            return false;
                        }
                    } else if (b_state[j].length() == 3) {
                        if (Integer.parseInt(b_state[j].substring(1, 3)) != 12 && Integer.parseInt(b_state[j].substring(1, 3)) != 20 && Integer.parseInt(b_state[j].substring(1, 3)) != 30) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else if (b_state[j].charAt(0) == 'S') {
                    if (b_state[j].length() == 2) {
                        if (Character.getNumericValue(b_state[j].charAt(1)) != 3 && Character.getNumericValue(b_state[j].charAt(1)) != 4 &&
                                Character.getNumericValue(b_state[j].charAt(1)) != 5 && Character.getNumericValue(b_state[j].charAt(1)) != 7 &&
                                Character.getNumericValue(b_state[j].charAt(1)) != 9) {
                            return false;
                        }
                    } else if (b_state[j].length() == 3) {
                        if (Integer.parseInt(b_state[j].substring(1, 3)) != 11) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (b_state[j].length() == 2) {
                        if (!(Character.isDigit(b_state[j].charAt(1)))) {
                            return false;
                        }
                    } else if (b_state[j].length() == 3) {
                        if (!(Integer.parseInt(b_state[j].substring(1, 3)) >= 10 && Integer.parseInt(b_state[j].substring(1, 3)) <= 15)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }  return true; // FIXME: Task #3
    }


    /**
     * Check if the string encoding of a player action is well formed.
     *
     * @param action: The string representation of the action.
     * @return true iff the string is a well-formed representation of
     *         a board state, false otherwise.
     */
    public static boolean isActionWellFormed(String action) {
        boolean flag = false;
        List<String> road = new ArrayList<String>(
                List.of("R0", "R1", "R2", "R3", "R4",
                        "R5", "R6", "R7", "R8", "R9", "R10", "R11",
                        "R12", "R13", "R14", "R15"));

        List<String> settlement = new ArrayList<String>(
                List.of("S3", "S4", "S5", "S7", "S9", "S11"));

        List<String> city = new ArrayList<String>(
                List.of("C7", "C12", "C20", "C30"));

        List<String> unusedKnight = new ArrayList<String>(
                List.of("J1", "J2", "J3", "J4", "J5"));

        List<String> Knight = new ArrayList<String>(
                List.of("K1", "K2", "K3", "K4", "K5"));

        List<String> number = new ArrayList<String>(
                List.of("0", "1", "2", "3", "4", "5"));


        String[] arr = action.split(" ");

        if (arr[0].equals("build")) {
            if ((road.contains(arr[1]) == true) ||
                    (settlement.contains(arr[1]) == true) ||
                    (city.contains(arr[1]) == true) ||
                    (unusedKnight.contains(arr[1]) == true) ||
                    (Knight.contains(arr[1]) == true)) {
                flag = true;
            } else {
                flag = false;
            }


        } else if (arr[0].equals("trade")) {
            if (number.contains(arr[1]) == true) {
                flag = true;
            } else {
                flag = false;
            }


        } else if (arr[0].equals("swap")) {
            if ((number.contains(arr[1]) == true) && (number.contains(arr[2]) == true)) {
                flag = true;
            } else {
                flag = false;
            }


        } else {
            flag = false;
        }

        return flag;

        // FIXME: Task #4
    }

    /**
     * Roll the specified number of dice and add the result to the
     * resource state.
     *
     * The resource state on input is not necessarily empty. This
     * method should only _add_ the outcome of the dice rolled to
     * the resource state, not remove or clear the resources already
     * represented in it.
     *
     * @param n_dice: The number of dice to roll (>= 0).
     * @param resource_state: The available resources that the dice
     *        roll will be added to.
     *
     * This method does not return any value. It should update the given
     * resource_state.
     */
    public static void rollDice(int n_dice, int[] resource_state) {
        Random rad = new Random();
        String[] res = {"ore","grain","wool","lumber","brick","gold"};
        HashMap<String,Integer> resource = new HashMap<>();
        for (int i=1;i<=n_dice;i++) {
            int num = rad.nextInt(6);
            String s1 = res[num];
            if(!(resource.containsKey(s1))) {
                resource.put(s1,1);
            } else{
                resource.put(s1,resource.get(s1)+1);
            }
        }

        for (String i1: resource.keySet()) {
            for(int i=0;i<res.length;i++) {
                if(i1.equals(res[i])) {
                    resource_state[i] = resource_state[i]+resource.get(i1);
                }
            }
        }


	// FIXME: Task #6
    }

    /**
     * Check if the specified structure can be built next, given the
     * current board state. This method should check that the build
     * meets the constraints described in section "Building Constraints"
     * of the README file.
     *
     * @param structure: The string representation of the structure to
     *        be built.
     * @param board_state: The string representation of the board state.
     * @return true iff the structure is a possible next build, false
     *         otherwise.
     */
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
        boolean flag = false;


        if (structure.charAt(0) == 'R') {
            if ((resource_state[4] >= 1) &&
                    (resource_state[3] >= 1)) {
                flag = true;
            }

        } else if (structure.charAt(0) == 'S') {
            if ((resource_state[1] >= 1) &&
                    (resource_state[2] >= 1) &&
                    (resource_state[3] >= 1) &&
                    (resource_state[4] >= 1)) {
                flag = true;
            }
        } else if (structure.charAt(0) == 'C') {
            if ((resource_state[0] >= 3) &&
                    (resource_state[1] >= 2)) {
                flag = true;
            }

        } else if (structure.charAt(0) == 'J') {
            if ((resource_state[0] >= 1) &&
                    (resource_state[1] >= 1) &&
                    (resource_state[2] >= 1)) {
                flag = true;
            }
            // FIXME: Task #7
        }
        return flag;
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

    /**
     * Find the path of roads that need to be built to reach a specified
     * (unbuilt) structure in the current board state. The roads should
     * be returned as an array of their string representation, in the
     * order in which they have to be built. The array should _not_ include
     * the target structure (even if it is a road). If the target structure
     * is reachable via the already built roads, the method should return
     * an empty array.
     * 
     * Note that on the Island One map, there is a unique path to every
     * structure. 
     *
     * @param target_structure: The string representation of the structure
     *        to reach.
     * @param board_state: The string representation of the board state.
     * @return An array of string representations of the roads along the
     *         path.
     */
    public static String[] pathTo(String target_structure,
				  String board_state) {
	String[] result = {};
	return result; // FIXME: Task #13
    }

    /**
     * Generate a plan (sequence of player actions) to build the target
     * structure from the given board and resource state. The plan may
     * include trades and swaps, as well as bulding other structures if
     * needed to reach the target structure or to satisfy the build order
     * constraints.
     *
     * However, the plan must not have redundant actions. This means it
     * must not build any other structure that is not necessary to meet
     * the building constraints for the target structure, and it must not
     * trade or swap for resources if those resources are not needed.
     *
     * If there is no valid build plan for the target structure from the
     * specified state, return null.
     *
     * @param target_structure: The string representation of the structure
     *        to be built.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return An array of string representations of player actions. If
     *         there exists no valid build plan for the target structure,
     *         the method should return null.
     */
    public static String[] buildPlan(String target_structure,
				     String board_state,
				     int[] resource_state) {
	 return null; // FIXME: Task #14
    }

}
