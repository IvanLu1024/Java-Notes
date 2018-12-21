package code_06_backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 797. All Paths From Source to Target
 *
 * Given a directed, acyclic graph of N nodes.
 * Find all possible paths from node 0 to node N-1, and return them in any order.
 * The graph is given as follows:
 * the nodes are 0, 1, ..., graph.length - 1.
 * graph[i] is a list of all nodes j for which the edge (i, j) exists.
 *
 * Example:
 Input: [[1,2], [3], [3], []]
 Output: [[0,1,3],[0,2,3]]
 Explanation: The graph looks like this:
 0--->1
 |    |
 v    v
 2--->3
 这里面graph是使用的二维数组表示的
 [[1,2], [3], [3], []]可以使用矩阵这样表示

 There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.

 Note:
 The number of nodes in the graph will be in the range [2, 15].
 You can print different paths in any order, but you should keep the order of nodes inside one path.
 */
public class Code_797_AllPathsFromSourcetoTarget {
    private List<List<Integer>> res;

    /**
     * 思路：
     * 分析可知解集是子集树
     */
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        res=new ArrayList<>();
        int n=graph.length;
        if(n==0){
            return res;
        }
        List<Integer> p=new ArrayList<>();
        p.add(0);
        findPath(graph,0,n-1,p);
        return res;
    }

    //p保存的是[start...end]点的路径
    private void findPath(int[][] graph,int start,int end,List<Integer> p){
        if(start==end){
            res.add(new ArrayList<>(p));
            return;
        }

        //(index,nodes[i])表示一条边
        int[] vertexs=graph[start];
        if(vertexs.length!=0){
            for(int vertex:vertexs){
                p.add(vertex);
                //查找到vertex顶点接着向下寻找
                findPath(graph,vertex,end,p);
                p.remove(p.size()-1);
            }
        }
    }

    @Test
    public void test(){
        int[][] g={{1,2},
                {3},
                {3},
                {}
        };
        System.out.println(g.length);
        System.out.println(allPathsSourceTarget(g));
    }
}
