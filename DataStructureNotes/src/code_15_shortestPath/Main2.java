package code_15_shortestPath;

/**
 * Created by 18351 on 2019/1/8.
 */
public class Main2 {
    public static void main(String[] args) {
        //String filename = "testG_negative_circle.txt";
        String filename = "testG9.txt";
        int V = 5;

        SparseWeightedGraph<Integer> g = new SparseWeightedGraph<>(V, true);

        ReadWeightedGraph readWeightedGraph=new ReadWeightedGraph(g,filename);

        System.out.println("Test Bellman-Ford:\n");
        BellmanFordSP<Integer> bf = new  BellmanFordSP<Integer>(g,0);
        if(bf.negativeCycle()){
            System.out.println("The graph has negative cycle");
        }else{
            for( int i = 1 ; i < V ; i ++ ){
                if(bf.hasPathTo(i)) {
                    System.out.println("Shortest Path to " + i + " : " + bf.shortestPathTo(i));
                    bf.showPath(i);
                } else{
                    System.out.println("No Path to " + i );
                }
                System.out.println("----------");
            }
        }
    }
}
