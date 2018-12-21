package code_07_dp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 118. Pascal's Triangle
 *
 * Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
 */
public class Code_118_Pascal_sTriangle {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res=new ArrayList<>();
        if(numRows==0) {
            return res;
        }
        for(int i=0;i<numRows;i++){
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < i+1; j++) {
                if(j==0 || j==i){
                    list.add(1);
                }else{
                    list.add(res.get(i - 1).get(j - 1) + res.get(i - 1).get(j));
                }
            }
            res.add(list);
        }
        return res;
    }

    @Test
    public void test(){
        System.out.println(generate(5));
    }
}
