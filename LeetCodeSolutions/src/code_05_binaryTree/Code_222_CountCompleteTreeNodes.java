package code_05_binaryTree;

/**
 * 222. Count Complete Tree Nodes
 *
 * Given a complete binary tree, count the number of nodes.
 *
 * Note:
 * Definition of a complete binary tree from Wikipedia:
 * In a complete binary tree every level, except possibly the last, is completely filled, and all nodes in the last level are as far left as possible.
 * It can have between 1 and 2^h nodes inclusive at the last level h.
 */
public class Code_222_CountCompleteTreeNodes {
    /**
     * 思路：
     * 如果用常规的解法一个个遍历，就是O(n)时间复杂度 ，会不通过。
     * 因为是完全二叉树，满二叉树有一个性质是节点数等于2^h-1,h为高度，
     * 所以可以这样判断节点的左右高度是不是一样，如果是一样说明是满二叉树，
     */
    public int countNodes(TreeNode root) {
        //空树
        if(root==null){
            return 0;
        }
        //只有一个节点
        if(root.left==null && root.right==null){
            return 1;
        }
        int l=getLeftHeight(root);
        int r=getRightHeight(root);
        if(l==r){
            return (1<<r)-1;
            //(1<<r)表示的是 2^r ，也就是有l层满二叉树中节点的数目是 （2^r-1）
        }else{
            return countNodes(root.left)+countNodes(root.right)+1;
        }
    }

    private int getLeftHeight(TreeNode root){
        int height=0;
        while(root!=null){
            height++;
            root=root.left;
        }
        return height;
    }

    private int getRightHeight(TreeNode root){
        int height=0;
        while(root!=null){
            height++;
            root=root.right;
        }
        return height;
    }
}
