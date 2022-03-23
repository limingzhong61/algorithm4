package com.mars.algorithms.chapter5_strings.chapter5_3_substring_search;

import edu.princeton.cs.algs4.StdOut;

/**
 * Knuth-Morris-Pratt 字符串查找算法
 */
public class KMP {
    private String pat;
    private int[][] dfa;

    public KMP(String pat) {
        // 由模式字符串构造DFA
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            // 计算dfa[][j]
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X]; // 复制匹配失败情况下的值
            dfa[pat.charAt(j)][j] = j + 1; // 设置匹配成功情况下的值
            X = dfa[pat.charAt(j)][X]; // 更新重启状态
        }
    }

    public int search(String txt) {
        // 在txt上模拟DFA的运行
        int i, j, N = txt.length(), M = pat.length();
        for (i = 0, j = 0; i < N && j < M; i++)
            j = dfa[txt.charAt(i)][j];
        if (j == M) return i - M; // 找到匹配（到达模式字符串的结尾）
        else return N; // 未找到匹配（到达文本字符串的结尾）
    }

    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];
        KMP kmp = new KMP(pat);
        StdOut.println("text:    " + txt);
        int offset = kmp.search(txt);
        StdOut.print("pattern: ");
        for (int i = 0; i < offset; i++) {
            StdOut.print(" ");
        }
        StdOut.println(pat);
    }
}
