package code_10_math;

/**
 * 858. Mirror Reflection
 *
 * There is a special square room with mirrors on each of the four walls.
 * Except for the southwest corner, there are receptors(接收器)
 * on each of the remaining corners, numbered 0, 1, and 2.
 *
 * The square room has walls of length p,
 * and a laser ray from the southwest corner first meets the east wall at a distance q from the 0th receptor(接收器).
 * Return the number of the receptor that the ray meets first.
 * (It is guaranteed that the ray will meet a receptor eventually.)
 * (返回接收器的编号)
 */
public class Code_858_MirrorReflection {
    /**
     * 思路：
     * 如果没有上下两面镜子，光线会一直向上反射，这两面镜子的作用仅仅是改变了光线的走向而已。
     * TODO:同时，可以通过光线走过的纵向距离来判断光线是否到达接收器，
     * 如果此距离是p的偶数倍，那么光线到达上面的接收器，即接收器0；
     * 使用变量count记录光线与左右镜子接触的次数，
     * 同上，可根据count来判断光线到达接收器1和2。
     * 当距离为p的奇数倍
     * 如果count为奇数,则到达接收器1；
     * 如果count为偶数,则到达接收器2。
     */
    public int mirrorReflection(int p, int q) {
        //记录光线与左右镜子接触的次数
        int count = 0;
        //记录光线走过的纵向的距离
        int dist = 0;

        while(true){
            count ++;
            dist += q;

            //余数，用于判断 dist 是否是 p 的偶数倍
            int remain = dist % (2*p);
            if(remain == p){
                // dist 是 p 的奇数倍
                if(count % 2 == 1){
                    return 1;
                }else {
                    return 2;
                }
            }
            if( remain == 0){
                // dist 是 p 的偶数倍
                return 0;
            }
        }
    }
}
