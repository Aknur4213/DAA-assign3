
package daa.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DagResult {

    public final long[] dist;
    public final int[] parent;
    public final long edgeScans;

    public DagResult(long[] dist, int[] parent,
                     long edgeScans) {

        this.dist = dist.clone();
        this.parent = parent.clone();
        this.edgeScans = edgeScans;
    }

    public List<Integer> pathTo(int target) {

        if (target < 0 || target >= dist.length) {
            throw new IndexOutOfBoundsException(
                    "Invalid vertex: " + target);
        }

        List<Integer> path = new ArrayList<>();

        int current = target;

        while (current != -1) {
            path.add(current);
            current = parent[current];
        }

        Collections.reverse(path);

        return path;
    }

    public long sumDistances() {
        long sum = 0;

        for (long distance : dist) {
            sum += distance;
        }

        return sum;
    }
}
