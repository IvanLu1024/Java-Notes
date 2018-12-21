package code_03_list;

import org.junit.Test;

import java.util.Stack;

/**
 *
 * 445. Add Two Numbers II
 *
 *You are given two non-empty linked lists representing two non-negative integers.
 * The most significant digit comes first and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Follow up:
 * What if you cannot modify the input lists? In other words, reversing the lists is not allowed.
 *
 * Example:
 * Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 8 -> 0 -> 7
 */
public class Code_445_AddTwoNumbersII {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1==null){
            return l2;
        }
        if(l2==null){
            return l1;
        }
        //题目要求不能直接反转链表，那么只能借助栈来实现链表的反转
        Stack<Integer> stack1=new Stack<>();
        Stack<Integer> stack2=new Stack<>();

        //实现链表l1的反转
        while(l1!=null){
            stack1.push(l1.val);
            l1=l1.next;
        }
        //实现链表l2的反转
        while(l2!=null){
            stack2.push(l2.val);
            l2=l2.next;
        }

        ListNode dummyHead=new ListNode(-1);

        //模仿Code_2_AddTwoNumbers的剧情了！！！！,但是这里创建链表采用头插法
        int tmp=0;
        while(!stack1.empty() || !stack2.empty()){
            if(!stack1.empty()){
                tmp+=stack1.pop();
            }
            if(!stack2.empty()){
                tmp+=stack2.pop();
            }
            ListNode newNode=new ListNode(tmp%10);
            newNode.next=dummyHead.next;
            dummyHead.next=newNode;
            tmp/=10;
        }
        if(tmp==1){
            ListNode newNode=new ListNode(1);
            newNode.next=dummyHead.next;
            dummyHead.next=newNode;
        }

        ListNode retNode=dummyHead.next;
        dummyHead=null;

        return retNode;
    }

    @Test
    public void test(){
        int[] arr1={7,2,4,3};
        int[] arr2={5,6,4};

        ListNode l1=LinkedListUtils.createLinkedList(arr1);
        ListNode l2=LinkedListUtils.createLinkedList(arr2);
        LinkedListUtils.printList(l1);
        LinkedListUtils.printList(l2);

        ListNode head=addTwoNumbers(l1,l2);
        LinkedListUtils.printList(head);
    }
}
