package code_05_binaryTree;

/**
 * Invert a binary tree.

 Example:

 Input:

         4
      /   \
     2     7
   / \   / \
 1   3 6   9
 Output:

       4
     /   \
    7     2
   / \   / \
 9   6 3   1
 */
public class Code_226_InvertBinaryTree {
    public TreeNode invertTree(TreeNode root) {
        if(root==null){
            return null;
        }
        invertTree(root.left);
        invertTree(root.right);

        //交换root的左右节点
        TreeNode tmp=root.left;
        root.left=root.right;
        root.right=tmp;
        return root;
    }

    private void swap(TreeNode node1,TreeNode node2){
        TreeNode tmp=node1;
        node1=node2;
        node2=tmp;
    }
}
