package comp1110.ass2;

import comp1110.ass2.Structure;

import static comp1110.ass2.ResourceType.*;
import static comp1110.ass2.StructureType.*;

// This class authored by Hugo Heanly u7119555
public class GameState {

    //The structures on the game board

    public Structure[] structures;

    //The Knights on the game board

    public Knight[] knights;

    //The number of structures on the game board
    public final static int STRUCTURES_NO = 27;

    //The number of tiles on the board

    public final static int TILES_NO = 6;


    //Initialises the structures and tiles to represent the start of the game

    public GameState() {
        this.structures = new Structure[27];

        for (int i = 0; i < STRUCTURES_NO; i++) {
            // if the structure is the starting road
            if (i == 0) {
                this.structures[i] = new Structure("R0", START, 0, false);
            }
            // if the structure is a settlement
            else if ((i == 1) | (i == 4) | (i == 7) | (i == 10) | (i == 13) | (i == 16)) {
                if (i == 1) {
                    this.structures[i] = new Structure("S3", SETTLEMENT, 3, false);
                } else if (i == 4) {
                    this.structures[i] = new Structure("S4", SETTLEMENT, 4, false);
                } else if (i == 7) {
                    this.structures[i] = new Structure("S5", SETTLEMENT, 5, false);
                } else if (i == 10) {
                    this.structures[i] = new Structure("S7", SETTLEMENT, 9, false);
                } else if (i == 13) {
                    this.structures[i] = new Structure("S9", SETTLEMENT, 11, false);
                } else {
                    this.structures[i] = new Structure("S11", SETTLEMENT, 11, false);
                }

            } // if the structure is a city

            else if ((i == 18) | (i == 20) | (i == 23) | (i == 26)) {
                if (i == 18) {
                    this.structures[i] = new Structure("C7", CITY, 7, false);
                } else if (i == 20) {
                    this.structures[i] = new Structure("C12", CITY, 12, false);
                } else if (i == 23) {
                    this.structures[i] = new Structure("C23", CITY, 20, false);
                } else {
                    this.structures[i] = new Structure("C30", CITY, 30, false);
                }

            } //if the structure is a road

            else {
                if (i == 2) {
                    this.structures[i] = new Structure("R0", ROAD, 1, false);
                } else if (i == 3) {
                    this.structures[i] = new Structure("R1", ROAD, 1, false);
                } else if (i == 5) {
                    this.structures[i] = new Structure("R2", ROAD, 1, false);
                } else if (i == 6) {
                    this.structures[i] = new Structure("R3", ROAD, 1, false);
                } else if (i == 8) {
                    this.structures[i] = new Structure("R4", ROAD, 1, false);
                } else if (i == 9) {
                    this.structures[i] = new Structure("R5", ROAD, 1, false);
                } else if (i == 11) {
                    this.structures[i] = new Structure("R6", ROAD, 1, false);
                } else if (i == 12) {
                    this.structures[i] = new Structure("R7", ROAD, 1, false);
                } else if (i == 14) {
                    this.structures[i] = new Structure("R8", ROAD, 1, false);
                } else if (i == 15) {
                    this.structures[i] = new Structure("R9", ROAD, 1, false);
                } else if (i == 17) {
                    this.structures[i] = new Structure("R10", ROAD, 1, false);
                } else if (i == 19) {
                    this.structures[i] = new Structure("R11", ROAD, 1, false);
                } else if (i == 21) {
                    this.structures[i] = new Structure("R12", ROAD, 1, false);
                } else if (i == 22) {
                    this.structures[i] = new Structure("R13", ROAD, 1, false);
                } else if (i == 24) {
                    this.structures[i] = new Structure("R14", ROAD, 1, false);
                } else {
                    this.structures[i] = new Structure("R15", ROAD, 1, false);
                }
            }

        }
    }

    public Knight[] startKnight() {
        this.knights = new Knight[6];

        //First knight
        this.knights[1] = new Knight(1, ORE, false, false);

        //Second knight
        this.knights[2] = new Knight(2, GRAIN, false, false);

        //Third knight
        this.knights[3] = new Knight(3, WOOL, false, false);

        //Fourth knight
        this.knights[4] = new Knight(4, TIMBER, false, false);

        //Fifth knight
        this.knights[5] = new Knight(2, BRICK, false, false);

        //Sixth knight
        this.knights[6] = new Knight(2, MYSTERY, false, false);

        return knights;

    }

    // Creates a GameState object given a board array, player,
    //public GameStateObject(Structure[] structures, Knight[] knights, Player player, ResourceState state, int score) {



    }
