package code_07_dp;

import code_04_stackQueue.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 96. Unique Binary Search Trees
 *
 * Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
 */
public class Code_96_UniqueBinarySearchTrees {
    /**
     * 思路：
     * 这棵树的不同形态的二叉查找树的个数，就是根节点的左子树的个数 *  右子树的个数，
     * 想想还是很容易理解的，就是左边的所有情况乘以右边的所有情况，
     */
    public int numTrees(int n) {
        int[] dp=new int[n+1];

        //n=0表示null,(null树也是二分树)只有1种情况
        dp[0]=1;
        //n=1表示只有一个节点，只有一种情况
        dp[1]=1;
        for(int i=2;i<=n;i++){ //i 表示的是BST树的节点个数
            for(int j=1;j<=i;j++){
                // dp[j-1] 表示左子树中不同形态的二叉搜索树的个数
                // dp[i-j]  表示右子树中不同形态的二叉搜索树的个数
                dp[i] += dp[j-1] * dp[i-j];
            }
        }
        return dp[n];
    }
}
