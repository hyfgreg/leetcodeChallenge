import java.util.*;
import java.util.Queue;
import edu.princeton.cs.algs4.StdOut;

class TwoSum1 {
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int leftValue = target - nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == leftValue) {
                    res[0] = i;
                    res[1] = j;
                    return res;
                }
            }
        }
        return res;
    }
}

class TwoSumUpdate {
    /*
    return value not index
    */
    public int[] twoSum(int[] nums, int target) {
        Arrays.sort(nums);
        int i = 0, j = nums.length - 1;
        int[] res = new int[2];
        while (i < j) {
            if (nums[i] + nums[j] == target) {
                res[0] = nums[i];
                res[1] = nums[j];
                return res;
            } else {
                ++i;
                ++j;
            }
        }
        return res;
    }
}

class MinimumDepthOfBinaryTree111 {
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        LinkedList<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int depth = 1;
        while (q.size() != 0) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                TreeNode node = q.poll();
                if (node.left == null && node.right == null) return depth;
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            depth++;
        }
        return depth;
    }
}

class OpenTheLock752 {
    //  todo
}


class ThreeSum15 {
    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> res = new ThreeSum15().threeSum(nums);
        StdOut.println(res);
    }

    public List<List<Integer>> threeSum(int[] nums) {
        return threeSum(nums, 0);
    }

    private List<List<Integer>> threeSum(int[] nums, int target) {
        Arrays.sort(nums);
        StdOut.println(Arrays.toString(nums));
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; ) {
            StdOut.printf("nums[%d]: %d", i, nums[i]);
            StdOut.println();
            List<List<Integer>> tmp = twoSum(Arrays.copyOfRange(nums, i + 1, nums.length), target - nums[i]);
            StdOut.println(tmp);
            if (tmp.size() > 0) {
                for (List<Integer> j : tmp) {
                    j.add(nums[i]);
                    res.add(j);
                }
            }
            int val = nums[i];
            while (i < nums.length && nums[i] == val) i++; // todo i的变化由这里控制！
        }
        return res;
    }

    private List<List<Integer>> twoSum(int[] nums, int target) {
        StdOut.println("target:" + target);
//        Arrays.sort(nums);
        int lo = 0, hi = nums.length - 1;
        List<List<Integer>> res = new ArrayList<>();
        while (lo < hi) {
            int left = nums[lo], right = nums[hi];
            int sum = left + right;
            if (sum == target) {
                StdOut.println("left:" + left + " right: " + right);
                List<Integer> ans = new ArrayList<>();
                ans.add(left);
                ans.add(right);
                res.add(ans);
                while (lo < hi && nums[lo] == left) lo++;
                while (lo < hi && nums[hi] == right) hi--;
            } else if (sum < target) {
                while (lo < hi && nums[lo] == left) lo++;
            } else {
                while (lo < hi && nums[hi] == right) hi--;
            }
        }
        return res;
    }
}

class BinaryTreeInorderTraversal94 {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        inorderTraversal(root, res);
        return res;
    }

    private void inorderTraversal(TreeNode node, List<Integer> res) {
        if (node == null) return;
        inorderTraversal(node.left, res);
        res.add(node.val);
        inorderTraversal(node.right, res);
    }
}

class UniqueBinarySearchTrees98 {
    // TODO DP, 也可以用递归来做
    static Map<Integer, Integer> memo = new HashMap<>();

    public int numTrees(int n) {
        return helper(1, n);
    }

    private int helper(int begin, int end) {
        if (memo.containsKey(end - begin)) return memo.get(end - begin);
        if (begin > end) return 1;
        int sum = 0;
        for (int i = begin; i <= end; i++) {
            int left = helper(begin, i - 1);
            int right = helper(i + 1, end);
            sum += (left * right);
        }
        memo.put(end - begin, sum);
        return sum;
    }
}

class ValidateBinarySearchTree98 {
    // 递归，root.val > 左子树的最大值， 同理， root.val < 右子树的最小值
    // 二叉搜索树的中序遍历为升序，+ 以上思路，中序遍历，对比上一个值和当前值的大小

    public static void main(String[] args) {
        TreeNode root = new TreeNode(0);
        StdOut.println(new ValidateBinarySearchTree98().isValidBST(root));
    }

