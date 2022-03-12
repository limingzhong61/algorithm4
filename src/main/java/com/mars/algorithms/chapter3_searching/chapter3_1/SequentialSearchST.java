package com.mars.algorithms.chapter3_searching.chapter3_1;

import com.mars.algorithms.chapter1.chapter1_3.Queue;

public class SequentialSearchST<Key, Value> {
	private Node first;			// 链表首结点
	private int N = 0;

	private class Node {		// 链表结点的定义
		Key key;
		Value val;
		Node next;

		public Node(Key key, Value val, Node next) {
			this.key = key;
			this.val = val;
			this.next = next;
		}
	}

	public Value get(Key key) {
		// 查找给定的键，返回相关联的值
		for (Node x = first; x != null; x = x.next) {
			if (key.equals(x.key)) {
				return x.val;		// 命中
			}
		}
		return null;				// 未名中
	}

	public void put(Key key, Value val) {
		// 查找给定的键，找到则更新其值，否则在表中新建结点
		for (Node x = first; x != null; x = x.next) {
			if (key.equals(x.key)) {
				x.val = val;
				return;
			}			// 命中，更新
		}
		first = new Node(key, val, first);		// 未命中，新建结点
		N++;
	}

	// Exercise 3.1.5
	public int size() {
		return N;
	}

	public void delete(Key key) {
		first = delete(first, key);
	}

	private Node delete(Node x, Key key) {
		if (x == null) {
			return null;
		}
		if (x.key.equals(key)) {
			N--;
			return x.next;
		}
		x.next = delete(x.next, key);
		return x;
	}

	public Iterable<Key> keys() {
		Queue<Key> queue = new Queue<>();
		for (Node x = first; x != null; x = x.next) {
			queue.enqueue(x.key);
		}
		return queue;
	}
}
