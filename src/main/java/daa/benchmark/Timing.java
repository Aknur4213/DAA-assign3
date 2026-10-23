
package daa.benchmark;

import java.util.Arrays;

public class Timing {

    public static double medianOfFive(Runnable task) {

        double[] times = new double[5];

        for (int i = 0; i < 5; i++) {

            long startTime = System.nanoTime();

            task.run();

            long endTime = System.nanoTime();

            times[i] = (endTime - startTime) / 1_000_000.0;
        }

        Arrays.sort(times);

        return times[2];
    }
}

