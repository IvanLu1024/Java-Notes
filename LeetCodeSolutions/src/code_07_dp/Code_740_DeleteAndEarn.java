package code_07_dp;

/**
 * 740. Delete and Earn
 *
 * Given an array nums of integers, you can perform operations on the array.
 * In each operation, you pick any nums[i] and delete it to earn nums[i] points.
 * After, you must delete every element equal to nums[i] - 1 or nums[i] + 1.
 * You start with 0 points. Return the maximum number of points you can earn by applying such operations.
 *
 * You start with 0 points. Return the maximum number of points you can earn by applying such operations.

 题目大意：
 给一个数组，你可以拿起nums[i]并且删除nums[i]并得到nums[i]分数，
 但是你必须删除数组中所有的nums[i]-1和nums[i]+1，
 就是说nums[i]-1和nums[i]+1的分数是不可能得到了,求问你能够获得最大的分数会是多少。

 Example 1:
 Input: nums = [3, 4, 2]
 Output: 6
 Explanation:
 Delete 4 to earn 4 points, consequently 3 is also deleted.
 Then, delete 2 to earn 2 points. 6 total points are earned.

 Input: nums = [2, 2, 3, 3, 3, 4]
 Output: 9
 Explanation:
 Delete 3 to earn 3 points, deleting both 2's and the 4.
 Then, delete 3 again to earn 3 points, and 3 again to earn 3 points.
 9 total points are earned.

 Note:
 The length of nums is at most 20000.
 Each element nums[i] is an integer in the range [1, 10000].

 */
public class Code_740_DeleteAndEarn {
    /**
     对于值为i的元素，要么抛弃它，要么选择它
     选择它意味着抛弃i-1和i+1：
     dp[i] = 所有i的和 + dp[i+2]
     而抛弃它意味着
     dp[i] = dp[i+1]
     */
    public int deleteAndEarn(int[] nums) {
        int len = nums.length;

        //数组中元素范围在[1, 10000].
        int[] num=new int[10001];
        int[] dp=new int[10003];
        //num[i]统计值为nums数组中值为nums[i]的元素之和
        for (int i = 0; i < len; i++) {
            num[nums[i]] += nums[i];
        }
        for (int i = 10000; i >= 0; i--) {
            dp[i] = Math.max(num[i]+dp[i+2], dp[i+1]);
        }
        return dp[0];
    }
}
