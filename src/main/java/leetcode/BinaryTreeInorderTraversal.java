package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTreeInorderTraversal {
	/*
	94. Binary Tree Inorder Traversal
Medium

Given a binary tree, return the inorder traversal of its nodes' values.

Example:

Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [1,3,2]

Follow up: Recursive solution is trivial, could you do it iteratively?

	 */

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		root.right = node2;
		TreeNode node3 = new TreeNode(3);
		node2.left = node3;

		List<Integer> res = new BinaryTreeInorderTraversal().inorderTraversal(root);
		TreeNode.printTree(root);
		System.out.println();
		for (int n : res) {
			System.out.print(n + ", ");
		}
		System.out.println();
	}

	/**
	 * Definition for a binary tree node.
	 * public class TreeNode {
	 * int val;
	 * TreeNode left;
	 * TreeNode right;
	 * TreeNode(int x) { val = x; }
	 * }
	 */
	public List<Integer> inorderTraversal(TreeNode root) {
		List<Integer> res = new ArrayList<>();
		if (root == null) return res;
		Stack<TreeNode> nodeStack = new Stack<>();

		Direction direction = Direction.TO_LEFT_CHILD;
		nodeStack.push(root);
		while (true) {
			TreeNode currentNode = nodeStack.peek();
			if (direction == Direction.TO_LEFT_CHILD) {
				if (currentNode.left != null) {
					nodeStack.push(currentNode.left);
					direction = Direction.TO_LEFT_CHILD; // can be removed
				} else {
					res.add(currentNode.val);
					direction = Direction.TO_RIGHT_CHILD;
				}
			} else if (direction == Direction.TO_RIGHT_CHILD) {
				if (currentNode.right != null) {
					nodeStack.push(currentNode.right);
					direction = Direction.TO_LEFT_CHILD;
				} else {
					direction = Direction.TO_PARENT;
				}
			} else if (direction == Direction.TO_PARENT) {
				if (currentNode.equals(root)) break;
				TreeNode oldNode = nodeStack.pop();
				currentNode = nodeStack.peek();
				if (oldNode.equals(currentNode.left)) {
					res.add(currentNode.val);
					direction = Direction.TO_RIGHT_CHILD;
				}
				else {
					direction = Direction.TO_PARENT;
				}
			}
		}
		return res;
	}

	enum Direction {
		TO_LEFT_CHILD, TO_RIGHT_CHILD, TO_PARENT
	}

}
