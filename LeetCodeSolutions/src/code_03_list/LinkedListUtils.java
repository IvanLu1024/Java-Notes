package code_03_list;

/**
 * Created by 18351 on 2018/11/3.
 */
public class LinkedListUtils {
    public static ListNode createLinkedList(int[] arr){
        if(arr.length==0){
            return null;
        }
        ListNode head=new ListNode(arr[0]);

        ListNode curNode=head;
        for(int i=1;i<arr.length;i++){
            curNode.next=new ListNode(arr[i]);
            curNode=curNode.next;
        }
        return head;
    }

    public static void printList(ListNode head){
        ListNode curNode=head;
        while(curNode!=null){
            System.out.print(curNode.val+"-->");
            curNode=curNode.next;
        }
        System.out.println("NULL");
    }
}
