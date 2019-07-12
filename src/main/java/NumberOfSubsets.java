public class NumberOfSubsets {
    /*
    There is a set of positive numbers.
    Find the number of subsets with the given sum of elements.
    For example, for {2, 4, 6, 10} and given number 16 it should return 2, because  we have 2 subsets with the sum equal to 16: {2, 4, 10} and {6, 10}
    */

    int[] initialData = {2, 4, 6, 10};
    int givenSum = 16;

    public static void main(String[] args) {
        new NumberOfSubsets().doJob();
    }

    void doJob() {
        System.out.println(getSubsetCount(0, givenSum));
    }

    int getSubsetCount(int n, int sum) {
        if (n == initialData.length) return 0;
        if (sum == initialData[n]) return 1;
        if (sum < 0) return 0;
        return getSubsetCount(n+1, sum) + getSubsetCount(n+1, sum - initialData[n]);
    }
}
