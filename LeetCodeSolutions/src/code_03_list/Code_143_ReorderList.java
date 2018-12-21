package code_03_list;

import org.junit.Test;

/**
 * Given a singly linked list L: L0→L1→…→Ln-1→Ln,
 * reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
 *
 * You may not modify the values in the list's nodes, only nodes itself may be changed.
 *
 * Example 1:
 * Given 1->2->3->4, reorder it to 1->4->2->3.
 *
 * Example 2:
 * Given 1->2->3->4->5, reorder it to 1->5->2->4->3.
 */
public class Code_143_ReorderList {
    public void reorderList(ListNode head) {
        if(head==null || head.next==null){
            return;
        }

        //先将该链表拆成两个部分:
        // L0→L1→...→L(n/2) ==> head1
        // L(n/2+1)→L1→...→L(n) ==> head2
        ListNode slow=head;
        ListNode fast=head;
        while(slow!=null && fast.next!=null && fast.next.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }

        ListNode head2=slow.next;
        slow.next=null;
        ListNode head1=head;

        //先反转head2链表
        head2=reverseList(head2);

        //合并这两张表
        ListNode p=head1;
        ListNode q=head2;
        while(p!=null && q!=null){
            ListNode next1=p.next;
            ListNode next2=q.next;
            q.next=next1;
            p.next=q;
            p=next1;
            q=next2;
        }
        head=head1;
    }

    //反转链表
    public ListNode reverseList(ListNode head) {
        ListNode pre=null;
        ListNode cur=head;

        while(cur!=null){
            ListNode next=cur.next;
            cur.next=pre;
            pre=cur;
            cur=next;
        }
        return pre;
    }

    @Test
    public void test(){
        int[] arr={1,2,3,4};
        ListNode head=LinkedListUtils.createLinkedList(arr);
        LinkedListUtils.printList(head);
        //head=reverseList(head);
        //LinkedListUtils.printList(head);

        reorderList(head);
        LinkedListUtils.printList(head);
    }
}
