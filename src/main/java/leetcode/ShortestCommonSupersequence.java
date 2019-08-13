package leetcode;

public class ShortestCommonSupersequence {
	/*
	1092. Shortest Common Supersequence
Hard

Given two strings str1 and str2, return the shortest string that has both str1 and str2 as subsequences.  If multiple answers exist, you may return any of them.

(A string S is a subsequence of string T if deleting some number of characters from T (possibly 0, and the characters are chosen anywhere from T) results in the string S.)



Example 1:

Input: str1 = "abac", str2 = "cab"
Output: "cabac"
Explanation:
str1 = "abac" is a subsequence of "cabac" because we can delete the first "c".
str2 = "cab" is a subsequence of "cabac" because we can delete the last "ac".
The answer provided is the shortest such string that satisfies these properties.



Note:

    1 <= str1.length, str2.length <= 1000
    str1 and str2 consist of lowercase English letters.


	 */

	public static void main(String[] args) {
		System.out.println(new ShortestCommonSupersequence().shortestCommonSupersequence("abacas", "cab"));
		System.out.println(new ShortestCommonSupersequence().shortestCommonSupersequence("abacas", "casty"));
		System.out.println(new ShortestCommonSupersequence().shortestCommonSupersequence("abacas", "bacas"));
	}


	public String shortestCommonSupersequence(String str1, String str2) {
		int n1 = str1.length();
		int n2 = str2.length();
		int[] hashes1 = getHashes(str1);
		int[] hashes2 = getHashes(str2);

		int minLen = Math.min(n1, n2);
		int[] hashesLen1 = getLenHashes(str1, minLen);
		int[] hashesLen2 = getLenHashes(str2, minLen);

		int[] hashesLen;
		int hashLenCheck;
		String strMin;
		String strMax;
		int[] hashesMin;
		int[] hashesMax;
		if (n1 > n2) {
			strMin = str2;
			strMax = str1;
			hashesLen = hashesLen1;
			hashLenCheck = hashesLen2[0];
			hashesMin = hashes2;
			hashesMax = hashes1;
		} else {
			strMin = str1;
			strMax = str2;
			hashesLen = hashesLen2;
			hashLenCheck = hashesLen1[0];
			hashesMin = hashes1;
			hashesMax = hashes2;
		}

		for (int i = 0; i < hashesLen.length; i++) {
			int h = hashesLen[i];
			if (h == hashLenCheck) {
				boolean equal = true;
				for (int j = 0; j < Math.min(n1, n2); j++) {
					if (strMin.charAt(j) != strMax.charAt(i + j)) equal = false;
				}
				if (equal) return strMax;
			}
		}

		for (int i = 1; i < minLen; i++) {
			int j = minLen - i - 1;
			// check start of long word with the end of short
			if (hashesMin[hashesMin.length - minLen  + i] == hashesMax[minLen - i - 1]) {
				boolean equal = true;
				for (int k = 0, l = i; k < j + 1; k++, l++) {
					if (strMax.charAt(k) != strMin.charAt(l)) equal = false;
				}
				if (equal) return strMin.substring(0, i) + strMax;
			}
			// check start of short word with the end of long
			if (hashesMax[hashesMax.length - minLen  + i] == hashesMin[minLen - i - 1]) {
				boolean equal = true;
				for (int k = 0, l = strMax.length() - minLen + i; k < j + 1; k++, l++) {
					if (strMin.charAt(k) != strMax.charAt(l)) equal = false;
				}
				if (equal) return strMax.substring(0, strMax.length() - minLen + i) + strMin;
			}
		}

		return "";
	}

	private int[] getLenHashes(String s, int length) {
		int n1 = s.length();
		int[] hashes1 = new int[n1 - length + 1];
		int curHash = 0;
		for (int i = 0; i < length; i++) {
			curHash = calcHash(i, curHash, (char) 0, s.charAt(i));
		}
		hashes1[0] = curHash;

		for (int i = 0; i < n1 - length; i++) {
			curHash = calcHash(length, curHash, s.charAt(i), s.charAt(i + length));
			hashes1[i + 1] = curHash;
		}
		return hashes1;
	}

	private int[] getHashes(String s) {
		int n1 = s.length();
		int[] hashes1 = new int[2 * n1 - 1];
		int curHash = 0;
		for (int i = 0; i < n1; i++) {
			curHash = calcHash(i, curHash, (char) 0, s.charAt(i));
			hashes1[i] = curHash;
		}
		for (int i = n1; i > 1; i--) {
			curHash = calcHash(i, curHash, s.charAt(n1 - i), (char) 0);
			hashes1[2 * n1 - i] = curHash;
		}
		return hashes1;
	}

	private int calcHash(int hashStrLen, int hash, char remove, char add) {
		int decrease = (int) remove;
		for (int i = 0; i < hashStrLen - 1; i++) {
			decrease *= 31;
		}
		if (add == 0) return hash - decrease;
		else return (hash - decrease) * 31 + (int) add;
	}
}
