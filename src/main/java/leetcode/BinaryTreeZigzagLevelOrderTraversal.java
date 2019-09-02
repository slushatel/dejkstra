package leetcode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class BinaryTreeZigzagLevelOrderTraversal {
	/*
	103. Binary Tree Zigzag Level Order Traversal
Medium

Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).

For example:
Given binary tree [3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7

return its zigzag level order traversal as:

[
  [3],
  [20,9],
  [15,7]
]

	 */

	public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
		Deque<TreeNode> list = new LinkedList<>();
		if (root != null) list.add(root);
		List<List<Integer>> res = new LinkedList<>();

		boolean leftToRight = true;
		while (!list.isEmpty()) {
			int n = list.size();
			List<Integer> newList = new LinkedList<>();
			res.add(newList);
			for (int i = 0; i < n; i++) {
				if (leftToRight) {
					TreeNode node = list.pollFirst();
					newList.add(node.val);
					if (node.left != null) list.addLast(node.left);
					if (node.right != null) list.addLast(node.right);
				} else {
					TreeNode node = list.pollLast();
					newList.add(node.val);
					if (node.right != null) list.addFirst(node.right);
					if (node.left != null) list.addFirst(node.left);
				}
			}
			leftToRight = !leftToRight;
		}

		return res;
	}

}
