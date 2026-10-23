
package daa.algorithms;

import daa.graph.Edge;
import daa.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kosaraju {

    public static SCCResult run(Graph graph) {

        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Kosaraju requires a directed graph");
        }

        int n = graph.size();

        List<List<Edge>> adj = new ArrayList<>();
        List<List<Integer>> reversed = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adj.add(graph.neighbors(i));
            reversed.add(new ArrayList<>());
        }

        for (Edge edge : graph.edges()) {
            reversed.get(edge.to).add(edge.from);
        }

        boolean[] visited = new boolean[n];
        int[] nextIndex = new int[n];
        List<Integer> finishOrder = new ArrayList<>(n);

        ArrayDeque<Integer> stack = new ArrayDeque<>();

        long edgeScans = 0;
        int maxDepth = 0;

        // First DFS pass: calculate finishing order
        for (int start = 0; start < n; start++) {

            if (visited[start]) {
                continue;
            }

            visited[start] = true;
            stack.push(start);

            maxDepth = Math.max(maxDepth, stack.size());

            while (!stack.isEmpty()) {

                int v = stack.peek();

                if (nextIndex[v] < adj.get(v).size()) {

                    Edge edge = adj.get(v).get(nextIndex[v]++);
                    edgeScans++;

                    int u = edge.to;

                    if (!visited[u]) {
                        visited[u] = true;
                        stack.push(u);

                        maxDepth = Math.max(maxDepth, stack.size());
                    }

                } else {
                    stack.pop();
                    finishOrder.add(v);
                }
            }
        }

        // Second DFS pass: find components
        Arrays.fill(visited, false);

        int[] component = new int[n];
        Arrays.fill(component, -1);

        int count = 0;

        for (int i = finishOrder.size() - 1; i >= 0; i--) {

            int start = finishOrder.get(i);

            if (visited[start]) {
                continue;
            }

            visited[start] = true;
            component[start] = count;
            stack.push(start);

            maxDepth = Math.max(maxDepth, stack.size());

            while (!stack.isEmpty()) {

                int v = stack.pop();

                for (int u : reversed.get(v)) {
                    edgeScans++;

                    if (!visited[u]) {
                        visited[u] = true;
                        component[u] = count;
                        stack.push(u);

                        maxDepth = Math.max(maxDepth, stack.size());
                    }
                }
            }

            count++;
        }

        return new SCCResult(
                count, component, edgeScans, maxDepth
        );
    }
}
