package code_10_avl.set;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by 18351 on 2018/12/17.
 */
public class BST<E extends Comparable<E>> {
    private Node root;
    private int size;

    public BST(){
        root=null;
        size=0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    private class Node{
        public E e;
        public Node left,right;
        public Node(E e){
            this.e=e;
            this.left=null;
            this.right=null;
        }
    }

   /* //向BST中添加新元素e
    public void add(E e){
        if(root==null){
            root=new Node(e);
            size++;
            return;
        }else{
            add(root,e);
        }
    }

    //向以node为根节点的BST树种插入元素e
    private void add(Node node,E e){
        if(e.equals(node.e)){
            //插入元素与根节点相等，直接返回
            return;
        }else if(e.compareTo(node.e)<0 && node.left==null){
            node.left=new Node(e);
            size++;
            return;
        }else if(e.compareTo(node.e)>0 && node.right==null){
            node.right=new Node(e);
            size++;
            return;
        }
        if(e.compareTo(node.e)<0){
            add(node.left,e);
        }else{ //e.compareTo(node.e)>0
            add(node.right,e);
        }
    }*/

    //向BST中添加新元素e
    public void add(E e){
        root=add(root,e);
    }

    //向以node为根节点的BST树种插入元素e
    //返回插入新元素后该BST的根
    private Node add(Node node,E e){
        if(node==null){
            size++;
            return new Node(e);
        }
        //注意：对于e.equals(node.e)不做处理
        if(e.compareTo(node.e)<0){
            node.left=add(node.left,e);
        }else if(e.compareTo(node.e)>0){
            node.right=add(node.right,e);
        }
        return node;
    }

    //查看BST中是否包含元素e
    public boolean contains(E e){
        return contains(root,e);
    }

    //查看以node为根节点的BST中是否包含元素e
    private boolean contains(Node node,E e){
        if(node==null){
            return false;
        }
        if(e.compareTo(node.e)==0){
            return true;
        }else if(e.compareTo(node.e)<0){
            return contains(node.left,e);
        }else{
            return contains(node.right,e);
        }
    }

    //BST的前序遍历
    public void preOrder(){
        preOrder(root);
    }

    private void preOrder(Node node){
        if(node==null){
            return;
        }
        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }

    //BST的前序遍历（非递归方式）
    public void preOrderNR(){
        preOrderNR(root);
    }

    private void preOrderNR(Node node){
        if(node==null){
            return;
        }
        Stack<StackNode> stack=new Stack<>();
        stack.push(new StackNode(Command.GO,node));
        while(!stack.isEmpty()){
            StackNode stackNode=stack.pop();
            Node n=stackNode.node;
            Command command=stackNode.command;
            if(command== Command.COUT){
                System.out.println(stackNode.node.e);
            }else{
                if(n.right!=null){
                    stack.push(new StackNode(Command.GO,n.right));
                }
                if(n.left!=null){
                    stack.push(new StackNode(Command.GO,n.left));
                }
                stack.push(new StackNode(Command.COUT,n));
            }
        }
    }


    //BST的中序遍历
    public void inOrder(){
        inOrder(root);
    }

    private void inOrder(Node node){
        if(node==null){
            return;
        }
        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }

    //BST的中序遍历（非递归方式）
    public void inOrderNR(){
        inOrderNR(root);
    }

    private void inOrderNR(Node node){
        if(node==null){
            return;
        }
        Stack<StackNode> stack=new Stack<>();
        stack.push(new StackNode(Command.GO,node));
        while(!stack.isEmpty()){
            StackNode stackNode=stack.pop();
            Node n=stackNode.node;
            Command command=stackNode.command;
            if(command== Command.COUT){
                System.out.println(stackNode.node.e);
            }else{
                if(n.right!=null){
                    stack.push(new StackNode(Command.GO,n.right));
                }
                stack.push(new StackNode(Command.COUT,n));
                if(n.left!=null){
                    stack.push(new StackNode(Command.GO,n.left));
                }
            }
        }
    }

    //BST的后序遍历
    public void postOrder(){
        postOrder(root);
    }

    private void postOrder(Node node){
        if(node==null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);
    }

    //BST的后序遍历（非递归方式）
    public void postOrderNR(){
        postOrderNR(root);
    }

    private void postOrderNR(Node node){
        if(node==null){
            return;
        }
        Stack<StackNode> stack=new Stack<>();
        stack.push(new StackNode(Command.GO,node));
        while(!stack.isEmpty()){
            StackNode stackNode=stack.pop();
            Node n=stackNode.node;
            Command command=stackNode.command;
            if(command== Command.COUT){
                System.out.println(stackNode.node.e);
            }else{
                stack.push(new StackNode(Command.COUT,n));
                if(n.right!=null){
                    stack.push(new StackNode(Command.GO,n.right));
                }
                if(n.left!=null){
                    stack.push(new StackNode(Command.GO,n.left));
                }
            }
        }
    }

    //枚举命令，GO表示访问元素，COUT表示打印元素
    private enum Command{GO,COUT};

    private class StackNode{
        Command command;
        Node node;
        StackNode(Command command,Node node){
            this.command=command;
            this.node=node;
        }
    }

    //BST的层序遍历
    public void levelOrder(){
        Queue<Node> q=new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            Node node=q.remove();
            System.out.println(node.e);
            if(node.left!=null){
                q.add(node.left);
            }
            if(node.right!=null){
                q.add(node.right);
            }
        }
    }

