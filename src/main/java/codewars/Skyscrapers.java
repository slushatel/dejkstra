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

    int n;
    List<Integer>[][] permutations;
    int[] heights;
    //    List<Clue> cluesVert = new LinkedList<>();
//    List<Clue> cluesHorizont = new LinkedList<>();
//    List<Clue> cluesVert2 = new LinkedList<>();
//    List<Clue> cluesHorizont2 = new LinkedList<>();
    List<Clue> allClues;
    int[][] allPermutations;
    int fillPermutationsCurrentIndex = 0;

    int currentClue = 0;
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

        allClues = new ArrayList<>(2 * n);
        sortClues(clues);

        // index in all permutations (when there are no clues) or in permutations (if there are clues)
        currentPermutation = new int[2 * n];

        if (!fillFromClues(0)) throw new RuntimeException();

        // return result
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

    boolean fillFromClues(int clueIndex) {
        if (clueIndex == 2 * n) return true;
        Clue clue = allClues.get(clueIndex);
        if (clue.leftTop > 0 || clue.rightBottom > 0) {
            for (int i = 0; i < permutations[clue.leftTop][clue.rightBottom].size(); i++) {
                currentPermutation[clueIndex] = i;
                if (!checkClues(clueIndex)) continue;
                if (fillFromClues(clueIndex + 1)) return true;
            }
        } else {
            for (int i = 0; i < allPermutations.length; i++) {
                currentPermutation[clueIndex] = i;
                if (!checkClues(clueIndex)) continue;
                if (fillFromClues(clueIndex + 1)) return true;
            }
        }
        return false;
    }

    boolean checkClues(int clueIndex) {
        Clue clue = allClues.get(clueIndex);
        for (int i = 0; i <= clueIndex; i++) {
            Clue clue2 = allClues.get(i);
            if (clue.isVertical != clue2.isVertical) {
                if (!isSameCrossNumber(clueIndex, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean isSameCrossNumber(int clueInd, int clueInd2) {
        Clue clue = allClues.get(clueInd);
        int[] perm1 = getPermutationByClueIndex(clueInd);
        Clue clue2 = allClues.get(clueInd2);
        int[] perm2 = getPermutationByClueIndex(clueInd2);
        return perm1[clue2.lineIndex] == perm2[clue.lineIndex];
    }

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

    int factor(int n) {
        if (n == 0) return 1;
        return n * factor(n - 1);
    }

    void sortClues(int[] clues) {
        for (int i = 0; i < n; i++) {
            allClues.add(new Clue(clues[i], clues[3 * n - 1 - i], i, true));
            allClues.add(new Clue(clues[4 * n - 1 - i], clues[n + i], i, false));
        }
        allClues.sort(Comparator.comparing(this::getCluePower));
    }

    Integer getCluePower(Clue clue) {
        if (clue.leftTop > 0 && clue.rightBottom > 0) return 0;
        if (clue.leftTop > 0 || clue.rightBottom > 0) return 1;
        return 2;
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

    void swap(int[] data, int a, int b) {
        int tmp = data[a];
        data[a] = data[b];
        data[b] = tmp;
    }
}
