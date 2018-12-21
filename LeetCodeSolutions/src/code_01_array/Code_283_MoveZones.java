package code_01_array;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 283. Move Zeroes
 * Description:
 * Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.

 * Example:

 * Input: [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 * Note:
 *  You must do this in-place without making a copy of the array.
 *  Minimize the total number of operations.
 */
public class Code_283_MoveZones {
    /**
     * 思路一：
     * 1、准备一个集合，用于存储该数组中的非0元素，遍历数组中元素，遇到非0元素，就放入集合中
     * 2、对该数组重新赋值，前面的非0元素都是从集合中取出，后面的元素都是0
     * 时间复杂度 O(n)
     * 空间复杂度 O(n)
     */
    public void moveZeroes1(int[] nums) {
        List<Integer> list=new ArrayList<Integer>();
        for(int num:nums){
            if(num!=0){
                list.add(num);
            }
        }
        for(int i=0;i<list.size();i++){
            nums[i]=list.get(i);
        }
        for(int j=list.size();j<nums.length;j++){
            nums[j]=0;
        }
    }

    /**
     * 思路二：
     * 1、引入另外一个指针k,用于指向数组中非0元素（原有一个遍历数组的指针i），很显然k <= nums.length-1
     * 2、则nums的前k个都是非0元素了，剩下的元素就是0元素
     * @param nums
     */
    //时间复杂度：O(n)
    //空间复杂度：O(1)
    public void moveZeroes2(int[] nums) {
        int k=0;//[0,k)都是非0元素
        for(int num:nums){
            if(num!=0){
                nums[k++]=num;
            }
        }
        for(int i=k;i<nums.length;i++){
            nums[i]=0;
        }
    }

    /**
     * 思路三：
     * 1、引入另外一个指针k,用于指向数组中非0元素（原有一个遍历数组的指针i），很显然k <= nums.length-1
     * 2、[0,k)中元素是非0元素，i指向非0元素，就与k指向的元素交换，这样保证元素的相对顺序
     */
    //时间复杂度：O(n)
    //空间复杂度：O(1)
    public void moveZeroes(int[] nums) {
        int k=0;//[0,k)都是非0元素
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=0){
                if(i!=k){
                    swap(nums,k++,i);
                }else{
                    k++;
                }
            }
        }
    }

    public void swap(int[] nums,int i,int j){
        int tmp=nums[i];
        nums[i]=nums[j];
        nums[j]=tmp;
    }
}
