package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class TreeNode {
	public int val;
	public TreeNode left;
	public TreeNode right;

	public TreeNode(int x) {
		val = x;
	}

	private static Queue<TreeNode> queue = new LinkedList<>();

	public static void printTree(TreeNode root) {
		if (root == null) return;
		queue.add(root);
		printTreeHelper(queue, 1);
	}

	private static void printTreeHelper(Queue<TreeNode> queue, int level) {
		if (queue.isEmpty()) return;

		int len = queue.size();
		for (int i = 0; i < len; i++) {
			TreeNode node = queue.poll();
			System.out.print(node.val + "\t");
			if (node.left != null) queue.add(node.left);
			if (node.right != null) queue.add(node.right);
		}
		System.out.println();
		printTreeHelper(queue, level + 1);
	}

}
