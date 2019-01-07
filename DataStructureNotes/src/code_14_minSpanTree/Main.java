package code_14_minSpanTree;

/**
 * Created by 18351 on 2019/1/7.
 */
public class Main {
    public static void main(String[] args) {
        // 使用两种图的存储方式读取testG1.txt文件
        String filename = "testG4.txt";
        SparseWeightedGraph<Double> g1 = new SparseWeightedGraph<Double>(8, false);
        ReadWeightedGraph readGraph1 = new ReadWeightedGraph(g1, filename);
        System.out.println("test G4 in Sparse Weighted Graph:");
        g1.show();

        System.out.println();

        DenseWeightedGraph<Double> g2 = new DenseWeightedGraph<Double>(8, false);
        ReadWeightedGraph readGraph2 = new ReadWeightedGraph(g2 , filename );
        System.out.println("test G4 in Dense Graph:");
        g2.show();

        System.out.println();
    }
}
