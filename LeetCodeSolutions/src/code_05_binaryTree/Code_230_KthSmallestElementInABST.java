package code_05_binaryTree;

import org.junit.Test;

/**
 * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.

 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ BST's total elements.
 *
 * Example 1:
 *
 * Input: root = [3,1,4,null,2], k = 1
      3
    / \
   1   4
   \
   2
  Output: 1
 *
 */
public class Code_230_KthSmallestElementInABST {
    private int index=0;
    private TreeNode tmp;

    //k是从1开始的
    //k始终有效
    public int kthSmallest(TreeNode root, int k) {
        inOrder(root,k);
        return tmp.val;
    }

    //利用BST树中序遍历的性质
    public void inOrder(TreeNode root,int k){
       if(root==null){
           return;
       }
       inOrder(root.left,k);
       index++;
       if(index==k){
           tmp=root;
           return;
       }
       inOrder(root.right,k);
    }

    @Test
    public void test(){
        int[] pre={3,1,2,4};
        int[] in={1,2,3,4};
        TreeNode root=TreeNodeUtils.ConstructBinaryTree(pre,in);
        System.out.println(kthSmallest(root,1));
    }
}
