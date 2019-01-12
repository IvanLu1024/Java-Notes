package code_15_shortestPath;

/**
 * Created by 18351 on 2019/1/8.
 */
public class Main {
    public static void main(String[] args) {
        String filename = "testG8.txt";
        int V = 5;

        SparseWeightedGraph<Integer> g = new SparseWeightedGraph<>(V, true);
        // Dijkstra最短路径算法同样适用于有向图
        //SparseGraph<int> g = SparseGraph<int>(V, false);
        //ReadWeightedGraph readGraph = new ReadWeightedGraph(g,filename);
        ReadWeightedGraph readWeightedGraph=new ReadWeightedGraph(g,filename);

        System.out.println("Test Dijkstra:\n");
        DijkstraSP<Integer> dij = new DijkstraSP<Integer>(g,0);
        for( int i = 1 ; i < V ; i ++ ){
            if(dij.hasPathTo(i)) {
                System.out.println("Shortest Path to " + i + " : " + dij.shortestPathTo(i));
                dij.showPath(i);
            } else{
                System.out.println("No Path to " + i );
            }
            System.out.println("----------");
        }

    }
}
