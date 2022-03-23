package com.mars.algorithms.chapter6_context.chapter3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * KWIC 类（见下
 * 页框注）会为命令行参数指定
 * 的文本构造后缀数组，从标准
 * 输入接受查询并打印出被查询
 * 的子字符串在文本中的上下文（该字符串的前后若干个字符）。KWIC 这个名字表示的是上下文中
 * 的关键词（keyword-in-context）查找，
 */
public class KWIC {
	public static void main(String[] args) {
		In in = new In(args[0]);
		int context = Integer.parseInt(args[1]);

		String text = in.readAll().replaceAll("\\s+", " ");
		int N = text.length();
		SuffixArray sa = new SuffixArray(text);

		while (StdIn.hasNextLine()) {
			String q = StdIn.readLine();
			for (int i = sa.rank(q); i < N && sa.select(i).startsWith(q); i++) {
				int from = Math.max(0, sa.index(i) - context);
				int to = Math.min(N - 1, from + q.length() + 2 * context);
				StdOut.println(text.substring(from, to));
			}
			StdOut.println();
		}
	}
}
