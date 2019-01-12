package code_05_binaryTree;

import java.util.*;

/**
 * 894. All Possible Full Binary Trees
 *
 * A full binary tree is a binary tree where each node has exactly 0 or 2 children.
 * Return a list of all possible full binary trees with N nodes.
 * Each element of the answer is the root node of one possible tree.
 * Each node of each tree in the answer must have node.val = 0.
 *
 * Note:
 * 1 <= N <= 20
 */
public class Code_894_AllPossibleFullBinaryTrees {
    /**
     * 思路：
     * 现在考虑有个根节点，它的所有满二叉树组合为allPossibleFBT(N)，
     * 左子树可能有的节点数目为1，2，…，i，…，N-1，
     * 同时右子树可能有的节点数目为N-1-1，N-2-1，…，N-i-1，…，1。
     * 那么我们可以分别得到左右子树的所有满二叉树的组合：
     * allPossibleFBT（i）和allPossibleFBT（N-i-1）。
     */
    public List<TreeNode> allPossibleFBT1(int N) {
        List<TreeNode> res=new ArrayList<>();
        if(N==0){
            return res;
        }
        if(N==1){
            res.add(new TreeNode(0));
            return res;
        }
        //一个结点只允许两种情况:无节点或者两个节点
        for(int i=1;i<=N-1;i+=2){
            List<TreeNode> leftChild=allPossibleFBT1(i);
            List<TreeNode> rightChild=allPossibleFBT1(N-1-i);
            for(TreeNode leftNode:leftChild){
                for(TreeNode rightNode:rightChild){
                    TreeNode root=new TreeNode(0);
                    root.left=leftNode;
                    root.right=rightNode;
                    res.add(root);
                }
            }
        }
        return res;
    }

    /**
     * 使用记忆化搜索方式进行改进
     */
    public List<TreeNode> allPossibleFBT(int N) {
        if (N % 2 == 0) {
            return  new ArrayList<>();
        }
        //记录<节点数，以该结点数所构造的二叉树的所有可能>
        List<TreeNode>[] memo=new ArrayList[N+1];
        for(int i=0;i<memo.length;i++){
            memo[i]=new ArrayList<>();
        }
        return allPossibleFBT(N,memo);
    }

    private List<TreeNode> allPossibleFBT(int N,List<TreeNode>[] memo){
        if(memo[N].size()!=0){
            return memo[N];
        }
        if(N==1){
            memo[1]=new ArrayList<>(Arrays.asList(new TreeNode(0)));
            return memo[1];
        }
        for(int i=1;i<N;i+=2){
            List<TreeNode> leftChild=allPossibleFBT(i);
            List<TreeNode> rightChild=allPossibleFBT(N-1-i);
            for(TreeNode leftNode:leftChild){
                for(TreeNode rightNode:rightChild){
                    TreeNode root=new TreeNode(0);
                    root.left=leftNode;
                    root.right=rightNode;
                    memo[N].add(root);
                }
            }
        }
        return memo[N];
    }
}
