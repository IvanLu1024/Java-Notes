package code_01_array;

import org.junit.Test;

import java.util.Arrays;

/**
 * 853. Car Fleet
 *
 * N cars are going to the same destination along a one lane road.
 * The destination is target miles away.
 * Each car i has a constant speed speed[i] (in miles per hour),
 * and initial position position[i] miles towards the target along the road.
 *
 * A car can never pass another car ahead of it, but it can catch up to it, and drive bumper to bumper at the same speed.
 * The distance between these two cars is ignored - they are assumed to have the same position.

 * A car fleet is some non-empty set of cars driving at the same position and same speed.
 * Note that a single car is also a car fleet.
 * If a car catches up to a car fleet right at the destination point, it will still be considered as one car fleet.


 * How many car fleets will arrive at the destination?
 *
 * Example 1:
 Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
 Output: 3
 Explanation:
 The cars starting at 10 and 8 become a fleet, meeting each other at 12.
 The car starting at 0 doesn't catch up to any other car, so it is a fleet by itself.
 The cars starting at 5 and 3 become a fleet, meeting each other at 6.
 Note that no other cars meet these fleets before the destination, so the answer is 3.
 */
public class Code_853_CarFleet {
    private class Car implements Comparable<Car>{
        int position;
        int speed;
        //到达终点所用的时间
        double time;

        public Car(int position,int speed,double time){
            this.position=position;
            this.speed=speed;
            this.time=time;
        }

        //按照起始位置进行降序排列
        @Override
        public int compareTo(Car car) {
            return car.position-this.position;
        }
    }

    /**
     * 思路：
     * 按照 position位置对车进行排序（降序）
     * 两辆车相撞的条件就是一辆车在前面并且到达结束位置的时在前的车用时较长。
     */
    public int carFleet(int target, int[] position, int[] speed) {
        if(position.length==0){
            return 0;
        }
        Car[] cars=new Car[position.length];
        for(int i=0;i<position.length;i++){
            cars[i]=new Car(position[i],speed[i],1.0*(target-position[i])/speed[i]);
        }
        //对这些车按照起始位置进行排序
        Arrays.sort(cars);
        int res = 1;
        for(int i = 1 ; i < cars.length ; i ++){
            if(cars[i].time <= cars[i-1].time){
                cars[i].time = cars[i-1].time;
            }else{
                //后一辆车（离目的地更远）到达目的地所用的时间 > 前一辆车到达目的地所用的时间,这两辆车是不同车队的。
                res ++;
            }

        }
        return res;
    }

    @Test
    public void test(){
        int target=12;
        int[] position = {10,8,0,5,3};
        int[] speed = {2,4,1,1,3};
        System.out.println(carFleet(target,position,speed));
    }
}
