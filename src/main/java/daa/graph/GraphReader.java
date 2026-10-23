
package daa.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphReader {

    public static Graph read(String filename) throws FileNotFoundException {

        try (Scanner scanner = new Scanner(new File(filename))) {

            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String type = scanner.next();

            if (!type.equals("directed") && !type.equals("undirected")) {
                throw new IllegalArgumentException("Invalid graph type: " + type);
            }

            boolean directed = type.equals("directed");

            Graph graph = new Graph(n, directed);

            for (int i = 0; i < m; i++) {
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                int w = scanner.nextInt();

                graph.addEdge(u, v, w);
            }

            return graph;
        }
    }
}
