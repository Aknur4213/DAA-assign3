
package daa;

import daa.algorithms.DagResult;
import daa.algorithms.DagShortPath;
import daa.graph.Edge;
import daa.graph.Graph;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DagShortPathTest {

    @Test
    void testNegativeWeights() {
        Graph graph = new Graph(4, true);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(2, 1, -3);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 6);

        DagResult result = DagShortPath.run(graph);

        assertArrayEquals(
                new long[]{0, -1, 2, 1},
                result.dist
        );

        assertEquals(List.of(0, 2, 1, 3),
                result.pathTo(3));

        assertEquals(2, result.sumDistances());
        assertEquals(5, result.edgeScans);
    }

    @Test
    void testMultipleSources() {
        Graph graph = new Graph(5, true);

        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, -4);
        graph.addEdge(1, 4, 3);

        DagResult result = DagShortPath.run(graph);

        assertArrayEquals(
                new long[]{0, 0, 2, -2, 3},
                result.dist
        );

        assertEquals(List.of(1, 2, 3),
                result.pathTo(3));

        assertEquals(3, result.sumDistances());
        assertEquals(4, result.edgeScans);
    }

    @Test
    void testCycleRejected() {
        Graph graph = new Graph(3, true);

        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 0, 4);

        assertThrows(IllegalArgumentException.class,
                () -> DagShortPath.run(graph));
    }

    @Test
    void testRandomDAGsAgainstBellmanFord() {
        Random rnd = new Random(42);

        for (int test = 0; test < 100; test++) {

            int n = 2 + rnd.nextInt(59);
            Graph graph = new Graph(n, true);

            int m = n * 4;

            for (int i = 0; i < m; i++) {

                int u = rnd.nextInt(n);
                int v = rnd.nextInt(n);

                if (u == v) {
                    continue;
                }

                if (u > v) {
                    int temp = u;
                    u = v;
                    v = temp;
                }

                int weight = rnd.nextInt(41) - 20;

                graph.addEdge(u, v, weight);
            }

            DagResult actual = DagShortPath.run(graph);
            long[] expected = bellmanFordReference(graph);

            assertArrayEquals(
                    expected,
                    actual.dist,
                    "Distances differ in test " + test
            );

            assertEquals(graph.edges().size(),
                    actual.edgeScans);
        }
    }

    private long[] bellmanFordReference(Graph graph) {

        int n = graph.size();
        long INF = Long.MAX_VALUE / 4;

        long[] dist = new long[n];
        int[] inDegree = new int[n];

        Arrays.fill(dist, INF);

        for (Edge edge : graph.edges()) {
            inDegree[edge.to]++;
        }

        // Same virtual super-source as the assignment
        for (int v = 0; v < n; v++) {
            if (inDegree[v] == 0) {
                dist[v] = 0;
            }
        }

        // Bellman-Ford relaxation
        for (int i = 0; i < n - 1; i++) {

            boolean changed = false;

            for (Edge edge : graph.edges()) {

                if (dist[edge.from] == INF) {
                    continue;
                }

                long newDist =
                        dist[edge.from] + edge.weight;

                if (newDist < dist[edge.to]) {
                    dist[edge.to] = newDist;
                    changed = true;
                }
            }

            if (!changed) {
                break;
            }
        }

        return dist;
    }

    @Test
    void testEmptyDAG() {
        Graph graph = new Graph(0, true);

        DagResult result = DagShortPath.run(graph);

        assertEquals(0, result.dist.length);
        assertEquals(0, result.sumDistances());
        assertEquals(0, result.edgeScans);
    }
}
