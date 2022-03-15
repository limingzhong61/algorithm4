package com.mars.algorithms.chapter4_graph.chapter4_1;

/**
 * G 是无环图吗？（假设不存在自环或平行边）
 * @author LiMingzhong
 */
public class Cycle {
	private boolean[] marked;
	private boolean hasCycle;

	public Cycle(Graph G) {
		marked = new boolean[G.V()];
		for (int s = 0; s < G.V(); s++) {
			if (!marked[s]) {
				dfs(G, s, s);
			}
		}
	}

	/**
	 *
	 * @param G
	 * @param v
	 * @param u v的父节点()，即dfs先u后v
	 */
	private void dfs(Graph G, int v, int u) {
		marked[v] = true;
		for (int w : G.adj(v)) { // w是v的邻接顶点（子节点），即dfs先v后w
			if (!marked[w]) {
				dfs(G, w, v);
			} else if (w != u) { // w不是u（u->v,v->w ,w!=u，则不是递归回退），则被访问了两次（有环）
				hasCycle = true;
			}
		}
	}

	public boolean hasCycle() {
		return hasCycle;
	}
}
