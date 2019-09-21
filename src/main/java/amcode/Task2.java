package amcode;

public class Task2 {
	public static void main(String[] args) {
//		System.out.println(new Task2().solution(2872));
		for (int i = 0; i < 5; ++i) {
			System.out.println(i);

		}
	}

	int solution(int n) {
		int[] d = new int[30];
		int l = 0;
		int p;
		while (n > 0) {
			d[l] = n % 2;
			n /= 2;
			l++;
		}
		for (p = 1; p < 1 + l; ++p) {
			int i;
			boolean ok = true;
			for (i = 0; i < l - p; ++i) {
				if (d[i] != d[i + p]) {
					ok = false;
					break;
				}
			}
			if (ok) {
				return p;
			}
		}
		return -1;
	}
}
