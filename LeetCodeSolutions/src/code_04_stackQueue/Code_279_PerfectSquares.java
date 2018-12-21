package code_04_stackQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 279. Perfect Squares
 *
 * Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.
 *
 * Example 1:
 * Input: n = 12
 * Output: 3
 * Explanation: 12 = 4 + 4 + 4.
 *
 * Example 2:
 * Input: n = 13
 * Output: 2
 * Explanation: 13 = 4 + 9.
 */
public class Code_279_PerfectSquares {
    private class Pair{
        int num;
        //记录节点的数值
        int steps;
        //记录num数值该走几步
        Pair(int num,int steps){
            this.num=num;
            this.steps=steps;
        }
    }

    public int numSquares(int n) {
        Queue<Pair> queue=new LinkedList<>();
        queue.add(new Pair(n,0));

        boolean[] isVisited=new boolean[n+1];
        isVisited[n]=true;

        while(!queue.isEmpty()){
            Pair pair=queue.poll();
            int num=pair.num;
            int steps=pair.steps;
            for(int i=1;;i++){
                int tmp=num-i*i;
                if(tmp<0){
                    break;
                }
                if(tmp==0){
                    return steps+1;
                }
                if(!isVisited[tmp]){
                    queue.add(new Pair(tmp,steps+1));
                    isVisited[tmp]=true;
                }
            }
        }
        //如果结果不存在，就返回0
        return 0;
    }
}
