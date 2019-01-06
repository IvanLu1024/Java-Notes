package code_13_graph;

/**
 * 图的dfs应用之统计图的连通分量
 */
public class Component {
    private Graph graph;
    //连通分量的个数
    private int num;
    private boolean[] visited;

    public Component(Graph graph){
        this.graph=graph;
        visited=new boolean[graph.V()];
        num=0;
        for(int i=0;i<graph.V();i++){
            if(!visited[i]){
                dfs(i);
                num++;
            }
        }
    }

    private void dfs(int v){
        visited[v]=true;
        for(int w:graph.adj(v)){
            if(!visited[w]){
                dfs(w);
            }
        }
    }

    //获取连通分量个数
    public int getNum(){
        return num;
    }
}
