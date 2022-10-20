package comp1110.ass2;

/**
 * Author of class: Hugo Heanly u7119555
 */
public class ResourceState {

    //The type of Resource for the given tile
    public int[] state;

    public ResourceState() {
        this.state = new int[]{0, 0, 0, 0, 0, 0};
    }

    public void changeResourceState(int[] resources) {
        this.state = resources;
    }

    public int[] getResourceState() {
        return this.state;
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

    public void changeResourceWithDice(int[] die) {
        int oreCount = countDice(0, die);
        int grainCount = countDice(1, die);
        int woolCount = countDice(2, die);
        int timberCount = countDice(3, die);
        int brickCount = countDice(4, die);
        int goldCount = countDice(5, die);

        for (int i = 0; i < 6; i ++) {
            if (i == 0) {
                this.state[0] = oreCount;
            } else if (i == 1) {
                this.state[1] = grainCount;
            } else if (i == 2) {
                this.state[1] = woolCount;
            } else if (i == 3) {
                this.state[1] = timberCount;
            } else if (i == 4) {
                this.state[1] = brickCount;
            } else {
                this.state[1] = goldCount;
            }
        }
    }

    public int countDice(int resource, int[] die) {
        int count = 0;
        for (int i = 0; i < die.length; i++) {
            if (die[i] == resource) {
                count+=1;
            }
        }
        return count;
    }


}
