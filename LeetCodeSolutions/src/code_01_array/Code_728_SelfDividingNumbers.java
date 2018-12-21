package code_01_array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 728. Self Dividing Numbers
 *
 * A self-dividing number is a number that is divisible by every digit it contains.
 * For example, 128 is a self-dividing number because 128 % 1 == 0, 128 % 2 == 0, and 128 % 8 == 0.
 * Also, a self-dividing number is not allowed to contain the digit zero.
 *
 * Given a lower and upper number bound, output a list of every possible self dividing number, including the bounds if possible.
 *
 * Example 1:
 Input:left = 1, right = 22
 Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22]

 * Note:
 The boundaries of each input argument are 1 <= left <= right <= 10000.
 */
public class Code_728_SelfDividingNumbers {
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> res=new ArrayList<>();
        if(left>right){
            return res;
        }
        for(int i=left;i<=right;i++){
            if(isValid(i)){
                res.add(i);
            }
        }
        return res;
    }

    private boolean isValid(int num){
        int Num=num;
        //获取num的各位上的数字
        while(num!=0){
            int tmp=num%10;
            if(tmp==0){
                return false;
            }
            if(Num%tmp!=0){
                return false;
            }
            num=num/10;
        }
        return true;
    }

    @Test
    public void test(){
        int left = 1, right = 22;
        System.out.println(selfDividingNumbers(left,right));
    }
}
