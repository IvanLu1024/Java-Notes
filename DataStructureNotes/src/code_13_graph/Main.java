package code_13_graph;

/**
 * Created by 18351 on 2019/1/6.
 */
public class Main {
    public static void main(String[] args) {
        DenseGraph g1=new DenseGraph(4,false);

        g1.addEdge(0,1);
        g1.addEdge(0,2);
        g1.addEdge(0,3);
        g1.addEdge(1,3);
        g1.addEdge(2,3);

        Iterable<Integer> adj1=g1.adj(0);
        System.out.println(adj1);

        DenseGraph g2=new DenseGraph(4,false);

        g2.addEdge(0,1);
        g2.addEdge(0,2);
        g2.addEdge(0,3);
        g2.addEdge(1,3);
        g2.addEdge(2,3);

        Iterable<Integer> adj2=g1.adj(0);
        System.out.println(adj2);
    }
}
