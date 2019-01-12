package code_05_binaryTree;

import javafx.util.Pair;

/**
 * 865. Smallest Subtree with all the Deepest Nodes
 *
 * Given a binary tree rooted at root,
 * the depth of each node is the shortest distance to the root.
 * A node is deepest if it has the largest depth possible among any node in the entire tree.
 * The subtree of a node is that node, plus the set of all descendants(后代) of that node.
 * Return the node with the largest depth such that it contains all the deepest nodes in its subtree.
 *
 * Example 1:
 * Input: [3,5,1,6,2,0,8,null,null,7,4]
 * Output: [2,7,4]
 */
public class Code_865_SmallestSubtreeWithAllTheDeepestNodes {
    /**
     * 思路：
     * 这个题的模型其实比较左右子树的高度，如果左右子树的高度相等，说明当前节点就是要求的。
     * 这个解释是这样的：必须包含所有的最大高度的叶子，左右叶子高度相等，所以必须包含当前节点。
     *
     * 当左子树高度>右子树高度的时候，要求的节点在左边；反之，在右边。
     * 所以，递归思路 + 一个pair。这个pair的思路是，保存了当前节点的深度和当前节点的最深子树节点。
     * pair就是< 当前节点的深度，当前节点的最深子树节点>
     */
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        if(root==null){
            return null;
        }
        return getNode(root).getValue();
    }

    private Pair<Integer,TreeNode> getNode(TreeNode root){
        if(root==null){
            return new Pair<>(-1,null);
        }
        Pair<Integer,TreeNode> l=getNode(root.left);
        Pair<Integer,TreeNode> r=getNode(root.right);
        int leftChildDepth=l.getKey();
        int rightChildDepth=r.getKey();
        int depth=Math.max(leftChildDepth,rightChildDepth)+1;
        /**
         * 如果左右子树的高度相等，说明当前节点就是要求的。
         * 这个解释是这样的：必须包含所有的最大高度的叶子，左右叶子高度相等，所以必须包含当前节点。
         */
        TreeNode node= null;
        if(leftChildDepth==rightChildDepth){
            node = root;
        }else if(leftChildDepth>rightChildDepth){
             //当左子树高度>右子树高度的时候，要求的节点在左边；反之，在右边。
            node = l.getValue();
        }else if(leftChildDepth<rightChildDepth){
            node = r.getValue();
        }
        return new Pair<>(depth,node);
    }
}
