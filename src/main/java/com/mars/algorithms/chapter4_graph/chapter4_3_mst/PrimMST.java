package com.mars.algorithms.chapter4_graph.chapter4_3_mst;

import com.mars.algorithms.chapter1.chapter1_3.Bag;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 * 最小生成树的Prim 算法（即时版本，通常使用最多的版本，lazyPrim上课就没讲过，效率也没这个高）
 * @author LiMingzhong
 */
public class PrimMST {
	private Edge[] edgeTo; // 距离树最近的边
	private double[] distTo; // distTo[w]=edgeTo[w].weight()
	private boolean[] marked; // 如果v在树中则为true
	private IndexMinPQ<Double> pq; // 有效的横切边

	public PrimMST(EdgeWeightedGraph G) {
		edgeTo = new Edge[G.V()];
		distTo = new double[G.V()];
		marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); v++) {
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		pq = new IndexMinPQ<Double>(G.V());

		distTo[0] = 0.0;
		pq.insert(0, 0.0);			// 用顶点0和权重0初始化pq
		while (!pq.isEmpty()) {
			visit(G, pq.delMin());			// 将最近的顶点添加到树中
		}
	}

	private void visit(EdgeWeightedGraph G, int v) {
		// 将顶点v添加到树中，更新数据
		marked[v] = true;
		for (Edge e : G.adj(v)) {
			int w = e.other(v);

			if (marked[w]) {		// v-w失效
				continue;
			}
			if (e.weight() < distTo[w]) {
				// 连接w和树的最佳边Edge变为e
				edgeTo[w] = e;

				distTo[w] = e.weight();
				if (pq.contains(w)) {
					pq.changeKey(w, distTo[w]);
				} else {
					pq.insert(w, distTo[w]);
				}
			}
		}
	}

	/**
	 * Exercise 4.3.21
	 * 
	 * @return
	 */
	public Iterable<Edge> edges() {
		Bag<Edge> mst = new Bag<>();
		for (int i = 1; i < edgeTo.length; i++) {
			mst.add(edgeTo[i]);
		}
		return mst;
	}

	/**
	 * Exercise 4.3.31
	 * 
	 * @return
	 */
	public double weight() {
		double weight = 0;
		for (int i = 0; i < distTo.length; i++) {
			weight += distTo[i];
		}
		return weight;
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		EdgeWeightedGraph G = new EdgeWeightedGraph(in);

		PrimMST mst = new PrimMST(G);
		for (Edge e : mst.edges()) {
			StdOut.println(e);
		}
		StdOut.println(mst.weight());
	}
}
