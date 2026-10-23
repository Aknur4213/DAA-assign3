
package daa;

import daa.algorithms.Kahn;
import daa.algorithms.TopoResult;
import daa.graph.Graph;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class KahnTest {

    @Test
    void testValidDAG() {
        Graph graph = new Graph(5, true);

        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(2, 4, 1);

        TopoResult result = Kahn.run(graph);

        assertFalse(result.hasCycle);
        assertEquals(5, result.order.size());
        assertEquals(4, result.edgeScans);

        assertTrue(Kahn.isValidOrder(graph, result.order));
    }

    @Test
    void testDirectedCycle() {
        Graph graph = new Graph(3, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);

        TopoResult result = Kahn.run(graph);

        assertTrue(result.hasCycle);
        assertTrue(result.order.isEmpty());
    }

    @Test
    void testInvalidOrder() {
        Graph graph = new Graph(3, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);

        assertTrue(Kahn.isValidOrder(
                graph, List.of(0, 1, 2)));

        assertFalse(Kahn.isValidOrder(
                graph, List.of(2, 1, 0)));

        assertFalse(Kahn.isValidOrder(
                graph, List.of(0, 0, 2)));
    }

    @Test
    void testRandomDAGs() {
        Random rnd = new Random(42);

        for (int test = 0; test < 100; test++) {

            int n = 2 + rnd.nextInt(99);
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

                graph.addEdge(u, v, 1);
            }

            TopoResult result = Kahn.run(graph);

            assertFalse(result.hasCycle);
            assertEquals(n, result.order.size());
            assertTrue(Kahn.isValidOrder(graph, result.order));

            assertEquals(graph.edges().size(), result.edgeScans);
        }
    }

    @Test
    void testSelfLoop() {
        Graph graph = new Graph(2, true);

        graph.addEdge(0, 0, 1);
        graph.addEdge(0, 1, 2);

        TopoResult result = Kahn.run(graph);

        assertTrue(result.hasCycle);
        assertTrue(result.order.isEmpty());
    }
}
