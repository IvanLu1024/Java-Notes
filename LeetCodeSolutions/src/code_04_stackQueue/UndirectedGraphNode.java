package code_04_stackQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18351 on 2018/12/24.
 */
public class UndirectedGraphNode{
    public int label;
    public List<UndirectedGraphNode> neighbors;
    public UndirectedGraphNode(int x) {
        label = x;
        neighbors = new ArrayList<UndirectedGraphNode>();
    }
}