package com.mars.algorithms.chapter3_searching.chapter3_2;

import com.mars.algorithms.chapter1.chapter1_3.Queue;
import edu.princeton.cs.algs4.StdOut;

//基于二叉查找树的符号表
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;                // 二叉查找树的根结点

    private class Node {
        private Key key;        // 键
        private Value val;        // 值
        private Node left, right;        // 指向子树的链接
        private int N;                    // 以该结点为根的子树中的结点总数

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            this.N = N;
        }

    }


    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.N;
        }
    }

    public Value getRecur(Key key) {
        return getRecur(root, key);
    }

    private Value getRecur(Node x, Key key) {
        // 在以x为根结点的子树中查找并返回key所对应的值；
        // 如果找不到则返回null
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return getRecur(x.left, key);
        } else if (cmp > 0) {
            return getRecur(x.right, key);
        } else {
            return x.val;
        }
    }

    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) return x.val;
            else if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
        }
        return null;
    }

    /**
     * Ex 3.2.13
     *
     * @param key
     * @param val
     */
    public void put(Key key, Value val) {
        Node x = root;
        Node z = new Node(key, val);

        if (x == null) {
            root = z;
            return;
        }

        Node parent = null;
        while (x != null) {
            parent = x;
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                x.val = val;
            } else if (cmp < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        int cmp = key.compareTo(parent.key);
        if (cmp < 0) {
            parent.left = z;
        } else {
            parent.right = z;
        }
    }

    public void putRecur(Key key, Value val) {
        // 查找key，找到则更新它的值，否则为它创建一个新的结点
        root = putRecur(root, key, val);
    }

    private Node putRecur(Node x, Key key, Value val) {
        // 如果key存在于以x为根结点的子树中则更新它的值；
        // 否则将以key和val为键值对的新结点插入到该子树中
        if (x == null) {
            return new Node(key, val, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = putRecur(x.left, key, val);
        } else if (cmp > 0) {
            x.right = putRecur(x.right, key, val);
        } else {
            x.val = val;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        return max(x.right);
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) {
            return null;
        }
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp < 0) {
            return floor(x.left, key);
        }
        Node t = floor(x.right, key);
        if (t != null) {
            return t;
        } else {
            return x;
        }
    }

    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x == null) {
            return null;
        }
        return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp > 0) {
            return floor(x.right, key);
        }
        Node t = floor(x.left, key);
        if (t != null) {
            return t;
        } else {
            return x;
        }
    }

    public Key select(int k) {
        return select(root, k).key;
    }

    private Node select(Node x, int k) {
        // 返回排名为k的结点
        if (x == null) {
            return null;
        }
        int t = size(x.left);
        if (t > k) {
            return select(x.left, k);
        } else if (t < k) {
            return select(x.right, k - t - 1);
        } else {
            return x;
        }
    }

    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        // 返回以x为根结点的子树中小于x.key的键的数量
        if (x == null) {
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return rank(key, x.left);
        } else if (cmp > 0) {
            return 1 + size(x.left) + rank(key, x.right);
        } else {
            return size(x.left);
        }
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) {
            return;
        }
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) {
            keys(x.left, queue, lo, hi);
        }
        if (cmplo <= 0 && cmphi >= 0) {
            queue.enqueue(x.key);
        }
        if (cmphi > 0) {
            keys(x.right, queue, lo, hi);
        }
    }

    /**
     * Exercise 3.2.6
     *
     * @return height of bs tree
     */
    public int height() {
        return height(root);
    }

    /**
     * Exercise 3.2.6
     *
     * @return height of bs tree
     */
    private int height(Node p) {
        if (p == null) {
            return 0;
        }
        return Math.max(height(p.right), height(p.left)) + 1;
    }


    // Exercise 3.2.6, 高度少了1
    public int heightOther() {
        return heightOther(root);
    }

    private int heightOther(Node x) {
        if (x == null) {
            return -1;
        }
        return 1 + Math.max(heightOther(x.left), heightOther(x.right));
    }

    // Exercise 3.2.32
    private boolean isBST() {
        return isBST(root, null, null);
    }

    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) {
            return true;
        }
        if (min != null && x.key.compareTo(min) <= 0) {
            return false;
        }
        if (max != null && x.key.compareTo(max) >= 0) {
            return false;
        }
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    /**
     * Exercise 3.2.33
     *
     * @return
     */
    public boolean isRankConsistent() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }
        for (Key key : keys()) {
            if (!key.equals(select(rank(key)))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String str = "H C S A E X R";
        String[] arr = str.split(" ");

        BST<String, Integer> ST = new BST<>();

        for (int i = 0; i < arr.length; i++) {
            ST.putRecur(arr[i], i);
        }

        StdOut.println(ST.height());
        System.out.println("heightOther:" + ST.heightOther());
        str = "A B C D E F G";
        arr = str.split(" ");
        ST = new BST<>();

        for (int i = 0; i < arr.length; i++) {
            ST.putRecur(arr[i], i);
        }

        StdOut.println(ST.height());
    }
}
