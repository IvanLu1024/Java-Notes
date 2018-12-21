package code_01_array;

import org.junit.Test;

/**
 *
 * 875. Koko Eating Bananas
 *
 * Koko loves to eat bananas.  There are N piles of bananas, the i-th pile has piles[i] bananas.
 * The guards have gone and will come back in H hours.
 *
 * Koko can decide her bananas-per-hour eating speed of K.
 * Each hour, she chooses some pile of bananas, and eats K bananas from that pile.
 * If the pile has less than K bananas, she eats all of them instead, and won't eat any more bananas during this hour.
 * Koko likes to eat slowly, but still wants to finish eating all the bananas before the guards come back.
 * Return the minimum integer K such that she can eat all the bananas within H hours.
 *
 * Example 1:
 Input: piles = [3,6,7,11], H = 8
 Output: 4

 * Example 2:
 Input: piles = [30,11,23,4,20], H = 5
 Output: 30

 * Example 3:
 Input: piles = [30,11,23,4,20], H = 6
 Output: 23

 Note:
 1 <= piles.length <= 10^4
 piles.length <= H <= 10^9
 1 <= piles[i] <= 10^9
 */
public class Code_875_KokoEatingBananas {
    public int minEatingSpeed(int[] piles, int H) {
        if (piles.length > H ) {
            return -1;
        }
        //maxSpeed KOKO吃香蕉的最快速度
        int maxSpeed=piles[0];
        for(int i=0;i<piles.length;i++){
            maxSpeed=Math.max(maxSpeed,piles[i]);
        }
        //KOKO吃香蕉的速度在[1,maxSpeed]之间
        int l=1;
        int h=maxSpeed;
        while(l<=h){
            int mid=l+(h-l)/2;
            int hours=hours(piles,mid);
            if(hours==H){
                return mid;
            }else if(hours<H){
                //hours<H说明吃的快了,速度要降下来
                h=mid-1;
            }else{
                //hours>H说明吃的慢了，速度要快起来
                l=mid+1;
            }
        }
        return l;
    }

    //以spped速度吃香蕉所花费的时间
    private int hours(int[] piles,int speed){
        int time=0;
        for(int pile:piles){
            if(pile%speed==0){
                time+=pile/speed;
            }else{
                time+=(pile/speed+1);
            }
        }
        return time;
    }

    @Test
    public void test(){
        int[] piles={30,11,23,4,20};
        int H=6;
        System.out.println(minEatingSpeed(piles,H));
    }
}
