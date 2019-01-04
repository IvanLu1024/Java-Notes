package code_05_binaryTree;

/**
 * 897. Increasing Order Search Tree
 *
 * Given a tree,
 * rearrange the tree in in-order so that the leftmost node in the tree is now the root of the tree,
 * and every node has no left child and only 1 right child.
 *
 * Note:
 The number of nodes in the given tree will be between 1 and 100.
 Each node will have a unique integer value from 0 to 1000.
 */
public class Code_897_IncreasingOrderSearchTree {
    /**
     * 思路一：
     */
    private int[] inorder=new int[200];
    private int index;

    public TreeNode increasingBST1(TreeNode root) {
        if(root==null){
            return null;
        }
        inOrder(root);
        return buildBST(inorder,0,index-1);
    }

    private TreeNode buildBST(int[] arr,int start,int end){
        if(arr==null || arr.length==0 || start>end){
            return null;
        }
        TreeNode root=new TreeNode(arr[start]);
        root.left=null;
        root.right=buildBST(arr,start+1,end);
        return root;
    }

    private void inOrder(TreeNode root){
        if(root==null){
            return;
        }
        inOrder(root.left);
        inorder[index++]=root.val;
        inOrder(root.right);
    }

    private TreeNode cur;

    public TreeNode increasingBST(TreeNode root) {
        if(root==null){
            return null;
        }
        TreeNode dummyNode=new TreeNode(-1);
        cur=dummyNode;
        inorder(root);
        return dummyNode.right;
    }

    private void inorder(TreeNode root){
        if(root==null){
            return;
        }
        inorder(root.left);

        cur.right=root;
        cur=root;
        cur.left=null;

        inorder(root.right);
    }
}
