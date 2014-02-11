package codility;

import org.junit.Assert;
import org.junit.Test;

public class SolutionTest {

    @Test
    public void test() {
        Solution solution = new Solution();

        int[] T = { 1, 2, 3, 3, 2, 1, 4 };
        int[] expected = { 2, 0, 6, 3, 5 };

        int[] result = solution.solution(2, T);

        Assert.assertEquals(expected.length, result.length);
        for (int i = 0; i < result.length; i++) {
            Assert.assertEquals(expected[i], result[i]);
        }

    }
}
