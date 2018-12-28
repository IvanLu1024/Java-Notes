package code_09_unionFind;

/**
 * Created by 18351 on 2018/12/28.
 */
public class UnionFind2 implements UF{
    //parent[i]表示i锁指向的父节点
    private int[] parent;

    public UnionFind2(int size){
        parent=new int[size];
        //初始化时，将每个元素看成一个树节点，它们指向自身，构成森林
        for(int i=0;i<parent.length;i++){
            parent[i]=i;
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


    @Override
    public void unionElements(int p, int q) {
        int pRoot=find(p);
        int qRoot=find(q);
        if(pRoot==qRoot){
            return;
        }
        //将p所在集合的根节点指向q所在集合的根节点
        parent[pRoot]=qRoot;
    }
}