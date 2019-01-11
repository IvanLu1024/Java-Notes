package code_05_binaryTree;

import javafx.util.Pair;

import java.util.*;

/**
 * 863. All Nodes Distance K in Binary Tree
 *
 * We are given a binary tree (with root node root),
 * a target node, and an integer value K.
 * Return a list of the values of all nodes that have a distance K from the target node.
 * The answer can be returned in any order.
 *
 * Note:
 The given tree is non-empty.
 Each node in the tree has unique values 0 <= node.val <= 500.
 The target node is a node in the tree.
 0 <= K <= 1000.
 */
public class Code_863_AllNodesDistanceKInBinaryTree {
    /**
     * 解题思路：先从根dfs一遍，将树变成图。之后从要求的点bfs即可。
     */
    //使用集合数组存储图
    private Vector<Integer>[] G;

    //用于判断图中某一个结点，是否被访问
    private boolean[] isVisited;

    //通过dfs操作将该二叉树，转化为一个无向图
    private void dfs(TreeNode root){
        if(root==null){
            return;
        }
        int v=root.val;
        if(root.left!=null){
            int w=root.left.val;
            G[v].add(w);
            G[w].add(v);
            dfs(root.left);
        }
        if(root.right!=null){
            int w=root.right.val;
            G[v].add(w);
            G[w].add(v);
            dfs(root.right);
        }
    }

    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> res=new ArrayList<>();
        if(root==null){
            return res;
        }
        if(K==0){
            res.add(target.val);
            return res;
        }
        G=(Vector<Integer>[])new Vector[1005];
        for(int i=0;i<1005;i++){
            G[i]=new Vector<>();
        }
        isVisited=new boolean[1005];
        //将二叉树转化为获取图
        dfs(root);

        //从target开始对图进行广度优先遍历
        //Pair<Integer,Integer>存的是<顶点，从target到该顶点的距离>
        Queue<Pair<Integer,Integer>> queue=new LinkedList<>();
        queue.add(new Pair<>(target.val,0));
        isVisited[target.val]=true;
        while(!queue.isEmpty()){
            Pair<Integer,Integer> pair=queue.poll();
            int v=pair.getKey();
            int distance=pair.getValue();
            if(distance==K){
                res.add(v);
                continue;
            }
            //遍历与v相邻的顶点
            for(Integer w:G[v]){
                if(!isVisited[w]){
                    queue.add(new Pair<>(w,distance+1));
                    isVisited[w]=true;
                }
            }
        }
        return res;
    }
}
