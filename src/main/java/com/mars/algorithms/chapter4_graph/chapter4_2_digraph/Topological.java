package com.mars.algorithms.chapter4_graph.chapter4_2_digraph;

import edu.princeton.cs.algs4.StdOut;

/**
 * @author LiMingzhong
 */
public class Topological {
	private Iterable<Integer> order;			// 顶点的拓扑顺序
	
	public Topological(Digraph G) {
		DirectedCycle cycleFinder = new DirectedCycle(G);
		if (!cycleFinder.hasCycle()) {
			DepthFirstOrder dfs = new DepthFirstOrder(G);
			order = dfs.reversePost();
		}
	}
	
	public Iterable<Integer> order() {
		return order;
	}
	
	public boolean isDAG() {
		return order != null;
	}

	public static void main(String[] args) {
		String filename = args[0];
		String separator = args[1];
		SymbolDigraph sg = new SymbolDigraph(filename, separator);
		
		Topological top = new Topological(sg.G());
		
		for (int v : top.order()) {
			StdOut.println(sg.name(v));
		}
	}
}
