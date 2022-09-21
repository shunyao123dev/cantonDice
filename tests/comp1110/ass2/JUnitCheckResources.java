package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class JUnitCheckResources {


    @Test
    void checkRoads() {
        assertFalse(CatanDice.checkResources("R1", new int[]{1, 0, 1, 2, 0, 2}));
        assertFalse(CatanDice.checkResources("R2", new int[]{1, 0, 1, 0, 2, 2}));
        assertFalse(CatanDice.checkResources("R3", new int[]{1, 0, 1, 1, -2, 2}));
        assertFalse(CatanDice.checkResources("R4", new int[]{1, 0, 1, -1, 2, 2}));
        assertTrue(CatanDice.checkResources("R5", new int[]{1, 0, 1, 1, 1, 2}));
        assertTrue(CatanDice.checkResources("R6", new int[]{1, 0, 1, 2, 8, 2}));

    }

    @Test
    void checkKnight() {
        assertFalse(CatanDice.checkResources("J1", new int[]{0, 0, 1, 2, 0, 2}));
        assertFalse(CatanDice.checkResources("J2", new int[]{0, 1, 0, 0, 2, 2}));
        assertFalse(CatanDice.checkResources("J3", new int[]{1, 0, 0, 1, -2, 2}));
        assertFalse(CatanDice.checkResources("J4", new int[]{1, 0, 1, -1, 2, 2}));
        assertTrue(CatanDice.checkResources("J5", new int[]{1, 1, 1, 1, 1, 2}));
        assertTrue(CatanDice.checkResources("J5", new int[]{4, 1, 1, 2, 8, 2}));

    }


    @Test
    void checkSettlement() {
        assertFalse(CatanDice.checkResources("S3", new int[]{0, 0, 1, 2, 0, 2}));
        assertFalse(CatanDice.checkResources("S4", new int[]{0, 1, 0, 0, 2, 2}));
        assertFalse(CatanDice.checkResources("S5", new int[]{1, 0, 0, 1, -2, 2}));
        assertFalse(CatanDice.checkResources("S7", new int[]{1, 0, 1, -1, 2, 2}));
        assertTrue(CatanDice.checkResources("S9", new int[]{1, 1, 1, 1, 1, 2}));
        assertTrue(CatanDice.checkResources("S11", new int[]{4, 1, 1, 2, 8, 2}));

    }


    @Test
    void checkCity () {
        assertFalse(CatanDice.checkResources("C7", new int[]{0, 0, 1, 2, 0, 2}));
        assertFalse(CatanDice.checkResources("C7", new int[]{0, 1, 0, 0, 2, 2}));
        assertFalse(CatanDice.checkResources("C12", new int[]{1, 0, 0, 1, -2, 2}));
        assertFalse(CatanDice.checkResources("C12", new int[]{-1, 0, 1, -1, 2, 2}));
        assertTrue(CatanDice.checkResources("C30", new int[]{3, 2, 1, 1, 1, 2}));
        assertTrue(CatanDice.checkResources("C30", new int[]{4, 2, 1, 2, 8, 2}));

    }

}
