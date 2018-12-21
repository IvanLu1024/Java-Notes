package code_03_list;

import org.junit.Test;

/**
 * 147. Insertion Sort List
 *
 * Sort a linked list using insertion sort.
 *
 * Algorithm of Insertion Sort:
 *
 * Insertion sort iterates, consuming one input element each repetition, and growing a sorted output list.
 * At each iteration, insertion sort removes one element from the input data, finds the location it belongs within the sorted list, and inserts it there.
 * It repeats until no input elements remain.
 *
 * Example 1:
 * Input: 4->2->1->3
 * Output: 1->2->3->4
 *
 * Example 2:
 * Input: -1->5->3->4->0
 * Output: -1->0->3->4->5
 */
public class Code_147_InsertionSortList {
    //时间复杂度O(n^2)
    //空间复杂度O(1)
    public ListNode insertionSortList(ListNode head) {
        if(head==null || head.next==null){
            return head;
        }

        ListNode dummyHead=new ListNode(-1);

        ListNode pre=dummyHead;
        //pre始终指向要插入节点的前一个节点

        ListNode cur=head;

        while(cur!=null){
            pre=dummyHead;
            //每次循环，都要将pre初始化，方便定位要插入的节点的前一个位置
            while(pre.next!=null && pre.next.val<cur.val){
                pre=pre.next;
            }
            ListNode tmp=cur.next;
            //将 cue插入到链表中,在 pre-->pre.next之间插入
            cur.next=pre.next;
            pre.next=cur;
            cur=tmp;
        }

        ListNode retNode=dummyHead.next;
        dummyHead=null;

        return retNode;
    }

    public void insertSort(int[] arr){
        int n=arr.length;
        for(int i=1;i<n;i++){
            //注意边界条件
            for(int j=i;j>0;j--){
                if(arr[j-1]>arr[j]){
                    swap(arr,j-1,j);
                }
            }
        }
    }

    public void swap(int[] arr,int i,int j){
        int tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }

    @Test
    public void test(){
        int[] arr={3,2,1,4,5,-1,0};
        insertSort(arr);
        for(int i:arr){
            System.out.println(i);
        }
    }
}
