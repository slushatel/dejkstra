package leetcode;

public class LongestChunkedPalindromeDecomposition {
	/*
	1147. Longest Chunked Palindrome Decomposition
Hard

Return the largest possible k such that there exists a_1, a_2, ..., a_k such that:

    Each a_i is a non-empty string;
    Their concatenation a_1 + a_2 + ... + a_k is equal to text;
    For all 1 <= i <= k,  a_i = a_{k+1 - i}.



Example 1:

Input: text = "ghiabcdefhelloadamhelloabcdefghi"
Output: 7
Explanation: We can split the string on "(ghi)(abcdef)(hello)(adam)(hello)(abcdef)(ghi)".

Example 2:

Input: text = "merchant"
Output: 1
Explanation: We can split the string on "(merchant)".

Example 3:

Input: text = "antaprezatepzapreanta"
Output: 11
Explanation: We can split the string on "(a)(nt)(a)(pre)(za)(tpe)(za)(pre)(a)(nt)(a)".

Example 4:

Input: text = "aaa"
Output: 3
Explanation: We can split the string on "(a)(a)(a)".



Constraints:

    text consists only of lowercase English characters.
    1 <= text.length <= 1000

	 */

	public static void main(String[] args) {
//		System.out.println(new LongestChunkedPalindromeDecomposition().longestDecomposition("antaprezatepzapreanta"));
		System.out.println(new LongestChunkedPalindromeDecomposition().longestDecomposition("merchant"));
	}

	public int longestDecomposition(String text) {
		int res = 0;
		int posEnd = text.length() - 1;
		int posStart = 0;

		char ch = text.charAt(posEnd);
		String chars = "";
		while (posStart <= posEnd) {
			char chStart = text.charAt(posStart);
			chars += chStart;
			if (chStart == ch) {
				if (chars.equals(text.substring(posEnd - chars.length() + 1, posEnd + 1))) {
					if (posStart == posEnd) res++;
					else res += 2;
					posEnd -= chars.length();
					chars = "";
					ch = posEnd >= 0 ? text.charAt(posEnd) : 0;
				}
			}
			posStart++;
		}
		return res;
	}
}
