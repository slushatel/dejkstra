package amcode;

public class Task3 {

	public static void main(String[] args) {
		int[] A = {2, 8, 4, 3, 2};
		System.out.println(new Task3().solution(A, 7, 11, 3));
	}

	public int solution(int[] A, int X, int Y, int Z) {
		if (A.length == 0) return 0;

		int n = 3;
		int[] dispensers = {X, Y, Z};
		boolean[] isFree = new boolean[n];
		for (int i = 0; i < n; i++) {
			isFree[i] = true;
		}
		// to save end of fueling process for each dispenser
		int[] carsAtDispencers = new int[n];
		for (int i = 0; i < n; i++) {
			carsAtDispencers[i] = 0;
		}

		int curTime = 0;
		int currentCar = 0;
		int maxTime = 0;

		while (true) {
			int disp;
			do {
				disp = getFreeDispenser(dispensers, isFree, A[currentCar]);
				if (disp != -1) {
					// move car to the dispenser
					carsAtDispencers[disp] = curTime + A[currentCar];
					dispensers[disp] -= A[currentCar];
					currentCar++;
					maxTime = curTime;
				}
				// if all cars was fueled?
				if (currentCar == A.length) return maxTime;
			} while (disp != -1);

			// move time, release a dispenser
			int min = -1;
			for (int i = 0; i < n; i++) {
				if (!isFree[i]) {
					if (min == -1) {
						min = carsAtDispencers[i];
					} else {
						min = Math.min(min, carsAtDispencers[i]);
					}
				}
			}
			// no dispenser to free
			if (min == -1) return -1;

			curTime = min;

			for (int i = 0; i < n; i++) {
				if (isFree[i]) {
					carsAtDispencers[i] = curTime;
				} else {
					if (carsAtDispencers[i] == curTime) {
						isFree[i] = true;
					}
				}
			}
		}
	}

	int getFreeDispenser(int[] dispensers, boolean[] isFree, int amount) {
		for (int i = 0; i < dispensers.length; i++) {
			if (isFree[i] && dispensers[i] >= amount) {
				isFree[i] = false;
				return i;
			}
		}
		return -1;
	}

}
