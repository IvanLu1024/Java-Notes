package code_03_list;

import org.junit.Test;

/**
 * 725. Split Linked List in Parts
 *
 * Given a (singly) linked list with head node root, write a function to split the linked list
 * into k consecutive(连续的) linked list "parts".
 * The length of each part should be as equal as possible:
 * no two parts should have a size differing by more than 1. This may lead to some parts being null.
 * The parts should be in order of occurrence(按照出现的次序) in the input list,
 * and parts occurring earlier should always have a size greater than or equal parts occurring later.
 * Return a List of ListNode's representing the linked list parts that are formed.
 * Examples 1->2->3->4, k = 5 // 5 equal parts [ [1], [2], [3], [4], null ]

 * Example 1:
 Input:
 root = [1, 2, 3], k = 5
 Output: [[1],[2],[3],[],[]]
 Explanation:
 The input and each element of the output are ListNodes, not arrays.
 For example, the input root has root.val = 1, root.next.val = 2, \root.next.next.val = 3, and root.next.next.next = null.
 The first element output[0] has output[0].val = 1, output[0].next = null.
 The last element output[4] is null, but it's string representation as a ListNode is [].

 * Example 2:
 Input:
 root = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 3
 Output: [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]
 Explanation:
 The input has been split into consecutive parts with size difference at most 1, and earlier parts are a larger size than the later parts
 */
public class Code_725_SplitLinkedListInParts {
    /**
     * 思路：
     * cnt/k：每段链表的最短长度
     * mod=cnt%k：链表的修正长度，就是长度不能被k整除后，所得的余数分散到前mod个链表中
     */
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode[] res=new ListNode[k];

        //len数组记录苏分割的相应的链表的长度
        int[] len=new int[k];

        int cnt=getCount(root);

        //每段链表的最短长度
        int perLen=cnt/k;
        //链表的修正长度，就是长度不能被k整除后，所得的余数分散到前mod个链表中
        int mod=cnt%k;
        for(int i=0;i<k;i++){
            len[i]=perLen;
            if(mod>0){
                len[i]++;
                mod--;
            }
        }

        for(int i=0;i<k;i++){
            res[i]=root;
            root=getNextRoot(root,len[i]);
        }
        return res;
    }

    //统计链表中节点数
    private int getCount(ListNode root){
        int count=0;
        ListNode cur=root;
        while (cur!=null){
            count++;
            cur=cur.next;
        }
        return count;
    }

    //在原链表中截取长度为size的链表,长度为[0..size-1]的链表的下一个链表
    private ListNode getNextRoot(ListNode root,int size){
        if(root==null){
            return null;
        }
        while(root!=null && size-1>0){
            root=root.next;
            size--;
        }
        ListNode res=root;
        if(res!=null){
            res=res.next;
        }
        //root此时是上一个节点的尾节点
        if(root!=null){
            root.next=null;
        }
        return res;
    }

    @Test
    public void test(){
        int[] arr={1,2,3};
        ListNode list=LinkedListUtils.createLinkedList(arr);
        ListNode[] listNodes=splitListToParts(list,5);
        for(int i=0;i<listNodes.length;i++){
            LinkedListUtils.printList(listNodes[i]);
        }
    }

}
