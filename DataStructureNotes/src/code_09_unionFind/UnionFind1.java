package code_09_unionFind;

/**
 * Created by 18351 on 2018/12/28.
 */
public class UnionFind1 implements UF{
    //存储相应元素的集合编号
    private int[] id;

    public UnionFind1(int size){
        id=new int[size];
        for(int i=0;i<id.length;i++){
            id[i]=i;
        }
    }

    @Override
    public int getSize() {
        return id.length;
    }

    //元素属于同一个集合，则这两个元素就是相连的
    //时间复杂度：O(1)
    @Override
    public boolean isConnected(int p, int q) {
        return find(p)==find(q);
    }

    //查找p元素所属的集合
    private int find(int p){
        return id[p];
    }

    //时间复杂度：O(n)
    @Override
    public void unionElements(int p, int q) {
        int pId=find(p);
        int qId=find(q);
        if(pId==qId){
            return;
        }
        //连接两个元素后，如果它们是在两个不同的集合中，
        //则现在由于p、q的连接，就会成为一个集合
        for(int i=0;i<id.length;i++){
            if(id[i]==pId){
                id[i]=qId;
            }
        }
    }
}

