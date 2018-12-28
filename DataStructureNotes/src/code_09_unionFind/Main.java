package code_09_unionFind;

import java.util.Random;

/**
 * Created by 18351 on 2018/12/28.
 */
public class Main {
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
        // UnionFind1 慢于 UnionFind2
        //int size = 100000;
        //int m = 10000;

        // UnionFind2 慢于 UnionFind1, 但UnionFind3最快
        int size = 100000;
        int m = 100000;

        UnionFind1 uf1 = new UnionFind1(size);
        System.out.println("UnionFind1 : " + testUF(uf1, m) + " s");

        UnionFind2 uf2 = new UnionFind2(size);
        System.out.println("UnionFind2 : " + testUF(uf2, m) + " s");

        //基于size对UionFind2进行改进
        UnionFind3 uf3 = new UnionFind3(size);
        System.out.println("UnionFind3 : " + testUF(uf3, m) + " s");
        /**
         * 输出结果：
          UnionFind1 : 6.986927179 s
         UnionFind2 : 14.555996308 s
         UnionFind3 : 0.017809231 s
         */
    }
}