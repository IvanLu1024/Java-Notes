package code_10_math;

import org.junit.Test;

import java.util.Arrays;

/**
 * 462. Minimum Moves to Equal Array Elements II
 *
 * Given a non-empty integer array,
 * find the minimum number of moves required to make all array elements equal,
 * where a move is incrementing a selected element by 1 or decrementing a selected element by 1.
 *
 * You may assume the array's length is at most 10,000.
 *
 * Example:
 Input:
 [1,2,3]
 Output:
 2
 Explanation:
 Only two moves are needed (remember each move increments or decrements one element):
 [1,2,3]  =>  [2,2,3]  =>  [2,2,2]
 */
public class Code_462_MinimumMovesToEqualArrayElementsII {
    public int minMoves21(int[] nums) {
        Arrays.sort(nums);
        int low=0;
        int high=nums.length-1;

        int res=0;
        while(low<high){
            res += nums[high] - nums[low];
            low++;
            high--;
        }
        return res;
    }

    /**
     * 思路二：
     * 使用快速选择找到中位数，时间复杂度 O(N)
     * */
    public int minMoves2(int[] nums) {
        //获取该数组的中位数
        int mid=findKthSmallest(nums,nums.length/2);
        int res=0;
        for(int num : nums){
            res += Math.abs(num-mid);
        }
        return res;
    }

    /**
     * 查找第k小的数值
     */
    private int findKthSmallest(int[] nums, int k) {
        int l=0;
        int r=nums.length-1;
        while(l<r){
            int p=partition(nums,l,r);
            if(p==k){
                break;
            }else if(p<k){
                l = p+1;
            }else{
                r = p-1;
            }
        }
        return nums[k];
    }

    //nums的[l,r]获取切分后的元素下标
    private int partition(int[] nums,int l,int r){
        int pivot=nums[l];
        while(l<r){
            //从数组的右端向左扫描找到第一个小于pivot的元素，交换这两个元素
            while(l<r && nums[r]>=pivot){
                r--;
            }
            nums[l]=nums[r];
            //从数组的左端向右扫描找到第一个大于pivot的元素，交换这两个元素
            while(l<r && nums[l]<=pivot){
                l++;
            }
            nums[r]=nums[l];
        }
        nums[l]=pivot;
        return l;
    }

    @Test
    public void test(){
        int[] nums={1,1,1};
        System.out.println(minMoves2(nums));
        //System.out.println(partition(nums,0,nums.length-1));
    }
}
