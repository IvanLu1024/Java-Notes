package code_04_stackQueue;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 23. Merge k Sorted Lists
 *
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
 *
 * Example:
 * Input:
 [
 1->4->5,
 1->3->4,
 2->6
 ]
 * Output:
 * 1->1->2->3->4->4->5->6
 */
public class Code_23_MergeKSortedLists {
    //思路一：逐步合并有序链表,n是数组长度，K是数组中的链表平均长度
    //时间复杂度：O(n*K)
    //空间复杂度：O(1)
    public ListNode mergeKLists1(ListNode[] lists) {
        if(lists==null || lists.length==0){
            return null;
        }

        //将该数组中元素逐步合并
        ListNode head=lists[0];
        for(int i=1;i< lists.length;i++){
            head=mergeTwoLists(head,lists[i]);
        }
        return head;
    }

    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
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

    //思路二:使用优先队列,n是数组长度，K是链表平均长度
    //时间复杂度：O(n * k log k)
    //空间复杂度：O(n)
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists==null || lists.length==0){
            return null;
        }
        if (lists.length == 1){
            return lists[0];
        }

        //维护一个最小堆
        PriorityQueue<ListNode> queue = new PriorityQueue<>(new Comparator<ListNode>() {
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });
        for (int i = 0; i < lists.length; i++) {
            //这里只是加了每个链表的头结点
            if (lists[i] != null) queue.add(lists[i]);
        }

        ListNode dummyHead=new ListNode(-1);
        ListNode cur=dummyHead;

        while(!queue.isEmpty()){
            ListNode tmp=queue.poll();
            cur.next=tmp;
            cur=tmp;
            //看看该结点是否有下一个结点，这样就确保所有的元素都加入优先队列中了。
            if (tmp.next != null){
                queue.add(tmp.next);
            }
        }
        cur.next=null;

        ListNode retNode=dummyHead.next;
        dummyHead=null;

        return retNode;
    }
}
