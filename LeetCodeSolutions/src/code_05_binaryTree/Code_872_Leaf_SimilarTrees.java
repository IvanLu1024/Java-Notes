package code_05_binaryTree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 872. Leaf-Similar Trees
 *
 * Consider all the leaves of a binary tree.
 * From left to right order, the values of those leaves form a leaf value sequence.
 *
 * Note:
 * Both of the given trees will have between 1 and 100 nodes.
 */
public class Code_872_Leaf_SimilarTrees {
    /**
     * 思路：直接求出叶子节点，放入指定集合中即可，然后进行比较
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> res1=new ArrayList<>();
        List<Integer> res2=new ArrayList<>();
        getLeaves(root1,res1);
        getLeaves(root2,res2);
        if(res1.equals(res2)){
            return true;
        }
        return false;
    }

    private void getLeaves(TreeNode root,List<Integer> res){
        if(root==null){
            return;
        }
        if(root.left==null && root.right==null){
            res.add(root.val);
        }
        getLeaves(root.left,res);
        getLeaves(root.right,res);
    }

    @Test
    public void test() {
        int[] pre={1,2,3};
        int[] in={3,2,1};
        TreeNode root=TreeNodeUtils.ConstructBinaryTree(pre,in);

        int[] pre2={1,3,2};
        int[] in2={2,3,1};
        TreeNode root2=TreeNodeUtils.ConstructBinaryTree(pre2,in2);
        System.out.println(leafSimilar(root,root2));
    }
}
