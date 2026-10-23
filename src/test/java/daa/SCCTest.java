
package daa;

import daa.algorithms.Kosaraju;
import daa.algorithms.SCCResult;
import daa.graph.Edge;
import daa.graph.Graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SCCTest {

    @Test
    void testSimpleGraph() {
        Graph graph = new Graph(5, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 4, 1);
        graph.addEdge(4, 3, 1);

        SCCResult result = Kosaraju.run(graph);

        assertEquals(2, result.count);
        assertEquals(12, result.edgeScans);

        assertEquals(result.component[0], result.component[1]);
        assertEquals(result.component[1], result.component[2]);

        assertEquals(result.component[3], result.component[4]);
        assertNotEquals(result.component[0], result.component[3]);
    }

    @Test
    void testDisconnectedGraph() {
        Graph graph = new Graph(4, true);

        SCCResult result = Kosaraju.run(graph);

        assertEquals(4, result.count);
        assertEquals(0, result.edgeScans);
    }

    @Test
    void testRandomGraphsAgainstBruteForce() {
        Random rnd = new Random(42);

        for (int test = 0; test < 30; test++) {

            int n = 2 + rnd.nextInt(59);
            Graph graph = new Graph(n, true);

            int m = n * 3;

            for (int i = 0; i < m; i++) {
                int u = rnd.nextInt(n);
                int v = rnd.nextInt(n);

                graph.addEdge(u, v, 1);
            }

            SCCResult result = Kosaraju.run(graph);

            boolean[][] reachable = new boolean[n][n];

            for (int start = 0; start < n; start++) {
                reachable[start] = bfs(graph, start);
            }

            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {

                    boolean expected =
                            reachable[u][v] && reachable[v][u];

                    boolean actual =
                            result.component[u] == result.component[v];

                    assertEquals(expected, actual);
                }
            }

            Set<Integer> unique = new HashSet<>();

            for (int id : result.component) {
                unique.add(id);
            }

            assertEquals(unique.size(), result.count);
            assertEquals(2L * m, result.edgeScans);
        }
    }

    private boolean[] bfs(Graph graph, int start) {

        boolean[] visited = new boolean[graph.size()];
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int v = queue.poll();

            for (Edge edge : graph.neighbors(v)) {
                int u = edge.to;

                if (!visited[u]) {
                    visited[u] = true;
                    queue.add(u);
                }
            }
        }

        return visited;
    }

    @Test
    void testStackSafety() {
        int n = 100_000;
        Graph graph = new Graph(n, true);

        for (int i = 0; i < n - 1; i++) {
            graph.addEdge(i, i + 1, 1);
        }

        SCCResult result = assertDoesNotThrow(
                () -> Kosaraju.run(graph)
        );

        assertEquals(n, result.count);
        assertEquals(2L * (n - 1), result.edgeScans);
        assertEquals(n, result.maxDepth);
    }
}
