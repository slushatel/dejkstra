package leetcode;

import java.util.Stack;

/*
1028. Recover a Tree From Preorder Traversal
We run a preorder depth first search on the root of a binary tree.

At each node in this traversal, we output D dashes (where D is the depth of this node), then we output the value of this node.  (If the depth of a node is D, the depth of its immediate child is D+1.  The depth of the root node is 0.)

If a node has only one child, that child is guaranteed to be the left child.

Given the output S of this traversal, recover the tree and return its root.

 */
public class RecoverATree {
    int dashCount = 0;
    String currentNumber = "";
    TreeNode tree;
    TreeNode currentNode;
    Stack<TreeNode> traverseStack = new Stack<>();

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode recoverFromPreorder(String S) {
        int i = 0;
        char ch;
        while (i < S.length()) {
            ch = S.charAt(i);
            if (ch == '-') {
                if (!currentNumber.isEmpty()) processNumber();
                dashCount++;
            } else if (ch >= '0' && ch <= '9') {
                currentNumber += ch;
            }
            i++;
        }
        processNumber();
        return tree;
    }

    void processNumber() {
        int level = dashCount;
        int val = Integer.valueOf(currentNumber);
        if (level == 0) {
            currentNode = new TreeNode(val);
            tree = currentNode;
            traverseStack.push(currentNode);
        } else {
            int currentLevel = traverseStack.size() - 1;
            if (level > currentLevel) {
                currentNode.left = new TreeNode(val);
                currentNode = currentNode.left;
                traverseStack.push(currentNode);
            } else {
                int levelDiff = currentLevel - level + 1;
                for (int i = 0; i < levelDiff; i++) {
                    traverseStack.pop();
                }
                currentNode = traverseStack.peek();

                currentNode.right = new TreeNode(val);
                currentNode = currentNode.right;
                traverseStack.push(currentNode);
            }
        }

        dashCount = 0;
        currentNumber = "";
    }

    public static void main(String[] args) {
        String s = "1-2--3--4-5--6--7";
        TreeNode root = new RecoverATree().recoverFromPreorder(s);
        printTree(root);
    }

    static void printTree(TreeNode root) {
        if (root == null) return;
        System.out.println(root.val);
        printTree(root.left);
        printTree(root.right);
    }

}
