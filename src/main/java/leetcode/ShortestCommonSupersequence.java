package leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
		System.out.println(new ShortestCommonSupersequence().shortestCommonSupersequence(
				"bcaaacbbbcbdcaddadcacbdddcdcccdadadcbabaccbccdcdcbcaccacbbdcbabb",
				"dddbbdcbccaccbababaacbcbacdddcdabadcacddbacadabdabcdbaaabaccbdaa"));
		System.out.println(new ShortestCommonSupersequence().shortestCommonSupersequence(
				"atdznrqfwlfbcqkezrltzyeqvqemikzgghxkzenhtapwrmrovwtpzzsyiwongllqmvptwammerobtgmkpowndejvbuwbporfyroknrjoekdgqqlgzxiisweeegxajqlradgcciavbpgqjzwtdetmtallzyukdztoxysggrqkliixnagwzmassthjecvfzmyonglocmvjnxkcwqqvgrzpsswnigjthtkuawirecfuzrbifgwolpnhcapzxwmfhvpfmqapdxgmddsdlhteugqoyepbztspgojbrmpjmwmhnldunskpvwprzrudbmtwdvgyghgprqcdgqjjbyfsujnnssfqvjhnvcotynidziswpzhkdszbblustoxwtlhkowpatbypvkmajumsxqqunlxxvfezayrolwezfzfyzmmneepwshpemynwzyunsxgjflnqmfghsvwpknqhclhrlmnrljwabwpxomwhuhffpfinhnairblcayygghzqmotwrywqayvvgohmujneqlzurxcpnwdipldofyvfdurbsoxdurlofkqnrjomszjimrxbqzyazakkizojwkuzcacnbdifesoiesmkbyffcxhqgqyhwyubtsrqarqagogrnaxuzyggknksrfdrmnoxrctntngdxxechxrsbyhtlbmzgmcqopyixdomhnmvnsafphpkdgndcscbwyhueytaeodlhlzczmpqqmnilliydwtxtpedbncvsqauopbvygqdtcwehffagxmyoalogetacehnbfxlqhklvxfzmrjqofaesvuzfczeuqegwpcmahhpzodsmpvrvkzxxtsdsxwixiraphjlqawxinlwfspdlscdswtgjpoiixbvmpzilxrnpdvigpccnngxmlzoentslzyjjpkxemyiemoluhqifyonbnizcjrlmuylezdkkztcphlmwhnkdguhelqzjgvjtrzofmtpuhifoqnokonhqtzxmimp",
				"xjtuwbmvsdeogmnzorndhmjoqnqjnhmfueifqwleggctttilmfokpgotfykyzdhfafiervrsyuiseumzmymtvsdsowmovagekhevyqhifwevpepgmyhnagjtsciaecswebcuvxoavfgejqrxuvnhvkmolclecqsnsrjmxyokbkesaugbydfsupuqanetgunlqmundxvduqmzidatemaqmzzzfjpgmhyoktbdgpgbmjkhmfjtsxjqbfspedhzrxavhngtnuykpapwluameeqlutkyzyeffmqdsjyklmrxtioawcrvmsthbebdqqrpphncthosljfaeidboyekxezqtzlizqcvvxehrcskstshupglzgmbretpyehtavxegmbtznhpbczdjlzibnouxlxkeiedzoohoxhnhzqqaxdwetyudhyqvdhrggrszqeqkqqnunxqyyagyoptfkolieayokryidtctemtesuhbzczzvhlbbhnufjjocporuzuevofbuevuxhgexmckifntngaohfwqdakyobcooubdvypxjjxeugzdmapyamuwqtnqspsznyszhwqdqjxsmhdlkwkvlkdbjngvdmhvbllqqlcemkqxxdlldcfthjdqkyjrrjqqqpnmmelrwhtyugieuppqqtwychtpjmloxsckhzyitomjzypisxzztdwxhddvtvpleqdwamfnhhkszsfgfcdvakyqmmusdvihobdktesudmgmuaoovskvcapucntotdqxkrovzrtrrfvoczkfexwxujizcfiqflpbuuoyfuoovypstrtrxjuuecpjimbutnvqtiqvesaxrvzyxcwslttrgknbdcvvtkfqfzwudspeposxrfkkeqmdvlpazzjnywxjyaquirqpinaennweuobqvxnomuejansapnsrqivcateqngychblywxtdwntancarldwnloqyywrxrganyehkglbdeyshpodpmdchbcc"));
	}

	public String shortestCommonSupersequence(String str1, String str2) {
		int len1 = str1.length();
		int len2 = str2.length();
		// calc the shortest path for possible sequences
		int[][] pathCalc = new int[len1 + 1][len2 + 1];
		for (int i = 0; i <= len1; i++) {
			pathCalc[i][0] = i;
		}
		for (int j = 0; j <= len2; j++) {
			pathCalc[0][j] = j;
		}
		for (int i = 1; i <= len1; i++) {
			for (int j = 1; j <= len2; j++) {
				char ch1 = str1.charAt(i - 1);
				char ch2 = str2.charAt(j - 1);
				if (ch1 == ch2) pathCalc[i][j] = pathCalc[i - 1][j - 1] + 1;
				else pathCalc[i][j] = Math.min(pathCalc[i][j - 1], pathCalc[i - 1][j]) + 1;
			}
		}

		// choose the sequence
		int i = len1;
		int j = len2;
		int curNum = pathCalc[i][j];
		List<Character> res = new LinkedList<>();
		char ch;
		while (curNum > 0) {
			if (i > 0 && j > 0 && str1.charAt(i-1) == str2.charAt(j-1)) {
				i--;
				j--;
				ch = str1.charAt(i);
			} else if (i > 0 && pathCalc[i - 1][j] == curNum - 1) {
				i--;
				ch = str1.charAt(i);
			} else {
				j--;
				ch = str2.charAt(j);
			}
			res.add(0, ch);
			curNum = pathCalc[i][j];
		}

		return res.stream().map(Object::toString).collect(Collectors.joining());
	}

}