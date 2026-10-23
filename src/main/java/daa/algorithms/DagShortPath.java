
package daa.algorithms;

import daa.graph.Edge;
import daa.graph.Graph;

import java.util.Arrays;

public class DagShortPath {

    public static DagResult run(Graph graph) {

        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "DAG shortest paths require a directed graph");
        }

        TopoResult topo = Kahn.run(graph);

        if (topo.hasCycle) {
            throw new IllegalArgumentException(
                    "Graph contains a directed cycle");
        }

        int n = graph.size();

        int[] inDegree = new int[n];

        for (Edge edge : graph.edges()) {
            inDegree[edge.to]++;
        }

        long INF = Long.MAX_VALUE / 4;

        long[] dist = new long[n];
        int[] parent = new int[n];

        Arrays.fill(dist, INF);
        Arrays.fill(parent, -1);

        for (int v = 0; v < n; v++) {
            if (inDegree[v] == 0) {
                dist[v] = 0;
            }
        }

        long edgeScans = 0;

        for (int v : topo.order) {

            if (dist[v] == INF) {
                continue;
            }

            for (Edge edge : graph.neighbors(v)) {

                edgeScans++;

                int u = edge.to;
                long newDist = dist[v] + edge.weight;

                if (newDist < dist[u]) {
                    dist[u] = newDist;
                    parent[u] = v;
                }
            }
        }

        return new DagResult(dist, parent, edgeScans);
    }
}
