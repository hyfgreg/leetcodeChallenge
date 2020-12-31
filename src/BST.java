import java.util.LinkedList;
import java.util.Queue;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int N; // 以该节点为根的子树中的节点总数

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    public void put(Key key, Value val) {
        // 查找key，找到则更新它的值，否则为它创建一个新的节点;
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        // 如果key存在，则更新它的val
        // 如果key不存在，以key和val为键值对的新节点插入到该子树中
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val); // 更新自己的左子树
        else if (cmp > 0) x.right = put(x.right, key, val); // 更新自己的右子树
        else x.val = val; // 更新自己的val
        x.N = size(x.left) + size(x.right) + 1; // 根据自己的新的左/右子树更新自己的N
        return x;
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    public Key floor(Key key) {
        // <= key
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return floor(x.left, key);
        } else if (cmp > 0) {
            Node tmp = floor(x.right, key);
            return tmp == null ? x : tmp;
        } else {
            // cmp == 0
            return x;
        }
    }

    public Key ceiling(Key key) {
        Node t = ceiling(root, key);
        return t == null ? null : t.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            return ceiling(x.right, key);
        } else if (cmp < 0) {
            Node tmp = ceiling(x.left, key);
            return tmp == null ? x : tmp;
        } else {
            return x;
        }
    }

    public Key select(int k) {
        if (k > size(root)) return null;
        return select(root, k).key;
    }

    private Node select(Node x, int k) {
        int leftSize = size(x.left);
        if (k < leftSize) return select(x.left, k);
        else if (k > leftSize) return select(x.right, k - leftSize - 1);
        return x;
    }

    public int rank(Key key) {
        if (size(root) == 0) return 0;
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return size(x.left);
        } else if (cmp < 0) {
            return rank(x.left, key);
        }
        return size(x.left) + 1 + rank(x.right, key);
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        // 深入根节点的左子树中，直到遇到一个空连接，返回它的右链接，让它的父节点的左链接指向这个右链接
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.left = t.left;
            x.right = deleteMin(t.right);
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    private void print(Node x) {
        if (x == null) return;
        print(x.left);
        System.out.println(x.val);
        print(x.right);
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new LinkedList<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.add(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }
}
