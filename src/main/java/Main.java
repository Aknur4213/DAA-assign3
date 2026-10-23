
import daa.algorithms.Kruskal;
import daa.algorithms.MSTResult;
import daa.graph.Graph;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph(4, false);

        graph.addEdge(0, 0, 1);  // Self-loop
        graph.addEdge(0, 1, 8);
        graph.addEdge(0, 1, 3);  // Parallel edge
        graph.addEdge(1, 2, 4);


        MSTResult result = Kruskal.run(graph);

        System.out.println("MST weight: " + result.totalWeight);
        System.out.println("Edges selected: " + result.treeEdges.size());
        System.out.println("Components: " + result.components);
        System.out.println("Edge scans: " + result.edgeScans);
    }
}
