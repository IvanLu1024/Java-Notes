package code_03_list;

/**
 * 287. Find the Duplicate Number
 *
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive),
 * prove that at least one duplicate number must exist.
 * Assume that there is only one duplicate number, find the duplicate one.
 *
 * Example 1:
 Input: [1,3,4,2,2]
 Output: 2

 * Example 2:
 Input: [3,1,3,4,2]
 Output: 3

 Note:
 You must not modify the array (assume the array is read only).
 You must use only constant, O(1) extra space.
 Your runtime complexity should be less than O(n2).
 There is only one duplicate number in the array, but it could be repeated more than once.
 */
public class Code_287_FindTheDuplicateNumber {
    /**
     * 思路：
     * 链表快指针与慢指针的应用
     * 将数组抽象为一条线和一个圆环，因为1～n 之间有n＋1个数，所以一定有重复数字出现，所以重复的数字即是圆环与线的交汇点。
     * 然后设置两个指针，一个快指针一次走两步，一个慢指针一次走一步。
     * 当两个指针第一次相遇时:
     * 令快指针回到原点（0）且也变成一次走一步，
     * 慢指针则继续前进，再次会合时即是线与圆环的交汇点。
     */
    public int findDuplicate(int[] nums) {
        if(nums.length<=1){
            //返回值不在[1...nums.length]之间即可
            return 0;
        }
        int slow=nums[0];
        int fast=nums[nums[0]];
        while(slow!=fast){
            slow=nums[slow];
            fast=nums[nums[fast]];
        }
        fast=0;
        while(fast!=slow){
            slow=nums[slow];
            fast=nums[fast];
        }
        return slow;
    }
}
