package code_08_greedyAlgorithms;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 452. Minimum Number of Arrows to Burst Balloons
 *
 *
 * There are a number of spherical(球形的) balloons spread in two-dimensional space.
 * For each balloon, provided input is the start and end coordinates(坐标系) of the horizontal diameter（水平直径）.
 * Since it's horizontal, y-coordinates don't matter and hence the x-coordinates of start and end of the diameter suffice.
 * Start is always smaller than end. There will be at most 104 balloons.

 * An arrow can be shot up exactly vertically from different points along the x-axis.
 * TODO:A balloon with xstart and xend bursts by an arrow shot at x if xstart ≤ x ≤ xend.
 * There is no limit to the number of arrows that can be shot.
 * An arrow once shot keeps travelling up infinitely(无限制的).
 * The problem is to find the minimum number of arrows that must be shot to burst all balloons.
 */
public class Code_452_MinimumNumberofArrowstoBurstBalloons {
    /**
     * 思路：贪心策略的区间选点问题
     */
    public int findMinArrowShots(int[][] points) {
        // N 记录的是气球数
        int N = points.length;
        if(N==0){
            return 0;
        }
        Interval[] bolloons = new Interval[N];
        for(int i=0;i<N;i++){
            int start = points[i][0];
            int end = points[i][1];
            bolloons[i] = new Interval(start,end);
        }

        //按照 end 进行升序排列
        //再按照 start 进行降序排列
        Arrays.sort(bolloons, new Comparator<Interval>() {
            @Override
            public int compare(Interval interval1, Interval interval2) {
                int num = interval1.end - interval2.end;
                int num2 = (num == 0) ? interval2.start - interval2.start : num ;
                return num2;
            }
        });

        int res = 1;
        // j 用来记录结果区间编号，第一个区间必然是结果区间
        int j = 0;
        for(int i=1;i<bolloons.length;i++){
            // bolloons[j].end 在区间之间 --> 则 bolloons[j].end任然作为这些区间的公共点
            if(bolloons[j].end >= bolloons[i].start && bolloons[j].end <= bolloons[i].end){
                continue;
            }else{
                res ++;
                j = i;
            }
        }
        return res;
    }

    @Test
    public void test(){
        int[][] points={
                {10,16},
                {2,8},
                {1,6},
                {7,12}
        };
        System.out.println(findMinArrowShots(points));
    }
}
