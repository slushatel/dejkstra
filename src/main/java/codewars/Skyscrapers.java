package codewars;

import java.util.*;

public class Skyscrapers {
    /*
    In a grid of 7 by 7 squares you want to place a skyscraper in each square with only some clues:

    The height of the skyscrapers is between 1 and 7
    No two skyscrapers in a row or column may have the same number of floors
    A clue is the number of skyscrapers that you can see in a row or column from the outside
    Higher skyscrapers block the view of lower skyscrapers located behind them

Can you write a program that can solve this puzzle in time?

This kata is based on 4 By 4 Skyscrapers and 6 By 6 Skyscrapers by FrankK. By now, examples should be superfluous;
you should really solve Frank's kata first, and then probably optimise some more.
A naive solution that solved a 4×4 puzzle within 12 seconds
might need time somewhere beyond the Heat Death of the Universe for this size. It's quite bad.
Task

Create

Skyscrapers.solvePuzzle(int[] clues)

Clues are passed in as an int[28] array.
The return value is an int[7][7] array of arrays.

All puzzles have one possible solution.
All this is the same as with the earlier kata.

Caveat: The tests for this kata have been tailored to run in ~10 seconds with the JavaScript reference solution.
You'll need to do better than that! Please note the optimization tag.

Conceptis Puzzles have heaps of these puzzles, from 5×5 (they don't even bother with 4×4) up to 7×7
and unsolvable within CodeWars time constraints. Old puzzles from there were used for the tests.
They also have lots of other logic, numbers and mathematical puzzles, and their puzzle user interface is generally nice, very nice.
(It is, however, Flash, and their mobile offerings are far fewer. Desktop PC recommended.)

     */

    // the size of the square
    int n;
    // array where rows and columns are equal to the number of visible skyscrapers (column is 0 if we have only row information and vice versa)
    // If we do not have any information, the permutation is only in 'allPermutations' array
    List<Integer>[][] permutations;
    // heights of skyscrapers (1, 2, 3 ...)
    int[] heights;
    List<Clue> allClues;
    // all possible permutations of given size n
    int[][] allPermutations;

    // keeps the index during 'allPermutations' filling
    int fillPermutationsCurrentIndex = 0;

    // keeps indexes of current permutations for all clues
    int[] currentPermutation;

    class Clue {
        int leftTop;
        int rightBottom;
        int lineIndex;
        boolean isVertical;

        public Clue(int leftTop, int rightBottom, int lineIndex, boolean isVertical) {
            this.leftTop = leftTop;
            this.rightBottom = rightBottom;
            this.lineIndex = lineIndex;
            this.isVertical = isVertical;
        }
    }

    public static int[][] solvePuzzle(int[] clues) {
        return new Skyscrapers().doJob(clues);
    }

    int[][] doJob(int[] clues) {
        n = clues.length / 4;
        // fill heights
        heights = new int[n];
        for (int i = 0; i < n; i++) {
            heights[i] = i + 1;
        }
        // fill permutations
        permutations = new ArrayList[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                permutations[i][j] = new ArrayList<>();
            }
        }
        allPermutations = new int[factor(n)][n];
        getPermutations(heights, 0);

        // sort clues
        allClues = new ArrayList<>(2 * n);
        sortClues(clues);

        // index in all permutations (when there are no clues) or in permutations (if there are clues)
        currentPermutation = new int[2 * n];

        if (!fillFromClues(0)) throw new RuntimeException();

