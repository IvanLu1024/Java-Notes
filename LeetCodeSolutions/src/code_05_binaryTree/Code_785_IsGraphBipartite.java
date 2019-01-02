package code_05_binaryTree;

/**
 * 785. Is Graph Bipartite?(二分图)
 *
 * Given an undirected graph, return true if and only if it is bipartite（双向的）.
 * Recall that a graph is bipartite if we can split it's set of nodes into two independent subsets A and B
 * such that every edge in the graph has one node in A and another node in B.

 * The graph is given in the following form:
 * graph[i] is a list of indexes j for which the edge between nodes i and j exists.
 * Each node is an integer between 0 and graph.length - 1.
 * There are no self edges or parallel edges: graph[i] does not contain i, and it doesn't contain any element twice.

 * Example 1:
 Input: [[1,3], [0,2], [1,3], [0,2]]
 Output: true
 Explanation:
 The graph looks like this:
 0----1
 |    |
 |    |
 3----2
 We can divide the vertices into two groups: {0, 2} and {1, 3}.

 * Example 2:
 Input: [[1,2,3], [0,2], [0,1,3], [0,2]]
 Output: false
 Explanation:
 The graph looks like this:
 0----1
 | \  |
 |  \ |
 3----2
 We cannot find a way to divide the set of nodes into two independent subsets.
 */
public class Code_785_IsGraphBipartite {
    /**
     * 思路：
     * 首先检查该结点是否已经被检查，如果是，则看看是否能满足要求；
     * 否则就给结点分组，并且采用DFS的策略对和其直接或者间接相连的所有结点进行分组。
     * 整个过程中如果发现冲突就提前返回false；否则在最后返回true。
     */
    private int[] checked;

    public boolean isBipartite(int[][] graph) {
        checked=new int[graph.length];
        for(int i=0;i<checked.length;i++){
            checked[i]=-1;
        }
        for(int i=0;i<graph.length;i++){
            if(checked[i]==-1){
                if(!check(graph,i,0)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param graph
     * @param index 对应的结点下标
     * @param group 分组标号，数值为0和1，group初始值为0，1-group值就变成1
     * @return
     */
    private boolean check(int[][] graph,int index,int group){
        if(checked[index]!=-1){
            //首先检查该结点是否已经被检查，如果是，则看看是否能满足要求；
            return checked[index]==group;
        }
        checked[index]=group;
        //遍历与该结点相连的其他结点
        for(int next:graph[index]){
            if(!check(graph,next,1-group)){
                return false;
            }
        }
        return true;
    }
}
