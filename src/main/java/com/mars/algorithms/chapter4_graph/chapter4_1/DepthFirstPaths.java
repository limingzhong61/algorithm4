package com.mars.algorithms.chapter4_graph.chapter4_1;

import com.mars.algorithms.chapter1.chapter1_3.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 使用深度优先搜索查找图中的路径
 * @author LiMingzhong
 */
public class DepthFirstPaths {
	// 这个顶点上调用过dfs()了吗？
	private boolean[] marked;
	// 从起点到一个顶点的已知路径上的最后一个顶点
    //以顶点编号为索引的数组edgeTo[]，edgeTo[w]=v 表示v-w 是第一次访问w 时经过的边。
    //edgeTo[] 数组是一棵用父链接表示的以s 为根且含有所有与s 连通的顶点的树。
	private int[] edgeTo;
	private final int s;

	public DepthFirstPaths(Graph G, int s) {
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		this.s = s;
		dfs(G, s);
	}

	private void dfs(Graph G, int v) {
		marked[v] = true;
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				edgeTo[w] = v;
				dfs(G, w);
			}
		}
	}

	public boolean hasPathTo(int v) {
		return marked[v];
	}

	public Iterable<Integer> pathTo(int v) {
		if (!hasPathTo(v)) {
			return null;
		}
		Stack<Integer> path = new Stack<Integer>();
		for (int x = v; x != s; x = edgeTo[x]) {
			path.push(x);
		}
		path.push(s);
		return path;
	}

	public static void main(String[] args) {
		// vertex size
		Graph G = new Graph(new In(args[0]));
		// edge size
		int s = Integer.parseInt(args[1]);
		DepthFirstPaths search = new DepthFirstPaths(G, s);
		for (int v = 0; v < G.V(); v++) {
			StdOut.print(s + " to " + v + ": ");
			if (search.hasPathTo(v)) {
				for (int x : search.pathTo(v)) {
					if (x == s) {
						StdOut.print(x);
					} else {
						StdOut.print("-" + x);
					}
				}
			}
			StdOut.println();
		}
	}
}
