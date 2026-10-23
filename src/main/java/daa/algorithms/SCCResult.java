
package daa.algorithms;

public class SCCResult {

    public final int count;
    public final int[] component;
    public final long edgeScans;
    public final int maxDepth;

    public SCCResult(int count, int[] component,
                     long edgeScans, int maxDepth) {

        this.count = count;
        this.component = component.clone();
        this.edgeScans = edgeScans;
        this.maxDepth = maxDepth;
    }
}
