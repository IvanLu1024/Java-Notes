package code_03_list;


import org.junit.Test;

/**
 * 92. Reverse Linked List II
 *
 * Reverse a linked list from position m to n. Do it in one-pass.
 * Note: 1 ≤ m ≤ n ≤ length of list.
 *
 * Example:
 * Input: 1->2->3->4->5->NULL, m = 2, n = 4
 * Output: 1->4->3->2->5->NULL
 */
public class Code_92_ReverseLinkedListII {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(m>=n){
            return head;
        }
        if(head==null || head.next==null){
            return head;
        }

        ListNode newHead=new ListNode(0);
        newHead.next=head;
        head=newHead;
        ListNode preNode=head;
        //保存的是指向m位置的前一个节点，也就是(m-1)位置
        for(int i=0;i<m-1;i++){
            preNode=preNode.next;
        }
        ListNode nodeA = preNode.next;
        ListNode nodeB = preNode.next.next;
        //cur此时是当前要翻转的节点
        for(int i=0;i<n-m;i++){
            nodeA.next=nodeB.next;
            nodeB.next=preNode.next;
            preNode.next=nodeB;
            nodeB=nodeA.next;
        }
        return head.next;
    }

    @Test
    public void test(){
        int arr[]={1,2,3,4,5};
        int m=2,n=4;
        ListNode head=LinkedListUtils.createLinkedList(arr);
        LinkedListUtils.printList(head);
        head=reverseBetween(head,m,n);
        LinkedListUtils.printList(head);
    }
}
