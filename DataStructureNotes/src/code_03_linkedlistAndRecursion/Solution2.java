package code_03_linkedlistAndRecursion;

/**
 * Created by DHA on 2018/12/7.
 */
public class Solution2 {
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummyHead=new ListNode(-1);
        dummyHead.next=head;

        ListNode prev=dummyHead;
        while(prev.next!=null){
            if(prev.next.val==val){
                ListNode delNode=prev.next;
                prev.next=delNode.next;
                delNode.next=null;
            }else{
                prev=prev.next;
            }
        }
        return dummyHead.next;
    }

    public static void main(String[] args) {
        int[] arr={1,2,6,3,4,5,6};
        int val = 6;
        ListNode head=new ListNode(arr);
        System.out.println(head);
        new Solution2().removeElements(head,val);
        System.out.println(head);
    }
}
