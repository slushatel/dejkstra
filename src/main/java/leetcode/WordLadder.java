package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class WordLadder {
	/*
	127. Word Ladder
Medium

Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:

    Only one letter can be changed at a time.
    Each transformed word must exist in the word list. Note that beginWord is not a transformed word.

Note:

    Return 0 if there is no such transformation sequence.
    All words have the same length.
    All words contain only lowercase alphabetic characters.
    You may assume no duplicates in the word list.
    You may assume beginWord and endWord are non-empty and are not the same.

Example 1:

Input:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

Output: 5

Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
return its length 5.

Example 2:

Input:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log"]

Output: 0

Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.

	 */

	private void addToGraph(Map<String, List<String>> graph, String s) {
		int len = s.length();
		for (int i = 0; i < len; i++) {
			String pattern = s.substring(0, i) + "*" + s.substring(i + 1, len);
			List<String> list = graph.computeIfAbsent(pattern, key -> new LinkedList<>());
			list.add(s);
			graph.put(pattern, list);
		}
	}

	public int ladderLength(String beginWord, String endWord, List<String> wordList) {
		int len = beginWord.length();
		Map<String, List<String>> graph = new HashMap<>();
		for (String s : wordList) {
			addToGraph(graph, s);
		}

		Set<String> visited = new HashSet<>();
		Queue<String> queue = new LinkedList<>();
		Map<String, String> comeFrom = new HashMap<>();

		queue.add(beginWord);
		int res = 0;
		while (!queue.isEmpty()) {
			res++;
			int queueSize = queue.size();
			for (int n = 0; n < queueSize; n++) {
				String s = queue.poll();
				visited.add(s);
				for (int i = 0; i < len; i++) {
					String pattern = s.substring(0, i) + "*" + s.substring(i + 1, len);
					List<String> list = graph.computeIfAbsent(pattern, key -> new LinkedList<>());
					for (String next : list) {
						if (!visited.contains(next)) {
							visited.add(next);
							queue.add(next);
							comeFrom.put(next, s);
						}
						if (next.equals(endWord))
							return res + 1;

					}
				}
			}
		}

		return 0;
	}

	public static void main(String[] args) {
		List<String> dict = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
		System.out.println(new WordLadder().ladderLength("hit", "cog", dict));
		dict = Arrays.asList("hot", "dot", "dog", "lot", "log");
		System.out.println(new WordLadder().ladderLength("hit", "cog", dict));
	}

}
