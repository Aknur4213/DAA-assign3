
package daa.algorithms;

import daa.graph.Edge;
import daa.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Kahn {

    public static TopoResult run(Graph graph) {

        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Kahn requires a directed graph");
        }

        int n = graph.size();
        int[] inDegree = new int[n];

        // Calculate in-degrees
        for (Edge edge : graph.edges()) {
            inDegree[edge.to]++;
        }

        ArrayDeque<Integer> queue = new ArrayDeque<>();

        for (int v = 0; v < n; v++) {
            if (inDegree[v] == 0) {
                queue.add(v);
            }
        }

        List<Integer> order = new ArrayList<>();
        long edgeScans = 0;

        while (!queue.isEmpty()) {

            int v = queue.poll();
            order.add(v);

            for (Edge edge : graph.neighbors(v)) {

                edgeScans++;

                int u = edge.to;
                inDegree[u]--;

                if (inDegree[u] == 0) {
                    queue.add(u);
                }
            }
        }

        boolean hasCycle = order.size() != n;

        if (hasCycle) {
            return new TopoResult(List.of(), true, edgeScans);
        }

        return new TopoResult(order, false, edgeScans);
    }

    public static boolean isValidOrder(Graph graph, List<Integer> order) {

        int n = graph.size();

        if (!graph.isDirected() || order.size() != n) {
            return false;
        }

        int[] pos = new int[n];
        boolean[] seen = new boolean[n];

        for (int i = 0; i < n; i++) {

            int v = order.get(i);

            if (v < 0 || v >= n || seen[v]) {
                return false;
            }

            seen[v] = true;
            pos[v] = i;
        }

        for (Edge edge : graph.edges()) {

            if (pos[edge.from] >= pos[edge.to]) {
                return false;
            }
        }

        return true;
    }

}
