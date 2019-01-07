package code_14_minSpanTree;

import java.util.PriorityQueue;
import java.util.Vector;

/**
 * Created by 18351 on 2019/1/7.
 */
public class LazyPrimMST<E extends Number & Comparable> {
    private WeightedGraph<E> G;    // 图的引用
    private PriorityQueue<Edge<E>> pq;   // 最小堆, -->Java默认使用小根堆
    private boolean[] marked;           // 标记数组, 在算法运行过程中标记节点i是否被访问
    private Vector<Edge<E>> mst;   // 最小生成树所包含的所有边
    private Number mstWeight;           // 最小生成树的权值

    public LazyPrimMST(WeightedGraph graph){
        this.G=graph;
        pq=new PriorityQueue<>();
        marked=new boolean[graph.V()];
        mst=new Vector<>();

        //从0节点开始访问
        visit(0);
        while(!pq.isEmpty()){
            //获取最小边
            Edge<E> e=pq.poll();
            //看看这条最小边是否是横切边
            if(marked[e.v()]==marked[e.w()]){
                continue;
            }
            //是横切边，说明就是MST的边
            mst.add(e);

            // 访问和这条边连接的还没有被访问过的节点
            if(! marked[e.v()]){
                visit(e.v());
            }else{
                visit(e.w());
            }

            //计算最小生成树的权值
            mstWeight=mst.elementAt(0).wt();
            for(int i=1;i<mst.size();i++){
                mstWeight = mstWeight.doubleValue() + mst.elementAt(i).wt().doubleValue();
            }
        }
    }

    //v顶点是未访问过的
    private void visit(int v){
        assert !marked[v];
        marked[v]=true;
        //将与v节点相连的顶点边加入到堆中
        for(Edge e:G.adj(v)){
            if(!marked[e.other(v)]){
                pq.add(e);
            }
        }
    }

    // 返回最小生成树的所有边
    public Vector<Edge<E>> mstEdges(){
        return mst;
    }

    // 返回最小生成树的权值
    public Number result(){
        return mstWeight;
    }
}
