package code_13_graph;

import java.util.Stack;

/**
 * Created by 18351 on 2019/1/6.
 */
public class Main3 {
    public static void main(String[] args) {
        SparseGraph g1=new SparseGraph(13,false);
        ReadGraph.readGraph(g1,"testG1.txt");
        Component component=new Component(g1);
        System.out.println("Num Of Component:"+component.getNum());

        SparseGraph g2=new SparseGraph(6,false);
        ReadGraph.readGraph(g2,"testG2.txt");
        Component component2=new Component(g2);
        System.out.println("Num Of Component:"+component2.getNum());

        SparseGraph g3=new SparseGraph(2,false);
        ReadGraph.readGraph(g3,"testG3.txt");
        Component component3=new Component(g3);
        System.out.println("Num Of Component:"+component3.getNum());
    }
}
