
package daa.algorithms;

import daa.graph.Edge;
import java.util.List;

public class MSTResult {

    public final long totalWeight;
    public final List<Edge> treeEdges;
    public final int components;

    public final long dsuOps;
    public final long edgeScans;
    public final long comparisons;

    public MSTResult(long totalWeight, List<Edge> treeEdges,
                     int components, long dsuOps,
                     long edgeScans, long comparisons) {

        this.totalWeight = totalWeight;
        this.treeEdges = List.copyOf(treeEdges);
        this.components = components;

        this.dsuOps = dsuOps;
        this.edgeScans = edgeScans;
        this.comparisons = comparisons;
    }
}
