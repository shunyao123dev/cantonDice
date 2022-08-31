package comp1110.ass2;

// This class authored by Hugo Heanly u7119555
public class Resource {

    //The type of Resource for the given tile
    public ResourceType type;

    //Has the resource been used
    public boolean used;

    public Resource (ResourceType type, boolean used) {
        this.type = type;
        this.used =used;
    }


    // Returns the resource of a given tile
    public ResourceType getResource() {return this.type;}

    // Returns if the resource of a given tile has been used
    public boolean isUsed() {return this.used;}

    // Sets the resource type of a given tile with a given parameter
    public void setType(ResourceType type) {this.type = type;}

    // Sets the used boolean to true, indicating the resource has been used
    public void setUsed(boolean used) {this.used = used;}
}
