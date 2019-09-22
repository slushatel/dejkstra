package codewars;

import java.util.*;

public class SlidingPuzzle {
/*
A sliding puzzle is a combination puzzle that challenges a player to slide (frequently flat) pieces along certain routes
(usually on a board) to establish a certain end-configuration.

Your goal for this kata is to write a function that produces a sequence of tile movements that solves the puzzle.
Input

An n col n array/list comprised of integer values ranging from 0 to n^2 - 1 (inclusive), which represents a square grid of tiles.
Note that there will always be one empty tile (represented by 0) to allow for movement of adjacent tiles.
Output

An array/list comprised of any (but not necessarily all) of the integers from 1 to n^2 - 1, inclusive.
This represents the sequence of tile moves for a successful transition from the initial unsolved state to the solved state.
If the puzzle is unsolvable, return null(JavaScript, Java, PHP) or None(Python) or the vector {0} (C++).
Test Example

let simpleExample = [
    [ 1, 2, 3, 4],
    [ 5, 0, 6, 8],
    [ 9,10, 7,11],
    [13,14,15,12]
];
slidePuzzle(simpleExample); // [6,7,11,12]

// TRANSITION SEQUENCE:
[ 1, 2, 3, 4]    [ 1, 2, 3, 4]    [ 1, 2, 3, 4]    [ 1, 2, 3, 4]    [ 1, 2, 3, 4]
[ 5, 0, 6, 8]    [ 5, 6, 0, 8]    [ 5, 6, 7, 8]    [ 5, 6, 7, 8]    [ 5, 6, 7, 8]
[ 9,10, 7,11] -> [ 9,10, 7,11] -> [ 9,10, 0,11] -> [ 9,10,11, 0] -> [ 9,10,11,12]
[13,14,15,12]    [13,14,15,12]    [13,14,15,12]    [13,14,15,12]    [13,14,15, 0]

// NOTE: Your solution does not need to follow this exact sequence to pass

Technical Details

    Input will always be valid.
    The range of values for n are: 10 >= n >= 3

 */

	private final int[][] puzzle;
	private final int n;
	private final int shiftN;

	public SlidingPuzzle(int[][] puzzle, int shiftN) {
		this.puzzle = puzzle;
		n = puzzle.length;
		this.shiftN = shiftN;
	}

	public List<Integer> solve() {
		PriorityQueue<TreeNode> queue = new PriorityQueue<>(Comparator.comparingInt(v -> v.moves + v.hScore));
		queue.add(new TreeNode(null, new PuzzleState(puzzle), 0));

		while (!queue.isEmpty()) {
			TreeNode currentNode = queue.poll();

			if (currentNode.puzzleState.solved())
				return createPath(currentNode);

			for (PuzzleState puzzleState : currentNode.puzzleState.getNeighbours()) {
				if (currentNode.parent != null && puzzleState.equals(currentNode.parent.puzzleState)) continue;
				queue.add(new TreeNode(currentNode, puzzleState, currentNode.moves + 1));
			}
		}

		return null;
	}

	private List<Integer> createPath(TreeNode node) {
		List<Integer> res = new ArrayList<>();
		while (node.parent != null) {
			res.add(0, node.puzzleState.changedTile);
			node = node.parent;
		}
		return res;
	}

	// game tree
	class TreeNode {
		final TreeNode parent;
		final PuzzleState puzzleState;
		final int moves;
		final int hScore;

		public TreeNode(TreeNode parent, PuzzleState puzzleState, int moves) {
			this.parent = parent;
			this.puzzleState = puzzleState;
			this.moves = moves;
			this.hScore = puzzleState.distance;
		}
	}

	class PuzzleState {
		final int[][] puzzle;
		final int distance;
		int changedTile;

		public PuzzleState(int[][] puzzle, int changedTile) {
			this.puzzle = copyPuzzle(puzzle);
			distance = getDistance();
			this.changedTile = changedTile;
		}

		public PuzzleState(int[][] puzzle) {
			this(puzzle, -1);
		}

		public boolean solved() {
			return distance == 0;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			PuzzleState that = (PuzzleState) o;
			return Arrays.deepEquals(puzzle, that.puzzle);
		}

		private int getDistance() {
			int res = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (puzzle[i][j] == 0) continue;
					int num = puzzle[i][j] - 1;
					int row = num / (n+shiftN) - shiftN;
					int col = num % (n+shiftN) - shiftN;
					res += Math.abs(row - i) + Math.abs(col - j);
				}
			}
			return res;
		}

		private List<PuzzleState> getNeighbours() {
			List<PuzzleState> res = new ArrayList<>();
			int row = 0, col = 0;
			loop:
			for (row = 0; row < n; row++) {
				for (col = 0; col < n; col++) {
					int num = puzzle[row][col];
					if (num == 0) break loop;
				}
			}
			if (row > 0) {
				int[][] newPuzzle = copyPuzzle(puzzle);
				newPuzzle[row][col] = newPuzzle[row - 1][col];
				newPuzzle[row - 1][col] = 0;
				res.add(new PuzzleState(newPuzzle, newPuzzle[row][col]));
			}
			if (row < n - 1) {
				int[][] newPuzzle = copyPuzzle(puzzle);
				newPuzzle[row][col] = newPuzzle[row + 1][col];
				newPuzzle[row + 1][col] = 0;
				res.add(new PuzzleState(newPuzzle, newPuzzle[row][col]));
			}
			if (col > 0) {
				int[][] newPuzzle = copyPuzzle(puzzle);
				newPuzzle[row][col] = newPuzzle[row][col - 1];
				newPuzzle[row][col - 1] = 0;
				res.add(new PuzzleState(newPuzzle, newPuzzle[row][col]));
			}
			if (col < n - 1) {
				int[][] newPuzzle = copyPuzzle(puzzle);
				newPuzzle[row][col] = newPuzzle[row][col + 1];
				newPuzzle[row][col + 1] = 0;
				res.add(new PuzzleState(newPuzzle, newPuzzle[row][col]));
			}
			return res;
		}

		private int[][] copyPuzzle(int[][] puzzle) {
			int[][] res = new int[n][n];
			for (int row = 0; row < n; row++) {
				for (int col = 0; col < n; col++) {
					res[row][col] = puzzle[row][col];
				}
			}
			return res;
		}
	}

	public static void main(String[] args) {
		{
			int[][] data = {
					{1, 2, 3, 4},
					{5, 0, 6, 8},
					{9, 10, 7, 11},
					{13, 14, 15, 12}
			};
			List<Integer> res = new SlidingPuzzle(data, 0).solve();
			if (res == null) System.out.println("no solution");
			else
				for (Integer re : res) {
					System.out.println(re);
				}
		}
//		{
//			int[][] data = {{7, 14, 26, 10, 8, 18}, {9, 1, 2, 12, 6, 29}, {31, 0, 5, 16, 3, 4},
//					{13, 21, 15, 24, 25, 35}, {19, 28, 34, 17, 11, 22}, {32, 27, 20, 33, 30, 23}};
//			List<Integer> res = new SlidingPuzzle(data).solve();
//			if (res == null) System.out.println("no solution");
//			else
//				for (Integer re : res) {
//					System.out.println(re);
//				}
//		}
	}

}
