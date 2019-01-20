package code_10_math;

import java.util.Random;

/**
 * Given the radius and x-y positions of the center of a circle,
 * write a function randPoint which generates a uniform random point in the circle.

 Note:
 input and output values are in floating-point.
 radius and x-y position of the center of the circle is passed into the class constructor.
 a point on the circumference of the circle is considered to be in the circle.
 randPoint returns a size 2 array containing x-position and y-position of the random point, in that order.
 */
public class Code_478_GenerateRandomPointInACircle {
    class Solution {
        private double raduis;
        private double x;
        private double y;
        private Random random;

        public Solution(double radius, double x_center, double y_center) {
            this.raduis=radius;
            this.x=x_center;
            this.y=y_center;
            random=new Random();
        }

        public double[] randPoint() {
            //产生random.nextDouble()[0.0,1.0)随机数据
            //x产生[x_center-radius,x_center+radius)
            double randx, randy;
            do{
                randx = x - raduis + randDouble(0, 2 * raduis);
                randy = y-raduis + randDouble(0, 2 * raduis);
            }while (!inCircle(randx,randy));
            return new double[]{randx,randy};
        }

        //[minDouble,maxDopuble]之间的随机数
        private double randDouble(double minDouble, double maxDouble){
            double randNum = (double) random.nextInt(Integer.MAX_VALUE) / Integer.MAX_VALUE; //近似[0.0,1.0]之间的随机数
            return randNum * (maxDouble - minDouble) + minDouble; //近似[minDouble,maxDouble]之间的随机数
        }

        //判断(x0,y0)是否在圆内
        private boolean inCircle(double x0, double y0){
            return (x - x0) * (x - x0) + (y - y0) * (y - y0) <= raduis * raduis;
        }
    }
}
