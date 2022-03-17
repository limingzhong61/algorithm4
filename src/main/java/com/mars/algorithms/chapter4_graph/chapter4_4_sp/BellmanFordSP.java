package com.mars.algorithms.chapter4_graph.chapter4_4_sp;

import com.mars.algorithms.chapter1.chapter1_3.Queue;
import com.mars.algorithms.chapter1.chapter1_3.Stack;

/**
 * 基于队列的Bellman-Ford 算法
 * @author LiMingzhong
 */
public class BellmanFordSP {
	private double[] distTo; // 从起点到某个顶点的路径长度
	private DirectedEdge[] edgeTo; // 从起点到某个顶点的最后一条边
	private boolean[] onQ; // 该顶点是否存在于队列中
	private Queue<Integer> queue; // 正在被放松的顶点
	private int cost; // relax()的调用次数
	private Iterable<DirectedEdge> cycle; // edgeTo[]中的是否有负权重环

	public BellmanFordSP(EdgeWeightedDigraph G, int s) {
		distTo = new double[G.V()];
		edgeTo = new DirectedEdge[G.V()];
		onQ = new boolean[G.V()];
		queue = new Queue<Integer>();
		for (int v = 0; v < G.V(); v++) {
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		distTo[s] = 0.0;
		queue.enqueue(s);
		onQ[s] = true;
		while (!queue.isEmpty() && !hasNegativeCycle()) {
			int v = queue.dequeue();
			onQ[v] = false;
			relax(G, v);
		}
	}

	private void relax(EdgeWeightedDigraph G, int v) {
		for (DirectedEdge e : G.adj(v)) {
			int w = e.to();
			if (distTo[w] > distTo[v] + e.weight()) {
				distTo[w] = distTo[v] + e.weight();
				edgeTo[w] = e;
				if (!onQ[w]) {
					queue.enqueue(w);
					onQ[w] = true;
				}
			}
			if (cost++ % G.V() == 0) {
				findNegativeCycle();
			}
		}
	}

	public double distTo(int v) {
		// 最短路径树实现中的标准查询算法
		return distTo[v];
	}

	public boolean hasPathTo(int v) {
		return distTo[v] < Double.POSITIVE_INFINITY;
	}

	public Iterable<DirectedEdge> pathTo(int v) {
		if (!hasPathTo(v)) {
			return null;
		}
		Stack<DirectedEdge> path = new Stack<DirectedEdge>();
		for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
			path.push(e);
		}
		return path;
	}

	private void findNegativeCycle() {
		int V = edgeTo.length;
		EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
		for (int v = 0; v < V; v++) {
			if (edgeTo[v] != null) {
				spt.addEdge(edgeTo[v]);
			}
		}

		EdgeWeightedCycleFinder cf = new EdgeWeightedCycleFinder(spt);

		cycle = cf.cycle();
	}

	public boolean hasNegativeCycle() {
		return cycle != null;
	}

	public Iterable<DirectedEdge> negativeCycle() {
		return cycle;
	}
}
