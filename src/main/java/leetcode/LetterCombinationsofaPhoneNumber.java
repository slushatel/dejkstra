package leetcode;

import java.util.LinkedList;
import java.util.List;

public class LetterCombinationsofaPhoneNumber {
	/*
	17. Letter Combinations of a Phone Number
Medium

Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.

A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.

Example:

Input: "23"
Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].

Note:

Although the above answer is in lexicographical order, your answer could be in any order you want.

	 */


	public List<String> letterCombinations(String digits) {
		List<String> allPaths = new LinkedList<>();
		List<String> nodes = new LinkedList<>();
		for (int i = 0; i < digits.length(); i++) {
			char ch = digits.charAt(i);
			nodes.add(getLetters(ch));
		}

		if (nodes.size() > 0) dfs(allPaths, nodes, 0, "");
		return allPaths;
	}

	private void dfs(List<String> allPaths, List<String> nodes, int level, String prefix) {
		if (level == nodes.size()) {
			allPaths.add(prefix);
			return;
		}
		for (char ch : nodes.get(level).toCharArray()) {
			dfs(allPaths, nodes, level + 1, prefix + ch);
		}
	}

	private String getLetters(char ch) {
		String res = null;
		if (ch == '2') {
			res = "abc";
		} else if (ch == '3') {
			res = "def";
		} else if (ch == '4') {
			res = "ghi";
		} else if (ch == '5') {
			res = "jkl";
		} else if (ch == '6') {
			res = "mno";
		} else if (ch == '7') {
			res = "pqrs";
		} else if (ch == '8') {
			res = "tuv";
		} else if (ch == '9') {
			res = "wxyz";
		} else if (ch == '0') {
			res = " ";
		}

		return res;
	}

	public static void main(String[] args) {
		List<String> res = new LetterCombinationsofaPhoneNumber().letterCombinations("23");
		for (String re : res) {
			System.out.println(re);
		}
	}

}
