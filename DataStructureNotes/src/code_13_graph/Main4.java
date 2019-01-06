package code_13_graph;

/**
 * Created by 18351 on 2019/1/6.
 */
public class Main4 {
    public static void main(String[] args) {
        SparseGraph g2=new SparseGraph(6,false);
        ReadGraph.readGraph(g2,"testG2.txt");

        DepthFirstPaths dfp=new DepthFirstPaths(g2,0);
        dfp.showPath(5);

        BreadthFirstPaths bfp=new BreadthFirstPaths(g2,0);
        bfp.showPath(3);
    }
}
