package leetcode;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LargestNumber {
	/*
	179. Largest Number
Medium

Given a list of non negative integers, arrange them such that they form the largest number.

Example 1:

Input: [10,2]
Output: "210"

Example 2:

Input: [3,30,34,5,9]
Output: "9534330"

Note: The result may be very large, so you need to return a string instead of an integer.

	 */

	public String largestNumber(int[] nums) {
		String res = Arrays.stream(nums).mapToObj(String::valueOf).sorted((s1, s2) -> (s2 + s1).compareTo(s1 + s2))
				.collect(Collectors.joining());
		// remove leading zeros
		int i = 0;
		while (i < res.length() - 1 && res.charAt(i) == '0') {
			i++;
		}
		return res.substring(i);
	}

	public static void main(String[] args) {
		{
			int[] data = {3, 30, 34, 5, 9};
			System.out.println(new LargestNumber().largestNumber(data));
		}
		{
			int[] data = {0, 0};
			System.out.println(new LargestNumber().largestNumber(data));
		}
	}
}
