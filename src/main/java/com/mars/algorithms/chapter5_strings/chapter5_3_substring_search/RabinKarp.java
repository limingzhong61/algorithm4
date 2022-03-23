package com.mars.algorithms.chapter5_strings.chapter5_3_substring_search;

import edu.princeton.cs.algs4.StdOut;

import java.math.BigInteger;
import java.util.Random;

/**
 * Rabin-Karp 指纹字符串查找算法
 */
public class RabinKarp {
	private String pat; // 模式字符串（仅拉斯维加斯算法需要）
	private long patHash; // 模式字符串的散列值
	private int M; // 模式字符串的长度
	private long Q; // 一个很大的素数
	private int R = 256; // 字母表的大小
	private long RM; // R^(M-1) % Q

	public RabinKarp(String pat) {
		this.pat = pat; // 保存模式字符串（仅拉斯维加斯算法需要）
		this.M = pat.length();
		Q = longRandomPrime(); // 请见练习5.3.33
		RM = 1;
		for (int i = 1; i <= M - 1; i++) {			// 计算R^(M-1) % Q
			RM = (R * RM) % Q;						// 用于减去第一个数字时的计算
		}
		patHash = hash(pat, M);
	}

	public boolean check(int i) { // 蒙特卡洛算法（请见正文）
		return true;			// 对于拉斯维加斯算法，检查模式与txt(i..i-M+1)的匹配
	}

	private long hash(String key, int M) {
		// 计算key[0..M-1]的散列值
		long h = 0;
		for (int j = 0; j < M; j++) {
			h = (R * h + key.charAt(j)) % Q;
		}
		return h;
	}

	public int search(String txt) {
		// 在文本中查找相等的散列值
		int N = txt.length();
		long txtHash = hash(txt, M);
		if (patHash == txtHash && check(0)) {			// 一开始就匹配成功
			return 0;
		}
		for (int i = M; i < N; i++) {			// 减去第一个数字，加上最后一个数字，再次检查匹配
			txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
			txtHash = (txtHash * R + txt.charAt(i)) % Q;
			if (patHash == txtHash) {
				if (check(i - M + 1)) {			// 找到匹配
					return i - M + 1;
				}
			}
		}
		return N;				// 未找到匹配
	}

	/**
	 * Exercise 5.3.33
	 * 
	 * @return
	 */
	private static long longRandomPrime() {
		BigInteger prime = BigInteger.probablePrime(31, new Random());
		return prime.longValue();
	}

	public static void main(String[] args) {
		String pat = args[0];
		String txt = args[1];
		RabinKarp rk = new RabinKarp(pat);
		StdOut.println("text:    " + txt);
		int offset = rk.search(txt);
		StdOut.print("pattern: ");
		for (int i = 0; i < offset; i++) {
			StdOut.print(" ");
		}
		StdOut.println(pat);
	}
}
