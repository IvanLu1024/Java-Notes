package code_10_math;

import java.util.Random;

/**
 * 497. Random Point in Non-overlapping Rectangles
 * Given a list of non-overlapping axis-aligned rectangles rects, write a function pick
 * which randomly and uniformily picks an integer point in the space covered by the rectangles.
 *
 * Note:
 - An integer point is a point that has integer coordinates.
 - A point on the perimeter of a rectangle is included in the space covered by the rectangles.
 - ith rectangle = rects[i] = [x1,y1,x2,y2], where [x1, y1] are the integer coordinates of the bottom-left corner, and [x2, y2] are the integer coordinates of the top-right corner.
 - length and width of each rectangle does not exceed 2000.
 - 1 <= rects.length <= 100
 - pick return a point as an array of integer coordinates [p_x, p_y]
 - pick is called at most 10000 times.
 */
public class Code_497_RandomPointinNon_overlappingRectangles {
    /**
     * 思路：长方形不重叠，则同一长方形内部的整数点被选择的概率相同。
     * 而且并且长方形内部的点被选择的概率等于该长方形的面积。
     * 这里长方形的面积就是求长方形中整数点的个数即可，计算方式是(x2 - x1 + 1) * (y2 - y1 + 1)。
     *
     * 所以，先按照面积随机选择一个长方形，然后再在长方形中随机选择一个整数点。
     */
    class Solution {
        private int[][] rects;
        private int[] sum;
        //统计所有的长方形点数
        private int total;
        private Random random;

        public Solution(int[][] rects) {
            this.rects=rects;
            this.random=new Random();

            //统计各个长方形的"面积"
            int[] area=new int[rects.length];
            for(int i=0;i<rects.length;i++){
                int x1=rects[i][0];
                int y1=rects[i][1];
                int x2=rects[i][2];
                int y2=rects[i][3];
                area[i]= (x2 - x1 + 1) * (y2 - y1 + 1);
            }

            //sum中下标和长方形对应
            this.sum=new int[area.length];
            this.total=0;
            for(int i=0;i<area.length;i++){
                total += area[i];
                sum[i]=total;
            }
        }

        public int[] pick() {
            int[] rect = rects[pickRandomRec()];

            // x在 [rect[0],rect[2]]范围内
            int x = rect[0] + random.nextInt(rect[2] - rect[0] + 1);
            //y 在 [rect[1],rect[3]]范围内
            int y = rect[1] + random.nextInt(rect[3] - rect[1] + 1);
            return new int[]{x,y};
        }

        //随机选择一个长方形-->二分查找方式
        private int pickRandomRec(){
            //随机获取在[0,total)之间的一个点
            int target = random.nextInt(total);
            int i = 0, j = sum.length - 1;
            while (i < j) {
                int mid = (i + j) / 2;
                if (sum[mid] > target) {
                    j = mid;
                } else {
                    i = mid + 1;
                }
            }
            return i;
        }
    }
}
