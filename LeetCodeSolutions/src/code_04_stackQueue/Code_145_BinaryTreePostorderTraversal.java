package code_04_stackQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 145. Binary Tree Postorder Traversal
 */
public class Code_145_BinaryTreePostorderTraversal {
    private enum Command{GO,PRINT};

    private class StackNode{
        Command command;
        TreeNode node;
        StackNode(Command command,TreeNode node){
            this.command=command;
            this.node=node;
        }
    }

    public List<Integer> postorderTraversal(TreeNode root) {
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
                stack.push(new StackNode(Command.PRINT,stackNode.node));
                if(stackNode.node.right!=null){
                    stack.push(new StackNode(Command.GO,stackNode.node.right));
                }
                if(stackNode.node.left!=null){
                    stack.push(new StackNode(Command.GO,stackNode.node.left));
                }
            }
        }
        return ret;
    }
}
