package codility;

public class GraphGenerator {

    public int[] generate(int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                result[i] = 1;
            } else {
                result[i] = ((int) Math.random()) % i;
            }
        }

        return result;
    }

}
