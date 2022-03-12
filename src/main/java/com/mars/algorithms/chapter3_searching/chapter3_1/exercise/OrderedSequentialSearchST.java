package com.mars.algorithms.chapter3_searching.chapter3_1.exercise;

import java.util.LinkedList;
import java.util.NoSuchElementException;

//使用有序链表来实现我们的有序符号表API
// Exercise 3.1.3
public class OrderedSequentialSearchST<Key extends Comparable<Key>, Value> {
    private Node first;     // 链表首结点
    //private Node last;      //链表尾结点
    private int size = 0;

    private class Node {        // 链表结点的定义
        Key key;
        Value value;
        Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    //创建一张有序符号表
    public OrderedSequentialSearchST() {

    }

    /**
     * 将键值对存入表中（若值为空则将键key 从表中删除）
     * @param key
     * @param value
     */
    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (value == null) {
            delete(key);
            return;
        }
        //注意判断头结点是否为null
        if(first == null){
            first = new Node(key,value,null);
            size ++;
            return;
        }
        // < first,insert as head node
        if (first.key.compareTo(key) < 0) {
            this.first = new Node(key, value, this.first);
        }

        Node p = first;
        while (p.next != null) {
            //find,更新
            if (p.next.key.equals(key)) {
                p.value = value;
                return;
            } else if (p.next.key.compareTo(key) < 0) {
                // < key
                Node newNode = new Node(key, value, p.next);
                p.next = newNode;
                size++;
                return;
            }
            p = p.next;
        }
        // 比所有节点都大
        Node newNode = new Node(key, value, null);
        p.next = newNode;
        size++;
    }

    public Value get(Key key) {
        for (Node p = first; p.next != null && p.key.compareTo(key) < 0; p = p.next) {
            if (key.equals(p.key)) {
                return p.value;
            }
        }
        // not find
        return null;
    }

    // 不完美，需改善（有序）
    public void delete(Key key) {
        if (isEmpty()) {
            throw new NoSuchElementException("ST underflow error");
        }
        first = delete(first, key);
    }

    //递归形式删除元素
    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }
        if (x.key.equals(key)) {
            size--;
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }


    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("ST underflow error");
        }
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("ST underflow error");
        }
        return first.key;
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("ST underflow error");
        }
        Node p = this.first;
        while (p.next != null) {
            p = p.next;
        }
        return p.key;
    }

    /**
     * largest key less than or equal to key
     * @param key
     * @return
     */
    public Key floor(Key key) {
        if (isEmpty()) {
            throw new NoSuchElementException("ST underflow error");
        }
        Node p = this.first;
        while (p.next != null) {
            if (p.next.key.compareTo(key) > 0) {
                return p.key;
            }
            p = p.next;
        }
        return p.key; //尾结点
    }

    /**
     * smallest key greater than or equal to key
     * @param key
     * @return
     */
    public Key ceiling(Key key) {
        if (isEmpty()) {
            throw new NoSuchElementException("ST underflow error");
        }
        Node p = this.first;
        while (p.next != null) {
            if (p.key.compareTo(key) >= 0) {
                return p.key;
            }
        }
        //尾结点
        return null;
    }

    public int rank(Key key) {
        Node p = this.first;
        int cnt = 0;
        while (p.key.compareTo(key) < 0 && p.next != null) {
            cnt++;
            p.next = p;
        }
        return cnt;
    }

    /**
     * @param k [0,size)
     * @return
     */
    public Key select(int k) {
        if (k >= size || k < 0) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        }
        Node p = this.first;
        //int cnt = 0;
        for (; k > 0 && p.next != null; k--) {
            p = p.next;
        }
        return p.key;
    }

    /**
     * 删除最小的键
     */
    void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("ST underflow error");
        }
        Node p = first;
        first = first.next;
        p.key = null;
        p.value = null;
        p = null;
        size--;
    }

    /**
     * 删除最大的键
     */
    void deleteMax() {
        Node p = this.first;
        if(p.next != null){ //不止一个结点
            while(p.next.next != null){ // 找到尾指针的前一个结点才能删除
                p = p.next;
            }
        }
        Node x = p.next;
        p.next = null;
        x.key = null;
        x.value = null;
        x = null;
    }

    /**
     *
     * @param lo
     * @param hi
     * @return  [lo..hi] 之间键的数量
     */
    int size(Key lo, Key hi) {
        Node p = this.first;
        int cnt = 0;
        for( ;p.key.compareTo(hi) <= 0 && p.next != null; p= p.next){
            if(p.key.compareTo(lo) >= 0 && p.key.compareTo(hi)>= 0){
                cnt++;
            }
        }
        return  cnt;
    }

    /**
     *
     * @param lo
     * @param hi
     * @return  [lo..hi] 之间的所有键，已排序
     */
    Iterable<Key> keys(Key lo, Key hi){
        if (isEmpty()) {
            throw new NoSuchElementException("ST underflow error");
        }
        LinkedList<Key> linkedList = new LinkedList<>();
        Node p = this.first;
        for( ;p.key.compareTo(hi) <= 0 && p.next != null; p= p.next){
            if(p.key.compareTo(lo) >= 0 && p.key.compareTo(hi)>= 0){
                linkedList.add(p.key);
            }
        }
        return linkedList;
    }

    /**
     *
     * @return 表中的所有键的集合，已排序
     */
    Iterable<Key> keys(){
        if (isEmpty()) {
            throw new NoSuchElementException("ST underflow error");
        }
        LinkedList<Key> linkedList = new LinkedList<>();
        Node p = this.first;
        for( ; p.next != null; p= p.next){
                linkedList.add(p.key);
        }
        return linkedList;
    }
}
