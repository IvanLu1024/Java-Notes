package code_14_minSpanTree;

import java.util.Vector;

/**
 * Created by 18351 on 2019/1/7.
 */
public class Main2 {
    public static void main(String[] args) {
        // 使用两种图的存储方式读取testG1.txt文件
        String filename = "testG4.txt";
        SparseWeightedGraph<Double> g1 = new SparseWeightedGraph<Double>(8, false);
        ReadWeightedGraph readGraph1 = new ReadWeightedGraph(g1, filename);
        System.out.println("test G4 in Sparse Weighted Graph:");
        g1.show();

        LazyPrimMST<Double> lazyPrimMST=new LazyPrimMST<>(g1);
        Vector<Edge<Double>> mst=lazyPrimMST.mstEdges();
        for(Edge<Double> e:mst){
            System.out.println(e);
        }
        System.out.println("The mst weight is:"+lazyPrimMST.result());

        PrimMST<Double> primMST=new PrimMST<>(g1);
        Vector<Edge<Double>> mst2=primMST.mstEdges();
        for(Edge<Double> e:mst2){
            System.out.println(e);
        }
        System.out.println("The mst weight is:"+primMST.result());


        KruskalMST<Double> kruskalMST=new KruskalMST<>(g1);
        Vector<Edge<Double>> mst3=kruskalMST.mstEdges();
        for(Edge<Double> e:mst3){
            System.out.println(e);
        }
        System.out.println("The mst weight is:"+kruskalMST.result());

    }
}
