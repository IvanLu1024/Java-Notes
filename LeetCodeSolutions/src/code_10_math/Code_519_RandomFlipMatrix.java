package code_10_math;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * 519. Random Flip Matrix
 *
 * You are given the number of rows n_rows and number of columns n_cols of a 2D binary matrix
 * where all values are initially 0.
 * Write a function flip which chooses a 0 value uniformly(一致地) at random, changes it to 1,
 * and then returns the position [row.id, col.id] of that value.
 * Also, write a function reset which sets all values back to 0.
 * Try to minimize the number of calls to system's Math.random() and optimize the time and space complexity.

 Note:
 * 1 <= n_rows, n_cols <= 10000
 * 0 <= row.id < n_rows and 0 <= col.id < n_cols
 * flip will not be called when the matrix has no 0 values left.
 * the total number of calls to flip and reset will not exceed 1000.
 */
public class Code_519_RandomFlipMatrix {
    /**
     * 思路：
     * 本题是经典的产生无重复数序列的随机数。
     * 假设矩阵的规模是n_rows * n_cols，对于每一个格子都对应着一个编号 num= n_rows * (i-1)+j，
     * 其中i是当前行，j是当前列。
     * TODO:那么该题就转换成如何产生一个[0，n_rows * n_cols -1]的随机数，并且不重复。
     */
    class Solution {
        private HashSet<Integer> set;
        private int rows;
        private int cols;
        private Random random;

        public Solution(int n_rows, int n_cols) {
            set = new HashSet<>();
            rows = n_rows;
            cols = n_cols;
            random = new Random();
        }

        public int[] flip() {
             int r;
             //一直到 r 不是重复元素为止
             do{
                 r = random.nextInt(rows*cols);
             }while (set.contains(r)); //判断条件 r 不是重复元素，就会退出循环
            //r 是一个新的随机数
            set.add(r);
             return new int[]{r/cols,r%cols};
        }

        public void reset() {
            set.clear();
        }
    }
}
