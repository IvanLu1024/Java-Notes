package code_03_list;

import org.junit.Test;

/**
 * 24. Swap Nodes in Pairs
 *
 * Given a linked list, swap every two adjacent nodes and return its head.
 *
 * Example:
 *
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 *
 * Note:
 * Your algorithm should use only constant extra space.
 * You may not modify the values in the list's nodes, only nodes itself may be changed.
 */
public class Code_24_SwapNodesInPairs {
    public ListNode swapPairs(ListNode head) {
        if(head==null || head.next==null){
            return head;
        }
        ListNode dummyHead=new ListNode(0);
        dummyHead.next=head;

        ListNode p=dummyHead;
        while(p.next!=null && p.next.next!=null){
            //保证有要交换的两个节点
            ListNode node1=p.next;
            ListNode node2=node1.next;
            ListNode next=node2.next;

            node2.next=node1;
            node1.next=next;
            p.next=node2;

            p=node1;
        }

        ListNode retNode=dummyHead.next;
        dummyHead.next=null;

        return retNode;
    }

    @Test
    public void test(){
        int[] arr={1,2,3,4};
        ListNode head=LinkedListUtils.createLinkedList(arr);
        LinkedListUtils.printList(head);

        head=swapPairs(head);
        LinkedListUtils.printList(head);
    }
}
