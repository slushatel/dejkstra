/*
giving 10 words, create crossword
 */

import java.util.*;

public class Crossword {

	CrossBoard crossBoard;

	public static void main(String[] args) {
		String words = "SATELLITE;CATHEDRAL;CANADA;PLEASE;OTTAWA;TIBET;EASEL;NINES;DENSE;GAS";
		System.out.println(Arrays.toString(new Crossword().doJob(words.split(";"))));
	}

	private WordPlace[] doJob(String[] words) {
		Arrays.sort(words, (o1, o2) -> ((Integer) o1.length()).compareTo(o2.length()));
		List<String> wordList = new ArrayList<>(Arrays.asList(words));
		crossBoard = new CrossBoard(words.length);
		processWords(wordList, 0);
		return crossBoard.wordPlaces;
	}

	private boolean processWords(List<String> words, int level) {
		for (String word : words) {
			boolean vertical = !crossBoard.moreVertical(level);
			if (crossBoard.findWordPosition(word, vertical, level) ||
					crossBoard.findWordPosition(word, !vertical, level)) {
				List<String> wordCopy = new ArrayList<>(words);
				wordCopy.remove(word);
				return processWords(wordCopy, level + 1);
			}
		}
		return false;
	}

	private List<Point> findIntersections(String word1, String word2) {
		List<Point> res = new LinkedList<>();
		for (int i = (word1.length() - 1) / 2; i >= 0; i--) {
			for (int j = (word2.length() - 1) / 2; j >= 0; j--) {
				int i2 = word1.length() - 1 - i;
				int j2 = word2.length() - 1 - j;

				if (word1.charAt(i) == word2.charAt(j)) {
					res.add(new Point(i, j));
				} else if (word1.charAt(i2) == word2.charAt(j)) {
					res.add(new Point(i2, j));
				} else if (word1.charAt(i) == word2.charAt(j2)) {
					res.add(new Point(i, j2));
				} else if (word1.charAt(i2) == word2.charAt(j2)) {
					res.add(new Point(i2, j2));
				}
			}
		}
		return res;
	}

	class CrossBoard {
		WordPlace[] wordPlaces;

		public CrossBoard(int wordCount) {
			wordPlaces = new WordPlace[wordCount];
		}

		private boolean findWordPosition(String word, boolean vertical, int level) {
			if (level == 0) {
				WordPlace newWp = new WordPlace(0, 0, vertical, word);
				wordPlaces[level] = newWp;
				return true;
			}

			for (int i = 0; i < level; i++) {
				WordPlace wp = wordPlaces[i];
				if (vertical != wp.isVertical) {
					List<Point> intersections = findIntersections(wp.word, word);
					for (Point p : intersections) {
						int x, y;
						if (vertical) {
							x = p.x;
							y = wp.startRow - p.y;
						} else {
							x = wp.startCol - p.x;
							y = p.y;
						}
						WordPlace newWp = new WordPlace(x, y, vertical, word);
						if (check(newWp, level)) {
							wordPlaces[level] = newWp;
							return true;
						}
					}
				}
			}
			return false;
		}

		boolean moreVertical(int level) {
			int vertCount = 0, horCount = 0;
			for (int i = 0; i < level; i++) {
				WordPlace wp = wordPlaces[i];
				if (wp.isVertical) {
					vertCount++;
				} else {
					horCount++;
				}
			}

			return vertCount > horCount;
		}

		private boolean check(WordPlace newWp, int level) {
			for (int i = 0; i < level; i++) {
				if (wordPlaces[i].isVertical != newWp.isVertical) {
					WordPlace vertWordPlace, horWordPlace;
					if (wordPlaces[i].isVertical) {
						vertWordPlace = wordPlaces[i];
						horWordPlace = newWp;
					} else {
						vertWordPlace = newWp;
						horWordPlace = wordPlaces[i];
					}
					int xv = vertWordPlace.startCol;
					int yv = vertWordPlace.startRow;
					int lenv = vertWordPlace.getLength();

					int xh = horWordPlace.startCol;
					int yh = horWordPlace.startRow;
					int lenh = horWordPlace.getLength();

					if (xv - xh + 1 > 0 && xv - xh + 1 <= lenh && yh - yv + 1 > 0 && yh - yv + 1 <= lenv) {
						if (vertWordPlace.word.charAt(yh - yv) != horWordPlace.word.charAt(xv - xh)) return false;
					}
				} else {
					if (wordPlaces[i].isVertical) {
						if (Math.abs(wordPlaces[i].startCol - newWp.startCol) <= 1 &&
								(newWp.startRow >= wordPlaces[i].startRow &&
										newWp.startRow <= wordPlaces[i].startRow + wordPlaces[i].getLength()) ||
								newWp.startRow + newWp.getLength() >= wordPlaces[i].startRow &&
										newWp.startRow + newWp.getLength() <= wordPlaces[i].startRow + wordPlaces[i].getLength() ||
								wordPlaces[i].startRow >= newWp.startRow &&
										wordPlaces[i].startRow <= newWp.startRow + newWp.getLength()
								) return false;
					} else {
						if (Math.abs(wordPlaces[i].startRow - newWp.startRow) <= 1 &&
								(newWp.startCol >= wordPlaces[i].startCol &&
										newWp.startCol <= wordPlaces[i].startCol + wordPlaces[i].getLength()) ||
								newWp.startCol + newWp.getLength() >= wordPlaces[i].startCol &&
										newWp.startCol + newWp.getLength() <= wordPlaces[i].startCol + wordPlaces[i].getLength() ||
								wordPlaces[i].startCol >= newWp.startCol &&
										wordPlaces[i].startCol <= newWp.startCol + newWp.getLength()
								) return false;
					}
				}
			}
			return true;
		}
	}

	class Point {
		final int x, y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	class WordPlace {
		final int startRow;
		final int startCol;
		final boolean isVertical;
		final String word;

		public WordPlace(int startRow, int startCol, boolean isVertical, String word) {
			this.startRow = startRow;
			this.startCol = startCol;
			this.isVertical = isVertical;
			this.word = word;
		}

		int getLength() {
			return word.length();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			WordPlace wordPlace = (WordPlace) o;
			return startRow == wordPlace.startRow &&
					startCol == wordPlace.startCol &&
					word.equals(wordPlace.word) &&
					isVertical == wordPlace.isVertical;
		}

		@Override
		public int hashCode() {
			return Objects.hash(startRow, startCol, isVertical, word);
		}

		@Override
		public String toString() {
			return "WordPlace{" +
					"startRow=" + startRow +
					", startCol=" + startCol +
					", word=" + word +
					", isVertical=" + isVertical +
					'}';
		}
	}
}
