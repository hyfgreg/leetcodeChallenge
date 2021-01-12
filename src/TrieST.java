import javax.swing.*;
import java.util.Arrays;

public class TrieST<Value> {
    private static int R = 256;  // ascii码范围
    private Node root;

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", next=" + Arrays.toString(next) +
                    '}';
        }
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
//        StdOut.println("x.val " + x.val);
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) {
        // d表示第key的第几个字母
        if (x == null) {
//            StdOut.println("get null, key " + key + " d " + d);
            return null;
        }
        ;
        if (d == key.length()) {
//            StdOut.println("get key " + key + " d " + d);
            return x;
        }
        char c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }

    public void put(String key, Value val) {
        // put(root, key, val, 0); 只写成这样是不行的！！！put后，root还是null
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) {
            x = new Node();
        }
        ;
        if (d == key.length()) {
            x.val = val;
//            StdOut.println("key " + key + " x.val " + x.val + " d " + d);
            return x;
        }
        char c = key.charAt(d);
//        StdOut.println("key " + key + " d " + d + " index " + c);
        x.next[c] = put(x.next[c], key, val, d + 1);
//        StdOut.println("x.next[c] "+x.next[c]);
        return x;
    }


    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new Queue<>();
        collect(get(root, pre, 0), pre, q);
        return q;
    }

    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) return;
        if (x.val != null) q.enqueue(pre);
        for (char c = 0; c < R; c++) {
            collect(x.next[c], pre + c, q);
        }
    }

    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new Queue<String>();
        collect(root, "", pat, q);
        return q;
    }

    private void collect(Node x, String pre, String pat, Queue<String> q) {
        int d = pre.length();
        if (x == null) return;
        if (d == pat.length() && x.val != null) q.enqueue(pre);
        if (d == pat.length()) return;

        char next = pat.charAt(d);
        for (char c = 0; c < R; c++) {
            if (next == '.' || next == c) {
                collect(x.next[c], pre + c, pat, q);
            }
        }
    }

    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length) {
        if (x == null) return length; // 不包含s中第d个字符...s[d-1]
        if (x.val != null) length = d; // 在逐个查找s中字符的过程中如果有单词存在，就更新length
        if (d == s.length()) return length;  // 整个s都找完了
        char c = s.charAt(d);  // 按照s的第d个字符往下查找
        return search(x.next[c], s, d + 1, length);
    }

    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
//        if (d > s.length()) return x;
        if (d == key.length()) {
            x.val = null;
            for (char c = 0; c < R; c++) {
                if (x.next[c] != null) return x;
            }
            return null;
        }
        char next = key.charAt(d);
        x.next[next] = delete(x.next[next], key, d + 1);

        if (x.val != null) return x;

        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) return x;
        }
        return null;
    }

    public static void main(String[] args) {
        String[] a = {"she", "sells", "shells", "by", "the", "sea", "shore"};
        TrieST<Integer> tst = new TrieST<>();
//        StdOut.println("tst.root is null, " + (tst.root == null));
        for (int i = 0; i < a.length; i++) {
            tst.put(a[i], i);
        }
//        StdOut.println("tst.root is null, " + (tst.root == null));

//        String[] tests = {"he", "buys", "shells", "near", "shore"};
//        for (String t : tests) {
//            StdOut.println(t + " " + tst.get(t));
//        }

//        for (String c : tst.keysWithPrefix("se")) {
//            StdOut.println(c);
//        }

//        for (String c : tst.keysThatMatch("...")) {
//            StdOut.println(c);
//        }

        for (String c : tst.keys()) {
            StdOut.println(c);
        }

        StdOut.println("=====================================");

        tst.delete("a");
        for (String c : tst.keys()) {
            StdOut.println(c);
        }

        StdOut.println("=====================================");

        tst.delete("shells");
        for (String c : tst.keys()) {
            StdOut.println(c);
        }

        StdOut.println("=====================================");

        tst.delete("she");
        for (String c : tst.keys()) {
            StdOut.println(c);
        }
    }
}

