package algorithms.iterative_dfs;

import leetcode.TreeNode;

import java.util.Stack;

public class IterativeDfs {

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		TreeNode newNode2 = new TreeNode(2);
		TreeNode newNode3 = new TreeNode(3);
		TreeNode newNode4 = new TreeNode(4);
		TreeNode newNode5 = new TreeNode(5);
		TreeNode newNode6 = new TreeNode(6);
		TreeNode newNode7 = new TreeNode(7);
		TreeNode newNode8 = new TreeNode(8);
		root.left = newNode2;
		root.right = newNode3;
		newNode2.left = newNode4;
		newNode2.right = newNode5;
		newNode3.left = newNode8;
		newNode4.right = newNode6;
		newNode5.left = newNode7;

		TreeNode.printTree(root);

		new IterativeDfs().iterate(root);
	}

	private void iterate(TreeNode root) {
		Stack<TreeNode> stack = new Stack<>();
		TreeNode current = root;

		while (current != null) {
			while (current.left != null) {
				stack.add(current);
				process(current);
				current = current.left;
			}
			process(current);
			while (current.right == null && !stack.isEmpty()) {
				current = stack.pop();
			}
			current = current.right;
		}
	}

	private void process(TreeNode node) {
		System.out.println(node.val);
	}
}
