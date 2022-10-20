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
     * @author Shunyao Yang
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
     * @author Zimu Li
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
     * @author Shunyao Yang
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
     * @author Shunyao Yang
     * @return true iff the structure is a possible next build, false
     * otherwise.
     */
    public static boolean checkBuildConstraints(String structure,
                                                String board_state) {
        if (!(isBoardStateWellFormed(board_state))) {
            return false;
        } else if (structure.equals("R0") || structure.equals("S3") || structure.equals("J1")) {
            return true;
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
     * @author Shunyao Yang
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
     * @author Shynyao Yang
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
     * @author Zimu Li
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
     * @author Shunyao Yang
     * @param structure:      The string representation of the structure to
     *                        be built.
     * @param board_state:    The string representation of the board state.
     * @param resource_state: The available resources.
     * @author Shunyao Yang
     * @return true iff the structure can be built with the available
     * resources, false otherwise.
     */
    public static boolean checkResourcesWithTradeAndSwap(String structure,
                                                         String board_state,
                                                         int[] resource_state) {
        boolean flag = false;
        if (checkResources(structure, resource_state)) {
            return true;
        } else {
            Map<Integer, Integer> missing_resource;
            missing_resource = missing_resources(structure, resource_state);
            int total_miss = 0;
            int[] resource_after = resource_state.clone();
            for (Integer missing_re : missing_resource.keySet()) {
                total_miss += missing_resource.get(missing_re);
            }
            ArrayList<String> b_state = new ArrayList<>(Arrays.asList(board_state.split(",")));
            if (resource_state[5] < 2) { // cannot do any trade, only consider swap
                after_possibleSwap(resource_after,missing_resource,b_state,structure);
                if(checkResources(structure,resource_after)) {
                    flag = true;
                }
            } else { // can do the trade
                if (resource_state[5] / 2 >= total_miss) { // trade action is enough to get all missing resources
                    return true;
                } else {// trade not enough, also consider swap
                    after_possibleSwap(resource_after, missing_resource, b_state, structure);
                    if (checkResources(structure, resource_after)) {
                        flag = true;
                    } else {
                        after_all_possible_trade(resource_after,missing_resource);
                        if (checkResources(structure,resource_after)) {
                            flag = true;
                        }
                    }
                }

            }
        }
        return flag; // FIXME: Task #12
    }

    /**
     * Check which index of resource can be used to swap
     * @author Shunyao Yang
     * @param re_state the available resource
     * @param structure the structure that plan to build
     * @return the first index of resource that can be used to swap, if there is not resource
     * that can be used to swap, return -1;
     */
    public static int indexToSwap (int[] re_state,String structure) {
        char s = structure.charAt(0);
        if (s=='C') {
            for(int i=2;i<=5;i++) {
                if (re_state[i]>=1) {
                    return i;
                }
            }
            if (re_state[0]>3) {
                return 0;
            }
            else if (re_state[1]>2) {
                return 1;
            }
        } else if (s=='S') {
            if (re_state[0]>=1) {
                return 0;
            }
            else if (re_state[5]>=1) {
                return 5;
            }
            for (int i=1;i<=4;i++) {
                if(re_state[i]>1) {
                    return i;
                }
            }
        } else if (s=='R') {
            if (re_state[5]>=1) {
                return 5;
            }
            else if (re_state[3]>1) {
                return 3;
            } else if (re_state[4]>1) {
                return 4;
            }
            for (int i=0;i<=2;i++) {
                if(re_state[i]>=1) {
                    return i;
                }
            }
        } else {
            for (int i=0;i<=2;i++) {
                if (re_state[i]>1) {
                    return i;
                }
            }
            for (int i=3;i<=5;i++) {
                if (re_state[i]>=1) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Do all the possible swap based on the given input, and make corresponding changes to the map of missing resources,
     * board_state, and the resource_state
     * @author Shunyoa Yang
     * @param re_state the available resource
     * @param missing_resource: the map representation of the missing resource, with the missing resource as the key, the number still need as the value
     * @param b_state: The arraylist representation of the board_state
     * @param structure the structure that plan to build
     *                  <p> This method doesn't return anything, it updates the resource_state, board_state, missing resource map after implementing all
     *                  possible swapping actions.
     */
    public static void after_possibleSwap(int[] re_state, Map<Integer,Integer> missing_resource,ArrayList<String> b_state,String structure) {
        for (Integer re: missing_resource.keySet()) {
            if(checkExistKnight(re,b_state) && indexToSwap(re_state,structure)!=-1){
                int idx = indexToSwap(re_state,structure);
                String act = "swap "+idx+" "+re;
                state_after_swap(re_state,act,b_state);
                missing_resource.put(re,missing_resource.get(re)-1);
            }
        }
    }

    /**
     * Do all the possible trades based on the hashmap and the amount of available golds, and update the resource_state
     * based on the number of performing trading action
     * @author Shunyao Yang
     * @param re_state the available resource
     * @param missing_res the map representation of the missing resource, missing resource is the key, the amount of resource still
     *                    need is the value
     *                    <p> This method doesn't return anything, it should update the resource_state and the values of
     *                    the missing resource hashmap after performing all possible trades
     */
    public static void after_all_possible_trade(int[] re_state,Map<Integer,Integer> missing_res) {
        int num_trade = re_state[5]/2;
        for(Integer res: missing_res.keySet()) {
            while(missing_res.get(res)>0 && num_trade>0) {
                String act = "trade "+res;
                state_after_trade(act, re_state);
                num_trade--;
                missing_res.put(res,missing_res.get(res)-1);
            }
        }
    }

    /**
     * check whether the board_state has the corresponding unused knight based on the input
     * @param re: the resource index that plan to swap for
     * @param board_state: the arraylist representation of the board_state
     * @author Shunyao Yang
     * @return true if there is corresponding unused knight on the board_state, false otherwise
     */
    public static boolean checkExistKnight(int re, ArrayList<String> board_state) {
        boolean flag = false;
        if (re == 0 && (board_state.contains("J1")||(board_state.contains("J6")))){
            flag = true;
        } else if (re == 1 && (board_state.contains("J2")||(board_state.contains("J6")))) {
            flag = true;
        } else if (re == 2 && (board_state.contains("J3")||(board_state.contains("J6")))) {
            flag = true;
        } else if (re == 3 && (board_state.contains("J4")||(board_state.contains("J6")))) {
            flag = true;
        } else if (re == 4 && (board_state.contains("J5")||(board_state.contains("J6")))) {
            flag = true;
        } else if (re == 5 && board_state.contains("J6")) {
            flag = true;
        }
        return flag;
    }

    /**
     * Check what resources are still missing, and the difference between the required amount and the
     * current amount, and then use the hashmap to store both of them. If the difference is larger than 0, just put
     * into the map, the missing resource_index is the key, and the difference between the required amount and the
     * the current amount is the value
     * @param structure: the string representation of the structure that plan to build
     * @param resource_state: the available resources
     * @author Shunyao Yang
     * @return the hashmap representation of the missing resources
     */
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
     * @param action:    String representation of the action to check.
     * @param board_state:    The string representation of the board state.
     * @param resource_state: The available resources.
     * @author Shunyao Yang
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




    /**
     * check if there is available resource can be used to swap
     * @param swap_resource1: the index of the resource that used to swap
     * @param swap_resource2: the index of the resource that plan to swap for
     * @param board_state: the String representation of the board_state
     * @param resource_state: The available resources
     * @author Shunyao Yang
     * @return true if there is available resource can be used to swap, false
     * otherwise
     */
    public static boolean canDoSwap(int swap_resource1, int swap_resource2, String board_state, int[] resource_state) {
        String[] b_state = board_state.split(",");
        if (resource_state[swap_resource1] < 1) {
            return false;
        }
        ArrayList<String> B_state = new ArrayList<>(Arrays.asList(b_state));
        boolean res = false;
        if (swap_resource2 == 0) {
            if (B_state.contains("J1") || B_state.contains("J6")) {
                res = true;
            }
        } else if (swap_resource2 == 1) {
            if (B_state.contains("J2") || B_state.contains("J6")) {
                res = true;
            }
        } else if (swap_resource2 == 2) {
            if (B_state.contains("J3") || B_state.contains("J6")) {
                res = true;
            }
        } else if (swap_resource2 == 3) {
            if (B_state.contains("J4") || B_state.contains("J6")) {
                res = true;
            }
        } else if (swap_resource2 == 4) {
            if (B_state.contains("J5") || B_state.contains("J6")) {
                res = true;
            }
        } else if (swap_resource2 == 5) {
            if (B_state.contains("J6")) {
                res = true;
            }
        }
        return res;
    }

    /**
     * Check if the specified sequence of player actions is executable
     * from the given board and resource state.
     *
     * @param actions: The sequence of (string representatins of) actions.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @author Zimu Li
     * @return true iff the action sequence is executable, false otherwise.
     */

    public static boolean canDoSequence(String[] actions,
                                        String board_state,
                                        int[] resource_state) {
        ArrayList<String> action = new ArrayList<>(Arrays.asList(actions));
        ArrayList<String> b_state = new ArrayList<>(Arrays.asList(board_state.split(",")));
        int[] re_state = resource_state.clone();
        String n_board_state = board_state;
        for (String act:action) {
            if(canDoAction(act,n_board_state,re_state)) {
                if (act.charAt(0)=='b') {
                    String[] s = act.split(" ");
                    state_after_building(s[1],re_state,b_state);
                } else if (act.charAt(0)=='t') {
                    state_after_trade(act, re_state);
                } else{
                    state_after_swap(re_state,act,b_state);
                }
                n_board_state = String.join(",",b_state);
            } else{
                return false;
            }
        }
        return true;
         // FIXME: Task #11
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
     * @author Zimu Li
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
     * Get an arraylist of action that need to take to build target structures
     * @param re_state: the available resources
     * @param b_state: the arraylist representation of the board_state
     * @param missing_res: the hashmap representation of the missing resource
     * @param target_strucutre: the string representation of the structure plan to build
     * @author Shunyao Yang
     * @return the arraylist representation of the actions need to take to get enough resources of building
     * target structure
     */
    public static ArrayList<String> action_to_get_enough_resource(int[] re_state, ArrayList<String> b_state,
                                                                  Map<Integer,Integer> missing_res,String target_strucutre) {
        ArrayList<String> action = new ArrayList<>();
        int[] r_state = re_state.clone();
        for (Integer res : missing_res.keySet()) {
            if (missing_res.get(res) == 1) {
                if (checkExistKnight(res, b_state) && indexToSwap(r_state, target_strucutre) != -1) {
                    int idx = indexToSwap(r_state, target_strucutre);
                    String act1 = "swap " + idx + " " + res;
                    r_state[idx]--;
                    r_state[res]++;
                    missing_res.put(res, missing_res.get(res) - 1);
                    action.add(act1);
                } else {
                    if (r_state[5] >= 2) {
                        String act = "trade " + res;
                        action.add(act);
                        missing_res.put(res, missing_res.get(res) - 1);
                        r_state[5] -= 2;
                        r_state[res]++;
                    }
                }
            };
        } return action;
    }
    /**
     * Generate a plan (sequence of player actions) to build the target
     * structure from the given board and resource state. The plan may
     * include trades and swaps, as well as building other structures if
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
     * @author Shunyao Yang
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
        int[] r_state = resource_state.clone();
        ArrayList<String> b_plan = new ArrayList<>();
        String[] B_state = board_state.split(",");
        ArrayList<String> plan;
        ArrayList<String> b_state = new ArrayList<>(Arrays.asList(B_state));
        ArrayList<String> stru_to_built;
        ArrayList<String> action;
        Map<Integer,Integer> miss_resource = new HashMap<>();
        if (target_structure.charAt(0)=='J') {
            plan = plan_for_knight(target_structure,b_state);
            stru_to_built = plan;
        } else if (target_structure.charAt(0)=='C') {
            plan = plan_for_city(target_structure);
            stru_to_built = struc_need_to_built(b_state,plan);
        } else if (target_structure.charAt(0)=='S') {
            plan = plan_for_settlement(target_structure);
            stru_to_built = struc_need_to_built(b_state,plan);
        } else{
            plan = plan_for_Road(target_structure);
            stru_to_built = struc_need_to_built(b_state,plan);
        }
        for (String s:stru_to_built) {
            if(checkResources(s,r_state)) {
                String act = "build "+s;
                b_plan.add(act);
                state_after_building(s,r_state,b_state);
            } else{
                String n_board_state = String.join(",",b_state);
                if (checkResourcesWithTradeAndSwap(s,n_board_state,r_state)) {
                    miss_resource = missing_resources(s,r_state);
                    action = action_to_get_enough_resource(r_state,b_state,miss_resource,s);
                    for (String act:action) {
                        if (act.charAt(0)=='t') {
                            state_after_trade(act, r_state);
                        } else{
                            state_after_swap(r_state,act,b_state);
                        }
                    }
                    b_plan.addAll(action);
                    String s2 = "build "+s;
                    b_plan.add(s2);
                    state_after_building(s,r_state,b_state);
                } else{
                    return null;
                }
            }
        }
        String[] build_plan = new String[b_plan.size()];
        for (int i=0;i<b_plan.size();i++) {
            build_plan[i]=b_plan.get(i);
        }
        return build_plan;
        // FIXME: Task #14
    }

    /**
     * Modify the resource state and board_state based on the string representation of the swap action
     * @param r_state: The available resources
     * @param action: The string representation of the action.
     * @param b_state: The arraylist representation of the board_state
     *                <p>
     *                It does not return anything, but update the resource_state and board_state after each swap
     */
    public static void state_after_swap (int[] r_state, String action, ArrayList<String> b_state) {
        String[] act = action.split(" ");
        int res_to_swap = Integer.parseInt(act[1]);
        int res_swap_for = Integer.parseInt(act[2]);
        int idx;
        r_state[res_to_swap]-=1;
        r_state[res_swap_for]+=1;
        if (res_swap_for==0) {
            if (b_state.contains("K1")) {
                idx = b_state.lastIndexOf("J6");
                b_state.set(idx,"K6");
            } else{
                idx = b_state.lastIndexOf("J1");
                b_state.set(idx,"K1");
            }
        } else if (res_swap_for==1) {
            if (b_state.contains("K2")) {
                idx = b_state.lastIndexOf("J6");
                b_state.set(idx,"K6");
            } else{
                idx = b_state.lastIndexOf("J2");
                b_state.set(idx,"K2");
            }
        } else if (res_swap_for==2) {
            if (b_state.contains("K3")) {
                idx = b_state.lastIndexOf("J6");
                b_state.set(idx,"K6");
            } else{
                idx = b_state.lastIndexOf("J3");
                b_state.set(idx,"K3");
            }
        } else if (res_swap_for==3) {
            if (b_state.contains("K4")) {
                idx = b_state.lastIndexOf("J6");
                b_state.set(idx,"K6");
            } else{
                idx = b_state.lastIndexOf("J4");
                b_state.set(idx,"K4");
            }
        } else if (res_swap_for==4) {
            if (b_state.contains("K5")) {
                idx = b_state.lastIndexOf("J6");
                b_state.set(idx,"K6");
            } else{
                idx = b_state.lastIndexOf("J5");
                b_state.set(idx,"K5");
            }
        } else{
            idx = b_state.lastIndexOf("J6");
            b_state.set(idx,"K6");
        }
    }

    /**
     * Make changes to the resource_state after each time of trading
     * @param action the string representation of the trade action
     *               <p> It doesn't return anything, but update the resource_state
     *               correspondingly based on the provided action
     * @param r_state the available resources
     */
    public static void state_after_trade (String action, int[] r_state) {
        String[] act = action.split(" ");
        int trade_idx = Integer.parseInt(act[1]);
        r_state[5]-=2;
        r_state[trade_idx]+=1;
    }

    /**
     * Corresponding change to the resource_state and board_state after building the target-structure
     * @param t_structure the structure plan to build
     * @param resource_state the available resources
     * @param b_state the Arraylist representation of the board state
     *                <p>
     *                It doesn't return anything, but update the resource_state and board_state each time after
     *                building something
     */
    public static void state_after_building (String t_structure, int[] resource_state, ArrayList<String> b_state) {
        if (t_structure.charAt(0)=='J') {
            resource_state[0]--;
            resource_state[1]--;
            resource_state[2]--;
        } else if (t_structure.charAt(0)=='S') {
            resource_state[1]--;
            resource_state[2]--;
            resource_state[3]--;
            resource_state[4]--;
        } else if (t_structure.charAt(0)=='C') {
            resource_state[0]-=3;
            resource_state[1]-=2;
        } else{
            resource_state[3]--;
            resource_state[4]--;
        }
        b_state.add(t_structure);
    }

    /**
     * Generate an arraylist representating what structures we are still missing if we plan to build target structure
     * @param board_state: Arraylist representation of the board_state
     * @param plan: Arraylist representation of building plan from scratch to target structure
     * @author Shunyao Yang
     * @return the arraylist representation of structures that still need to be built to build target structure
     */
    public static ArrayList<String> struc_need_to_built (ArrayList<String> board_state,ArrayList<String> plan){
        ArrayList<String> struc_to_be_built  = new ArrayList<>();
        for (String str:plan) {
            if (!board_state.contains(str)) {
                struc_to_be_built.add(str);
            }
        }
        return struc_to_be_built;
    }

    /**
     * Generate an arraylist representing all necessary structures need to be built in order to build
     * target settlement
     * @param target_structure: the String representation of settlement that plan to be built
     * @author Shunyao Yang
     * @return the arraylist representation of all necessary structures need to build in order to build target
     * settlement
     */
    public static ArrayList<String> plan_for_settlement (String target_structure) {
        String[] s_structure = {"R0","S3","R2","S4","R3","R5","S5","R6","R7","S7","R8","R9","S9","R10","R11","S11"};
        String[] part_structure;
        ArrayList<String> s_plan = new ArrayList<>();
        if (target_structure.equals("S3")) {
            part_structure = Arrays.copyOfRange(s_structure,0,2);
            s_plan.addAll(Arrays.asList(part_structure));
        } else if (target_structure.equals("S4")) {
            part_structure = Arrays.copyOfRange(s_structure,0,4);
            s_plan.addAll(Arrays.asList(part_structure));
        } else if (target_structure.equals("S5")) {
            part_structure = Arrays.copyOfRange(s_structure,0,7);
            s_plan.addAll(Arrays.asList(part_structure));
        } else if (target_structure.equals("S7")) {
            part_structure = Arrays.copyOfRange(s_structure,0,10);
            s_plan.addAll(Arrays.asList(part_structure));
        } else if (target_structure.equals("S9")) {
            part_structure = Arrays.copyOfRange(s_structure,0,13);
            s_plan.addAll(Arrays.asList(part_structure));
        } else{
            part_structure = s_structure.clone();
            s_plan.addAll(Arrays.asList(part_structure));
        }
        return s_plan;
    }

    /**
     * Generate an arraylist representing all necessary structures need to be built in order to build
     * target road
     * @param target_structure: the string representation of the target structure
     * @author Shunyao Yang
     * @return the arraylist representation of all necessary structures need to build in order to build target
     * roads
     */
    public static ArrayList<String> plan_for_Road (String target_structure) {
        ArrayList<String> r_plan = new ArrayList<>();
        if (target_structure.equals("R1")) {
            r_plan.add("R0");
            r_plan.add("R1");
        } else if (target_structure.equals("R2")) {
            r_plan.add("R0");
            r_plan.add("R2");
        } else if (target_structure.equals("R3")) {
            r_plan.add("R0");
            r_plan.add("R2");
            r_plan.add("R3");
        } else if (target_structure.equals("R4")) {
            r_plan.add("R0");
            r_plan.add("R2");
            r_plan.add("R3");
            r_plan.add("R4");
        } else if (target_structure.equals("R5")) {
            r_plan.add("R0");
            r_plan.add("R2");
            r_plan.add("R3");
            r_plan.add("R5");
        } else if (target_structure.equals("R6")) {
            r_plan.add("R0");
            r_plan.add("R2");
            r_plan.add("R3");
            r_plan.add("R5");
            r_plan.add("R6");
        } else if (target_structure.equals("R7")) {
            r_plan.add("R0");
            r_plan.add("R2");
            r_plan.add("R3");
            r_plan.add("R5");
            r_plan.add("R6");
            r_plan.add("R7");
        } else if (target_structure.equals("R8")) {
            r_plan.add("R0");
            r_plan.add("R2");
            r_plan.add("R3");
            r_plan.add("R5");
            r_plan.add("R6");
            r_plan.add("R7");
            r_plan.add("R8");
        } else if (target_structure.equals("R9")) {
            r_plan.add("R0");
            r_plan.add("R2");
            r_plan.add("R3");
            r_plan.add("R5");
            r_plan.add("R6");
            r_plan.add("R7");
            r_plan.add("R8");
            r_plan.add("R9");
        } else if (target_structure.equals("R10")) {
            r_plan.add("R0");
            r_plan.add("R2");
            r_plan.add("R3");
            r_plan.add("R5");
            r_plan.add("R6");
            r_plan.add("R7");
            r_plan.add("R8");
            r_plan.add("R9");
            r_plan.add("R10");
        } else if (target_structure.equals("R11")) {
            r_plan.add("R0");
            r_plan.add("R2");
            r_plan.add("R3");
            r_plan.add("R5");
            r_plan.add("R6");
            r_plan.add("R7");
            r_plan.add("R8");
            r_plan.add("R9");
            r_plan.add("R10");
            r_plan.add("R11");
        } else if (target_structure.equals("R12")) {
            r_plan.add("R0");
            r_plan.add("R2");
            r_plan.add("R3");
            r_plan.add("R5");
            r_plan.add("R6");
            r_plan.add("R7");
            r_plan.add("R12");
        } else if (target_structure.equals("R13")) {
            r_plan.add("R0");
            r_plan.add("R2");
            r_plan.add("R3");
            r_plan.add("R5");
            r_plan.add("R6");
            r_plan.add("R7");
            r_plan.add("R12");
            r_plan.add("R13");
        } else if (target_structure.equals("R14")) {
            r_plan.add("R0");
            r_plan.add("R2");
            r_plan.add("R3");
            r_plan.add("R5");
            r_plan.add("R6");
            r_plan.add("R7");
            r_plan.add("R12");
            r_plan.add("R13");
            r_plan.add("R14");
        } else{
            r_plan.add("R0");
            r_plan.add("R2");
            r_plan.add("R3");
            r_plan.add("R5");
            r_plan.add("R6");
            r_plan.add("R7");
            r_plan.add("R12");
            r_plan.add("R13");
            r_plan.add("R14");
            r_plan.add("R15");
        }
        return r_plan;
    }

    /**
     * Generate a plan for all necessary structures need to build in order to build the target city
     * @param target_structure: the string representation of the target city plan to build
     * @author Shunyao Yang
     * @return an arraylist representation of all necessary structures need to build to build target city
     */
    public static ArrayList<String> plan_for_city (String target_structure) {
        String[] c_structure = {"R0","R1","C7","R2","R3","R4","C12","R5","R6","R7","R12","R13","C20","R14","R15","C30"};
        String[] part_structure;
        ArrayList<String> c_plan = new ArrayList<>();
        if(target_structure.equals("C7")) {
            part_structure = Arrays.copyOfRange(c_structure,0,3);
            c_plan.addAll(Arrays.asList(part_structure));
        } else if (target_structure.equals("C12")) {
            part_structure = Arrays.copyOfRange(c_structure,0,7);
            c_plan.addAll(Arrays.asList(part_structure));
        } else if (target_structure.equals("C20")) {
            part_structure = Arrays.copyOfRange(c_structure,0,13);
            c_plan.addAll(Arrays.asList(part_structure));
        } else {
            part_structure = c_structure.clone();
            c_plan.addAll(Arrays.asList(part_structure));
        }
        return c_plan;
    }

    /**
     * Generate a plan for all necessary structures need to build in order to build the target knight based on the
     * board_state
     * @param target_structure: the target knight that plan to build
     * @param b_state: the arraylist representation of board_state
     * @author Shunyao Yang
     * @return the arraylist representation of the plan for building target knight
     */
    public static ArrayList<String> plan_for_knight (String target_structure, ArrayList<String> b_state) {
        String[] k_structure = {"R0","J1","J2","J3","J4","J5","J6"};
        ArrayList<String> k_plan = new ArrayList<>();
        if (target_structure.equals("J1")) {
            k_plan.add("J1");
        }
        else if (target_structure.equals("J2")) {
            if(!b_state.contains("J1") && (!b_state.contains("K1"))) {
                k_plan.add("J1");
                k_plan.add("J2");
            } else{
                k_plan.add("J2");
            }
        }
        else if (target_structure.equals("J3")) {
            if (!b_state.contains("J1") && !(b_state.contains("K1"))) {
                k_plan.add("J1");
                k_plan.add("J2");
                k_plan.add("J3");
            } else if (!b_state.contains("J2") && !b_state.contains("K2")) {
                k_plan.add("J2");
                k_plan.add("J3");
            } else{
                k_plan.add("J3");
            }
        }
        else if (target_structure.equals("J4")) {
            if (!b_state.contains("J1") && !(b_state.contains("K1"))) {
                k_plan.add("J1");
                k_plan.add("J2");
                k_plan.add("J3");
                k_plan.add("J4");
            } else if (!b_state.contains("J2") && !b_state.contains("K2")) {
                k_plan.add("J2");
                k_plan.add("J3");
                k_plan.add("J4");
            } else if (!b_state.contains("J3") && !b_state.contains("K3")) {
                k_plan.add("J3");
                k_plan.add("J4");
            } else{
                k_plan.add("J4");
            }
        }
        else if (target_structure.equals("J5")) {
            if (!b_state.contains("J1") && !(b_state.contains("K1"))) {
                k_plan.add("J1");
                k_plan.add("J2");
                k_plan.add("J3");
                k_plan.add("J4");
                k_plan.add("J5");
            } else if (!b_state.contains("J2") && !b_state.contains("K2")) {
                k_plan.add("J2");
                k_plan.add("J3");
                k_plan.add("J4");
                k_plan.add("J5");
            } else if (!b_state.contains("J3") && !b_state.contains("K3")){
                k_plan.add("J3");
                k_plan.add("J4");
                k_plan.add("J5");
            } else if (!b_state.contains("J4") && !b_state.contains("K4")) {
                k_plan.add("J4");
                k_plan.add("J5");
            } else{
                k_plan.add("J5");
            }
        }
        else{
            if (!b_state.contains("J1") && !(b_state.contains("K1"))) {
                k_plan.add("J1");
                k_plan.add("J2");
                k_plan.add("J3");
                k_plan.add("J4");
                k_plan.add("J5");
                k_plan.add("J6");
            } else if (!b_state.contains("J2") && !b_state.contains("K2")) {
                k_plan.add("J2");
                k_plan.add("J3");
                k_plan.add("J4");
                k_plan.add("J5");
                k_plan.add("J6");
            } else if (!b_state.contains("J3") && !b_state.contains("K3")){
                k_plan.add("J3");
                k_plan.add("J4");
                k_plan.add("J5");
                k_plan.add("J6");
            } else if (!b_state.contains("J4") && !b_state.contains("K4")) {
                k_plan.add("J4");
                k_plan.add("J5");
                k_plan.add("J6");
            } else if (!b_state.contains("J5") && !b_state.contains("K5")) {
                k_plan.add("J5");
                k_plan.add("J6");
            } else{
                k_plan.add("J6");
            }

        }
        return k_plan;
    }
}
