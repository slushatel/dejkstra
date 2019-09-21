package leetcode;

public class ImplementTrie {

	static class Trie {

		NodeValue root = new NodeValue();

		/**
		 * Initialize your data structure here.
		 */
		public Trie() {

		}

		/**
		 * Inserts a word into the trie.
		 */
		public void insert(String word) {
			root = insertIntoNode(word, 0, root);
		}

		private NodeValue insertIntoNode(String word, int start, NodeValue root) {

			if (root == null) root = new NodeValue();
			if (start == word.length()) {
				root.isWord = true;
				return root;
			}

			char ch = word.charAt(start);
			NodeValue nodeToAdd = root.getChildrenToWrite()[getChildIndex(ch)];
			root.children[getChildIndex(ch)] = insertIntoNode(word, start + 1, nodeToAdd);
			return root;
		}

		private int getChildIndex(char ch) {
			return ch - 'a';
		}

		/**
		 * Returns if the word is in the trie.
		 */
		public boolean search(String word) {
			return searchHelper(word, 0, root, false);
		}

		public boolean searchHelper(String word, int start, NodeValue root, boolean prefixSearch) {
			if (start == word.length()) return prefixSearch || root.isWord;

			char ch = word.charAt(start);
			if (root.children == null || root.children[getChildIndex(ch)] == null) return false;
			return  searchHelper(word, start +1, root.children[getChildIndex(ch)], prefixSearch);
		}

		/**
		 * Returns if there is any word in the trie that starts with the given prefix.
		 */
		public boolean startsWith(String prefix) {
			return searchHelper(prefix, 0, root, true);
		}

		class NodeValue {
			boolean isWord;
			NodeValue[] children;

			NodeValue[] getChildrenToWrite() {
				if (children == null) children = new NodeValue[getChildIndex('z') + 1];
				return children;
			}
		}
	}


	/**
	 * Your Trie object will be instantiated and called as such:
	 * Trie obj = new Trie();
	 * obj.insert(word);
	 * boolean param_2 = obj.search(word);
	 * boolean param_3 = obj.startsWith(prefix);
	 */

	public static void main(String[] args) {
		Trie obj = new Trie();
		System.out.println(obj.search("a"));
		obj.insert("ab");
		obj.insert("abcd");
		obj.insert("abce");
		System.out.println(obj.search("ab"));
		System.out.println(obj.search("abc"));
		System.out.println(obj.search("abce"));

		System.out.println(obj.startsWith("ab"));
		System.out.println(obj.startsWith("abc"));
		System.out.println(obj.startsWith("abce"));
		System.out.println(obj.startsWith("abcf"));

	}
}
