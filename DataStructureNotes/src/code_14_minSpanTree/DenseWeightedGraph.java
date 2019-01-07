package code_14_minSpanTree;

import java.util.Vector;

/**
 * 有向图
 */
public class DenseWeightedGraph<E extends Number & Comparable> implements WeightedGraph<E>{

    private int n;  // 节点数
    private int m;  // 边数
    private boolean directed;   // 是否为有向图
    private Edge[][] g;      // 图的具体数据

    // 构造函数
    public DenseWeightedGraph(int n , boolean directed ){
        assert n >= 0;
        this.n = n;
        this.m = 0;    // 初始化没有任何边
        //false表示无向图
        this.directed = directed;
        // g初始化为n*n的有向边矩阵
        g = new Edge[n][n];
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

        if( hasEdge( edge.v() , edge.w() ) ){
            return;
        }
        g[edge.v()][edge.w()] = new Edge(edge);
        if( edge.v() != edge.w() && !directed ){
            g[edge.w()][edge.v()] = new Edge(edge.w(), edge.v(), edge.wt());
        }
        m ++;
    }

    // 验证图中是否有从v到w的边
    @Override
    public boolean hasEdge( int v , int w ){
        assert v >= 0 && v < n ;
        assert w >= 0 && w < n ;
        return g[v][w]!=null;
    }

    @Override
    public void show() {
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(g[i][j]!=null){
                    System.out.print(g[i][j].wt()+"\t");
                }else{
                    System.out.print("NULL\t");
                }
            }
            System.out.println();
        }
    }

    // 返回图中一个顶点的所有邻边
    // 由于java使用引用机制，返回一个Vector不会带来额外开销
    @Override
    public Iterable<Edge<E>> adj(int v) {
        assert v >= 0 && v < n;
        Vector<Edge<E>> adjV = new Vector<>();
        for (int i = 0; i < n; i++) {
            if (g[v][i]!=null) {
                adjV.add(g[v][i]);
            }
        }
        return adjV;
    }
}