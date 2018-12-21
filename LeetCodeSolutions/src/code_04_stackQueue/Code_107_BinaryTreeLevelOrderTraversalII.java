package code_04_stackQueue;

import javafx.util.Pair;

import java.util.*;

/**
 * 107. Binary Tree Level Order Traversal II
 *
 * Given a binary tree, return the bottom-up level order traversal of its nodes' values.
 * (ie, from left to right, level by level from leaf to root).

 * For example:
 Given binary tree [3,9,20,null,null,15,7],
 3
 / \
 9  20
 /  \
 15   7
 return its bottom-up level order traversal as:
 [
 [15,7],
 [9,20],
 [3]
 ]
 */
public class Code_107_BinaryTreeLevelOrderTraversalII {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> ret=new ArrayList<>();
        if(root==null){
            return new ArrayList<>();
        }
        Queue<Pair<TreeNode,Integer>> queue=new LinkedList<>();
        queue.add(new Pair<TreeNode,Integer>(root,0));
        while(!queue.isEmpty()){
            Pair pair=queue.poll();
            TreeNode node=(TreeNode) pair.getKey();
            int level=(int)pair.getValue();
            if(level==ret.size()){
                ret.add(new ArrayList<>());
            }
            ret.get(level).add(node.val);

            if(node.left!=null){
                queue.add(new Pair<TreeNode,Integer>(node.left,level+1));
            }
            if(node.right!=null){
                queue.add(new Pair<TreeNode,Integer>(node.right,level+1));
            }
        }
        Collections.reverse(ret);

        return ret;
    }
}
