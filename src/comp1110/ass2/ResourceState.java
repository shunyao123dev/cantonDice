package comp1110.ass2;

// This class authored by Hugo Heanly u7119555
public class ResourceState {

    //The type of Resource for the given tile
    public int[] state;

    public ResourceState(int[] state) {
        this.state = state;
    }

    // Initialises the resource state to zer

    public void initialiseState() {
        this.state = new int[]{0, 0, 0, 0, 0, 0};
    }

    //Update Resource State with by given parameters

    public void changeResource(ResourceType type, int num) {
        switch (type) {
            case ORE -> this.state[0] = num;
            case GRAIN -> this.state[1] = num;
            case WOOL -> this.state[2] = num;
            case TIMBER -> this.state[3] = num;
            case BRICK -> this.state[4] = num;
            case GOLD -> this.state[5] = num;
        }
    }
}
