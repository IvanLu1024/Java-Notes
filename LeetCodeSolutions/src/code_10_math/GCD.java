package code_10_math;

import org.junit.Test;

import java.lang.annotation.Target;

/**
 * 求 a,b的最大公约数
 */
public class GCD {
    public int gcd1(int a,int b){
        return b==0? a: gcd1(b,a%b);
    }

    /**
     * 思路：
     * 对于 a 和 b 的最大公约数 f(a, b)，有：
     如果 **a 和 b 均为偶数**，f(a, b) = 2*f(a/2, b/2);
     如果 **a 是偶数 b 是奇数**，f(a, b) = f(a/2, b);
     如果 **a 是奇数 b 是偶数**，f(a, b) = f(a, b/2);
     如果 **a 和 b 均为奇数**，f(a, b) = f(b, a-b);
     */
    public int gcd2(int a,int b){
        if(b>a){
            return gcd2(b,a);
        }
        if(b==0){
            return a;
        }
        boolean isAEven=isEven(a);
        boolean isBEven=isEven(b);
        if(isAEven && isBEven){
            return 2 * gcd2(a>>1,b>>1);
        }else if(isAEven && !isBEven){
            return gcd2(a>>1,b);
        }else if(!isAEven && isBEven){
            return gcd2(a,b>>1);
        }else{
            return gcd2(b,a-b);
        }
    }

    //判断n是否是偶数
    private boolean isEven(int n){
        if(n % 2==0){
            return true;
        }
        return false;
    }

    @Test
    public void test(){
        System.out.println(gcd1(4,8));
        System.out.println(gcd2(4,8));
    }
}
