package code_02_find;

import java.util.*;

/**
 * 855. Exam Room
 *
 * In an exam room, there are N seats in a single row, numbered 0, 1, 2, ..., N-1.
 *
 * When a student enters the room,
 * they must sit in the seat that maximizes the distance to the closest person.
 * If there are multiple such seats, they sit in the seat with the lowest number.
 * (Also, if no one is in the room, then the student sits at seat number 0.)
 *
 * Return a class ExamRoom(int N) that exposes two functions:
 * ExamRoom.seat() returning an int representing what seat the student sat in,
 * and ExamRoom.leave(int p) representing that the student in seat number p now leaves the room.
 * It is guaranteed that any calls to ExamRoom.leave(p) have a student sitting in seat p.
 * (请确保每次调用 ExamRoom.leave(p) 时都有学生坐在座位 p 上。)
 *
 * Example 1:
 Input: ["ExamRoom","seat","seat","seat","seat","leave","seat"], [[10],[],[],[],[],[4],[]]
 Output: [null,0,9,4,2,null,5]
 Explanation:
 ExamRoom(10) -> null
 seat() -> 0, no one is in the room, then the student sits at seat number 0.
 seat() -> 9, the student sits at the last seat number 9.
 seat() -> 4, the student sits at the last seat number 4.
 seat() -> 2, the student sits at the last seat number 2.
 leave(4) -> null
 seat() -> 5, the student sits at the last seat number 5.

 * Note:
 1 <= N <= 10^9
 ExamRoom.seat() and ExamRoom.leave() will be called at most 10^4 times across all test cases.
 Calls to ExamRoom.leave(p) are guaranteed to have a student currently sitting in seat number p.
 */
public class Code_855_ExamRoom {
    /**
     * 思路：
     * 题目要求明确，新建一个容量为N的考试房间，每次向里放人或者向外赶人，
     * 放人要求必须放在离其他人最远的位置，这个数据结构的放置范围在1∼10~9，
     * 不能使用数组，可以使用List代替数组，存储学生座位位置
     * 每次先检查0和最后一个位置是否坐了人，毕竟这两个位置只需要考虑一边，
     * 而其它位置的的选取则必然是在两个座位中间选取。
     * maxDis变量记录最长的距离，
     * idx记录上一个同学的位置，遍历即可
     * 删除根据下标进行删除。
     */
    class ExamRoom {
        //存储学生座位位置
        private List<Integer> seats;
        //座位总数
        private int n;

        public ExamRoom(int N) {
           seats=new ArrayList<>();
           n=N;
        }

        public int seat() {
            //一开始没有人，第一个人直接坐到0位置
            if(seats.isEmpty()){
                seats.add(0);
                return 0;
            }
            //res就是要坐的位置
            int res = 0;
            //idx记录上一个同学的位置
            int idx = 0;
            int maxDis = 0;
            //0位置有人
            if(seats.contains(0)){
                maxDis=seats.get(0)-0;
                res=0;
            }
            for(int i=0;i<seats.size();i++){
                int dis=(seats.get(i)-idx)/2;
                if(dis>maxDis){
                    maxDis=dis;
                    res=(seats.get(i)+idx)/2;
                }
                idx=seats.get(i);
            }
            //最后一个位置是否还有人
            if(seats.contains(n-1)){
                int dis = n - 1 - seats.get(seats.size()-1);
                if (dis > maxDis) {
                    maxDis=dis;
                    res = n - 1;
                }
            }
            return res;
        }

        public void leave(int p) {
            for(int i=0;i<seats.size();i++){
                if(seats.get(i)==p){
                    seats.remove(i);
                }
            }
        }
    }
}
