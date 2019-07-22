package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class InsertIntoBinarySearchTree {
    /*
    Given the root node of a binary search tree (BST) and a value to be inserted into the tree, insert the value into the BST.
    Return the root node of the BST after the insertion. It is guaranteed that the new value does not exist in the original BST.

Note that there may exist multiple valid ways for the insertion, as long as the tree remains a BST after insertion. You can return any of them.

For example,

Given the tree:
        4
       / \
      2   7
     / \
    1   3
And the value to insert: 5

You can return this binary search tree:

         4
       /   \
      2     7
     / \   /
    1   3 5

This tree is also valid:

         5
       /   \
      2     7
     / \
    1   3
         \
          4

     */

    public TreeNode insertIntoBST(TreeNode root, int val) {
        insertIntoBSTHelper(root, val);
        return root;
    }

    private TreeNode insertIntoBSTHelper(TreeNode node, int val) {
        if (val < node.val) {
            if (node.left != null) {
                return insertIntoBSTHelper(node.left, val);
            } else {
                node.left = new TreeNode(val);
                return node.left;
            }
        } else {
            if (node.right != null) {
                return insertIntoBSTHelper(node.right, val);
            } else {
                node.right = new TreeNode(val);
                return node.right;
            }
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        new InsertIntoBinarySearchTree().insertIntoBST(root, 5);
        printTree(root);
    }

    static Queue<TreeNode> queue = new LinkedList<>();

    private static void printTree(TreeNode root) {
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
