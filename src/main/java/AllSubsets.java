import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AllSubsets {
    /*
    Find all possible subsets for a set
    {1, 2, 3}
    =>
    {}, {1}, {2}, {3}, {1, 2}, {1, 3}, {2, 3}, {1, 2, 3}
    + permutations
    */

    int[] initialSet = {1, 2, 3};
    Stack<Integer> sets = new Stack<>();
//    List<Integer> usedNumbers = new LinkedList<>();

    public static void main(String[] args) {
        new AllSubsets().doJob();
    }

    void doJob() {
        printSets();
        while (true) {
            if (sets.size() < initialSet.length) {
                addFirstNumber();
            } else if (sets.size() == initialSet.length) {
                if (!changeToTheNextNumber()) return;
            }
            printSets();
        }
    }

    void addFirstNumber() {
        for (int n : initialSet) {
            if (!sets.contains(n)) {
                sets.push(n);
                return;
            }
        }
    }

    boolean changeToTheNextNumber() {
        if (sets.isEmpty()) return false;
//        int last = sets.peek();
        Integer next = getNextNumber();
        sets.pop();
        if (next != null) {
            sets.push(next);
            return true;
        } else {
            return changeToTheNextNumber();
        }
    }

    public static int findIndex(int arr[], int t)
    {
        int len = arr.length;
        return IntStream.range(0, len)
                .filter(i -> t == arr[i])
                .findFirst() // first occurence
                .orElse(-1); // No element found
    }

    Integer getNextNumber() {
        int last = sets.peek();
        for (int i = findIndex(initialSet, last) + 1; i < initialSet.length; i++) {
            if (!sets.contains(initialSet[i])) {
                return initialSet[i];
            }
        }
        return null;
    }

    void printSets() {
        String s = sets.stream().map(Objects::toString).collect(Collectors.joining("\t"));
        System.out.println(s);
    }

}
