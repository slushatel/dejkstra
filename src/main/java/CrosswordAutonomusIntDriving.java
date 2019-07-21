import java.io.IOException;
import java.util.Arrays;
import java.util.List;


class Result {

	/*
	 * Complete the 'findMatch' function below.
	 *
	 * The function is expected to return a STRING.
	 * The function accepts following parameters:
	 *  1. STRING_ARRAY possibleMatches
	 *  2. STRING crossword
	 */

	public static String findMatch(List<String> possibleMatches, String crossword) {
		String res = "";
		int patternLength = crossword.length();
		// we compare everything in lower case
		crossword = crossword.toLowerCase();
		// compare all string with pattern one by one
		stringLoop:
		for (String s : possibleMatches) {
			// check if the length is the same
			if (s.length() != patternLength) continue;
			int i = 0;
			// we need output in lower case, also we compare in lower case
			String sLower = s.toLowerCase();
			// compare characters one by one
			while (i < patternLength) {
				char patternChar = crossword.charAt(i);
				if (patternChar != '.' && patternChar != sLower.charAt(i)) {
					continue stringLoop;
				}
				i++;
			}
			res = sLower;
			break;
		}
		return res;
	}
}

public class CrosswordAutonomusIntDriving {
	public static void main(String[] args) throws IOException {
		String[] possibleMatches = {"Bolteon", "Jolteon", "flareon", "espeon", "umbreon", "leafeon", "glaceon", "sylveon"};
		String crossword = "j......";
		String result = Result.findMatch(Arrays.asList(possibleMatches), crossword);
		System.out.println(result);
	}
}
