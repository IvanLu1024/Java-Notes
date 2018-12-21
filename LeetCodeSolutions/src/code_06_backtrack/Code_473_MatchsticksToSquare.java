package code_06_backtrack;

import org.junit.Test;

/**
 * 473. Matchsticks to Square
 *
 * Remember the story of Little Match Girl? By now, you know exactly what matchsticks the little match girl has,
 * please find out a way you can make one square by using up all those matchsticks.
 * You should not break any stick, but you can link them up, and each matchstick must be used exactly one time.
 * Your input will be several matchsticks the girl has, represented with their stick length.
 * Your output will either be true or false,
 * to represent whether you could make one square using all the matchsticks the little match girl has.
 * （是否能用所有的火柴拼成正方形）
 *
 * Example 1:
 Input: [1,1,2,2,2]
 Output: true
 Explanation: You can form a square with length 2, one side of the square came two sticks with length 1.

 * Example 2:
 Input: [3,3,3,3,4]
 Output: false
 Explanation: You cannot find a way to form a square with all the matchsticks.

 * Note:
 The length sum of the given matchsticks is in the range of 0 to 10^9.
 The length of the given matchstick array will not exceed 15.
 */
public class Code_473_MatchsticksToSquare {
    /**
     * 思路：
     * 建立一个长度为4的数组sums来保四条的长度和，我们希望每条边都等于target=sum/4。
     * 所有火柴总长度必须是4的倍数，否则不能构成正方形。
     * 每根火柴必须用到，这里通过递归深度优先的方法逐个遍历所有火柴，
     * 依次将其加入到四条边上之后进行下一层遍历，
     * 如果最终四条边长度都达到边长长度则返回true，否则遍历完所有情况后返回false。
     */
    public boolean makesquare(int[] nums) {
        if (nums == null || nums.length < 4) {
            return false;
        }
        int sum=0;
        for(int num:nums){
            sum+=num;
        }
        if(sum%4!=0){
            return false;
        }
        int[] sums=new int[4];
        int target=sum/4;
        return canPutMatchSticks(nums,sums,0,target);
    }

    /**
     * @param nums 火柴长度数组
     * @param sums 存储四条边的长度之和
     * @param step 每次放一根火柴棒的步数
     * @param target 目标正方形的边长
     */
    private boolean canPutMatchSticks1(int[] nums,int[] sums,int step,int target){
        if(step==nums.length){
            //这里在结束才作判断，效率是不高的，要想办法进行再深度遍历的时候进行剪枝
            if(sums[0]==target && sums[1]==target &&
                    sums[2]==target && sums[3]==target){
                return true;
            }else{
                return false;
            }
        }
        for(int i=0;i<4;i++){
            //sum[i]表示该正方形某一边的长度 nums[step]第step步放入火柴
            //sums[i]+nums[step] 就表示在step步将火柴放入长度为sum[i]的边上
            if(sums[i]+nums[step]>target) {
                continue;
            }
            sums[i]+=nums[step];
            if(canPutMatchSticks1(nums,sums,step+1,target)){
                return true;
            }
            sums[i]-=nums[step];
        }
        return false;
    }

    /**
     * @param nums 火柴长度数组
     * @param sums 存储四条边的长度之和
     * @param step 每次放一根火柴棒的步数
     * @param target 目标正方形的边长
     */
    private boolean canPutMatchSticks(int[] nums,int[] sums,int step,int target){
        if(step==nums.length){
            return true;
        }
        for(int i=0;i<4;i++){
            //sum[i]表示该正方形某一边的长度 nums[step]第step步放入火柴
            //sums[i]+nums[step] 就表示在step步将火柴放入长度为sum[i]的边上
            if(sums[i]+nums[step]>target) {
                continue;
            }
            if(step<4 && i>step){
                break;
            }
            sums[i]+=nums[step];
            if(canPutMatchSticks(nums,sums,step+1,target)){
                return true;
            }
            sums[i]-=nums[step];
        }
        return false;
    }

    @Test
    public void test(){
        int[] nums={3,3,3,3,3,3,2};
        makesquare(nums);
    }
}
