package code_05_binaryTree;

/**
 * 236. Lowest Common Ancestor of a Binary Tree
 *
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 *
 * According to the definition of LCA on Wikipedia:
 * “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants
 * (where we allow a node to be a descendant of itself).”
 */
public class Code_236_LowestCommonAncestorOfABinaryTree {
    // 在root中寻找p和q
    // 如果p和q都在root所在的二叉树中, 则返回LCA
    // 如果p和q只有一个在root所在的二叉树中, 则返回p或者q
    // 如果p和q均不在root所在的二叉树中, 则返回NULL
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null){
            return null;
        }
        //根节点是p或者q，则就返回该根节点就可以了
        if(root==p || root==q){
            return root;
        }
        //看看root的最子树是否包含p,q
        TreeNode left=lowestCommonAncestor(root.left,p,q);
        TreeNode right=lowestCommonAncestor(root.right,p,q);
        //p,q的最小公共祖先
        //要么是root
        //要么是在root的左子树
        //要么是在root的右子树
        if(left!=null && right!=null){
            return root;
        }
        if(left!=null){
            return left;
        }
        if(right!=null){
            return right;
        }
        return null;
    }
}
