package code_03_list;

/**
 * 61. Rotate List
 *
 * Given a linked list, rotate the list to the right by k places, where k is non-negative.
 *
 * Example 1:
 * Input: 1->2->3->4->5->NULL, k = 2
 * Output: 4->5->1->2->3->NULL
 *
 * Explanation:
 * rotate 1 steps to the right: 5->1->2->3->4->NULL
 * rotate 2 steps to the right: 4->5->1->2->3->NULL
 *
 * Example 2:
 * Input: 0->1->2->NULL, k = 4
 * Output: 2->0->1->NULL
 * Explanation:
 * rotate 1 steps to the right: 2->0->1->NULL
 * rotate 2 steps to the right: 1->2->0->NULL
 * rotate 3 steps to the right: 0->1->2->NULL
 * rotate 4 steps to the right: 2->0->1->NULL
 */
public class Code_61_RotateList {
    public ListNode rotateRight1(ListNode head, int k) {
        int len=0;
        ListNode cur=head;
        while(cur!=null){
            len++;
            cur=cur.next;
        }
        if(len==0){
            return head;
        }
        k=k%len;
        if(k==0){
            return head;
        }

        ListNode dummyHead=new ListNode(-1);
        dummyHead.next=head;

        ListNode p=dummyHead;
        ListNode preq=null; //始终指向q节点的前一个节点
        ListNode q=dummyHead;
        //先定位q指向（k+1）位置
        for(int i=0;i<k+1;i++){
            preq=q;
            q=q.next;
        }

        while(q!=null){
            p=p.next;
            preq=q;
            q=q.next;
        }
        //现在p指向倒数第k个位置的前一个位置
        head=p.next;
        p.next=null;
        preq.next=dummyHead.next;
        return head;
    }

    public ListNode rotateRight(ListNode head, int k) {
        if(head==null || head.next==null){
            return head;
        }
        int len=1;
        ListNode cur=head;
        while(cur.next!=null){
            len++;
            cur=cur.next;
        }
        //cur指向最后一个元素
        k=k%len;
        if(k==0){
            return head;
        }

        ListNode p=head;
        //先定位q指向（len-k-1）位置
        for(int i=0;i<len-k-1;i++){
            p=p.next;
        }

        ListNode tmp=p.next;
        p.next=null;
        cur.next=head;
        head=tmp;
        return head;
    }
}
