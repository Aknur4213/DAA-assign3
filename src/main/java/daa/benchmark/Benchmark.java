
package daa.benchmark;

import daa.algorithms.*;
import daa.graph.Graph;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class Benchmark {

    public static void main(String[] args) throws IOException {

        Files.createDirectories(Path.of("results"));

        try (BufferedWriter writer =
                     Files.newBufferedWriter(Path.of("results/results.csv"))) {

            writer.write("algorithm,graph,n,m,time_ms,dsu_ops,"
                    + "edge_scans,comparisons,max_depth,result");
            writer.newLine();

            int[] sizes = ReferenceValues.SIZES;

            for (int i = 0; i < sizes.length; i++) {

                int n = sizes[i];

                for (int density = 0; density < 2; density++) {

                    int m = (density == 0) ? 4 * n : 20 * n;
                    int index = i * 2 + density;

                    System.out.println(
                            "Benchmarking n=" + n + ", m=" + m);

                    // Undirected graph: Kruskal and Prim
                    long mstWeight;

                    {
                        Graph graph = GraphGenerator.undirected(n, m);

                        MSTResult kruskal = Kruskal.run(graph);
                        MSTResult prim = Prim.run(graph);

                        if (kruskal.totalWeight != prim.totalWeight) {
                            throw new IllegalStateException(
                                    "Kruskal and Prim weights differ");
                        }

                        mstWeight = kruskal.totalWeight;

                        double kruskalTime = Timing.medianOfFive(
                                () -> Kruskal.run(graph));

                        writeRow(writer, "Kruskal", "undirected",
                                n, m, kruskalTime,
                                kruskal.dsuOps, kruskal.edgeScans,
                                kruskal.comparisons, 0,
                                kruskal.totalWeight);

                        double primTime = Timing.medianOfFive(
                                () -> Prim.run(graph));

                        writeRow(writer, "Prim", "undirected",
                                n, m, primTime,
                                prim.dsuOps, prim.edgeScans,
                                prim.comparisons, 0,
                                prim.totalWeight);
                    }

                    // Directed graph: Kosaraju
                    int sccCount;

                    {
                        Graph graph = GraphGenerator.directed(n, m);

                        SCCResult scc = Kosaraju.run(graph);
                        sccCount = scc.count;

                        if (scc.edgeScans != 2L * m) {
                            throw new IllegalStateException(
                                    "Incorrect Kosaraju edge scans");
                        }

                        double time = Timing.medianOfFive(
                                () -> Kosaraju.run(graph));

                        writeRow(writer, "Kosaraju", "directed",
                                n, m, time, 0,
                                scc.edgeScans, 0,
                                scc.maxDepth, scc.count);
                    }

                    // DAG: Kahn and shortest paths
                    long dagSum;

                    {
                        Graph graph = GraphGenerator.dag(n, m);

                        TopoResult topo = Kahn.run(graph);

                        if (topo.hasCycle ||
                                !Kahn.isValidOrder(graph, topo.order)) {
                            throw new IllegalStateException(
                                    "Invalid topological order");
                        }

                        DagResult paths = DagShortPath.run(graph);
                        dagSum = paths.sumDistances();

                        if (topo.edgeScans != m ||
                                paths.edgeScans != m) {
                            throw new IllegalStateException(
                                    "Incorrect DAG edge scans");
                        }

                        double kahnTime = Timing.medianOfFive(
                                () -> Kahn.run(graph));

                        writeRow(writer, "Kahn", "dag",
                                n, m, kahnTime, 0,
                                topo.edgeScans, 0, 0,
                                topo.order.size());

                        double pathTime = Timing.medianOfFive(
                                () -> DagShortPath.run(graph));

                        writeRow(writer, "DagShortPath", "dag",
                                n, m, pathTime, 0,
                                paths.edgeScans, 0, 0,
                                dagSum);
                    }

                    // Verify Section 7 reference values
                    ReferenceValues.check(
                            index, mstWeight, sccCount, dagSum);

                    writer.flush();

                    System.out.println("PASS");
                }
            }
        }

        System.out.println("Benchmark completed successfully!");
        System.out.println("Results saved to results/results.csv");
    }

    private static void writeRow(
            BufferedWriter writer,
            String algorithm,
            String graphType,
            int n,
            int m,
            double timeMs,
            long dsuOps,
            long edgeScans,
            long comparisons,
            int maxDepth,
            long result
    ) throws IOException {

        String row = String.format(
                Locale.US,
                "%s,%s,%d,%d,%.6f,%d,%d,%d,%d,%d",
                algorithm, graphType, n, m, timeMs,
                dsuOps, edgeScans, comparisons,
                maxDepth, result
        );

        writer.write(row);
        writer.newLine();
    }
}
