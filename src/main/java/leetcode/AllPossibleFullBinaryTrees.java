package leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AllPossibleFullBinaryTrees {
	/*
	894. All Possible Full Binary Trees
Medium

A full binary tree is a binary tree where each node has exactly 0 or 2 children.

Return a list of all possible full binary trees with N nodes.  Each element of the answer is the root node of one possible tree.

Each node of each tree in the answer must have node.val = 0.

You may return the final list of trees in any order.



Example 1:

Input: 7
Output: [[0,0,0,null,null,0,0,null,null,0,0],[0,0,0,null,null,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,null,null,null,null,0,0],[0,0,0,0,0,null,null,0,0]]
Explanation:



Note:

    1 <= N <= 20

	 */

	Map<Integer, List<TreeNode>> cache = new HashMap<>();

	public List<TreeNode> allPossibleFBT(int N) {
		if (cache.containsKey(N)) return cache.get(N);

		List<TreeNode> res = new LinkedList<>();
		if (N == 1) {
			TreeNode root = new TreeNode(0);
			res.add(root);
			return res;
		} else if (N % 2 != 0){
			for (int i = 1; i < N - 1; i += 2) {
				List<TreeNode> listL = allPossibleFBT(i);
				List<TreeNode> listR = allPossibleFBT(N - i - 1);
				for (int l = 0; l < listL.size(); l++) {
					for (int r = 0; r < listR.size(); r++) {
						TreeNode root = new TreeNode(0);
						root.left = listL.get(l);
						root.right = listR.get(r);
						res.add(root);
					}
				}
			}
		}
		cache.put(N, res);
		return res;
	}

	public static void main(String[] args) {
		List<TreeNode> res = new AllPossibleFullBinaryTrees().allPossibleFBT(7);
		for (TreeNode root : res) {
			TreeNode.printTree(root);
			System.out.println();
		}
	}


}
