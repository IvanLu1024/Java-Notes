package code_09_unionFind;

/**
 * Created by 18351 on 2018/12/28.
 */
public interface UF {
    int getSize();
    boolean isConnected(int p,int q);
    void unionElements(int p,int q);
}