package code_05_binaryTree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 129. Sum Root to Leaf Numbers
 *
 * Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
 * An example is the root-to-leaf path 1->2->3 which represents the number 123.
 * Find the total sum of all root-to-leaf numbers.
 *
 * Note: A leaf is a node with no children.
 *
 * Example:
 * Input: [1,2,3]
    1
   / \
  2   3
 * Output: 25
 * Explanation:
 * The root-to-leaf path 1->2 represents the number 12.
 * The root-to-leaf path 1->3 represents the number 13.
 * Therefore, sum = 12 + 13 = 25.
 */
public class Code_129_SumRootToLeafNumbers {
    public int sumNumbers(TreeNode root) {
        List<String> ret=findPath(root);
        int sum=0;
        for(String ele:ret){
            Integer i=Integer.parseInt(ele);
            sum+=i;
        }
        return sum;
    }

    //查找所有到叶子结点的路径
    private List<String> findPath(TreeNode root){
        List<String> ret=new ArrayList<>();
        if(root==null){
            return ret;
        }
        if(root.left==null && root.right==null){
            ret.add(root.val+"");
            return ret;
        }
        List<String> leftPath=findPath(root.left);
        for(String path:leftPath){
            ret.add(root.val+""+path);
        }
        List<String> rightPath=findPath(root.right);
        for(String path:rightPath){
            ret.add(root.val+""+path);
        }
        return ret;
    }

    @Test
    public void test(){
        /*int[] pre={1,2,3};
        int[] in={2,1,3};
        TreeNode root=TreeNodeUtils.ConstructBinaryTree(pre,in);
        System.out.println(findPath(root));*/
        int[] pre={4,9,5,1,0};
        int[] in={5,9,1,4,0};
        TreeNode root=TreeNodeUtils.ConstructBinaryTree(pre,in);
        System.out.println(findPath(root));
    }
}
