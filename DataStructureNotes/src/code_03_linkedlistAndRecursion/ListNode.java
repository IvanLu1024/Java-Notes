package code_03_linkedlistAndRecursion;

/**
 * Created by DHA on 2018/12/7.
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode(int x) { val = x; }

    public ListNode(int[] arr){
        if(arr==null || arr.length==0){
            throw new IllegalArgumentException("arr can not be empty");
        }
        ListNode cur=this;
        cur.val=arr[0];
        for(int i=1;i<arr.length;i++){
            cur.next=new ListNode(arr[i]);
            cur=cur.next;
        }
    }

    @Override
    public String toString() {
        StringBuilder res=new StringBuilder();
        ListNode cur=this;
        while(cur!=null){
            res.append(cur.val+"->");
            cur=cur.next;
        }
        res.append("NULL");
        return res.toString();
    }
}
