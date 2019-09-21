package leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class NumberOfIslands {
	/*
	200. Number of Islands
Medium

Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:

Input:
11110
11010
11000
00000

Output: 1

Example 2:

Input:
11000
11000
00100
00011

Output: 3

	 */

	boolean[][] visited;
	int islands = 0;
	char[][] grid;
	int rows;
	int cols;

	public int numIslands(char[][] grid) {
		if (grid.length == 0) return 0;

		this.grid = grid;
		rows = grid.length;
		cols = grid[0].length;
		visited = new boolean[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				dfs(new Cell(i, j));
			}
		}
		return islands;
	}

	private void dfs(Cell start) {
		if (visited[start.row][start.col] || grid[start.row][start.col] == '0') return;
		islands++;

		Stack<Cell> path = new Stack<>();
		path.add(start);
		while (!path.isEmpty()) {
			Cell cell = path.pop();
			visited[cell.row][cell.col] = true;
			for (Cell adjCell : findAdjacent(cell)) {
				path.add(adjCell);
			}
		}
	}

	private List<Cell> findAdjacent(Cell cell) {
		List<Cell> res = new LinkedList<>();

		if (cell.row > 0) {
			Cell adjacentCell = new Cell(cell.row - 1, cell.col);
			if (isGround(adjacentCell) && !isVisited(adjacentCell))
				res.add(adjacentCell);
		}
		if (cell.row < rows - 1) {
			Cell adjacentCell = new Cell(cell.row + 1, cell.col);
			if (isGround(adjacentCell) && !isVisited(adjacentCell))
				res.add(adjacentCell);
		}
		if (cell.col > 0) {
			Cell adjacentCell = new Cell(cell.row, cell.col - 1);
			if (isGround(adjacentCell) && !isVisited(adjacentCell))
				res.add(adjacentCell);
		}
		if (cell.col < cols - 1) {
			Cell adjacentCell = new Cell(cell.row, cell.col + 1);
			if (isGround(adjacentCell) && !isVisited(adjacentCell))
				res.add(adjacentCell);
		}
		return res;
	}

	private boolean isGround(Cell cell) {
		return grid[cell.row][cell.col] == '1';
	}

	private boolean isVisited(Cell cell) {
		return visited[cell.row][cell.col];
	}


	class Cell {
		final int row, col;

		public Cell(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	public static void main(String[] args) {
		char[][] data = {
				{'1', '1', '0', '0', '0'},
				{'1', '1', '0', '0', '0'},
				{'0', '0', '1', '0', '0'},
				{'0', '0', '0', '1', '1'},
		};
		int res = new NumberOfIslands().numIslands(data);
		System.out.println(res);
	}
}
