import java.util.*;

class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode next;

    public TreeNode(int v) {
        val = v;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public void setValue(int val) {
        this.val = val;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public int getValue() {
        return val;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val=" + val +
                ", left=" + left +
                ", right=" + right +
                ", next=" + next +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return val == treeNode.val && Objects.equals(left, treeNode.left) && Objects.equals(right, treeNode.right) && Objects.equals(next, treeNode.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, left, right, next);
    }

    public static TreeNode buildTree(String[] nums) {
        if (nums.length == 0) return null;
        Queue<TreeNode> q = new LinkedList<>();
        String val = nums[0];
        TreeNode root = new TreeNode(Integer.parseInt(val));
        q.offer(root);
        int index = 1;
        while (q.size() != 0) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                TreeNode node = q.poll();
                if (index < nums.length) {
                    String leftVal = nums[index];
                    if (!leftVal.equals("null")) {
                        TreeNode left = new TreeNode(Integer.parseInt(leftVal));
                        node.left = left;
                        q.offer(left);
                    }
                    index++;
                }
                if (index < nums.length) {
                    String rightVal = nums[index];
                    if (!rightVal.equals("null")) {
                        TreeNode right = new TreeNode(Integer.parseInt(rightVal));
                        node.right = right;
                        q.offer(right);
                    }
                    index++;
                }
            }
        }
        return root;
    }

    public static void preOrder(TreeNode root) {
        if (root == null) return;
        System.out.println(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }

    public static void inOrder(TreeNode root) {
        if (root == null) return;
        inOrder(root.left);
        System.out.println(root.val);
        inOrder(root.right);
    }
}

class TestBuildTree {
    public static void main(String[] args) {
        String[] test = {"5", "3", "6", "2", "4", "null", "null", "1"};
        TreeNode root = TreeNode.buildTree(test);
        TreeNode.preOrder(root);
    }
}

public class TreeNeverEnds {
    public static void main(String[] args) {

    }

    public static TreeNode InvertBinaryTree226(TreeNode root) {
        /*

         */
        if (root == null) return root;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        if (root.left != null) InvertBinaryTree226(root.left);
        if (root.right != null) InvertBinaryTree226(root.right);
        return root;
    }

    public static TreeNode PopulatingNextRightPointersInEachNode116(TreeNode root) {
        if (root == null) return root;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (q.size() > 0) {
            int sz = q.size();
            TreeNode next = null;
            for (int i = 0; i < sz; i++) {
                TreeNode node = q.poll();
                node.next = next;
                next = node;
                if (node.right != null) q.add(node.right);
                if (node.left != null) q.add(node.left);
            }
        }
        return root;
    }

    public static void FlattenBinaryTreeToLinkedList114(TreeNode root) {
        if (root == null) return;
        FlattenBinaryTreeToLinkedList114(root.left);
        FlattenBinaryTreeToLinkedList114(root.right);
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = null;
        root.right = left;

        TreeNode p = root;
        // TreeNode p = left; // left == null时，要额外处理
        while (p.right != null) {
            p = p.right;
        }
        p.right = right;
    }
}

class ConstructBinaryTreeFromPreorderAndInorderTraversal105 {
    public static void main(String[] args) {
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        TreeNode root = buildTree(preorder, inorder);
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        return build(preorder, inorder, 0, 0, inorder.length);
    }

    public static TreeNode build(int[] preorder, int[] inorder, int preLeft, int inLeft, int inRight) {
        System.out.printf("preLeft %d, inLeft %d, inRight %d\n", preLeft, inLeft, inRight);
        if (inLeft == inRight) {
            System.out.println("inLeft " + inLeft + " == inRight " + inRight + " return null");
            return null;
        }
        ;
        int rootVal = preorder[preLeft];
        int rootValInIndex = -1;
        for (int i = inLeft; i < inRight; i++) {
            if (inorder[i] == rootVal) {
                rootValInIndex = i;
                break;
            }
        }
        System.out.println("rootValInIndex " + rootValInIndex);
        System.out.println("root val " + rootVal);
        TreeNode root = new TreeNode(rootVal);
        System.out.println("rootVal " + rootVal + " build left");
        TreeNode left = build(preorder, inorder, preLeft + 1, inLeft, rootValInIndex);
        System.out.println("rootVal " + rootVal + " build right");
        TreeNode right = build(preorder, inorder, preLeft + rootValInIndex + 1 - inLeft, rootValInIndex + 1, inRight);
        root.left = left;
        root.right = right;
        return root;
    }
}

