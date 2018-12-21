package code_03_list;

import org.junit.Test;

/**
 *
 * 82 Remove Duplicates from Sorted List II
 * Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.
 *
 * Example 1:
 * Input: 1->2->3->3->4->4->5
 * Output: 1->2->5
 *
 * Example 2:
 * Input: 1->1->1->2->3
 * Output: 2->3
 */
public class Code_82_RemoveDuplicatesFromSortedListII {
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null || head.next==null){
            return head;
        }

        ListNode dummyHead=new ListNode(0);
        dummyHead.next=head;

        ListNode pre=dummyHead;//指向相同元素的前一个元素
        ListNode cur=pre.next; //指向当前元素

        while(cur.next!=null){
          if(cur.val!=cur.next.val){
              if(pre.next==cur){
                  pre=cur;
              }else{
                  //删除cur元素
                  pre.next=cur.next;
              }
          }
          cur=cur.next;
        }
        //cur此时是最后一个元素，
        //如果pre.next不是最后一个元素，这就说明最后一个元素是重复元素，被删除了，
        //则pre就是最后一个元素了
        if(pre.next!=cur){
            pre.next=null;
        }

        ListNode retNode=dummyHead.next;
        dummyHead.next=null;

        return retNode;
    }

    @Test
    public void test(){
        int[] arr={1,2,3,3,4,4,5};
        ListNode head=LinkedListUtils.createLinkedList(arr);
        LinkedListUtils.printList(head);

        head=deleteDuplicates(head);
        LinkedListUtils.printList(head);
    }
}
