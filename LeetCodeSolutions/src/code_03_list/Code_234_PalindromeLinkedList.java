package code_03_list;

/**
 * Given a singly linked list, determine if it is a palindrome.
 *
 * Example 1:
 * Input: 1->2
 * Output: false
 *
 * Example 2:
 * Input: 1->2->2->1
 * Output: true
 *
 * Follow up:
 * Could you do it in O(n) time and O(1) space?
 */
public class Code_234_PalindromeLinkedList {
    public boolean isPalindrome(ListNode head) {
        if(head==null || head.next==null){
            return true;
        }
        ListNode fast=head;
        ListNode slow=head;
        while(slow!=null && fast.next!=null && fast.next.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }

        ListNode head1=head;
        ListNode head2=slow.next;
        slow.next=null;
        head2=reverseList(head2);

        while(head1!=null && head2!=null){
            if(head1.val!=head2.val){
                return false;
            }
            head1=head1.next;
            head2=head2.next;
        }
        return true;
    }

    //反转链表
    public ListNode reverseList(ListNode head) {
        ListNode pre=null;
        ListNode cur=head;

        while(cur!=null){
            ListNode next=cur.next;
            cur.next=pre;
            pre=cur;
            cur=next;
        }
        return pre;
    }
}
