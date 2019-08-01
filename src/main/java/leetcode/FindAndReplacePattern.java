package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindAndReplacePattern {
	public List<String> findAndReplacePattern(String[] words, String pattern) {
		int patternLen = pattern.length();
		List<String> res = new ArrayList<>();
		wordsLoop:
		for (String s : words) {
			Map<Character, Character> wp = new HashMap<>();
			Map<Character, Character> pw = new HashMap<>();
			for (int i = 0; i < patternLen; i++) {
				Character ch = s.charAt(i);
				Character pch = pattern.charAt(i);
				if (wp.get(ch) == pch && pw.get(pch) == ch) {

				} else if (!wp.keySet().contains(ch) && !pw.keySet().contains(pch)) {
					wp.put(ch, pch);
					pw.put(pch, ch);
				} else {
					continue wordsLoop;
				}
			}
			res.add(s);
		}
		return res;
	}

	public static void main(String[] args) {
		String[] arr = {"abc", "deq", "mee", "aqq", "dkd", "ccc"};
		List<String> res = new FindAndReplacePattern().findAndReplacePattern(arr, "abb");
		for (String s : res) {
			System.out.println(s);
		}
	}

}
