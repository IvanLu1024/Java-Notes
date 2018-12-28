package code_09_unionFind;

/**
 * Created by 18351 on 2018/12/28.
 */
public class UnionFind6 implements UF{
    //parent[i]表示i所指向的父节点
    private int[] parent;

    //rank[i]表示根节点为i的树的深度
    private int[] rank;

    public UnionFind6(int size){
        parent=new int[size];
        rank=new int[size];
        //初始化时，将每个元素看成一个树节点，它们指向自身，构成森林
        for(int i=0;i<parent.length;i++){
            parent[i]=i;
            //初始化时，每个节点就是一个集合，那么元素所在集合的深度就是1
            rank[i]=1;
        }
    }

    @Override
    public int getSize() {
        return parent.length;
    }

    //查找p元素所在的树（也就是集合）的根节点
    //时间复杂度：O(h),其中h为该集合树的深度
    private int find(int p){
        if(p<0 || p>=parent.length){
            throw new IllegalArgumentException("p is out of bound.");
        }
        if(p!=parent[p]){
            parent[p]=find(parent[p]);
        }
        return parent[p];
    }

    @Override
    public boolean isConnected(int p, int q) {
        //根节点相同，就是同一个集合
        return find(p)==find(q);
    }

    //根据两个元素所在的集合的树的深度不同，判断合并方向。
    //将深度浅的集合合并到深度深的的集合上，降低树的高度。
    @Override
    public void unionElements(int p, int q) {
        int pRoot=find(p);
        int qRoot=find(q);
        if(pRoot==qRoot){
            return;
        }
        //将p所在集合的根节点指向q所在集合的根节点
        //parent[pRoot]=qRoot;

        /*改进：
        if(sz[pRoot]<sz[qRoot]){
            //p节点所在的集合元素较少，pRoot的父指针指向qRoot
            parent[pRoot]=qRoot;
            sz[qRoot]+=sz[pRoot];
        }else{
            parent[qRoot]=pRoot;
            sz[pRoot]+=sz[qRoot];
        }*/

        //改进
        if(rank[pRoot]<rank[qRoot]){
            //pRoot集合的深度浅，pRoot的父指针指向qRoot
            parent[pRoot]=qRoot;
        }else if(rank[pRoot]>rank[qRoot]){
            parent[qRoot]=pRoot;
        }else{ //rank[pRoot]==rank[qRoot]
            parent[pRoot]=qRoot;
            //pRoot的父指针指向qRoot,则qRoot所在的集合深度+1
            rank[qRoot]+=1;
        }
    }
}