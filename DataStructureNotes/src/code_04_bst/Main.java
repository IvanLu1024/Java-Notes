package code_04_bst;

/**
 * Created by 18351 on 2018/12/19.
 */
public class Main {
    public static void main(String[] args) {
        BST<Integer> bst=new BST<>();
        int[] nums={5,3,6,8,4,2};
        for(int num:nums){
            bst.add(num);
        }
        /**
         *      5
         *     / \
         *    3   6
         *   / \   \
         *  2  4    8
         */
       /* bst.preOrder();
        System.out.println();
        bst.preOrderNR();
        System.out.println("=====================");
        //bst.inOrder();
        //System.out.println();
        bst.inOrder();
        System.out.println();
        bst.inOrderNR();
        System.out.println("=====================");

        bst.postOrder();
        System.out.println();
        bst.postOrderNR();
        //System.out.println(bst);

        System.out.println(bst.min());
        System.out.println(bst.max());*/
        bst.levelOrder();
        System.out.println();
        bst.delMin();
        System.out.println(bst);
        bst.delMin();
        System.out.println(bst);
        bst.delMax();
        System.out.println(bst);

        bst.del(5);
        System.out.println(bst);
    }
}
