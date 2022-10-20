package comp1110.ass2;

import comp1110.ass2.gui.Game;

import java.util.ArrayList;

/**
 * Author of class: Hugo Heanly u7119555
 */

public class AI {

    static String[] allMoves = new String[]{"build R0", "build R1", "build R2", "build R3", "build R4", "build R5", "build R6", "build R7", "build R8", "build R9", "build R10", "build R11", "build R12",
            "build R13", "build R14", "build R15", "build R16", "build S3",
            "build S4", "build S5", "build S7", "build S9", "build S11",
            "build C7", "build C12", "build C20", "build C30", "build J1",
            "build J2", "build J3", "build J4", "build J5", "build J6",
            "trade 0", "trade 0", "trade 0", "trade 0", "trade 0", "trade 0",
            "swap 0 1","swap 0 2","swap 0 3","swap 0 4","swap 0 5",
            "swap 1 0","swap 1 2","swap 1 3","swap 1 4","swap 1 5",
            "swap 2 0","swap 2 1","swap 2 3","swap 2 4","swap 2 5",
            "swap 3 0","swap 3 1","swap 3 2","swap 3 4","swap 3 5",
            "swap 4 0","swap 4 1","swap 4 2","swap 4 3","swap 4 5",
            "swap 5 0","swap 5 1","swap 5 2","swap 5 3","swap 5 4"};
    public static ArrayList<String> possibleMoves (Player player) {
        ArrayList<String> possibleMoves = new ArrayList<>();
        String board = boardToString(player.getCurrentBoard());
        int[] resources = player.getCurrentResources().getResourceState();

        for (String move : allMoves) {
            if (CatanDice.canDoAction(move, board, resources)) {
                possibleMoves.add(move);
            }
        }
        return possibleMoves;
    }

    public static boolean anyMovePossible (Player player) {
        if (!possibleMoves(player).isEmpty()) {
            return true;
        }
        return false;
    }

    public static String boardToString(Board board)  {
        String boardString = "";

        Structure[] currentStructures = board.getStructures();
        for (Structure structure : currentStructures) {
            if (structure.isBuilt()) {
                boardString = boardString + structure.getPosition() + ",";
            }
        }
        if (boardString.equals("")) {
            return boardString;
        }
        boardString = boardString.substring(0, boardString.length() -1);
        return boardString;
    }





}