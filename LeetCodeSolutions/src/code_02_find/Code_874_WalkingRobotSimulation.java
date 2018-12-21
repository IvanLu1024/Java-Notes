package code_02_find;

import java.util.HashSet;
import java.util.Set;

/**
 * 874. Walking Robot Simulation
 *
 * A robot on an infinite grid starts at point (0, 0) and faces north.
 * The robot can receive one of three possible types of commands:
 * -2: turn left 90 degrees
 * -1: turn right 90 degrees
 * 1 <= x <= 9: move forward x units
 * Some of the grid squares are obstacles.
 *
 * The i-th obstacle is at grid point (obstacles[i][0], obstacles[i][1])
 * If the robot would try to move onto them, the robot stays on the previous grid square instead
 * (but still continues following the rest of the route.)
 * Return the square of the maximum Euclidean distance(欧式距离) that the robot will be from the origin.
 *
 * Example 1:
 Input: commands = [4,-1,3], obstacles = []
 Output: 25
 Explanation: robot will go to (3, 4)

 * Example 2:
 Input: commands = [4,-1,4,-2,4], obstacles = [[2,4]]
 Output: 65
 Explanation: robot will be stuck at (1, 4) before turning left and going to (1, 8)
 */
public class Code_874_WalkingRobotSimulation {
    private int[][] d={{0,1},{1,0},{0,-1},{-1,0}};
    //表示机器人向上、右、下、左四个方向移动一个单元格

    public int robotSim(int[] commands, int[][] obstacles) {
        //set存储的是障碍物的下标
        Set<String> set=new HashSet<>();
        for(int i=0;i<obstacles.length;i++){
            set.add(obstacles[i][0]+","+obstacles[i][1]);
        }

        //direction表示一开始的时候机器人的方向，direction=0，表示机器人一开始向北
        int direction=0;
        //x,y是机器人所在位置的横、纵坐标
        int x=0,y=0;
        int max=0;//记录机器人 maximum Euclidean distance(欧式距离) that the robot will be from the origin.
        for(int command:commands){
            if(command==-1){
                //表示向右移动
                direction=(direction+1)%4;
            }else if(command==-2){
                //表示向左移动
                direction=(direction+4-1)%4;
            }else{
                //每次移动一步，移动command次,遇到障碍物，则移动至障碍物前一位停止
                for(int step=1;step<=command;step++){
                    //机器人移动后的新位置
                    int newX=x+d[direction][0];
                    int newY=y+d[direction][1];
                    if(set.contains(newX+","+newY)){
                        //新位置上有障碍物
                        break;
                    }
                    //更新机器人位置
                    x=newX;
                    y=newY;
                }
                max=Math.max(max,x*x+y*y);
            }
        }
        return max;
    }
}
