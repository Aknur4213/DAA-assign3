
package daa;

import daa.algorithms.Kruskal;
import daa.algorithms.Prim;
import daa.algorithms.MSTResult;
import daa.graph.Graph;

import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MSTTest {

    @Test
    void testSimpleGraph() {
        Graph graph = new Graph(4, false);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 3);

        MSTResult kruskal = Kruskal.run(graph);
        MSTResult prim = Prim.run(graph);

        assertEquals(6, kruskal.totalWeight);
        assertEquals(6, prim.totalWeight);

        assertEquals(3, kruskal.treeEdges.size());
        assertEquals(3, prim.treeEdges.size());
    }

    @Test
    void testDisconnectedGraph() {
        Graph graph = new Graph(5, false);

        graph.addEdge(0, 1, 4);
        graph.addEdge(1, 2, 2);
        graph.addEdge(3, 4, 7);

        MSTResult kruskal = Kruskal.run(graph);
        MSTResult prim = Prim.run(graph);

        assertEquals(13, kruskal.totalWeight);
        assertEquals(kruskal.totalWeight, prim.totalWeight);

        assertEquals(2, kruskal.components);
        assertEquals(2, prim.components);

        assertEquals(3, kruskal.treeEdges.size());
        assertEquals(3, prim.treeEdges.size());
    }

    @Test
    void testSelfLoopsAndParallelEdges() {
        Graph graph = new Graph(4, false);

        graph.addEdge(0, 0, 1);
        graph.addEdge(0, 1, 8);
        graph.addEdge(0, 1, 3);
        graph.addEdge(1, 2, 4);

        MSTResult kruskal = Kruskal.run(graph);
        MSTResult prim = Prim.run(graph);

        assertEquals(7, kruskal.totalWeight);
        assertEquals(7, prim.totalWeight);

        assertEquals(2, kruskal.components);
        assertEquals(2, prim.components);
    }

    @Test
    void testEmptyGraph() {
        Graph graph = new Graph(0, false);

        MSTResult kruskal = Kruskal.run(graph);
        MSTResult prim = Prim.run(graph);

        assertEquals(0, kruskal.totalWeight);
        assertEquals(0, prim.totalWeight);

        assertEquals(0, kruskal.components);
        assertEquals(0, prim.components);
    }

    @Test
    void testRandomConnectedGraphs() {
        Random rnd = new Random(42);

        for (int test = 0; test < 100; test++) {

            int n = 2 + rnd.nextInt(99);
            Graph graph = new Graph(n, false);

            // Create a connected spanning tree first
            for (int i = 1; i < n; i++) {
                int parent = rnd.nextInt(i);
                int weight = 1 + rnd.nextInt(1000);

                graph.addEdge(parent, i, weight);
            }

            // Add additional random edges
            int extraEdges = n * 3;

            for (int i = 0; i < extraEdges; i++) {
                int u = rnd.nextInt(n);
                int v = rnd.nextInt(n);
                int weight = 1 + rnd.nextInt(1000);

                graph.addEdge(u, v, weight);
            }

            MSTResult kruskal = Kruskal.run(graph);
            MSTResult prim = Prim.run(graph);

            assertEquals(
                    kruskal.totalWeight,
                    prim.totalWeight,
                    "MST weights differ in test " + test
            );

            assertEquals(n - 1, kruskal.treeEdges.size());
            assertEquals(n - 1, prim.treeEdges.size());

            assertEquals(1, kruskal.components);
            assertEquals(1, prim.components);
        }
    }
}