    double minValue = -Double.MAX_VALUE;

    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        boolean left = isValidBST(root.left);
        if (!left) return false;
        if (minValue >= root.val) return false;
        minValue = root.val;
        return isValidBST(root.right);
    }

    public boolean isValidBSTSlow(TreeNode root) {
        if (root == null) return true;
        LinkedList<Integer> list = new LinkedList<>();
        traverse(root, list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) <= list.get(i - 1)) return false;
        }
        return true;
    }

    public void traverse(TreeNode node, LinkedList<Integer> res) {
        if (node == null) return;
        traverse(node.left, res);
        res.add(node.val);
        traverse(node.right, res);
    }
}

class SymmetricTree101 {
    // 还可以用BFS，遍历每一层的节点，然后看该层节点组成的array是否回文array

    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return cmp(root.left, root.right);
    }

    private boolean cmp(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        } else if (node1 == null) {
            return false;
        } else if (node2 == null) {
            return false;
        }
        if (node1.val != node2.val) return false;
        return cmp(node1.left, node2.right) && cmp(node1.right, node2.left);
    }
}

class BinaryTreeLevelOrderTraversal02 {
    // 本质就是个BFS
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) return res;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int sz = q.size();
            List<Integer> level = new LinkedList<>();
            for (int i = 0; i < sz; i++) {
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            res.add(level);
        }
        return res;
    }
}

class MaximumDepthofBinaryTree104 {
    public int maxDepth(TreeNode root) {
        return maxDepth(root, 0);
    }

    private int maxDepth(TreeNode node, int depth) {
        if (node == null) return depth;
        int leftDepth = maxDepth(node.left);
        int rightDepth = maxDepth(node.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }
}

class RemoveCoveredIntervals1288 {
    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });

//        int left = intervals[0][0];
        int right = intervals[0][1];
        int res = 0;

        int sz = intervals.length;
        for (int i = 1; i < sz; i++) {
            if (intervals[i][1] <= right) {
                res++;
            } else if (right >= intervals[i][0]) {
                right = intervals[i][1];
            } else {
//                left = intervals[i][0];
                right = intervals[i][1];
            }
        }
        return sz - res;
    }
}

class MergeIntervals56 {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });
        int coveredIndex = 0;
        int right = intervals[0][1];
        int res = 0;

        int sz = intervals.length;
        for (int i = 1; i < sz; i++) {
            if (intervals[i][1] <= right) {
                continue;
            } else if (right >= intervals[i][0]) {
                right = intervals[i][1];
                intervals[coveredIndex][1] = right;
            } else {
                right = intervals[i][1];
                ++coveredIndex;
                intervals[coveredIndex][0] = intervals[i][0];
                intervals[coveredIndex][1] = intervals[i][1];
            }
        }
        return Arrays.copyOf(intervals, coveredIndex + 1);
    }
}

class FindPivotIndex724 {
    public int pivotIndex(int[] nums) {
        if (nums.length == 0) return -1;
        int left = 0;
        int right = 0;
        for (int i = 1; i < nums.length; i++) right += nums[i];
        if (left == right) return 0;
        for (int i = 1; i < nums.length; i++) {
            left += nums[i - 1];
            right -= nums[i];
            if (left == right) return i;
        }
        return -1;
    }

//    private int mySum(int[] nums, int begin, int end) {
//        if (begin >= end) return 0;
//        int ret = 0;
//        for (int i = begin; i < end; i++) {
//            ret += nums[i];
//        }
//        return ret;
//    }
}


class IntervalListIntersections986 {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        if (firstList.length == 0) {
            return firstList;
        } else if (secondList.length == 0) {
            return secondList;
        }

        int firstLen = firstList.length;
        int secondLen = secondList.length;

        int[][] res = new int[firstLen * secondLen][2];
        int index = 0;

        int i = 0, j = 0;
        while (i < firstLen && j < secondLen) {
            if (firstList[i][0] <= secondList[j][0] && firstList[i][1] >= secondList[j][0]) {
                res[index][0] = secondList[j][0];
                if (firstList[i][1] <= secondList[j][1]) {
                    res[index][1] = firstList[i][1];
                    i++;
                } else {
                    res[index][1] = secondList[j][1];
                    j++;
                }
                index++;
            } else if (firstList[i][0] >= secondList[j][0] && firstList[i][0] <= secondList[j][1]) {
                res[index][0] = firstList[i][0];
                if (firstList[i][1] >= secondList[j][1]) {
                    res[index][1] = secondList[j][1];
                    j++;
                } else {
                    res[index][1] = firstList[i][1];
                    i++;
                }
                index++;
            } else if (firstList[i][1] < secondList[j][0]) {
                i++;
            } else {
                j++;
            }
        }
        return Arrays.copyOf(res, index);
    }
}

