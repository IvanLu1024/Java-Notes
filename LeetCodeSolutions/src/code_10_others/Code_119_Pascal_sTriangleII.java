package code_10_others;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 119. Pascal's Triangle II
 *
 * Given a non-negative index k where k ≤ 33,
 * return the kth index row of the Pascal's triangle.
 * Note that the row index starts from 0.
 *
 * Example:

 Input: 3
 Output: [1,3,3,1]

 * Follow up:
 Could you optimize your algorithm to use only O(k) extra space?
 */
public class Code_119_Pascal_sTriangleII {
    public List<Integer> getRow1(int rowIndex) {
        int size=rowIndex+1;
        Integer[][] triangle=new Integer[size][size];

        //初始化杨辉三角
        for(int i=0;i<size;i++){
            triangle[i][0]=1;
        }
        for(int i=0;i<size;i++){
           triangle[i][i]=1;
        }

        for(int i=2;i<size;i++){
            for(int j=1;j<i;j++){
                triangle[i][j]=triangle[i-1][j-1]+triangle[i-1][j];
            }
        }

        List<Integer> res= Arrays.asList(triangle[rowIndex]);
        return res;
    }

    /**
     * 改进
     * 空间复杂度只有O(k)
     */
    public List<Integer> getRow(int rowIndex) {
        List<Integer> res=new ArrayList<>();

        for(int i=0;i<=rowIndex;i++){
            res.add(1);
            for(int j=i-1;j>=1;j--){
                res.set(j,res.get(j)+res.get(j-1));
            }
        }
        return res;
    }


    @Test
    public void test(){
        System.out.println(getRow(3));
    }
}
