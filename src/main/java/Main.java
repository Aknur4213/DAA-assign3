import daa.algorithms.DSU;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {


        DSU dsu = new DSU(5);

        System.out.println(dsu.components());

        dsu.union(0, 1);
        dsu.union(1, 2);
        dsu.union(3, 4);

        System.out.println(dsu.components());
        System.out.println(dsu.find(0) == dsu.find(2));
        System.out.println(dsu.find(0) == dsu.find(4));

        dsu.union(2, 4);

        System.out.println(dsu.components());
        System.out.println(dsu.getDsuOps());

    }
}
