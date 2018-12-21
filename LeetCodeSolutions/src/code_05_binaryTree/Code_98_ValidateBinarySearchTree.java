package code_05_binaryTree;

/**
 * Given a binary tree, determine if it is a valid binary search tree (BST).
 *
 * Assume a BST is defined as follows:
 *  The left subtree of a node contains only nodes with keys less than the node's key.
 *  The right subtree of a node contains only nodes with keys greater than the node's key.
 *  Both the left and right subtrees must also be binary search trees.
 */
public class Code_98_ValidateBinarySearchTree {
    //注意数值范围的问题，有的节点的值超过了int类型的范围
    public boolean isValidBST(TreeNode root) {
        return isValid(root,Long.MIN_VALUE,Long.MAX_VALUE);
    }

    //判断左子树最大值为leftMax,右子树最小值为rightMin的树是否是BST
    public boolean isValid(TreeNode root,long leftMax,long rightMin){
        if(root==null){
            return true;
        }
        if(root.left==null && root.right==null){
            return true;
        }
        if(root.val<=leftMax || root.val>=rightMin){
            return false;
        }
        return isValid(root.left,leftMax,root.val) && isValid(root.right,root.val,rightMin);
    }
}
