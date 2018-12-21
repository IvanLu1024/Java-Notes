package code_04_stackQueue;

import org.junit.Test;

import java.util.Stack;

/**
 *735. Asteroid Collision
 *
 * We are given an array asteroids(小行星) of integers representing asteroids in a row.
 * For each asteroid, the absolute value represents its size,
 * and the sign represents its direction (positive meaning right, negative meaning left).
 * Each asteroid moves at the same speed.
 *
 * Find out the state of the asteroids after all collisions.
 * If two asteroids meet, the smaller one will explode.
 * If both are the same size, both will explode.
 * Two asteroids moving in the same direction will never meet.
 *
 * Example 1:
 Input:
 asteroids = [5, 10, -5]
 Output: [5, 10]
 Explanation:
 The 10 and -5 collide resulting in 10.  The 5 and 10 never collide.

 * Example 2:
 Input:
 asteroids = [8, -8]
 Output: []
 Explanation:
 The 8 and -8 collide exploding each other.

 * Example 3:
 Input:
 asteroids = [10, 2, -5]
 Output: [10]
 Explanation:
 The 2 and -5 collide resulting in -5.  The 10 and -5 collide resulting in 10.

 * Example 4:
 Input:
 asteroids = [-2, -1, 1, 2]
 Output: [-2, -1, 1, 2]
 Explanation:
 The -2 and -1 are moving left, while the 1 and 2 are moving right.
 Asteroids moving the same direction never meet, so no asteroids will meet each other.

 * Note:
 The length of asteroids will be at most 10000.
 Each asteroid will be a non-zero integer in the range [-1000, 1000]..
 */
public class Code_735_AsteroidCollision {
    /**
     * 思路：
     * 利用stack存储，每次对比栈顶元素（相同的话就放入栈中），否则就对比，直至栈顶相同为止
     * 注意：当栈顶为负数时，直接进栈。
     * 只有当栈顶为正数才需要发生碰撞
     */
    public int[] asteroidCollision(int[] asteroids) {
        if(asteroids==null || asteroids.length==0){
            return new int[]{};
        }
        if(asteroids.length==1){
            return new int[]{asteroids[0]};
        }
        Stack<Integer> stack=new Stack<>();
        for(int i=0;i<asteroids.length;i++){
            //栈为空或者入栈元素 > 0则直接加入栈中
            if(stack.isEmpty() || asteroids[i]>0){
                stack.push(asteroids[i]);
                continue;
            }
            //处理 asteroids[i]<0的情况
            while(true){
                //获取栈顶元素
                int top=stack.peek();
                //栈顶元素<0,那么就不会碰撞，直接入栈即可
                if(top<0){
                    stack.push(asteroids[i]);
                    break;
                }
                //栈顶元素>0的情况
                if(top==-asteroids[i]){
                    //相等，则碰撞后，两颗行星都会消失：asteroids[i]不能插入，栈顶元素出栈
                    stack.pop();
                    break;
                }else if(top>-asteroids[i]){
                    //相撞后，top方向质量更大：asteroids[i]不能插入
                    break;
                }else{
                    //相撞后，asteroids[i]方向质量更大：将top出栈，将asteroids[i]插入
                    stack.pop();
                    if(stack.isEmpty()){
                        //栈为空说明asteroids[i]是质量最大的
                        stack.push(asteroids[i]);
                        break;
                    }
                }
            }
        }

        int [] res=new int[stack.size()];
        int i=stack.size()-1;
        while(!stack.empty()){
            res[i--]=stack.pop();
        }
        return res;
    }

    @Test
    public void test(){
        //int[] as={-2, -1, 1, 2};
        int[] as={10, 2, -5};
        int[] res=asteroidCollision(as);
        for(int i:res){
            System.out.println(i);
        }
    }
}
