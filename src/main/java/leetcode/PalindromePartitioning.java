package leetcode;

import java.util.LinkedList;
import java.util.List;

public class PalindromePartitioning {
/*
131. Palindrome Partitioning
Medium

Given a string s, partition s such that every substring of the partition is a palindrome.

Return all possible palindrome partitioning of s.

Example:

Input: "aab"
Output:
[
  ["aa","b"],
  ["a","a","b"]
]


 */

	public List<List<String>> partition(String s) {
		List<List<String>> res = new LinkedList<>();
		helper(res, new LinkedList<>(), s, 0);
		return res;
	}

	private void helper(List<List<String>> res, List<String> palindroms, String s, int start) {
		if (start == s.length()) {
			res.add(palindroms);
			return;
		}

		for (int i = start; i < s.length(); i++) {
			if (isPalindrom(s.substring(start, i + 1))) {
				List<String> palindromsNew = new LinkedList<>(palindroms);
				palindromsNew.add(s.substring(start, i+1));
				helper(res, palindromsNew, s, i+1);
			}
		}
	}

	boolean isPalindrom(String s) {
		int len = s.length();
		for (int i = 0; i < len / 2; i++) {
			if (s.charAt(i) != s.charAt(len - i - 1)) return false;
		}
		return true;
	}


	public static void main(String[] args) {
		List<List<String>> res = new PalindromePartitioning().partition("aab");
		for (List<String> list : res) {
			for (String s : list) {
				System.out.print(s + " ");
			}
			System.out.println();
		}
	}


}
