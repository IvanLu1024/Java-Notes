package code_01_array;

/**
 * 打印一维整型数组和二维整型数组的工具类
 */
public class ArrayUtils2 {
    /**
     * 打印二维数组
     */
    public static void print2DimenArray(int[][] martix){
        int m = martix.length;
        if (m==0){
            throw new IllegalArgumentException("martix error");
        }
        int n=martix[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j==n-1){
                    System.out.print(martix[i][j]);
                }else {
                    System.out.print(martix[i][j]+",");
                }
            }
            System.out.println();
        }
    }

    /**
     * 打印数组的工具类
     */
    public static void printArray(int[] nums){
        if (nums==null||nums.length==0){
            throw new IllegalArgumentException("array is null");
        }
        System.out.print("[");
        for (int i = 0; i < nums.length; i++) {
            if (i!=nums.length-1){
                System.out.print(nums[i]+",");
            }else {
                System.out.print(nums[i]+"]");
            }
        }

    }
}
