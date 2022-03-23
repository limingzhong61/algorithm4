package com.mars.algorithms.chapter5_strings.chapter5_5;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

/**
 * 哈夫曼编码
 * 哈夫曼压缩
 */
public class Huffman {
	private static int R = 256; // ASCII字母表

	private static class Node implements Comparable<Node> {
		// 霍夫曼单词查找树中的结点
		private char ch; 		// 内部结点不会使用该变量
		//freq 的实例变量来表示以它为根结点的子树中的所有字符出现的频率
		// 展开过程不会使用该变量
		private int freq;
		private final Node left, right;

		Node(char ch, int freq, Node left, Node right) {
			this.ch = ch;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}

		@Override
		public int compareTo(Node that) {
			return this.freq - that.freq;
		}
	}

	public static void expand() {
		// 展开第i个编码所对应的字母
		Node root = readTrie();
		int N = BinaryStdIn.readInt();
		for (int i = 0; i < N; i++) {
			Node x = root;
			while (!x.isLeaf()) {
				if (BinaryStdIn.readBoolean()) {
					x = x.right;
				} else {
					x = x.left;
				}
			}
			BinaryStdOut.write(x.ch);
		}
		BinaryStdOut.close();
	}

	private static String[] buildCode(Node root) {
		// 使用单词查找树构造编译表
		String[] st = new String[R];
		buildCode(st, root, "");
		return st;
	}

	private static void buildCode(String[] st, Node x, String s) {
		// 使用单词查找树构造编译表（递归）
		if (x.isLeaf()) {
			st[x.ch] = s;
			return;
		}
		buildCode(st, x.left, s + '0');
		buildCode(st, x.right, s + '1');
	}

	private static Node buildTrie(int[] freq) {
		// 使用多棵单结点树初始化优先队列
		/**
		 * 动态排序，使用优先队列（堆排序）比较好
		 */
		MinPQ<Node> pq = new MinPQ<Node>();
		for (char c = 0; c < R; c++) {
			if (freq[c] > 0) {
				pq.insert(new Node(c, freq[c], null, null));
			}
		}

		while (pq.size() > 1) {
			// 合并两棵频率最小的树
			Node x = pq.delMin();
			Node y = pq.delMin();
			Node parent = new Node('\0', x.freq + y.freq, x, y);
			pq.insert(parent);
		}
		return pq.delMin();
	}

	private static void writeTrie(Node x) {
		// 输出单词查找树的比特字符串
		if (x.isLeaf()) {
			BinaryStdOut.write(true);
			BinaryStdOut.write(x.ch);
			return;
		}
		BinaryStdOut.write(false);
		writeTrie(x.left);
		writeTrie(x.right);
	}

	private static Node readTrie() {
		if (BinaryStdIn.readBoolean()) {
			return new Node(BinaryStdIn.readChar(), 0, null, null);
		}
		return new Node('\0', 0, readTrie(), readTrie());
	}

	public static void compress() {
		// 读取输入
		String s = BinaryStdIn.readString();
		char[] input = s.toCharArray();
		// 统计频率
		int[] freq = new int[R];
		for (int i = 0; i < input.length; i++) {
			freq[input[i]]++;
		}
		// 构造霍夫曼编码树
		Node root = buildTrie(freq);
		// （递归地）构造编译表
		String[] st = new String[R];
		buildCode(st, root, "");

		// （递归地）打印解码用的单词查找树
		writeTrie(root);

		// 打印字符总数
		BinaryStdOut.write(input.length);
		// 使用霍夫曼编码处理输入
		for (int i = 0; i < input.length; i++) {
			String code = st[input[i]];
			for (int j = 0; j < code.length(); j++) {
				if (code.charAt(j) == '1') {
					BinaryStdOut.write(true);
				} else {
					BinaryStdOut.write(false);
				}
			}
		}
		BinaryStdOut.close();
	}

	public static void main(String[] args) {
		if (args[0].equals("-")) {
			compress();
		}
		if (args[0].equals("+")) {
			expand();
		}
	}
}
