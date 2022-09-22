package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class isBoardStateWellFormedTest {

    private static final String testBoardState =
            "R0,R1,R2,R3,R4,R5,R6,R7,R8," +
                    "R9,R10,R11,R12,R13,R14,R15,S3," +
                    "S4,S5,S7,S9,S11,C7,C12,C20," +
                    "C30,J1,J2,J3,J4,J5,J6,K1,K2,K3,K4,K5,K6";


    @Test
    void checkTrue() {
        assertTrue(CatanDice.isBoardStateWellFormed("J3,K2,R13,S7"));
        assertTrue(CatanDice.isBoardStateWellFormed("J2,J2,J2,J2,J2,J2,J2"));
        assertTrue(CatanDice.isBoardStateWellFormed(testBoardState));
    }

    void checkFalse() {
        assertFalse(CatanDice.isBoardStateWellFormed("R2-R4-R7"));
        assertFalse(CatanDice.isBoardStateWellFormed("J12"));
        assertFalse(CatanDice.isBoardStateWellFormed("G10"));


    }
}