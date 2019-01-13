package code_10_others;

import java.util.List;
import java.util.Random;

/**
 * 382. Linked List Random Node
 *
 * Given a singly linked list, return a random node's value from the linked list.
 * Each node must have the same probability of being chosen.
 *
 * Follow up:
 * What if the linked list is extremely large and its length is unknown to you?
 * Could you solve this efficiently without using extra space?
 */
public class Code_382_LinkedListRandomNode {
    /**
     * 思路：
     * 第i个元素取到的概率等于 1/i，
     * 则不取得该元素的概率等于  (1/i)*(i/(1+i) * ((1+i)/(2+i)).......*(n-1/n)=1/n
     */
    class Solution {
        private ListNode head;
        private Random random=new Random();
        /** @param head The linked list's head.
        Note that the head is guaranteed to be not null, so it contains at least one node. */
        public Solution(ListNode head) {
            this.head=head;
        }

        /** Returns a random node's value. */
        public int getRandom() {
            ListNode cur=head;
            int res=cur.val;
            int i=1;
            while(cur!=null){
                if(random.nextInt(i)==0){
                    res=cur.val;
                }
                cur=cur.next;
                i++;
            }
            return res;
        }
    }
}
