package code_03_list;

/**
 * 19. Remove Nth Node From End of List
 * Given a linked list, remove the n-th node from the end of list and return its head.
 *
 * Example:
 *
 * Given linked list: 1->2->3->4->5, and n = 2.
 * After removing the second node from the end, the linked list becomes 1->2->3->5.
 *
 * Note:
 * Given n will always be valid.
 */
public class Code_19_RemoveNthNodeFromEndOfList {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if(n<0){
            return head;
        }

        ListNode dummyHead=new ListNode(0);
        dummyHead.next=head;

        ListNode p=dummyHead;
        ListNode q=dummyHead;
        //q指向的是p后面的第（n+1）个节点
        for(int i=0;i<n+1;i++){
            //如果n太大的话,q有可能指向null
            if(q==null){
                break;
            }
            q=q.next;
        }
        while(q!=null){
            p=p.next;
            q=q.next;
        }

        //删除目标节点
        ListNode delNode=p.next;
        p.next=delNode.next;

        ListNode retNode=dummyHead.next;
        dummyHead=null;

        return retNode;
    }
}
