package code_15_shortestPath;

import java.util.Stack;
import java.util.Vector;

/**
 * Created by 18351 on 2019/1/8.
 */
public class DijkstraSP<E extends Number & Comparable>{
    private WeightedGraph G;
    // 图的引用
    private int s;
    // 起始点
    private Number[] distTo;
    // distTo[i]存储从起始点s到顶点i的最短路径长度
    private boolean[] marked;
    // 标记数组, 在算法运行过程中标记节点i是否被访问
    private Edge<E>[] from;
    // from[i]记录最短路径中, 到达i点的边是哪一条
    // 可以用来恢复整个最短路径

    public DijkstraSP(WeightedGraph graph,int s){
        this.G=graph;
        this.s=s;
        distTo=new Number[G.V()];
        marked=new boolean[G.V()];
        from=new Edge[G.V()];

        //使用索引堆记录当前找到的到达每个顶点的最短距离 ---> Number类型
        IndexMinHeap<E> indexMinHeap=new IndexMinHeap<>(G.V());
        //初始化起始点
        distTo[s] = 0.0;
        from[s]=new Edge<E>(s,s,(E)((Number)0.0));
        marked[s]=true;

        indexMinHeap.insert(s,(E)distTo[s]);
        while (!indexMinHeap.isEmpty()){
            int v=indexMinHeap.extractMinIndex();

            // distTo[v]就是s到v的最短距离
            marked[v] = true;

            for(Object item:G.adj(v)){
                Edge<E> edge=(Edge<E>)item;
                int w=edge.other(v);
                //如果从s点到w点的最短路径还没有找到
                if(!marked[w]){
                    // 如果w点以前没有访问过,
                    // 或者访问过, 但是通过当前的v点到w点距离更短, 则进行更新
                    if(from[w]==null ||
                            (distTo[v].doubleValue()+edge.wt().doubleValue() < distTo[w].doubleValue())){
                        distTo[w]=distTo[v].doubleValue()+edge.wt().doubleValue();
                        from[w]=edge;
                        if(indexMinHeap.contain(w)){
                            indexMinHeap.change(w,(E)distTo[w]);
                        }else{
                            indexMinHeap.insert(w,(E)distTo[w]);
                        }
                    }
                }
            }
        }
    }

    //获取从s点到w点的最短路径长度
    public Number shortestPathTo(int w){
        assert hasPathTo(w);
        return distTo[w];
    }

    // 判断从s点到w点是否联通
    public boolean hasPathTo(int w){
        assert w>=0 && w<G.V();
        return marked[w];
    }

    //寻找从s到w的最短路径, 将整个路径经过的边存放在res中
    public Vector<Edge<E>> shortestPath(int w){
        assert hasPathTo(w);

        //通过from数组逆向查找到从s到w的路径, 存放到栈中
        Stack<Edge<E>> stack=new Stack<>();
        Edge<E> e=from[w];
        while (e.v()!=s){
            stack.push(e);
            e=from[e.v()];
        }
        //最后e.v()就是s,那么e这条边入栈
        stack.push(e);

        //从栈中依次取出元素, 获得顺序的从s到w的路径
        Vector<Edge<E>> res=new Vector<>();
        while (!stack.isEmpty()){
            Edge<E> edge=stack.pop();
            res.add(edge);
        }
        return res;
    }


    // 打印出从s点到w点的路径
    public void showPath(int w){
        assert hasPathTo(w);

        Vector<Edge<E>> path =  shortestPath(w);
        for( int i = 0 ; i < path.size() ; i ++ ){
            System.out.print( path.elementAt(i).v() + " -> ");
            if( i == path.size()-1 ){
                System.out.println(path.elementAt(i).w());
            }
        }
    }
}
