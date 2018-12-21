package code_05_binaryTree;

import org.junit.Test;

import java.util.*;

/**
 * Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
 * Note: A leaf is a node with no children.
 *
 * Example:
 * Given the below binary tree and sum = 22,
          5
        / \
      4   8
     /   / \
   11  13  4
  /  \    / \
 7    2  5   1

 * Return:
 [
 [5,4,11,2],
 [5,8,4,5]
 ]
 */
public class Code_113_PathSumII {
    private List<List<Integer>> res;

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        res=new ArrayList<>();
        if(root==null){
            return res;
        }
        List<Integer> p=new ArrayList<>();
        dfs(root,0,sum,p);
        return res;
    }

    private void dfs(TreeNode node,int tSum, int sum,List<Integer> p){
        if(node==null){
            return;
        }
        p.add(node.val);
        tSum+=node.val;

        //到达叶子节点并且路径值和为sum,这说明找到了一条路径，将值存入
        if(node.left==null && node.right==null){
            if(tSum==sum){
                res.add(new ArrayList<>(p));
            }
        }else{
            if(node.left!=null){
                dfs(node.left,tSum,sum,p);
            }
            if(node.right!=null){
                dfs(node.right,tSum,sum,p);
            }
        }
        p.remove(p.size()-1);
        tSum-=node.val;
        return;
    }

    //找从根节点到叶子节点的路径和为sum的路径
    private List<String> findPath(TreeNode root,int sum){
        List<String> paths = new ArrayList<>();
        if(root == null){
            return paths;
        }
        if(root.left == null && root.right == null){
            if(root.val==sum){
                paths.add(root.val+"");
                return paths;
            }
        }
        List<String> leftPath=findPath(root.left,sum-root.val);
        for (String path : leftPath) {
            paths.add(root.val + "->" + path);
        }
        List<String> rightPath=findPath(root.right,sum-root.val);
        for (String path : rightPath) {
            paths.add(root.val + "->" + path);
        }
        return paths;
    }

    @Test
    public void test(){
        int[] pre={5,4,11,7,2,8,13,4,5,1};
        int[] in={7,11,2,4,5,13,8,5,4,1};
        TreeNode root=TreeNodeUtils.ConstructBinaryTree(pre,in);
        List<String> ret=findPath(root,22);
        System.out.println(ret);
        System.out.println(pathSum(root,22));
    }
}
