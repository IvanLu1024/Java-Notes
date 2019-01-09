package code_15_shortestPath;

/**
 * Created by 18351 on 2019/1/7.
 */
public class Edge<E extends Number & Comparable> implements Comparable<Edge>{
    //定义改变的起始节点和终止节点
    private int from;
    private int to;
    //该边的权值
    private E weight;

    public Edge(int v, int w, E weight)
    {
        this.from = v;
        this.to = w;
        this.weight = weight;
    }

    public Edge(Edge<E> e)
    {
        this.from = e.from;
        this.to = e.to;
        this.weight = e.weight;
    }

    //获取起点
    public int v(){
        return from;
    }

    //获取终点
    public int w(){
        return to;
    }

    //获取权值
    public E wt(){
        return weight;
    }

    // 给定一个顶点, 返回另一个顶点
    public int other(int x){
        assert x == from || x == to;
        return x == from ? to : from;
    }

    @Override
    public String toString() {
        return "" + from + "-" + to + ": " + weight;
    }

    @Override
    public int compareTo(Edge that) {
        int num=weight.compareTo(that.wt());
        return num;
    }
}