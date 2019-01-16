package code_10_math;

/**
 * Created by DHA on 2019/1/16.
 */
public class Code_470_ImplementRand10UsingRand7 {
    /**
     * The rand7() API is already defined in the parent class SolBase.
     * public int rand7();
     * @return a random integer in the range 1 to 7
     */
    /**
     * 思路：模仿 rand5-->rand7()
     * rand7-->rand10 --> [0,6*7+6) -->[0,48)
     * 要求数值在 [1,10]之间
     */
    /*class Solution extends SolBase {
        public int rand10() {
            int v=7*(rand7()-1) + (rand7()-1);
            if(v<=39){
                //要求数值在 [1,10]之间,所以需要+1
                return v%10+1;
            }
            return rand10();
        }
    }*/
}
