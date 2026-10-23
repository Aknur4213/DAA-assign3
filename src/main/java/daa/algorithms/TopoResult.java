
package daa.algorithms;

import java.util.List;

public class TopoResult {

    public final List<Integer> order;
    public final boolean hasCycle;
    public final long edgeScans;

    public TopoResult(List<Integer> order,
                      boolean hasCycle, long edgeScans) {

        this.order = List.copyOf(order);
        this.hasCycle = hasCycle;
        this.edgeScans = edgeScans;
    }
}
