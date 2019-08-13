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
		System.out.println(new ShortestCommonSupersequence().shortestCommonSupersequence(
				"bcaaacbbbcbdcaddadcacbdddcdcccdadadcbabaccbccdcdcbcaccacbbdcbabb",
				"dddbbdcbccaccbababaacbcbacdddcdabadcacddbacadabdabcdbaaabaccbdaa"));
		System.out.println(new ShortestCommonSupersequence().shortestCommonSupersequence(
				"atdznrqfwlfbcqkezrltzyeqvqemikzgghxkzenhtapwrmrovwtpzzsyiwongllqmvptwammerobtgmkpowndejvbuwbporfyroknrjoekdgqqlgzxiisweeegxajqlradgcciavbpgqjzwtdetmtallzyukdztoxysggrqkliixnagwzmassthjecvfzmyonglocmvjnxkcwqqvgrzpsswnigjthtkuawirecfuzrbifgwolpnhcapzxwmfhvpfmqapdxgmddsdlhteugqoyepbztspgojbrmpjmwmhnldunskpvwprzrudbmtwdvgyghgprqcdgqjjbyfsujnnssfqvjhnvcotynidziswpzhkdszbblustoxwtlhkowpatbypvkmajumsxqqunlxxvfezayrolwezfzfyzmmneepwshpemynwzyunsxgjflnqmfghsvwpknqhclhrlmnrljwabwpxomwhuhffpfinhnairblcayygghzqmotwrywqayvvgohmujneqlzurxcpnwdipldofyvfdurbsoxdurlofkqnrjomszjimrxbqzyazakkizojwkuzcacnbdifesoiesmkbyffcxhqgqyhwyubtsrqarqagogrnaxuzyggknksrfdrmnoxrctntngdxxechxrsbyhtlbmzgmcqopyixdomhnmvnsafphpkdgndcscbwyhueytaeodlhlzczmpqqmnilliydwtxtpedbncvsqauopbvygqdtcwehffagxmyoalogetacehnbfxlqhklvxfzmrjqofaesvuzfczeuqegwpcmahhpzodsmpvrvkzxxtsdsxwixiraphjlqawxinlwfspdlscdswtgjpoiixbvmpzilxrnpdvigpccnngxmlzoentslzyjjpkxemyiemoluhqifyonbnizcjrlmuylezdkkztcphlmwhnkdguhelqzjgvjtrzofmtpuhifoqnokonhqtzxmimp",
				"xjtuwbmvsdeogmnzorndhmjoqnqjnhmfueifqwleggctttilmfokpgotfykyzdhfafiervrsyuiseumzmymtvsdsowmovagekhevyqhifwevpepgmyhnagjtsciaecswebcuvxoavfgejqrxuvnhvkmolclecqsnsrjmxyokbkesaugbydfsupuqanetgunlqmundxvduqmzidatemaqmzzzfjpgmhyoktbdgpgbmjkhmfjtsxjqbfspedhzrxavhngtnuykpapwluameeqlutkyzyeffmqdsjyklmrxtioawcrvmsthbebdqqrpphncthosljfaeidboyekxezqtzlizqcvvxehrcskstshupglzgmbretpyehtavxegmbtznhpbczdjlzibnouxlxkeiedzoohoxhnhzqqaxdwetyudhyqvdhrggrszqeqkqqnunxqyyagyoptfkolieayokryidtctemtesuhbzczzvhlbbhnufjjocporuzuevofbuevuxhgexmckifntngaohfwqdakyobcooubdvypxjjxeugzdmapyamuwqtnqspsznyszhwqdqjxsmhdlkwkvlkdbjngvdmhvbllqqlcemkqxxdlldcfthjdqkyjrrjqqqpnmmelrwhtyugieuppqqtwychtpjmloxsckhzyitomjzypisxzztdwxhddvtvpleqdwamfnhhkszsfgfcdvakyqmmusdvihobdktesudmgmuaoovskvcapucntotdqxkrovzrtrrfvoczkfexwxujizcfiqflpbuuoyfuoovypstrtrxjuuecpjimbutnvqtiqvesaxrvzyxcwslttrgknbdcvvtkfqfzwudspeposxrfkkeqmdvlpazzjnywxjyaquirqpinaennweuobqvxnomuejansapnsrqivcateqngychblywxtdwntancarldwnloqyywrxrganyehkglbdeyshpodpmdchbcc"));
	}

	String[][] cache;

	public String shortestCommonSupersequence(String str1, String str2) {
		cache = new String[str1.length() + 1][str2.length() + 1];
		return moveForward(str1, str2, 0, 0);
	}

	private String moveForward(String s1, String s2, int i1, int i2) {
		if (cache[i1][i2] != null) return cache[i1][i2];

		if (i1 == s1.length() && i2 == s2.length()) {
			cache[i1][i2] = "";
		} else if (i1 == s1.length()) {
			cache[i1][i2] = s2.charAt(i2) + moveForward(s1, s2, i1, i2 + 1);
		} else if (i2 == s2.length()) {
			cache[i1][i2] = s1.charAt(i1) + moveForward(s1, s2, i1 + 1, i2);
		} else {
			char ch1 = s1.charAt(i1);
			char ch2 = s2.charAt(i2);
			if (ch1 == ch2) {
				String res = moveForward(s1, s2, i1 + 1, i2 + 1);
				cache[i1][i2] = ch1 + res;
			} else {
				String res1 = moveForward(s1, s2, i1 + 1, i2);
				String res2 = moveForward(s1, s2, i1, i2 + 1);
				if (res1.length() < res2.length()) {
					cache[i1][i2] = ch1 + res1;
				} else {
					cache[i1][i2] = ch2 + res2;
				}
			}
		}
		return cache[i1][i2];
	}

}
