package code_01_array;

import org.junit.Test;

/**
 * Given n non-negative integers a1, a2, ..., an ,
 * where each represents a point at coordinate (i, ai).
 * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
 * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
 *
 * Note: You may not slant the container and n is at least 2.
 *
 * Example:

 * Input: [1,8,6,2,5,4,8,3,7]
 * Output: 49
 */
public class Code_11_ContainerWithMostWater {
    public int maxArea1(int[] height) {
        int mostWater=0;
        for(int i=0;i<height.length;i++){
            for(int j=i+1;j<height.length;j++){
                int h=Math.min(height[i],height[j]);
                if((j-i)*h>mostWater){
                    mostWater=(j-i)*h;
                }
            }
        }
        return mostWater;
    }

    /**
     *  对撞指针
     *  假设有左指针和右指针，且左指针指向的值小于右指针的值。
     *  假如我们将右指针左移，则右指针左移后的值和左指针指向的值相比有三种情况：
     *  （1）右指针指向的值大于左指针：这种情况下，容器的高取决于左指针，但是底变短了，所以容器盛水量一定变小
     *  （2）右指针指向的值等于左指针：这种情况下，容器的高取决于左指针，但是底变短了，所以容器盛水量一定变小
     *  （3）右指针指向的值小于左指针：这种情况下，容器的高取决于右指针，但是右指针小于左指针，且底也变短了，所以容量盛水量一定变小了
     *  反之，情况类似。
     *  综上所述，容器高度较大的一侧的移动只会造成容器盛水量减小。
     *  所以应当移动高度较小一侧的指针，并继续遍历，直至两指针相遇。
     */
    public int maxArea(int[] height) {
        int i=0;
        int j=height.length-1;
        int mostWater=0;
        while(i<j){
            int h=Math.min(height[i],height[j]);
            if((j-i)*h>mostWater){
                mostWater=(j-i)*h;
            }
            if(height[i]<height[j]){
                i++;
            }else{
                j--;
            }
        }
        return mostWater;
    }

    @Test
    public void test(){
        int[] height={1,8,6,2,5,4,8,3,7};
        System.out.println(maxArea(height));
    }
}
