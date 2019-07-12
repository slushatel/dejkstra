package codewars;

public class Immortal {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /*
    In the nation of CodeWars, there lives an Elder who has lived for a long time. Some people call him the Grandpatriarch, but most people just refer to him as the Elder.

There is a secret to his longetivity: he has a lot of young worshippers, who regularly perform a ritual to ensure that the Elder stays immortal:

    The worshippers lines up in a magic rectangle, of dimensions m and n.
    They channel their will to wish for the Elder. In this magic rectangle, any worshipper can donate time equal to the xor of the column and the row (zero-indexed) he's on, in seconds, to the Elder.
    However, not every ritual goes perfectly. The donation of time from the worshippers to the Elder will experience a transmission loss l (in seconds). Also, if a specific worshipper cannot channel more than l seconds, the Elder will not be able to receive this worshipper's donation.

The estimated age of the Elder is so old it's probably bigger than the total number of atoms in the universe. However, the lazy programmers (who made a big news by inventing the Y2K bug and other related things) apparently didn't think thoroughly enough about this, and so their simple date-time system can only record time from 0 to t-1 seconds. If the elder received the total amount of time (in seconds) more than the system can store, it will be wrapped around so that the time would be between the range 0 to t-1.

Given m, n, l and t, please find the number of seconds the Elder has received, represented in the poor programmer's date-time system.

(Note: t will never be bigger than 2^32 - 1, and in JS, 2^26 - 1.)

Example:

m=8, n=5, l=1, t=100

Let's draw out the whole magic rectangle:
0 1 2 3 4 5 6 7
1 0 3 2 5 4 7 6
2 3 0 1 6 7 4 5
3 2 1 0 7 6 5 4
4 5 6 7 0 1 2 3

Applying a transmission loss of 1:
0 0 1 2 3 4 5 6
0 0 2 1 4 3 6 5
1 2 0 0 5 6 3 4
2 1 0 0 6 5 4 3
3 4 5 6 0 0 1 2

Adding up all the time gives 105 seconds.

Because the system can only store time between 0 to 99 seconds, the first 100 seconds of time will be lost, giving the answer of 5.

This is no ordinary magic (the Elder's life is at stake), so you need to care about performance. All test cases (900 tests) can be passed within 1 second, but naive solutions will time out easily. Good luck, and do not displease the Elder.

     */
    static long elderAge(long n, long m, long k, long newp) {

        return 0;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 33; i++) {
            int n = 1;
            for (int j = 0; j < 33; j++) {
                String s = "\t" + (i ^ j);
                if ((i ^ j) == 0) {
                    s = ANSI_BLUE + s + ANSI_RESET;
                } else if (j == n) {
                    s = ANSI_RED + s + ANSI_RESET;
                    n *= 2;
                }
                System.out.print(s);
            }
            System.out.println();
        }


        int m = 19;
        int n = 14;
        int res = 0;
        int t1 = 0;
        while (true) {
            int pow = 1;
            while (pow * 2 < m) {
                pow = pow * 2;
            }
            m -= pow;
            while (pow > 0 && m > 0) {
                pow = pow / 2;
                if (pow < m) m -= pow;
            }

//            2 ^ 5 > m = 19 > t2 = 2 ^ 4
//            res += (t1 + (t1 + t2) - 1) / 2 * (t2 - t1) * n = (0 + 2 ^ 4 - 1) / 2 * 2 ^ 4 * 14;
//            m = n = 14, n = m –t2 = 3;
//            t1 = t2;
//            t2 = 12 = 2 ^ 3 + 2 ^ 2 < m
        }


    }
}
