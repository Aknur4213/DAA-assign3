
import daa.graph.Edge;
import daa.graph.Graph;
import daa.graph.GraphReader;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Graph graph = GraphReader.read("data/connected.txt");

        for (int v = 0; v < graph.size(); v++) {

            System.out.print("Vertex " + v + ": ");

            for (Edge edge : graph.neighbors(v)) {
                System.out.print(edge.to + "(" + edge.weight + ") ");
            }

            System.out.println();
        }
    }
}
