package code_03_list;

import org.junit.Test;

/**
 * Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.

 *  Example:
 *
 * Input: 1->2->4, 1->3->4
 * Output: 1->1->2->3->4->4
 */
public class Code_21_MergeTwoSortedLists {
    //时间复杂度 O(n)
    //空间复杂度 O(1)
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==null){
            return l2;
        }
        if(l2 ==null){
            return l1;
        }

        ListNode dummyHead=new ListNode(0);

        ListNode cur1=l1;
        ListNode cur2=l2;
        ListNode cur=dummyHead;
        while(cur1!=null && cur2!=null){
            if(cur1.val <cur2.val){
                cur.next=cur1;
                cur1=cur1.next;
            }else{
               cur.next=cur2;
               cur2=cur2.next;
            }
            cur=cur.next;
        }
        if(cur1!=null){
            cur.next=cur1;
        }
        if(cur2!=null){
            cur.next=cur2;
        }

        ListNode retNode=dummyHead.next;
        dummyHead.next=null;
        return retNode;
    }

    @Test
    public void test(){
        int[] arr1={1,2,4};
        int[] arr2={1,3,4};
        ListNode l1=LinkedListUtils.createLinkedList(arr1);
        ListNode l2=LinkedListUtils.createLinkedList(arr2);

        ListNode head=mergeTwoLists(l1,l2);
        LinkedListUtils.printList(head);
    }
}
