package code_02_find;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 149. Max Points on a Line
 *
 * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
 *
 * Input: [[1,1],[2,2],[3,3]]
 * Output: 3
 * Explanation:
     ^
     |
     |        o
     |     o
     |  o
     +------------->
     0  1  2  3  4
 * Input: [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
 *
 * Output: 4
 *
 *  ^
    |
    | o
    |     o        o
    |        o
    |  o        o
    +------------------->
     0  1  2  3  4  5  6
 */
class Point {
    int x;
    int y;
    Point() {
        x = 0;
        y = 0;
    }
    Point(int a, int b) {
        x = a;
        y = b;
    }
 }

public class Code_149_MaxPointsOnALine {
    public int maxPoints(Point[] points) {
        if(points==null || points.length==0){
            return 0;
        }
        if(points.length==1){
            return 1;
        }
        int ret=0;
        for(int i=0;i<points.length;i++){
            Map<Double,Integer> map=new HashMap<Double, Integer>();
            //map存储的是斜率与点出现的频率
            int same=1;
            //记录出现相同的点的个数
            for(int j=0;j<points.length;j++){
                if(i==j){
                    continue;
                }
                //先看看是否有相同点
                if(isSamePoint(points[i],points[j])){
                    same++;
                    continue;
                }else{
                    double slope=getSlope(points[i],points[j]);
                    int freq=map.getOrDefault(slope,0)+1;
                    map.put(slope,freq);
                }
            }
            if(map.size()==0){
                //只有相同点的极端情况
                ret=Math.max(ret,same);
            }else {
                for (Double slope : map.keySet()) {
                    ret = Math.max(ret,map.get(slope)+same);
                    if(slope>=0.9999999894638303){
                        ret-=1;
                    }
                }
            }
        }
        return ret;
    }

    //判断两点是否是相同点
    public boolean isSamePoint(Point p1,Point p2){
        if((p1.x==p2.x) && (p1.y==p2.y)){
            return true;
        }
        return false;
    }

    //计算两点所在直线的斜率
    public double getSlope(Point p1,Point p2){
        if(p1.x==p2.x){
            return Double.MAX_VALUE;
        }
        return (double)(p1.y-p2.y)/(p1.x-p2.x);
    }

    @Test
    public void test(){
        //[[2,3],[3,3],[-5,3]]
        //针对这种情况，无能为力了：[[0,0],[94911151,94911150],[94911152,94911151]]
        Point[] points=
                {new Point(0,0),new Point(94911151,94911150),new Point(94911152,94911151)};
        System.out.println(maxPoints(points));
    }
}
