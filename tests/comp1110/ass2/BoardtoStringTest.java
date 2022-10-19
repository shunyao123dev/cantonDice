package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import comp1110.ass2.Player;

class BoardToStringTest {
    Player testPlayer = new Player("Player 1");

    public Board testBoard1() {
        Board currentBoard = new Board();
        currentBoard.buildStructures("R0,R1,R2,R3,J1,J2");
        return currentBoard;
    }

    public Board testBoard2() {
        Board currentBoard = new Board();
        currentBoard.buildStructures("R0,R1,R2,R3,R4,R5,R6,R7,R8," +
                "R9,R10,R11,R12,R13,R14,R15,S3," +
                "S4,S5,S7,S9,S11,C7,C12,C20," +
                "C30,J1,J2,J3,J4,J5,J6,K1,K2,K3,K4,K5,K6");
        return currentBoard;
    }

    Board testBoard1 = testBoard1();
    Board testBoard2 = testBoard2();
    String testString1 = "R0,R1,R2,R3,J1,J2";
    String testString1Result = testBoard1.boardToString(testBoard1.stringToBoard(testString1));


    @Test
    void testSet1() {
        assertTrue(testBoard1.boardToString(testBoard1).equals("R0,R1,R2,R3,J1,J2"), "Expected R0,R1,R2,R3,J1,J2 but got " + testBoard1.boardToString(testBoard1));
        assertTrue(testString1.equals(testString1Result), "Expected R0,R1,R2,R3,J1,J2 but got " + testString1Result);
    }
}