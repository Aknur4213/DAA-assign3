# DAA Assignment 3 — Graph Algorithms

## Project Description

This project implements and benchmarks graph algorithms for the
Design and Analysis of Algorithms course.

The project uses Java 25, Maven, and JUnit 5.

## Implemented Algorithms

1. Disjoint Set Union (DSU)
    - Union by size
    - Path compression

2. Minimum Spanning Tree
    - Kruskal's algorithm
    - Prim's algorithm

3. Strongly Connected Components
    - Kosaraju's algorithm
    - Iterative DFS

4. Topological Sorting
    - Kahn's algorithm
    - Cycle detection

5. DAG Shortest Paths
    - Topological-order relaxation
    - Negative edge weights
    - Path reconstruction

## Project Structure

- src/main/java/daa/graph — Graph representation and file reading
- src/main/java/daa/algorithms — Graph algorithms
- src/main/java/daa/benchmark — Graph generation and benchmarking
- src/main/java/daa/metrics — Metrics package
- src/test/java/daa — JUnit 5 tests
- data — Sample input graphs
- results/results.csv — Benchmark results
- results/plots — Three PNG charts
- scripts/plot_results.py — Plot generation script
- REPORT.md — Complexity analysis and experimental results

## Requirements

- JDK 25
- Maven
- Python 3
- Matplotlib

## Build and Test

Open the project in IntelliJ IDEA.

Run Maven Lifecycle → test.

Alternatively, if Maven is configured in PATH:

```bash
mvn test
```

## Run Benchmark

Run the main method in:

`daa.benchmark.Benchmark`

The benchmark generates 50 CSV rows and verifies the reference
values from Assignment 3.

Results are written to:

`results/results.csv`

## Generate Plots

Install Matplotlib:

```bash
py -m pip install matplotlib
```

Generate all three plots:

```bash
py scripts/plot_results.py
```

## Input Format

The first line contains:

`n m directed|undirected`

The remaining lines contain:

`u v w`

Example:

```text
4 3 undirected
0 1 4
1 2 2
0 3 7
```

## Verification

The project includes 24 JUnit 5 tests.

Tests cover:

- DSU correctness
- MST agreement
- SCC correctness against BFS
- Topological order validation
- DAG shortest paths against Bellman-Ford
- Stack safety with 100,000 vertices

All ten benchmark configurations reproduce the required reference values.

## Git Workflow

Feature branches:

- feature/dsu
- feature/mst
- feature/scc
- feature/paths
- feature/metrics

Stable branch: main

Release tag: v1.0

## AI Use Acknowledgement

AI assistance was used for implementation guidance, explanations,
test development, debugging, benchmarking, plotting, and report preparation.

The algorithms were implemented and tested in Java, and their
results were verified against automated tests and the assignment's
reference values.