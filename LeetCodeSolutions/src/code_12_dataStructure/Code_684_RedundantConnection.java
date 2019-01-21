package code_12_dataStructure;

/**
 * 684. Redundant Connection
 *
 * In this problem, a tree is an undirected graph that is connected and has no cycles.
 * The given input is a graph that started as a tree with N nodes
 * (with distinct values 1, 2, ..., N), with one additional edge added.
 * The added edge has two different vertices(顶点) chosen from 1 to N,
 * and was not an edge that already existed.
 *
 * The resulting graph is given as a 2D-array of edges.
 * Each element of edges is a pair [u, v] with u < v,
 * that represents an undirected edge connecting nodes u and v.
 *
 * Return an edge that can be removed so that the resulting graph is a tree of N nodes.
 * If there are multiple answers,
 * return the answer that occurs last in the given 2D-array.
 * The answer edge [u, v] should be in the same format, with u < v.
 *
 * Example 1:
 Input: [[1,2], [1,3], [2,3]]
 Output: [2,3]
 Explanation: The given undirected graph will be like this:
    1
   / \
  2 - 3

 * Example 2:
 Input: [[1,2], [2,3], [3,4], [1,4], [1,5]]
 Output: [1,4]
 Explanation: The given undirected graph will be like this:
 5 - 1 - 2
     |   |
     4 - 3

 * Note:
 The size of the input 2D-array will be between 3 and 1000.
 Every integer represented in the 2D-array will be between 1 and N,
 where N is the size of the input array.
 */
public class Code_684_RedundantConnection {
    /**
     * 思路：
     * 判断无向图是否有环可以使用并查集来判断。
     * 新加入的边若两个点的根节点相同，则形成环，
     * 这是因为这条边中一定要有一个结点是新加入集合的。
     */
    public int[] findRedundantConnection(int[][] edges) {
        //edges的size在[3,1000]之间
        int N = edges.length;

        //顶点的编号在 [1,N]之间
        int[] parent = new int[N+1];
        //初始化时，每个点都是一棵树，就看成森林
        for(int i=1;i<=N;i++){
            parent[i]=i;
        }

        //遍历每条边的顶点
        for(int i=0;i<N;i++){
            int p1=find(parent,edges[i][0]);
            int p2=find(parent,edges[i][1]);
            //这两个顶点的是同一个父结点，说明构成了环。
            if(p1==p2){
                return edges[i];
            }
            //既然是图，那么各个顶点所在树的根结点必然相连的
            parent[p1] = p2;
        }
        return new int[]{0,0};
    }

    //查找p元素所在的树（也就是集合）的根节点
    //时间复杂度：O(h),其中h为该集合树的深度
    private int find(int[] parent,int p){
        if(p<0 || p>=parent.length){
            throw new IllegalArgumentException("p is out of bound.");
        }
        while(p!=parent[p]){
            p=parent[p];
        }
        //返回的是p所在的集合的编号，同时也是p集合非根节点
        return p;
    }
}
