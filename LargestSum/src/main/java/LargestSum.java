
import java.util.List;

public class LargestSum {
    /**
     * Get the largest possible sum that can be obtained from a pair of values in the list. A number can't be added
     * to itself, unless there are duplicates.
     *
     * @param nums a list of ints.
     * @return the largest possible sum of separate numbers from nums.
     */
    public int bigSum(List<Integer> nums) {
        if (nums.size() == 1 ) return nums.get(0);
        if (nums.size() == 2) return nums.get(0) + nums.get(1);
        
        int i = 2;
        int x = 0;
        int y = 1;

        while (i < nums.size()) {
            if (nums.get(y) < nums.get(i)) {
                if (nums.get(y) > nums.get(x)) x = y;
                y = i;
            }
            i++;
        }

        return nums.get(x) + nums.get(y);
    }
}