package code_05_binaryTree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 783. Minimum Distance Between BST Nodes
 *
 * Given a Binary Search Tree (BST) with the root node root,
 * return the minimum difference between the values of any two different nodes in the tree.
 *
 * Example :
 * Input: root = [4,2,6,1,3,null,null]
 * Output: 1
 * Explanation:
 * Note that root is a TreeNode object, not an array.
 * The given tree [4,2,6,1,3,null,null] is represented by the following diagram:
         4
      /   \
    2      6
  / \
 1   3
 * while the minimum difference in this tree is 1, it occurs between node 1 and node 2, also between node 3 and node 2.
 * Note:
 * The size of the BST will be between 2 and 100.
 * The BST is always valid, each node's value is an integer, and each node's value is different.
 */
public class Code_783_MinimumDistanceBetweenBSTNodes {
    /**
     * 思路：
     * 利用BST的中序遍历的特性，
     * 计算相邻元素的差值
     */
    public int minDiffInBST(TreeNode root) {
        inOrder(root);
        int min=Integer.MAX_VALUE;
        for(int i=0;i<inOrder.size()-1;i++){
            Integer num1=inOrder.get(i);
            Integer num2=inOrder.get(i+1);
            min=Math.min(min,Math.abs(num1-num2));
        }
        return min;
    }

    private List<Integer> inOrder=new ArrayList<>();
    private void inOrder(TreeNode root){
        if(root==null){
            return;
        }
        inOrder(root.left);
        inOrder.add(root.val);
        inOrder(root.right);
    }

    @Test
    public void test(){
        int[] pre={4,2,1,3,6};
        int[] in={1,2,3,4,6};
        TreeNode treeNode=TreeNodeUtils.ConstructBinaryTree(pre,in);
        System.out.println(minDiffInBST(treeNode));
    }
}
