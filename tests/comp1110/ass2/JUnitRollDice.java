package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUnitRollDice {
    int[] list1 = {0,0,0,0,0,0};
    @Test
    void test1() {
        CatanDice.rollDice(3, list1);
        assertFalse((new int[]{0,0,0,0,0,0}).equals(list1) );
    }

    int[] list2 = {0,2,1,4,2,0};
    @Test
    void test2() {
        CatanDice.rollDice(2, list2);
        assertFalse((new int[]{0,2,1,4,2,0}).equals(list2) );
    }

    int[] list3 = {3,2,0,4,1,0};
    @Test
    void test3() {
        CatanDice.rollDice(1, list3);
        assertFalse((new int[]{3,2,0,4,1,0}).equals(list3) );
    }


    int[] list4 = {0,2,3,1,1,5};
    @Test
    void test4() {
        CatanDice.rollDice(1, list4);
        assertFalse((new int[]{0,2,3,1,1,5}).equals(list4) );
    }
}
