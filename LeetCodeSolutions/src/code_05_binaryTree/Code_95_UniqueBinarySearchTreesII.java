package code_05_binaryTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an integer n, generate all structurally
 * unique BST's (binary search trees) that store values 1 ... n.
 */
public class Code_95_UniqueBinarySearchTreesII {
    /**
     * 使用回溯法：穷举
     */
    public List<TreeNode> generateTrees(int n) {
        if(n<=0){
            return new ArrayList<>();
        }
       return genenateTree(1,n);
    }

    //[start,end]的数据产生二叉树
    private List<TreeNode> genenateTree(int start,int end){
        List<TreeNode> res=new ArrayList<>();
        if(start>end){
            res.add(null);
            return res;
        }

        for(int i=start;i<=end;i++){
            List<TreeNode> left=genenateTree(start,i-1);
            List<TreeNode> right=genenateTree(i+1,end);
            for(TreeNode leftNode:left){
                for(TreeNode rightNode:right){
                    TreeNode root=new TreeNode(i);
                    root.left=leftNode;
                    root.right=rightNode;
                    res.add(root);
                }
            }
        }
        return res;
    }


    //向以node为根节点的BST树种插入元素e
    //返回插入新元素后该BST的根
    private TreeNode add(TreeNode node,int e){
        if(node==null){
            return new TreeNode(e);
        }
        if(e<node.val){
            node.left=add(node.left,e);
        }else if(e>node.val){
            node.right=add(node.right,e);
        }
        return node;
    }
}
