package code_03_list;

/**
 * 203. Remove Linked List Elements
 * Remove all elements from a linked list of integers that have value val.

 * Example:
 * Input:  1->2->6->3->4->5->6, val = 6
 * Output: 1->2->3->4->5
 */
public class Code_203_RemoveLinkedListElements {
    public ListNode removeElements(ListNode head, int val) {
        if(head==null){
            return head;
        }
        //创建一个虚拟的头结点
        ListNode dummyHead=new ListNode(0);
        dummyHead.next=head;

        ListNode cur=dummyHead;
        while(cur.next!=null){
            //cur指向被删除元素的前一个元素,cur.next就是要删除的元素
            if(cur.next.val==val){
                ListNode delNode=cur.next;
                cur.next=delNode.next;
                //删除该节点，直接赋值为null,让JVM进行垃圾回收就行了
                delNode.next=null;
            }else{
                cur=cur.next;
            }
        }

        ListNode retNode=dummyHead.next;
        dummyHead.next=null;
        return retNode;
    }
}
