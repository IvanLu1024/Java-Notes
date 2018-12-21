package code_03_list;

import org.junit.Test;

/**
 * 2. Add Two Numbers
 *
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Example:
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 *
 */
public class Code_2_AddTwoNumbers {
    //这里针对的是 两个链表相加，不超过节点数的情况
    //未能通过
    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        ListNode dummyHead=new ListNode(-1);
        ListNode tail=dummyHead;

        while(l1!=null && l2!=null){
            int sum=l1.val+l2.val;
            if(sum>=10){
                sum-=10;
                if(getLen(l1)>getLen(l2)){
                    if(l1.next!=null){
                        l1.next.val+=1;
                    }
                }else{
                    if(l2.next!=null){
                        l2.next.val+=1;
                    }
                }
            }
            ListNode newNode=new ListNode(sum);
            tail.next=newNode;
            tail=newNode;
            l1=l1.next;
            l2=l2.next;
        }
        if(l1!=null){
            tail.next=l1;
        }
        if(l2!=null){
            tail.next=l2;
        }
        return dummyHead.next;
    }

    //计算链表的长度
    private int getLen(ListNode head){
         int len=0;
         while(head!=null){
             len++;
             head=head.next;
         }
         return len;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //使用尾插法创建链表
        ListNode dummyHead=new ListNode(-1);
        ListNode tail=dummyHead;

        int tmp=0;
        while(l1!=null || l2!=null){
            if(l1!=null){
                tmp+=l1.val;
                l1=l1.next;
            }
            if(l2!=null){
                tmp+=l2.val;
                l2=l2.next;
            }
            ListNode newNode=new ListNode(tmp%10);
            tail.next=newNode;
            tail=newNode;
            tmp/=10;
        }
        if(tmp==1){
            ListNode newNode=new ListNode(1);
            tail.next=newNode;
            tail=newNode;
        }

        ListNode retNode=dummyHead.next;
        dummyHead=null;
        return retNode;
    }

    @Test
    public void test(){
        int[] arr1={2,4,9,9};
        int[] arr2={5,6,4,8};

        ListNode l1=LinkedListUtils.createLinkedList(arr1);
        ListNode l2=LinkedListUtils.createLinkedList(arr2);
        LinkedListUtils.printList(l1);
        LinkedListUtils.printList(l2);

        ListNode head=addTwoNumbers(l1,l2);
        LinkedListUtils.printList(head);
    }
}
