package codewars;

import java.util.*;
import java.util.stream.Collectors;

public class FPT {

    /*
    The eccentric candy-maker, Billy Bonka, is building a new candy factory to produce his new 4-flavor sugar pops. The candy is made by placing a piece of candy base onto a conveyer belt which transports the candy through four separate processing stations in sequential order. Each station adds another layer of flavor.

Due to an error in the factory blueprints, the four stations have been constructed in incorrect locations. It's too costly to disassemble the stations, so you've been called in.

Arrange the directional path of the conveyer belt so that it passes through each of the stations in sequential order while also traveling the shortest distance possible.
Input

An array consisting of the locations of each station on the factory floor, in order. The factory floor is a 10 x 10 matrix (with 0 starting index).
Output

Your function should return the path of the conveyer belt as an array.
If a valid configuration is not possible, return null or None.

The position values in the input and output arrays will consist of integers in the range 0 - 99, inclusive. These integers represent a position on the factory floor.
For example, the position [0,8] is given as 8, and [4,6] is given as 46
Technical Details

    The conveyer belt must run through each station once and in ascending order
    The conveyer belt must not intersect/overlap itself
    The distance covered by the conveyer belt must be the minimum necessary to complete the task
    Full Test Suite: 30 fixed tests, 100 random tests
    Inputs will always be valid and each test will have zero or more possible solutions..

Test Example
4 stations

// INPUT - reference image A
int[] stations = {0,65,93,36};
new FPT(stations).solve();

// OUTPUT #1 - reference image B
// [0, 1, 2, 3, 4, 5, 15, 25, 35, 45, 55, 65, 64, 63, 73, 83, 93, 94, 95, 96, 86, 76, 66, 56, 46, 36]

// OUTPUT #2 - reference image C
// [0, 10, 20, 30, 40, 50, 60, 61, 62, 63, 64, 65, 75, 85, 84, 83, 93, 94, 95, 96, 86, 76, 66, 56, 46, 36]
     */

	// one of the paths has to be the shortest

	int n = 10;
	int[] stations;
	int[] path;
	Set<Integer> usedSquares = new HashSet<>();
//    int squareFrom, squareTo;

	public FPT(int[] stations) {
		this.stations = stations;
//        squareFrom = path[0];
//        squareTo = path[1];
	}


	public List<Integer> solve() {
//		List<List<Node>> basePoints = findBasePoints();
		int ind = 0;
		return getMinPathLength(ind);
//		return Arrays.stream(path).boxed().collect(Collectors.toList());
	}

//	List<List<Node>> findBasePoints(Node from, Node to) {
//		Node node1 = new Node(from.x, to.y);
//
//		Node node2 = new Node(to.x, from.y);
//
//		int nodeX, nodeY;
//		Node over = new Node(stations[2]);
//		Node leftBottom = new Node(Math.min(from.x, to.x), Math.min(from.y, to.y));
//		Node rightTop = new Node(Math.max(from.x, to.x), Math.max(from.y, to.y));
//		if (over.x <= leftBottom.x) {
//			nodeX = over.x - 1;
//		} else if (over.x >= rightTop.x) {
//			nodeX = over.x + 1;
//		} else {
//			nodeX = over.x;
//		}
//		if (over.y <= leftBottom.y) {
//			nodeY = over.y - 1;
//		} else if (over.y >= rightTop.y) {
//			nodeY = over.y + 1;
//		} else {
//			nodeY = over.y;
//		}
//		Node node3 = new Node(nodeX, nodeY);
//
//	}

	public static void main(String[] args) {
		int[] stations = {0, 65, 93, 36};
		System.out.println(Arrays.toString(new FPT(stations).solve().toArray()));
	}

	private List<Integer> getMinPathLength(int ind) {
		List<Integer> res = new LinkedList<>();
		res.addAll(findPath(stations[0], stations[1]));
		res.addAll(findPath(stations[1], stations[2]));
		res.addAll(findPath(stations[2], stations[3]));
		res.add(stations[3]);
		return res;
	}


	private List<Integer> findPath(int from, int to) {
		// fill length matrix
		Set<Node> usedNodes = new HashSet<>();
		Map<Node, Set<Node>> distanceMap = new HashMap<>();
		for (int i = 0; i < n * n; i++) {
			if (i % 10 != 0) {
				addMove(distanceMap, new Node(i), new Node(i - 1));
			}
			if (i % 10 != 9) {
				addMove(distanceMap, new Node(i), new Node(i + 1));
			}
			if (i / 10 != 0) {
				addMove(distanceMap, new Node(i), new Node(i - 10));
			}
			if (i / 10 != 9) {
				addMove(distanceMap, new Node(i), new Node(i + 10));
			}
		}

		Integer[] minLength = new Integer[n * n];
		Node[] prevVertex = new Node[n * n];
		minLength[from] = 0;

		while (true) {
			int min = Integer.MAX_VALUE;
			Node node = null;
			for (int i = 0; i < n * n; i++) {
//				node = new Node(i);
				if (!usedNodes.contains(new Node(i)) && minLength[i] != null && minLength[i] < min) {
					min = minLength[i];
					node = new Node(i);
				}
			}
			if (node == null) break;
			usedNodes.add(node);

			for (Node nodeTo : distanceMap.get(node)) {
				if (!usedNodes.contains(nodeTo)) {
					if (minLength[nodeTo.position] == null) {
						minLength[nodeTo.position] = minLength[node.position] + 1;
					} else {
						minLength[nodeTo.position] = Math.min(minLength[nodeTo.position], minLength[node.position] + 1);
					}
					prevVertex[nodeTo.position] = node;
				}
			}
		}
		System.out.println("dist to " + to + "=" + minLength[to]);

		List<Integer> res = new ArrayList<>();
		int nextInd = to;
//		res.add(nextInd);
		while (true) {
			nextInd = prevVertex[nextInd].position;
			res.add(0,nextInd);
			if (nextInd == from) break;
		}
		return res;
	}

	private void addMove(Map<Node, Set<Node>> moveMap, Node from, Node to) {
		Set<Node> list = moveMap.computeIfAbsent(from, k -> new HashSet<>());
		list.add(to);
	}

	private int getMin(int... numbers) {
		int min = 0;
		for (int n : numbers) {
			min = Math.min(min, n);
		}
		return min;
	}


	class Node implements Comparable<Node> {
		final Integer position;
		final int x, y;

		Node(int position) {
			this.position = position;
			this.x = position % 10;
			this.y = position / 10;
		}

		Node(int x, int y) {
			this.position = Integer.valueOf("" + x + y);
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Node node = (Node) o;
			return position == node.position;
		}

		@Override
		public int hashCode() {
			return Objects.hash(position);
		}

		@Override
		public int compareTo(Node o) {
			return position.compareTo(o.position);
		}
	}
}
