package code_05_binaryTree;

import org.junit.Test;

/**
 * 450. Delete Node in a BST
 *
 * Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.
 * Basically, the deletion can be divided into two stages:
 *
 * Search for a node to remove.
 * If the node is found, delete the node.
 *
 * Note: Time complexity should be O(height of tree).
 */
public class Code_450_DeleteNodeInABST {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null){
            return root;
        }
        //删除的是根结点
        if(root.val==key){
            //右子树为空，那么就直接删除该根节点，返回右子树
            if(root.right==null){
                TreeNode node=root.left;
                root=node;
                return root;
            }else{
                //右子树不为空，则将该右子树的最小值，代替根结点的值
                TreeNode right=root.right;
                while(right.left!=null){
                    right=right.left;
                }
                int tmp=root.val;
                root.val=right.val;
                right.val=tmp;
            }
        }
        root.left=deleteNode(root.left,key);
        root.right=deleteNode(root.right,key);
        return root;
    }

    @Test
    public void test(){
        int[] pre={5,3,2,4,6,7};
        int[] in={2,3,4,5,6,7};
        TreeNode root=TreeNodeUtils.ConstructBinaryTree(pre,in);
        System.out.println(TreeNodeUtils.inorderTraversal(root));
        root=deleteNode(root,3);
        System.out.println(TreeNodeUtils.inorderTraversal(root));
    }
}