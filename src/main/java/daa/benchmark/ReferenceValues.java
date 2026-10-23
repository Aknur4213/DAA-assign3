
package daa.benchmark;

public class ReferenceValues {

    public static final int[] SIZES = {
            1000, 3162, 10000, 31623, 100000
    };

    public static final long[] MST_WEIGHTS = {
            147970, 30363,
            468911, 97589,
            1486139, 309550,
            4715515, 968517,
            15012094, 3056681
    };

    public static final int[] SCC_COUNTS = {
            27, 1,
            137, 1,
            373, 1,
            1188, 1,
            3923, 1
    };

    public static final long[] DAG_SUMS = {
            396274, 252411,
            1271911, 723157,
            3941828, 2381342,
            12551891, 7376612,
            40096539, 23512087
    };

    public static void check(int index, long mst,
                             int scc, long dagSum) {

        if (mst != MST_WEIGHTS[index]) {
            throw new IllegalStateException(
                    "MST mismatch at index " + index
                            + ": expected " + MST_WEIGHTS[index]
                            + ", got " + mst);
        }

        if (scc != SCC_COUNTS[index]) {
            throw new IllegalStateException(
                    "SCC mismatch at index " + index
                            + ": expected " + SCC_COUNTS[index]
                            + ", got " + scc);
        }

        if (dagSum != DAG_SUMS[index]) {
            throw new IllegalStateException(
                    "DAG mismatch at index " + index
                            + ": expected " + DAG_SUMS[index]
                            + ", got " + dagSum);
        }
    }
}
