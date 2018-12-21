package code_09_string;

/**
 * Give a string s,
 * count the number of non-empty (contiguous) substrings that have the same number of 0's and 1's,
 * and all the 0's and all the 1's in these substrings are grouped consecutively(继续地).
 * Substrings that occur multiple times are counted the number of times they occur.

 * Example 1:
 Input: "00110011"
 Output: 6
 Explanation: There are 6 substrings that have equal number of consecutive 1's and 0's: "0011", "01", "1100", "10", "0011", and "01".
 Notice that some of these substrings repeat and are counted the number of times they occur.
 Also, "00110011" is not a valid substring because all the 0's (and 1's) are not grouped together.

 * Example 2:
 Input: "10101"
 Output: 4
 Explanation: There are 4 substrings: "10", "01", "10", "01" that have equal number of consecutive 1's and 0's.
 */
public class Code_696_CountBinarySubstrings {
    /**
     * “1111000011010001011”转化为“4 4 2 1 1 3 1 1 2 ”，
     * 也就是统计一下每个连续子串的个数，这样我们就可以方便的获得满足条件的子串个数，
     * 统计转化后的数组相邻元素之间最小的那个求和即可。
     */
    public int countBinarySubstrings(String s) {
        int n=s.length();
        if(n==0){
            return 0;
        }
        int[] count=new int[n];
        int index=0;
        for(int i=0;i<n-1;i++){
            count[index]++;
            if(s.charAt(i)!=s.charAt(i+1)){
                index++;
            }
        }
        count[index]++;
        int res=0;
        for(int i=0;i<index;i++){
            res+=Math.min(count[i],count[i+1]);
        }
        return res;
    }
}
