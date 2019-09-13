package leetcode;

public class ImplementTrie {

	class Trie {

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
			NodeValue currentValue = root;
			for (int i = 0; i < word.length(); i++) {
				char ch = word.charAt(i);
				if (currentValue.child == null) {
					currentValue.child = new Node();
				}
				currentValue = currentValue.child.values[ch - 'a'];

				if (currentNode.values[ch - 'a'] == null) {
					currentNode.values[ch - 'a'] = new NodeValue();
				}
			}
		}

		/**
		 * Returns if the word is in the trie.
		 */
		public boolean search(String word) {
			return true;
		}

		/**
		 * Returns if there is any word in the trie that starts with the given prefix.
		 */
		public boolean startsWith(String prefix) {
			return true;
		}

		class Node {
			NodeValue[] values = new NodeValue[1 + 'z' - 'a'];


		}

		class NodeValue {
			Node child;
			boolean isWord;

		}
	}


/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */

}
