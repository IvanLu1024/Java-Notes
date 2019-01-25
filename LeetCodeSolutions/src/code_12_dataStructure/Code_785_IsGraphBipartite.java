package code_12_dataStructure;

/**
 * Created by 18351 on 2019/1/25.
 */
public class Code_785_IsGraphBipartite {
    /**
     * 思路：
     * 如果可以用两种颜色对图中的节点进行着色，并且保证相邻的节点颜色不同，那么这个图就是二分图。
     */
    private int[] checked;

    public boolean isBipartite(int[][] graph) {
        checked=new int[graph.length];
        for(int i=0;i<checked.length;i++){
            checked[i]=-1;
        }
        for(int i=0;i<graph.length;i++){
            if(checked[i]==-1){
                if(!check(graph,i,0)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param graph
     * @param index 对应的结点下标
     * @param group 分组标号，数值为0和1，group初始值为0，1-group值就变成1
     * @return
     */
    private boolean check(int[][] graph,int index,int group){
        if(checked[index]!=-1){
            //首先检查该结点是否已经被检查，如果是，则看看是否能满足要求；
            return checked[index]==group;
        }
        checked[index]=group;
        //遍历与该结点相连的其他结点
        for(int next:graph[index]){
            if(!check(graph,next,1-group)){
                return false;
            }
        }
        return true;
    }
}
