package code_14_minSpanTree;

import java.util.PriorityQueue;
import java.util.Vector;

/**
 * Created by DHA on 2019/1/7.
 */
public class KruskalMST<E extends Number & Comparable> {
    private Vector<Edge<E>> mst;   // 最小生成树所包含的所有边
    private Number mstWeight;           // 最小生成树的权值

    public KruskalMST(WeightedGraph graph){
        mst=new Vector<>();

        // 将图中的所有边存放到一个最小堆中
        PriorityQueue<Edge<E>> pq=new PriorityQueue<>(graph.E());
        for(int i=0;i<graph.V();i++){
           for(Object item:graph.adj(i)){
               Edge<E> e=(Edge<E>)item;
               if(e.w() <e.v()){ //由于是无向图，只要加入一条边就好了
                   pq.add(e);
               }
           }
        }

        //创建一个并查集, 来查看已经访问的节点的联通情况
        UnionFind uf = new UnionFind(graph.V());
        while (!pq.isEmpty() && mst.size()<graph.V()-1){
            // 从最小堆中依次从小到大取出所有的边
            Edge<E> e = pq.poll();

            //如果该边的两个端点是联通的, 说明加入这条边将产生环, 扔掉这条边
            if(uf.isConnected(e.w(),e.v())){
                continue;
            }

            // 否则, 将这条边添加进最小生成树, 同时标记边的两个端点联通
            mst.add(e);
            uf.unionElements(e.w(),e.v());
        }

        // 计算最小生成树的权值
        mstWeight = mst.elementAt(0).wt();
        for( int i = 1 ; i < mst.size() ; i ++ ){
            mstWeight = mstWeight.doubleValue() + mst.elementAt(i).wt().doubleValue();
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
