package comp1110.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static comp1110.ass2.ResourceType.*;
import static comp1110.ass2.StructureType.*;

/**
 * Author of class: Hugo Heanly u7119555
 */

public class Board {

    //The structures on the game board
    public Structure[] structures;

    public int[] resources;

    //The Knights on the game board
    public final static int STRUCTURES_NO = 32;


    /**
     * Constructor to instantiate a blank game
     */

    public Board() {
        structures = new Structure[32];

        //The prerequisite structures for each to be built

        //Roads
        ArrayList<String> preReqR0 = new ArrayList<>();
        ArrayList<String> preReqR1 = new ArrayList<>(List.of("R0"));
        ArrayList<String> preReqR2 = new ArrayList<>(List.of("R0"));
        ArrayList<String> preReqR3 = new ArrayList<>(Arrays.asList("R0", "R2"));
        ArrayList<String> preReqR4 = new ArrayList<>(Arrays.asList("R0", "R2", "R3"));
        ArrayList<String> preReqR5 = new ArrayList<>(Arrays.asList("R0", "R2", "R3"));
        ArrayList<String> preReqR6 = new ArrayList<>(Arrays.asList("R0", "R2", "R3", "R5"));
        ArrayList<String> preReqR7 = new ArrayList<>(Arrays.asList("R0", "R2", "R3", "R5", "R6"));
        ArrayList<String> preReqR8 = new ArrayList<>(Arrays.asList("R0", "R2", "R3", "R5", "R6", "R7"));
        ArrayList<String> preReqR9 = new ArrayList<>(Arrays.asList("R0", "R2", "R3", "R5", "R6", "R7", "R8"));
        ArrayList<String> preReqR10 = new ArrayList<>(Arrays.asList("R0", "R2", "R3", "R5", "R6", "R7", "R8", "R9"));
        ArrayList<String> preReqR11 = new ArrayList<>(Arrays.asList("R0", "R2", "R3", "R5", "R6", "R7", "R8", "R9", "R10"));
        ArrayList<String> preReqR12 = new ArrayList<>(Arrays.asList("R0", "R2", "R3", "R5", "R6", "R7"));
        ArrayList<String> preReqR13 = new ArrayList<>(Arrays.asList("R0", "R2", "R3", "R5", "R6", "R7", "R12"));
        ArrayList<String> preReqR14 = new ArrayList<>(Arrays.asList("R0", "R2", "R3", "R5", "R6", "R7", "R12", "R13"));
        ArrayList<String> preReqR15 = new ArrayList<>(Arrays.asList("R0", "R2", "R3", "R5", "R6", "R7", "R12", "R13", "R14"));

        //Settlements

        ArrayList<String> preReqS3 = new ArrayList<>(Arrays.asList("S3", "R0", "R2"));
        ArrayList<String> preReqS4 = new ArrayList<>(Arrays.asList("S3", "R0", "R2"));
        ArrayList<String> preReqS5 = new ArrayList<>(Arrays.asList("S3", "R0", "R2", "S4", "R3", "R5"));
        ArrayList<String> preReqS7 = new ArrayList<>(Arrays.asList("S3", "R0", "R2", "S4", "R3", "R5", "S5", "R6", "R7"));
        ArrayList<String> preReqS9 = new ArrayList<>(Arrays.asList("S3", "R0", "R2", "S4", "R3", "R5", "S5", "R6", "R7", "S7", "R8", "R9"));
        ArrayList<String> preReqS11 = new ArrayList<>(Arrays.asList("S3", "R0", "R2", "S4", "R3", "R5", "S5", "R6", "R7", "S7", "R8", "R9", "S9", "R10", "R11"));

        //Cities

        ArrayList<String> preReqC7 = new ArrayList<>(Arrays.asList("R0", "R1"));
        ArrayList<String> preReqC12 = new ArrayList<>(Arrays.asList("R0", "R2", "R3", "R4", "C7"));
        ArrayList<String> preReqC20 = new ArrayList<>(Arrays.asList("R0", "R2", "R3", "R5", "R6", "R7", "R12", "R13", "C7", "C12"));
        ArrayList<String> preReqC30 = new ArrayList<>(Arrays.asList("R0", "R2", "R3", "R5", "R6", "R7", "R12", "R13", "C7", "C12", "R14", "R15", "C20"));

        //Knights

        ArrayList<String> preReqK1 = new ArrayList<>();
        ArrayList<String> preReqK2 = new ArrayList<>(List.of("K1"));
        ArrayList<String> preReqK3 = new ArrayList<>(Arrays.asList("K1", "K2"));
        ArrayList<String> preReqK4 = new ArrayList<>(Arrays.asList("K1", "K2", "K3"));
        ArrayList<String> preReqK5 = new ArrayList<>(Arrays.asList("K1", "K2", "K3", "K4"));
        ArrayList<String> preReqK6 = new ArrayList<>(Arrays.asList("K1", "K2", "K3", "K4", "K5"));


        for (int i = 0; i < STRUCTURES_NO; i++) {
            // if the structure is the starting road
            if (i == 0) {
                structures[i] = new Structure("R0", ROAD, 1, false, false, NIL, preReqR0);
            }
            // if the structure is a settlement
            else if ((i == 1) | (i == 4) | (i == 7) | (i == 10) | (i == 13) | (i == 16)) {
                if (i == 1) {
                    structures[i] = new Structure("S3", SETTLEMENT, 3, false, false, NIL, preReqS3);
                } else if (i == 4) {
                    structures[i] = new Structure("S4", SETTLEMENT, 4, false, false, NIL, preReqS4);
                } else if (i == 7) {
                    structures[i] = new Structure("S5", SETTLEMENT, 5, false, false, NIL, preReqS5);
                } else if (i == 10) {
                    structures[i] = new Structure("S7", SETTLEMENT, 9, false, false, NIL, preReqS7);
                } else if (i == 13) {
                    structures[i] = new Structure("S9", SETTLEMENT, 11, false, false, NIL, preReqS9);
                } else {
                    structures[i] = new Structure("S11", SETTLEMENT, 11, false, false, NIL, preReqS11);
                }

            } // if the structure is a city

            else if ((i == 18) | (i == 20) | (i == 23) | (i == 25)) {
                if (i == 18) {
                    structures[i] = new Structure("C7", CITY, 7, false, false, NIL, preReqC7);
                } else if (i == 20) {
                    structures[i] = new Structure("C12", CITY, 12, false, false, NIL, preReqC12);
                } else if (i == 23) {
                    structures[i] = new Structure("C20", CITY, 20, false, false, NIL, preReqC20);
                } else {
                    structures[i] = new Structure("C30", CITY, 30, false, false, NIL, preReqC30);
                }

            } //if the structure is a knight

            else if ((i == 26) | (i == 27) | (i == 28) | (i == 29) | (i == 30) | (i == 31)) {
                if (i == 26) {
                    structures[i] = new Structure("J1", KNIGHT, 1, false, false, ORE, preReqK1);
                } else if (i == 27) {
                    structures[i] = new Structure("J2", KNIGHT, 2, false, false, GRAIN, preReqK2);
                } else if (i == 28) {
                    structures[i] = new Structure("J3", KNIGHT, 3, false, false, WOOL, preReqK3);
                } else if (i == 29) {
                    structures[i] = new Structure("J4", KNIGHT, 4, false, false, TIMBER, preReqK4);
                } else if (i == 30) {
                    structures[i] = new Structure("J5", KNIGHT, 5, false, false, BRICK, preReqK5);
                } else {
                    structures[i] = new Structure("J6", KNIGHT, 6, false, false, MYSTERY, preReqK6);
                }
            }

            // if structure is a road

            else {
                if (i == 2) {
                    structures[i] = new Structure("R1", ROAD, 1, false, false, NIL, preReqR1);
                } else if (i == 3) {
                    structures[i] = new Structure("R2", ROAD, 1, false, false, NIL, preReqR2);
                } else if (i == 5) {
                    structures[i] = new Structure("R3", ROAD, 1, false, false, NIL, preReqR3);
                } else if (i == 6) {
                    structures[i] = new Structure("R4", ROAD, 1, false, false, NIL, preReqR4);
                } else if (i == 8) {
                    structures[i] = new Structure("R5", ROAD, 1, false, false, NIL, preReqR5);
                } else if (i == 9) {
                    structures[i] = new Structure("R6", ROAD, 1, false, false, NIL, preReqR6);
                } else if (i == 11) {
                    structures[i] = new Structure("R7", ROAD, 1, false, false, NIL, preReqR7);
                } else if (i == 12) {
                    structures[i] = new Structure("R8", ROAD, 1, false, false, NIL, preReqR8);
                } else if (i == 14) {
                    structures[i] = new Structure("R9", ROAD, 1, false, false, NIL, preReqR9);
                } else if (i == 15) {
                    structures[i] = new Structure("R10", ROAD, 1, false, false, NIL, preReqR10);
                } else if (i == 17) {
                    structures[i] = new Structure("R11", ROAD, 1, false, false, NIL, preReqR11);
                } else if (i == 19) {
                    structures[i] = new Structure("R12", ROAD, 1, false, false, NIL, preReqR12);
                } else if (i == 21) {
                    structures[i] = new Structure("R13", ROAD, 1, false, false, NIL, preReqR13);
                } else if (i == 22) {
                    structures[i] = new Structure("R14", ROAD, 1, false, false, NIL, preReqR14);
                } else {
                    structures[i] = new Structure("R15", ROAD, 1, false, false, NIL, preReqR15);
                }
            }


        }

    }

