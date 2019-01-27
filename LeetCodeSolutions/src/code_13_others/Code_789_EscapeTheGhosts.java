package code_13_others;

/**
 * 789. Escape The Ghosts
 *
 * You are playing a simplified Pacman game.
 * You start at the point (0, 0), and your destination is (target[0], target[1]).
 * There are several ghosts on the map, the i-th ghost starts at (ghosts[i][0], ghosts[i][1]).
 *
 * Each turn, you and all ghosts simultaneously *may* move in one of 4 cardinal directions:
 * north, east, west, or south, going from the previous point to a new point 1 unit of distance away.

 * You escape if and only if you can reach the target before any ghost reaches you
 * (for any given moves the ghosts may take.)
 * If you reach any square (including the target) at the same time as a ghost, it doesn't count as an escape.

 * Return True if and only if it is possible to escape.
 *
 * Example 1:
 Input:
 ghosts = [[1, 0], [0, 3]]
 target = [0, 1]
 Output: true
 Explanation:
 You can directly reach the destination (0, 1) at time 1, while the ghosts located at (1, 0) or (0, 3) have no way to catch up with you.

 * Example 2:
 Input:
 ghosts = [[1, 0]]
 target = [2, 0]
 Output: false
 Explanation:
 You need to reach the destination (2, 0), but the ghost at (1, 0) lies between you and the destination.

 * Example 3:
 Input:
 ghosts = [[2, 0]]
 target = [1, 0]
 Output: false
 Explanation:
 The ghost can reach the target at the same time as you.
 */
public class Code_789_EscapeTheGhosts {
    /**
     * 思路：
     * 考虑Example 2，为何鬼拦在我们和目的地之间，我们就无法顺利到达？
     * 如果考虑我们始终选择最短路径到达目的地（因为如果我们不选择最短路径，则鬼的移动范围则会增加），
     * 则鬼也可以选择最短路径去目标处埋伏你。
     * 所以问题就转化为，你到达目的地的最快速度，是否能比鬼快。
     */
    public boolean escapeGhosts(int[][] ghosts, int[] target) {
        //到target的最短路径
        int dis = Math.abs(target[0]) + Math.abs(target[1]);

        //只要鬼到target的最短路径 <= dis ,则返回false,因为鬼只要到target处，进行埋伏就好了。
        for(int[] ghost : ghosts){
            int ghostDis = Math.abs(ghost[0]-target[0]) + Math.abs(ghost[1]-target[1]);
            if(ghostDis <= dis){
                return false;
            }
        }
        return true;
    }
}
