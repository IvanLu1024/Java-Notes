package code_05_binaryTree;

/**
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 *
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:

       1
     / \
    2   2
  / \ / \
 3  4 4  3
 But the following [1,2,2,null,3,null,3] is not:
   1
  / \
 2   2
 \   \
 3    3
 Note:
 Bonus points if you could solve it both recursively and iteratively.
 */
public class Code_101_SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        if(root==null){
            return true;
        }
        return isSymmetric(root.left,root.right);
    }

    //判断两棵树是否对称
    private boolean isSymmetric(TreeNode p,TreeNode q){
        if(p==null && q==null){
            return true;
        }
        if(p==null && q!=null){
            return false;
        }
        if(p!=null && q==null){
            return false;
        }
        //注意p.q是对象，比较的是节点的值
        if(p.val!=q.val){
            return false;
        }

        return isSymmetric(p.left,q.right) && isSymmetric(p.right,q.left);
    }
}
