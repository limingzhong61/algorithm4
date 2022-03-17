package com.mars.algorithms.chapter4_graph.chapter4_4_sp;

import com.mars.algorithms.chapter1.chapter1_3.Bag;

import edu.princeton.cs.algs4.In;

/**
 * 加权有向图的数据类型
 * @author LiMingzhong
 */
public class EdgeWeightedDigraph {
	private final int V; // 顶点总数
	private int E; // 边的总数
	private Bag<DirectedEdge>[] adj; // 邻接表

	public EdgeWeightedDigraph(int V) {
		this.V = V;
		this.E = 0;
		adj = (Bag<DirectedEdge>[]) new Bag[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new Bag<DirectedEdge>();
		}
	}

	public EdgeWeightedDigraph(In in) {
		this(in.readInt());
		int E = in.readInt();
		if (E < 0) {
			throw new IllegalArgumentException("Number of edges must be nonnegative");
		}
		for (int i = 0; i < E; i++) {
			int v = in.readInt();
			int w = in.readInt();
			double weight = in.readDouble();
			addEdge(new DirectedEdge(v, w, weight));
		}
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;
	}

	public void addEdge(DirectedEdge e) {
		adj[e.from()].add(e);
		E++;
	}

	public Iterable<DirectedEdge> adj(int v) {
		return adj[v];
	}

	public Iterable<DirectedEdge> edges() {
		Bag<DirectedEdge> bag = new Bag<DirectedEdge>();
		for (int v = 0; v < V; v++) {
			for (DirectedEdge e : adj[v]) {
				bag.add(e);
			}
		}
		return bag;
	}

	/**
	 * Exercise 4.4.2
	 */
	@Override
	public String toString() {
		String s = V + " vertices, " + E + " edges\n";
		for (int v = 0; v < V; v++) {
			s += v + ": ";
			for (DirectedEdge e : this.adj(v)) {
				s += e + " ";
			}
			s += "\n";
		}
		return s;
	}
}
