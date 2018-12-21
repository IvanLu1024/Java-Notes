package code_04_stackQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 144. Binary Tree Preorder Traversal
 */


public class Code_144_BinaryTreePreorderTraversal {
    private enum Command{GO,PRINT};

    private class StackNode{
        Command command;
        TreeNode node;
        StackNode(Command command,TreeNode node){
            this.command=command;
            this.node=node;
        }
    }

    public List<Integer> preorderTraversal(TreeNode root) {
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
                if(stackNode.node.left!=null){
                    stack.push(new StackNode(Command.GO,stackNode.node.left));
                }
                stack.push(new StackNode(Command.PRINT,stackNode.node));
            }
        }
        return ret;
    }
}
