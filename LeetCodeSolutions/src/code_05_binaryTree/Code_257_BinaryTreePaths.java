package code_05_binaryTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 257. Binary Tree Paths
 *
 * Given a binary tree, return all root-to-leaf paths.
 * Note: A leaf is a node with no children.
 *
 * Example:
 * Input:

    1
 /   \
 2     3
 \
 5

 * Output: ["1->2->5", "1->3"]
 * Explanation: All root-to-leaf paths are: 1->2->5, 1->3
 */
public class Code_257_BinaryTreePaths {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> ret=new ArrayList<>();
        if(root==null){
            return ret;
        }
        if(root.left==null && root.right==null){
            ret.add(root.val+"");
            return ret;
        }
        List<String> leftS=binaryTreePaths(root.left);
        for(String s:leftS){
            ret.add(root.val+"->"+s);
        }
        List<String> rightS=binaryTreePaths(root.right);
        for(String s:rightS){
            ret.add(root.val+"->"+s);
        }
        return ret;
    }
}
