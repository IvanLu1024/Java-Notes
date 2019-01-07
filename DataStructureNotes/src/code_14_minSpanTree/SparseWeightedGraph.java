package code_14_minSpanTree;

import java.util.Vector;

/**
 * 有向图
 */
public class SparseWeightedGraph<E extends Number & Comparable> implements WeightedGraph<E>{

    private int n;  // 节点数
    private int m;  // 边数
    private boolean directed;    // 是否为有向图
    private Vector<Edge<E>>[] g; // 图的具体数据

    // 构造函数
    public SparseWeightedGraph(int n , boolean directed ){
        assert n >= 0;
        this.n = n;
        this.m = 0;    // 初始化没有任何边
        this.directed = directed;
        // g初始化为n个空的vector, 表示每一个g[i]都为空, 即没有任和边
        g = (Vector<Edge<E>>[])new Vector[n];
        for(int i = 0 ; i < n ; i ++){
            g[i] = new Vector<Edge<E>>();
        }
    }

    @Override
    public int V(){ return n;} // 返回节点个数

    @Override
    public int E(){ return m;} // 返回边的个数

    // 向图中添加一个边
    @Override
    public void addEdge(Edge edge){

        assert edge.v() >= 0 && edge.v() < n ;
        assert edge.w() >= 0 && edge.w() < n ;

        g[edge.v()].add(edge);
        if( edge.v() != edge.w() && !directed ){
            g[edge.w()].add(new Edge(edge.w(),edge.v(),edge.wt()));
        }
        m ++;
    }

    // 验证图中是否有从v到w的边 -->时间复杂度：O(n)
    @Override
    public boolean hasEdge(int v, int w){

        assert v >= 0 && v < n ;
        assert w >= 0 && w < n ;

        for( int i = 0 ; i < g[v].size() ; i ++ ){
            if( g[v].elementAt(i).other(v) == w ){
                return true;
            }
        }
        return false;
    }

    @Override
    public void show() {
        for( int i = 0 ; i < n ; i ++ ){
            System.out.print("vertex " + i + ":\t");
            for( int j = 0 ; j < g[i].size() ; j ++ ){
                Edge e = g[i].elementAt(j);
                System.out.print( "( to:" + e.other(i) + ",wt:" + e.wt() + ")\t");
            }
            System.out.println();
        }
    }

    // 返回图中一个顶点的所有邻边
    // 由于java使用引用机制，返回一个Vector不会带来额外开销

    @Override
    public Iterable<Edge<E>> adj(int v) {
        assert v >= 0 && v < n;
        return g[v];
    }
}

