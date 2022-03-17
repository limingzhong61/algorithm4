package com.mars.algorithms.chapter4_graph.chapter4_4_sp;

import com.mars.algorithms.chapter1.chapter1_3.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 　无环加权有向图的最短路径算法
 * 按照拓扑顺序处理无环有向图中的顶点
 * Acyclic: adj. 非循环的；非环的
 * @author LiMingzhong
 */
public class AcyclicSP {
	private DirectedEdge[] edgeTo;
	private double[] distTo;

	public AcyclicSP(EdgeWeightedDigraph G, int s) {
		edgeTo = new DirectedEdge[G.V()];
		distTo = new double[G.V()];

		for (int v = 0; v < G.V(); v++) {
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		distTo[s] = 0.0;

		EdgeWeightedTopological top = new EdgeWeightedTopological(G);
		for (int v : top.order()) {
			relax(G, v);
		}
	}

	private void relax(EdgeWeightedDigraph G, int v) {
		for (DirectedEdge e : G.adj(v)) {
			int w = e.to();
			if (distTo[w] > distTo[v] + e.weight()) {
				distTo[w] = distTo[v] + e.weight();
				edgeTo[w] = e;
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

	public static void main(String[] args) {
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In(args[0]));
		int s = Integer.parseInt(args[1]);
		AcyclicSP sp = new AcyclicSP(G, s);

		for (int t = 0; t < G.V(); t++) {
			StdOut.print(s + " to " + t);
			StdOut.printf(" (%4.2f): ", sp.distTo(t));
			if (sp.hasPathTo(t)) {
				for (DirectedEdge e : sp.pathTo(t)) {
					StdOut.print(e + "   ");
				}
			}
			StdOut.println();
		}
	}
}
