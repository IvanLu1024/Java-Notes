package code_09_unionFind;

import java.util.Random;

/**
 * Created by 18351 on 2018/12/28.
 */
public class Main2 {
    private static double testUF(UF uf, int m){
        int size = uf.getSize();
        Random random = new Random();
        long startTime = System.nanoTime();
        for(int i = 0 ; i < m ; i ++){
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.unionElements(a, b);
        }

        for(int i = 0 ; i < m ; i ++){
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.isConnected(a, b);
        }

        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) {
        int size = 10000000;
        int m = 10000000;

        //基于size对UionFind2进行改进
        UnionFind3 uf3 = new UnionFind3(size);
        System.out.println("UnionFind3 : " + testUF(uf3, m) + " s");

        //基于rank对UionFind3进行改进
        UnionFind4 uf4 = new UnionFind4(size);
        System.out.println("UnionFind4 : " + testUF(uf4, m) + " s");
        /**
         * 输出结果：
         UnionFind3 : 6.095589744 s
         UnionFind4 : 4.930825026 s
         */
    }
}