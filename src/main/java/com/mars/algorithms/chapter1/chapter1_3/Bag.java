package com.mars.algorithms.chapter1.chapter1_3;

import java.util.Iterator;

/**
 * @author Lenovo
 *
 * 背包是一种不支持从中删除元素的集合数据类型——它的
 * 目的就是帮助用例收集元素并迭代遍历所有收集到的元素（用
 * 例也可以检查背包是否为空或者获取背包中元素的数量
 */
public class Bag<Item> implements Iterable<Item> {
	private Node first;
	private int N;

	private class Node {
		Item item;
		Node next;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return N;
	}

	public void add(Item item) {
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		N++;
	}

	@Override
	public Iterator<Item> iterator() {
		return new ListIterator();
	}

	private class ListIterator implements Iterator<Item> {
		private Node current = first;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
		}
	}
}
