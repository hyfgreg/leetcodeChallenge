import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class Permute {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        permute(nums);
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums, res, track);
        return res;
    }

    public static void backtrack(int[] nums, List<List<Integer>> res, LinkedList<Integer> track) {
        if (track.size() == nums.length) {
            res.add(new LinkedList<Integer>(track));
            System.out.println(track);
            return;
        }

        for (int num : nums) {
            if (track.contains(num)) continue;
            track.add(num);
            backtrack(nums, res, track);
            track.removeLast();
        }
    }


}
