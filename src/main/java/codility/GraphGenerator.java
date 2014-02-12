package codility;

import java.util.Random;

public class GraphGenerator {

    public int[] generate(int size) {
        int[] result = new int[size];
        Random r = new Random(System.nanoTime());
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                result[i] = 1;
            } else {
                result[i] = r.nextInt(i);
            }
        }

        return result;
    }

}
