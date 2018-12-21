package code_05_binaryTree;

/**
 * 108. Convert Sorted Array to Binary Search Tree
 *
 * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
 * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node
 * never differ by more than 1.
 */
public class Code_108_ConvertSortedArrayToBinarySearchTree {
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums,0,nums.length-1);
    }

    private TreeNode sortedArrayToBST(int[] nums,int start,int end) {
        if(start>end){
            return null;
        }
        int mid=start+(end-start)/2;
        TreeNode root=new TreeNode(nums[mid]);
        if(start==end){
            return root;
        }
        root.left=sortedArrayToBST(nums,start,mid-1);
        root.right=sortedArrayToBST(nums,mid+1,end);
        return root;
    }
}
