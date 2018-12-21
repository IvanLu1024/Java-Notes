package code_04_stackQueue;

import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 752. Open the Lock
 *
 * You have a lock in front of you with 4 circular wheels.
 * Each wheel has 10 slots: '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'.
 * The wheels can rotate freely and wrap around:
 * for example we can turn '9' to be '0', or '0' to be '9'. Each move consists of turning one wheel one slot.
 * The lock initially starts at '0000', a string representing the state of the 4 wheels.
 *
 * You are given a list of deadends dead ends,
 * meaning if the lock displays any of these codes, the wheels of the lock will stop turning and you will be unable to open it.
 * Given a target representing the value of the wheels that will unlock the lock,
 * return the minimum total number of turns required to open the lock, or -1 if it is impossible.
 *
 * Example 1:
 Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
 Output: 6
 Explanation:
 A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
 Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
 because the wheels of the lock become stuck after the display becomes the dead end "0102".

 * Example 2:
 Input: deadends = ["8888"], target = "0009"
 Output: 1
 Explanation:
 We can turn the last wheel in reverse to move from "0000" -> "0009".

*  Example 3:
 Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
 Output: -1
 Explanation:
 We can't reach the target without getting stuck.
 */
public class Code_752_OpenTheLock {
    /**
     * 思路一：暴力求解，但是超时了
     */
    public int openLock(String[] deadends, String target) {
        Set<String> deadSet=new HashSet<>();
        for(String deadend:deadends){
            deadSet.add(deadend);
        }

        if(deadSet.contains(target) || deadSet.contains("0000")){
            return -1;
        }

        Set<String> visited=new HashSet<>();
        Queue<Pair> q=new LinkedList<>();
        q.add(new Pair("0000",0));
        visited.add("0000");
        while(!q.isEmpty()){
            Pair p=q.poll();
            String curS=p.s;
            int curTurn=p.turn;
            Set<String> next=getNextLock(curS,deadSet);
            for(String lock:next){
                if(!visited.contains(lock)){
                    if(target.equals(lock)){
                        return curTurn+1;
                    }
                    visited.add(lock);
                    q.add(new Pair(lock,curTurn+1));
                }
            }
        }
        return -1;
    }

    private class Pair{
        String s;
        int turn;
        Pair(String s,int turn){
            this.s=s;
            this.turn=turn;
        }
    }

    //锁的密码是s时，获取下一个有可能的锁密码
    private Set<String> getNextLock(String s,Set<String> deadends){
        Set<String> res=new HashSet<>();
        assert s.length()==4;
        for(int i=0;i<4;i++){
            int num = s.charAt(i)- '0';
            int d = num + 1;
            if(d > 9) d = 0;
            String t=s.substring(0,i)+((char) (d+'0'))+s.substring(i+1,4);
            if(!deadends.contains(t)){
                res.add(t);
            }
            d=num-1;
            if(d < 0) d = 9;
            t=s.substring(0,i)+((char) (d+'0'))+s.substring(i+1,4);
            System.out.println(t);
            if(!deadends.contains(t)){
                res.add(t);
            }
        }
        return res;
    }

    @Test
    public void test(){
        String[] arr={"0201","0101","0102","1212","2002"};
        String target="0202";
        System.out.println(openLock(arr,target));

        String s="0000";
        int d=1;
        String t=s.substring(0,0)+((char) (d+'0'))+s.substring(1,4);
        System.out.println(t);
    }
}
