package code_03_list;

/**
 * 141. Linked List Cycle
 *
 * Given a linked list, determine if it has a cycle in it.
 * To represent a cycle in the given linked list, we use an integer pos which represents
 * the position (0-indexed) in the linked list where tail connects to.
 * If pos is -1, then there is no cycle in the linked list.
 */
public class Code_141_LinkedListCycle {
    public boolean hasCycle(ListNode head) {
        if(head==null)
            return false;
        ListNode slow=head;
        ListNode fast=head.next;
        while(slow!=null &&
                (fast!=null && fast.next!=null)){
            if(fast==slow)
                return true;
            //fast!=null && fast.next!=null) 保证了一次走两步
            slow=slow.next;
            fast=fast.next.next;
        }
        return false;
    }
}
