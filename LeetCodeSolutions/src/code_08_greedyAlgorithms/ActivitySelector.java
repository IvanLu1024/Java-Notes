package code_08_greedyAlgorithms;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 18351 on 2019/1/30.
 */
public class ActivitySelector {
    public List<Interval> select(Interval[] activities){
        //按照区间的bi,进行排序
        Arrays.sort(activities, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                int num = o1.end - o2.end;
                int num2 = (num==0)? o1.start - o2.start : num ;
                return num2;
            }
        });

        List<Interval> res = new ArrayList<>();
        //默认将第一个活动先安排
        res.add(activities[0]);

        //记录最近一次安排的活动
        int j = 0;
        for(int i=1;i<activities.length;i++){
            if(activities[j].end <= activities[i].start){
                res.add(activities[i]);
                j = i;
            }
        }
        return res;
    }

    @Test
    public void test(){
        Interval[] intervals =
                new Interval[]{
                new Interval(0,0),
                new Interval(1,4),
                new Interval(3,5),
                new Interval(0,6),
                new Interval(5,7),
                new Interval(3,8),
                new Interval(5,9),
                new Interval(6,10),
                new Interval(8,11),
                new Interval(8,12),
                new Interval(2,13),
                new Interval(12,14),
                };
        System.out.println(select(intervals));
    }
}
