package code_14_minSpanTree;

// 图的接口
public interface WeightedGraph<E extends Number & Comparable> {
    public int V();
    public int E();
    public void addEdge(Edge<E> edge);
    boolean hasEdge(int v, int w);
    void show();
    public Iterable<Edge<E>> adj(int v);
}
