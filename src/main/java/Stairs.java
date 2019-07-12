import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Stairs {
    /*
    There's a staircase with N steps, and you can climb 1 or 2 steps at a time.
    Given N, write a function that returns the number of unique ways you can climb the staircase.
    The order of the steps matters.
    For example, if N is 4, then there are 5 unique ways:
            1, 1, 1, 1
            2, 1, 1
            1, 2, 1
            1, 1, 2
            2, 2
    What if, instead of being able to climb 1 or 2 steps at a time, you could climb any number from a set of positive integers X?
    For example, if X = {1, 3, 5}, you could climb 1, 3, or 5 steps at a time. Generalize your function to take in X.
    */

    Stack<Integer> steps = new Stack<>();
    int curLen = 0;
    int[] possibleSteps;
    int stairsLen;
    Map<Integer, Integer> nextSteps = new HashMap<>();

    public Stairs(int[] possibleSteps, int stairsLen) {
        this.possibleSteps = possibleSteps;
        this.stairsLen = stairsLen;
        for (int i = 0; i < possibleSteps.length - 1; i++) {
            nextSteps.put(possibleSteps[i], possibleSteps[i + 1]);
        }
    }

    void addFirstStep() {
        Integer firstStep = getFirstStep();
        steps.push(firstStep);
        curLen += firstStep;
    }

    boolean addNextStep() {
        if (steps.isEmpty()) return false;
        Integer nextStep = getNextStep(steps.peek());
        curLen -= steps.pop();
        if (nextStep == null) {
            return addNextStep();
        } else {
            steps.push(nextStep);
            curLen += nextStep;
        }
        return true;
    }

    void removeLastStep() {
        Integer lastStep = steps.pop();
        curLen -= lastStep;
    }

    public static void main(String[] args) {
        int[] data = {1, 2};
        System.out.println(Arrays.toString(data));
        new Stairs(data, 4).doJob();

        data = new int[]{1, 3, 5};
        System.out.println(Arrays.toString(data));
        new Stairs(data, 7).doJob();

    }

    void doJob() {
        while (true) {
            if (curLen < stairsLen) {
                addFirstStep();
            } else if (curLen >= stairsLen) {
                if (curLen == stairsLen) printPath();
                removeLastStep();
                if (!addNextStep()) return;
            }
        }
    }

    Integer getNextStep(int step) {
        return nextSteps.get(step);
    }

    int getFirstStep() {
        return possibleSteps[0];
    }

    private void printPath() {
        String s = steps.stream().map(Object::toString).collect(Collectors.joining("\t"));
        System.out.println(s);
    }

}
