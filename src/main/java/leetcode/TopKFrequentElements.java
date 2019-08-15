package leetcode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequentElements {
	/*
	347. Top K Frequent Elements
Medium

Given a non-empty array of integers, return the k most frequent elements.

Example 1:

Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]

Example 2:

Input: nums = [1], k = 1
Output: [1]

Note:

    You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
    Your algorithm's time complexity must be better than O(n log n), where n is the array's size.


	 */

	public static void main(String[] args) {

	}

	public List<Integer> topKFrequent(int[] nums, int k) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int n : nums) {
			map.put(n, map.getOrDefault(n, 0) + 1);
		}

		Comparator<Map.Entry<Integer, Integer>> comp = (o1, o2) -> o1.getValue().compareTo(o2.getValue()) * -1;
		PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(comp);
		pq.addAll(map.entrySet());

		List<Integer> res = new LinkedList<>();
		for (int i = 0; i < k; i++) {
			res.add(pq.poll().getKey());
		}
		return res;
	}
}
