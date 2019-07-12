import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AllSubsets2 {
    /*
    Find all possible subsets for a set
    {1, 2, 3}
    =>
    {}, {1}, {2}, {3}, {1, 2}, {1, 3}, {2, 3}, {1, 2, 3}
    (no permutations)
    */

    int[] initialSet = {1, 2, 3};
    Stack<Integer> sets = new Stack<>();
//    List<Integer> usedNumbers = new LinkedList<>();

    public static void main(String[] args) {
        new AllSubsets2().doJob();
    }

    void doJob() {
        printSets();
        while (true) {
            if (sets.size() < initialSet.length) {
                if (!addFirstNumber()) return;
            } else if (sets.size() == initialSet.length) {
                if (!changeToTheNextNumber()) return;
            }
            printSets();
        }
    }

    boolean addFirstNumber() {
        Integer next = getNextNumber();
        if (next != null) {
            sets.push(next);
            return true;
        } else {
            return changeToTheNextNumber();
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

    public static int findIndex(int arr[], int t) {
        int len = arr.length;
        return IntStream.range(0, len)
                .filter(i -> t == arr[i])
                .findFirst() // first occurence
                .orElse(-1); // No element found
    }

    Integer getNextNumber() {
        if (sets.isEmpty()) return initialSet[0];

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
