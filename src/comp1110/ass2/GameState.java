package comp1110.ass2;

import comp1110.ass2.Structure;

import static comp1110.ass2.ResourceType.*;
import static comp1110.ass2.StructureType.*;

// This class authored by Hugo Heanly u7119555
public class GameState {

    //The structures on the game board

    public Structure [][] structures;

    //The tiles on the game board

    public Knight [][] knights;

    //The number of structures on the game board
    public final static int STRUCTURES_NO = 27;

    //The number of tiles on the board

    public final static int TILES_NO = 6;


    //Initialises the structures and tiles to represent the start of the game

    public void startGame() {
        this.structures = new Structure[27][];

        for (int i = 0; i < 27; i++) {
            // if the structure is the starting road
            if (i == 0) {
                this.structures[i][4] = new Structure(0, START, 0, false);
            }
            // if the structure is a settlement
            else if ((i == 1) | (i == 4) | (i == 7) | (i == 9) | (i == 11)) {
                if (i == 1){
                    this.structures[i][4] = new Structure(1,SETTLEMENT, 3, false);
                } else if (i == 4){
                    this.structures[i][4] = new Structure(4,SETTLEMENT, 4, false);
                } else if (i == 7){
                    this.structures[i][4] = new Structure(7,SETTLEMENT, 5, false);
                } else if (i == 9){
                    this.structures[i][4] = new Structure(9,SETTLEMENT, 9, false);
                } else {
                    this.structures[i][4] = new Structure(11,SETTLEMENT, 11, false);
                }

            } // if the structure is a city

            else if ((i == 18) | (i == 20) | (i == 23) | (i == 26)) {
                if (i == 18){
                    this.structures[i][4] = new Structure(18, CITY, 7, false);
                } else if (i == 20){
                    this.structures[i][4] = new Structure(20, CITY, 12, false);
                } else if (i == 23){
                    this.structures[i][4] = new Structure(23, CITY, 20, false);
                } else {
                    this.structures[i][4] = new Structure(26, CITY, 30, false);
                }

            } //if the structure is a road

            else {
                this.structures[i][4] = new Structure(i, ROAD, 1, false);

            }
        }


        this.knights = new Knight[6][];

        //First knight
        this.knights[1][4] = new Knight(1, ORE, false, false);

        //Second knight
        this.knights[2][4] = new Knight(2, GRAIN, false, false);

        //Third knight
        this.knights[3][4] = new Knight(3, WOOL, false, false);

        //Fourth knight
        this.knights[4][4] = new Knight(4, TIMBER, false, false);

        //Fifth knight
        this.knights[5][4] = new Knight(2, BRICK, false, false);

        //Sixth knight
        this.knights[6][4] = new Knight(2, MYSTERY, false, false);


    }






}