class ConstructBinaryTreeFromInorderAndPostorderTraversal106 {
    public static void main(String[] args) {
        int[] inorder = {9, 3, 15, 20, 7};
        int[] postorder = {9, 15, 7, 20, 3};
        buildTree(inorder, postorder);
    }

    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        return build(inorder, 0, inorder.length, postorder, 0, postorder.length);
    }

    public static TreeNode build(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd) {
        if (inStart >= inEnd) return null;
        System.out.printf("inStart %d, inEnd %d, postStart %d, postEnd %d\n", inStart, inEnd, postStart, postEnd);
        int rootVal = postorder[postEnd - 1];
        int index = 0;
        for (int i = inStart; i < inEnd; i++) {
            if (inorder[i] == rootVal) {
                index = i;
                break;
            }
        }
        int left_size = index - inStart;
        TreeNode root = new TreeNode(rootVal);
        TreeNode left = build(inorder, inStart, index, postorder, postStart, postStart + left_size);
        TreeNode right = build(inorder, index + 1, inEnd, postorder, postStart + left_size, postEnd - 1);
        root.left = left;
        root.right = right;
        return root;
    }
}

class FindDuplicateSubtrees {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        root.left = left;
        root.right = right;

        TreeNode leftLeft = new TreeNode(4);
        left.left = leftLeft;

        TreeNode rightLeft = new TreeNode(2);
        right.left = rightLeft;

        TreeNode rightLeftLeft = new TreeNode(4);
        right.left.left = rightLeftLeft;

        TreeNode rightRight = new TreeNode(4);
        right.right = rightRight;
        List<TreeNode> result = findDuplicateSubtrees(root);
        System.out.println(result.toString());

    }

    public static List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        HashMap<String, Integer> subTree = new HashMap<>();
        List<TreeNode> result = new ArrayList<>();
        help(root, subTree, result);
        return result;
    }

    public static String help(TreeNode node, HashMap<String, Integer> subTree, List<TreeNode> result) {
        if (node == null) return "#";
        String leftString = help(node.left, subTree, result);
        String rightString = help(node.right, subTree, result);
        String nodeSubTree = leftString + "," + rightString + "," + node.val;
        System.out.println("node: " + node.val + " , subTree: " + nodeSubTree);
        int count = subTree.getOrDefault(nodeSubTree, 0);
        if (count == 1) {
            result.add(node);
            subTree.put(nodeSubTree, count + 1);
        } else {
            subTree.put(nodeSubTree, count + 1);
        }
        return nodeSubTree;
    }
}

/*
二叉搜索树，左儿子小于自己，右儿子大于自己
中序遍历可以升序打印所有的节点
*
 */

class KthSmallestElementInABST230 {
    public static void main(String[] args) {
        String[] test = {"5", "3", "6", "2", "4", "null", "null", "1"};
        TreeNode root = TreeNode.buildTree(test);
        int k = 3;
        int val = kthSmallest(root, k);
        System.out.println(k + "th smallest num: " + val);
    }

    public static int kthSmallest(TreeNode root, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        traverse(root, res);
        System.out.println(res.toString());
        for (int i = 1; i <= res.size(); i++) {
            if (i == k) return res.get(i - 1);
        }
        return -1;
    }

    public static void traverse(TreeNode root, ArrayList<Integer> res) {
        if (root == null) return;
        traverse(root.left, res);
        res.add(root.val);
        traverse(root.right, res);
    }
}

class ConvertBSTToGreaterTree538 {
    int sum = 0;
    public TreeNode convertBST(TreeNode root) {
        traverse(root);
        return root;
    }

    void traverse(TreeNode root) {
        if (root == null) return;
        traverse(root.right);
        sum += root.val;
        root.val = sum;
        traverse(root.left);
    }
}
