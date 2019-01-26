package code_13_others;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 781. Rabbits in Forest
 *
 * In a forest, each rabbit has some color.
 * Some subset of rabbits (possibly all of them) tell you how many other rabbits have the same color as them.
 * Those answers are placed in an array.
 *
 * Return the minimum number of rabbits that could be in the forest.
 *
 * Examples:
 Input: answers = [1, 1, 2]
 Output: 5
 Explanation:
 The two rabbits that answered "1" could both be the same color, say red.
 The rabbit than answered "2" can't be red or the answers would be inconsistent(不确定的).
 Say the rabbit that answered "2" was blue.
 Then there should be 2 other blue rabbits in the forest that didn't answer into the array.
 The smallest possible number of rabbits in the forest is therefore 5: 3 that answered plus 2 that didn't.

 Input: answers = [10, 10, 10]
 Output: 11

 Input: answers = []
 Output: 0

 * Note:
 * answers will have length at most 1000.
 * Each answers[i] will be an integer in the range [0, 999].
 */
public class Code_781_RabbitsInForest {
    /**
     * 思路：
     * 同一种颜色的兔子的答案一定是相同的（但是答案相同的兔子不一定就是一种颜色），答案不同的兔子一定颜色不同。
     * 所以我们的思路就是首先把答案相同的兔子进行聚类。
     *
     * 1.对于每组答案相同的兔子而言，如果它们都属于同一种颜色，那么参与回答的兔子数量一定不会超过它们的答案+1
     * （例如，假如有3个兔子都回答说有另外1个兔子和它本身的颜色相同，
     * 那么这3个兔子不可能同样颜色，最好的可能是其中2个兔子颜色相同，
     * 而另外1个兔子是另外一种颜色，也有可能3个兔子的颜色各不相同）。
     * 根据这一限制，我们就知道回答这组答案的兔子的颜色数的最小可能，
     *
     * 2.而每种颜色的兔子的个数即是它们的答案+1，这样就可以得到回答这组答案的兔子的最小可能颜色数。
     * 这样把所有组答案的兔子的最小可能个数加起来，就是题目所求。
     */
    public int numRabbits(int[] answers) {
        if(answers == null || answers.length == 0){
            return 0;
        }
        // <答案，数量>
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int answer : answers){
            int count = map.getOrDefault(answer,0);
            map.put(answer,++count);
        }

        int res = 0;
        for(int nums : map.keySet()){
            int groupNum = map.get(nums)/(nums+1);
            if(map.get(nums)%(nums+1)!=0){
                groupNum++;
            }
            res += groupNum * (nums+1);
        }
        return res;
    }

    @Test
    public void test(){
        int[] ans ={0,0,1,1,1};
        //int[] ans={10, 10, 10};
        System.out.println(numRabbits(ans));
    }
}
