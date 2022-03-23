package com.mars.algorithms.chapter5_strings.chapter5_4_regular_expression;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * regular_expression
 * 正则表达式的模式匹配（grep）
 */
public class GREP {
	public static void main(String[] args) {
		String regexp = "(.*" + args[0] + ".*)";
		NFA nfa = new NFA(regexp);
		while (StdIn.hasNextLine()) {
			String txt = StdIn.readLine();
			if (nfa.recognizes(txt)) {
				StdOut.println(txt);
			}
		}
	}
}
/*
"(A*B|AC)D"
AC
AD
AAA
ABD
ADD
BCD
ABCCBD
BABAAA
BABBAAA
output:
"(A*B|AC)D"
ABD
ABCCBD
* */
