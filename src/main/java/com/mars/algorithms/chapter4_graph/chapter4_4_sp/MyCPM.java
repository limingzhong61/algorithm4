package com.mars.algorithms.chapter4_graph.chapter4_4_sp;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * 优先级限制下的并行任务调度问题的关键路径方法
 * 关键路径：critical path
 * 感觉我的更好，只要形成aoe网络即可
 *
 * @author LiMingzhong
 */
public class MyCPM {
    public static void main(String[] args) {
        int N = StdIn.readInt();
        StdIn.readLine();
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(2 * N + 2);
        // s为起点，t为终点
        int s = 2 * N, t = 2 * N + 1;
        int[] inDegree = new int[N];
        //s= 0;
        for (int i = 0; i < N; i++) {
            String[] a = StdIn.readLine().split("\\s+");
            double duration = Double.parseDouble(a[0]);
            //形成aoe网络即可
            //不判断形成aoe网络

            for (int j = 1; j < a.length; j++) {
                int successor = Integer.parseInt(a[j]);
                G.addEdge(new DirectedEdge(i, successor, duration));
                inDegree[successor]++;
            }
            // 添加所有顶点到终点t的边（因为不知道那一个顶点是出度为0的点），值为权重
            //G.addEdge(new DirectedEdge(i, t, duration));
            // 添加起点s到所有顶点的边（因为不知道那一个顶点是入度为0的点）,值为0
            //G.addEdge(new DirectedEdge(s, i, 0));

            // 添加出度为0的点到终点t的边，值为权重
            if (a.length == 1) {
                G.addEdge(new DirectedEdge(i, t, duration));
            }
        }
        // 添加起点s到入度为0的点的边,值为0
        for(int i = 0; i < N;i++){
            if(inDegree[i] == 0) {
                G.addEdge(new DirectedEdge(s,i,0));
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
