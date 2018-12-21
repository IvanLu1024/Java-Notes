package code_02_find;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Given n points in the plane that are all pairwise distinct, a "boomerang" is a tuple of points (i, j, k) such that the distance between i and j equals the distance between i and k (the order of the tuple matters).
 * Find the number of boomerangs. You may assume that n will be at most 500 and coordinates of points are all in the range [-10000, 10000] (inclusive).

 * Example:
 *Input:
 *[[0,0],[1,0],[2,0]]
 * Output:
 * 2

 * Explanation:
 * The two boomerangs are [[1,0],[0,0],[2,0]] and [[1,0],[2,0],[0,0]]
 */
public class Code_447_NumberOfBoomerangs {
    public int numberOfBoomerangs(int[][] points) {
        //points 表示 [[0,0],[1,0],[2,0]],实际上就是 points[points.length][2]
        int res=0;
        //存储返回的结果
        for(int i=0;i<points.length;i++){
            Map<Integer,Integer> map=new HashMap<Integer,Integer>();
            //距离到i距离相同的点出现的次数
            for(int j=0;j<points.length;j++){
                if(j!=i){
                    int freq=map.get(distance(points,i,j))==null?0:map.get(distance(points,i,j));
                    map.put(distance(points,i,j),++freq);
                }
            }

            //遍历map,如果到点i的距离相等的点>=2（假设为n）,则有n*(n-1)种可能
            Set<Integer> set=map.keySet();
            for(Integer key:set){
                int value=map.get(key);
                if(value>=2){
                    res+=value*(value-1);
                }
            }
        }
        return res;
    }

    //计算点i到点j的距离
    public int distance(int[][] points,int i,int j){
        return (points[i][0]-points[j][0])*(points[i][0]-points[j][0])+
                (points[i][1]-points[j][1])*(points[i][1]-points[j][1]);
    }
}
