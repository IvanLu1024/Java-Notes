package code_03_list;

/**
 * 148. Sort List
 *
 * Sort a linked list in O(n log n) time using constant space complexity.

 * Example 1:
 * Input: 4->2->1->3
 * Output: 1->2->3->4
 *
 * Example 2:
 * Input: -1->5->3->4->0
 * Output: -1->0->3->4->5
 */
public class Code_148_SortList {
    //时间复杂度O(n^2)
    public ListNode sortList1(ListNode head) {
        if(head==null || head.next==null){
            return head;
        }

        ListNode dummyHead=new ListNode(-1);
        ListNode pre=dummyHead;
        ListNode cur=head;
        while(cur!=null){
            pre=dummyHead;
            while(pre.next!=null && pre.next.val<cur.val){
                pre=pre.next;
            }
            ListNode tmp=cur.next;
            cur.next=pre.next;
            pre.next=cur;
            cur=tmp;
        }
        ListNode retNode=dummyHead.next;
        dummyHead.next=null;

        return retNode;
    }

    //有O(n lgn)时间复杂度的算法为，快速排序，堆排序，归并排序
    //这里就使用归并排序
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        //获取该链表的中间节点
        ListNode midNode=getMidOfList(head);
        //此时链表分为两个部分 ：
        // head->...->midNode的前一个节点
        // midNOde->..->NULL
        return mergeTwoSortedList(sortList(head),sortList(midNode));
    }

    //获取链表中间节点，将两个子链表分离
    private ListNode getMidOfList(ListNode head){
        //这里保证了至少有两个节点
        ListNode slow=head;
        ListNode fast=head;
        ListNode pre=head;
        while(slow!=null && fast!=null){
            pre=slow;
            slow=slow.next;
            fast=fast.next;
            //注意fast是快指针，每次走两步，但是当链表中节点数是奇数时，就会有问题，
            //所以走第二步时，要先进行判断
            if(fast!=null){
                fast=fast.next;
            }else{
                break;
            }
        }
        pre.next=null;
        return slow;
    }

    //合并两个有序的链表
    private ListNode mergeTwoSortedList(ListNode head1,ListNode head2){
        if(head1==null){
            return head2;
        }
        if(head2==null){
            return head1;
        }
        ListNode dummyHead=new ListNode(-1);
        ListNode cur=dummyHead;
        while(head1!=null && head2!=null){
            if(head1.val<head2.val){
                cur.next=head1;
                head1=head1.next;
            }else{
                cur.next=head2;
                head2=head2.next;
            }
            cur=cur.next;
        }
        if(head1!=null){
            cur.next=head1;
        }
        if(head2!=null){
            cur.next=head2;
        }
        ListNode retNode=dummyHead.next;
        dummyHead=null;
        return retNode;
    }
}
