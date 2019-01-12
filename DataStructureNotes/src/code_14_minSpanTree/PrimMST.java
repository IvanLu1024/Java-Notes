package code_14_minSpanTree;

import java.util.Vector;

/**
 * 优化的Prim算法求图的最小生成树
 */
public class PrimMST<E extends Number & Comparable> {
    private WeightedGraph G;              // 图的引用
    private IndexMinHeap<E> ipq;     // 最小索引堆, 算法辅助数据结构
    private Edge<E>[] edgeTo;        // 访问的点所对应的边, 算法辅助数据结构
    private boolean[] marked;             // 标记数组, 在算法运行过程中标记节点i是否被访问
    private Vector<Edge<E>> mst;     // 最小生成树所包含的所有边
    private Number mstWeight;             // 最小生成树的权值

    public PrimMST(WeightedGraph graph){
        G = graph;
        assert( graph.E() >= 1 );
        ipq = new IndexMinHeap<E>(graph.V());

        // 算法初始化
        marked = new boolean[G.V()];
        edgeTo = new Edge[G.V()];
        mst=new Vector<>();

        //Lazy Prim算法的改进
        visit(0);
        while (!ipq.isEmpty()){
            // 使用最小索引堆找出已经访问的边中权值最小的边
            // 最小索引堆中存储的是点的索引, 通过点的索引找到相对应的边
            int v=ipq.extractMinIndex();
            assert( edgeTo[v] != null );
            mst.add(edgeTo[v]);
            visit(v);
        }

        //计算最小生成树的权值
        mstWeight=mst.elementAt(0).wt();
        for(int i=1;i<mst.size();i++){
            mstWeight=mstWeight.doubleValue()+mst.elementAt(i).wt().doubleValue();
        }
    }

    private void visit(int v){
        assert !marked[v];
        marked[v] = true;

        // 将和节点v相连接的未访问的另一端点, 和与之相连接的边, 放入最小堆中
        for(Object edge:G.adj(v)){
            Edge<E> e=(Edge<E>)edge;
            int w=e.other(v);
            // 如果边的另一端点未被访问
            if(!marked[w]){
                if(edgeTo[w]==null){
                    //如果从没有考虑过这个端点, 直接将这个端点和与之相连接的边加入索引堆
                    //edgeTo[w] 表示访问w定点所对应的边<v,w>
                    edgeTo[w]=e;
                    ipq.insert(w,e.wt());
                }else if(e.wt().compareTo(edgeTo[w].wt())<0){
                    //如果曾经考虑这个端点, 但现在的边比之前考虑的边更短, 则进行替换
                    edgeTo[w]=e;
                    ipq.change(w,e.wt());
                }
            }
        }
    }

    // 返回最小生成树的所有边
    Vector<Edge<E>> mstEdges(){
        return mst;
    }

    // 返回最小生成树的权值
    Number result(){
        return mstWeight;
    }
}
