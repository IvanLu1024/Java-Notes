package code_07_dp;

import com.southeast.code_05_binaryTree.TreeNode;

/**
 * 337. House Robber III
 *
 * The thief has found himself a new place for his thievery again. There is only one entrance to this area,
 * called the "root." Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that "all houses in this place forms a binary tree".
 * TODO:It will automatically contact the police if two directly-linked houses were broken into on the same night.
 *
 * Determine the maximum amount of money the thief can rob tonight without alerting the police.
 *
 * Example:
 * Input: [3,2,3,null,3,null,1]
      [3]
     / \
    2   3
    \   \
   [3]  [1]
 * Output: 7
 * Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
 */
public class Code_337_HouseRobberIII {
    public int rob1(TreeNode root) {
        return rob1(root,true);
    }

    private int rob1(TreeNode root,boolean include){
        if(root==null){
            return 0;
        }
        int res=rob1(root.left,true)+rob1(root.right,true);
        if(include){
            res=Math.max(res,root.val+rob1(root.left,false)+rob1(root.right,false));
        }
        return res;
    }

    public int rob(TreeNode root) {
        int[] res=tryRob(root);
        return Math.max(res[0],res[1]);
    }

    private int[] tryRob(TreeNode root){
        if(root==null){
            return new int[]{0,0};
        }
        int[] resultL=tryRob(root.left);
        int[] resultR=tryRob(root.right);
        int[] res=new int[2];
        //小标0 表示不抢劫该根节点的最大价值，小标1表示抢劫该根节点的最大价值

        //不抢劫该根节点
        res[0]=resultL[1]+resultR[1];
        res[1]=Math.max(res[0],root.val+resultL[0]+resultR[0]);
        return res;
    }
}
