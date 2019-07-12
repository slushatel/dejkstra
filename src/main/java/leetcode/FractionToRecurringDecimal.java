package leetcode;

import java.util.HashMap;
import java.util.Map;

public class FractionToRecurringDecimal {

    public static void main(String[] args) {
        int num = -1;
        int den = -2147483648;

        System.out.println(num*1.0/den);
        System.out.println(new FractionToRecurringDecimal().fractionToDecimal(num, den));
    }


    public String fractionToDecimal(int numerator, int denominator) {
        StringBuilder res = new StringBuilder();
        if (numerator < 0 && denominator > 0 || numerator > 0 && denominator < 0) {
            res.append("-");
        }
        long numer = Math.abs((long) numerator);
        long denomin = Math.abs((long) denominator);

        res.append(numer / denomin);
        long remain = numer % denomin;
        if (remain == 0) return res.toString();
        res.append(".");
        Map<Long, Integer> usedNominators = new HashMap<>();
        int ind = res.length();
        while (remain != 0) {
            usedNominators.put(remain, ind);
            res.append(remain * 10L / denomin);
            remain = remain * 10L % denomin;
            if (usedNominators.containsKey(remain)) {
                // add period
                int i = usedNominators.get(remain);
                res = new StringBuilder(res.substring(0, i) + "(" + res.substring(i) + ")");
                break;
            }
            ind++;
        }
        return res.toString();
    }
}
