package code_03_list;

import org.junit.Test;

/**
 * 876. Middle of the Linked List
 *
 * Given a non-empty, singly linked list with head node head, return a middle node of linked list.
 * If there are two middle nodes, return the second middle node.
 *
 * Example 1:
 Input: [1,2,3,4,5]
 Output: Node 3 from this list (Serialization: [3,4,5])
 The returned node has value 3.  (The judge's serialization of this node is [3,4,5]).
 Note that we returned a ListNode object ans, such that:
 ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, and ans.next.next.next = NULL.
 */
public class Code_876_MiddleOfTheLinkedList {
    public ListNode middleNode(ListNode head) {
        ListNode fast=head;
        ListNode slow=head;
        while(slow!=null && (fast!=null && fast.next!=null)){
            slow=slow.next;
            fast=fast.next.next;
        }
        return slow;
    }

    @Test
    public void test(){
        int[] arr={1,2,3,4,5,6};
        ListNode head=LinkedListUtils.createLinkedList(arr);
        LinkedListUtils.printList(head);
        ListNode node=middleNode(head);
        LinkedListUtils.printList(node);
    }
}
