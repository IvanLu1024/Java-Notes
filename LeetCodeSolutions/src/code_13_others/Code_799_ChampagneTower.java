package code_13_others;

import org.junit.Test;

import java.util.Scanner;

/**
 * 799. Champagne Tower
 *
 * We stack glasses in a pyramid, where the first row has 1 glass,
 * the second row has 2 glasses, and so on until the 100th row.  Each glass holds one cup (250ml) of champagne.
 * Then, some champagne is poured in the first glass at the top.
 * When the top most glass is full, any excess liquid poured will fall equally to the glass immediately to the left and right of it.
 * When those glasses become full, any excess champagne will fall equally to the left and right of those glasses, and so on.
 * (A glass at the bottom row has it's excess champagne fall on the floor.)
 * For example, after one cup of champagne is poured, the top most glass is full.
 * After two cups of champagne are poured, the two glasses on the second row are half full.
 * After three cups of champagne are poured, those two cups become full - there are 3 full glasses total now.
 * After four cups of champagne are poured, the third row has the middle glass half full,
 * and the two outside glasses are a quarter full.
 *
 * Note:
 * poured will be in the range of [0, 10 ^ 9].
 * query_glass and query_row will be in the range of [0, 99].
 */
public class Code_799_ChampagneTower {
    /**
     * 思路一：
     * 分析数字的规律
     * 先确定倒入 poured 杯水后，所能达到的层数。
     * 注意：层数是从1开始的。
     */
    public double champagneTower1(int poured, int query_row, int query_glass) {
        if(poured ==0){
            return 0;
        }
        if(poured == 1 && query_row==0 && query_glass==0){
            return 1.0;
        }

        //当倒入 poured 杯水后，所在的层数
        int k = 1;
        while(k*(k+1)/2 < poured){
            k++;
        }
        int level = k -1;

        if(query_row == level){
            if(query_glass==0 || query_glass==level){
                return (1.0/(2*level));
            }else{
                return (1.0/level);
            }

        }
        if(query_row < level && query_glass<level){
            return 1.0;
        }
        return 0.0;
    }

    /**
     * 思路二：动态规划问题。
     * 假设有 10 杯酒。
     * capacity[i][j]表示第 i 行、第 j 列酒杯所装的酒，酒杯满了，就取 1。
     *
     * 如果 poured = 10 的话，则有
     10

     4.5    4.5

     1.75       3.5     1.75

     0.375 1.625 1.625 0.375

     0 0.3125 0.625 0.3125 0
     (注意，凡是装酒容量 >1 的，说明酒杯满了)
     */
    public double champagneTower(int poured, int query_row, int query_glass) {
        if(poured == 0){
            //未倒酒的情况
            return 0.0;
        }
        //虽然 query_glass and query_row will be in the range of [0, 99]
        //但是下面有 i+1 的操作，会导致数组越界，多取一个长度
        double[][] capacity = new double[101][101];
        capacity[0][0] = poured * 1.0;

        for(int i=0;i<=query_row;i++){
            for(int j=0;j<=i;j++){
                //先看看 i行j杯是否满了
                double remain = (capacity[i][j] - 1)/2;
                if(remain > 0){
                    //i行j杯满了，酒就会向下一层的两个相邻的杯子中溢出
                    capacity[i+1][j] += remain;
                    capacity[i+1][j+1] += remain;
                }
            }
        }
        return Math.min(capacity[query_row][query_glass],1.0);
    }

    @Test
    public void test(){
       /* int k = 1;
        int n = 1;
        while(k*(k+1)/2 < n){
            k++;
        }
        System.out.println(k-1);*/

        int poured = 11, query_glass = 2, query_row = 4;
        System.out.println(champagneTower(poured,query_glass,query_row));
    }
}
