package code_13_graph;

import java.util.Stack;

/**
 * Created by 18351 on 2019/1/6.
 */
public class Main2 {
    public static void main(String[] args) {
        SparseGraph g1=new SparseGraph(6,false);
        ReadGraph.readGraph(g1,"testG2.txt");
        g1.show();

        //深度优先遍历
        DepthFirstPaths dfp=new DepthFirstPaths(g1,0);
        dfp.showPath(5);
    }
}
