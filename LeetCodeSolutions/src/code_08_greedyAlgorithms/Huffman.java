package code_08_greedyAlgorithms;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by 18351 on 2019/1/30.
 */
public class Huffman {
    private class Node implements Comparable<Node>{
        char ch;
        int freq;
        boolean isLeaf;
        //要构成哈夫曼树，就需要左右子树
        Node left,right;

        //该构造方法用于生成叶子结点
        public Node(char ch,int freq){
            this.ch = ch;
            this.freq = freq;
            isLeaf = true;
        }

        //该构造方法用于生成非叶子结点
        public Node(Node left,Node right,int freq){
            this.left = left;
            this.right = right;
            this.freq = freq;
            this.isLeaf = false;
        }

        @Override
        public int compareTo(Node o) {
            //结点比较的是出现的频率
            return freq - o.freq;
        }
    }

    /**
     *
     * @param frequencyForChar <字符，该字符的频率>
     * @return 返回的字符对应的编码
     */
    public Map<Character, String> encode(Map<Character, Integer> frequencyForChar){
        //维护一个优先队列(最小堆)，方便每次取出频率最小的两个元素
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Character c : frequencyForChar.keySet()) {
            pq.add(new Node(c, frequencyForChar.get(c)));
        }

        //每次取出两个结点，构造哈夫曼树
        while(pq.size() != 1){
            //pq中至少要2个结点
            Node node1 = pq.poll();
            Node node2 = pq.poll();
            pq.add(new Node(node1,node2, node1.freq+node2.freq));
        }

        //从该哈夫曼树根节点开始进行编码
        return encode(pq.peek());
    }

    private Map<Character,String> encode(Node node){
        Map<Character, String> encodingForChar = new HashMap<>();
        //初始值编码值为: ""
        encode(node,"",encodingForChar);
        return encodingForChar;
    }

    /**
     * 生成编码时，从根节点出发，向左遍历则添加二进制位 0，向右则添加二进制位 1，
     * 直到遍历到叶子节点，叶子节点代表的字符的编码就是这个路径编码。
     */
    private void encode(Node node, String encoding, Map<Character, String> encodingForChar) {
        if (node.isLeaf) {
            encodingForChar.put(node.ch, encoding);
            return;
        }
        encode(node.left, encoding + '0', encodingForChar);
        encode(node.right, encoding + '1', encodingForChar);
    }

    @Test
    public void test(){
        Map<Character,Integer> map = new HashMap<>();
        map.put('a',10);
        map.put('b',20);
        map.put('c',40);
        map.put('d',80);
        Map<Character,String> map2 = encode(map);
        for(Character c : map2.keySet()){
            String code = map2.get(c);
            System.out.println(c+":"+code);
        }
    }
}
