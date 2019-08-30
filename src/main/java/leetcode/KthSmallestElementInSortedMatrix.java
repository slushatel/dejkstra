package leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class KthSmallestElementInSortedMatrix {

	/*
	378. Kth Smallest Element in a Sorted Matrix
Medium

Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the kth smallest element in the matrix.

Note that it is the kth smallest element in the sorted order, not the kth distinct element.

Example:

matrix = [
   [ 1,  5,  9],
   [10, 11, 13],
   [12, 13, 15]
],
k = 8,

return 13.

Note:
You may assume k is always valid, 1 ≤ k ≤ n2.
	 */

	public static void main(String[] args) {
		{
			int[][] matrix = {{1, 5, 9}, {10, 11, 13}, {12, 13, 15}};
			int k = 8;
			System.out.println(new KthSmallestElementInSortedMatrix().kthSmallest(matrix, k));
		}
		{
			int[][] matrix = {{1, 2}, {3, 3}};
			int k = 2;
			System.out.println(new KthSmallestElementInSortedMatrix().kthSmallest(matrix, k));
		}
	}

	public int kthSmallest(int[][] matrix, int k) {
		int n = matrix.length;
		PriorityQueue<Item> pq = new PriorityQueue<>(new ItemComparator());
		pq.add(new Item(matrix, 0, 0));
		for (int i = 0; i < k - 1; i++) {
			Item item = pq.poll();
			if (item.row + 1 < n) pq.add(new Item(matrix, item.row + 1, item.col));
			if (item.row == 0 && item.col + 1 < n) pq.add(new Item(matrix, item.row, item.col + 1));
		}
		return pq.peek().value;
	}

	class Item {
		int value;
		int row, col;

		public Item(int[][] matrix, int row, int col) {
			this.value = matrix[row][col];
			this.row = row;
			this.col = col;
		}

		@Override
		public String toString() {
			return "Item{" +
					"value=" + value +
					", row=" + row +
					", col=" + col +
					'}';
		}
	}

	class ItemComparator implements Comparator<Item> {
		@Override
		public int compare(Item o1, Item o2) {
			return Integer.compare(o1.value, o2.value);
		}
	}

}
