package comp1110.ass2;

import java.util.ArrayList;

import static comp1110.ass2.StructureType.*;

/**
 * Author of class: Hugo Heanly u7119555
 */
public class Structure {

    // the position on the board
    public String position;

    // the structure type on the given tile and position
    public StructureType type;

    private ArrayList<String> prerequisites;

    // the value of the given Structure
    public int value;

    // if the structure is built
    public boolean built;

    // in the case of a knight, if the knight has been used

    public boolean used;

    // in the case of a knight, the type of resource

    public ResourceType resource;

    // Constructor for the Structure class that  allows specifying
    // for the structure's coordinates as well as its type (ROAD,
    // SETTLEMENT, CITY)

    public Structure(String position, StructureType type, int value, boolean built, boolean used, ResourceType resource, ArrayList<String> prerequisites) {
        this.position = position;
        this.type = type;
        this.value = value;
        this.built = built;
        this.used = used;
        this.resource = resource;
        this.prerequisites = prerequisites;
    }



    // Constructor for the Structure class that allows specifying
    // for the structure's position only


    //Return functions

    // Returns the position of a given structure
    public String getPosition() {return this.position;}

    // Returns the type of a given structure
    public StructureType getType() {return this.type;}

    // Returns the position of a given structure
    public int getValue() {return this.value;}

    // Returns if the structure is built
    public boolean isBuilt() {return this.built;}

    // Sets the type of the structure according to the given
    // parameter

    public void setBuilt() {this.built = true;}

    public void setType(StructureType type) {this.type = type;}

    // Sets the position of the structure according to the given
    // parameter

    public void setPosition(String position) {this.position = position;}

    public void setKnightUsed() {
        this.used = true;
    }


    // Returns whether the structure is the start
    public boolean isStart() {

        return type == START;
    }

    // Returns whether the structure is a road

    public boolean isRoad() {

        return type == ROAD;
    }

    // Returns whether the structure is a settlement

    public boolean isSettlement() {

        return type == SETTLEMENT;
    }

    // Returns whether the structure is a settlement

    public boolean isCity() {

        return type == CITY;
    }

    // Returns whether the structure is a knight

    public boolean isKnight() {

        return type == KNIGHT;




}}