package code_12_dataStructure;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 210. Course Schedule II
 *
 * There are a total of n courses you have to take, labeled from 0 to n-1.
 * Some courses may have prerequisites,
 * for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
 *
 * Given the total number of courses and a list of prerequisite pairs,
 * return the ordering of courses you should take to finish all courses.
 * There may be multiple correct orders, you just need to return one of them.
 * If it is impossible to finish all courses, return an empty array.
 *
 *
 * Example 1:
 Input: 2, [[1,0]]
 Output: [0,1]
 Explanation: There are a total of 2 courses to take. To take course 1 you should have finished
 course 0. So the correct course order is [0,1] .

 * Example 2:
 Input: 4, [[1,0],[2,0],[3,1],[3,2]]
 Output: [0,1,2,3] or [0,2,1,3]
 Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both
 courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
 So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3]
 */
public class Code_210_CourseScheduleII {
    /**
     * 思路：
     * 使用 DFS 来实现拓扑排序，
     * 使用一个栈存储后序遍历结果，这个栈的逆序结果就是拓扑排序结果。
     * 注意有向图中存在环，则不存在拓扑排序
     */
    //保存拓扑排序的逆序
    private Stack<Integer> stack;

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        ArrayList<Integer> [] g = new ArrayList[numCourses];
        for(int i=0;i<numCourses;i++){
            g[i] = new ArrayList<>();
        }
        for(int[] edge : prerequisites){
            int from = edge[1];
            int to = edge[0];
            g[from].add(to);
        }
        //有向图中存在环，则不存在拓扑排序
        if(hasCycle(g)){
            return new int[]{};
        }
        //拓扑排序就是其中的一个可行解
        return topoOrder(g);
    }

    //判断图是否存在环
    private boolean hasCycle(ArrayList<Integer> [] g){
        boolean[] visited = new boolean[g.length];
        boolean[] onPath = new boolean[g.length];
        for(int i =0 ; i< g.length ;i++){
            if(!visited[i]){
                if(dfs(g,i,visited,onPath)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(ArrayList<Integer> [] g,int v,boolean[] visited,boolean[] onPath){
        visited[v] = true;
        onPath[v] = true;
        for(int next : g[v]){
            if(!visited[next]){
                if(dfs(g,next,visited,onPath)){
                    return true;
                }
            }else if(onPath[next]){
                return true;
            }
        }
        onPath[v] = false;
        return false;
    }

    //使用dfs进行拓扑排序
    private int[] topoOrder(ArrayList<Integer> [] g){
        boolean[] visited = new boolean[g.length];
        stack = new Stack<>();
        for(int i=0;i<g.length;i++){
            if(!visited[i]){
                dfsForTopoOrder(g,i,visited);
            }
        }
        int[] res = new int[g.length];
        int index=0;
        while(!stack.isEmpty()){
            res[index++] = stack.pop();
        }
        return res;
    }


    /**
     * 拓扑排序
     * @param g
     * @param v
     * @param visited 与上面的visted数组不是同一个
     */
    private void dfsForTopoOrder(ArrayList<Integer> [] g,int v,boolean[] visited){
        visited[v] = true;
        for(int next : g[v]){
            if(! visited[next]){
                dfsForTopoOrder(g,next,visited);
            }
        }
        stack.push(v);
    }

    @Test
    public void test(){
        int[][] q = {
                {1,0},
                {2,0},
                {3,1},
                {3,2},
        };
        int[] res= findOrder(4,q);
        for(int i : res){
            System.out.println(i);
        }
    }
}
