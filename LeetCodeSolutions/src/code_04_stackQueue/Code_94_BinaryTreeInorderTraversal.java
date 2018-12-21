package code_04_stackQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by 18351 on 2018/11/8.
 */
public class Code_94_BinaryTreeInorderTraversal {
    private enum Command{GO,PRINT};

    private class StackNode{
        Command command;
        TreeNode node;
        StackNode(Command command, TreeNode node){
            this.command=command;
            this.node=node;
        }
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ret=new ArrayList<>();
        if(root==null){
            return ret;
        }

        Stack<StackNode> stack=new Stack<>();
        stack.push(new StackNode(Command.GO,root));
        while(!stack.empty()){
            StackNode stackNode=stack.pop();
            Command command=stackNode.command;
            if(command== Command.PRINT){
                ret.add(stackNode.node.val);
            }else{
                if(stackNode.node.right!=null){
                    stack.push(new StackNode(Command.GO,stackNode.node.right));
                }
                stack.push(new StackNode(Command.PRINT,stackNode.node));
                if(stackNode.node.left!=null){
                    stack.push(new StackNode(Command.GO,stackNode.node.left));
                }
            }
        }
        return ret;
    }
}
