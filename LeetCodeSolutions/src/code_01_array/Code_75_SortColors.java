package code_01_array;

import org.junit.Test;

/**
 * 75 Sort Colors

 * Given an array with n objects colored red, white or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white and blue.
 * Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
 * Note: You are not suppose to use the library's sort function for this problem.
 *
 * Example:
 * Input: [2,0,2,1,1,0]
 * Output: [0,0,1,1,2,2]
 */
public class Code_75_SortColors {
    /**
     * 思路一：
     分别统计0,1,2元素个数，然后再对数组重新赋值
     时间复杂度：O(n)
     空间复杂度：O(k),k是nums元素的最大值
     * @param nums
     */
    public void sortColors1(int[] nums) {
        int[] count=new int[3];
        //存放0,1,2元素的频率
        for(int i=0;i<nums.length;i++){
            count[nums[i]]++;
        }
        int index=0;
        for(int i=0;i<count[0];i++){
            nums[index++]=0;
        }
        for(int i=0;i<count[1];i++){
            nums[index++]=1;
        }
        for(int i=0;i<count[2];i++){
            nums[index++]=2;
        }
    }

    /**
     * 思路二：
     * zero指针指向0元素,two指针指向2元素，i是遍历数组的指针,
     这里有nums[0..zero]==0,nums[zero+1..i+1]==1,nums[two..n-1]==2:

     * i指向值为0的元素，swap(i,zero+1) ,交换后，i位置元素是1(nums[zero+1..i+1]==1)，不需要处理，i直接+1
     * i指向值为1的元素，不需要处理直接+1
     * i指向值为2的元素，swap(i,two-1),交换后，two-1位置元素就是2
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param nums
     */
    public void sortColors(int[] nums){
        int zero=-1;
        //nums[0,zero]==0,开始并没有元素，所以取zero=-1,[0,-1]显然不成立
        int two=nums.length;
        //nums[two,n-1]==2,开始并没有元素，所以取two=n,[n,n-1]显然不成立
        for(int i=0;i<two;){
            if(nums[i]==0){
                zero++;
                swap(nums,zero,i);
                i++;
            }else if(nums[i]==2){
                two--;
                swap(nums,two,i);
            }else{
                i++;
            }
        }
    }

    public void swap(int[] nums,int i,int j){
        int tmp=nums[i];
        nums[i]=nums[j];
        nums[j]=tmp;
    }

    @Test
    public void test(){
        int[] nums={1,1,1,2,2,2,2,0,0,0};
        sortColors(nums);

        for(int i=0;i<nums.length;i++){
            System.out.println(nums[i]);
        }
    }
}
