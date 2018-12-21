package code_04_stackQueue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 637. Average of Levels in Binary Tree
 * Given a non-empty binary tree, return the average value of the nodes on each level in the form of an array.
 *
 * Given a non-empty binary tree, return the average value of the nodes on each level in the form of an array.
 * Example 1:
 Input:
 3
 / \
 9  20
 /  \
 15   7
 Output: [3, 14.5, 11]
 Explanation:
 The average value of nodes on level 0 is 3,  on level 1 is 14.5, and on level 2 is 11. Hence return [3, 14.5, 11].
 */
public class Code_637_AverageOfLevelsInBinaryTree {
    public List<Double> averageOfLevels(TreeNode root) {
        //存储层序遍历各层的元素
        List<List<Integer>> list=new ArrayList<>();
        Queue<Pair> q=new LinkedList<>();
        q.add(new Pair(root,0));
        while(!q.isEmpty()){
            Pair pair=q.poll();
            TreeNode treeNode=pair.treeNode;
            int level=pair.level;
            if(level==list.size()){
                list.add(new ArrayList<>());
            }
            list.get(level).add(treeNode.val);
            if(treeNode.left!=null){
                q.add(new Pair(treeNode.left,level+1));
            }
            if(treeNode.right!=null){
                q.add(new Pair(treeNode.right,level+1));
            }
        }

        List<Double> res=new ArrayList<>();
        if(list.size()==0){
            return res;
        }
        int sum=0;
        for(List<Integer> tmp:list){
            res.add(getAverage(tmp));
        }
        return res;
    }

   private double getAverage(List<Integer> list){
        //这里要注意 节点值为2147483647相加的情况
        long sum=0L;
        for(Integer num:list){
            sum+=num;
            //[2147483647.0,2147483647.0]
        }
        return (sum*1.0)/list.size();
   }

    private class Pair{
        TreeNode treeNode;
        int level;
        Pair(TreeNode treeNode,int level){
            this.treeNode=treeNode;
            this.level=level;
        }
    }

    @Test
    public void test(){
       TreeNode node=new TreeNode(2147483647);
       TreeNode node2=new TreeNode(2147483647);
       TreeNode node3=new TreeNode(2147483647);
       node.left=node2;
       node.right=node3;
        System.out.println(averageOfLevels(node));
    }
}
