package com.mars.algorithms.chapter4_graph.chapter4_1;

/**
 * G 是二分图吗？（双色问题）
 * @author LiMingzhong
 */
public class TwoColor {
	private boolean[] marked;
	private boolean[] color;
	private boolean isTwoColorable = true;

	public TwoColor(Graph G) {
		marked = new boolean[G.V()];
		color = new boolean[G.V()];
		for (int s = 0; s < G.V(); s++) {
			if (!marked[s]) {
				dfs(G, s);
			}
		}
	}

	private void dfs(Graph G, int v) {
		marked[v] = true;
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				color[w] = !color[v];
				dfs(G, w);
				/*
					经过的结点，如果标记与当前结点相同则不能二分，
				 	当遍历全部没有问题后，就是二分图了
				*/
			} else if (color[w] == color[v]) {

				isTwoColorable = false;
			}
		}
	}

	public boolean isBipartite() {
		return isTwoColorable;
	}
}
