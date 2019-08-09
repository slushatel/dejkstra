package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class BraceExpansionII {
	/*

	1096. Brace Expansion II
Hard

Under a grammar given below, strings can represent a set of lowercase words.  Let's use R(expr) to denote the set of words the expression represents.

Grammar can best be understood through simple examples:

    Single letters represent a singleton set containing that word.
        R("a") = {"a"}
        R("w") = {"w"}
    When we take a comma delimited list of 2 or more expressions, we take the union of possibilities.
        R("{a,b,c}") = {"a","b","c"}
        R("{{a,b},{b,c}}") = {"a","b","c"} (notice the final set only contains each word at most once)
    When we concatenate two expressions, we take the set of possible concatenations between two words where the first word comes from the first expression and the second word comes from the second expression.
        R("{a,b}{c,d}") = {"ac","ad","bc","bd"}
        R("a{b,c}{d,e}f{g,h}") = {"abdfg", "abdfh", "abefg", "abefh", "acdfg", "acdfh", "acefg", "acefh"}

Formally, the 3 rules for our grammar:

    For every lowercase letter x, we have R(x) = {x}
    For expressions e_1, e_2, ... , e_k with k >= 2, we have R({e_1,e_2,...}) = R(e_1) ∪ R(e_2) ∪ ...
    For expressions e_1 and e_2, we have R(e_1 + e_2) = {a + b for (a, b) in R(e_1) × R(e_2)}, where + denotes concatenation, and × denotes the cartesian product.

Given an expression representing a set of words under the given grammar, return the sorted list of words that the expression represents.



Example 1:

Input: "{a,b}{c,{d,e}}"
Output: ["ac","ad","ae","bc","bd","be"]

Example 2:

Input: "{{a,z},a{b,c},{ab,z}}"
Output: ["a","ab","ac","z"]
Explanation: Each distinct word is written only once in the final answer.



Constraints:

    1 <= expression.length <= 50
    expression[i] consists of '{', '}', ','or lowercase English letters.
    The given expression represents a set of words based on the grammar given in the description.


	 */

	public static void main(String[] args) {
		{
			String s = "{a,b}{c,{d,e}}";
			System.out.println(Arrays.toString(new BraceExpansionII().braceExpansionII(s).toArray()));
		}
		{
			String s = "{{a,z},a{b,c},{ab,z}}";
			System.out.println(Arrays.toString(new BraceExpansionII().braceExpansionII(s).toArray()));
		}
	}

	private Stack<String> operationStack = new Stack<>();

	public List<String> braceExpansionII(String expression) {
		int i = 0;
		List<String> res = new ArrayList<>();
		while (i < expression.length()) {
			char ch = expression.charAt(i);
			if (ch == '{') {
				operationStack.add(String.valueOf(ch));
			} else if (ch == '}') {
				calcStackUntilParenth();
			} else if (ch == ',') {
				operationStack.add(String.valueOf(ch));
			} else {
				operationStack.add(String.valueOf(ch));
				calcStackMultiplication();
			}
			i++;
		}
		if (operationStack.isEmpty()) return new ArrayList<>();
		return Arrays.stream(operationStack.lastElement().split(";")).distinct().sorted().collect(Collectors.toList());
	}

	private void calcStackUntilParenth() {
		String s = operationStack.pop();
		if (s.equals("{")) return;
		while (true) {
			String oper = operationStack.pop();
			if (oper.equals("{")) break;
			String s2 = operationStack.pop();
			s = s2 + ";" + s;
		}
		operationStack.push(s);
		calcStackMultiplication();
	}

	private void calcStackMultiplication() {
		if (operationStack.size() < 2) return;
		if (isValue(operationStack.elementAt(operationStack.size() - 2))
				&& isValue(operationStack.lastElement())) {
			String str1 = operationStack.pop();
			String str2 = operationStack.pop();
			List<String> res = new ArrayList<>();
			for (String s1 : str1.split(";")) {
				for (String s2 : str2.split(";")) {
					res.add(s2 + s1);
				}
			}
			operationStack.push(String.join(";", res));
		}
	}

	boolean isValue(String s) {
		return !(s.equals("{") || s.equals(","));
	}
}
