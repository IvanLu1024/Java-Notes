package code_09_unionFind;

/**
 * Created by 18351 on 2018/12/28.
 */
public class UnionFind3 implements UF{
    //parent[i]表示i所指向的父节点
    private int[] parent;
    //sz[i]表示i元素所在的集合的元素个数
    private int[] sz;

    public UnionFind3(int size){
        parent=new int[size];
        sz=new int[size];
        //初始化时，将每个元素看成一个树节点，它们指向自身，构成森林
        for(int i=0;i<parent.length;i++){
            parent[i]=i;
            //初始化时，每个节点就是一个集合，那么元素个数就是1
            sz[i]=1;
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
        while(p!=parent[p]){
            p=parent[p];
        }
        //返回的是p所在的集合的编号，同时也是p集合非根节点
        return p;
    }

    @Override
    public boolean isConnected(int p, int q) {
        //根节点相同，就是同一个集合
        return find(p)==find(q);
    }

    //根据两个元素所在的集合的元素个数的不同，判断合并方向。
    //将元素少的集合合并到元素多的集合上，降低树的高度。
    @Override
    public void unionElements(int p, int q) {
        int pRoot=find(p);
        int qRoot=find(q);
        if(pRoot==qRoot){
            return;
        }
        //将p所在集合的根节点指向q所在集合的根节点
        //parent[pRoot]=qRoot;

        //改进：
        if(sz[pRoot]<sz[qRoot]){
            //p节点所在的集合元素较少，pRoot的父指针指向qRoot
            parent[pRoot]=qRoot;
            sz[qRoot]+=sz[pRoot];
        }else{
            parent[qRoot]=pRoot;
            sz[pRoot]+=sz[qRoot];
        }
    }
}