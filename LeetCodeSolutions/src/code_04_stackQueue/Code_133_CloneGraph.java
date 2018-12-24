package code_04_stackQueue;

import java.util.*;

/**
 * 133. Clone Graph
 *
 * Given the head of a graph, return a deep copy (clone) of the graph. Each node in the graph contains a label (int) and a list (List[UndirectedGraphNode]) of its neighbors. There is an edge between the given node and each of the nodes in its neighbors.


 OJ's undirected graph serialization (so you can understand error output):
 Nodes are labeled uniquely.

 We use # as a separator for each node, and , as a separator for node label and each neighbor of the node.


 As an example, consider the serialized graph {0,1,2#1,2#2,2}.

 The graph has a total of three nodes, and therefore contains three parts as separated by #.

 1.First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
 2.Second node is labeled as 1. Connect node 1 to node 2.
 3.Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle.

 * Visually, the graph looks like the following:

         1
       / \
     /   \
    0 --- 2
        / \
       \_/
 * Note: The information about the tree serialization is only meant
 * so that you can understand error output if you get a wrong answer.
 * You don't need to understand the serialization to solve the problem.
 */
public class Code_133_CloneGraph {
    /**
     * 思路一：DFS
     */
    //存储一条边上的两个结点
    /*Map<UndirectedGraphNode, UndirectedGraphNode> map=
            new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if(node==null){
            return null;
        }
        //不访问重复的顶点
        if(map.containsKey(node)){
            return map.get(node);
        }
        UndirectedGraphNode newNode=new UndirectedGraphNode(node.label);
        map.put(node,newNode);
        for(UndirectedGraphNode neighbor: node.neighbors){
            newNode.neighbors.add(cloneGraph(neighbor));
        }
        return newNode;
    }*/
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if(node==null){
            return null;
        }
        Map<UndirectedGraphNode, UndirectedGraphNode> map=
                new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
        Queue<UndirectedGraphNode> q=new LinkedList<>();
        UndirectedGraphNode newNode=new UndirectedGraphNode(node.label);
        map.put(node,newNode);
        q.add(node);
        while(!q.isEmpty()){
            UndirectedGraphNode curNode=q.poll();
            List<UndirectedGraphNode> curNeightbors=curNode.neighbors;
            for(UndirectedGraphNode neighbor:curNeightbors){
                if(map.containsKey(neighbor)){
                    map.get(curNode).neighbors.add(map.get(neighbor));
                }else{
                    UndirectedGraphNode newNode2=new UndirectedGraphNode(neighbor.label);
                    map.put(neighbor,newNode2);
                    map.get(curNode).neighbors.add(newNode2);
                    q.add(neighbor);
                }
            }
        }
        return newNode;
    }

}
