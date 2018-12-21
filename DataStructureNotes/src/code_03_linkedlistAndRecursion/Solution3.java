package code_03_linkedlistAndRecursion;

/**
 * Created by DHA on 2018/12/7.
 */
public class Solution3 {
    public ListNode removeElements(ListNode head, int val) {
        if(head==null){
            return null;
        }
        ListNode res=removeElements(head.next,val);
        if(head.val==val){
            return res;
        }else{
            head.next=res;
            return head;
        }
    }

    public static void main(String[] args) {
        int[] arr={1,2,6,3,4,5,6};
        int val = 6;
        ListNode head=new ListNode(arr);
        System.out.println(head);
        new Solution3().removeElements(head,val);
        System.out.println(head);
    }
}
