package code_03_list;

import org.junit.Test;

/**
 * 328. Odd Even Linked List
 * Given a singly linked list, group all odd nodes（奇数） together followed by the even nodes（偶数）.
 * TODO:Please note here we are talking about the node number and not the value in the nodes.
 * You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.
 *
 * Example 1:
 * Input: 1->2->3->4->5->NULL
 * Output: 1->3->5->2->4->NULL
 *
 * Example 2:
 * Input: 2->1->3->5->6->4->7->NULL
 * Output: 2->3->6->7->1->5->4->NULL
 *
 * Note:
 * The relative order inside both the even and odd groups should remain as it was in the input.
 * The first node is considered odd, the second node even and so on ...
 */
public class Code_328_OddEvenLinkedList {
    //node的num是从1开始编号的
    public ListNode oddEvenList(ListNode head) {
        if(head==null || head.next==null){
            return head;
        }
        ListNode oddHead=new ListNode(-1);
        ListNode evenHead=new ListNode(-1);
        int num=1;
        ListNode cur=head;
        ListNode odd=oddHead;
        ListNode even=evenHead;
        while(cur!=null){
            if(num%2==1){
                odd.next=cur;
                odd=odd.next;
            }else{
                even.next=cur;
                even=even.next;
            }
            cur=cur.next;
            num++;
        }

        odd.next=evenHead.next;
        even.next=null;

        evenHead.next=null;
        ListNode retNode=oddHead.next;
        oddHead.next=null;
        return retNode;
    }

    @Test
    public void test(){
        //int[] arr={1,2,3,4,5};
        int[] arr={2,1,3,5,6,4,7};
        ListNode head=LinkedListUtils.createLinkedList(arr);
        LinkedListUtils.printList(head);

        head=oddEvenList(head);
        LinkedListUtils.printList(head);
    }
}
