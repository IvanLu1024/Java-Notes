package code_05_binaryTree;

/**
 * 110. Balanced Binary Tree
 *
 * Given a binary tree, determine if it is height-balanced.
 *
 * For this problem, a height-balanced binary tree is defined as:
 * a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
 */
public class Code_110_BalancedBinaryTree {
    public boolean isBalanced(TreeNode root) {
        if(root==null){
            return true;
        }
        if(root.left==null && root.right==null){
            return true;
        }
        //获取该树的左右子树的深度
        int l=getTreeDepth(root.left);
        int r=getTreeDepth(root.right);
        if(Math.abs(l-r)>1){
            return false;
        }
        return isBalanced(root.left) && isBalanced(root.right);
    }

    //得到一棵树的深度
    private int getTreeDepth(TreeNode root){
        if(root==null){
            return 0;
        }
        return Math.max(getTreeDepth(root.left),getTreeDepth(root.right))+1;
    }
}
