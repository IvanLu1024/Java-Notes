package code_03_list;

import org.junit.Test;

/**
 *
 * given a sorted linked list, delete all duplicates such that each element appear only once.
 *
 * Example 1:
 * Input: 1->1->2
 * Output: 1->2
 *
 * Example 2:
 * Input: 1->1->2->3->3
 * Output: 1->2->3
 */
public class Code_83_RemoveDuplicatesFromSortedList {
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null || head.next==null){
            return head;
        }
        ListNode cur=head;
        ListNode newCur=head.next;

        while(newCur!=null){
            //ListNode next=cur.next;
            if(cur.val==newCur.val){
                //如果相邻元素是相等的话，删除后者
                cur.next=newCur.next;
            }else{
                cur=newCur;
            }
            newCur=newCur.next;
        }
        return head;
    }

    @Test
    public void test(){
        //int[] arr={1,1,2};
        //int[] arr={1,1,2,3,3};
        int[] arr={1,1,1,1,1};
        ListNode head=LinkedListUtils.createLinkedList(arr);
        LinkedListUtils.printList(head);

        head=deleteDuplicates(head);
        LinkedListUtils.printList(head);
    }
}
