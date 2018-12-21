package code_01_array;

import org.junit.Test;

import java.util.*;

/**
 * 56. Merge Intervals
 *
 * Given a collection of intervals, merge all overlapping(重叠) intervals.
 *
 * Example 1:
 Input: [[1,3],[2,6],[8,10],[15,18]]
 Output: [[1,6],[8,10],[15,18]]
 Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].

 * Example 2:
 Input: [[1,4],[4,5]]
 Output: [[1,5]]
 Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 */
public class Code_56_MergeIntervals {
    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> res=new ArrayList<>();
        if(intervals.size()==0){
            return res;
        }
        //按照区间的开始值进行排序
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                int num=o1.start-o2.start;
                int num2=(num==0)?o1.end-o2.end:num;
                return num2;
            }
        });

        int start=intervals.get(0).start;
        int end=intervals.get(0).end;
        for(int i=1;i<intervals.size();i++){
            int nextStart=intervals.get(i).start;
            int nextEnd=intervals.get(i).end;
            if(nextStart<=end){
                end=Math.max(nextEnd,end);
            }else{
                res.add(new Interval(start,end));
                start=nextStart;
                end=nextEnd;
            }
        }
        res.add(new Interval(start,end));
        return res;
    }

    @Test
    public void test(){
        List<Interval> list=new ArrayList<>();
        Interval i1=new Interval(1,3);
        Interval i2=new Interval(2,6);
        Interval i3=new Interval(8,10);
        Interval i4=new Interval(15,18);
        list.add(i1);
        list.add(i2);
        list.add(i3);
        list.add(i4);
        List<Interval> res=merge(list);
        for(Interval interval:res){
            System.out.println("["+interval.start+","+interval.end+"]");
        }
    }
}
