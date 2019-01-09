package code_15_shortestPath;

/**
 * 定义有向边
 */
public interface WeightedEdge<E extends Number & Comparable> {
    public int V();
    public int E();
    public void addEdge(Edge<E> e);
    boolean hasEdge(int v, int w);
    void show();
    public Iterable<Edge<E>> adj(int v);
}
