package code_03_list;

import org.junit.Test;

/**
 * 25. Reverse Nodes in k-Group
 *
 * Given a linked list,
 * reverse the nodes of a linked list k at a time and return its modified list.
 * k is a positive integer and is less than or equal to the length of the linked list.
 * If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
 * 给出一个链表，将这个链表以k个结点为一组进行翻转，不够k个的部分就保持原样不进行翻转。
 *
 * Example:
 * Given this linked list: 1->2->3->4->5
 * For k = 2, you should return: 2->1->4->3->5
 * For k = 3, you should return: 3->2->1->4->5
 *
 * Note:
 * Only constant extra memory is allowed.
 * You may not alter the values in the list's nodes, only nodes itself may be changed.
 */
public class Code_25_ReverseNodesInK_Group {
    public ListNode reverseKGroup(ListNode head, int k) {
        if(k==1){
            return head;
        }
        ListNode root = new ListNode(-1);
        root.next=head;
        ListNode dummyHead=root;

        int n=0;
        //统计链表中节点数
        ListNode cur=head;
        while(cur!=null){
            n++;
            cur=cur.next;
        }
        //root.next指向子链表里的第一个节点，
        //head为第一个节点，反转一次即变成第二个节点，从而保证他后面的节点就是下一个将被放到前面的节点。
        //head始终指向子链表的第一个节点
        //root始终指向子链表的第一个节点的前一个节点
        while(n>=k){
            //这样循环的巧妙之处，就是循环的次数。
            //反转 后面的（k-1）个元素
            for(int j = 0 ; j < k-1; j++){
                ListNode node = root.next;

                root.next = head.next;
                head.next = root.next.next;
                root.next.next = node;
            }
            root = head;
            head = head.next;
            n-=k;
        }

        ListNode retNode=dummyHead.next;
        dummyHead=null;

        return retNode;
    }

    @Test
    public void test(){
        int[] arr={1,2,3,4,5};
        ListNode head=LinkedListUtils.createLinkedList(arr);
        LinkedListUtils.printList(head);

        int k=3;
        head=reverseKGroup(head,k);
        LinkedListUtils.printList(head);
    }
}
