package code_05_binaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 173. Binary Search Tree Iterator
 *
 * Implement an iterator over a binary search tree (BST).
 * Your iterator will be initialized with the root node of a BST.
 * Calling next() will return the next smallest number in the BST.
 */
public class Code_173_BinarySearchTreeIterator {
    /**
     * 思路一：
     * 就是利用BST的中序遍历性质(递归思路)
     * 空间复杂度：O(n)
     * 时间复杂度： next() --> O(1)
     *              hasNext() --> O(1)
     */
    class BSTIterator1 {
        private List<Integer> data=new ArrayList<>();
        private int nextIndex;

        public BSTIterator1(TreeNode root) {
            inOrder(root);
            nextIndex=0;
        }

        private void inOrder(TreeNode root){
            if(root==null){
                return;
            }
            inOrder(root.left);
            data.add(root.val);
            inOrder(root.right);
        }

        /** @return the next smallest number */
        public int next() {
            return data.get(nextIndex++);
        }

        /** @return whether we have a next smallest number */
        public boolean hasNext() {
            return nextIndex<data.size()-1;
        }
    }

    /**
     * 思路二：非递归方式
     * 空间复杂度：O(h)
     * 时间复杂度：next() --> O(1)
     *             hasNext() --> O(h)
     */
    class BSTIterator{
        private Stack<TreeNode> myStack;

        public BSTIterator(TreeNode root) {
            myStack=new Stack<>();
            TreeNode node = root;
            while(node != null){
                myStack.push(node);
                node = node.left;
            }
        }

        /** @return the next smallest number */
        public int next() {
            assert(hasNext());
            TreeNode retNode = myStack.pop();

            TreeNode node = retNode.right;
            while(node != null){
                myStack.push(node);
                node = node.left;
            }
            return retNode.val;
        }

        /** @return whether we have a next smallest number */
        public boolean hasNext() {
            return !myStack.isEmpty();
        }
    }
}
