package code_07_dp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 120. Triangle
 *
 * Given a triangle, find the minimum path sum from top to bottom.
 * Each step you may move to adjacent numbers on the row below.
 * For example, given the following triangle

 [
 [2],
 [3,4],
 [6,5,7],
 [4,1,8,3]
 ]
 * The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).

 * Note:
 * Bonus point if you are able to do this using only O(n) extra space,
 * where n is the total number of rows in the triangle.
 */
public class Code_120_Triangle {
    //状态转移方程
    //f(0,0)=tri[0][0]
    //f(i,0)=tri[i][0]+f(i-1,0) (i>0)
    //f(i,i)=tri[i][i]+f(i-1,i-1)(i>=1)
    //f(i,j)=tri[i][j]+min{f(i-1,j-1),f(i-1,j)}

    //空间复杂度O(n^2),不符合题目要求，要进行优化
    public int minimumTotal1(List<List<Integer>> triangle) {
        int n = triangle.size();

        int[][] memo=new int[n][];
        for(int i=0;i<n;i++){
            memo[i]=new int[i+1];
        }
        memo[0][0]=triangle.get(0).get(0);
        for(int i = 1 ; i < n ; i ++){
            memo[i][0]=triangle.get(i).get(0)+memo[i-1][0];
            memo[i][i]=triangle.get(i).get(i)+memo[i-1][i-1];
            for(int j=1;j<i;j++){
                memo[i][j]=triangle.get(i).get(j)+Math.min(memo[i-1][j-1],memo[i-1][j]);
            }
        }
        Arrays.sort(memo[n-1]);
        return memo[n-1][0];
    }

    //观察状态转移方程，每次i都依赖于(i-1)
    public int minimumTotal2(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] memo=new int[n];

        for(int j=0;j<n;j++){
            memo[j]=triangle.get(n-1).get(j);
        }
        for(int i = n-2; i >=0 ; i --){
            for(int j = 0 ; j <= i ; j ++){
                memo[j]=triangle.get(i).get(j)+Math.min(memo[j],memo[j+1]);
            }
        }
        return memo[0];
    }

    /**
     * 思路：典型的动态规划问题
     * (1)存在最有子结构
     * (2)子问题存在大量的重复计算
     * @param triangle
     * @return
     */
    public int minimumTotal3(List<List<Integer>> triangle) {
        int n = triangle.size();
        for(int i = 1 ; i < n ; i ++){
            triangle.get(i).set(0,triangle.get(i).get(0)+triangle.get(i-1).get(0));
            triangle.get(i).set(i,triangle.get(i).get(i)+triangle.get(i-1).get(i-1));
            for(int j = 1 ; j < i ; j ++)
                triangle.get(i).set(j,triangle.get(i).get(j)+Math.min(triangle.get(i-1).get(j-1),triangle.get(i-1).get(j)));
        }
        Collections.sort(triangle.get(n-1));
        return triangle.get(n-1).get(0);
    }



    @Test
    public void test(){
        List<Integer> l1=new ArrayList<>();
        l1.add(2);

        List<Integer> l2=new ArrayList<>();
        l2.add(3);
        l2.add(4);

        List<Integer> l3=new ArrayList<>();
        l3.add(6);
        l3.add(5);
        l3.add(7);

        List<Integer> l4=new ArrayList<>();
        l4.add(4);
        l4.add(1);
        l4.add(8);
        l4.add(3);

        List<List<Integer>> list=new ArrayList<>();
        list.add(l1);
        list.add(l2);
        list.add(l3);
        list.add(l4);

        System.out.println(minimumTotal2(list));
    }
}