class PathWithMinimumEffort1631 {
    Queue<LinkedList<int[]>> paths = new LinkedList<>();
    LinkedList<LinkedList<int[]>> res = new LinkedList<>();

    public static void main(String[] args) {
//        int[][] heights = {{1,2,1,1,1}, {1,2,1,2,1}, {1,2,1,2,1},{1,2,1,2,1},{1,1,1,2,1}};
        int[][] heights = {{3}};
        new PathWithMinimumEffort1631().minimumEffortPath(heights);
    }

    public int minimumEffortPath(int[][] heights) {
        if (heights.length == 0) return 0;
        int[] start = {0, 0};
        LinkedList<int[]> node = new LinkedList<>();
        node.add(start);
        paths.add(node);
        while (paths.size() != 0) {
            int sz = paths.size();
            for (int i = 0; i < sz; i++) {
                LinkedList<int[]> path = paths.poll();
                move(path, heights);
            }
        }
        int ret = Integer.MAX_VALUE;
        StdOut.println(res.size());
        for (LinkedList<int[]> path : res) {
            int tmp = 0;
            for (int i = 1; i < path.size(); i++) {
                int cost = Math.abs(heights[path.get(i)[0]][path.get(i)[1]] - heights[path.get(i - 1)[0]][path.get(i - 1)[1]]);
                if (cost > tmp) tmp = cost;
            }

            if (tmp < ret) ret = tmp;
        }
        StdOut.println(ret);
        return ret;
    }

    private void move(LinkedList<int[]> path, int[][] heights) {
        int[] current = path.getLast();
        if (done(current, heights)) {
            res.add(path);
            return;
        }
        // 上
        int[] up = {current[0] - 1, current[1]};
        if (up[0] >= 0 && !contain(path, up)) {
            LinkedList<int[]> newPath = deepCopy(path);
            newPath.add(up);
            if (done(up, heights)) {
                res.add(newPath);
            } else {
                paths.add(newPath);
            }
        }

        // 右
        int[] right = {current[0], current[1] + 1};
        if (right[1] < heights[0].length && !contain(path, right)) {
            LinkedList<int[]> newPath = deepCopy(path);
            newPath.add(right);
            if (done(right, heights)) {
                res.add(newPath);
            } else {
                paths.add(newPath);
            }
        }

        // 下
        int[] down = {current[0] + 1, current[1]};
        if (down[0] < heights.length && !contain(path, down)) {
            LinkedList<int[]> newPath = deepCopy(path);
            newPath.add(down);
            if (done(down, heights)) {
                res.add(newPath);
            } else {
                paths.add(newPath);
            }
        }

        // 左
        int[] left = {current[0], current[1] - 1};
        if (left[1] >= 0 && !contain(path, left)) {
            LinkedList<int[]> newPath = deepCopy(path);
            newPath.add(left);
            if (done(left, heights)) {
                res.add(newPath);
            } else {
                paths.add(newPath);
            }
        }
    }

    private boolean done(int[] node, int[][] heights) {
        return node[0] == heights.length - 1 && node[1] == heights[0].length - 1;
    }

    private boolean contain(LinkedList<int[]> path, int[] node) {
        for (int[] x : path) {
            if (x[0] == node[0] && x[1] == node[1]) return true;
        }
        return false;
    }

    private LinkedList<int[]> deepCopy(LinkedList<int[]> src) {
        LinkedList<int[]> dst = new LinkedList<>();
        for (int[] node : src) {
            int[] tmp = {node[0], node[1]};
            dst.add(tmp);
        }
        return dst;
    }
}

class SerializeAndDeserializeBinaryTree297 {

    // Encodes a tree to a single string.
    static String nullVal = "null";

