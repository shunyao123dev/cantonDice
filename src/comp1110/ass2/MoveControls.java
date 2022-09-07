package comp1110.ass2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class MoveControls {
    /**
     * Roll the specified number of dice and add the result to the
     * resource state.
     *
     * The resource state on input is not necessarily empty. This
     * method should only _add_ the outcome of the dice rolled to
     * the resource state, not remove or clear the resources already
     * represented in it.
     *
     * @param n_dice: The number of dice to roll (>= 0).
     * @param resource_state: The available resources that the dice
     *        roll will be added to.
     *
     * This method does not return any value. It should update the given
     * resource_state.
     */
    public static void rollDice(int n_dice, int[] resource_state) {
        Random rad = new Random();
        String[] res = {"ore","grain","wool","lumber","brick","gold"};
        HashMap<String,Integer> resource = new HashMap<>();
        for (int i=1;i<=n_dice;i++) {
            int num = rad.nextInt(6);
            String s1 = res[num];
            if(!(resource.containsKey(s1))) {
                resource.put(s1,1);
            } else{
                resource.put(s1,resource.get(s1)+1);
            }
        }

        for (String i1: resource.keySet()) {
            for(int i=0;i<res.length;i++) {
                if(i1.equals(res[i])) {
                    resource_state[i] = resource_state[i]+resource.get(i1);
                }
            }
        }
        // FIXME: Task #6
    }
}


