package leetcode;

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

	private int[] matrixLinear;
	private int target;

	public static void main(String[] args) {
		int[][] matrix = {{0, 1, 0}, {1, 1, 1}, {0, 1, 0}};
		System.out.println(new NumberOfSubmatricesThatSumToTarget().numSubmatrixSumTarget(matrix, 0));
	}

//	public int numSubmatrixSumTarget(int[][] matrix, int target) {
//		int m = matrix.length;
//		int n = matrix[0].length;
//
//		for (int i = 0; i < m; i++) {
//			for (int j = 1; j < n; j++) {
//				matrix[i][j] += matrix[i][j - 1];
//			}
//		}
//		int ans = 0;
//		for (int i = 0; i < n; i++) {
//			for (int j = i; j < n; j++) {
//				Map<Integer, Integer> cnt = new HashMap<>();
//				cnt.put(0, 1);
//				int sum = 0;
//				for (int k = 0; k < m; k++) {
//					sum += matrix[k][j] - (i > 0 ? matrix[k][i - 1] : 0);
//					ans += cnt.getOrDefault(sum - target, 0);
//					cnt.put(sum, cnt.getOrDefault(sum, 0) + 1);
//				}
//			}
//		}
//		return ans;
//	}

	public int numSubmatrixSumTarget(int[][] matrix, int target) {
		int n1 = matrix.length;
		int n2 = matrix[0].length;
		int count = 0;

		// precalculate sums
		for (int i = 1; i < n1; i++) {
			matrix[i][0] += matrix[i - 1][0];
		}
		for (int j = 1; j < n2; j++) {
			matrix[0][j] += matrix[0][j - 1];
		}
		for (int i = 1; i < n1; i++) {
			for (int j = 1; j < n2; j++) {
				matrix[i][j] += matrix[i][j - 1] + matrix[i - 1][j] - matrix[i - 1][j - 1];
			}
		}

		// a|b
		// c|d

		for (int i = 0; i < n1; i++) {
			for (int j = 0; j < n2; j++) {
				for (int k = -1; k < i; k++) {
					for (int l = -1; l < j; l++) {
						int a, b, c;
						if (k < 0 && l < 0) {
							a = 0;
							b = 0;
							c = 0;
						} else if (k < 0) {
							a = 0;
							b = 0;
							c = matrix[i][l];
						} else if (l < 0) {
							a = 0;
							b = matrix[k][j];
							c = 0;
						} else {
							a = matrix[k][l];
							b = matrix[k][j];
							c = matrix[i][l];
						}
						int d = matrix[i][j];
						if (d - b - c + a == target) count++;
					}
				}
			}
		}
		return count;
	}

}
