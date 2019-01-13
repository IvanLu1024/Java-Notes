package code_08_greedyAlgorithms;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The i-th person has weight people[i], and each boat can carry a maximum weight of limit.
 * Each boat carries at most 2 people at the same time,
 * provided the sum of the weight of those people is at most limit.
 * (limit就是船的载重量)
 * Return the minimum number of boats to carry every given person.
 * (It is guaranteed each person can be carried by a boat.)
 *
 * Note:
 * 1 <= people.length <= 50000
 * 1 <= people[i] <= limit <= 30000
 */
public class Code_881_BoatsToSavePeople {
    /**
     * 先进行排序：
     * people[i] + people[j] <= limit 判断最大重量，最小重量之和
     * 如果两者之和小于那么能够被船承载，然后改变索引判断下一个最大值和最小值
     * 如果两者之和大于 limit，那么最大值被筛选出并单独坐一艘船，
     */
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);

        int i = 0, j = people.length - 1;
        int res = 0;
        while(i <= j){
            //每条船尽量多搭重量轻的人
            if(people[i] + people[j] <= limit){
                i ++;
                j --;
            }
            else{
                j--;
            }
            res ++;
        }
        return res;
    }

    @Test
    public void test(){
         //int[] people={3,5,3,4};
         //int limit = 5;
         //int[] people={1,2};
         //int limit = 3;
         int[] people={3,2,2,1};
         int limit = 3;
        System.out.println(numRescueBoats(people,limit));
    }
}