    //寻找BST中的最小元素
    public E min(){
        if(size==0){
            throw new IllegalArgumentException("BST is emmpty");
        }
        return min(root).e;
    }

    private Node min(Node node){
        if(node.left==null){
            return node;
        }
        return min(node.left);
    }

    //寻找BST中的最大元素
    public E max(){
        if(size==0){
            throw new IllegalArgumentException("BST is emmpty");
        }
        return max(root).e;
    }

    private Node max(Node node){
        if(node.right==null){
            return node;
        }
        return max(node.right);
    }

    //删除BST中的最小值
    public E delMin(){
        E res=min();
        delMin(root);
        return res;
    }

    //删除以node为根结点的BST中的最小值元素
    private Node delMin(Node node){
        if(node.left==null){
            Node nodeRight=node.right;
            node.right=null;
            size--;
            return nodeRight;
        }
        node.left=delMin(node.left);
        return node;
    }

    //删除BST中的最大值
    public E delMax(){
        E res=max();
        delMax(root);
        return res;
    }

    //删除以node为根结点的BST中的最大值元素
    private Node delMax(Node node){
        if(node.right==null){
            Node nodeLeft=node.left;
            node.left=null;
            size--;
            return nodeLeft;
        }
        node.right=delMax(node.right);
        return node;
    }

    //删除BST中任意元素
    public void del(E e){
        root=del(root,e);
    }

    private Node del(Node node,E e){
        if(node==null){
            return null;
        }
        if(e.compareTo(node.e)<0){
            return del(node.left,e);
        }else if(e.compareTo(node.e)>0){
            return del(node.right,e);
        }else{
            //节点node就是要删除的节点
            //该节点只右有子树
            if(node.left==null){
                Node rightNode=node.right;
                node.right=null;
                size--;
                return rightNode;
            }
            //该节点只有左子树
            if(node.right==null){
                Node leftNode=node.left;
                node.left=null;
                size--;
                return leftNode;
            }
            //删除既有左子树又有右子树的节点
            Node s=min(node.right);
            s.right=delMin(node.right);
            s.left=node.left;

            //删除node
            node.left=node.right=null;
            return s;
        }
    }

    @Override
    public String toString() {
        StringBuilder res=new StringBuilder();
        generateBST(root,0,res);
        return res.toString();
    }

    //生成以node为根节点，深度为depth的描述二叉树的字符串（利用前序遍历）
    private void generateBST(Node node,int depth,StringBuilder res){
        if(node==null){
            res.append(generateDepth(depth)+"null\n");
            return;
        }
        res.append(generateDepth(depth)+node.e+"\n");
        generateBST(node.left,depth+1,res);
        generateBST(node.right,depth+1,res);
    }

    private String generateDepth(int depth){
        StringBuilder res=new StringBuilder();
        for(int i=0;i<depth;i++){
            res.append("--");
        }
        return res.toString();
    }
}
