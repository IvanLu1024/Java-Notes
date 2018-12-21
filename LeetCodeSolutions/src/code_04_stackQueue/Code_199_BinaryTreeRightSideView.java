package code_04_stackQueue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 199. Binary Tree Right Side View
 *
 * Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.
 *
 * Example1:
 * Input: [1,2,3,null,5,null,4]
 * Output: [1, 3, 4]
 * Explanation:

    1            <---
  /   \
 2     3         <---
 \     \
 5     4       <---

 * Example1:
 * Input: [1,2,3,4]
 * Output: [1, 3, 4]
 * Explanation:
      1        <---
    / \
   2   3       <---
  /
 4             <--
 */
public class Code_199_BinaryTreeRightSideView {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ret=new ArrayList<>();
        if(root==null){
            return ret;
        }
        Queue<TreeNode> queue=new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()){
            //nodeNumsEachLevel表示每层的节点数
            int nodeNumsEachLevel=queue.size();
            while(nodeNumsEachLevel>0){
                TreeNode tmp=queue.poll();
                if(tmp.left!=null){
                    queue.add(tmp.left);
                }
                if(tmp.right!=null){
                    queue.add(tmp.right);
                }
                //每出队一次，该层就减少一个节点
                nodeNumsEachLevel--;
                //nodeNumsEachLevel==0，该层的最右侧节点
                if(nodeNumsEachLevel==0){
                    ret.add(tmp.val);
                }
            }
        }
        return ret;
    }
}
