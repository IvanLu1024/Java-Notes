package code_01_array;

/**
 *
 * 167 Two Sum II - Input array is sorted
 *
 * Given an array of integers that is already sorted in ascending order, find two numbers such that they add up to a specific target number.
 * The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2.
 *
 * Note:
 * Your returned answers (both index1 and index2) are not zero-based.
 * You may assume that each input would have exactly one solution and you may not use the same element twice.

 * Example:
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [1,2]
 *
 * Explanation: The sum of 2 and 7 is 9. Therefore index1 = 1, index2 = 2.
 */
public class Code_167_TwoSumII {

    /**
     * 思路一：
     数组有序，首先想到二分查找，对于nums[i]，如果数组中存在两个元素和为target，
     则必然在nums[i+1..n-1]中存在元素target-nums[i]。
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum1(int[] numbers, int target) {

        for(int i=0;i<numbers.length;i++){
            int index=binarySearch(numbers,target-numbers[i],i+1,numbers.length-1);
            if(index!=-1){
                //说明[i+1...n-1]存在元素target-numbers[i]
                return new int[]{i+1,index+1};
            }
        }
        return null;
    }

    /**
     *
     * 思路二：
     *
     * 引入两个指针 i,j，分别指向该有序数组的头部和尾部：
     当numbers[i]+numbers[j]==target时, i、j就是所求的解
     当numbers[i]+numbers[j]<target时,说明值过小，要加大值，i加1
     当numbers[i]+numbers[j]==target时,说明值过大，要减小值，j减1
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum(int[] numbers, int target) {
        int i=0;
        int j=numbers.length-1;
        while(i<j){
            //i和j是不能相等的，因为反返回两个不同下标
            int sum=numbers[i]+numbers[j];
            if(sum==target){
                return new int[]{i+1,j+1};
            }else if(sum<target){
                i++;
            }else{
                j--;
            }
        }
        return null;
    }

    public int binarySearch(int[] numbers,int target,int l,int r){
        while(l<=r){
            int mid=(r-l)/2+l;
            if(numbers[mid]==target){
                return mid;
            }else if(numbers[mid]<target){
                l=mid+1;
            }else{
                r=mid-1;
            }
        }
        return -1;
    }
}
