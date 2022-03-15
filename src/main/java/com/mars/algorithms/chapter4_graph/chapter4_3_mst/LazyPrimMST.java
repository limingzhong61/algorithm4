package com.mars.algorithms.chapter4_graph.chapter4_3_mst;

import com.mars.algorithms.chapter1.chapter1_3.Queue;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 *  使用优先队列找最小横切边（横切边：连个两个不同集合（加入树中的顶点集和其他顶点集合）的边）
 * 最小生成树的Prim 算法的延时实现
 * Prim 算法的这种实现使用了一条优先队列来保存所有的横切边、一个由顶点索引的数组来标记树的
 * 顶点以及一条队列来保存最小生成树的边。这种延时实现会在优先队列中保留失效的边。
 *
 *
 * **可以不看这个，及时实现比这个好多了**
 * @author LiMingzhong
 */
public class LazyPrimMST {
	private boolean[] marked; // 最小生成树的顶点
	private Queue<Edge> mst; // 最小生成树的边
	private MinPQ<Edge> pq; // 横切边（包括失效的边）
	private double weight;

	public LazyPrimMST(EdgeWeightedGraph G) {
		pq = new MinPQ<Edge>();
		marked = new boolean[G.V()];
		mst = new Queue<Edge>();

		visit(G, 0);		// 假设G是连通的（请见练习4.3.22）
		while (!pq.isEmpty()) {
			Edge e = pq.delMin();		// 从pq中得到权重最小的边

			int v = e.either(), w = e.other(v);
			if (marked[v] && marked[w]) {		// 跳过失效的边
				continue;
			}
			mst.enqueue(e);					// 将边添加到树中
			weight += e.weight();
			if (!marked[v]) {				// 将顶点（v或w）添加到树中
				visit(G, v);
			}
			if (!marked[w]) {
				visit(G, w);
			}
		}
	}

	private void visit(EdgeWeightedGraph G, int v) {
		// 标记顶点v并将所有连接v和未被标记顶点的边加入pq
		marked[v] = true;
		for (Edge e : G.adj(v)) {
			if (!marked[e.other(v)]) {
				pq.insert(e);
			}
		}
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

	public static void main(String[] args) {
		In in = new In(args[0]);
		EdgeWeightedGraph G = new EdgeWeightedGraph(in);

		LazyPrimMST mst = new LazyPrimMST(G);
		for (Edge e : mst.edges()) {
			StdOut.println(e);
		}
		StdOut.println(mst.weight());
	}
}
