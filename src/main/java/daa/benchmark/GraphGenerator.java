
package daa.benchmark;

import daa.graph.Graph;

import java.util.Random;

public class GraphGenerator {

    public static Graph undirected(int n, int m) {

        checkSize(n, m, true);

        Random rnd = new Random(42);
        Graph graph = new Graph(n, false);

        int[] perm = shuffled(n, rnd);

        // First create a random spanning tree
        for (int i = 1; i < n; i++) {

            int a = perm[rnd.nextInt(i)];
            int b = perm[i];
            int weight = 1 + rnd.nextInt(1000);

            graph.addEdge(a, b, weight);
        }

        // Add the remaining random edges
        for (int i = n - 1; i < m; i++) {

            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);

            while (b == a) {
                b = rnd.nextInt(n);
            }

            int weight = 1 + rnd.nextInt(1000);

            graph.addEdge(a, b, weight);
        }

        return graph;
    }

    public static Graph directed(int n, int m) {

        checkSize(n, m, false);

        Random rnd = new Random(42);
        Graph graph = new Graph(n, true);

        for (int i = 0; i < m; i++) {

            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);

            while (b == a) {
                b = rnd.nextInt(n);
            }

            int weight = 1 + rnd.nextInt(1000);

            graph.addEdge(a, b, weight);
        }

        return graph;
    }

    public static Graph dag(int n, int m) {

        checkSize(n, m, false);

        Random rnd = new Random(42);
        Graph graph = new Graph(n, true);

        int[] rank = shuffled(n, rnd);

        for (int i = 0; i < m; i++) {

            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);

            while (b == a) {
                b = rnd.nextInt(n);
            }

            if (rank[a] > rank[b]) {
                int temp = a;
                a = b;
                b = temp;
            }

            int weight = 1 + rnd.nextInt(1000);

            graph.addEdge(a, b, weight);
        }

        return graph;
    }

    private static int[] shuffled(int n, Random rnd) {

        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = i;
        }

        for (int i = n - 1; i > 0; i--) {

            int j = rnd.nextInt(i + 1);

            int temp = values[i];
            values[i] = values[j];
            values[j] = temp;
        }

        return values;
    }

    private static void checkSize(int n, int m,
                                  boolean connected) {

        if (n < 0 || m < 0) {
            throw new IllegalArgumentException(
                    "Graph size cannot be negative");
        }

        if (n == 0 && m != 0) {
            throw new IllegalArgumentException(
                    "An empty graph cannot have edges");
        }

        if (n == 1 && m > 0) {
            throw new IllegalArgumentException(
                    "Generator does not allow self-loops");
        }

        if (connected && m < Math.max(0, n - 1)) {
            throw new IllegalArgumentException(
                    "Not enough edges for a spanning tree");
        }
    }
}
