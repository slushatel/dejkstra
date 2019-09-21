package amcode;

import java.util.LinkedList;
import java.util.List;

public class Task1 {
	public static void main(String[] args) {
//		int[] a = {1, 3, 6, 4, 1, 2};
//		System.out.println(new Task1().solution(a));
		System.out.println(new Task1().solution(0));

	}

	public int solution(int N) {
		List<Integer> nums = new LinkedList<>();
		while (N > 0) {
			int digit = N % 10;
			nums.add(digit);
			N = N / 10;
		}
		nums.sort(Integer::compareTo);
		int res = 0;
		for (int i = nums.size() - 1; i >= 0; i--) {
			res = res * 10 + nums.get(i);

		}

		return res;
	}


//	public int solution(int[] A) {
//		Arrays.sort(A);
//		int lo = 0;
//		int hi = A.length - 1;
//		int mid = (lo + hi) / 2;
//
//		while (lo < hi) {
//			if (A[mid] > 0) {
//				hi = mid;
//			} else {
//				lo = mid + 1;
//			}
//			mid = (lo + hi) / 2;
//		}
//		System.out.println(lo);
//
////		int last = 1;
//		int i = lo;
//		while (i < A.length) {
//			if (i> 0 && A[i] - A[i-1] > 1)
//				return A[i-1] + 1;
//			i++;
////			last++;
//		}
//
//		return 1;
//	}


}
