package code_12_dataStructure;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 207. Course Schedule
 *
 * There are a total of n courses you have to take, labeled from 0 to n-1.
 * Some courses may have prerequisites,
 * for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
 * Given the total number of courses and a list of prerequisite pairs,
 * is it possible for you to finish all courses?
 *
 * Example 1:

 Input: 2, [[1,0]]
 Output: true
 Explanation: There are a total of 2 courses to take.
 To take course 1 you should have finished course 0. So it is possible.

 * Example 2:
 Input: 2, [[1,0],[0,1]]
 Output: false
 Explanation: There are a total of 2 courses to take.
 To take course 1 you should have finished course 0, and to take course 0 you should
 also have finished course 1. So it is impossible.
 */
public class Code_207_CourseSchedule {
    /**
     * 思路：
     * 一个课程可能会先修课程，判断给定的先修课程规定是否合法。
     * 不需要使用拓扑排序，只需要检测有向图是否存在环即可。
     * 有环则返回false
     */
    private boolean[] visited;
    private boolean[] onPath;
    ArrayList<Integer>[]  g;

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //使用邻接表表示图，这是应对边比较多的情况
        g = new ArrayList[numCourses];
        for(int i=0;i<g.length;i++){
            g[i] = new ArrayList();
        }

        for(int[] arr : prerequisites){
            //注意图中各边的起点和终点
            int from = arr[1];
            int to = arr[0];
            g[from].add(to);
        }

        return ! hasCycle(g);
    }

    /**
     * 判断 g 表示的有向图是否有环
     */
    private boolean hasCycle(ArrayList<Integer>[] g){
        visited = new boolean[g.length];
        onPath = new boolean[g.length];
        for(int i=0;i<g.length;i++){
            if(!visited[i]){
                if(dfs(g,i,visited,onPath)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 对图进行深度遍历，判断是否存在有向环
     * @param g 该有向图
     * @param v 深度遍历到的顶点
     * @param visited 记录节点访问情况
     * @param onPath 记录有向环的顶点
     */
    private boolean dfs(ArrayList<Integer>[] g, int v, boolean[] visited, boolean[] onPath) {
        visited[v] = true;
        onPath[v] = true;
        for(int next : g[v]){
            if(!visited[next]){
                if(dfs(g,next,visited,onPath)){
                    return true;
                }
            }else if(onPath[next]){
                // next 顶点是有向环中的顶点
                return true;
            }
        }
        onPath[v] = false;
        return false;
    }

    @Test
    public void test(){
       /* int[][] q= {
                {1,0},
                {0,1}
        };*/
       /*int[][] q = {
               {1,0}
       };*/
        int[][] q = {
                {1,0},
                {2,1},
                {0,2},
                {2,3},
        };
        System.out.println(canFinish(4,q));

        //for test 测试 onPath上的顶点，也就是环上的顶点
        for(int i=0;i<onPath.length;i++){
            if(onPath[i]==true){
                System.out.println(i);
            }
        }
    }
}
