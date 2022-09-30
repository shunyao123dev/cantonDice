package comp1110.ass2;


import java.util.*;

public class CatanDice {

    /**
     * Check if the string encoding of a board state is well formed.
     * Note that this does not mean checking if the state is valid
     * (represents a state that the player could get to in game play),
     * only that the string representation is syntactically well formed.
     *
     * @param board_state: The string representation of the board state.
     * @return true iff the string is a well-formed representation of
     * a board state, false otherwise.
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
                                && Character.getNumericValue(b_state[j].charAt(1)) != 6 && Character.getNumericValue(b_state[j].charAt(1)) != 4) {
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
        }
        return true; // FIXME: Task #3
    }


    /**
     * Check if the string encoding of a player action is well formed.
     *
     * @param action: The string representation of the action.
     * @return true iff the string is a well-formed representation of
     * a board state, false otherwise.
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
     * <p>
     * The resource state on input is not necessarily empty. This
     * method should only _add_ the outcome of the dice rolled to
     * the resource state, not remove or clear the resources already
     * represented in it.
     *
     * @param n_dice:         The number of dice to roll (>= 0).
     * @param resource_state: The available resources that the dice
     *                        roll will be added to.
     *                        <p>
     *                        This method does not return any value. It should update the given
     *                        resource_state.
     */
    public static void rollDice(int n_dice, int[] resource_state) {
        Random rad = new Random();
        String[] res = {"ore", "grain", "wool", "lumber", "brick", "gold"};
        HashMap<String, Integer> resource = new HashMap<>();
        for (int i = 1; i <= n_dice; i++) {
            int num = rad.nextInt(6);
            String s1 = res[num];
            if (!(resource.containsKey(s1))) {
                resource.put(s1, 1);
            } else {
                resource.put(s1, resource.get(s1) + 1);
            }
        }

        for (String i1 : resource.keySet()) {
            for (int i = 0; i < res.length; i++) {
                if (i1.equals(res[i])) {
                    resource_state[i] = resource_state[i] + resource.get(i1);
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
     * @param structure:   The string representation of the structure to
     *                     be built.
     * @param board_state: The string representation of the board state.
     * @return true iff the structure is a possible next build, false
     * otherwise.
     */
    public static boolean checkBuildConstraints(String structure,
                                                String board_state) {
        if (!(isBoardStateWellFormed(board_state))) {
            return false;
        }
        String[] b_state = board_state.split(",");
        ArrayList<String> B_state = new ArrayList<>(Arrays.asList(b_state));
        B_state.add(structure);
        int[] s_point = {3, 4, 5, 7, 9, 11};
        int[] c_point = {7, 12, 20, 30};
        int[] R_point = {0, 1, 2, 3, 4, 5, 6, 7};
        int[] R_point1 = {7, 12, 13, 14, 15};
        int[] R_point2 = {7, 8, 9, 10, 11};
        int[] K_point = {1, 2, 3, 4, 5, 6};
        if (!(Building_Valid_Settlement_City(B_state))) {
            return false;
        }
        Map<String, ArrayList<String>> h1 = new HashMap<>();
        h1 = Builtstructure(B_state);
        if (!checkAscendingPoints(h1, c_point, s_point, K_point, R_point, R_point1, R_point2)) {
            return false;
        }
        return true;
    }

    // FIXME: Task #8


    public static Map<String, ArrayList<String>> Builtstructure(ArrayList<String> B_state) {
        Map<String, ArrayList<String>> structure = new HashMap<>();
        ArrayList<String> S = new ArrayList<>();
        ArrayList<String> cit = new ArrayList<>();
        ArrayList<String> Road = new ArrayList<>();
        ArrayList<String> Road1 = new ArrayList<>();
        ArrayList<String> Road2 = new ArrayList<>();
        ArrayList<String> knight = new ArrayList<>();
        for (int i = 0; i < B_state.size(); i++) {
            String stru = B_state.get(i).substring(0, 1);
            if (stru.equals("R")) {
                int num = Integer.parseInt(B_state.get(i).substring(1));
                if (num >= 0 && num <= 7) {
                    Road.add(B_state.get(i));
                    structure.put(stru, Road);
                }
                if (num == 7 || (num >= 12 && num <= 15)) {
                    Road1.add(B_state.get(i));
                    structure.put(stru + "_p1", Road1);
                }
                if (num >= 7 && num <= 11) {
                    Road2.add(B_state.get(i));
                    structure.put(stru + "_p2", Road2);
                }
            } else if (stru.equals("C")) {
                cit.add(B_state.get(i));
                structure.put(stru, cit);
            } else if (stru.equals("J") || stru.equals("K")) {
                knight.add(B_state.get(i));
                structure.put(stru, knight);
            } else if (stru.equals("S")) {
                S.add(B_state.get(i));
                structure.put(stru, S);
            }
        }
        return structure;
    }

    /**
     * check whether Settlements, Cities, Knights and Roads were built
     * in order of increasing point value
     *
     * @param h1      a hashmap takes the character representation of each built structure as the key, and uses their corresponding
     *                point values as values (e.g. R->[R1,R2,R4,R6])
     * @param C_point the array in order of increasing point values of city
     * @param S_point the array in order of increasing point values of settlements
     * @param K_point the array in order of increasing point values of knights
     * @param R_point the array in order of increasing road point values of roads
     * @return true if all the structures were built in ascending points, false otherwise
     */
    public static boolean checkAscendingPoints(Map<String, ArrayList<String>> h1, int[] C_point, int[] S_point, int[] K_point, int[] R_point,
                                               int[] R_point1, int[] R_point2) {
        for (String s : h1.keySet()) {
            if (s.equals("R")) {
                for (int i = 0; i < h1.get(s).size(); i++) {
                    int r_point = Integer.parseInt(h1.get(s).get(i).substring(1));
                    if (r_point != R_point[i]) {
                        return false;
                    }
                }
            } else if (s.equals("R_p1")) {
                for (int i = 0; i < h1.get(s).size(); i++) {
                    int r1_point = Integer.parseInt(h1.get(s).get(i).substring(1));
                    if (r1_point != R_point1[i]) {
                        return false;
                    }
                }
            } else if (s.equals("R_p2")) {
                for (int i = 0; i < h1.get(s).size(); i++) {
                    int r2_point = Integer.parseInt(h1.get(s).get(i).substring(1));
                    if (r2_point != R_point2[i]) {
                        return false;
                    }
                }
            } else if (s.equals("K") || s.equals("J")) {
                for (int i = 0; i < h1.get(s).size(); i++) {
                    int k_point = Integer.parseInt(h1.get(s).get(i).substring(1));
                    if (k_point != K_point[i]) {
                        return false;
                    }
                }
            } else if (s.equals("S")) {
                for (int i = 0; i < h1.get(s).size(); i++) {
                    int s_point = Integer.parseInt(h1.get(s).get(i).substring(1));
                    if (s_point != S_point[i]) {
                        return false;
                    }
                }
            } else {
                for (int i = 0; i < h1.get(s).size(); i++) {
                    int c_point = Integer.parseInt(h1.get(s).get(i).substring(1));
                    if (c_point != C_point[i]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * check if Roads, Settlements and Cities
     * can form a connected network
     *
     * @param b_state - the arraylist containing the string representation of board_state
     * @return true if Roads, Settlements and Cities
     * can form a connected network, starting
     * from the initial Road.
     */
    public static boolean Building_Valid_Settlement_City(ArrayList<String> b_state) {
        for (int i = 0; i < b_state.size(); i++) {
            if (b_state.get(i).charAt(0) == 'C') {
                if (b_state.get(i).equals("C7")) {
                    if (!(b_state.subList(0, i).contains("R1"))) {
                        return false;
                    }
                } else if (b_state.get(i).equals("C12")) {
                    if (!(b_state.subList(0, i).contains("R4"))) {
                        return false;
                    }
                } else if (b_state.get(i).equals("C20")) {
                    if (!(b_state.subList(0, i).contains("R13"))) {
                        return false;
                    }
                } else if (b_state.get(i).equals("C30")) {
                    if (!(b_state.subList(0, i).contains("R15"))) {
                        return false;
                    }
                }
            } else if (b_state.get(i).charAt(0) == 'S') {
                if (b_state.get(i).equals("S4")) {
                    if (!(b_state.subList(0, i).contains("R2"))) {
                        return false;
                    }
                } else if (b_state.get(i).equals("S5")) {
                    if (!(b_state.subList(0, i).contains("R5"))) {
                        return false;
                    }
                } else if (b_state.get(i).equals("S7")) {
                    if (!(b_state.subList(0, i).contains("R7"))) {
                        return false;
                    }
                } else if (b_state.get(i).equals("S9")) {
                    if (!(b_state.subList(0, i).contains("R9"))) {
                        return false;
                    }
                } else if (b_state.get(i).equals("S11")) {
                    if (!(b_state.subList(0, i).contains("R11"))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * Check if the available resources are sufficient to build the
     * specified structure, without considering trades or swaps.
     *
     * @param structure:      The string representation of the structure to
     *                        be built.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     * resources, false otherwise.
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
     * @param structure:      The string representation of the structure to
     *                        be built.
     * @param board_state:    The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     * resources, false otherwise.
     */
    public static boolean checkResourcesWithTradeAndSwap(String structure,
                                                         String board_state,
                                                         int[] resource_state) {
        if (checkResources(structure, resource_state)) {
            return true;
        }

        return false; // FIXME: Task #12
    }

    public static boolean checkExistKnight(int re, ArrayList<String> board_state) {
        boolean flag = false;
        if (re == 0 && board_state.contains("J1")) {
            flag = true;
        } else if (re == 1 && board_state.contains("J2")) {
            flag = true;
        } else if (re == 2 && board_state.contains("J3")) {
            flag = true;
        } else if (re == 3 && board_state.contains("J4")) {
            flag = true;
        } else if (re == 4 && board_state.contains("J5")) {
            flag = true;
        } else if (re == 5 && board_state.contains("J6")) {
            flag = true;
        }
        return flag;
    }

    public static Map<Integer, Integer> missing_resources(String structure, int[] resource_state) {
        Map<Integer, Integer> missing_resource = new HashMap<>();
        char name = structure.charAt(0);
        if (name == 'R') {
            for (int i = 3; i <= 4; i++) {
                if (1 - resource_state[i] > 0) {
                    missing_resource.put(i, 1 - resource_state[i]);
                }
            }

        } else if (name == 'C') {
            if (3 - resource_state[0] > 0) {
                missing_resource.put(0, 3 - resource_state[0]);
            }
            if (2 - resource_state[1] > 0) {
                missing_resource.put(1, 2 - resource_state[1]);
            }
        } else if (name == 'S') {
            for (int i = 1; i <= 4; i++) {
                if (1 - resource_state[i] > 0) {
                    missing_resource.put(i, 1 - resource_state[i]);
                }
            }
        } else {
            for (int i = 0; i <= 2; i++) {
                if (1 - resource_state[i] > 0) {
                    missing_resource.put(i, 1 - resource_state[i]);
                }
            }
        }
        return missing_resource;
    }

    /**
     * Check if a player action (build, trade or swap) is executable in the
     * given board and resource state.
     *
     * @param action:         String representatiion of the action to check.
     * @param board_state:    The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the action is applicable, false otherwise.
     */
    public static boolean canDoAction(String action,
                                      String board_state,
                                      int[] resource_state) {
        String[] act = action.split(" ");
        if (act[0].equals("build")) {
            if (act.length != 2 || !(checkBuildConstraints(act[1], board_state)) || !(checkResources(act[1], resource_state))) {
                return false;
            }
        } else if (act[0].equals("trade")) {
            if (act.length != 2 || resource_state[5] < 2) {
                return false;
            }

        } else if (act[0].equals("swap")) {
            int idx1 = Integer.parseInt(act[1]);
            int idx2 = Integer.parseInt(act[2]);
            if (act.length != 3 || !(canDoSwap(idx1, idx2, board_state, resource_state))) {
                return false;
            }
        } else {
            return false;
        }
        return true;
        // FIXME: Task #9
    }

    public static boolean canDoSwap(int swap_resource1, int swap_resource2, String board_state, int[] resource_state) {
        String[] b_state = board_state.split(",");
        if (resource_state[swap_resource1] < 1) {
            return false;
        }
        ArrayList<String> B_state = new ArrayList<>(Arrays.asList(b_state));
        boolean res = false;
        if (swap_resource2 == 0) {
            if (B_state.contains("J1")) {
                res = true;
            }
        } else if (swap_resource2 == 1) {
            if (B_state.contains("J2")) {
                res = true;
            }
        } else if (swap_resource2 == 2) {
            if (B_state.contains("J3")) {
                res = true;
            }
        } else if (swap_resource2 == 3) {
            if (B_state.contains("J4")) {
                res = true;
            }
        } else if (swap_resource2 == 4) {
            if (B_state.contains("J5")) {
                res = true;
            }
        } else if (swap_resource2 == 5) {
            if (B_state.contains("J6")) {
                res = true;
            }
        }
        return res;
    }

    //    /**
//     * Check if the specified sequence of player actions is executable
//     * from the given board and resource state.
//     *
//     * @param actions: The sequence of (string representatins of) actions.
//     * @param board_state: The string representation of the board state.
//     * @param resource_state: The available resources.
//     * @return true iff the action sequence is executable, false otherwise.
//     */
    public static boolean buildHelper(String nameIn, String board, int[] states, boolean flag) {
        String firstName = nameIn.substring(0, 1);
        if (firstName.equals("R")) {
            if (!checkBuildConstraints(nameIn, board)) {
                return false;
            }
            if (checkResources(nameIn, states)) {
                states[4] = states[4] - 1;
                states[3] = states[3] - 1;
                return true;
            } else {
                return false;
            }
        } else if (firstName.equals("S")) {
            if (!checkBuildConstraints(nameIn, board)) {
                return false;
            }
            if (checkResources(nameIn, states)
            ) {
                states[4] = states[4] - 1;
                states[3] = states[3] - 1;
                states[2] = states[2] - 1;
                states[1] = states[1] - 1;
                return true;
            } else {
                return false;
            }
        } else if (firstName.equals("C")) {
            if (!checkBuildConstraints(nameIn, board)) {
                return false;
            }
            if (checkResources(nameIn, states)) {
                states[0] = states[0] - 3;
                states[1] = states[1] - 2;
                return true;
            } else {
                return false;
            }

        } else if (firstName.equals("J")) {
            if (!checkBuildConstraints(nameIn, board)) {
                return false;
            }
            if (checkResources(nameIn, states)) {
                states[0] = states[0] - 1;
                states[1] = states[1] - 1;
                states[2] = states[2] - 1;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public static boolean canDoSequence(String[] actions,
                                        String board_state,
                                        int[] resource_state) {

        boolean flag = false;
        for (int i = 0; i < actions.length; i++) {
            String[] parts = actions[i].split(" ");
            if (parts[0].equals("trade")) {
                if (resource_state[5] >= 2) {
                    int num = Integer.parseInt(parts[1]);
                    resource_state[5] = resource_state[5] - 2;
                    resource_state[num] = resource_state[num] + 1;
                    flag = true;
                } else {
                    flag = false;
                }
            } else if (parts[0].equals("swap")) {
                int number = Integer.parseInt(parts[1]);
                int number2 = Integer.parseInt(parts[2]);
                if (board_state.contains("J" + (number2 + 1))
                        && (resource_state[number] >= 1)) {
                    resource_state[number] = resource_state[number] - 1;
                    resource_state[number2] = resource_state[number2] + 1;
                    String jTemp = "J" + (number2 + 1);
                    String kTemp = "K" + (number2 + 1);
                    board_state = board_state.replaceAll(jTemp, kTemp);
                    flag = true;
                } else if (board_state.contains("J6") && (resource_state[number] >= 1)) {
                    board_state = board_state.replaceAll("J6", "K6");
                    flag = true;
                } else {
                    return false;
                }
            } else if (parts[0].equals("build")) {
                String name = parts[1];
                flag = buildHelper(name, board_state, resource_state, flag);
                if (flag) {
                    board_state = board_state + (',' + name);
                }
            } else {
                return false;
            }
        }
        return flag; // FIXME: Task #11
    }


    /**
     * Find the path of roads that need to be built to reach a specified
     * (unbuilt) structure in the current board state. The roads should
     * be returned as an array of their string representation, in the
     * order in which they have to be built. The array should _not_ include
     * the target structure (even if it is a road). If the target structure
     * is reachable via the already built roads, the method should return
     * an empty array.
     * <p>
     * Note that on the Island One map, there is a unique path to every
     * structure.
     *
     * @param target_structure: The string representation of the structure
     *                          to reach.
     * @param board_state:      The string representation of the board state.
     * @return An array of string representations of the roads along the
     * path.
     */
    public static String[] pathTo(String target_structure,
                                  String board_state) {
        List<String> temp9 = Arrays.asList(board_state.split(","));
        List boardState = new ArrayList(temp9);

        String[] R15 = new String[]{"R0", "R2", "R3", "R5", "R6", "R7", "R12", "R13", "R14"};
        String[] R14 = new String[]{"R0", "R2", "R3", "R5", "R6", "R7", "R12", "R13"};
        String[] R13 = new String[]{"R0", "R2", "R3", "R5", "R6", "R7", "R12"};
        String[] R12 = new String[]{"R0", "R2", "R3", "R5", "R6", "R7"};
        String[] R11 = new String[]{"R0", "R2", "R3", "R5", "R6", "R7", "R8", "R9", "R10"};
        String[] R10 = new String[]{"R0", "R2", "R3", "R5", "R6", "R7", "R8", "R9"};
        String[] R9 = new String[]{"R0", "R2", "R3", "R5", "R6", "R7", "R8"};
        String[] R8 = new String[]{"R0", "R2", "R3", "R5", "R6", "R7"};
        String[] R7 = new String[]{"R0", "R2", "R3", "R6", "R5"};
        String[] R6 = new String[]{"R0", "R2", "R3", "R5"};
        String[] R5 = new String[]{"R0", "R2", "R3"};
        String[] R4 = new String[]{"R0", "R2", "R3"};
        String[] R3 = new String[]{"R0", "R2"};
        String[] R2 = new String[]{"R0"};
        String[] R1 = new String[]{"R0"};
        String[] R0 = new String[]{};

        String[] C7 = new String[]{"R0", "R1"};
        String[] C12 = new String[]{"R0", "R2", "R3", "R4"};
        String[] C20 = new String[]{"R0", "R2", "R3", "R5", "R6", "R7", "R12", "R13"};
        String[] C30 = new String[]{"R0", "R2", "R3", "R5", "R6", "R7", "R12", "R13", "R14", "R15"};

        String[] S3 = new String[]{};
        String[] S4 = new String[]{"R0", "R2"};
        String[] S5 = new String[]{"R0", "R2", "R3", "R5"};
        String[] S7 = new String[]{"R0", "R2", "R3", "R5", "R6", "R7"};
        String[] S9 = new String[]{"R0", "R2", "R3", "R5", "R6", "R7", "R8", "R9"};
        String[] S11 = new String[]{"R0", "R2", "R3", "R5", "R6", "R7", "R8", "R9", "R10", "R11"};

        List<String[]> list = new ArrayList<>(
                Arrays.asList(R15, R14, R13, R12, R11, R10, R9, R8, R7, R6, R5,
                        R4, R3, R2, R1, R0, C7, C12, C20, C30, S3, S4, S5, S7, S9, S11));

        List<String> list2 = new ArrayList<>(
                Arrays.asList("R15", "R14", "R13", "R12", "R11", "R10", "R9", "R8", "R7", "R6", "R5",
                        "R4", "R3", "R2", "R1", "R0", "C7", "C12", "C20", "C30", "S3", "S4", "S5", "S7", "S9", "S11")
        );
        Map<String, String[]> map = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            map.put(list2.get(i), list.get(i));
        }

        ArrayList<String> findList = new ArrayList<>(Arrays.asList(map.get(target_structure)));
        findList.removeAll(temp9);
        return findList.toArray(new String[findList.size()]);


        // FIXME: Task #13
    }

    /**
     * Generate a plan (sequence of player actions) to build the target
     * structure from the given board and resource state. The plan may
     * include trades and swaps, as well as bulding other structures if
     * needed to reach the target structure or to satisfy the build order
     * constraints.
     * <p>
     * However, the plan must not have redundant actions. This means it
     * must not build any other structure that is not necessary to meet
     * the building constraints for the target structure, and it must not
     * trade or swap for resources if those resources are not needed.
     * <p>
     * If there is no valid build plan for the target structure from the
     * specified state, return null.
     *
     * @param target_structure: The string representation of the structure
     *                          to be built.
     * @param board_state:      The string representation of the board state.
     * @param resource_state:   The available resources.
     * @return An array of string representations of player actions. If
     * there exists no valid build plan for the target structure,
     * the method should return null.
     */
    public static String[] buildPlan(String target_structure,
                                     String board_state,
                                     int[] resource_state) {
        return null; // FIXME: Task #14
    }

}
