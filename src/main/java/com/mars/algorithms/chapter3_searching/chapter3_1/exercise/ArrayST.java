package com.mars.algorithms.chapter3_searching.chapter3_1.exercise;

import java.util.Arrays;

/*
Mars
 */
//（无序）数组实现 SymbolTable
public class ArrayST<Key, Value> {
    private Key[] keys;
    private Value[] values;
    private int size;

    //创建一张符号表
    public ArrayST(int capacity) {
        // 调整数组大小的标准代码请见算法1.1
        keys = (Key[]) new Object[capacity];
        values = (Value[]) new Object[capacity];
        size = 0;
    }


    public void put(Key key, Value value) {
        if (value == null) {
            delete(key);
        }
        for (int i = 0; i < size; i++) {
            if (keys[i] == key) { // mapping
                values[i] = value;
                return;
            }
        }
        // not mapping
        keys[size] = key;
        values[size] = value;
        size++;
    }

    private Value get(Key key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) { // 对象比较使用equals()
                return values[i];
            }
        }
        return null; // not find
    }

    private void delete(Key key) {
        int i = 0;
        for (; i < size; i++) {
            if (keys[i].equals(key)) {
                break;
            }
        }
        if (i != size) { //find key
            for (; i < size - 1; i++) {
                keys[i] = keys[i + 1];
            }
        }
        //else{
        //
        //}
    }

    public void deleteOther(Key key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                keys[i] = keys[size - 1];
                values[i] = values[size - 1];
                keys[size - 1] = null;
                values[size - 1] = null;
                size--;
                return;
            }
        }
    }

    boolean contains(Key key) {
        return get(key) == null;
    }

    boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public Iterable<Key> keys() {
        //ArrayList<Key> queue = new ArrayList<>();
        return Arrays.asList(keys);
    }

    public static void main(String[] args) {

    }
}
