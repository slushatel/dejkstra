package hackerrank;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CrosswordPuzzle {

	static int n = 10;
	static List<WordPlace> crossData = null;
	static String[] wordsResult;

	static String[] crosswordPuzzle(String[] crossword, String words) {
		crossData = parseCrossword(crossword);
		String[] wordsArr = words.split(";");
		wordsResult = new String[wordsArr.length];
		if (!putWord(0, wordsArr)) {
			return new String[0];
		}
		return wordsResult;
	}

	static boolean putWord(int i, String[] words) {
		if (i==words.length) return true;
		for (String word : words) {
			wordsResult[i] = word;
			if (checkWord(i) && putWord(i + 1, words)) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}

	static boolean checkWord(int n) {
		if (crossData.get(n).length != wordsResult[n].length()) return false;
		for (int i = 0; i < n; i++) {
			if (crossData.get(i).isVertical != crossData.get(n).isVertical) {
				int iv, ih;
				if (crossData.get(i).isVertical) {
					iv = i;
					ih = n;
				} else {
					iv = n;
					ih = i;
				}
				int xv = crossData.get(iv).startCol;
				int yv = crossData.get(iv).startRow;
				int lenv = crossData.get(iv).length;

				int xh = crossData.get(ih).startCol;
				int yh = crossData.get(ih).startRow;
				int lenh = crossData.get(ih).length;

				if (xv - xh + 1 > 0 && xv - xh + 1 <= lenh && yh - yv + 1 > 0 && yh - yv + 1 <= lenv) {
					if (wordsResult[iv].charAt(yh-yv) != wordsResult[ih].charAt(xv-xh)) return false;
				}
			}
		}
		return true;
	}

	private static List<WordPlace> parseCrossword(String[] crossword) {
		List<WordPlace> res = new LinkedList<>();
		int len = 0;
		char[][] crosswordChars = new char[n][n];
		for (int i = 0; i < n; i++) {
			crosswordChars[i] = crossword[i].toCharArray();
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (crosswordChars[i][j] == '+') {
					if (len > 0) {
						if (len > 1)
							res.add(new WordPlace(i, j - len, len, false));
						len = 0;
					}
				} else if (crosswordChars[i][j] == '-') {
					len++;
				}
			}
			if (len > 0) {
				res.add(new WordPlace(i, n - len, len, false));
				len = 0;
			}
		}
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				if (crosswordChars[i][j] == '+') {
					if (len > 0) {
						if (len > 1)
							res.add(new WordPlace(i - len, j, len, true));
						len = 0;
					}
				} else if (crosswordChars[i][j] == '-') {
					len++;
				}
			}
			if (len > 0) {
				res.add(new WordPlace(n - len, j, len, true));
				len = 0;
			}
		}
		return res;
	}

	static class WordPlace {
		final int startRow;
		final int startCol;
		final int length;
		final boolean isVertical;

		public WordPlace(int startRow, int startCol, int length, boolean isVertical) {
			this.startRow = startRow;
			this.startCol = startCol;
			this.length = length;
			this.isVertical = isVertical;
		}
	}

	public static void main(String[] args) throws IOException {
		String[] crossword = {
				"+-++++++++",
				"+-++++++++",
				"+-++++++++",
				"+-----++++",
				"+-+++-++++",
				"+-+++-++++",
				"+++++-++++",
				"++------++",
				"+++++-++++",
				"+++++-++++"
		};
		String words = "LONDON;DELHI;ICELAND;ANKARA";
		String[] result = crosswordPuzzle(crossword, words);
		System.out.println(Arrays.toString(result));
	}
}

