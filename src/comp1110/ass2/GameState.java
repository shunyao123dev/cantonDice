package comp1110.ass2;

import comp1110.ass2.Structure;

import static comp1110.ass2.ResourceType.*;
import static comp1110.ass2.StructureType.*;

// This class authored by Hugo Heanly u7119555
public class GameState {

    //The structures on the game board

    public Structure[] structures;

    public int[] resources;

    //The Knights on the game board
    public final static int STRUCTURES_NO = 33;

    //The number of tiles on the board

    public final static int TILES_NO = 6;


    /**
     * Constructor to instantiate a blank game
     */

    public GameState() {
        structures = new Structure[33];

        for (int i = 0; i < STRUCTURES_NO; i++) {
            // if the structure is the starting road
            if (i == 0) {
                structures[i] = new Structure("R0", START, 0, false, false, NIL);
            }
            // if the structure is a settlement
            else if ((i == 1) | (i == 4) | (i == 7) | (i == 10) | (i == 13) | (i == 16)) {
                if (i == 1) {
                    structures[i] = new Structure("S3", SETTLEMENT, 3, false, false, NIL);
                } else if (i == 4) {
                    structures[i] = new Structure("S4", SETTLEMENT, 4, false, false, NIL);
                } else if (i == 7) {
                    structures[i] = new Structure("S5", SETTLEMENT, 5, false, false, NIL);
                } else if (i == 10) {
                    structures[i] = new Structure("S7", SETTLEMENT, 9, false, false, NIL);
                } else if (i == 13) {
                    structures[i] = new Structure("S9", SETTLEMENT, 11, false, false, NIL);
                } else {
                    structures[i] = new Structure("S11", SETTLEMENT, 11, false, false, NIL);
                }

            } // if the structure is a city

            else if ((i == 18) | (i == 20) | (i == 23) | (i == 26)) {
                if (i == 18) {
                    structures[i] = new Structure("C7", CITY, 7, false, false, NIL);
                } else if (i == 20) {
                    structures[i] = new Structure("C12", CITY, 12, false, false, NIL);
                } else if (i == 23) {
                    structures[i] = new Structure("C23", CITY, 20, false, false, NIL);
                } else {
                    structures[i] = new Structure("C30", CITY, 30, false, false, NIL);
                }

            } //if the structure is a knight

            else if ((i == 27) | (i == 28) | (i == 29) | (i == 30) | (i == 31) | (i == 32)) {
                if (i == 27) {
                    structures[i] = new Structure("J1", KNIGHT, 7, false, false, ORE);
                } else if (i == 28) {
                    structures[i] = new Structure("J2", KNIGHT, 12, false, false, GRAIN);
                } else if (i == 29) {
                    structures[i] = new Structure("J3", KNIGHT, 20, false, false, WOOL);
                } else if (i == 30) {
                    structures[i] = new Structure("J4", KNIGHT, 20, false, false, TIMBER);
                } else if (i == 31) {
                    structures[i] = new Structure("J5", KNIGHT, 20, false, false, BRICK);
                } else {
                    structures[i] = new Structure("J6", KNIGHT, 30, false, false, MYSTERY);
                }
            }

            // if structure is a road

            else {
                if (i == 2) {
                    structures[i] = new Structure("R0", ROAD, 1, false, false, NIL);
                } else if (i == 3) {
                    structures[i] = new Structure("R1", ROAD, 1, false, false, NIL);
                } else if (i == 5) {
                    structures[i] = new Structure("R2", ROAD, 1, false, false, NIL);
                } else if (i == 6) {
                    structures[i] = new Structure("R3", ROAD, 1, false, false, NIL);
                } else if (i == 8) {
                    structures[i] = new Structure("R4", ROAD, 1, false, false, NIL);
                } else if (i == 9) {
                    structures[i] = new Structure("R5", ROAD, 1, false, false, NIL);
                } else if (i == 11) {
                    structures[i] = new Structure("R6", ROAD, 1, false, false, NIL);
                } else if (i == 12) {
                    structures[i] = new Structure("R7", ROAD, 1, false, false, NIL);
                } else if (i == 14) {
                    structures[i] = new Structure("R8", ROAD, 1, false, false, NIL);
                } else if (i == 15) {
                    structures[i] = new Structure("R9", ROAD, 1, false, false, NIL);
                } else if (i == 17) {
                    structures[i] = new Structure("R10", ROAD, 1, false, false, NIL);
                } else if (i == 19) {
                    structures[i] = new Structure("R11", ROAD, 1, false, false, NIL);
                } else if (i == 21) {
                    structures[i] = new Structure("R12", ROAD, 1, false, false, NIL);
                } else if (i == 22) {
                    structures[i] = new Structure("R13", ROAD, 1, false, false, NIL);
                } else if (i == 24) {
                    structures[i] = new Structure("R14", ROAD, 1, false, false, NIL);
                } else {
                    structures[i] = new Structure("R15", ROAD, 1, false, false, NIL);
                }
            }

        }
    }

    /**
     * Forms a GameState object given a board array
     *
     * @param structure: A Structure[] that represents the board state
     */
    public GameState(Structure[] structure) {
        this.structures = new Structure[33];
    }

    /**
     * Returns the board
     * @return
     */
    public Structure[] getBoard() {
        return structures;
    }
}