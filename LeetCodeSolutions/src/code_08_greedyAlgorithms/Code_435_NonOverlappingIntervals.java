package code_08_greedyAlgorithms;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 435. Non-overlapping Intervals
 *
 * Given a collection of intervals,
 * find the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.
 *
 * Note:
 * You may assume the interval's end point is always bigger than its start point.
 * Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.

 * Example 1:
 Input: [ [1,2], [2,3], [3,4], [1,3] ]
 Output: 1
 Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
 */
public class Code_435_NonOverlappingIntervals {
    public int eraseOverlapIntervals1(Interval[] intervals) {
        int n = intervals.length;
        if (n == 0) {
            return 0;
        }
        Arrays.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                //按照开始时间升序排序
                int num = o1.start - o2.start;
                int num2 = (num == 0) ? o1.end - o2.end : num;
                return num2;
            }
        });
        int[] memo = new int[n];
        for (int i = 0; i < n; i++) {
            memo[i] = 1;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (intervals[i].start >= intervals[j].end) {
                    memo[i] = Math.max(memo[i], 1 + memo[j]);
                }
            }
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, memo[i]);
        }
        return n - res;
    }

    public int eraseOverlapIntervals(Interval[] intervals) {
        int n = intervals.length;
        if (n == 0) {
            return 0;
        }
        Arrays.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                int num=o1.end-o2.end;
                int num2=(num==0)?o1.start-o2.start:num;
                return num2;
            }
        });

        int res=1;
        int pre=0;
        for(int i=1;i<n;i++){
            if(intervals[i].start>=intervals[pre].end){
                res++;
                pre=i;
            }
        }
        return n-res;
    }
}
