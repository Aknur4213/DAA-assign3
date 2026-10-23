
import daa.algorithms.DagResult;
import daa.algorithms.DagShortPath;
import daa.graph.Graph;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph(3, true);

        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 0, 4);

        DagResult result = DagShortPath.run(graph);

        System.out.println(
                "Distances: " + Arrays.toString(result.dist));

        System.out.println(
                "Parents: " + Arrays.toString(result.parent));

        System.out.println(
                "Path to 3: " + result.pathTo(3));

        System.out.println(
                "Sum of distances: " + result.sumDistances());

        System.out.println(
                "Edge scans: " + result.edgeScans);
    }
}
