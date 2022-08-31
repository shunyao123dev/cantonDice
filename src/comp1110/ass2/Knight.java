package comp1110.ass2;

import static comp1110.ass2.ResourceType.ORE;

// This class authored by Hugo Heanly u7119555
public class Knight {

    //The value of the Knight
    public int value;

    //The resource of the Knight
    public ResourceType resource;

    //If the Knight is built
    public boolean built;

    //If the Knight is used
    public boolean used;


    public Knight(int value, ResourceType resource, boolean built, boolean used) {
        this.value = value;
        this.resource = resource;
        this.built = built;
        this.used = used;

    }


    // Returns the tile of a given structure
    public int getKnightValue() {return this.value;}

    // Returns the position of a given structure
    public ResourceType getKnightResource() {return this.resource;}

    // Returns the type of a given structure
    public boolean isKnightBuilt() {return this.built;}

    // Returns the position of a given structure
    public boolean isKnightUsed() {return this.used;}

    // Sets the resource of the knight according to the given
    // parameter

    public void setKnightResource(ResourceType resource) {this.resource = resource;}

    // Changes the knight to be built

    public void KnightNowBuilt() {this.built = true;}

    // Changes the knight to be used
    public void KnightNowUsed() {this.used = true;}

    // Returns whether the structure is a settlement


}

