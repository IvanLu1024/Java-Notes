package code_05_binaryTree;

import org.junit.Test;

/**
 * 109. Convert Sorted List to Binary Search Tree
 *
 * Given a singly linked list where elements are sorted in ascending order,
 * convert it to a height balanced BST.
 * For this problem, a height-balanced binary tree is
 * defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
 *
 * Example:

 Given the sorted linked list: [-10,-3,0,5,9],

 One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:

        0
      / \
   -3   9
   /   /
 -10  5
 */
public class Code_109_ConvertSortedListtoBinarySearchTree {
    /**
     * 思路二：局部从下往上构造BST
     */
    private ListNode cur;
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }
        cur=head;
        return buildBST(0,getLength(head)-1);
    }

    private TreeNode buildBST(int start,int end){
        if(start>end){
            return null;
        }
        int mid=start+(end-start)/2;
        TreeNode left=buildBST(start,mid-1);

        TreeNode root=new TreeNode(cur.val);
        cur=cur.next;

        TreeNode right=buildBST(mid+1,end);

        root.left=left;
        root.right=right;
        return root;
    }

    /**
     * 思路一：108题思路
     */
    public TreeNode sortedListToBST1(ListNode head) {
        if(head==null){
            return null;
        }
        return sortedListToBST(head,0,getLength(head)-1);
    }

    private TreeNode sortedListToBST(ListNode head,int start,int end){
        if(start>end){
            return null;
        }
        int mid=start+(end-start)/2;
        TreeNode root=new TreeNode(getElement(head,mid));
        if(start==end){
            return root;
        }
        root.left=sortedListToBST(head,start,mid-1);
        root.right=sortedListToBST(head,mid+1,end);
        return root;
    }

    private int getLength(ListNode head){
        int count=0;
        ListNode cur=head;
        while(cur!=null){
            count++;
            cur=cur.next;
        }
        return count;
    }

    //按照index获取链表中数据
    private int getElement(ListNode head,int index){
       int i=0;
       ListNode cur=head;
       while(cur!=null && i!=index){
           cur=cur.next;
           i++;
       }
       return cur.val;
    }

    public void preOrder(TreeNode root){
        if(root==null){
            return;
        }
        System.out.println(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }

    public void inOrder(TreeNode root){
        if(root==null){
            return;
        }
        inOrder(root.left);
        System.out.println(root.val);
        inOrder(root.right);
    }

    @Test
    public void test(){
        int[] arr={-10,-3,0,5,9};
        ListNode head=LinkedListUtils.createLinkedList(arr);
        TreeNode root=sortedListToBST(head);
        preOrder(root);
        System.out.println("=================");
        inOrder(root);
    }
}