package code_01_array;

import org.junit.Test;

/**
 * 27. Remove Element
 *
 * Given an array nums and a value val, remove all instances of that value in-place and return the new length.
 * TODO:Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 * TODO:The order of elements can be changed. It doesn't matter what you leave beyond the new length.

 * Example 1:
 *  Given nums = [3,2,2,3], val = 3,
 *  Your function should return length = 2, with the first two elements of nums being 2.
 *  It doesn't matter what you leave beyond the returned length.

 * Example 2:
 *  Given nums = [0,1,2,2,3,0,4,2], val = 2,
 *  Your function should return length = 5, with the first five elements of nums containing 0, 1, 3, 0, and 4.
 *  Note that the order of those five elements can be arbitrary.
 *  It doesn't matter what values are set beyond the returned length.
 */
public class Code_27_RemoveElement {
    /**
     * 思路1：
     * 1、准备一个指针k，指向不是val的元素,保证 [0,k)都没有val元素
     * 2、[0,k)就是nums数组的前k个非val的元素，直接返回k值，就可以了
     * @param nums
     * @param val
     * @return
     */
    public int removeElement1(int[] nums, int val) {
        int k=0;
        for(int num:nums){
            if(num!=val){
                nums[k++]=num;
            }
        }
        return k;
    }

    /**
     * 思路二：
     * 遍历数组，若和val相等，则用最后一项代替这一项，并判断代替后的值是不是和val相等。
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        int last=0;
        for(int i=0;i<nums.length-last;i++){
            if(nums[i]==val){
                //元素值与目标值相等，就用最后一项元素代替该元素，再从该元素进行比较
                last++;
                nums[i]=nums[nums.length-last];
                //将最后一项赋值给该元素
                i--;//再比较替换后的元素，是够等于val
            }
        }
        return nums.length-last;
    }


    @Test
    public void test(){
        int[] arr={3,2,2,3};
        int val=3;
        System.out.println(removeElement(arr,val));
    }
}
