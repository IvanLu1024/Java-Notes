package code_07_dp;

/**
 * 788. Rotated Digits
 *
 * X is a good number if after rotating each digit individually by 180 degrees,
 * we get a valid number that is different from X.
 *
 * Each digit must be rotated - we cannot choose to leave it alone.
 * A number is valid if each digit remains a digit after rotation.
 * 0, 1, and 8 rotate to themselves;
 * 2 and 5 rotate to each other;
 * 6 and 9 rotate to each other,
 * and the rest of the numbers do not rotate to any other number and become invalid.
 * Now given a positive number N, how many numbers X from 1 to N are good?
 *
 * Example:
 Input: 10
 Output: 4
 Explanation:
 There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
 Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.
 */
public class Code_788_RotatedDigits {
    public int rotatedDigits(int N) {
        int res=0;
        for(int num=1;num<=N;num++){
            if(isGood(num,false)){
                res++;
            }
        }
        return res;
    }

    //判断 num 是否是 good
    //num在[1-9]之间，则 2，5，6，9 必然是 good
    //num当num是 0、1、8时要接着看上一位了
    private boolean isGood(int num,boolean flag){
        if(num==0){
            return flag;
        }

        int d=num%10;
        //如果num数字每一位上，都有3或4或7,则num必然不是good
        //如347就不是good , 437也不是good
        if(d==3 || d==4 || d==7){
            return false;
        }

        if(d==0 || d==1 || d==8){
            return isGood(num/10,flag);
        }

        return isGood(num/10,true);
    }
}
