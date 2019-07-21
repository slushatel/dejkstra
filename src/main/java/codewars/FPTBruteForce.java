package codewars;

import java.util.*;

public class FPTBruteForce {

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
new FPTPermute(stations).solve();

// OUTPUT #1 - reference image B
// [0, 1, 2, 3, 4, 5, 15, 25, 35, 45, 55, 65, 64, 63, 73, 83, 93, 94, 95, 96, 86, 76, 66, 56, 46, 36]

// OUTPUT #2 - reference image C
// [0, 10, 20, 30, 40, 50, 60, 61, 62, 63, 64, 65, 75, 85, 84, 83, 93, 94, 95, 96, 86, 76, 66, 56, 46, 36]
     */

	// one of the paths has to be the shortest

	int n = 10;
	int[] stations;
	Set<Integer> usedSquares = new HashSet<>();
	long time = 0;

	public FPTBruteForce(int[] stations) {
		this.stations = stations;
	}

	public static void main(String[] args) {
		int[] stations = {0, 65, 93, 36};
		System.out.println(Arrays.toString(new FPTBruteForce(stations).solve().toArray()));
	}

	public List<Integer> solve() {
		return stepCount(stations[0]);
//		return Arrays.stream(path).boxed().collect(Collectors.toList());
	}

	private List<Integer> stepCount(int current) {
		if (usedSquares.contains(current)) return null;

		if (current == stations[3]) {
			if (usedSquares.contains(stations[1]) && usedSquares.contains(stations[2])) {
				List<Integer> res = new LinkedList<>();
				res.add(current);
				return res;
			} else {
				List<Integer> res = new LinkedList<>();
				for (int i = 0; i < 1000; i++) {
					res.add(-1);
				}
				return null;
			}
		}
		usedSquares.add(current);
		int min = Integer.MAX_VALUE;
		List<Integer> res = null;
		// not left column
		if (current % 10 != 0) {
			List<Integer> res0 = stepCount(current - 1);
			if (res0 != null && res0.size() < min) {
				res = res0;
			}
		}
		if (current % 10 != 9) {
			List<Integer> res0 = stepCount(current + 1);
			if (res0 != null && res0.size() < min) {
				res = res0;
			}
		}
		if (current / 10 != 0) {
			List<Integer> res0 = stepCount(current - 10);
			if (res0 != null && res0.size() < min) {
				res = res0;
			}
		}
		if (current / 10 != 9) {
			List<Integer> res0 = stepCount(current + 10);
			if (res0 != null && res0.size() < min) {
				res = res0;
			}
		}
		if (res != null) {
			res.add(0, current);


		}
		usedSquares.remove(current);
		long curTime = System.nanoTime();
		if (curTime - time > 1000000000) {
			time = curTime;
			System.out.println(Arrays.toString(usedSquares.toArray()));
		}

		return res;
	}
}
