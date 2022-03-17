package com.mars.algorithms.chapter4_graph.chapter4_4_sp;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * 优先级限制下的并行任务调度问题的关键路径方法
 * 关键路径：critical path
 * @author LiMingzhong
 */
public class CPM {
	public static void main(String[] args) {
		int N = StdIn.readInt();
		StdIn.readLine();
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(2 * N + 2);
		int s = 2 * N, t = 2 * N + 1;
		for (int i = 0; i < N; i++) {
			String[] a = StdIn.readLine().split("\\s+");
			double duration = Double.parseDouble(a[0]);
			/*
			* 对于每个任务都有一条从它的起始顶点指向结束顶点的边，边的权重为任务所需的时间。对于
				每条优先级限制v → w，添加一条从v 的结束顶点指向w 的起始顶点的权重为零的边。
			*
			* */
			// 0->10, 1->11... 10,11为中间结点，
			G.addEdge(new DirectedEdge(i, i + N, duration));
			G.addEdge(new DirectedEdge(s, i, 0.0));

			G.addEdge(new DirectedEdge(i + N, t, 0.0));
			for (int j = 1; j < a.length; j++) {
				int successor = Integer.parseInt(a[j]);
				G.addEdge(new DirectedEdge(i + N, successor, 0.0));
			}
		}

		AcyclicLP lp = new AcyclicLP(G, s);

		StdOut.println("Start times:");
		for (int i = 0; i < N; i++) {
			StdOut.printf("%4d: %5.1f\n", i, lp.distTo(i));
		}
		StdOut.printf("Finish time: %5.1f\n", lp.distTo(t));
	}
}
/*
10
41.0 1 7 9
51.0 2
50.0
36.0
38.0
45.0
21.0 3 8
32.0 3 8
32.0 2
29.0 4 6
Start times:
   0:   0.0
   1:  41.0
   2: 123.0
   3:  91.0
   4:  70.0
   5:   0.0
   6:  70.0
   7:  41.0
   8:  91.0
   9:  41.0
Finish time: 173.0

Process finished with exit code 0

* */