package codewars;

import java.util.*;

public class SlidingPuzzle2 {
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
	private int n;

	public SlidingPuzzle2(int[][] puzzle) {
		this.puzzle = puzzle;
		n = puzzle.length;
	}

	Coordinates targetToCoordinates(int target) {
		return new Coordinates((target - 1) / n, (target - 1) % n);
	}

	int coordinatesToTarget(Coordinates coord) {
		return n * coord.row + coord.col + 1;
	}

	public List<Integer> solve() {
		if (!isSolvable(puzzle)) return null;

		List<Integer> res = new LinkedList<>();


		// per line approach
		Set<Coordinates> exclusions = new HashSet<>();
		int currentRow = 0;
		int currentCol = 0;
		while (currentCol < n - 3) {
			// move row
			Coordinates beforeLastInTheLine = new Coordinates(currentRow, n - 2);
			Coordinates lastInTheLine = new Coordinates(currentRow, n - 1);
			Coordinates lastInTheNextLine = new Coordinates(currentRow + 1, n - 1);
			List<Coordinates> line = new ArrayList<>();
			for (int col = currentCol; col < n - 2; col++) {
				line.add(new Coordinates(currentRow, col));
			}
			moveLine(res, beforeLastInTheLine, lastInTheLine, lastInTheNextLine, exclusions, line);
			currentRow++;

			// move column
			beforeLastInTheLine = new Coordinates(n - 2,currentCol);
			lastInTheLine = new Coordinates(n - 1, currentCol);
			lastInTheNextLine = new Coordinates(n - 1, currentCol + 1);
			line = new ArrayList<>();
			for (int row = currentRow; row < n - 2; row++) {
				line.add(new Coordinates(row, currentCol));
			}
			moveLine(res, beforeLastInTheLine, lastInTheLine, lastInTheNextLine, exclusions, line);
			currentCol++;
		}

		// ML approach for small matrix
		int shiftN = n-3;
		int[][] data = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				data[i][j] = puzzle[i+ shiftN][j+shiftN];
			}
		}
		res.addAll(new SlidingPuzzle(data, shiftN).solve());

		return res;
	}

	void moveLine(List<Integer> res, Coordinates beforeLastInTheLine, Coordinates lastInTheLine, Coordinates lastInTheNextLine,
				  Set<Coordinates> exclusions, List<Coordinates> line) {
		Coordinates bottomRight = new Coordinates(n - 1, n - 1);

		//all except last two
		for (Coordinates coordTo : line) {
			moveTile(res, coordTo, findPosition(coordinatesToTarget(coordTo)), exclusions);
		}

		// last two

		// nothing to move
		if (findPosition(coordinatesToTarget(lastInTheLine)).equals(lastInTheLine) &&
				findPosition(coordinatesToTarget(beforeLastInTheLine)).equals(beforeLastInTheLine)) return;

		// move last number away from the line
		Coordinates coordOfLast = findPosition(coordinatesToTarget(lastInTheLine));
		if (coordOfLast.equals(beforeLastInTheLine) || coordOfLast.equals(lastInTheLine)) {
			moveTile(res, bottomRight, coordOfLast, exclusions);
		}

		moveTile(res, lastInTheLine, findPosition(coordinatesToTarget(beforeLastInTheLine)), exclusions);
		exclusions.add(lastInTheLine);

		moveTile(res, lastInTheNextLine, findPosition(coordinatesToTarget(lastInTheLine)), exclusions);
		exclusions.remove(exclusions.size() - 1);
		exclusions.add(lastInTheNextLine);

		moveTile(res, beforeLastInTheLine, lastInTheLine, exclusions);
		exclusions.remove(exclusions.size() - 1);

		moveTile(res, lastInTheLine, lastInTheNextLine, exclusions);
	}

	void moveTile(List<Integer> res, Coordinates coordTo, Coordinates coordFrom, Set<Coordinates> exclusions) {
		Coordinates coordZeroTo;
		while (!coordTo.equals(coordFrom)) {
			if (coordTo.equals(coordFrom)) return;
			Coordinates coordZeroFrom = findPosition(0);
			if (coordFrom.row == coordTo.row && coordFrom.col > coordTo.col) {
				coordZeroTo = new Coordinates(coordFrom.row, coordFrom.col - 1);
			} else if (coordFrom.row == coordTo.row && coordFrom.col < coordTo.col) {
				coordZeroTo = new Coordinates(coordFrom.row, coordFrom.col + 1);
			} else if (coordFrom.col == coordTo.col && coordFrom.row > coordTo.row) {
				coordZeroTo = new Coordinates(coordFrom.row - 1, coordFrom.col);
			} else if (coordFrom.col == coordTo.col && coordFrom.row < coordTo.row) {
				coordZeroTo = new Coordinates(coordFrom.row + 1, coordFrom.col);
			} else if (coordFrom.col > coordTo.col && coordFrom.row > coordTo.row) {
				// is inside the rectangle
				coordZeroTo = getNearestCoordinate(coordZeroFrom,
						new Coordinates(coordFrom.row, coordFrom.col - 1), new Coordinates(coordFrom.row - 1, coordFrom.col));
			} else if (coordFrom.row < coordTo.row) {
				// return into rectangle
				coordZeroTo = new Coordinates(coordFrom.row + 1, coordFrom.col);
			} else if (coordFrom.col < coordTo.col) {
				// return into rectangle
				coordZeroTo = new Coordinates(coordFrom.row, coordFrom.col + 1);
			} else {
				throw new UnsupportedOperationException();
			}
			exclusions.add(coordFrom);
			List<Integer> moves = moveZeroTile2(coordZeroFrom, coordZeroTo, exclusions);
			exclusions.remove(coordFrom);
			res.addAll(moves);
			res.add(puzzle[coordFrom.row][coordFrom.col]);
			swapTiles(puzzle, coordFrom.row, coordFrom.col, coordZeroTo.row, coordZeroTo.col);
			coordFrom = coordZeroTo;
		}
		exclusions.add(coordTo);
	}

	//////////////////////////////////////////////////

	class PointHolder {
		Coordinates coord;
		int fScore;

		public PointHolder(Coordinates coord, int fScore) {
			this.coord = coord;
			this.fScore = fScore;
		}
	}

	List<Integer> reconstruct_path(Map<Coordinates, Coordinates> cameFrom, Coordinates current) {
		List<Integer> res = new LinkedList<>();
		List<Coordinates> coordinates = new LinkedList<>();
		while (current != null) {
			coordinates.add(0, current);
			Coordinates prev = cameFrom.get(current);
			current = prev;
		}

		for (int i = 0; i < coordinates.size() - 1; i++) {
			res.add(puzzle[coordinates.get(i + 1).row][coordinates.get(i + 1).col]);
			swapTiles(puzzle, coordinates.get(i).row, coordinates.get(i).col, coordinates.get(i + 1).row, coordinates.get(i + 1).col);
		}

		return res;
	}

	public List<Integer> moveZeroTile2(Coordinates from, Coordinates to, Set<Coordinates> exclusions) {
		Set<Coordinates> openSet = new HashSet<>();
		openSet.add(from);
		PriorityQueue<PointHolder> queue = new PriorityQueue<>(Comparator.comparingInt(v -> v.fScore));
		queue.add(new PointHolder(from, getDist(from, to)));

		// For node n, cameFrom[n] is the node immediately preceding it on the cheapest path from start to n currently known.
		Map<Coordinates, Coordinates> cameFrom = new HashMap<>();

		// For node n, gScore[n] is the cost of the cheapest path from start to n currently known.
		Map<Coordinates, Integer> gScore = new HashMap<>();
		gScore.put(from, 0);

		// For node n, fScore[n] := gScore[n] + h(n).
		Map<Coordinates, Integer> fScore = new HashMap<>();
		fScore.put(from, getDist(from, to));

		Set<Coordinates> closedSet = new HashSet<>();

		while (!openSet.isEmpty()) {
			Coordinates current = queue.poll().coord;
			openSet.remove(current);
			if (current.equals(to))
				return reconstruct_path(cameFrom, current);

			closedSet.add(current);

			for (Coordinates neighbor : getNeighbors(current, exclusions)) {
				if (closedSet.contains(neighbor)) continue;
				int tentative_gScore = gScore.get(current) + 1;
				if (tentative_gScore < gScore.computeIfAbsent(neighbor, i -> Integer.MAX_VALUE)) {
					// This path to neighbor is better than any previous one. Record it!
					cameFrom.put(neighbor, current);
					gScore.put(neighbor, tentative_gScore);
					fScore.put(neighbor, gScore.get(neighbor) + getDist(neighbor, to));
					if (!openSet.contains(neighbor))
						openSet.add(neighbor);
					queue.add(new PointHolder(neighbor, fScore.get(neighbor)));
				}
			}

		}
		// Open set is empty but goal was never reached
		return null;

	}

	List<Coordinates> getNeighbors(Coordinates current, Set<Coordinates> exclusions) {
		List<Coordinates> res = new ArrayList<>();
		if (current.row > 0) {
			Coordinates coord = new Coordinates(current.row - 1, current.col);
			if (!exclusions.contains(coord))
				res.add(coord);
		}
		if (current.row < n - 1) {
			Coordinates coord = new Coordinates(current.row + 1, current.col);
			if (!exclusions.contains(coord))
				res.add(coord);
		}
		if (current.col > 0) {
			Coordinates coord = new Coordinates(current.row, current.col - 1);
			if (!exclusions.contains(coord))
				res.add(coord);
		}
		if (current.col < n - 1) {
			Coordinates coord = new Coordinates(current.row, current.col + 1);
			if (!exclusions.contains(coord))
				res.add(coord);
		}
		return res;
	}


	Coordinates getNearestCoordinate(Coordinates from, Coordinates to1, Coordinates to2) {
		if (getDist(from, to1) < getDist(from, to2)) return to1;
		else return to2;
	}

	int getDist(Coordinates from, Coordinates to) {
		return Math.abs(from.row - to.row) + Math.abs(from.col - to.col);
	}

	Coordinates findPosition(int searchNumber) {
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				int num = puzzle[row][col];
				if (num == searchNumber) return new Coordinates(row, col);
			}
		}
		throw new RuntimeException();
	}

	class Coordinates {
		final int row, col;

		Coordinates(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Coordinates that = (Coordinates) o;
			return col == that.col &&
					row == that.row;
		}

		@Override
		public String toString() {
			return "Coordinates{" +
					"row=" + row +
					", col=" + col +
					'}';
		}

		@Override
		public int hashCode() {
			return Objects.hash(col, row);
		}
	}

	private void swapTiles(int[][] puzzle, int r1, int c1, int r2, int c2) {
		int temp = puzzle[r1][c1];
		puzzle[r1][c1] = puzzle[r2][c2];
		puzzle[r2][c2] = temp;
	}

	int getInversionCount(int[][] puzzle) {
		int inversionCount = 0;
		for (int i = 0; i < n * n - 1; i++) {
			for (int j = i + 1; j < n * n; j++) {
				int row1 = i / n;
				int col1 = i % n;
				int row2 = j / n;
				int col2 = j % n;
				if (puzzle[row1][col1] == 0 || puzzle[row2][col2] == 0) continue;
				if (puzzle[row1][col1] > puzzle[row2][col2])
					inversionCount++;
			}
		}
		return inversionCount;
	}

	int findZeroPosition(int[][] puzzle) {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (puzzle[i][j] == 0)
					return n - i;
		return -1;
	}

	boolean isSolvable(int[][] puzzle) {
		int inversionCount = getInversionCount(puzzle);
		if (n % 2 == 1)
			return inversionCount % 2 == 0;
		else {
			int zeroPosition = findZeroPosition(puzzle);
			if (zeroPosition % 2 == 1)
				return inversionCount % 2 == 0;
			else
				return inversionCount % 2 == 1;
		}
	}


	public static void main(String[] args) {
		{
			int[][] data = {
					{2, 3, 1, 4},
					{5, 7, 10, 8},
					{9, 0, 6, 11},
					{13, 14, 15, 12}
			};
			List<Integer> res = new SlidingPuzzle2(data).solve();
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
