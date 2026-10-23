package daa.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private final int n;
    private final boolean directed;
    private final List<List<Edge>> adj;
    private final List<Edge> edges;

    public Graph(int n, boolean directed) {
        if (n < 0) {
            throw new IllegalArgumentException("Number of vertices cant be negative");
        }

        this.n = n;
        this.directed = directed;
        this.adj = new ArrayList<>();
        this.edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, int weight) {
        checkVertex(from);
        checkVertex(to);

        Edge edge = new Edge(from, to, weight);

        edges.add(edge);
        adj.get(from).add(edge);

        if (!directed) {
            adj.get(to).add(new Edge(to, from, weight));
        }
    }

    private void checkVertex(int v) {
        if (v < 0 || v >= n) {
            throw new IndexOutOfBoundsException("Invalid vertex: " + v);
        }
    }

    public int size() {
        return n;
    }

    public boolean isDirected() {
        return directed;
    }

    public List<Edge> neighbors(int v) {
        checkVertex(v);
        return List.copyOf(adj.get(v));
    }

    public List<Edge> edges() {
        return List.copyOf(edges);
    }
}
