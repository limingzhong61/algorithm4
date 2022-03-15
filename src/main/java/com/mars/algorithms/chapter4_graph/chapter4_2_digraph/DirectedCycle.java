package com.mars.algorithms.chapter4_graph.chapter4_2_digraph;

import com.mars.algorithms.chapter1.chapter1_3.Stack;

/**
 * @author LiMingzhong
 */
public class DirectedCycle {
	private boolean[] marked;
	private int[] edgeTo;
	private Stack<Integer> cycle; // 有向环中的所有顶点（如果存在），记录有向环上的顶点
	/*
	递归调用的栈上的所有顶点
	(在调用dfs(G,v) 时将onStack[v] 设为true，在调用结束时将其设为false）
	* */
	private boolean[] onStack;

	public DirectedCycle(Digraph G) {
		onStack = new boolean[G.V()];
		edgeTo = new int[G.V()];
		marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); v++) {
			if (!marked[v]) {
				dfs(G, v);
			}
		}
	}

	private void dfs(Digraph G, int v) {
		onStack[v] = true;
		marked[v] = true;
		for (int w : G.adj(v)) {
			if (this.hasCycle()) {
				return;
			} else if (!marked[w]) {
				edgeTo[w] = v;
				dfs(G, w);
			} else if (onStack[w]) {    // 存在环
				// 以下步骤记录环
				cycle = new Stack<Integer>();
				for (int x = v; x != w; x = edgeTo[x]) {
					cycle.push(x);
				}
				/*
					一旦我们找到了一条有向边v → w 且w 已经存在于栈中，就找到了一个环，
				因为栈表示的是一条由w 到v 的有向路径，而v → w 正好补全了这个环
				**/
				cycle.push(w);
				cycle.push(v);
			}
		}
		onStack[v] = false;
	}

	public boolean hasCycle() {
		return cycle != null;
	}

	public Iterable<Integer> cycle() {
		return cycle;
	}
}
