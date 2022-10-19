package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import comp1110.ass2.Player;

class BoardtoStringTest {
    Player testPlayer  = new Player("Player 1");



    @Test
    void testSet1() {
        int[] a1 = {1,0,1,0,0,2};
        int[] a2 = {0,1,1,1,2,1};
        int[] a3 = {1,0,1,1,0,2};
        assertFalse(CatanDice.canDoSwap(3,1,"R0,R1,R2,R3,J1,J2",a1));
        assertFalse(CatanDice.canDoSwap(2,0,"R0,R1,R2,R3,K1",a2));
        assertTrue(CatanDice.canDoSwap(2,0,"R0,R1,R2,R3,J1",a2));
        assertTrue(CatanDice.canDoSwap(3,1,"R0,R1,R2,R3,J1,J2",a3));
    }

    @Test
    void testSet2() {
        int[] a4 = {1,0,1,1,0,1};
        assertFalse(CatanDice.canDoSwap(3,2,"R0,R1,R2,R3,J1,J2",a4));
        assertFalse(CatanDice.canDoSwap(2,6,"R0,S3,R1,R2,R3,J1,J2,J3,J4,J5",a4));
        assertTrue(CatanDice.canDoSwap(3,2,"R0,R1,R2,R3,J1,J2,J3",a4));
        assertTrue(CatanDice.canDoSwap(2,5,"R0,S3,R1,R2,R3,J1,J2,J3,J4,J5,J6",a4));
    }
}