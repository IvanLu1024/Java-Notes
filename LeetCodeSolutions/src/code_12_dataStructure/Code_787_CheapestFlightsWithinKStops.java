package code_12_dataStructure;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

/**
 *
 * 787. Cheapest Flights Within K Stops
 *
 * There are n cities connected by m flights.
 * Each fight starts from city u and arrives at v with a price w.
 * Now given all the cities and flights,
 * together with starting city src and the destination dst,
 * your task is to find the cheapest price from src to dst with up to k stops.
 * If there is no such route, output -1.
 *
 * Example 1:
 Input:
 n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 src = 0, dst = 2, k = 1
 Output: 200

 * Example 2:
 Input:
 n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 src = 0, dst = 2, k = 0
 Output: 500
 */
public class Code_787_CheapestFlightsWithinKStops {
    /**
     * 思路：
     * 由于要求最短路径中间经过的顶点数目不能超过K个，所以整个最短路径的跳转次数不能超过K + 1。
     * 因此我们可以通过修改Bellman-Ford算法来实现：循环K+1次，每次更新一下每条边导致的权重减少。
     * 算法的描述如下：
     *（1）创建源顶点src到图中所有顶点的最短距离的集合，并初始化为INT_MAX，但是将源顶点的最短路径距离置为0；
     * (2)计算最短路径，执行K + 1次遍历：对于图中的每条边，如果起点u的距离d加上边的权值w小于终点v的距离d，则更新终点v的距离值d。
     * (3)当执行完K+1次遍历之后，如果顶点dst的距离不为INT_MAX，就说明从src到dst存在长度不超过K+1的最短路径。
     * Bellman-Ford算法其实采用了动态规划的思想，是最简单优雅但是又最有效的算法之一。
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        //dis[i]，表示从 src 到 i 的最短距离
        int[] dis = new int[n];
        for(int i=0;i<n;i++){
            dis[i] = Integer.MAX_VALUE;
        }
        dis[src] = 0;

        // k+1 次遍历
        for(int i=0;i<K+1;i++){
            int[] tmp_dis = new int[n];
            for(int k=0;k<n;k++){
                tmp_dis[k] = dis[k];
            }

            for(int[] flight : flights){
                int u = flight[0];
                int v = flight[1];
                int w = flight[2];

                //dis[u] < Integer.MAX_VALUE 说明存在从 src -> u 的路径
                if( dis[u] < Integer.MAX_VALUE ){
                    //对于图中的每条边<u,v>，dis[u]加上边的权值w小于终点v的距离，则更新到顶点v的距离。
                    tmp_dis[v] = Math.min(tmp_dis[v],dis[u] + w);
                }
            }
            dis = tmp_dis;
        }
        return dis[dst] == Integer.MAX_VALUE ? -1 : dis[dst];
    }

    @Test
    public void test() {
        int n = 3;
        int[][] flights = {
                {0, 1, 100},
                {1, 2, 100},
                {0, 2, 500}
        };
        int src = 0, dst = 2, k = 1;
        System.out.println(findCheapestPrice(n, flights, src, dst, k));
    }
}
