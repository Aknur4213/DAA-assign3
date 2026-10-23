
package daa;

import daa.algorithms.DSU;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DSUTest {

    @Test
    void testInitialComponents() {
        DSU dsu = new DSU(5);

        assertEquals(5, dsu.components());

        for (int i = 0; i < 5; i++) {
            assertEquals(i, dsu.find(i));
        }
    }

    @Test
    void testUnionAndFind() {
        DSU dsu = new DSU(5);

        assertTrue(dsu.union(0, 1));
        assertTrue(dsu.union(1, 2));

        assertEquals(dsu.find(0), dsu.find(2));
        assertNotEquals(dsu.find(0), dsu.find(3));

        assertEquals(3, dsu.components());
    }

    @Test
    void testRepeatedUnion() {
        DSU dsu = new DSU(4);

        assertTrue(dsu.union(0, 1));
        assertFalse(dsu.union(0, 1));
        assertFalse(dsu.union(1, 0));

        assertEquals(3, dsu.components());
        assertEquals(1, dsu.getSuccessUnions());
    }

    @Test
    void testInvalidIndex() {
        DSU dsu = new DSU(5);

        assertThrows(IndexOutOfBoundsException.class,
                () -> dsu.find(8));

        assertThrows(IndexOutOfBoundsException.class,
                () -> dsu.union(-1, 2));
    }

    @Test
    void testRandomUnions() {
        int n = 100;
        DSU dsu = new DSU(n);

        int[] reference = new int[n];

        for (int i = 0; i < n; i++) {
            reference[i] = i;
        }

        Random rnd = new Random(42);

        for (int step = 0; step < 1000; step++) {
            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);

            int labelA = reference[a];
            int labelB = reference[b];

            dsu.union(a, b);

            if (labelA != labelB) {
                for (int i = 0; i < n; i++) {
                    if (reference[i] == labelB) {
                        reference[i] = labelA;
                    }
                }
            }

            int expectedComponents = 0;

            for (int i = 0; i < n; i++) {
                if (reference[i] == i) {
                    expectedComponents++;
                }

                for (int j = 0; j < n; j++) {
                    boolean expected =
                            reference[i] == reference[j];

                    boolean actual =
                            dsu.find(i) == dsu.find(j);

                    assertEquals(expected, actual);
                }
            }

            assertEquals(expectedComponents, dsu.components());
        }
    }
}
