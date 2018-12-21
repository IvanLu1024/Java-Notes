package code_03_list;

import java.util.HashSet;
import java.util.Set;

/**
 * 817. Linked List Components
 *
 * We are given head, the head node of a linked list containing unique integer values.(链表中每个数字都不同)
 * We are also given the list G, a subset of the values in the linked list.
 *
 * Return the number of connected components in G,
 * where two values are connected if they appear consecutively（连续的） in the linked list.
 *
 * Example 1:
 Input:
 head: 0->1->2->3
 G = [0, 1, 3]
 Output: 2
 Explanation:
 0 and 1 are connected, so [0, 1] and [3] are the two connected components.

* Example 2:
 Input:
 head: 0->1->2->3->4
 G = [0, 3, 1, 4]
 Output: 2
 Explanation:
 0 and 1 are connected, 3 and 4 are connected, so [0, 1] and [3, 4] are the two connected components.

 * Note:
 If N is the length of the linked list given by head, 1 <= N <= 10000.

 The value of each node in the linked list will be in the range [0, N - 1].

 1 <= G.length <= 10000.

 G is a subset of all values in the linked list.
 */
public class Code_817_LinkedListComponents {
    /**
     * 思路：
     * 我们顺次检查链表中的节点是否出现在G中，如果出现了，
     * 则增加一个统计，并连续跟踪直到当前的连续序列结束。
     * 这样遍历完成一遍列表，就可以获得预期答案。
     */
    public int numComponents(ListNode head, int[] G) {
        Set<Integer> set=new HashSet<>();
        int res=0;

        for(int i=0;i<G.length;i++){
            set.add(G[i]);
        }

        ListNode cur=head;
        while(cur!=null){
            //检查cur结点是都在G中
            if(set.contains(cur.val)){
                res++;
                while(cur!=null && set.contains(cur.val)){
                    cur=cur.next;
                }
            }
            if(cur!=null){
                cur=cur.next;
            }
        }
        return res;
    }
}
