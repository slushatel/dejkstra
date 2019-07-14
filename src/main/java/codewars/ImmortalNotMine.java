package codewars;

import java.math.BigInteger;

public class ImmortalNotMine {

    /**
     * set true to enable debug
     */
    static boolean debug = false;

    static BigInteger intervalSum(long a, long b) {
        if (b < a) return BigInteger.ZERO;
        BigInteger bigA = BigInteger.valueOf(a);
        BigInteger bigB = BigInteger.valueOf(b);
        return ((bigA.add(bigB)).multiply(bigB.subtract(bigA).add(BigInteger.ONE))).shiftRight(1);
    }

    static BigInteger calculate(long n, long m, long k, long delta) {
        if (m > n) return calculate(m, n, k, delta);
        long nH = java.lang.Long.highestOneBit(n);
        long mH = (m < nH) ? m : nH;
        BigInteger result = intervalSum(Math.max(0, delta - k), delta + nH - k - 1).multiply(BigInteger.valueOf(mH));
        if (n > nH) result = (result.add(calculate(n - nH, mH, k, delta + nH)));
        if (m > mH) result = (result.add(calculate(nH, m - mH, k, delta + mH)));
        if (n > nH && m > mH) result = result.add(calculate(n - nH, m - mH, k, delta + (mH ^ nH)));
        return result;
    }

    static long elderAge(long n, long m, long k, long newp) {
        return calculate(n, m, k, 0).mod(BigInteger.valueOf(newp)).longValue();
    }
}
