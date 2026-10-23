
import daa.algorithms.Kosaraju;
import daa.algorithms.SCCResult;
import daa.graph.Graph;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph(5, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);

        graph.addEdge(2, 3, 1);

        graph.addEdge(3, 4, 1);
        graph.addEdge(4, 3, 1);

        SCCResult result = Kosaraju.run(graph);

        System.out.println("SCC count: " + result.count);
        System.out.println("Edge scans: " + result.edgeScans);
        System.out.println("Max stack depth: " + result.maxDepth);

        for (int v = 0; v < graph.size(); v++) {
            System.out.println(
                    "Vertex " + v + ": SCC " + result.component[v]
            );
        }
    }
}
