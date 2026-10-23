
import daa.algorithms.Kruskal;
import daa.algorithms.Prim;
import daa.algorithms.MSTResult;
import daa.graph.Graph;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph(4, false);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 3);

        MSTResult kruskal = Kruskal.run(graph);
        MSTResult prim = Prim.run(graph);

        System.out.println("Kruskal weight: " + kruskal.totalWeight);
        System.out.println("Prim weight: " + prim.totalWeight);

        System.out.println("Kruskal edges: " + kruskal.treeEdges.size());
        System.out.println("Prim edges: " + prim.treeEdges.size());

        if (kruskal.totalWeight != prim.totalWeight) {
            throw new IllegalStateException("MST weights do not match!");
        }

        System.out.println("Both algorithms agree!");
    }
}
