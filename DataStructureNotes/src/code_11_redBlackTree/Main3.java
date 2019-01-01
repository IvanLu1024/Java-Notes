package code_11_redBlackTree;

import java.util.ArrayList;
import java.util.Random;

/**
 - 对于完全随机的数据，建议使用普通的BST。但是在极端情况下，会退化成链表！(或者高度不平衡)

 - 对于查询较多的使用情况，建议使用AVL树。

 - 红黑树牺牲了平衡性(2log n的高度)，统计性能更优(增删改查所有的操作)
 */
public class Main3 {

    public static void main(String[] args) {

        int n = 2000000;

        ArrayList<Integer> testData = new ArrayList<>(n);
        for(int i = 0 ; i < n ; i ++)
            testData.add(i);

        // Test AVL
        long startTime = System.nanoTime();

        AVLTree<Integer, Integer> avl = new AVLTree<>();
        for (Integer x: testData)
            avl.add(x, null);

        long endTime = System.nanoTime();

        double time = (endTime - startTime) / 1000000000.0;
        System.out.println("AVL: " + time + " s");


        // Test RBTree
        startTime = System.nanoTime();

        RBTree<Integer, Integer> rbt = new RBTree<>();
        for (Integer x: testData)
            rbt.add(x, null);

        endTime = System.nanoTime();

        time = (endTime - startTime) / 1000000000.0;
        System.out.println("RBTree: " + time + " s");
    }
}