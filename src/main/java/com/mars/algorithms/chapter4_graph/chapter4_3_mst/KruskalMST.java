package com.mars.algorithms.chapter4_graph.chapter4_3_mst;

import com.mars.algorithms.chapter1.chapter1_3.Queue;
import com.mars.algorithms.chapter1.chapter1_5.UF;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 * 最小生成树的Kruskal 算法
 *
 * 这份Kruskal 算法的实现使用了一条队列来保
 * 存最小生成树中的所有边、一条优先队列来保存还
 * 未被检查的边和一个union-find 的数据结构来判断无
 * 效的边。最小生成树的所有边会按照权重的升序返
 * 回给用例
 * @author LiMingzhong
 */
public class KruskalMST {
	private Queue<Edge> mst;
	private double weight;

	public KruskalMST(EdgeWeightedGraph G) {
		mst = new Queue<Edge>();
		MinPQ<Edge> pq = new MinPQ<Edge>();
		for (Edge e : G.edges()) {
			pq.insert(e);
		}
		UF uf = new UF(G.V());

		while (!pq.isEmpty() && mst.size() < G.V() - 1) {
			Edge e = pq.delMin();		// 从pq得到权重最小的边和它的顶点
			int v = e.either(), w = e.other(v);
			if (uf.connected(v, w)) {		// 忽略失效的边
				continue;
			}
			uf.union(v, w); // 合并分量
			mst.enqueue(e); // 将边添加到最小生成树中
			weight += e.weight();
		}

		assert check(G);
	}

	public Iterable<Edge> edges() {
		return mst;
	}

	/**
	 * Exercise 4.3.31
	 * 
	 * @return
	 */
	public double weight() {
		return weight;
	}

	/**
	 * Exercise 4.3.33
	 * 
	 * @param G
	 * @return
	 */
	private boolean check(EdgeWeightedGraph G) {
		// check total weight
		double total = 0;
		for (Edge edge : mst) {
			total += edge.weight();
		}
		if (Math.abs(total - weight) > 1E-12) {
			System.err.println("total weight not equal");
			return false;
		}

		// check that it is acyclic
		UF uf = new UF(G.V());
		for (Edge edge : mst) {
			int v = edge.either(), w = edge.other(v);
			if (uf.connected(v, w)) {
				System.err.println("it is not acyclic");
				return false;
			} else {
				uf.union(v, w);
			}
		}

		// check that it is a spanning tree
		for (Edge edge : G.edges()) {
			int v = edge.either(), w = edge.other(v);
			if (!uf.connected(v, w)) {
				System.err.println("it is not a spanning tree");
				return false;
			}
		}

		// check that it is a minimal spanning tree (cut optimality
		// conditions)
		for (Edge edge : mst) {
			uf = new UF(G.V());
			for (Edge e : mst) {
				int v = e.either(), w = e.other(v);
				if (e != edge) {
					uf.union(v, w);
				}
			}
			for (Edge e : G.edges()) {
				int v = e.either(), w = e.other(v);
				if (!uf.connected(v, w) && e.weight() < edge.weight()) {
					System.err.println("it is not a minimal spanning tree");
					return false;
				}
			}
		}

		return true;
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		EdgeWeightedGraph G = new EdgeWeightedGraph(in);

		KruskalMST mst = new KruskalMST(G);
		for (Edge e : mst.edges()) {
			StdOut.println(e);
		}
		StdOut.println(mst.weight());
	}
}
