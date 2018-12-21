package code_03_list;

import org.junit.Test;

/**
 * 86. Partition List
 *
 * Given a linked list and a value x,
 * partition it such that all nodes less than x come before nodes greater than or equal to x.
 * You should preserve the original relative order of the nodes in each of the two partitions.
 *
 * Example:
 * Input: head = 1->4->3->2->5->2, x = 3
 * Output: 1->2->2->4->3->5
 */
public class Code_86_PartitionList {
    //注意：要保留元素相对位置
    //准备两个链表，分别指向 <x 的元素，和>=x的元素
    //然后将这两个链表合并
    public ListNode partition(ListNode head, int x) {
        if(head==null || head.next==null){
            return head;
        }
        ListNode lessHead=new ListNode(0);
        ListNode moreOrEqualHead=new ListNode(0);

        ListNode less=lessHead;// 存储<x的元素
        ListNode moreOrEqual=moreOrEqualHead;//存储>=x的元素
        ListNode cur=head;
        while(cur!=null){
            if(cur.val<x){
                less.next=cur;
                less = less.next;
            }else{
                moreOrEqual.next=cur;
                moreOrEqual=moreOrEqual.next;
            }
            cur=cur.next;
        }

        //将两个链表连接起来
        less.next=moreOrEqualHead.next;
        //注意最后一个元素，要进行处理
        moreOrEqual.next=null;

        //释放头结点元素
        moreOrEqual.next=null;
        ListNode retNode=lessHead.next;
        lessHead.next=null;

        return retNode;
    }

    @Test
    public void test(){
        int[] arr={1,4,3,2,2};
        int x=3;
        ListNode head=LinkedListUtils.createLinkedList(arr);
        LinkedListUtils.printList(head);

        head=partition(head,x);
        LinkedListUtils.printList(head);
    }
}
