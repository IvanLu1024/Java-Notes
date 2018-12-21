package code_04_stackQueue;

import javafx.util.Pair;

import java.util.*;

/**
 *Given a binary tree, return the zigzag level order traversal of its nodes' values.
 * (ie, from left to right, then right to left for the next level and alternate between).
 *
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 3
 / \
 9  20
 /  \
 15   7
 * return its zigzag level order traversal as:
 [
 [3],
 [20,9],
 [15,7]
 ]

 */
public class Code_103_BinaryTreeZigzagLevelOrderTraversal {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ret=new ArrayList<>();
        if(root==null){
            return ret;
        }

        Queue<Pair<TreeNode,Integer>> queue=new LinkedList<>();
        queue.add(new Pair<TreeNode,Integer>(root,0));
        //root结点对应的是0层
        while(!queue.isEmpty()){
            Pair pair=queue.poll();
            TreeNode node= (TreeNode) pair.getKey();
            int level= (int) pair.getValue();

            if(level==ret.size()){
                //因为level是从0开始的，当level=ret.size()表示需要新创建 List，来存储level层的元素
                ret.add(new ArrayList<>());
            }
            //ret.get(level)表示的是level层
            ret.get(level).add(node.val);

            if(node.left!=null){
                queue.add(new Pair<TreeNode,Integer>(node.left,level+1));
            }
            if(node.right!=null){
                queue.add(new Pair<TreeNode,Integer>(node.right,level+1));
            }
        }
        //进行层次遍历后，对于偶数层的数据，进行逆序处理
        int cnt=0;
        for(List<Integer> list:ret){
            if(cnt%2==1){
                Collections.reverse(list);
            }
            cnt++;
        }
        return ret;
    }
}