        return getResult();
    }

    // return the result (gather information from all arrays using permutation indexes from the currentPermutation array)
    int[][] getResult() {
        int[][] res = new int[n][n];
        for (int i = 0; i < 2 * n; i++) {
            Clue clue = allClues.get(i);
            if (!clue.isVertical) {
                int permIndex;
                if (clue.leftTop > 0 || clue.rightBottom > 0) {
                    permIndex = permutations[clue.leftTop][clue.rightBottom].get(currentPermutation[i]);
                } else {
                    permIndex = currentPermutation[i];
                }
                res[clue.lineIndex] = allPermutations[permIndex];
            }
        }
        return res;
    }

    // main recursive method. It fills the square according to the first clue, than the second and so on.
    // every time it checks if the current clue do not brake the rules (has the same numbers in common with crossing lines
    // and do not have the same numbers on similar places with parallel lines)
    boolean fillFromClues(int clueIndex) {
        if (clueIndex == 2 * n) return true;
        Clue clue = allClues.get(clueIndex);

        int[] permutationPattern = getPossiblePermutationPattern(clueIndex);

        if (clue.leftTop > 0 || clue.rightBottom > 0) {
            for (int i = 0; i < permutations[clue.leftTop][clue.rightBottom].size(); i++) {
                currentPermutation[clueIndex] = i;
                if (!checkCrossedPermutationOnPattern(permutationPattern, getPermutationByClueIndex(clueIndex))) continue;
                if (!checkParallelClues(clueIndex)) continue;
                if (fillFromClues(clueIndex + 1)) return true;
            }
        } else {
            for (int i = 0; i < allPermutations.length; i++) {
                currentPermutation[clueIndex] = i;
                if (!checkCrossedPermutationOnPattern(permutationPattern, getPermutationByClueIndex(clueIndex))) continue;
                if (!checkParallelClues(clueIndex)) continue;
                if (fillFromClues(clueIndex + 1)) return true;
            }
        }
        return false;
    }

    // check all patterns that crosses current and find common numbers (in the place of intersection)
    int[] getPossiblePermutationPattern(int clueIndex) {
        int[] res = new int[n];
        Clue clue = allClues.get(clueIndex);
        for (int i = 0; i <= clueIndex; i++) {
            Clue clue2 = allClues.get(i);
            if (clue.isVertical != clue2.isVertical) {
                res[clue2.lineIndex] = getCrossNumber(i, clue.lineIndex);
            }
        }
        return res;
    }

    // check if permutation suites to pattern. pattern is an array with zeros on empty places: [0, 1, 3]
    // it means that permutation [2, 1, 3] passes the check, but [2, 3, 1] do not (1 and 3 are not in the same places as in pattern)
    boolean checkCrossedPermutationOnPattern(int[] pattern, int[] permutation) {
        for (int i = 0; i < n; i++) {
            if (pattern[i] != 0 && pattern[i] != permutation[i]) return false;
        }
        return true;
    }

    // check two horizontal or vertical permutations clues
    boolean checkParallelClues(int clueIndex) {
        Clue clue = allClues.get(clueIndex);
        for (int i = 0; i < clueIndex; i++) {
            Clue clue2 = allClues.get(i);
            if (clue.isVertical == clue2.isVertical) {
                if (haveIdenticalNumbers(clueIndex, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if two permutations have the same numbers at the same place
    boolean haveIdenticalNumbers(int clueInd, int clueInd2) {
        int[] perm1 = getPermutationByClueIndex(clueInd);
        int[] perm2 = getPermutationByClueIndex(clueInd2);
        for (int i = 0; i < n; i++) {
            if (perm1[i] == perm2[i]) return true;
        }
        return false;
    }

    // get n-th number from the line that was created by clue with the index equal to clueIndex
    int getCrossNumber(int clueIndex, int lineIndex) {
        int[] perm1 = getPermutationByClueIndex(clueIndex);
        return perm1[lineIndex];
    }

    // find the value of permutation by index.
    // The index of permutation is stored in 'currentPermutations' for each clue
    // The index of permutation points to 'permutations' array if the clue is 'not empty'
    // or to 'allPermutations' array
    int[] getPermutationByClueIndex(int clueIndex) {
        Clue clue = allClues.get(clueIndex);
        int permIndex;
        if (clue.leftTop > 0 || clue.rightBottom > 0) {
            permIndex = permutations[clue.leftTop][clue.rightBottom].get(currentPermutation[clueIndex]);
        } else {
            permIndex = currentPermutation[clueIndex];
        }
        return allPermutations[permIndex];
    }

    // n!
    int factor(int n) {
        if (n == 0) return 1;
        return n * factor(n - 1);
    }

    // sort clues to find more important clues
    void sortClues(int[] clues) {
        for (int i = 0; i < n; i++) {
            allClues.add(new Clue(clues[i], clues[3 * n - 1 - i], i, true));
            allClues.add(new Clue(clues[4 * n - 1 - i], clues[n + i], i, false));
        }
        allClues.sort(Comparator.comparing(this::getClueComplexity));
    }

    // returns value in direct ratio to the number of possible permutations
    // all permutations if there is no clue, or number of permutations from 'permutations'
    Integer getClueComplexity(Clue clue) {
        if (clue.leftTop == 0 && clue.rightBottom == 0) return allPermutations.length;
        return permutations[clue.leftTop][clue.rightBottom].size();
    }

    // fill allPermutations[] with all possible permutations of number sequence (heights)
    // fill permutations[][]. Indexes are numbers of visible skyscrapers from the top and left.
    // Values - list of all suitable permutations
    void getPermutations(int[] data, int index) {
        if (index == n - 1) {
            return;
        }
        if (index == 0) {
            putPermutation(data);
        }

        getPermutations(data, index + 1);

        for (int i = index + 1; i < n; i++) {
            swap(data, index, i);
            putPermutation(data);
            getPermutations(data, index + 1);
            swap(data, i, index);
        }
    }

    void putPermutation(int[] data) {
        int seenFromLeft = 0;
        int seenFromRight = 0;
        int leftHeight = 0;
        int rightHeight = 0;
        for (int i = 0; i < data.length; i++) {
            int height = data[i];
            if (height > leftHeight) {
                seenFromLeft++;
                leftHeight = height;
            }
            height = data[data.length - i - 1];
            if (height > rightHeight) {
                seenFromRight++;
                rightHeight = height;
            }
        }
        allPermutations[fillPermutationsCurrentIndex] = Arrays.copyOf(data, data.length);
        permutations[seenFromLeft][seenFromRight].add(fillPermutationsCurrentIndex);
        // extra copy
        permutations[0][seenFromRight].add(fillPermutationsCurrentIndex);
        permutations[seenFromLeft][0].add(fillPermutationsCurrentIndex);
        fillPermutationsCurrentIndex++;
    }

    void swap(int[] data, int a, int b) {
        int tmp = data[a];
        data[a] = data[b];
        data[b] = tmp;
    }
}
