package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class isActionWellFormedTest {

    @Test
    void checkCorrectActions() {
        assertTrue(CatanDice.isActionWellFormed("build R13"));
        assertTrue(CatanDice.isActionWellFormed("build J4"));
        assertTrue(CatanDice.isActionWellFormed("swap 0 2"));
        assertTrue(CatanDice.isActionWellFormed("swap 0 4"));
        assertTrue(CatanDice.isActionWellFormed("swap 1 2"));
        assertTrue(CatanDice.isActionWellFormed("swap 1 3"));
        assertTrue(CatanDice.isActionWellFormed("swap 3 0"));
        assertTrue(CatanDice.isActionWellFormed("swap 3 3"));
    }

    @Test
    void checkIncorrectActions() {
        assertFalse(CatanDice.isActionWellFormed("uild R2"));
        assertFalse(CatanDice.isActionWellFormed("rtade R3"));
        assertFalse(CatanDice.isActionWellFormed("5 4 swap"));
        assertFalse(CatanDice.isActionWellFormed("build r2"));
        assertFalse(CatanDice.isActionWellFormed("build R(3)"));
        assertFalse(CatanDice.isActionWellFormed("buildR2"));
        assertFalse(CatanDice.isActionWellFormed("f2 build"));
        assertFalse(CatanDice.isActionWellFormed("build 12F"));

    }
//    @Test


}