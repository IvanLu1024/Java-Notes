package code_10_math;

import java.util.HashSet;
import java.util.Set;

/**
 * 898. Bitwise ORs of Subarrays
 *
 * We have an array A of non-negative integers.
 * For every (contiguous) subarray B = [A[i], A[i+1], ..., A[j]] (with i <= j),
 * we take the bitwise OR of all the elements in B, obtaining a result A[i] | A[i+1] | ... | A[j].
 * Return the number of possible results.
 * (Results that occur more than once are only counted once in the final answer.)
 * 一个数组的所有子数组的位或结果，总共有多少个不同？
 *
 * Example 1:
 Input: [0]
 Output: 1
 Explanation:
 There is only one possible result: 0.

 Example 2:
 Input: [1,1,2]
 Output: 3
 Explanation:
 The possible subarrays are [1], [1], [2], [1, 1], [1, 2], [1, 1, 2].
 These yield the results 1, 1, 2, 1, 3, 3.
 There are 3 unique values, so the answer is 3.

 Example 3:
 Input: [1,2,4]
 Output: 6
 Explanation:
 The possible results are 1, 2, 3, 4, 6, and 7.

 * Note：
 1 <= A.length <= 50000
 0 <= A[i] <= 10^9
 */
public class Code_898_BitwiseORsOfSubarrays {
    /**
     * memo[i]表示以A[i]结尾的所有子数组的位或结果，其实是个set。
     * 转移方程式memo[i] =
     * (
     *    for b in memo[i - 1]
     *      (b | A[i])
     * )+ A[i]
     */
    public int subarrayBitwiseORs(int[] A) {
        Set<Integer> memo=new HashSet<>();
        Set<Integer> res=new HashSet<>();
        for(int a:A){
            Set<Integer> cur=new HashSet<>();
            for(int b:memo){
                cur.add(b|a);
            }
            cur.add(a);
            memo=cur;
            res.addAll(cur);
        }
        return res.size();
    }
}
