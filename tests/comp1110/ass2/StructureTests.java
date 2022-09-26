package comp1110.ass2;

import org.junit.jupiter.api.Test;
import comp1110.ass2.Structure;

import static comp1110.ass2.StructureType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class StructureTests {

    @Test
    void constructorTest() {
        Structure road = new Structure("R0", StructureType.ROAD, 1, false, false, ResourceType.NIL);
        Structure settlement = new Structure("S3", StructureType.SETTLEMENT, 3, false, false, ResourceType.NIL);
        Structure city = new Structure("C7", StructureType.CITY, 7, false, false, ResourceType.NIL);
        Structure knight = new Structure("J1", StructureType.KNIGHT, 1, true, false, ResourceType.ORE);

        assertNotEquals(road, null, "Expected to be non-null object, but got null");
        assertEquals(settlement.position, "S3");
        assertEquals(settlement.type, StructureType.SETTLEMENT);
        assertEquals(settlement.value, 3);
        assertFalse(settlement.built);
        assertFalse(settlement.used);
        assertEquals(settlement.resource, ResourceType.NIL);

        settlement.setPosition("S4");
        city.setType(StructureType.START);

        assertEquals(settlement.position, "S4");
        assertEquals(city.type, StructureType.START);
        assertTrue(city.isStart());

        city.setType(CITY);

        assertEquals(knight.getPosition(), "J1");

        knight.setBuilt();

        assertTrue(knight.isBuilt());
        assertTrue(road.isRoad());
        assertTrue(settlement.isSettlement());
        assertTrue(city.isCity());
        assertTrue(knight.isKnight());

    }


}