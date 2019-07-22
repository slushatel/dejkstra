package leetcode;

import java.util.Objects;

public class NumberOfSubmatricesThatSumToTarget {
/*
Given a matrix, and a target, return the number of non-empty submatrices that sum to target.

A submatrix x1, y1, x2, y2 is the set of all cells matrix[x][y] with x1 <= x <= x2 and y1 <= y <= y2.

Two submatrices (x1, y1, x2, y2) and (x1', y1', x2', y2') are different if they have some coordinate that is different: for example, if x1 != x1'.



Example 1:

Input: matrix = [[0,1,0],[1,1,1],[0,1,0]], target = 0
Output: 4
Explanation: The four 1x1 submatrices that only contain 0.

Example 2:

Input: matrix = [[1,-1],[-1,1]], target = 0
Output: 5
Explanation: The two 1x2 submatrices, plus the two 2x1 submatrices, plus the 2x2 submatrix.



Note:

    1 <= matrix.length <= 300
    1 <= matrix[0].length <= 300
    -1000 <= matrix[i] <= 1000
    -10^8 <= target <= 10^8

 */

	public static void main(String[] args) {
		int[][] matrix = {{0, 1, 0}, {1, 1, 1}, {0, 1, 0}};
		System.out.println(new NumberOfSubmatricesThatSumToTarget().numSubmatrixSumTarget(matrix, 0));
	}

	public int numSubmatrixSumTarget(int[][] matrix, int target) {

		return 0;
	}

// brute force
//	public int numSubmatrixSumTarget(int[][] matrix, int target) {
//		Map<Matrix, Integer> sums = new HashMap<>();
//		for (int i = 0; i < matrix.length; i++) {
//			for (int j = 0; j < matrix[0].length; j++) {
//				for (int k = 0; k <= i; k++) {
//					for (int l = 0; l <= j; l++) {
//						for (int m = i; m < matrix.length; m++) {
//							for (int n = j; n < matrix[0].length; n++) {
//								Matrix matr = new Matrix(k, l, m, n);
//								sums.put(matr, sums.computeIfAbsent(matr, item -> 0) + matrix[i][j]);
//							}
//						}
//					}
//				}
//			}
//		}
//
//		int count = 0;
//
//		for (int val : sums.values()) {
//			if (val == target) count++;
//		}
//		return count;
//	}

	class Matrix {
		final int x1, x2, y1, y2;

		public Matrix(int x1, int x2, int y1, int y2) {
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Matrix matrix = (Matrix) o;
			return x1 == matrix.x1 &&
					x2 == matrix.x2 &&
					y1 == matrix.y1 &&
					y2 == matrix.y2;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x1, x2, y1, y2);
		}
	}

}
