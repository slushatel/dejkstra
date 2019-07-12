package leetcode;

public class UniquePaths3 {
    /*
    On a 2-dimensional grid, there are 4 types of squares:

    1 represents the starting square.  There is exactly one starting square.
    2 represents the ending square.  There is exactly one ending square.
    0 represents empty squares we can walk over.
    -1 represents obstacles that we cannot walk over.

Return the number of 4-directional walks from the starting square to the ending square, that walk over every non-obstacle square exactly once.



Example 1:

Input: [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
Output: 2
Explanation: We have the following two paths:
1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)

Example 2:

Input: [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
Output: 4
Explanation: We have the following four paths:
1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)

Example 3:

Input: [[0,1],[2,0]]
Output: 0
Explanation:
There is no path that walks over every empty square exactly once.
Note that the starting and ending square can be anywhere in the grid.



Note:

    1 <= grid.length * grid[0].length <= 20

     */

    int[][] grid;
    boolean[][] gridSteps;
    int rowNum, colNum;
    int startRow, startCol;

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        {
            int[][] grid = {{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 2, -1}};
            System.out.println(new UniquePaths3().uniquePathsIII(grid));
        }
        {
            int[][] grid = {{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 2}};
            System.out.println(new UniquePaths3().uniquePathsIII(grid));
        }
        {
            int[][] grid = {{0, 1}, {2, 0}};
            System.out.println(new UniquePaths3().uniquePathsIII(grid));
        }

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
    }

    public int uniquePathsIII(int[][] grid) {
        this.grid = grid;
        rowNum = grid.length;
        colNum = grid[0].length;
        gridSteps = new boolean[rowNum][colNum];

        setupStartingSquare();
        return getPathsCount(startRow, startCol);
    }

    void setupStartingSquare() {
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (grid[i][j] == 1) {
                    startRow = i;
                    startCol = j;
                    return;
                }
            }
        }
    }

    int getPathsCount(int row, int col) {
        if (row < 0 || row == rowNum || col < 0 || col == colNum) return 0;
        if (grid[row][col] == -1) return 0;
        if (gridSteps[row][col]) return 0;
        if (grid[row][col] == 2) {
            if (allSquaresWasWalkedOver()) {
                return 1;
            } else {
                return 0;
            }
        }

        int res;
        gridSteps[row][col] = true;
        res = getPathsCount(row - 1, col) + getPathsCount(row + 1, col) +
                getPathsCount(row, col - 1) + getPathsCount(row, col + 1);
        gridSteps[row][col] = false;
        return res;
    }

    boolean allSquaresWasWalkedOver() {
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (grid[i][j] == 0 && !gridSteps[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

}
