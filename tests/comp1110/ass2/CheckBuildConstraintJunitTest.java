package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckBuildConstraintJunitTest {

    @Test
    void checkRoads() {
        assertFalse(CatanDice.checkBuildConstraints("R1","R0,S3,R2,R3,R4"));
        assertFalse(CatanDice.checkBuildConstraints("R7","R0,S3,R1,C7,R2,R3"));
        assertTrue(CatanDice.checkBuildConstraints("R4","R0,S3,R1,C7,R2,R3,S4"));
        assertTrue(CatanDice.checkBuildConstraints("R7","R0,R1,C7,R2,R3,R4,C12,R5,R6"));
    }

    @Test
    void checkCity() {
        assertFalse(CatanDice.checkBuildConstraints("C12","R0,S3,R1,R2,R3,R4"));
        assertFalse(CatanDice.checkBuildConstraints("C12","R0,S3,R1,C7,R2,R3,R4,R5,S4,S5,R6,R7,R12,R13,C20,J1"));
        assertFalse(CatanDice.checkBuildConstraints("C12","R0,S3,R1,R2,R3,C7,J1,J2,J3"));
        assertTrue(CatanDice.checkBuildConstraints("C7","R0,S3,R1,R2,J1,K2"));
        assertTrue(CatanDice.checkBuildConstraints("C12","R0,R1,R2,C7,R3,R4"));
    }

    @Test
    void checkKnights() {
        assertFalse(CatanDice.checkBuildConstraints("J5","R0,S3,R1,R2,J1,J2"));
        assertFalse(CatanDice.checkBuildConstraints("J2","R0,R1,R2,J1,J4"));
        assertFalse(CatanDice.checkBuildConstraints("J2","R0,S3,R1,C7,R2"));
        assertTrue(CatanDice.checkBuildConstraints("J3","R0,R1,C7,R2,J1,J2"));
        assertTrue(CatanDice.checkBuildConstraints("J1","R0,S3,R1,C7,R2,R3"));
    }

    @Test
    void chekcSeatlements() {
        assertFalse(CatanDice.checkBuildConstraints("S5","R0,R1,C7,R2,R3,R4,R5,C12,R5"));
        assertFalse(CatanDice.checkBuildConstraints("S5","R0,R1,S3,R2,R3,R5,C7"));
        assertFalse(CatanDice.checkBuildConstraints("S4","R0,S3,R1,C7,J1,J2,K3"));
        assertTrue(CatanDice.checkBuildConstraints("S7","R0,S3,R1,R2,R3,R4,S4,R5,S5,R6,R7,J1,K2"));
        assertTrue(CatanDice.checkBuildConstraints("S4","R0,S3,R1,C7,J1,J2,J3,R2"));
    }

}