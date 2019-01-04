package code_05_binaryTree;

/**
 * Created by 18351 on 2019/1/4.
 */
public class Code_106_ConstructBinaryTreefromInorderAndPostorderTraversal {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return buildTree(inorder,postorder,0,inorder.length-1,0,postorder.length-1);
    }

    private TreeNode buildTree(int[] inorder,int[] postorder,int inStart,int inEnd,int postStart,int postEnd){
        if(inStart>inEnd || postStart>postEnd){
            return null;
        }
        TreeNode root=new TreeNode(postorder[postEnd]);
        int mid=0;
        for(int i=inStart;i<=inEnd;i++){
            if(inorder[i]==postorder[postEnd]){
                mid=i;
                break;
            }
        }
        root.left=buildTree(inorder,postorder,inStart,mid-1,postStart,postStart+(mid-inStart)-1);
        root.right=buildTree(inorder,postorder,mid+1,inEnd,postStart+(mid-inStart),postEnd-1);
        return root;
    }
}
