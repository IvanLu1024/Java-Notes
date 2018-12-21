package code_03_linkedlistAndRecursion;

/**
 * Created by DHA on 2018/12/7.
 */
public class Solution {
    public ListNode removeElements(ListNode head, int val) {
        if(head==null){
            return head;
        }
        while(head.val==val){
            ListNode delNode=head;
            head=head.next;
            delNode.next=null;
        }

        ListNode prev=head;
        while(prev.next!=null){
            if(prev.next.val==val){
                ListNode delNode=prev.next;
                prev.next=delNode.next;
                delNode.next=null;
            }else{
                prev=prev.next;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        int[] arr={1,2,6,3,4,5,6};
        int val = 6;
        ListNode head=new ListNode(arr);
        System.out.println(head);
        new Solution().removeElements(head,val);
        System.out.println(head);
    }
}
