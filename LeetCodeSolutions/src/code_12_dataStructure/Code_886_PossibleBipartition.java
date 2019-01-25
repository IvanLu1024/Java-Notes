package code_12_dataStructure;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * 886. Possible Bipartition
 *
 * Given a set of N people (numbered 1, 2, ..., N),
 * we would like to split everyone into two groups of any size.
 *
 * Each person may dislike some other people, and they should not go into the same group.
 * Formally, if dislikes[i] = [a, b],
 * it means it is not allowed to put the people numbered a and b into the same group.
 *
 * Return true if and only if it is possible to split everyone into two groups in this way.
 *
 * Example 1:
 Input: N = 4, dislikes = [[1,2],[1,3],[2,4]]
 Output: true
 Explanation: group1 [1,4], group2 [2,3]

 * Example 2:
 Input: N = 3, dislikes = [[1,2],[1,3],[2,3]]
 Output: false

 * Example 3:
 Input: N = 5, dislikes = [[1,2],[2,3],[3,4],[4,5],[1,5]]
 Output: false
 */
public class Code_886_PossibleBipartition {
    /**
     * 思路：
     * 典型的二分图问题：
     * 定义color数组，值只能取 0,或者1 表示两种颜色
     */
    public boolean possibleBipartition(int N, int[][] dislikes) {
        HashSet<Integer>[] g = new HashSet[N];
        for(int i=0;i<N;i++){
            g[i] = new HashSet<>();
        }


        for(int[] edge : dislikes){
            //注意:从1开始的,为了后续操作方便，这里作 -1 处理
            int from = edge[0] -1;
            int to = edge[1] -1;
            //注意：是无向图
            g[from].add(to);
            g[to].add(from);
        }

        // colors 数组初始值为 -1，表示该顶点是否被访问到
        int[] colors = new int[N];
        for(int i=0;i<N;i++){
            colors[i] = -1;
        }

        for(int i=0;i< N ;i++){
            if(colors[i] == -1){
                if(! dfs(g,i,0,colors)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean dfs(HashSet<Integer>[] g,int vertex,int color,int[] colors){
        if(colors[vertex] != -1){
            //vertex 顶点不是 -1 颜色，说明访问了该顶点，看看是否是 color
            return colors[vertex] == color;
        }
        colors[vertex] = color;
        for(int next : g[vertex]){
            //与 vertex 顶点相连的 next 顶点，不能和 vertex顶点颜色相同
            if(!dfs(g,next,1-color,colors)){
                return false;
            }
        }
        return true;
    }

    @Test
    public void test() {
        int N = 4;
        int[][] dislikes = {
                {1, 2},
                {1, 3},
                {2, 4}
        };
        System.out.println(possibleBipartition(N,dislikes));
    }
}
