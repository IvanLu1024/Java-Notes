package code_05_binaryTree;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;

/**
 * 889. Construct Binary Tree from Preorder and Postorder Traversal
 *
 * Return any binary tree that matches the given preorder and postorder traversals.
 * Values in the traversals pre and post are distinct positive integers.
 */
public class Code_889_ConstructBinaryTreeFromPreorderAndPostorderTraversal {
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        return constructFromPrePost(pre,post,0,pre.length-1,0,post.length-1);
    }

    private TreeNode constructFromPrePost(int[] pre, int[] post,int preStart,int preEnd,int postStart,int postEnd){
        if(preStart>preEnd || postStart>postEnd){
            return null;
        }
        TreeNode root=new TreeNode(pre[preStart]);
        if(preStart==preEnd){
            return root;
        }

        //左子树根结点
        int k;
        for(k=postStart;k<postEnd;k++){
            if(post[k]==pre[preStart+1]){
                break;
            }
        }
        root.left=constructFromPrePost(pre,post,preStart+1,preStart+k-postStart+1,postStart,k);
        root.right=constructFromPrePost(pre,post,preStart+k-postStart+2,preEnd,k+1,postEnd-1);
        return root;
    }

    @Test
    public void test(){
        int[] pre={1,2,4,5,3,6,7};
        int[] post={4,5,2,6,7,3,1};
        TreeNode root=constructFromPrePost(pre,post);
        TreeNodeUtils.postorderTraversal(root);
    }
}
