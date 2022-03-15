package com.mars.algorithms.chapter4_graph.chapter4_1;

import com.mars.algorithms.chapter1.chapter1_3.Bag;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 使用深度优先搜索找出图中的所有连通分量
 * @author LiMingzhong
 */
public class CC {
	private boolean[] marked;
	private int[] id;
	private int count;

	public CC(Graph G) {
		marked = new boolean[G.V()];
		id = new int[G.V()];
		for (int s = 0; s < G.V(); s++) {
			if (!marked[s]) {
				dfs(G, s);
				count++;
			}
		}
	}

	private void dfs(Graph G, int v) {
		marked[v] = true;
		id[v] = count;
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				dfs(G, w);
			}
		}
	}

	public boolean connected(int v, int w) {
		return id[v] == id[w];
	}

	public int id(int v) {
		return id[v];
	}

	public int count() {
		return count;
	}

	public static void main(String[] args) {
		Graph G = new Graph(new In(args[0]));
		CC cc = new CC(G);

		int M = cc.count();
		StdOut.println(M + " components");

		Bag<Integer>[] components = (Bag<Integer>[]) new Bag[M];
		for (int i = 0; i < M; i++) {
			components[i] = new Bag<Integer>();
		}
		for (int v = 0; v < G.V(); v++) {
			components[cc.id(v)].add(v);
		}
		for (int i = 0; i < M; i++) {
			for (int v : components[i]) {
				StdOut.print(v + " ");
			}
			StdOut.println();
		}
	}
}
