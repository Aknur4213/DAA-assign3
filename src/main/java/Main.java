
import daa.graph.Edge;
import daa.graph.Graph;

public class Main {
    public static void main(String[] args) {

        Graph graph = new Graph(4, false);

        graph.addEdge(0, 1, 4);
        graph.addEdge(1, 2, 2);
        graph.addEdge(0, 3, 7);

        for (int v = 0; v < graph.size(); v++) {
            System.out.print("Vertex " + v + ": ");

            for (Edge edge : graph.neighbors(v)) {
                System.out.print(edge.to + "(" + edge.weight + ") ");
            }

            System.out.println();
        }
    }
}
