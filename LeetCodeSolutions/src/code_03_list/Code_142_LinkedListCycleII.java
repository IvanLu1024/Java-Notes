package code_03_list;

import org.junit.Test;

/**
 * 142. Linked List Cycle II
 *
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
 * To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to.
 * If pos is -1, then there is no cycle in the linked list.
 * Note: Do not modify the linked list.
 *
 * Example 1:

 Input: head = [3,2,0,-4], pos = 1
 Output: tail connects to node index 1
 Explanation: There is a cycle in the linked list, where tail connects to the second node.
 */
public class Code_142_LinkedListCycleII {
    /**
     * 思路一：断链法
     * 但是题目要求不能改变链表
     */
    public ListNode detectCycle1(ListNode head) {
        if(head==null || head.next==null){
            return null;
        }

        ListNode pre=head;
        ListNode cur=head.next;
        while(cur!=null){
            pre.next=null;
            pre=cur;
            cur=cur.next;
        }
        //说明没有环
        if(pre==cur){
            return null;
        }
        return pre;
    }

    /**
     * 思路二：快慢指针
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode fast=head;
        ListNode slow=head;
        while(slow!=null &&
                (fast!=null && fast.next!=null)){
            slow=slow.next;
            fast=fast.next.next;
            if(slow==fast){
                break;
            }
        }

        slow=head;
        if(fast==null || fast.next==null){
            return null;
        }
        while(fast!=slow){
            fast=fast.next;
            slow=slow.next;
        }
        return slow;
    }

    @Test
    public void test(){
        ListNode listNode=new ListNode(3);
        ListNode listNode2=new ListNode(2);
        ListNode listNode3=new ListNode(0);
        ListNode listNode4=new ListNode(-4);

        listNode.next=listNode2;
        listNode2.next=listNode3;
        listNode3.next=listNode4;
        listNode4.next=listNode2;
        ListNode ret=detectCycle(listNode);
        System.out.println(ret.val);
    }
}
