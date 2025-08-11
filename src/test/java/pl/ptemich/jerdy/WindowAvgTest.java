package pl.ptemich.jerdy;

import org.junit.jupiter.api.Test;

public class WindowAvgTest {

    public double[] calcAvg(double[] input, int k) {
        if (k>input.length) {
            throw new RuntimeException("too short table");
        }

        double[] res =  new double[input.length - (k-1)];
        for (int i=0; i<input.length - (k-1); i++) {
            res[i] = 0;
            for (int j=0; j<k; j++) {
                res[i] += input[i+j];
            }
            res[i] = res[i] / k;
        }
        return res;
    }


    @Test
    public void test1() {
        var res = calcAvg(new double[] {1,2,3}, 3);
        assert(res.length == 1);
        assert(res[0] == 2);
    }

    @Test
    public void test2() {
        var res = calcAvg(new double[] {2,4,8,16}, 2);
        assert(res.length == 3);
        assert(res[0] == 3);
        assert(res[1] == 6);
        assert(res[2] == 12);
    }


    boolean isPalindrom(String t1, String t2) {
        if (t1.length() != t2.length()) {
            return false;
        }

        for (int i=0; i<t1.length(); i++) {
            if (t1.charAt(i) != t2.charAt(t1.length()-i-1)) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void testP1() {
        assert(isPalindrom("turbo", "obrut"));
    }

    @Test
    public void testP2() {
        assert(!isPalindrom("nie", "tak"));
    }
}