    public void buildStructures(String string) {
        ArrayList<String> structureStrings = new ArrayList<>(List.of(string.split(",")));
        for (String pos : structureStrings) {
            for (Structure structure : this.structures) {
                if (pos.equals(structure.getPosition())) {
                    structure.setBuilt();
            }
            }
        }

    }

    public void setStructures(Structure[] structures) {
        this.structures = structures;
    }

    /**
     * Given the string board state, returns the equivalent Board
     * @return board: OO Board
     */
    public Board stringToBoard(String boardState) {
        ArrayList<String> structureStrings = new ArrayList<>(List.of(boardState.split(",")));
        Board currentBoard = new Board();
        Structure[] playersStructures = currentBoard.structures;

        for (String builtString : structureStrings) {
            for (Structure unBuiltStructure : playersStructures) {
                if (builtString.equals(unBuiltStructure.getPosition()) && isUsedKnight(builtString)) {
                    unBuiltStructure.setBuilt();
                    unBuiltStructure.setKnightUsed();
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
     * @param board: Returns the board into a string
     * @return: Returns the board as a string.
     */
    public String boardToString(Board board)  {
        StringBuilder boardString = new StringBuilder();
        Structure[] currentStructures = board.structures;
        int count = 0;
        int builtCount = 0;

        for (Structure structure : currentStructures) {
            if (structure.isBuilt()) {
                builtCount +=1;
            }
        }

        for (Structure structure : currentStructures) {
            if (structure.isBuilt() && (count != builtCount - 1)) {
                boardString.append(structure.getPosition()).append(",");
                count += 1;
            } else if (structure.isBuilt() && count == builtCount - 1) {
                boardString.append(structure.getPosition());
            }
        }
        int lengthString = boardString.length();
        boardString = new StringBuilder(boardString.substring(3, lengthString));
        return boardString.toString();
    }

    public boolean isUsedKnight(String knight) {
        return knight.equals("K1") || knight.equals("K2") ||
                knight.equals("K3") || knight.equals("K4") ||
                knight.equals("K5") || knight.equals("K6");
    }

    public Structure[] getStructures(){
        return this.structures;
    }

    public Structure getStructure(String pos, Structure[] structures) {
        for (Structure structure : structures) {
            if (structure.getPosition().equals(pos)) {
                return structure;
            }
        }
        return null;
    }

}