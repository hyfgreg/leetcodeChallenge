import java.util.LinkedList;
import java.util.Queue;

class TreeNode1 {
    private int value;
    private TreeNode1 left;
    private TreeNode1 right;

    public TreeNode1(int v) {
        value = v;
    }

    public void setLeft(TreeNode1 left) {
        this.left = left;
    }

    public void setRight(TreeNode1 right) {
        this.right = right;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TreeNode1 getLeft() {
        return left;
    }

    public TreeNode1 getRight() {
        return right;
    }

    public int getValue() {
        return value;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}

public class MinDepth {
    public static void main(String[] args) {
        TreeNode1 t1 = new TreeNode1(3);
        TreeNode1 t2 = new TreeNode1(9);
        TreeNode1 t3 = new TreeNode1(20);
        TreeNode1 t4 = new TreeNode1(15);
        TreeNode1 t5 = new TreeNode1(7);
        t1.setLeft(t2);
        t1.setRight(t3);
        t3.setLeft(t4);
        t3.setRight(t5);
        int minD = minDepth(t1);
        System.out.printf("min depth of this tree is %d", minD);
    }

    public static int minDepth(TreeNode1 root) {
        if (root == null) return 0;
        int depth = 0;
        Queue<TreeNode1> q = new LinkedList<>();
        q.offer(root);
        while (q.size() > 0) {
            int sz = q.size();
            depth += 1;
            for (int i = 0; i < sz; i++) {
                System.out.println("===================================================");
                TreeNode1 node = q.poll();
                System.out.printf("Current node is %d\n", node.getValue());
                if (node.isLeaf()) {
                    System.out.printf("Current node %d is leaf, current depth is %d, return\n", node.getValue(), depth);
                    return depth;
                }
                if (node.getLeft() != null) {
                    q.offer(node.getLeft());
                }
                if (node.getRight() != null) {
                    q.offer(node.getRight());
                }
            }
        }
        return depth;
    }
}