    public String serialize(TreeNode root) {
        // 序列化的时候注意负数，所以最好用一个符号分割各个数字
        if (root == null) return nullVal;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        StringBuilder res = new StringBuilder();
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                TreeNode node = q.poll();
                if (node == null) {
                    res.append(nullVal);
                    res.append(",");
                } else {
                    res.append(node.val);
                    res.append(",");
                    q.offer(node.left);
                    q.offer(node.right);
                }
            }
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        // 下面的都可以
        if (data.equals(nullVal)) return null;
        String[] nodes = data.split(",");
        int index = 1, len = nodes.length;
        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
//        TreeNode parent = root;
//        boolean isLeft = true;
//        for (; index < len; index++) {
//            String val = nodes[index];
//            TreeNode node = getNode(val);
//            if (isLeft) {
//                parent.left = node;
//            } else {
//                parent.right = node;
//            }
//            if (node != null) q.offer(node);
//            isLeft = !isLeft;
//            if (isLeft) {
//                parent = q.poll();
//            }
//        }
//        return root;
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                TreeNode node = q.poll();
                if (index >= len) break;
                if (!nodes[index].equals(nullVal)) {
                    TreeNode left = new TreeNode(Integer.parseInt(nodes[index]));
                    node.left = left;
                    q.offer(left);
                }
                index++;
                if (index >= len) break;
                if (!nodes[index].equals(nullVal)) {
                    TreeNode right = new TreeNode(Integer.parseInt(nodes[index]));
                    node.right = right;
                    q.offer(right);
                }
                index++;
            }
        }
        return root;
    }

    private TreeNode getNode(String val) {
        StdOut.println("val: " + val);
        if (val.equals(nullVal)) return null;
        return new TreeNode(Integer.parseInt(val));
    }

    private TreeNode deserialize(String data, int index) {
        if (index >= data.length()) return null;
        if (data.charAt(index) == nullVal.charAt(0)) return null;
        TreeNode node = new TreeNode((int) data.charAt(index) - (int) ('0'));
        node.left = deserialize(data, 2 * index + 1);
        node.right = deserialize(data, 2 * index + 2);
        return node;
    }
}

class PathSumIII437 {
    public static void main(String[] args) {
        String data = "5,4,8,11,null,13,4,7,2,null,null,5,1";
        SerializeAndDeserializeBinaryTree297 codec = new SerializeAndDeserializeBinaryTree297();
        TreeNode root = codec.deserialize(data);
        PathSumIII437 ps = new PathSumIII437();
        ps.pathSum(root, 22);
    }

    public static int count = 0;

    public int pathSum(TreeNode root, int sum) {
        // 可以用一个hashmap把所有的和的结果存起来，然后再返回ret.get(sum,0)，这样存储空间就是O(N)
        _pathSum(root, sum);
        return count;
    }

    private int[] _pathSum(TreeNode root, int sum) {
        if (root == null) return new int[0];
        int[] left = _pathSum(root.left, sum);
        int[] right = _pathSum(root.right, sum);
        int[] ret = new int[left.length + right.length + 1];
        for (int i = 0; i < left.length; i++) {
            ret[i] = left[i] + root.val;
            if (ret[i] == sum) {
                count++;
                System.out.println(count);
            }
        }
        for (int j = 0; j < right.length; j++) {
            ret[j + left.length] = right[j] + root.val;
            if (ret[j + left.length] == sum) {
                count++;
                StdOut.println(count);
            }
        }
        if (root.val == sum) {
            count++;
            StdOut.println(count);
        }
        ret[ret.length - 1] = root.val;
        return ret;
    }
}

class ConvertBSTToGreaterTree538_ {
    public TreeNode convertBST(TreeNode root) {
        convertBST(root, 0);
        return root;
    }

    private int convertBST(TreeNode root, int sum) {
        if (root == null) return sum;
        int ret = convertBST(root.right, sum);
        root.val += ret;
        return convertBST(root.left, root.val);
    }
}

class DiameterOfBinaryTree543 {
    static private int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        diameter = 0;
        helper(root);
        return diameter - 1;
    }

    private int helper(TreeNode root) {
        if (root == null) return 0;
        int leftMax = helper(root.left);
        int rightMax = helper(root.right);
        diameter = Math.max(diameter, leftMax + rightMax + 1);
        return Math.max(leftMax + 1, rightMax + 1);
    }
}

class MergeTwoBinaryTrees617 {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return null;
        int val = 0;
        if (t1 != null) val += t1.val;
        if (t2 != null) val += t2.val;
        TreeNode node = new TreeNode(val);
        if (t1 != null && t2 != null) {
            node.left = mergeTrees(t1.left, t2.left);
            node.right = mergeTrees(t1.right, t2.right);
        } else if (t1 != null) {
            node.left = mergeTrees(t1.left, null);
            node.right = mergeTrees(t1.right, null);
        } else {
            node.left = mergeTrees(null, t2.left);
            node.right = mergeTrees(null, t2.right);
        }
        return node;
    }

//    private TreeNode mergeTrees(TreeNode node, TreeNode t1, TreeNode t2) {
//        if (t1 == null && t2 == null) return null;
//    }
}