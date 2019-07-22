package leetcode;

import java.util.Objects;

public class MaxPointsOnLine {
    /*
    Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.

    Example 1:

    Input: [[1,1],[2,2],[3,3]]
    Output: 3
    Explanation:
    ^
    |
    |        o
    |     o
    |  o
    +------------->
    0  1  2  3  4

    Example 2:

    Input: [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
    Output: 4
    Explanation:
    ^
    |
    |  o
    |     o        o
    |        o
    |  o        o
    +------------------->
    0  1  2  3  4  5  6

    NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.

     */

	public static void main(String[] args) {
//		{
//			int[][] points = {{1, 1}, {3, 2}, {5, 3}, {4, 1}, {2, 3}, {1, 4}};
//			System.out.println(new MaxPointsOnLine().maxPoints(points));
//		}
//		{
//			int[][] points = {{1, 1}, {2, 2}, {3, 3}};
//			System.out.println(new MaxPointsOnLine().maxPoints(points));
//		}
//		{
//			int[][] points = {{0, 0}};
//			System.out.println(new MaxPointsOnLine().maxPoints(points));
//		}
//		{
//			int[][] points = {{0,0},{1,65536},{65536,0}};
//			System.out.println(new MaxPointsOnLine().maxPoints(points));
//		}
		{
			int[][] points = {{84, 250}, {0, 0}, {1, 0}, {0, -70}, {0, -70}, {1, -1}, {21, 10}, {42, 90}, {-42, -230}};
			System.out.println(new MaxPointsOnLine().maxPoints(points));
		}

	}

	class Point {
		final int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Point point = (Point) o;
			return x == point.x &&
					y == point.y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
	}

	// time complexity is O(n^3)
	public int maxPoints(int[][] points) {

//		Map<Point, Integer> pointNumbers = new HashMap<>();
//		Set<Point> pointSet = new HashSet<>();
//
//		for (int i = 0; i < points.length; i++) {
//			Point p = new Point(points[i][0], points[i][1]);
//			pointNumbers.put(p, pointNumbers.computeIfAbsent(p, item -> 0) + 1);
//			pointSet.add(p);
//		}


		if (points.length < 2) return points.length;

		int maxNumber = 0;

//		for (Point point1 : pointSet) {
//
//		}
		for (int i = 0; i < points.length; i++) {
			int x1 = points[i][0];
			int y1 = points[i][1];
			for (int j = i + 1; j < points.length; j++) {
				int number = 0;
				int x2 = points[j][0];
				int y2 = points[j][1];
				if (x1 == x2 && y1 == y2) continue;
				for (int k = 0; k < points.length; k++) {
					if (k == i || k == j) {
						number++;
						continue;
					}
					int x3 = points[k][0];
					int y3 = points[k][1];
					if (((long) y3 - y1) * (x2 - x1) == ((long) y2 - y1) * (x3 - x1)) number++;
				}
//				System.out.println(number);
				maxNumber = Math.max(maxNumber, number);
			}
		}
		// if all points were the equal
		if (maxNumber == 0) maxNumber = points.length;
		return maxNumber;
	}


}
