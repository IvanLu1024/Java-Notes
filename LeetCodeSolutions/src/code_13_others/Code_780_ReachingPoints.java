package code_13_others;

/**
 * 780. Reaching Points
 *
 * A move consists of taking a point (x, y) and transforming it to either (x, x+y) or (x+y, y).
 * Given a starting point (sx, sy) and a target point (tx, ty),
 * return True if and only if a sequence of moves exists to transform
 * the point (sx, sy) to (tx, ty). Otherwise, return False.

 * Example1:
 Input: sx = 1, sy = 1, tx = 3, ty = 5
 Output: True
 Explanation:
 One series of moves that transforms the starting point to the target is:
 (1, 1) -> (1, 2)
 (1, 2) -> (3, 2)
 (3, 2) -> (3, 5)

 * Example2:
 Input: sx = 1, sy = 1, tx = 2, ty = 2
 Output: False

 * Example3:
 Input: sx = 1, sy = 1, tx = 1, ty = 1
 Output: True
 */
public class Code_780_ReachingPoints {
    /**
     * 思路：
     * (sx,sy) 能否通过 (sx+sy,sy) 或者(sx,sx+sy)变到(tx,ty)。
     * 变化的方式只有加法，数据范围为[1,10^9]。
     * 所以,从后往前递推，当tx>ty的时候，(sx,sy)只可能是由 (tx-ty,ty)变来的。注意处理一下 相等的情况。
     */
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        //一开始二者就相等
        if(sx == tx && sy == ty){
            return true;
        }
        // (sx,sy) --> (tx,ty)
        while(true){
            //经过一系列的(sx+sy,sy) 或者(sx,sx+sy)操作后，得到(tx,ty)
            if(sx == tx && sy == ty){
                break;
            }
            if(sx > tx || sy > ty){
                return false;
            }
            if(sx == tx){
                //这时候要关注 sx 的变化 ty-sy 的差值(前面的判断保证了sy<= ty )是否是 sx 的整数倍
                if( (ty-sy) % sx ==0){
                    return true;
                }else{
                    return false;
                }
            }
            if(sy == ty){
                //这时候要关注 sy 的变化 tx-sx 的差值(前面的判断保证了sx <= tx )是否是 sy 的整数倍
                if( (tx - sx) % sy ==0){
                    return true;
                }else{
                    return false;
                }
            }
            if(tx!=ty) {
                if(tx>ty){
                    //当tx > ty的时候，(sx,sy)只可能是由 (tx - ty,ty)得到
                    tx-=ty;
                }else{
                    //当 tx < ty的时候，(sx,sy)只可能是由 (tx,ty - tx)得到
                    ty-=tx;
                }
            } else {
                // tx == ty ,直接返回 false
                return false;
            }
        }
        return true;
    }
}
