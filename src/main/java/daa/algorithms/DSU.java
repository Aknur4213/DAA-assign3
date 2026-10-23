
package daa.algorithms;

public class DSU {

    private final int[] parent;
    private final int[] size;
    private int componentCount;

    private long findCalls;
    private long successUnions;

    public DSU(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative number of elements");
        }

        parent = new int[n];
        size = new int[n];
        componentCount = n;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int x) {
        checkIndex(x);
        findCalls++;

        int root = x;

        while (parent[root] != root) {
            root = parent[root];
        }

        while (parent[x] != x) {
            int next = parent[x];
            parent[x] = root;
            x = next;
        }

        return root;
    }


    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return false;
        }

        if (size[rootX] < size[rootY]) {
            int temp = rootX;
            rootX = rootY;
            rootY = temp;
        }

        parent[rootY] = rootX;
        size[rootX] += size[rootY];

        componentCount--;
        successUnions++;

        return true;
    }

    public int components() {
        return componentCount;
    }

    public long getFindCalls() {
        return findCalls;
    }

    public long getSuccessUnions() {
        return successUnions;
    }

    public long getDsuOps() {
        return findCalls + successUnions;
    }

    private void checkIndex(int x) {
        if (x < 0 || x >= parent.length) {
            throw new IndexOutOfBoundsException("Invalid element: " + x);
        }
    }
}
