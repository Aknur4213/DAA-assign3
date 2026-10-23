
package daa.algorithms;

import daa.graph.Edge;
import daa.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class Kruskal {

    public static MSTResult run(Graph graph) {

        if (graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Kruskal requires an undirected graph");
        }

        int n = graph.size();

        DSU dsu = new DSU(n);

        List<Edge> sortedEdges = new ArrayList<>(graph.edges());
        List<Edge> mstEdges = new ArrayList<>();

        final long[] comparisons = {0};

        sortedEdges.sort((a, b) -> {
            comparisons[0]++;
            return Integer.compare(a.weight, b.weight);
        });

        long totalWeight = 0;
        long edgeScans = 0;

        for (Edge edge : sortedEdges) {

            if (mstEdges.size() == n - 1) {
                break;
            }

            edgeScans++;

            int rootU = dsu.find(edge.from);
            int rootV = dsu.find(edge.to);

            if (rootU != rootV) {

                dsu.union(edge.from, edge.to);

                mstEdges.add(edge);
                totalWeight += edge.weight;
            }
        }

        return new MSTResult(
                totalWeight,
                mstEdges,
                dsu.components(),
                dsu.getDsuOps(),
                edgeScans,
                comparisons[0]
        );
    }
}
