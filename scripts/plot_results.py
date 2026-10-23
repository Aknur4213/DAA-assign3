import csv
import math
from pathlib import Path

import matplotlib.pyplot as plt


ROOT = Path(__file__).resolve().parent.parent
CSV_PATH = ROOT / "results" / "results.csv"
PLOT_DIR = ROOT / "results" / "plots"

PLOT_DIR.mkdir(parents=True, exist_ok=True)

ALGORITHMS = [
    "Kruskal",
    "Prim",
    "Kosaraju",
    "Kahn",
    "DagShortPath",
]

COLORS = {
    "Kruskal": "#2563eb",
    "Prim": "#ea580c",
    "Kosaraju": "#16a34a",
    "Kahn": "#9333ea",
    "DagShortPath": "#dc2626",
}


def load_results():
    rows = []

    with open(CSV_PATH, newline="", encoding="utf-8") as file:
        reader = csv.DictReader(file)

        for row in reader:
            rows.append({
                "algorithm": row["algorithm"],
                "graph": row["graph"],
                "n": int(row["n"]),
                "m": int(row["m"]),
                "time_ms": float(row["time_ms"]),
                "dsu_ops": int(row["dsu_ops"]),
                "edge_scans": int(row["edge_scans"]),
                "comparisons": int(row["comparisons"]),
                "max_depth": int(row["max_depth"]),
                "result": int(row["result"]),
            })

    if len(rows) != 50:
        raise ValueError(f"Expected 50 rows, found {len(rows)}")

    return rows


def get_series(rows, algorithm, density):
    selected = [
        row for row in rows
        if row["algorithm"] == algorithm
        and row["m"] == density * row["n"]
    ]

    return sorted(selected, key=lambda row: row["n"])


def setup_plot(title, ylabel, log_y=False):
    plt.figure(figsize=(12, 7))

    plt.title(title, fontsize=15, fontweight="bold")
    plt.xlabel("Number of vertices (n)", fontsize=12)
    plt.ylabel(ylabel, fontsize=12)

    plt.xscale("log")

    if log_y:
        plt.yscale("log")

    plt.grid(True, which="both", alpha=0.25)


def save_plot(filename, legend_location="best", legend_below=False):
    if legend_below:
        plt.legend(
            loc="upper center",
            bbox_to_anchor=(0.5, -0.12),
            ncol=5,
            fontsize=9
        )
    else:
        plt.legend(
            loc=legend_location,
            fontsize=9,
            ncol=2
        )

    plt.tight_layout()

    plt.savefig(
        PLOT_DIR / filename,
        dpi=200,
        bbox_inches="tight"
    )

    plt.close()

    print(f"Saved {filename}")


def plot_operations(rows):
    setup_plot(
        "Operations vs Graph Size",
        "Total counted operations",
        log_y=True
    )

    for algorithm in ALGORITHMS:
        for density in [4, 20]:
            data = get_series(rows, algorithm, density)

            x = [row["n"] for row in data]

            y = [
                row["dsu_ops"]
                + row["edge_scans"]
                + row["comparisons"]
                for row in data
            ]

            plt.plot(
                x, y,
                marker="o",
                linestyle="-" if density == 4 else "--",
                color=COLORS[algorithm],
                label=f"{algorithm} ({density}n)"
            )

    save_plot("operations.png")


def plot_scans_per_edge(rows):
    setup_plot(
        "Edge Scans per Edge vs Graph Size",
        "edgeScans / m"
    )

    for algorithm in ALGORITHMS:
        for density in [4, 20]:
            data = get_series(rows, algorithm, density)

            x = [row["n"] for row in data]

            y = [
                row["edge_scans"] / row["m"]
                for row in data
            ]

            plt.plot(
                x, y,
                marker="o",
                linestyle="-" if density == 4 else "--",
                color=COLORS[algorithm],
                label=f"{algorithm} ({density}n)"
            )

    # Legend below the graph
    save_plot(
        "scans_per_edge.png",
        legend_below=True
    )


def plot_kruskal_share(rows):
    setup_plot(
        "Share of Edges Examined by Kruskal",
        "edgeScans / m"
    )

    for density in [4, 20]:
        data = get_series(rows, "Kruskal", density)

        x = [row["n"] for row in data]

        y = [
            row["edge_scans"] / row["m"]
            for row in data
        ]

        plt.plot(
            x, y,
            marker="o",
            linewidth=2.5,
            label="Sparse (4n)" if density == 4
            else "Dense (20n)"
        )

    plt.ylim(0, 1.05)

    # Legend in the lower-right corner
    save_plot(
        "kruskal_edge_share.png",
        legend_location="lower right"
    )


def log_log_slope(data, value_function):
    x = [math.log(row["n"]) for row in data]
    y = [math.log(value_function(row)) for row in data]

    mean_x = sum(x) / len(x)
    mean_y = sum(y) / len(y)

    numerator = sum(
        (a - mean_x) * (b - mean_y)
        for a, b in zip(x, y)
    )

    denominator = sum(
        (a - mean_x) ** 2 for a in x
    )

    return numerator / denominator


def print_slopes(rows):
    print("\nLog-log slopes:")

    for algorithm in ["Kosaraju", "Kahn", "DagShortPath"]:
        for density in [4, 20]:
            data = get_series(rows, algorithm, density)

            slope = log_log_slope(
                data,
                lambda row: row["edge_scans"]
            )

            print(
                f"{algorithm} ({density}n): {slope:.3f}"
            )

    for density in [4, 20]:
        data = get_series(rows, "Kruskal", density)

        slope = log_log_slope(
            data,
            lambda row: (
                row["dsu_ops"]
                + row["edge_scans"]
                + row["comparisons"]
            )
        )

        print(
            f"Kruskal total operations ({density}n): "
            f"{slope:.3f}"
        )


def main():
    rows = load_results()

    plot_operations(rows)
    plot_scans_per_edge(rows)
    plot_kruskal_share(rows)
    print_slopes(rows)

    print("\nAll three plots generated successfully!")


if __name__ == "__main__":
    main()